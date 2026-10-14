package org.iesalandalus.programacion.psp01.tareacomunicacionprocesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Escritor {

    public static void main(String[] args) {

        // Leemos el número recibido por la entrada estándar
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);

        try {
            // Leemos la línea enviada por el proceso Lector
            String linea = br.readLine();
            int numero = Integer.parseInt(linea.trim());

            // Mostramos los 10 números siguientes al recibido
            for (int i = 1; i <= 10; i++) {
                System.out.println(numero + i);
            }

            // Cerramos el flujo de entrada
            br.close();

        } catch (IOException e) {
            System.out.println("Error al leer el número: " + e.getMessage());
        }
    }
}