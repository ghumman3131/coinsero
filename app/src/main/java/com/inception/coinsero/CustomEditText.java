package com.inception.coinsero;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by charanghumman on 22/03/18.
 */

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);

        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {



                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueMedium.ttf");

                    setTypeface(myTypeface);


    }



}
