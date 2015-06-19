package pe.edu.finanzas.finanzas.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pe.edu.finanzas.finanzas.R;

/**
 * Created by francisco on 6/19/15.
 */
public class MisPlanesViewHolder extends RecyclerView.ViewHolder {
    public TextView txtNombre;
    public TextView txtTipo;
    public TextView txtMetodo;
    public ImageView ivTipo;

    public MisPlanesViewHolder(View v) {
        super(v);
        txtNombre =  (TextView) v.findViewById(R.id.txtNombre);
        txtTipo = (TextView)  v.findViewById(R.id.txtTipo);
        txtMetodo = (TextView)  v.findViewById(R.id.txtMetodo);
        ivTipo = (ImageView) v.findViewById(R.id.ivTipo);
    }
}