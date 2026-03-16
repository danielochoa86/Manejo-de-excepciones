package model;

import exceptions.MaterialNoDisponibleException;

public class Revista extends Material implements Prestable{

    private String numero;

    public Revista(int id, String titulo, String numero) {
        super(id, titulo);
        this.numero = numero;
    }

    public void prestar() throws MaterialNoDisponibleException {
        if (estaPrestado()){
            throw new MaterialNoDisponibleException("La revista ya está prestada.");
        }
        setPrestado(true);
    }

    public void devolver() {
        setPrestado(false);
    }

    public String toString(){
        return "Revista: " +
                "\nTitulo: " + titulo +
                "\nNúmero: " + numero;
    }
}
