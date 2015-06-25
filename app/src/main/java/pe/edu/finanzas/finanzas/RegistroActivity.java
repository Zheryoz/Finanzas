package pe.edu.finanzas.finanzas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tismart.tsmlibrary.rest.enums.ResponseCode;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pe.edu.finanzas.finanzas.libraries.Funciones;
import pe.edu.finanzas.finanzas.restclient.FinanzasRestClient;
import pe.edu.finanzas.finanzas.restclient.RestCallback;


public class RegistroActivity extends Activity {

    private EditText txtNombre;
    private EditText txtCorreo;
    private EditText txtClave;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtClave = (EditText) findViewById(R.id.txtClave);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrarUsuario();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void RegistrarUsuario(){

        txtNombre.setError(null);
        txtCorreo.setError(null);
        txtClave.setError(null);

        final String nombre = txtNombre.getText().toString();
        final String correo = txtCorreo.getText().toString();
        final String password = txtClave.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            txtClave.setError(getString(R.string.error_invalid_password));
            focusView = txtClave;
            cancel = true;
        }
        if (TextUtils.isEmpty(correo) && !isEmailValid(correo)) {
            txtCorreo.setError(getString(R.string.error_invalid_email));
            focusView = txtCorreo;
            cancel = true;
        }
        if (TextUtils.isEmpty(nombre) && !isNombreValid(nombre)) {
            txtNombre.setError(getString(R.string.error_invalid_nombre));
            focusView = txtNombre;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }else{
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("NombreUsuario", nombre);
                jsonObject.put("Email", correo);
                jsonObject.put("Contrasena", password);
                jsonObject.put("RolId", 2);
                FinanzasRestClient.getInstance().postAsync(this, "/Usuario", "/Agregar", jsonObject, new RestCallback() {
                    ProgressDialog progress;
                    @Override
                    public void OnStart() {
                        if (progress == null) {
                            progress = ProgressDialog.show(RegistroActivity.this, getString(R.string.registro_usuario), getString(R.string.mensaje_registro_usuario), true);
                            progress.setCancelable(false);
                            progress.setCanceledOnTouchOutside(false);
                        }
                    }

                    @Override
                    public void OnResponse(ResponseCode var1, String var2) {
                        progress.dismiss();
                        if(var2.equals("")) {
                            new AlertDialog.Builder(RegistroActivity.this)
                                    .setTitle("Registro Fallido!")
                                    .setMessage("No hemos podido registrar sus datos. Al parecer, el correo ingresado ya esta registrado.")
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }else {
                            new AlertDialog.Builder(RegistroActivity.this)
                                    .setTitle("Registro Exitoso!")
                                    .setMessage("Hemos registrado sus datos exitosamente.")
                                    .setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();

                                            Intent intent = new Intent(RegistroActivity.this, InicioActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_info)
                                    .show();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private boolean isNombreValid(String nombre) {
        Pattern patron = Pattern.compile("[^A-Za-z ]");
        Matcher encaja = patron.matcher(nombre);

        return encaja.find();
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    private boolean isPasswordValid(String password) {
        return password.trim().length() > 4;
    }
}
