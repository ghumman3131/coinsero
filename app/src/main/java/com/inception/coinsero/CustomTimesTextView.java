package com.inception.coinsero;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by charanghumman on 22/03/18.
 */

public class CustomTimesTextView extends android.support.v7.widget.AppCompatTextView {
    public CustomTimesTextView(Context context) {
        super(context);
        init();
    }

    public CustomTimesTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTimesTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {



        Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "timefonts.ttf");

        setTypeface(myTypeface);


    }

}
