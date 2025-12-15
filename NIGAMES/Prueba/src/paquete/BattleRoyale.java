package paquete;

import Objetos.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class BattleRoyale {

    private List<JugadorCombate> jugadores;
    private List<Zona> zonas;
    private List<Objeto> objetosDisponibles;

    private Random rand = new Random();
    private int ronda = 1;

    private static final int TAMANO_MAPA = 50;
    private static final int MAX_RONDAS = 50;

    private VentanaMapa ventanaMapa;
    private ZonaSegura tormenta;

    /* ===================== */
    /* ===== ZONA ========= */
    /* ===================== */
    public static class Zona {

        private int centroX;
        private int centroY;
        private int radio;
        private int id;

        private List<JugadorCombate> jugadores;
        private static int nextId = 1;

        public Zona(int centroX, int centroY, int radio) {
            this.id = nextId++;
            this.centroX = centroX;
            this.centroY = centroY;
            this.radio = radio;
            this.jugadores = new ArrayList<>();
        }

        public boolean estaDentro(JugadorCombate j) {
            double d = Math.sqrt(
                Math.pow(j.x - centroX, 2) +
                Math.pow(j.y - centroY, 2)
            );
            return d <= radio;
        }

        public void agregarJugador(JugadorCombate j) {
            jugadores.add(j);
        }

        public void eliminarJugador(JugadorCombate j) {
            jugadores.remove(j);
        }

        public List<JugadorCombate> getJugadores() {
            return jugadores;
        }

        public int getCentroX() { return centroX; }
        public int getCentroY() { return centroY; }
        public int getRadio()   { return radio; }
        public int getId()      { return id; }

        public void reducirRadio(int r) {
            if (radio > 2) {
                radio -= r;
            }
        }
    }

    /* ===================== */
    /* === CONSTRUCTOR ===== */
    /* ===================== */
    public BattleRoyale(List<JugadorCombate> jugadores) {

        this.jugadores = new ArrayList<>(jugadores);
        this.zonas = new ArrayList<>();

        inicializarZonas(5);
        asignarJugadoresAZonas();

        tormenta = new ZonaSegura(TAMANO_MAPA);

        objetosDisponibles = new ArrayList<>();
        objetosDisponibles.add(new Curas(20));
        objetosDisponibles.add(new Escudo(15));
        objetosDisponibles.add(new MejoraDanio(1.2f));
        objetosDisponibles.add(new MejoraPrecision(1.2f));
        objetosDisponibles.add(new MejoraAlcance(1.2f));
        objetosDisponibles.add(new MejoraCargador(2));

        SwingUtilities.invokeLater(() -> {
            ventanaMapa = new VentanaMapa();
            actualizarVentana();
        });
    }

    /* ===================== */
    /* ===== INICIO ======== */
    /* ===================== */
    public void iniciarBatalla() {

        System.out.println("⚔️ ¡BATALLA ROYALE INICIADA!");
        Logs.getInstance().agregarLogs("BATALLA INICIADA");

        actualizarVentana();

        while (contarVivos() > 1 && ronda <= MAX_RONDAS) {

            System.out.println("\n=== RONDA " + ronda + " ===");
            Logs.getInstance().agregarLogs("RONDA " + ronda);

            /* 1. OBJETOS */
            for (JugadorCombate jc : jugadores) {
                darObjetoAleatorio(jc);
            }

            /* 2. USAR OBJETOS */
            for (JugadorCombate jc : jugadores) {

                for (Objeto o : new ArrayList<>(jc.jugadorBase.getInventario())) {

                    jc.jugadorBase.usarObjeto(o);

                    jc.vida = (int) jc.jugadorBase.getVida();
                    jc.escudo = (int) jc.jugadorBase.getEscudo();

                    System.out.println(jc.nombre + " usa " + o.getNombre());
                    Logs.getInstance().agregarLogs(jc.nombre + " usa " + o.getNombre());
                }
            }

            /* 3. MOVIMIENTO */
            for (Zona z : zonas) {

                for (JugadorCombate j : new ArrayList<>(z.getJugadores())) {

                    if (j.vivo) {
                        moverDentroZona(j, z);
                    }
                }
            }

            /* 4. DAÑO TORMENTA */
            aplicarDanioTormenta(5);

            /* 5. COMBATE */
            combatirEnZonas();

            /* 6. REDUCCIONES */
            tormenta.reducirZona();
            reducirZonas();

            actualizarVentana();
            ronda++;
        }

        finalizarBatalla();
    }

    /* ===================== */
    /* ===== MÉTODOS ======= */
    /* ===================== */

    private void darObjetoAleatorio(JugadorCombate jc) {

        if (jc.jugadorBase.getInventario().size() >= 1) {
            return;
        }

        if (rand.nextInt(100) < 75) {

            Objeto obj = objetosDisponibles.get(
                rand.nextInt(objetosDisponibles.size())
            );

            jc.jugadorBase.agregarObjeto(obj);

            System.out.println("🎁 " + jc.nombre + " recibe " + obj.getNombre());
            Logs.getInstance().agregarLogs(jc.nombre + " recibe " + obj.getNombre());
        }
    }

    private void moverDentroZona(JugadorCombate j, Zona z) {

        int dx = rand.nextInt(5) - 2;
        int dy = rand.nextInt(5) - 2;

        j.x = Math.max(
            z.getCentroX() - z.getRadio(),
            Math.min(z.getCentroX() + z.getRadio(), j.x + dx)
        );

        j.y = Math.max(
            z.getCentroY() - z.getRadio(),
            Math.min(z.getCentroY() + z.getRadio(), j.y + dy)
        );
    }

    private void aplicarDanioTormenta(int d) {

        for (JugadorCombate j : jugadores) {

            if (!j.vivo) {
                continue;
            }

            if (!tormenta.estaDentro(j.x, j.y)) {

                j.recibirDanio(d);

                System.out.println(j.nombre + " recibe daño de tormenta");
                Logs.getInstance().agregarLogs(j.nombre + " daño tormenta");
            }
        }
    }

    private void combatirEnZonas() {

        for (Zona z : zonas) {

            List<JugadorCombate> lista = new ArrayList<>(z.getJugadores());
            Collections.shuffle(lista);

            if (lista.size() > 1) {

                System.out.println("🔥 Combate en zona " + z.getId());
                Logs.getInstance().agregarLogs("Combate zona " + z.getId());
            }

            for (int i = 0; i < lista.size() - 1; i++) {

                JugadorCombate a = lista.get(i);
                JugadorCombate d = lista.get(i + 1);

                if (a.vivo && d.vivo && a.arma != null) {

                    int dmg = a.arma.calcularDanio(
                        a.clase.getMultiplicadorDanio(),
                        a.clase.getMultiplicadorPrecision()
                    );

                    d.recibirDanio(dmg);

                    System.out.println(a.nombre + " ataca a " + d.nombre);
                    Logs.getInstance().agregarLogs(a.nombre + " ataca a " + d.nombre);
                }
            }
        }
    }

    private void reducirZonas() {

        for (Zona z : zonas) {
            z.reducirRadio(2);
        }
    }

    private void actualizarVentana() {

        if (ventanaMapa != null) {

            ventanaMapa.actualizarEstado(
                new ArrayList<>(jugadores),
                tormenta.getCentroX(),
                tormenta.getCentroY(),
                tormenta.getRadio()
            );
        }
    }

    private int contarVivos() {

        int c = 0;

        for (JugadorCombate j : jugadores) {

            if (j.vivo) {
                c++;
            }
        }

        return c;
    }

    private void finalizarBatalla() {

        JugadorCombate ganador = null;

        for (JugadorCombate j : jugadores) {

            if (j.vivo) {
                ganador = j;
            }
        }

        if (ganador != null) {
            System.out.println("🏆 GANA " + ganador.nombre);
            Logs.getInstance().agregarLogs("GANADOR " + ganador.nombre);
        }
    }

    /* ===================== */
    /* ===== ZONAS ========= */
    /* ===================== */
    private void inicializarZonas(int n) {

        Zona.nextId = 1;
        int r = TAMANO_MAPA / (n * 2);

        for (int i = 0; i < n; i++) {

            int cx = rand.nextInt(TAMANO_MAPA);
            int cy = rand.nextInt(TAMANO_MAPA);

            zonas.add(new Zona(cx, cy, r));
        }
    }

    private void asignarJugadoresAZonas() {

        for (JugadorCombate j : jugadores) {

            Zona z = zonas.get(rand.nextInt(zonas.size()));
            z.agregarJugador(j);

            j.x = z.getCentroX();
            j.y = z.getCentroY();
        }
    }
}
