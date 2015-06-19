package pe.edu.finanzas.finanzas.entities;

/**
 * Created by francisco on 6/19/15.
 */
public class Usuario {

    public int UsuarioId;
    public String Email;
    public String Nombre;
    public int RolId;

    public Usuario(int id, String email, String nombre, int rol){
        this.UsuarioId = id;
        this.Email = email;
        this.Nombre = nombre;
        this.RolId = rol;
    }
}
