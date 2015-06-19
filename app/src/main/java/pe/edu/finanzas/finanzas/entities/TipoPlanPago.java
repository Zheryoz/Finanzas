package pe.edu.finanzas.finanzas.entities;

/**
 * Created by francisco on 6/19/15.
 */
public class TipoPlanPago {

    public int TipoPlanPagoId;
    public String Nombre;

    public TipoPlanPago(int id, String nombre){
        this.TipoPlanPagoId = id;
        this.Nombre = nombre;
    }
}
