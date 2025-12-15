package Armas;

public class Multiplicador {
	private static float MULTIPLICADOR_FACIL = 0.75f;
	private static float MULTIPLICADOR_NORMAL = 1.0f;
	private static float MULTIPLICADOR_DIFICIL = 1.5f;
	
	private float multiplicadorActual;

    public Multiplicador(String dificultad) {
        establecerDificultad(dificultad);
    }

    private void establecerDificultad(String dificultad) {
        String dificultadNormalizada = dificultad.toLowerCase().replace("í", "i");

        switch (dificultadNormalizada) {
            case "facil":
                this.multiplicadorActual = MULTIPLICADOR_FACIL;
                break;
            case "dificil":
                this.multiplicadorActual = MULTIPLICADOR_DIFICIL;
                break;
            case "normal":
            default:
                this.multiplicadorActual = MULTIPLICADOR_NORMAL;
                break;
        }
    }


    public float getMultiplicador() {
        return multiplicadorActual;
    }
}

