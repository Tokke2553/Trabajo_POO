package Paquete;

import java.util.Random;

public class EliminarZonas {
	//RAndom
	Random random = new Random();
	//array de tormenta
	private array targetArray;
	//lectura
	Lectura leer = new Lectura();
	//valores de Comparacion
	int comp1;
	int comp2;
	//recibir array
	public EliminarZonas(array arrayAModificar) {
        this.targetArray = arrayAModificar;
    }
		//modificación de etormenta
		public void cambioArray() {
			//Acierto
			boolean acierto= false;
			//bucle para grantizar la destruccion
			while(!acierto) {
				//valores para j e i
				int num1 = random.nextInt(3);
				int num2 = random.nextInt(3);
				//System.out.println("num1= "+ num1);
				//System.out.println("num2= "+ num2);
					//si el numero es != 10
					if((leer.lectura(this.targetArray.array, num1, num2))==true) {
						int valorOriginal = this.targetArray.array[num1][num2];
						System.out.print("Ciudad "+valorOriginal+" Destruida❌\t     ");
						this.targetArray.array[num1][num2]=10;
						
						acierto=true;
						
						
					}else {
						//System.out.println("Ciudad "+this.targetArray.array[num1][num2]+" Anteriormente Destruida");
					}
					
			}
		}
		public void ciudadesSeguras() {
			//ciudades seguras
			for(int i=0;i<3;i++){				
				for(int j=0;j<3;j++){
					if(this.targetArray.array[i][j]!=10) {
						System.out.println("\t     ✅Ciudad "+this.targetArray.array[i][j]+" Zona Segura✅");
					}
				}
			}
		
		}
}
