package sk.mirorucka.sensors;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author rucka
 */
public class AssetsPropertyReader {

    private Context context;
    private Properties properties;

    public AssetsPropertyReader(Context context) {
        this.context = context;
        properties = new Properties();
    }

    public Properties getProperties(String FileName) {
        try {
            InputStream inputStream = context.getAssets().open(FileName);
            properties.load(inputStream);
        } catch (IOException e) {
            Log.e(Constant.APP_TAG.getCode(), e.toString(), e);
        }
        return properties;
    }
}
