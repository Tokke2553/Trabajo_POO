package paquete;
import Objetos.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;



public class BattleRoyale {



private List<JugadorCombate> jugadores;

private List<Zona> zonas;

private Random rand = new Random();

private int ronda = 1;

private static final int TAMANO_MAPA = 50;

private static final int MAX_RONDAS = 50;

//zona
private VentanaMapa ventanaMapa;  

private ZonaSegura tormenta;

private List<Objeto> objetosDisponibles;



// Clase interna Zona

public static class Zona {

private int centroX, centroY, radio;

private List<JugadorCombate> jugadores;

private int id;

private static int nextId = 1;



public Zona(int centroX, int centroY, int radio) {

this.id = nextId++;

this.centroX = centroX;

this.centroY = centroY;

this.radio = radio;

this.jugadores = new ArrayList<>();

}



public boolean estaDentro(JugadorCombate j) {

double dist = Math.sqrt(Math.pow(j.x - centroX, 2) + Math.pow(j.y - centroY, 2));

return dist <= radio;

}



public void agregarJugador(JugadorCombate j) { jugadores.add(j); }

public void eliminarJugador(JugadorCombate j) { jugadores.remove(j); }

public List<JugadorCombate> getJugadores() { return jugadores; }



public int getCentroX() { return centroX; }

public int getCentroY() { return centroY; }

public int getRadio() { return radio; }

public int getId() { return id; }



public void reducirRadio(int reduccion) {

if (radio > 2) radio -= reduccion;

}

}



// Constructor

public BattleRoyale(List<JugadorCombate> jugadores) {
    this.jugadores = new ArrayList<>(jugadores);
    this.zonas = new ArrayList<>();
    inicializarZonas(5);
    this.tormenta = new ZonaSegura(TAMANO_MAPA);
    asignarJugadoresAZonas();

    // Objetos disponibles
    objetosDisponibles = new ArrayList<>();
    objetosDisponibles.add(new Curas(20));
    objetosDisponibles.add(new Escudo(15));
    objetosDisponibles.add(new MejoraDanio(1.2f));
    objetosDisponibles.add(new MejoraPrecision(1.2f));
    objetosDisponibles.add(new MejoraAlcance(1.2f));
    objetosDisponibles.add(new MejoraCargador(2));

    // === AÑADE ESTO: CREAR LA VENTANA DEL MAPA ===
    SwingUtilities.invokeLater(() -> {
        ventanaMapa = new VentanaMapa();
        // Mostrar estado inicial
        actualizarVentana();
    });
}



private void inicializarZonas(int cantidad) {

Zona.nextId = 1;

int radioInicial = TAMANO_MAPA / (cantidad * 2);

for (int i = 0; i < cantidad; i++) {

int padding = radioInicial + 5;

int centroX = rand.nextInt(TAMANO_MAPA - 2 * padding) + padding;

int centroY = rand.nextInt(TAMANO_MAPA - 2 * padding) + padding;

zonas.add(new Zona(centroX, centroY, radioInicial));

}

}



private void asignarJugadoresAZonas() {

for (JugadorCombate j : jugadores) {

Zona z = zonas.get(rand.nextInt(zonas.size()));

j.x = z.getCentroX() + rand.nextInt(z.getRadio() * 2) - z.getRadio();

j.y = z.getCentroY() + rand.nextInt(z.getRadio() * 2) - z.getRadio();

z.agregarJugador(j);

}

}



private void darObjetoAleatorio(JugadorCombate jc) {

if (jc.jugadorBase.getInventario().size() >= 1) return;

if (rand.nextInt(100) < 75) {

Objeto obj = objetosDisponibles.get(rand.nextInt(objetosDisponibles.size()));

jc.jugadorBase.agregarObjeto(obj);

System.out.println("🎁 " + jc.nombre + " ha recibido un objeto: " + obj.getNombre());

}

}



public void iniciarBatalla() {

System.out.println("⚔️ ¡BATALLA ROYALE INICIADA!");

//Actualización inicial
actualizarVentana();

while (contarVivos() > 1 && ronda <= MAX_RONDAS) {

System.out.println("\n=== RONDA " + ronda + " ===");

System.out.println("🌪️ Tormenta (Centro: " + tormenta.getCentroX() + ", " + tormenta.getCentroY() + " / Radio: " + tormenta.getRadio() + ")");



// 0. Dar objetos aleatorios

for (JugadorCombate jc : jugadores) {

darObjetoAleatorio(jc);

}



// 1. Aplicar objetos del inventario

for (JugadorCombate jc : jugadores) {

for (Objeto obj : new ArrayList<>(jc.jugadorBase.getInventario())) {

jc.jugadorBase.usarObjeto(obj);

jc.vida = (int) jc.jugadorBase.getVida();

jc.escudo = (int) jc.jugadorBase.getEscudo();

System.out.println("🛡️ " + jc.nombre + " usa " + obj.getNombre() +

" | Vida: " + jc.vida + ", Escudo: " + jc.escudo);



if (jc.arma != null) {

jc.arma.setCargador(jc.arma.getCargador() * jc.jugadorBase.getMejoraCargador());

jc.arma.setDistancia(jc.arma.getDistancia() * jc.jugadorBase.getMejoraDistancia());

jc.arma.setDanioBase(jc.arma.getDanioBase()* jc.jugadorBase.getMejoraDanio());

jc.arma.setPrecision(jc.jugadorBase.getMejoraPrecision());

}

}

}



// 2. Mover jugadores dentro de su zona

for (Zona z : zonas) {

for (JugadorCombate j : new ArrayList<>(z.getJugadores())) {

if (!j.vivo) continue;

moverDentroZona(j, z);

}

}



// 3. Daño de tormenta

aplicarDanioTormenta(5);



//4. Daño en zonas

for (Zona z : zonas) {

List<JugadorCombate> lista = new ArrayList<>(z.getJugadores());



if (lista.size() > 1) {

System.out.println("--- Combate en Zona " + z.getId() + " (Centro: " + z.getCentroX() + ", " + z.getCentroY() + ")");

Logs.getInstance().agregarLogs("--- Combate en Zona " + z.getId() + " (Centro: " + z.getCentroX() + ", " + z.getCentroY() + ")");

}



Collections.shuffle(lista);

Set<JugadorCombate> yaAtacados = new HashSet<>();



for (int i = 0; i < lista.size(); i++) {

JugadorCombate atacante = lista.get(i);



if (atacante.vivo && !yaAtacados.contains(atacante)) {



for (int j = 0; j < lista.size(); j++) {

if (i != j) {

JugadorCombate defensor = lista.get(j);



if (defensor.vivo && !yaAtacados.contains(defensor) && atacante.arma != null) {



double distancia = Math.sqrt(Math.pow(atacante.x - defensor.x, 2) + Math.pow(atacante.y - defensor.y, 2));



if (distancia <= atacante.arma.getDistancia()) {



// 🔥 Disparar todas las balas del cargador

while (atacante.arma.tieneMunicion() && defensor.vivo) {

int danio = atacante.arma.calcularDanio(

atacante.clase.getMultiplicadorDanio(),

atacante.clase.getMultiplicadorPrecision()

);

atacante.arma.gastarMunicion();



if (danio > 0) {

defensor.recibirDanio(danio);

System.out.println("💥 (Zona " + z.getId() + ") " + atacante.nombre +

" ataca a " + defensor.nombre + " con " + atacante.arma.getNombre() +

" causando " + danio + " de daño. Vida restante: " + defensor.vida);

Logs.getInstance().agregarLogs("💥 (Zona " + z.getId() + ") " + atacante.nombre +

" ataca a " + defensor.nombre + " (" + danio + " daño). Vida restante: " + defensor.vida);



if (!defensor.vivo) {

System.out.println("💀 (Zona " + z.getId() + ") " + defensor.nombre +

" eliminado por " + atacante.nombre + " con " + atacante.arma.getNombre());

Logs.getInstance().agregarLogs("💀 (Zona " + z.getId() + ") " + defensor.nombre +

" eliminado por " + atacante.nombre + " con " + atacante.arma.getNombre());

}

} else {

System.out.println("🔫 (Zona " + z.getId() + ") " + atacante.nombre + " falló a " + defensor.nombre);

Logs.getInstance().agregarLogs("🔫 (Zona " + z.getId() + ") " + atacante.nombre + " falló a " + defensor.nombre);

}

}



// Marcar como atacados

yaAtacados.add(atacante);

yaAtacados.add(defensor);



// Terminar bucle de defensores para pasar al siguiente atacante

j = lista.size();

}

}

}

}

}

}

}



// 4. Reducir las zonas

tormenta.reducirZona();

reducirZonas();

//Actualización inicial
actualizarVentana();

ronda++;

// try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

}

//Finalización
int vivos = contarVivos();
String mensajeFinal = "";
if (vivos == 1) {
    JugadorCombate ganador = jugadores.stream().filter(j -> j.vivo).findFirst().orElse(null);
    mensajeFinal = "¡" + ganador.nombre + " gana la BATALLA ROYALE!";
} else if (vivos > 1 && ronda > MAX_RONDAS) {
    JugadorCombate ganador = jugadores.stream().filter(j -> j.vivo)
        .max(Comparator.comparingInt(j -> j.vida + j.escudo))
        .orElse(null);
    mensajeFinal = "Ganador por Supervivencia: " + ganador.nombre;
} else {
    mensajeFinal = "¡Todos han sido eliminados! Empate.";
}

System.out.println("🏆 " + mensajeFinal);

// Mostrar mensaje final en la ventana
if (ventanaMapa != null) {
    ventanaMapa.finalizarBatalla(mensajeFinal);
}

}



private void aplicarDanioTormenta(int danio) {

for (JugadorCombate j : new ArrayList<>(jugadores)) {

if (!j.vivo) continue;

if (!tormenta.estaDentro(j.x, j.y)) {

j.recibirDanio(danio);

System.out.println("🌩️ " + j.nombre + " recibe " + danio + " de daño de la tormenta. Vida restante: " + j.vida);

if (!j.vivo) System.out.println("💀 " + j.nombre + " eliminado por la tormenta.");

}

}

}



private void moverDentroZona(JugadorCombate j, Zona z) {

int dx = rand.nextInt(5) - 2;

int dy = rand.nextInt(5) - 2;



int nx = Math.max(z.getCentroX() - z.getRadio(), Math.min(z.getCentroX() + z.getRadio(), j.x + dx));

int ny = Math.max(z.getCentroY() - z.getRadio(), Math.min(z.getCentroY() + z.getRadio(), j.y + dy));



j.x = nx;

j.y = ny;

}



private void reducirZonas() {

List<Zona> zonasAEliminar = new ArrayList<>();

for (Zona z : zonas) {

z.reducirRadio(2);



List<JugadorCombate> fuera = new ArrayList<>();

for (JugadorCombate j : new ArrayList<>(z.getJugadores())) {

if (!z.estaDentro(j)) fuera.add(j);

}



for (JugadorCombate j : fuera) {

z.eliminarJugador(j);

if (zonas.isEmpty()) {

j.vivo = false;

continue;

}



if (j.esBot) {

Zona destino = zonas.get(rand.nextInt(zonas.size()));

j.x = destino.getCentroX() + rand.nextInt(destino.getRadio() * 2) - destino.getRadio();

j.y = destino.getCentroY() + rand.nextInt(destino.getRadio() * 2) - destino.getRadio();

destino.agregarJugador(j);

} else {

String[] opciones = new String[zonas.size()];

for (int i = 0; i < zonas.size(); i++) {

Zona zn = zonas.get(i);

opciones[i] = "Zona " + zn.getId() + " (C: " + zn.getCentroX() + ", " + zn.getCentroY() + " / R: " + zn.getRadio() + ")";

}

int eleccion = JOptionPane.showOptionDialog(

null,

j.nombre + " elige destino de reubicación:",

"Mover Zona - Ronda " + ronda,

JOptionPane.DEFAULT_OPTION,

JOptionPane.INFORMATION_MESSAGE,

null,

opciones,

opciones[0]

);

if (eleccion >= 0 && eleccion < zonas.size()) {

Zona destino = zonas.get(eleccion);

j.x = destino.getCentroX() + rand.nextInt(destino.getRadio() * 2) - destino.getRadio();

j.y = destino.getCentroY() + rand.nextInt(destino.getRadio() * 2) - destino.getRadio();

destino.agregarJugador(j);

} else {

j.vivo = false;

}

}

}



if (z.getJugadores().isEmpty()) zonasAEliminar.add(z);

}

zonas.removeAll(zonasAEliminar);

}
//actualizar ventana
private void actualizarVentana() {
    if (ventanaMapa != null) {
        // Usamos la tormenta como zona segura principal para la GUI
        ventanaMapa.actualizarEstado(
            new ArrayList<>(jugadores),
            tormenta.getCentroX(),
            tormenta.getCentroY(),
            tormenta.getRadio()
        );
    }
}


private int contarVivos() {

return (int) jugadores.stream().filter(j -> j.vivo).count();

}

}