package com.example.android.mgtumap;

import android.animation.ObjectAnimator;
import android.widget.ImageButton;

/**
 * Created by Anna on 20.11.2017.
 */

public class AnimateImageButton {
    final int ANIMATION_DURATION = 250;
    final float ACTIVE_APLHA_VALUE = 0.8f;
    final float INACTIVE_ALPHA_VALUE = 0.2f;
    final String PROPERTY_NAME = "alpha";

    ImageButton imgButton;
    ObjectAnimator animator;

    public AnimateImageButton() {
        animator = new ObjectAnimator();
        animator.setPropertyName(PROPERTY_NAME);
        animator.setDuration(ANIMATION_DURATION);
    }

    public ImageButton getImgButton() {
        return imgButton;
    }
    public ObjectAnimator getAnimator() {
        return animator;
    }
    public void setAnimatorTarget() {
        animator.setTarget(imgButton);
    }
    public void startAnimate() {
        animator.start();
    }
    public void setImageButton(ImageButton _imgButton) {
        imgButton = _imgButton;
        setAnimatorTarget();
    }

    public void setActiveAlphaValue() {
        animator.setFloatValues(ACTIVE_APLHA_VALUE);
    }
    public void setInactiveAlphaValue() {
        animator.setFloatValues(INACTIVE_ALPHA_VALUE);
    }
}
