package practica5;

import java.util.ArrayList;
import java.util.List;

public class Mecanico {
    private String nombre;
    private String marca;
    private List<Auto> autos;

    public Mecanico(String nombre, String marca) {
        this.nombre = nombre;
        this.marca = marca;
        this.autos = new ArrayList<>();
    }

    public void agregarAuto(Auto a) {
        if (autos.size() < 5) {
            autos.add(a);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public List<Auto> getAutos() {
        return autos;
    }
}