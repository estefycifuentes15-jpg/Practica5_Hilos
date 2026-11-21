package practica5;

import java.util.Random;

public class Auto {
    private String marca;
    private String placa;
    private double lambda;
    private double o2;
    private double n2o;
    private double co2;

    public Auto(String marca, String placa) {
        this.marca = marca;
        this.placa = placa;
        generarGases();
    }

    private void generarGases() {
        Random r = new Random();
        this.lambda = 0.7 + (r.nextDouble() * 0.9);
        this.o2 = r.nextDouble() * 4.0;
        this.n2o = r.nextDouble() * 65.0;
        this.co2 = r.nextDouble() * 25.0;
    }

    public boolean fallaLambda() {
        return lambda > 1.0;
    }

    public boolean fallaO2() {
        return o2 >= 2.0;
    }

    public boolean fallaN2O() {
        return n2o >= 40.0;
    }

    public boolean fallaCO2() {
        return co2 >= 15.0;
    }

    public int totalFallas() {
        int f = 0;
        if (fallaLambda()) f++;
        if (fallaO2()) f++;
        if (fallaN2O()) f++;
        if (fallaCO2()) f++;
        return f;
    }

    public String getMarca() {
        return marca;
    }

    public String getPlaca() {
        return placa;
    }

    public double getLambda() {
        return lambda;
    }

    public double getO2() {
        return o2;
    }

    public double getN2o() {
        return n2o;
    }

    public double getCo2() {
        return co2;
    }
}