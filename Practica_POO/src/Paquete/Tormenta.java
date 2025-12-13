package Paquete;
import Tormenta.*;

public class Tormenta {
	//adquirimos e mapa
    private array mapa; 

    public Tormenta(array mapa) {
        this.mapa = mapa;
    }
    //ejecutamos tormenta
    public void ejecutarRondaDeTormenta() {
        
        System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        System.out.println("■■■ 			 	Tormenta 		           ■■■");
        System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
        
        //Eliminamos 2 zonas
        EliminarZonas del = new EliminarZonas(mapa);
        
        for(int i = 0; i < 2; i++) {
            del.cambioArray();
        }

        System.out.println("");
        //printear mapa bomba
        mapa.printArray();
        //Ciudades seguras
        del.ciudadesSeguras();
       
    }
}
