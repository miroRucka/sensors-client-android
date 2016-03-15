package sk.mirorucka.sensors;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

/**
 * @author rucka
 */
public class HttpSettingsApplicableReset extends AsyncTask<String, String, String> {


    private String endpoint;

    private Context context;

    public HttpSettingsApplicableReset(String endpoint, Context context) {
        this.endpoint = endpoint;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            return postResetRequest();
        } catch (Exception e) {
            Log.e("sensors", "nastala chyba pri ziskavani dat", e);
            return null;
        }
    }

    public String postResetRequest() throws Exception {
        HttpClient httpclient = new DefaultHttpClient(HttpUtils.getHttpClientSettings(context));
        HttpGet httpget = new HttpGet(endpoint + "/api/sensors/last");
        httpget.setHeader("Accept", "application/json");
        httpget.setHeader("Content-type", "application/json");
        httpget.setHeader("Authorization", "c3VzbGlrOmJ1Ym8=");
        HttpResponse httpResponse = httpclient.execute(httpget);
        httpclient.getConnectionManager().shutdown();
        InputStream inputStream = httpResponse.getEntity().getContent();
        return IOUtils.toString(inputStream);
    }
}
