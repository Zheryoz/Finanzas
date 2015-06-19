package pe.edu.finanzas.finanzas.entities;

/**
 * Created by francisco on 6/19/15.
 */
public class PlanPago {

    public int PlanPagoId;
    public String Nombre;
    public int TipoPlanPagoId;
    public int MetodoId;
    public int UsuarioId;

    public PlanPago(int id, String nombre, int tipo, int metodo, int usuario){
        this.PlanPagoId = id;
        this.Nombre = nombre;
        this.TipoPlanPagoId = tipo;
        this.MetodoId = metodo;
        this.UsuarioId = usuario;
    }
}
