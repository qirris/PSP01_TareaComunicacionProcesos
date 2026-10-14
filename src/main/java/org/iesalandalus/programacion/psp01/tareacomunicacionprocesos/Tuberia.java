package org.iesalandalus.programacion.psp01.tareacomunicacionprocesos;

import java.io.*;

public class Tuberia {

    public static void main(String[] args) {

        try {
            // Creamos el primer proceso hijo que ejecuta el comando DIR
            ProcessBuilder pb1 = new ProcessBuilder("CMD", "/C", "DIR");

            // Creamos el segundo proceso hijo que ejecuta el comando FINDSTR
            ProcessBuilder pb2 = new ProcessBuilder("CMD", "/C", "FINDSTR", "java");

            // Iniciamos ambos procesos hijos
            Process proceso1 = pb1.start();
            Process proceso2 = pb2.start();

            // Leemos la salida del primer proceso
            BufferedReader lectorProceso1 = new BufferedReader(
                    new InputStreamReader(proceso1.getInputStream()));

            // Escribimos la salida del primer proceso en la entrada del segundo
            BufferedWriter escritorProceso2 = new BufferedWriter(
                    new OutputStreamWriter(proceso2.getOutputStream()));

            String linea;
            while ((linea = lectorProceso1.readLine()) != null) {
                escritorProceso2.write(linea);
                escritorProceso2.newLine();
            }

            // Cerramos los flujos
            lectorProceso1.close();
            escritorProceso2.flush();
            escritorProceso2.close();

            // Esperamos a que termine el primer proceso
            proceso1.waitFor();

            // Leemos y mostramos la salida del segundo proceso
            BufferedReader lectorProceso2 = new BufferedReader(
                    new InputStreamReader(proceso2.getInputStream()));

            while ((linea = lectorProceso2.readLine()) != null) {
                System.out.println(linea);
            }

            // Cerramos el lector
            lectorProceso2.close();

            // Capturamos posibles errores del segundo proceso
            BufferedReader brError = new BufferedReader(
                    new InputStreamReader(proceso2.getErrorStream()));

            while ((linea = brError.readLine()) != null) {
                System.out.println("ERROR: " + linea);
            }

            brError.close();

            // Esperamos a que termine el segundo proceso
            proceso2.waitFor();

            // Mensaje final obligatorio
            System.out.println("Finalizada la ejecución");

        } catch (IOException e) {
            System.out.println("Error al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}