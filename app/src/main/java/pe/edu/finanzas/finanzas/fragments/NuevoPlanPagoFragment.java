package pe.edu.finanzas.finanzas.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import pe.edu.finanzas.finanzas.R;
import pe.edu.finanzas.finanzas.entities.Metodo;
import pe.edu.finanzas.finanzas.entities.TipoPlanPago;
import pe.edu.finanzas.finanzas.libraries.Funciones;

/**
 * Created by francisco on 6/19/15.
 */
public class NuevoPlanPagoFragment extends Fragment {

    private List<TipoPlanPago> lstTipoPlanPago;
    private List<Metodo> lstMetodo;

    public TextView txtNombre;
    public Spinner spTipo;
    public TextView txtFecInicio;
    public Spinner spFecPago;
    public TextView txtTasaDescuento;
    public ImageView ivTasaDescuento;
    public TextView txtPorcEstructuracion;
    public ImageView ivPorcEstructuracion;
    public TextView txtColocacion;
    public ImageView ivColocacion;
    public TextView txtFlotacion;
    public ImageView ivFlotacion;
    public TextView txtCAVALI;
    public ImageView ivCAVALI;
    public TextView txtInflacion;
    public ImageView ivInflacion;
    public TextView txtTEP;
    public ImageView ivTEP;
    public TextView txtPGracia;
    public ImageView ivPGracia;
    public Button btnGuardar;

    private DatePickerDialog fecInicioPickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nuevoplanpago, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtNombre = (TextView)getView().findViewById(R.id.txtNombre);
        spTipo = (Spinner)getView().findViewById(R.id.spTipo);
        txtFecInicio = (TextView)getView().findViewById(R.id.txtFecInicio);
        spFecPago = (Spinner)getView().findViewById(R.id.spFecPago);
        txtTasaDescuento = (TextView)getView().findViewById(R.id.txtTasaDescuento);
        ivTasaDescuento = (ImageView)getView().findViewById(R.id.ivTasaDescuento);
        txtPorcEstructuracion = (TextView)getView().findViewById(R.id.txtPorcEstructuracion);
        ivPorcEstructuracion = (ImageView)getView().findViewById(R.id.ivPorcEstructuracion);
        txtColocacion = (TextView)getView().findViewById(R.id.txtColocacion);
        ivColocacion = (ImageView)getView().findViewById(R.id.ivColocacion);
        txtFlotacion = (TextView)getView().findViewById(R.id.txtFlotacion);
        ivFlotacion = (ImageView)getView().findViewById(R.id.ivFlotacion);
        txtCAVALI = (TextView)getView().findViewById(R.id.txtCAVALI);
        ivCAVALI = (ImageView)getView().findViewById(R.id.ivCAVALI);
        txtInflacion = (TextView)getView().findViewById(R.id.txtInflacion);
        ivInflacion = (ImageView)getView().findViewById(R.id.ivInflacion);
        txtTEP = (TextView)getView().findViewById(R.id.txtTEP);
        ivTEP = (ImageView)getView().findViewById(R.id.ivTEP);
        txtPGracia = (TextView)getView().findViewById(R.id.txtPGracia);
        ivPGracia = (ImageView)getView().findViewById(R.id.ivPGracia);
        btnGuardar = (Button)getView().findViewById(R.id.btnGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        setDateTimeField();

        lstTipoPlanPago = Funciones.SEL_TipoPlanPago(getActivity());
        lstMetodo = Funciones.SEL_Metodo(getActivity());

        List<String> lstTipo = new ArrayList<>();
        lstTipo.add("-= Seleccione =-");
        for(int i=0;i<lstTipoPlanPago.size();i++){
            lstTipo.add(lstTipoPlanPago.get(i).Nombre);
        }
        List<String> lstFrecuencia = new ArrayList<>();
        lstFrecuencia.add("-= Seleccione =-");
        lstFrecuencia.add("Diario");
        lstFrecuencia.add("Mensual");
        lstFrecuencia.add("Bimestral");
        lstFrecuencia.add("Trimestral");
        lstFrecuencia.add("Cuatrimestral");
        lstFrecuencia.add("Anual");

        ArrayAdapter<String> adapterTipo = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, lstTipo);
        ArrayAdapter<String> adapterFrecuencia = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, lstFrecuencia);

        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterFrecuencia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spTipo.setAdapter(adapterTipo);
        spFecPago.setAdapter(adapterFrecuencia);

    }
    private void setDateTimeField() {
        txtFecInicio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v == txtFecInicio && !fecInicioPickerDialog.isShowing())
                    fecInicioPickerDialog.show();
                return false;
            }
        });
        txtFecInicio.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (v==txtFecInicio && !fecInicioPickerDialog.isShowing()) fecInicioPickerDialog.show();
                return false;
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        fecInicioPickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                txtFecInicio.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
