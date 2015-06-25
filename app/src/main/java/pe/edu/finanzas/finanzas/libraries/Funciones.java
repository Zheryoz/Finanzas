package pe.edu.finanzas.finanzas.libraries;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

import pe.edu.finanzas.finanzas.entities.Usuario;

/**
 * Created by francisco on 6/24/15.
 */
public final class Funciones {

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
