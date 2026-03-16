package servicio;

import exceptions.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Material> materiales;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.materiales = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    //métodos de registro
    public void registrarMaterial(Material material){
        materiales.add(material);
    }

    public void registrarUsuario(Usuario usuario){
        usuarios.add(usuario);
    }



    //métodos de búsqueda
    public Material buscarMaterial(int id){
        for (Material m : materiales){
            if (m.getId() == id){
                return m;
            }
        }
        return null;
    }

    public Usuario buscarUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }

    //métodos de listado
    public List<Usuario> listarUsuarios(){return List.copyOf(usuarios);  }
    public List<Material> listarMateriales(){return List.copyOf(materiales);  }


    //Otros métodos especializado
    public void prestarMaterial(int usuarioId, int materialId)
        throws UsuarioNoEncontradoException,
            MaterialNoDisponibleException,
            MaterialNoEncontradoException,
            MaximoPrestamosException {
        Usuario usuario = buscarUsuario(usuarioId);
        if (usuario == null){
            throw new UsuarioNoEncontradoException("USuario no encontrado");
        }

        if (usuario.cantidadPrestamos() >= 3){
            throw new MaximoPrestamosException(
                    "El usuario ya alcanzó el máximo de préstamos permitidos (3)");
        }

        Material material = buscarMaterial(materialId);

        if (material == null){
            throw new MaterialNoEncontradoException("Material no encontrado");
        }

        if (material.estaPrestado()){
            throw new MaterialNoDisponibleException("Material ya prestado");
        }

        ((Prestable) material).prestar();

        usuario.prestarMaterial(material);
        System.out.println("Se ha prestado " + material.getTitulo() + " a "
        + usuario.getNombre());
    }

    public void devolverMaterial(int usuarioId, int materialId)
        throws UsuarioNoEncontradoException,
            MaterialNoDisponibleException,
            MaterialNoEncontradoException,
            MaximoPrestamosException {
        Usuario usuario = buscarUsuario(usuarioId);

        if (usuario == null){
            throw new UsuarioNoEncontradoException("Usuario no encontrado");
        }

        //busca el material entre los materiales prestados al usuario
        Material material = usuario.buscarMaterial(materialId);

        if (material == null){
            throw new MaterialNoEncontradoException("Material no encontrado");
        }

        if (!material.estaPrestado()){
            throw new MaterialNoDisponibleException("Material no está prestado");
        }

        ((Prestable) material).devolver();
        usuario.devolverMaterial(material);
    }
}
