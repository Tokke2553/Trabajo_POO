package Armas;

public abstract class Arma {


    protected String nombre;
    protected float danioBase;
    protected float precision;
    protected float distancia;
    protected int cargador;

   
    public Arma(String nombre, float danioBase, float distancia, float precision, int cargador) {
    	
        this.nombre = nombre;
        this.danioBase = danioBase;
        this.distancia = distancia;
        this.precision = precision;
        this.cargador = cargador;
        
    }



    public boolean tieneMunicion() {
        return cargador > 0;
    }

    public void gastarMunicion() { //Usando municion
    	
        if (cargador > 0) { 
            cargador--;
        }
        
    }
    

    public int getCargador() { 
        return cargador;
    }


  // Para calcular el daño y la precision
    public int calcularDanio(double multiplicadorDanio, double multiplicadorPrecision) {
    	
        double danio = danioBase * multiplicadorDanio;
        double probabilidad = Math.random();

        if (probabilidad <= precision * multiplicadorPrecision) {
        	
            return (int) Math.round(danio);
            
        }
        return 0;

    }



    public String getNombre() {
        return nombre;
    }

    public float getDanioBase() {
        return danioBase;
    }

    public float getPrecision() {
        return precision;
    }

    public float getDistancia() {
        return distancia;
    }


    @Override
    public String toString() {
        return nombre + " (balas: " + cargador + ")";
    }
    public void setCargador(int cargador) {
        this.cargador = cargador;
    }



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public void setDanioBase(float danioBase) {
		this.danioBase = danioBase;
	}



	public void setPrecision(float precision) {
		this.precision = precision;
	}



	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}

    
    
    
}
