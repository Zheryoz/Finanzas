package pe.edu.finanzas.finanzas.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.edu.finanzas.finanzas.R;
import pe.edu.finanzas.finanzas.adapters.MisPlanesAdapter;
import pe.edu.finanzas.finanzas.entities.PlanPago;
import pe.edu.finanzas.finanzas.libraries.RecyclerItemClickListener;

/**
 * Created by francisco on 6/19/15.
 */
public class MisPlanesFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener{

    private Button btnNuevoPlanPago;
    private RecyclerView rvMisPlanes;

    public MisPlanesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_misplanes, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnNuevoPlanPago = (Button)getView().findViewById(R.id.btnNuevoPlanPago);
        btnNuevoPlanPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AQUI CARGAMOS EL FRAGMENT!!!
            }
        });
        rvMisPlanes = (RecyclerView)getView().findViewById(R.id.rvMisPlanes);
        rvMisPlanes.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMisPlanes.setLayoutManager(llm);

        List<PlanPago> lstMisPlanes = new ArrayList<>();
        lstMisPlanes.add(new PlanPago(1,"PLAN PAGO TEST 1",1,1,1));
        lstMisPlanes.add(new PlanPago(1,"PLAN PAGO TEST 2",2,2,1));
        MisPlanesAdapter adapter = new MisPlanesAdapter(lstMisPlanes);

        rvMisPlanes.setAdapter(adapter);

        rvMisPlanes.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));
    }

    @Override
    public void onItemClick(View childView, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("¡PLAN DE PAGO SELECCIONADO!");

        builder.setMessage("¿Que desea hacer?");
        builder.setPositiveButton("Ver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(),"SELECCIONO VER",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(),"SELECCIONO EDITAR",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    @Override
    public void onItemLongPress(View childView, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("¡PLAN DE PAGO SELECCIONADO!");

        builder.setMessage("¿Está seguro(a) que desea eliminar el plan de pago?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(), "SELECCIONO ELIMINAR", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getActivity(),"SELECCIONO CANCELAR",Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}