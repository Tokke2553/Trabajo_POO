package Paquete;
import Armas.*;
import Objetos.*;

import java.util.Random;
public class Jugador {
	
	    private String nombre;
	    private float vida;
	    private float vidaMax;
	    private float escudo;
	    private Arma arma;
	    private Objetos objeto;
	    private boolean bot;
	    public Jugador(String nombre, float vida,float vidaMax, float escudo ,Arma arma, Objetos objeto ,boolean bot) {
	        this.nombre = nombre;
	        this.vida = vida;
	        this.vidaMax = vidaMax;
	        this.escudo = escudo;
	        this.arma = arma;
	        this.objeto = objeto;
	        this.bot = bot;
	        
	        
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
	    
	    public static Objetos obtenerObjetoAleatorio() {
	    	 float probabilidad = 0.75f;
    	    Random rand = new Random();
  
    	    if ((rand.nextFloat() > probabilidad) || (Pool_Objetos.length == 0))  { //comprobamos si el numero generado entre 0.0 y 1.0 es mayor a 0.75 y si la pool esta vacia
    	        return null;
    	    } else {
    	    	int indice = rand.nextInt(Pool_Objetos.length); 
    	    	return Pool_Objetos[indice]; 
    	    	}
	    }
	    
	    
	    public void curar(float cantidad) {
	    	this.vida += cantidad;
	    	if (vida > 100) {
	    		this.vida = 100;
	    	}
	    }
	    public void implementacionEscudo (float cantidad) {
	    	this.escudo += cantidad;
	    	if (escudo > 100) {
	    		this.escudo = 100;
	    	}
	    }
	    
	    public boolean estadoJugador(){
	    	return vida > 0;
	    	
	    } 
	    public float getVida() {
	        return vida;
	    }
	    public float getEscudo() {
	    	return escudo;
	    	
	    }
	    public String getNombre() {
	        return nombre;
	    }

	    public void recibirDanio(float danio) {
	        this.vida -= danio;

	        if (this.vida < 0) {
	            this.vida = 0;
	        }

	        System.out.println(nombre + " ha recibido " + danio + " de daño. Vida restante: " + vida);
	    }
	    
	    public void atacar(Jugador enemigo) {

	        float danio = arma.calcularDanio();
	        float danioRestante = danio - enemigo.escudo;
	       if (danioRestante > 0) {
	    	   enemigo.vida -= danioRestante;
	    	   enemigo.escudo = 0;
	    	  
	       }else {
	    	   enemigo.escudo -= danio; 
	    	      
	       }
	        
	       if (enemigo.vida < 0) {
	    	   enemigo.vida = 0;   
	       }

	        System.out.println(nombre + " ataca a " + enemigo.nombre + " con " + arma.getNombre() + " causando " + danio + " de daño");
	    }
	   
	    }
	   


