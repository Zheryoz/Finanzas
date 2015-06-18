package pe.edu.finanzas.finanzas.restclient;

/**
 * Created by luis.burgos on 18/06/2015.
 */
public class FinanzasRestContract {
    public static final String SERVICE = "";

    public static class IniciarSesionContract {
        public static final String METHOD = "login";

        public static class RequestContract {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }

        public static class ResponseContract {

        }
    }
}
