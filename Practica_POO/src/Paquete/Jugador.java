package Paquete;
import Armas.*;
import Objetos.*;
import Personajes.*;
import java.util.Random;
public class Jugador {
	
	    private String nombre;
	    private float vida;
	    private float vidaMax;
	    private float escudo;
	    private float multiplicadorDaño;
	    private float multiplicadorDistancia;
	    private float multiplicadorPrecision;
	    private float tamCargador;
	    private Arma arma;
	    public Objetos objeto;
	    private boolean bot;
	    private float DificultadAtaque = 1.0f;
	    ClasePersonaje clase;
	    
	    
	    public Jugador(String nombre, ClasePersonaje clase, Arma arma, Objetos objeto, boolean bot) {
	        this.nombre = nombre;
	        this.clase = clase;
	        this.vidaMax = clase.getVidaMax();
	        this.vida = clase.getVidaMax();
	        this.escudo = clase.getEscudo();
	        this.multiplicadorDaño = clase.getMultiDST();
	        this.multiplicadorDistancia = clase.getMultiDMG();
	        this.multiplicadorPrecision = clase.getMultiPRS();
	        this.tamCargador = clase.getTamCargador();
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
	    	vida += cantidad;
	    	System.out.println(this.nombre + "se ha curado");
	    	if (vida > vidaMax) {
	    		vida = 100;
	    	}
	    }
	    
	    public void implementacionEscudo (float cantidad) {
	    	escudo += cantidad;
	    	System.out.println(this.nombre + "ha usado una pocion de escudo");
	    	if (escudo > 100) {
	    		this.escudo = 100;
	    	}
	    }
	    
	    public void mejorarDaño(float multiplicador) { 
	    	multiplicadorDaño *= multiplicador;
	    	System.out.println(this.nombre + "ha usado una mejora de daño");
	    	}
	    
	    public void mejorarPrecision(float multiplicador) { 
	    	multiplicadorPrecision *= multiplicador; 
	    	System.out.println(this.nombre + "ha usado una mejora de precision");
	    	}
	    
	    public void mejorarCargador(float multiplicador) { 
	    	tamCargador = (int)(tamCargador * multiplicador);
	    	System.out.println(this.nombre + "ha usado una mejora de cargador");
	    	}
	    
	    public void mejorarDistancia(float multiplicador) { 
	    	multiplicadorDistancia *= multiplicador; 
	    	System.out.println(this.nombre + "ha usado una mejora de distancia");
	    	}
	    
	    
	  //Gestionar daño
	    public void recibirDanio(float danio) {
	        this.vida -= danio;

	        if (this.vida < 0) {
	            this.vida = 0;
	            System.out.println(nombre + "ha muerto");
	        	Logs.getInstance().agregarLog(nombre + " ha muerto");
	        }

	        System.out.println(nombre + " ha recibido " + danio + " de daño. Vida restante: " + vida);
	        
	        
	    }
	    
	    //Depentiendo de la dificultad el bot causa mas o menos daño
	    public void setDificultadAtaque(float factor) {
            if (this.isBot()) { 
                this.DificultadAtaque = factor;
                System.out.println("  -> Bot " + this.nombre + ": Factor de daño por dificultad establecido a x" + factor);
            }
        }
	    
	    public void usarObjeto() {
	        if (this.objeto != null) {
	            
	            System.out.println(this.nombre + " ha usado " + this.objeto.getNombre() + ".");
	            this.objeto.usar(this); 
	            this.objeto = null; 
	            System.out.println(this.nombre + " ya no tiene objeto equipado.");
	        } else {
	            System.out.println(this.nombre + " no tiene ningún objeto para usar.");
	        }
	    }
	    
	    public void atacar(Jugador enemigo) {

	        float danioArma = arma.calcularDanio(); //Daño base de las armas
	        float danioConMultiplicadores = danioArma * this.multiplicadorDaño; // Daño base arma +  multiplicador daño del objeto
	        float danioFinal; 
            if (this.isBot()) {
                danioFinal = danioConMultiplicadores * this.DificultadAtaque; //Daño dependiento del la dificultad, solo para bot
            } else {
                danioFinal = danioConMultiplicadores; // Los jugadores no afectan
            }
            
            
            //Aplicar escudo antes que la vida
	        float danioRestante = danioFinal - enemigo.escudo;
	       if (danioRestante > 0) {
	    	   enemigo.vida -= danioRestante;
	    	   enemigo.escudo = 0;
	    	  
	       }else {
	    	   enemigo.escudo -= danioFinal; 
	    	      
	       }
	        
	       if (enemigo.vida < 0) {
	    	   enemigo.vida = 0;   
	       }

	        System.out.println(nombre + " ataca a " + enemigo.nombre + " con " + arma.getNombre() + " causando " + danioFinal + " de daño");
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

	    	public float getVidaMax() {
	    		return vidaMax;
	    	}

			public float getMultiplicadorDaño() {
				return multiplicadorDaño;
			}


			public float getMultiplicadorDistancia() {
				return multiplicadorDistancia;
			}


			public float getMultiplicadorPrecision() {
				return multiplicadorPrecision;
			}


			public float getTamCargador() {
				return tamCargador;
			}


			public Arma getArma() {
				return arma;
			}


			public Objetos getObjeto() {
				return objeto;
			}


			public boolean isBot() {
				return bot;
			}


			public ClasePersonaje getClase() {
				return clase;
			}


			public static Objetos[] getPoolObjetos() {
				return Pool_Objetos;
			}
	    	
}
