package sk.mirorucka.sensors;

import android.content.Context;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.Properties;

/**
 * @author rucka
 */
public class HttpUtils {


    public static HttpParams getHttpClientSettings(Context context) {
        final HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, Integer.valueOf(getTimeout(context)));
        return httpParams;
    }

    private static String getTimeout(Context context) {
        Properties prop = new AssetsPropertyReader(context).getProperties(Constant.PROPERTIES_KEY.getCode());
        return prop.getProperty(Constant.HTTP_CLIENT_TIMEOUT.getCode());

    }
}
