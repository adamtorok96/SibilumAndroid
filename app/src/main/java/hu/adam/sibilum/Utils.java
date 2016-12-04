package hu.adam.sibilum;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

    public static String toUtf8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
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
}
