package Paquete;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Singleton para el manejo centralizado de logs.
 * Incluye un log global, un log de "kills" (historial) 
 * y logs específicos indexados por Ubicación (ciudades/zonas del mapa).
 */
public class Logs {
    
    // Límite de registros para evitar consumo excesivo de memoria
    private static final int MAX_LOGS = 2450; 
    
    // Log global de eventos
    private final List<String> registros; 
    
    // Arreglo de listas para logs específicos por ubicación del mapa
    private final List<String>[] logsPorUbicacion; 
    
    // Log específico para registros de muertes o historial de juego
    private final List<String> killLogs; 

    // Instancia única del Singleton
    private static Logs instancia;

    // Constructor privado para el patrón Singleton
    @SuppressWarnings("unchecked")
    private Logs() {
        this.registros = new ArrayList<>();
        this.killLogs = new ArrayList<>();
        
        // Inicializa el arreglo de listas basado en la cantidad de ubicaciones en el enum
        this.logsPorUbicacion = new ArrayList[Ubicaciones.values().length]; 

        // Inicializa cada lista dentro del arreglo
        for (int i = 0; i < logsPorUbicacion.length; i++) {
            logsPorUbicacion[i] = new ArrayList<>();
        }
    }

    /**
     * Obtiene la única instancia de la clase Logs (Singleton).
     * @return La instancia de Logs.
     */
    public static Logs getInstance() {
        if (instancia == null) {
            instancia = new Logs();
        }
        return instancia;
    }

    // ===========================================
    // MÉTODOS PARA EL LOG GLOBAL
    // ===========================================

    /**
     * Agrega un log al registro global, eliminando el más antiguo si se excede el límite.
     * @param log La cadena de registro a añadir.
     */
    public void agregarLog(String log) {
        if (registros.size() >= MAX_LOGS) {
            registros.remove(0); // Elimina el más antiguo (FIFO)
        }
        registros.add(log);
    }

    /**
     * Obtiene un log específico por índice del registro global.
     * @param index El índice del log.
     * @return La cadena de log o null si el índice es inválido.
     */
    public String obtenerLog(int index) {
        if (index >= 0 && index < registros.size()) {
            return registros.get(index);
        }
        return null;
    }

    /**
     * Devuelve una copia de todos los logs globales.
     * @return Una lista de cadenas con todos los logs.
     */
    public List<String> obtenerTodosLosLogs() {
        return new ArrayList<>(registros);
    }

    /**
     * Limpia completamente el registro global de logs.
     */
    public void limpiarLogs() {
        registros.clear();
    }

    /**
     * Devuelve la cantidad de logs en el registro global.
     * @return El número de logs.
     */
    public int obtenerCantidadDeLogs() {
        return registros.size();
    }

    /**
     * Verifica si el registro global está vacío.
     * @return true si está vacío, false en caso contrario.
     */
    public boolean estaVacio() {
        return registros.isEmpty();
    }

    /**
     * Obtiene el log más reciente del registro global.
     * @return La cadena del último log o null si está vacío.
     */
    public String obtenerUltimoLog() {
        if (!registros.isEmpty()) {
            return registros.get(registros.size() - 1);
        }
        return null;
    }
        
    /**
     * Guarda el log global en un archivo, sobrescribiendo el contenido anterior.
     * @param fileName El nombre del archivo de destino.
     */
    public void guardarLogsEnArchivo(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) { 
            for (String log : registros) {
                writer.write(log);
                writer.newLine();
            }
            System.out.println("Logs guardados en el archivo: " + fileName);
        } catch (IOException e) {
            System.err.println("Error al guardar los logs en el archivo: " + e.getMessage());
        }
    }

    // ===========================================
    // MÉTODOS PARA LOGS POR UBICACIÓN
    // ===========================================

    /**
     * Agrega un log al registro específico de una ubicación.
     * @param log La cadena de registro a añadir.
     * @param ubicacion La ubicación (enum) a la que pertenece el log.
     */
    public void agregarLogPorUbicacion(String log, Ubicaciones ubicacion) {
        int index = ubicacion.ordinal(); 
        List<String> logs = logsPorUbicacion[index];
        if (logs.size() >= MAX_LOGS) {
            logs.remove(0); 
        }
        logs.add(log);
    }

    /**
     * Obtiene una copia de todos los logs asociados a una ubicación específica.
     * @param ubicacion La ubicación (enum) de la que se quieren obtener los logs.
     * @return Una lista de cadenas con los logs de esa ubicación.
     */
    public List<String> obtenerLogsPorUbicacion(Ubicaciones ubicacion) {
        int index = ubicacion.ordinal(); 
        return new ArrayList<>(logsPorUbicacion[index]); 
    }

    /**
     * Limpia el registro de logs de una ubicación específica.
     * @param ubicacion La ubicación (enum) a limpiar.
     */
    public void limpiarLogsPorUbicacion(Ubicaciones ubicacion) {
        int index = ubicacion.ordinal(); 
        logsPorUbicacion[index].clear();
    }
        
    /**
     * Limpia los logs de todas las ubicaciones.
     */
    public void eliminarLogsporUbicacion() {
        for(int i = 0; i < logsPorUbicacion.length; i++) {
            logsPorUbicacion[i].clear();
        }
    }

    /**
     * Obtiene la cantidad de logs registrados para una ubicación.
     * @param ubicacion La ubicación (enum) a consultar.
     * @return La cantidad de logs.
     */
    public int obtenerCantidadDeLogsPorUbicacion(Ubicaciones ubicacion) {
        int index = ubicacion.ordinal(); 
        return logsPorUbicacion[index].size();
    }

    /**
     * Obtiene el log más reciente de una ubicación específica.
     * @param ubicacion La ubicación (enum) a consultar.
     * @return La cadena del último log o null si está vacío.
     */
    public String obtenerUltimoLogPorUbicacion(Ubicaciones ubicacion) {
        int index = ubicacion.ordinal(); 
        List<String> logs = logsPorUbicacion[index];
        if (!logs.isEmpty()) {
            return logs.get(logs.size() - 1);
        }
        return null;
    }

    // ===========================================
    // MÉTODOS PARA HISTORIAL (KILL LOGS)
    // ===========================================
    
    /**
     * Agrega un log al historial de muertes (killLogs), eliminando el más antiguo si se excede el límite.
     * @param log La cadena de registro de muerte a añadir.
     */
    public void agregarHistorial(String log) {
        System.out.println("Intentando agregar log a killLogs: " + log);
        if (killLogs.size() >= MAX_LOGS) {
            System.out.println("Se ha alcanzado el límite de logs, eliminando el más antiguo...");
            killLogs.remove(0);
        }
        killLogs.add(log);
        System.out.println("Log añadido exitosamente. killLogs ahora tiene " + killLogs.size() + " elementos.");
    }

    /**
     * Devuelve una copia de todos los logs del historial (killLogs).
     * @return Una lista de cadenas con los logs de muertes.
     */
    public List<String> getKillLogs() {
        return new ArrayList<>(killLogs);
    }
}