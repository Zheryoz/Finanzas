package pe.edu.finanzas.finanzas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.tismart.tsmlibrary.rest.ConnectionUtil;
import com.tismart.tsmlibrary.rest.enums.ResponseCode;

import org.json.JSONObject;

import pe.edu.finanzas.finanzas.libraries.Funciones;
import pe.edu.finanzas.finanzas.libraries.SQLiteHelper;
import pe.edu.finanzas.finanzas.restclient.RestCallback;
import pe.edu.finanzas.finanzas.restclient.FinanzasRestClient;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    private EditText mUserView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserView = (EditText) findViewById(R.id.user);
        mPasswordView = (EditText) findViewById(R.id.password);

        findViewById(R.id.signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarUsuario();
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ValidarUsuario() {
        mUserView.setError(null);
        mPasswordView.setError(null);

        final String user = mUserView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(user) || !isEmailValid(user)) {
            mUserView.setError(getString(R.string.error_invalid_email));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            if(!ConnectionUtil.isNetworkAvailable(this)){
                Funciones.AlertaSinInternet(this);
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Email", user);
                jsonObject.put("Contrasena", password);
                FinanzasRestClient.getInstance().postAsync(this, "/Usuario", "/Ingresar", jsonObject, new RestCallback() {
                    ProgressDialog progress;
                    @Override
                    public void OnStart() {
                        if (progress == null) {
                            progress = ProgressDialog.show(LoginActivity.this, getString(R.string.iniciando_sesion), getString(R.string.mensaje_iniciando_sesion), true);
                            progress.setCancelable(false);
                            progress.setCanceledOnTouchOutside(false);
                        }
                    }

                    @Override
                    public void OnResponse(ResponseCode var1, String var2) {
                        progress.dismiss();
                        if(var2.equals("")) {
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Validación fallida!")
                                    .setMessage("Usuario y/o contraseña inválida, por favor intentelo nuevamente.")
                                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }else {
                            try{
                                Funciones.GuardarLogin(getApplicationContext(), new JSONObject(var2));
                            }catch(Exception ex) {
                                ex.printStackTrace();
                            }
                            Intent intent = new Intent(LoginActivity.this, InicioActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    private boolean isPasswordValid(String password) {
        return password.trim().length() > 4;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
}