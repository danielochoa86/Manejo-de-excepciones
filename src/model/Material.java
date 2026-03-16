package model;

public abstract class Material {
    protected int id;
    protected String titulo;
    protected boolean prestado;

    //constructor
    public Material(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.prestado = false;
    }
    //getters
    public int getId() {return id;}
    public String getTitulo() {return titulo;}
    public boolean estaPrestado() {return prestado;}

    //setter
    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }


}
