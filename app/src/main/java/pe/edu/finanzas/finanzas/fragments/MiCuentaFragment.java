package pe.edu.finanzas.finanzas.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pe.edu.finanzas.finanzas.R;
import pe.edu.finanzas.finanzas.entities.Usuario;
import pe.edu.finanzas.finanzas.libraries.Funciones;

/**
 * Created by francisco on 6/19/15.
 */
public class MiCuentaFragment extends Fragment {

    private EditText txtNombre;
    private TextView txtCorreo;
    private EditText txtClave;
    private Button btnGuardar;
    private Button btnSincronizar;

    public MiCuentaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_micuenta, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Usuario user = Funciones.ObtenerUsuarioLogueado(getActivity());

        txtNombre = (EditText)getView().findViewById(R.id.txtNombre);
        txtCorreo = (TextView)getView().findViewById(R.id.txtCorreo);
        if(user!=null) {
            txtNombre.setText(user.Nombre);
            txtCorreo.setText(user.Email);
        }
        txtClave = (EditText)getView().findViewById(R.id.txtClave);
        txtClave.setText("----------------");
        btnGuardar = (Button)getView().findViewById(R.id.btnGuardar);
        btnGuardar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        btnSincronizar = (Button)getView().findViewById(R.id.btnSincronizar);
        btnSincronizar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }
}
