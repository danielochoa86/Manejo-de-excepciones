package app;

import servicio.Biblioteca;

public class Main {

    public static void main(String[] args){

        Biblioteca biblioteca = new Biblioteca();
        Menu menu = new Menu(biblioteca);
        menu.ejecutar();
    }

}
