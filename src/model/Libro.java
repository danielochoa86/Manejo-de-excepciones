package model;

import exceptions.MaterialNoDisponibleException;

public class Libro extends Material implements Prestable{
    private String autor;
    private String isbn;

    public Libro(int id, String titulo, String autor, String isbn) {
        super(id, titulo);
        this.autor = autor;
        this.isbn = isbn;
    }

    public void prestar() throws MaterialNoDisponibleException {
        if (estaPrestado()){
            throw new MaterialNoDisponibleException("El libro ya está prestado");
        }
        setPrestado(true);
    }

    public void devolver(){
        setPrestado(false);
    }

    public String toString(){
        return "Libro: " +
                "\nTitulo: " + titulo +
                "\nAutor: " + autor +
                "\nISBN: " + isbn;
    }

}
