package practica5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CargaArchivos implements Runnable {
    private String archivoMecanicos;
    private String archivoVehiculos;
    private List<Mecanico> mecanicos;

    public CargaArchivos(String archivoMecanicos, String archivoVehiculos) {
        this.archivoMecanicos = archivoMecanicos;
        this.archivoVehiculos = archivoVehiculos;
        this.mecanicos = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            int demora = new Random().nextInt(3001);
            System.out.println("Cargando datos del servidor... espera: "
                    + String.format("%.2f", demora / 1000.0) + " s");
            Thread.sleep(demora);

            List<Auto> autos = leerAutos(archivoVehiculos);
            List<Mecanico> listaMecanicos = leerMecanicos(archivoMecanicos);

            for (Mecanico m : listaMecanicos) {
                for (Auto a : autos) {
                    if (a.getMarca().equalsIgnoreCase(m.getMarca()) && m.getAutos().size() < 5) {
                        m.agregarAuto(a);
                    }
                }
            }

            this.mecanicos = listaMecanicos;
            System.out.println("Datos cargados.\n");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Carga interrumpida: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error leyendo archivos: " + e.getMessage());
        }
    }

    private List<Mecanico> leerMecanicos(String ruta) throws IOException {
        List<Mecanico> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length >= 2) {
                    lista.add(new Mecanico(d[0].trim(), d[1].trim()));
                }
            }
        }
        return lista;
    }

    private List<Auto> leerAutos(String ruta) throws IOException {
        List<Auto> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length >= 2) {
                    lista.add(new Auto(d[0].trim(), d[1].trim()));
                }
            }
        }
        return lista;
    }

    public List<Mecanico> getMecanicos() {
        return mecanicos;
    }
}