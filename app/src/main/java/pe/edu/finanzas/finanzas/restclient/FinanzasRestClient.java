package pe.edu.finanzas.finanzas.restclient;

import com.tismart.tsmlibrary.rest.enums.AmbienteEnum;

/**
 * Created by luis.burgos on 18/06/2015.
 */
public class FinanzasRestClient extends RestClient {

    private static FinanzasRestClient mInstance;

    private FinanzasRestClient() {
        super();
        ambienteEnum = AmbienteEnum.DESARROLLO;
        DES_URL = "http://planespago.somee.com/api";
        QA_URL = "http://planespago.somee.com/api";
        PRD_URL = "http://planespago.somee.com/api";
    }

    public static FinanzasRestClient getInstance() {
        if (mInstance == null) {
            mInstance = new FinanzasRestClient();
        }
        return mInstance;
    }
}
