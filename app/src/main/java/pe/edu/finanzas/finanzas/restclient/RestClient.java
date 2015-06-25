package pe.edu.finanzas.finanzas.restclient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.tismart.tsmlibrary.R.string;
import com.tismart.tsmlibrary.rest.ConnectionUtil;
import com.tismart.tsmlibrary.rest.enums.AmbienteEnum;
import com.tismart.tsmlibrary.rest.enums.ResponseCode;
import com.tismart.tsmlibrary.rest.exceptions.NetworkException;
import pe.edu.finanzas.finanzas.restclient.RestCallback;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class RestClient {
    private static final String HTTP_POST_ACCEPT = "Accept";
    private static final String HTTP_POST_CONTENTTYPE = "Content-type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String CHARSET = "UTF-8";
    protected AmbienteEnum ambienteEnum;
    protected String DES_URL;
    protected String QA_URL;
    protected String PRD_URL;

    public RestClient() {
    }

    public void postSync(Context context, String service, String method, JSONObject request, RestCallback mCallback) throws NetworkException, IOException, JSONException {
        if(!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(com.tismart.tsmlibrary.R.string.tsmlibrary_error_conexion));
        } else {
            mCallback.OnStart();
            RestClient.WebServiceResponse serviceResponse = this.processPost(this.getUrl() + service + method, request);
            mCallback.OnResponse(serviceResponse.responseCode, serviceResponse.response);
        }
    }

    public void getSync(Context context, String service, String method, RestCallback mCallback) throws NetworkException, IOException, JSONException {
        if(!ConnectionUtil.isNetworkAvailable(context)) {
            throw new NetworkException(context.getString(com.tismart.tsmlibrary.R.string.tsmlibrary_error_conexion));
        } else {
            URL url = new URL(this.getUrl() + service + method);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            try {
                mCallback.OnStart();
                RestClient.WebServiceResponse serviceResponse = this.processGet(this.getUrl() + service + method);
                mCallback.OnResponse(serviceResponse.responseCode, serviceResponse.response);
            } finally {
                urlConnection.disconnect();
            }

        }
    }

    public void postAsync(Context context, String service, String method, JSONObject request, final RestCallback mCallback) throws NetworkException {
        if(!ConnectionUtil.isNetworkAvailable(context))
            throw new NetworkException(context.getString(string.tsmlibrary_error_conexion));
        else {
            (new AsyncTask() {
                protected void onPreExecute() {
                    super.onPreExecute();
                    mCallback.OnStart();
                }

                @Override
                protected Object doInBackground(Object[] strings) {
                    RestClient.WebServiceResponse wsservice = RestClient.this.new WebServiceResponse();

                    try {
                        wsservice = RestClient.this.processPost(String.valueOf(strings[0]), new JSONObject(String.valueOf(strings[1])));
                    } catch (JSONException | IOException var4) {
                        var4.printStackTrace();
                    }

                    return wsservice;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    mCallback.OnResponse(((WebServiceResponse)o).responseCode, ((WebServiceResponse)o).response);
                }
            }).execute(new String[]{this.getUrl() + service + method, request.toString()});
        }
    }

    public void getAsync(Context context, String service, String method, final RestCallback mCallback) throws NetworkException, IOException {
        if(!ConnectionUtil.isNetworkAvailable(context))
            throw new NetworkException(context.getString(string.tsmlibrary_error_conexion));
        else {
            (new AsyncTask() {

                protected void onPreExecute() {
                    super.onPreExecute();
                    mCallback.OnStart();
                }

                @Override
                protected Object doInBackground(Object[] strings) {
                    RestClient.WebServiceResponse wsservice = RestClient.this.new WebServiceResponse();

                    try {
                        String ex = String.valueOf(strings[0]);
                        wsservice = RestClient.this.processGet(ex);
                    } catch (JSONException | IOException var4) {
                        var4.printStackTrace();
                    }

                    return wsservice;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    mCallback.OnResponse(((WebServiceResponse)o).responseCode, ((WebServiceResponse)o).response);
                }

            }).execute(new String[]{this.getUrl() + service + method});
        }
    }

    private RestClient.WebServiceResponse processPost(String str_url, JSONObject request) throws IOException, JSONException {
        RestClient.WebServiceResponse wsresponse = new RestClient.WebServiceResponse();
        Log.i("TSMLibrary - RestClient", "URL: " + str_url);
        Log.i("TSMLibrary - RestClient", "Request: " + request);
        URL url = new URL(str_url);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

        try {
            urlConnection.setConnectTimeout('\uea60');
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(request.toString());
            out.close();
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            wsresponse.responseCode = ResponseCode.valueOf(urlConnection.getResponseCode());
            wsresponse.response = this.convertStreamToString(in);
        } finally {
            urlConnection.disconnect();
        }

        return wsresponse;
    }

    private RestClient.WebServiceResponse processGet(String str_url) throws IOException, JSONException {
        RestClient.WebServiceResponse wsresponse = new RestClient.WebServiceResponse();
        Log.i("TSMLibrary - RestClient", "URL: " + str_url);
        URL url = new URL(str_url);
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

        try {
            urlConnection.setConnectTimeout('\uea60');
            urlConnection.setRequestProperty("Accept", "application/json");
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
            wsresponse.responseCode = ResponseCode.valueOf(urlConnection.getResponseCode());
            if(wsresponse.responseCode.equals(ResponseCode.HTTP_OK)) {
                wsresponse.response = this.convertStreamToString(in);
            }
        } finally {
            urlConnection.disconnect();
        }

        return wsresponse;
    }

    private String getUrl() {
        switch(this.ambienteEnum.ordinal()) {
            case 1:
                return this.DES_URL;
            case 2:
                return this.QA_URL;
            case 3:
                return this.PRD_URL;
            default:
                return this.DES_URL;
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        try {
            String line;
            try {
                while((line = reader.readLine()) != null) {
                    line = line + "\n";
                    sb.append(line);
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            }
        } finally {
            try {
                is.close();
            } catch (IOException var13) {
                var13.printStackTrace();
            }

        }

        return sb.toString();
    }

    private class WebServiceResponse {
        ResponseCode responseCode;
        String response;

        public WebServiceResponse() {
            this.responseCode = ResponseCode.HTTP_ERROR_UNRECOGNIZED;
            this.response = "";
        }
    }
}
