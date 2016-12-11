package hu.adam.sibilum;

import android.graphics.Bitmap;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.util.Random;

public class Utils {

    private static final Random sRandom = new Random();

    public static class Log {
        public static void error(String str) {
            android.util.Log.e("DEBUG", str);
        }

        public static void error(Exception e) {
            if( e.getMessage()!= null )
                android.util.Log.e("DEBUG", e.getMessage());
        }

        public static void info(String str) {
            android.util.Log.i("DEBUG", str);
        }
    }

    public static String toUtf8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static String fromUtf8(String str) {
        try {
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static boolean isValidModel(JSONObject json, String[] fields) {

        if( json == null )
            return false;

        for(String field : fields) {
            if( !json.has(field) )
                return false;
        }

        return true;
    }

    public static void snackbar(View view, int resId) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show();
    }

    public static void snackbar(View view, String str) {
        Snackbar.make(view, str, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static int rand(int min, int max) {
        return min + sRandom.nextInt(max - min);
    }
}
