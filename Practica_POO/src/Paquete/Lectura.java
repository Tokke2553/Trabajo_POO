package Paquete;

public class Lectura {
	
	boolean exist;

	public boolean lectura(int bloque[][],int i,int j) {
		
		
		int encontrarValor= bloque[i][j];
		
		if(encontrarValor != 10) {
			exist = true;
		}else {
			exist = false;
		}
		return exist;
	}
}

