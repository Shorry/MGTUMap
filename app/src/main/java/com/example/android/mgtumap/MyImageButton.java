package com.example.android.mgtumap;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by Anna on 20.11.2017.
 */

public class MyImageButton extends android.support.v7.widget.AppCompatImageButton {

    ObjectAnimator animator;

    public MyImageButton(Context context) {
        super(context);
        init();
    }
    public MyImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public MyImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        animator = new ObjectAnimator();
    }


    public void setAnimatorAlphaValue(PropertyValuesHolder value) {
        animator.setValues(value);
    }

    public void setAnimatorTarget() {
        animator.setTarget(this);
    }

    public void startAnimator() {
        animator.start();
    }
}
