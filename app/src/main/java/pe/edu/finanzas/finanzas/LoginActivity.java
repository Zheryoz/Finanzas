package pe.edu.finanzas.finanzas;

import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.tismart.tsmlibrary.rest.enums.ResponseCode;
import com.tismart.tsmlibrary.rest.exceptions.NetworkException;
import com.tismart.tsmlibrary.rest.interfaces.RestCallback;

import org.json.JSONException;
import org.json.JSONObject;

import pe.edu.finanzas.finanzas.accounts.AccountUtils;
import pe.edu.finanzas.finanzas.restclient.FinanzasRestClient;
import pe.edu.finanzas.finanzas.restclient.FinanzasRestContract;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AccountAuthenticatorActivity {

    private final static int REQ_REGISTER = 10;
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
                attemptLogin();
            }
        });
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivityForResult(intent, REQ_REGISTER);
            }
        });
    }

    public void attemptLogin() {
        mUserView.setError(null);
        mPasswordView.setError(null);

        final String user = mUserView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(user)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        } else if (!isEmailValid(user)) {
            mUserView.setError(getString(R.string.error_invalid_email));
            focusView = mUserView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(FinanzasRestContract.IniciarSesionContract.RequestContract.USERNAME, user);
                jsonObject.put(FinanzasRestContract.IniciarSesionContract.RequestContract.PASSWORD, password);
            } catch (JSONException jsone) {
                jsone.printStackTrace();
            }
            try {
                FinanzasRestClient.getInstance().postAsync(this, FinanzasRestContract.SERVICE, FinanzasRestContract.IniciarSesionContract.METHOD, jsonObject, new RestCallback() {

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
                    public void OnResponse(ResponseCode responseCode, JSONObject jsonObject) {
                        progress.dismiss();
                        AccountUtils.newInstance(LoginActivity.this).addAccount(user, password, null);
                        setAccountAuthenticatorResult(null);
                        finish();
                    }
                });
            } catch (NetworkException ne) {
                ne.printStackTrace();
            }
        }
    }

    private boolean isEmailValid(String email) {
        return !email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
}