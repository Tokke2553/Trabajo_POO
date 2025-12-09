package Paquete;

public class Main {
	public static void main(String[] args) {
		
		//crear array
		array array= new array();
		
		array.ArrayInicializador();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("■■■■                   MAPA                   ■■■■");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		array.printArray();
		//partida
		for(int j=0;j<4;j++) {
			//personaje
			Personaje personaje = new Personaje(array);
			personaje.moverseDeZona();
			System.out.println("");
			//tormenta
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■■■                RONDA "+(j+1)+"                   ■■■■");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("");
			System.out.println("--------------------------------------------------");
			System.out.println("---                Pos Tormenta                ---");
			System.out.println("--------------------------------------------------");
			System.out.println("");
			
			System.out.println("⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓⇓");
			EliminarZonas del = new EliminarZonas(array);
			for(int i=0;i<2;i++) {
				
				del.cambioArray();
			}
			System.out.println("");
			System.out.println("⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑⇑");
			
			
			System.out.println("");
			array.printArray();
			del.ciudadesSeguras();
			System.out.println("");
			System.out.println("");
		}
	}
	
}
