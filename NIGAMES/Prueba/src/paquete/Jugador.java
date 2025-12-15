package paquete;

import Objetos.Objeto;
import java.util.ArrayList;
import java.util.List;

public class Jugador {

    private String nombre;
    private float vidaMax;
    private float vida;
    private float escudo;
    private float mejoraDanio;
    private float mejoraPrecision;
    private int mejoraCargador;
    private float mejoraDistancia;

    private List<Objeto> inventario;

    public Jugador(String nombre, float vidaMax) {
        this.nombre = nombre;
        this.vidaMax = vidaMax;
        this.vida = vidaMax;
        this.escudo = 0;
        this.mejoraDanio = 1;
        this.mejoraPrecision = 1;
        this.mejoraCargador = 1;
        this.mejoraDistancia = 1;
        this.inventario = new ArrayList<>();
    }

    // =======================
    // Inventario y objetos
    // =======================
    public void agregarObjeto(Objeto obj) {
        inventario.add(obj);
    }

    public void usarObjeto(Objeto obj) {
        if (!inventario.contains(obj)) {
            System.out.println(nombre + " no tiene este objeto.");
            return;
        }
        obj.usar(this);
        inventario.remove(obj);
        System.out.println(nombre + " ha usado: " + obj.getNombre());
    }
 // Devuelve una copia de la lista de objetos
    public List<Objeto> getInventario() {
        return new ArrayList<>(inventario);
    }

    // =======================
    // Mejoras y curaciones
    // =======================
    public void curar(float cantidad) {
        vida += cantidad;
        if (vida > vidaMax) vida = vidaMax;
        System.out.println(nombre + " se ha curado. Vida actual: " + vida);
    }

    public void implementarEscudo(float cantidad) {
        escudo += cantidad;
        if (escudo > 100) escudo = 100;
        System.out.println(nombre + " ha usado una poción de escudo. Escudo actual: " + escudo);
    }

    public void mejorarDanio(float multiplicador) {
        mejoraDanio *= multiplicador;
        System.out.println(nombre + " ha mejorado el daño.");
    }

    public void mejorarPrecision(float multiplicador) {
        mejoraPrecision *= multiplicador;
        System.out.println(nombre + " ha mejorado la precisión.");
    }

    public void mejorarCargador(float multiplicador) {
        mejoraCargador = (int)(mejoraCargador * multiplicador);
        System.out.println(nombre + " ha mejorado el cargador.");
    }

    public void mejorarAlcance(float multiplicador) {
        mejoraDistancia *= multiplicador;
        System.out.println(nombre + " ha mejorado el alcance.");
    }

    // =======================
    // Getters y setters
    // =======================
    public String getNombre() { return nombre; }
    public float getVida() { return vida; }
    public float getEscudo() { return escudo; }
    public float getMejoraDanio() { return mejoraDanio; }
    public float getMejoraPrecision() { return mejoraPrecision; }
    public int getMejoraCargador() { return mejoraCargador; }
    public float getMejoraDistancia() { return mejoraDistancia; }

    public void setVida(float vida) { this.vida = vida; }
    public void setEscudo(float escudo) { this.escudo = escudo; }
}
