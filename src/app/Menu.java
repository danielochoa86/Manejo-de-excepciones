package app;

import exceptions.BibliotecaException;
import exceptions.MaterialNoDisponibleException;
import exceptions.MaterialNoEncontradoException;
import model.*;
import servicio.Biblioteca;
import util.registroErrores;

import java.util.List;
import java.util.Scanner;

public class Menu {

    private Biblioteca biblioteca;
    private Scanner sc;
    private int user_counter;
    private int m_counter;

    public Menu(Biblioteca biblioteca) {
        this.sc = new Scanner(System.in);
        this.biblioteca = biblioteca;
        user_counter = 0;
        m_counter = 0;
    }

    public void ejecutar(){

        int opcion = 0;
        while (opcion != 5){
            System.out.println("\n===== SISTEMA BIBLIOTECA =====");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Registrar material");
            System.out.println("3. Prestar material");
            System.out.println("4. Devolver material");
            System.out.println("5. Salir");

            System.out.println("\nSeleccione una opción:");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion){
                case 1 -> registrarUsuarioUI();
                case 2 -> registrarMaterialUI();
                case 3 -> prestarMaterialUI();
                case 4 -> devolverMaterialUI();
                case 5 -> {
                    System.out.println("Saliendo del sistema...");
                    return;
                }
                default -> opcionInvalida();
            }

        }

    }

    //opciones de registro
    public void registrarUsuarioUI(){
        System.out.println("\n===== REGISTRO USUARIO =====");
        String nombre = userInput("\nIngrese su nombre:");
        Usuario user = new Usuario(user_counter++,nombre);
        biblioteca.registrarUsuario(user);
        System.out.println("\nUsuario registrado: " + user.getNombre());
    }

    public void registrarMaterialUI(){
        System.out.println("\n===== REGISTRO MATERIAL =====");
        System.out.println("Tipo de material:");
        System.out.println("1. Libro");
        System.out.println("2. Revista");

        int seleccion = leerEnteroMensj("Ingrese su selección:");

        System.out.println("Ingrese la información que se le solicita");

        String titulo = userInput("Título:");

        switch (seleccion){
            case 1 -> {
                String Autor = userInput("Autor:");
                String ISBN = userInput("ISBN:");
                Libro libro = new Libro(m_counter++,titulo,Autor,ISBN);
                biblioteca.registrarMaterial(libro);
                System.out.println("¡Libro registrado!");
                System.out.println(libro);
            }
            case 2 -> {
                String num = userInput("Número de revista");
                Revista revista = new Revista(m_counter++,titulo,num);
                biblioteca.registrarMaterial(revista);
                System.out.println("¡Revista registrada!");
                System.out.println(revista);
            }
        }

    }

    //métodos biblioteca
    private void prestarMaterialUI(){
        System.out.println("===== Préstamo de Material =====");

        Usuario user = buscarUsuarioUI();
        if (user == null){return;}
        System.out.println("Usuario elegido:" + user.getNombre());
        List<Material> materials = biblioteca.listarMateriales();

        //evaluar que la lista no estén vacías
        if (materials.isEmpty()){
            System.out.println("No hay materiales registrados.");
            return;
        }

        try {
            //elegir el material
            System.out.println("Escoja un material por su índice:");

            for (int i = 0; i < materials.size(); i++){
                System.out.println((i+1) + " - " + materials.get(i).getTitulo() +
                        " " + materials.get(i).getClass().getSimpleName());
            }

            int material_id = leerEnteroMensj("Ingrese su seleccion");

            biblioteca.prestarMaterial(user.getId(),material_id-1);

        } catch (BibliotecaException e){
            System.out.println(e.getMessage());
            registroErrores.registrar(e);
        }
    }

    public void devolverMaterialUI(){
        System.out.println("===== Devolución de Material =====");
        Usuario user = buscarUsuarioUI();
        if (user == null){return;}

        System.out.println("Usuario elegido:" + user.getNombre());
        List<Material> materials = user.getPrestamos();
        if (materials == null || materials.isEmpty()){
            System.out.println(user.getNombre() + " no tiene materiales prestados.");
            return;
        }
        try {
            for (int i = 0;i<materials.size();i++){
                System.out.println((i+1) + " - " + materials.get(i).getTitulo() +
                        " - " + materials.get(i).getClass().getSimpleName());
            }
            int material_id = leerEnteroMensj("Ingrese su seleccion");
            Material material = user.buscarMaterial(material_id-1);

            if (material == null){
                throw new MaterialNoEncontradoException("Material no encontrado");
            }

            biblioteca.devolverMaterial(user.getId(),material_id-1);
            System.out.println(user.getNombre() + " ha devuelto " + material.getTitulo());
        } catch (BibliotecaException e){
            System.out.println(e.getMessage());
            registroErrores.registrar(e);
        }
    }

    //Métodos complementarios
    private Usuario buscarUsuarioUI(){
        List<Usuario> users = biblioteca.listarUsuarios();
        //evaluar que las listas no estén vacías
        if (users == null || users.isEmpty()){
            System.out.println("No hay usuarios registrados");
            return null;
        }
        //elegir usuario
        System.out.println("Escoja un usuario por su índice:");
        for (int i = 0; i < users.size(); i++){
            System.out.println((i+1) + " - " + users.get(i).getNombre());
        }
        int user_id = leerEnteroMensj("Ingrese su seleccion:");
        Usuario user = biblioteca.buscarUsuario(user_id-1);
        if (user == null){System.out.println("¡Usuario no encontrado!");}
        return user;
    }


    //lectores
    public String userInput(String p) {
        while (true) {
            System.out.println(p);
            String s = sc.nextLine();
            if (!s.isEmpty()){
                return s;
            } else
                System.out.println("Por favor, ingrese un valor.");
        }
    }

    public int leerEnteroMensj (String p){
        while (true) {
            System.out.print(p);

            try {
                String input = sc.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    public void opcionInvalida(){
        System.out.println("Opción inválida.");
    }



}
