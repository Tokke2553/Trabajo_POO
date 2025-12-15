package paquete;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Logs {

    private static final int MAX_LOGS = 2450;

    private final List <String> registros;
    private final List <String> killLogs;

    private static Logs instancia;

    private Logs() {
    	
        this.registros = new ArrayList<>();
        this.killLogs = new ArrayList<>();
    }

    public static Logs getInstance() {
    	
        if (instancia == null) {
            instancia = new Logs();
        }
        
        return instancia;
    }

    public void agregarLog(String log) {
        
    	if (registros.size() >= MAX_LOGS) {
            registros.remove(0);
        }
        
    	registros.add(log);
    }

    public String obtenerLog(int index) {
    
    	if (index >= 0 && index < registros.size()) {
            return registros.get(index);
        }
    	
        return null;
    
    }

    public List <String> obtenerTodosLosLogs() {
        return new ArrayList<>(registros);
    }

    public void limpiarLogs() {
        registros.clear();
    }

    public int obtenerCantidadDeLogs() {
        return registros.size();
    }

    public boolean estaVacio() {
        return registros.isEmpty();
    }

    public String obtenerUltimoLog() {
    
    	if (!registros.isEmpty()) {
            return registros.get(registros.size() - 1);
        }
        
    	return null;
    }

    public void guardarLogsEnArchivo(String fileName) {
        
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
        
    		for (String log : registros) {
                writer.write(log);
                writer.newLine();
            }
        
    	} catch (IOException e) {
        
    		System.err.println("Error al guardar los logs en el archivo: " + e.getMessage());
        }
    }

    
    public void agregarHistorial(String log) {
    
    	System.out.println("Intentando agregar log a killLogs: " + log);
        
    	if (killLogs.size() >= MAX_LOGS) {
        
    		System.out.println("Se ha alcanzado el límite de logs, eliminando el más antiguo...");
            killLogs.remove(0);
        }
        
    	killLogs.add(log);
        System.out.println("Log añadido exitosamente. killLogs ahora tiene " + killLogs.size() + " elementos.");
    
    }

    public void agregarLogs(String log) {
        agregarLog(log);
        guardarLogsEnArchivo("logs_partida.txt");
    }

    public List<String> getKillLogs() {
        return new ArrayList<>(killLogs);
    }


}
