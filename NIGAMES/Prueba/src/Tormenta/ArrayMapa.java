package Tormenta;

public class ArrayMapa {
    private static final int TAMANO = 50;
    private char[][] mapa;

    public ArrayMapa() {
    	
        mapa = new char[TAMANO][TAMANO];
    }

    public void ArrayInicializador() {
    	
        for (int i = 0; i < TAMANO; i++) {
            for (int j = 0; j < TAMANO; j++) {
            	
                mapa[i][j] = '.';
            }
        
        }
    }

    public char get(int x, int y) {
    	
        if (x >= 0 && x < TAMANO && y >= 0 && y < TAMANO) {
            return mapa[x][y];
        }
        return '#';
        
    }

    public void set(int x, int y, char c) {
    	
        if (x >= 0 && x < TAMANO && y >= 0 && y < TAMANO) {
            mapa[x][y] = c;
            
        }
    
    }

    public int getTamano() {
    	
        return TAMANO;
    }


}