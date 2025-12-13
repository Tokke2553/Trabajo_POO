package Personajes;

import Armas.*;

import java.util.*;

public abstract class ClasePersonaje {

	//Variables de los personajes
	String personaje;

	float vida;
	float escudo;
    float multiDST;
    float multiDMG;
    float multiPRS;
    int tamCargador;
    float vidaMax;
    //Guardamos arma en una variable
    Arma arma;
  
    
    //Constructor de ClasePersonaje

    public ClasePersonaje(String personaje, float vida,  float escudo, float multiDST, float multiDMG, float multiPRS,int tamCargador) {

    	
    	this.personaje = personaje;
        this.vida = vida;
        this.escudo = escudo;
        this.multiDMG = multiDMG;
        this.multiPRS = multiPRS;
        this.multiDST = multiDST;
        this.tamCargador= tamCargador;
        
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

	public  float getVida() {
		return vida;
	}

	public  float getEscudo() {
		return escudo;
	}

	public float getMultiDST() {
		return multiDST;
	}

	public float getMultiDMG() {
		return multiDMG;
	}

	public float getMultiPRS() {
		return multiPRS;
	}
	public float getTamCargador() {
		return tamCargador;
	}

	
	public Arma getArma() {
		return arma;
	}
	public float getVidaMax() {
		return vidaMax;
	}

	
	public static ClasePersonaje[] obtenerClases() {
	    return new ClasePersonaje[] { new Enano(), new Normal(), new Gigante() };
	}

	 
	 
}
		
	
	
	
	

	

    
    
    

    
