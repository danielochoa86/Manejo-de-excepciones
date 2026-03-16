package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nombre;
    private List<Material> prestamos;

    public Usuario(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.prestamos = new ArrayList<>();
    }

    //getters
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public List<Material> getPrestamos() { return List.copyOf(prestamos); }

    public Material buscarMaterial(int id){
        for (Material m : prestamos){
            if (m.getId() == id){
                return m;
            }
        }
        return null;
    }

    //métodos
    public int cantidadPrestamos(){return prestamos.size();}

    public void prestarMaterial(Material material){
        prestamos.add(material);
    }
    public void devolverMaterial(Material material){
        prestamos.remove(material);
    }

}
