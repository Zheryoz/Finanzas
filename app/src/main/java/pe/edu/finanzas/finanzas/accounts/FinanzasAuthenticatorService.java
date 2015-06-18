package pe.edu.finanzas.finanzas.accounts;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by luis.burgos on 18/06/2015.
 * <p/>
 * Servicio que implementa la clase @link UserAuthenticator para la creaci�n de cuentas del tipo usuario.
 */
public class FinanzasAuthenticatorService extends Service {

    private FinanzasAccountAuthenticator authenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        authenticator = new FinanzasAccountAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
