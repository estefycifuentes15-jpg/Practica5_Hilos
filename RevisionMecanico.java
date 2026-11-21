package practica5;

import java.util.Locale;
import java.util.Random;

public class RevisionMecanico extends Thread {
    private Mecanico mecanico;
    private static final Object LOCK = new Object();

    public RevisionMecanico(Mecanico mecanico) {
        super("Hilo-" + mecanico.getNombre().replaceAll("\\s+", ""));
        this.mecanico = mecanico;
    }

    @Override
    public void run() {
        try {
            int demora = new Random().nextInt(3001);
            Thread.sleep(demora);

            int totalMediciones = mecanico.getAutos().size() * 4;
            int fallasLote = 0;
            boolean falloCritico = false;

            for (Auto a : mecanico.getAutos()) {
                fallasLote += a.totalFallas();
                if (a.totalFallas() >= 3) {
                    falloCritico = true;
                }
            }

            double porcentajeError = (totalMediciones > 0)
                    ? (fallasLote * 100.0) / totalMediciones
                    : 0.0;

            String resultado = (!falloCritico && porcentajeError <= 30.0)
                    ? "Pasa en Primera Revisión"
                    : "Segunda Revisión Asignada";

            StringBuilder tabla = new StringBuilder();
            String sep = "+------------+------------+-------------+-------------+-------------+-------------+--------+";
            tabla.append("========================================================================================\n");
            tabla.append(String.format("MECANICO: %-25s | MARCA: %-15s | HILO: %s%n",
                    mecanico.getNombre(), mecanico.getMarca(), getName()));
            tabla.append("========================================================================================\n");
            tabla.append(sep).append("\n");
            tabla.append("| Marca      | Placa      | Lambda(>1)  | O2(>=2)     | N2O(>=40)   | CO2(>=15)   | Fallas |\n");
            tabla.append(sep).append("\n");

            for (Auto a : mecanico.getAutos()) {
                tabla.append(String.format(Locale.US,
                        "| %-10s | %-10s | %6.2f (%s) | %6.2f (%s) | %6.2f (%s) | %6.2f (%s) | %2d |%n",
                        a.getMarca(),
                        a.getPlaca(),
                        a.getLambda(), a.fallaLambda() ? "FAIL" : "OK  ",
                        a.getO2(), a.fallaO2() ? "FAIL" : "OK  ",
                        a.getN2o(), a.fallaN2O() ? "FAIL" : "OK  ",
                        a.getCo2(), a.fallaCO2() ? "FAIL" : "OK  ",
                        a.totalFallas()
                ));
            }

            tabla.append(sep).append("\n");
            tabla.append(String.format(Locale.US,
                    "Fallas: %d/%d | %% de Error: %.2f%% | Estado: %s%n",
                    fallasLote, totalMediciones, porcentajeError, resultado));
            tabla.append("========================================================================================\n");

            synchronized (LOCK) {
                System.out.println(tabla.toString());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Hilo interrumpido: " + mecanico.getNombre());
        }
    }
}