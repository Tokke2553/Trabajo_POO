package Paquete;
import java.util.Random;

public class Personaje {
	//valores
	private int filaActual = -1; 
    private int columnaActual = -1;
	//RAndom
	Random random = new Random();
	//Array
	private array ciudad;
	//lectura
	Lectura leer = new Lectura();
	public Personaje(array arrayMapa) {
        this.ciudad = arrayMapa;
    }
	//moverse de zona
	public void moverseDeZona(){
		//valores
		boolean encontrado = false;
        int nuevaFila;
        int nuevaColumna;
		//bucle
		do {
			//valores
			nuevaFila = random.nextInt(3);
            nuevaColumna = random.nextInt(3);
			//leer array
			if(leer.lectura(this.ciudad.array,nuevaFila,nuevaColumna)){
				encontrado = true;
			}
			
		}while(!encontrado);
			
		this.filaActual = nuevaFila;
        this.columnaActual = nuevaColumna;
        
        int posicion=this.ciudad.array[nuevaFila][nuevaColumna];
        System.out.println("Personaje se ha movido a Ciudad " + posicion);
	}
	public int getFilaActual() {
        return filaActual;
    }
    public int getColumnaActual() {
        return columnaActual;
    }
}
