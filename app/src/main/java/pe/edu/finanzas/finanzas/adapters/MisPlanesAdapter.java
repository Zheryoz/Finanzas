package pe.edu.finanzas.finanzas.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import pe.edu.finanzas.finanzas.R;
import pe.edu.finanzas.finanzas.entities.PlanPago;
import pe.edu.finanzas.finanzas.viewholders.MisPlanesViewHolder;

/**
 * Created by francisco on 6/19/15.
 */
public class MisPlanesAdapter extends RecyclerView.Adapter<MisPlanesViewHolder> {

    private List<PlanPago> lstPlanes;

    public MisPlanesAdapter(List<PlanPago> lstPlanes) {
        this.lstPlanes = lstPlanes;
    }

    @Override
    public int getItemCount() {
        return lstPlanes.size();
    }

    @Override
    public void onBindViewHolder(MisPlanesViewHolder viewHolder, int i) {
        PlanPago plan = lstPlanes.get(i);
        viewHolder.txtNombre.setText(plan.Nombre);
        viewHolder.txtTipo.setText(String.valueOf(plan.TipoPlanPagoId));
        viewHolder.txtMetodo.setText(String.valueOf(plan.MetodoId));
        viewHolder.ivTipo.setImageResource((plan.TipoPlanPagoId == 1 ? R.drawable.bono_corporativo:R.drawable.bono_vac));
    }

    @Override
    public MisPlanesViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_misplanes, viewGroup, false);
        return new MisPlanesViewHolder(itemView);
    }
}