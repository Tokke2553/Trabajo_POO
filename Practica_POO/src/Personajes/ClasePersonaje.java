package Personajes;

import Armas.*;

public abstract class ClasePersonaje {

	//Variables de los personajes
	String personaje;
    int vida;
    int escudo;
    double multiDMG;
    double multiPRS;
    
    //Guardamos arma en una variable
    Arma arma;
    
    //Constructor de ClasePersonaje
    public ClasePersonaje(String personaje, int vida, int escudo, double multiDMG, double multiPRS) {
    	
    	this.personaje = personaje;
        this.vida = vida;
        this.escudo = escudo;
        this.multiDMG = multiDMG;
        this.multiPRS = multiPRS;
        
    }
    
    
    //Getter de array de armas
    public abstract Arma[] getArmas();
    
    public void setArma(Arma arma) {
    	//Si armaPermitida ha devuelto que el arma no es válida, se imprimirá un mensaje por consola avisando al usuario
    	if (armaPermitida(arma) == false) {
    	       
        	System.out.println("ARMA NO VÁLIDA PARA ESTE PERSONAJE.");
 
        }else{
    	
    	this.arma = arma;
    	
        }
    }
    	
       
    
    //Funcion que comprueba si el arma elegida está permitida
    protected boolean armaPermitida(Arma arma) {
    	
    	//For que recorre el array de las armas
        for (Arma a : getArmas()) {
        	
            if (a.getClass().equals(arma.getClass())) {
            	
            	//En caso de que el arma seleccionada se encuentre en el array, devuelve true
                return true;
            }
        }//Si no, devuelve false
        return false;
    }
    
 

    //Getters estadisticas
    public String getPersonaje() {
		return personaje;
	}

	public int getVida() {
		return vida;
	}

	public int getEscudo() {
		return escudo;
	}

	public double getMultiDMG() {
		return multiDMG;
	}

	public double getMultiPRS() {
		return multiPRS;
	}

	public Arma getArma() {
		return arma;
	}
	
	
	
	 
	 
	 
}
		
	
	
	
	

	

    
    
    

    
