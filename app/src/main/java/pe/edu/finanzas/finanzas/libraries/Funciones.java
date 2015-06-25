package pe.edu.finanzas.finanzas.libraries;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;

import com.tismart.tsmlibrary.rest.ConnectionUtil;
import com.tismart.tsmlibrary.rest.enums.ResponseCode;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.finanzas.finanzas.entities.Metodo;
import pe.edu.finanzas.finanzas.entities.PlanPago;
import pe.edu.finanzas.finanzas.entities.TipoPlanPago;
import pe.edu.finanzas.finanzas.entities.Usuario;
import pe.edu.finanzas.finanzas.restclient.FinanzasRestClient;
import pe.edu.finanzas.finanzas.restclient.RestCallback;

/**
 * Created by francisco on 6/24/15.
 */
public final class Funciones {

    public static void Sincronizar(final Context context){
        Funciones.DEL_All(context);
        SINCR_TipoPlanPago(context);
    }

    public static void SINCR_PlanPago(final Context context) {
        if(!ConnectionUtil.isNetworkAvailable(context)){
            Funciones.AlertaSinInternet(context);
            return;
        }
        try {
            FinanzasRestClient.getInstance().getAsync(context, "/PlanPago", "/ObtenerPorUsuario?usuarioId=" + String.valueOf(Funciones.ObtenerUsuarioLogueado(context).UsuarioId) + "&pagina=0", new RestCallback() {
                ProgressDialog progress;

                @Override
                public void OnStart() {
                    if (progress == null) {
                        progress = ProgressDialog.show(context, "Sincronizando...", "Descargando sus planes de pago.", true);
                        progress.setCancelable(false);
                        progress.setCanceledOnTouchOutside(false);
                    }
                }

                @Override
                public void OnResponse(ResponseCode var1, String var2) {
                    progress.dismiss();
                    try {
                        if(!var2.equals("")){
                            JSONArray result = new JSONArray(var2);
                            for(int i=0;i<result.length();i++){
                                JSONObject subResult = new JSONObject(result.get(i).toString());
                                PlanPago objINS = new PlanPago(subResult.getInt("PlanPagoId"),subResult.getString("Nombre"),subResult.getInt("TipoPlanPagoId"),subResult.getInt("MetodoId"),subResult.getInt("UsuarioId"));
                                Funciones.INS_PlanPago(context,objINS);
                            }
                        }
                        Funciones.AlertaSincrExitosa(context);
                    } catch (Exception ex) {
                        Funciones.AlertaSincrFallida(context);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void SINCR_Metodo(final Context context) {
        if(!ConnectionUtil.isNetworkAvailable(context)){
            Funciones.AlertaSinInternet(context);
            return;
        }
        try {
            FinanzasRestClient.getInstance().getAsync(context, "/Metodo", "/ObtenerTodos", new RestCallback() {
                ProgressDialog progress;

                @Override
                public void OnStart() {
                    if (progress == null) {
                        progress = ProgressDialog.show(context, "Sincronizando...", "Descargando metodos de pago disponibles.", true);
                        progress.setCancelable(false);
                        progress.setCanceledOnTouchOutside(false);
                    }
                }

                @Override
                public void OnResponse(ResponseCode var1, String var2) {
                    progress.dismiss();
                    if (var2.equals("")) {
                        Funciones.AlertaSincrFallida(context);
                    } else {
                        try {
                            JSONArray result = new JSONArray(var2);
                            for(int i=0;i<result.length();i++){
                                JSONObject subResult = new JSONObject(result.get(i).toString());
                                Metodo objINS = new Metodo(subResult.getInt("MetodoId"),subResult.getString("Nombre"),subResult.getString("Descripcion"));
                                Funciones.INS_Metodo(context,objINS);
                            }
                            Funciones.SINCR_PlanPago(context);
                        } catch (Exception ex) {
                            Funciones.AlertaSincrFallida(context);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void SINCR_TipoPlanPago(final Context context) {
        if(!ConnectionUtil.isNetworkAvailable(context)){
            Funciones.AlertaSinInternet(context);
            return;
        }
        try {
            FinanzasRestClient.getInstance().getAsync(context, "/TipoPlanPago", "/ObtenerTodos", new RestCallback() {
                ProgressDialog progress;
                @Override
                public void OnStart() {
                    if (progress == null) {
                        progress = ProgressDialog.show(context,"Sincronizando...", "Descargando tipos de plan de pago disponibles.", true);
                        progress.setCancelable(false);
                        progress.setCanceledOnTouchOutside(false);
                    }
                }

                @Override
                public void OnResponse(ResponseCode var1, String var2) {
                    progress.dismiss();
                    if(var2.equals("")) {
                        Funciones.AlertaSincrFallida(context);
                    }else {
                        try{
                            JSONArray result = new JSONArray(var2);
                            for(int i=0;i<result.length();i++){
                                JSONObject subResult = new JSONObject(result.get(i).toString());
                                TipoPlanPago objINS = new TipoPlanPago(subResult.getInt("TipoPlanPagoId"),subResult.getString("Nombre"));
                                Funciones.INS_TipoPlanPago(context,objINS);
                            }
                            Funciones.SINCR_Metodo(context);
                        }catch(Exception ex) {
                            Funciones.AlertaSincrFallida(context);
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void AlertaSinInternet(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Sin conexión!")
                .setMessage("Por favor, revise su conexión a internet.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public static void AlertaSincrFallida(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Sincronización Fallida!")
                .setMessage("No hemos podido sincronizar la información.")
                .setNegativeButton("INTENTAR LUEGO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public static void AlertaSincrExitosa(Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Sincronización Exitosa!")
                .setMessage("Se ha sincronizado exitosamente sus planes de pago.")
                .setNegativeButton("CONTINUAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static void DEL_All(Context context) {
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        myDB.myDataBase.execSQL("DELETE FROM PLANPAGO;");
        myDB.myDataBase.execSQL("DELETE FROM METODO;");
        myDB.myDataBase.execSQL("DELETE FROM TIPOPLANPAGO;");
    }

    public static void INS_PlanPago(Context context, PlanPago objINS) {
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        String query = "INSERT INTO PlanPago(PlanPagoId,Nombre,TipoPlanPagoId,MetodoId,UsuarioId) VALUES([{0}],'[{1}]',[{2}],[{3}],[{4}]);";
        query = query.replace("[{0}]",String.valueOf(objINS.PlanPagoId));
        query = query.replace("[{1}]",objINS.Nombre);
        query = query.replace("[{2}]",String.valueOf(objINS.TipoPlanPagoId));
        query = query.replace("[{3}]",String.valueOf(objINS.MetodoId));
        query = query.replace("[{4}]",String.valueOf(objINS.UsuarioId));
        myDB.myDataBase.execSQL(query);
    }
    public static List<PlanPago> SEL_PlanPago(Context context) {
        List<PlanPago> lstResult = new ArrayList<>();
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        Cursor resultSet = myDB.myDataBase.rawQuery("Select PlanPagoId,Nombre,TipoPlanPagoId,MetodoId,UsuarioId from PlanPago",null);
        resultSet.moveToFirst();
        while(resultSet.isAfterLast()) {
            PlanPago obj = new PlanPago(Integer.valueOf(resultSet.getString(0)),resultSet.getString(1),Integer.valueOf(resultSet.getString(2)),Integer.valueOf(resultSet.getString(3)),Integer.valueOf(resultSet.getString(4)));
            lstResult.add(obj);
            resultSet.moveToNext();
        }
        return lstResult;
    }

    public static void INS_Metodo(Context context, Metodo objINS) {
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        String query = "INSERT INTO Metodo(MetodoId,Nombre,Descripcion) VALUES([{0}],'[{1}]','[{2}]');";
        query = query.replace("[{0}]",String.valueOf(objINS.MetodoId));
        query = query.replace("[{1}]",objINS.Nombre);
        query = query.replace("[{2}]",objINS.Descripcion);
        myDB.myDataBase.execSQL(query);
    }
    public static List<Metodo> SEL_Metodo(Context context) {
        List<Metodo> lstResult = new ArrayList<>();
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        Cursor resultSet = myDB.myDataBase.rawQuery("Select MetodoId,Nombre,Descripcion from Metodo",null);
        resultSet.moveToFirst();
        while(resultSet.isAfterLast()) {
            Metodo obj = new Metodo(Integer.valueOf(resultSet.getString(0)),resultSet.getString(1),resultSet.getString(2));
            lstResult.add(obj);
            resultSet.moveToNext();
        }
        return lstResult;
    }

    public static void INS_TipoPlanPago(Context context, TipoPlanPago objINS) {
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        String query = "INSERT INTO TipoPlanPago(TipoPlanPagoId,Nombre) VALUES([{0}],'[{1}]');";
        query = query.replace("[{0}]",String.valueOf(objINS.TipoPlanPagoId));
        query = query.replace("[{1}]", objINS.Nombre);
        myDB.myDataBase.execSQL(query);
    }
    public static List<TipoPlanPago> SEL_TipoPlanPago(Context context) {
        List<TipoPlanPago> lstResult = new ArrayList<>();
        SQLiteHelper myDB = SQLiteHelper.getDatabase(context);
        Cursor resultSet = myDB.myDataBase.rawQuery("Select TipoPlanPagoId,Nombre from TipoPlanPago",null);
        resultSet.moveToFirst();
        while(resultSet.isAfterLast()) {
            TipoPlanPago obj = new TipoPlanPago(Integer.valueOf(resultSet.getString(0)),resultSet.getString(1));
            lstResult.add(obj);
            resultSet.moveToNext();
        }
        return lstResult;
    }

    public static void EliminarUsuarioLogueado(Context context){
        SharedPreferences pref = context.getSharedPreferences("LoginDATA", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("UsuarioId", null);
        editor.putString("Nombre", null);
        editor.putString("Email", null);
        editor.commit();
    }
    public static Usuario ObtenerUsuarioLogueado(Context context){
        SharedPreferences pref = context.getSharedPreferences("LoginDATA", 0);

        Usuario user = new Usuario();
        user.UsuarioId = Integer.valueOf(pref.getString("UsuarioId","-1"));
        user.Nombre = pref.getString("Nombre","");
        user.Email = pref.getString("Email","");

        if(user.UsuarioId==-1)
            return null;
        return user;
    }
    public static void GuardarLogin(Context context, JSONObject jObject) throws Exception{
        try{
            SharedPreferences pref = context.getSharedPreferences("LoginDATA", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("UsuarioId", String.valueOf(jObject.get("UsuarioId")));
            editor.putString("Nombre", String.valueOf(jObject.get("Nombre")));
            editor.putString("Email", String.valueOf(jObject.get("Email")));
            editor.commit();
        }catch(Exception ex){
            throw ex;
        }
    }
}
