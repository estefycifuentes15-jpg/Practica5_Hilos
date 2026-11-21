package practica5;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CargaArchivos carga = new CargaArchivos("mecanicos.txt", "vehiculos.txt");
        Thread hiloCarga = new Thread(carga, "Hilo-Carga");
        hiloCarga.start();

        try {
            hiloCarga.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error al esperar carga: " + e.getMessage());
            return;
        }

        List<RevisionMecanico> hilos = new ArrayList<>();
        for (Mecanico m : carga.getMecanicos()) {
            RevisionMecanico h = new RevisionMecanico(m);
            hilos.add(h);
            h.start();
        }

        for (RevisionMecanico h : hilos) {
            try {
                h.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Error al esperar hilo: " + h.getName());
            }
        }
    }
}