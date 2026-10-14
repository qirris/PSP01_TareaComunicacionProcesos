package org.iesalandalus.programacion.psp01.tareacomunicacionprocesos;

import org.iesalandalus.programacion.utilidades.Entrada;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Lector {

    public static void main(String[] args) {

        // Pedimos el número al usuario por teclado
        System.out.print("Introduce un número entero: ");
        int numero = Entrada.entero();

        try {
            // Creamos el proceso hijo que ejecutará la clase Escritor
            ProcessBuilder pb = new ProcessBuilder(
                    "java",
                    "-cp", System.getProperty("java.class.path"),
                    "org.iesalandalus.programacion.psp01.tareacomunicacionprocesos.Escritor");

            // Establecemos el directorio de trabajo
            pb.directory(new File(System.getProperty("user.dir")));

            // Iniciamos el proceso hijo
            Process proceso = pb.start();

            // Enviamos el número al proceso hijo por su entrada estándar
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(proceso.getOutputStream()));

            bw.write(String.valueOf(numero));
            bw.newLine();
            bw.flush();
            bw.close();

            // Leemos la salida del proceso hijo y la mostramos por pantalla
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()));

            System.out.println("Los 10 números siguientes son:");
            String linea;
            while ((linea = lector.readLine()) != null) {
                System.out.println(linea);
            }

            // Cerramos el lector
            lector.close();

            // Capturamos posibles errores del proceso hijo
            BufferedReader brError = new BufferedReader(
                    new InputStreamReader(proceso.getErrorStream()));

            while ((linea = brError.readLine()) != null) {
                System.out.println("ERROR: " + linea);
            }

            brError.close();

            // Esperamos a que termine el proceso hijo
            int codigoSalida = proceso.waitFor();
            System.out.println("El proceso hijo terminó con código: " + codigoSalida);

        } catch (IOException e) {
            System.out.println("Error al ejecutar el proceso: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("El proceso fue interrumpido: " + e.getMessage());
        }
    }
}