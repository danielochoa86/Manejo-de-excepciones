package model;

import exceptions.MaterialNoDisponibleException;

public interface Prestable {

    boolean estaPrestado();
    void prestar() throws MaterialNoDisponibleException;
    void devolver();


}
