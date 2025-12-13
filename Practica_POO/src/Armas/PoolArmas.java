package Armas;

import Personajes.*;
import java.util.*;
public class PoolArmas {
	
	private static final Random random = new Random();
	 public static Arma armaPara(ClasePersonaje personaje) {
	        Arma[] armas = personaje.getArmas();
	        return armas[random.nextInt(armas.length)];
	    }
}
