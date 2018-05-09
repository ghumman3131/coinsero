package com.inception.coinsero;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by charanghumman on 22/03/18.
 */

public class CustomButton extends android.support.v7.widget.AppCompatButton {
    public CustomButton(Context context) {
        super(context);

        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {



                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "HelveticaNeueMedium.ttf");

                    setTypeface(myTypeface);


    }



}
