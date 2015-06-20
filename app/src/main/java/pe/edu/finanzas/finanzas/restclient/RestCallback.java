package pe.edu.finanzas.finanzas.restclient;

import com.tismart.tsmlibrary.rest.enums.ResponseCode;

public interface RestCallback {
    void OnStart();
    void OnResponse(ResponseCode var1, String var2);
}
