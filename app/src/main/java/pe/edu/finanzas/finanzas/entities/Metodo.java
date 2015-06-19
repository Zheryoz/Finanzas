package pe.edu.finanzas.finanzas.entities;

/**
 * Created by francisco on 6/19/15.
 */
public class Metodo {

    public int MetodoId;
    public String Nombre;
    public String Descripcion;

    public Metodo(int id, String nombre, String descripcion){
        this.MetodoId = id;
        this.Nombre = nombre;
        this.Descripcion = descripcion;
    }
}
