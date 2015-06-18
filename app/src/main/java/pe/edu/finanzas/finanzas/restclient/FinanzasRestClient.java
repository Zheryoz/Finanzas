package pe.edu.finanzas.finanzas.restclient;

import com.tismart.tsmlibrary.rest.RestClient;
import com.tismart.tsmlibrary.rest.enums.AmbienteEnum;

/**
 * Created by luis.burgos on 18/06/2015.
 */
public class FinanzasRestClient extends RestClient {

    private static FinanzasRestClient mInstance;

    private FinanzasRestClient() {
        super();
        ambienteEnum = AmbienteEnum.DESARROLLO;
        DES_URL = "";
        QA_URL = "";
        PRD_URL = "";
    }

    public static FinanzasRestClient getInstance() {
        if (mInstance == null) {
            mInstance = new FinanzasRestClient();
        }
        return mInstance;
    }
}
