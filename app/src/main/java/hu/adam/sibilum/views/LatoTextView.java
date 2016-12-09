package hu.adam.sibilum.views;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.TextView;

public class LatoTextView extends TextView {

    public LatoTextView(Context context) {
        super(context); Init();
    }

    public LatoTextView(Context context, AttributeSet attrs) {
        super(context, attrs); Init();
    }

    public LatoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr); Init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LatoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes); Init();
    }

    private void Init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/lato.ttf"));
    }
}
