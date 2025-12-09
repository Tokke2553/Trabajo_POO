package Paquete;



public class array {
		
	public int[][] array= new int[3][3];
	private int valor=1;
	
	public void ArrayInicializador() {
		
		for(int i=0;i<3;i++){
			
			for(int j=0;j<3;j++){
				
				array[i][j]=valor;
				valor++;
			}
		}
	}
	public void printArray() {
		
		for(int i=0;i<3;i++){
			
			System.out.println("__________________________________________________");
			for(int j=0;j<3;j++){
				if(array[i][j]==10) {
					System.out.print("|	💣 \t");
				}else {
					System.out.print("|	"+array[i][j]+"\t");
				}
			}
			System.out.println("|");
			
			
		}
		System.out.println("__________________________________________________");
	}
	
}

