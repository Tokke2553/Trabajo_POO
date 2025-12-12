package Paquete;
import Armas.*;
import Objetos.*;
import Armas.Arma;
import java.util.Random;
public class Jugador {
	
	    private String nombre;
	    private int vida;

	    public Jugador(String nombre, int vida) {
	        this.nombre = nombre;
	        this.vida = vida;
	    }
	    private static final Objetos[] Pool_Objetos = {
	    	new Curas(),
	    	new Curas(),
	    	new Escudo(),
	    	new Escudo(),
	    	new MejoraCargador(),
	    	new MejoraDaño(),
	    	new MejoraDistancia(),
	    	new MejoraPrecision()    	
	    };
	    
	    private static Objetos obtenerObjetoAleatorio() {
	    	 float probabilidad = 0.75f;
    	    Random rand = new Random();
  
    	    if ((rand.nextFloat() > probabilidad) || (Pool_Objetos.length == 0))  { //comprobamos si el numero generado entre 0.0 y 1.0 es mayor a 0.75 y si la pool esta vacia
    	        return null;
    	    } else {
    	    	int indice = rand.nextInt(Pool_Objetos.length); 
    	    	return Pool_Objetos[indice]; 
    	    	}
	    }
	    
	    public int getVida() {
	        return vida;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public void recibirDanio(int cantidad) {
	        this.vida -= cantidad;

	        if (this.vida < 0) {
	            this.vida = 0;
	        }

	        System.out.println(nombre + " ha recibido " + cantidad + " de daño. Vida restante: " + vida);
	    }
	    
	    public void atacar(Arma arma, Jugador enemigo) {
	        int danioTotal = (int)(arma.getDanio() * arma.getMultiplicador());
	        enemigo.recibirDanio(danioTotal);

	        System.out.println(nombre + " atacó a " + enemigo.getNombre() +
	                           " con " + arma.getNombre() + " causando " + danioTotal + " de daño.");
	    }
	   
	    }
	   


