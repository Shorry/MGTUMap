package com.example.android.mgtumap;

import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton mZoomInButton;
    ImageButton mZoomOutButton;
    ImageButton mPathButton;
    MainMapView mMapView;
    TextView mLogTextView;

    AnimateImageButton mZoomInAnimateButton;
    AnimateImageButton mZoomOutAnimateButton;
    AnimateImageButton mPathAnimateButton;

    int startX;
    int startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mZoomInButton = findViewById(R.id.b_zoom_in);
        mZoomOutButton = findViewById(R.id.b_zoom_out);
        mMapView = findViewById(R.id.view_main_map);
        mPathButton = findViewById(R.id.b_path);
        mLogTextView = findViewById(R.id.tv_test_log);
        RelativeLayout mainLayout = findViewById(R.id.layout_global);
        mainLayout.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        mMapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        //actions that must be done after the creating main layout
                        mMapView.setCanvasMeasures();
                        mMapView.setHalfCanvasMeasures();
                        //mLogTextView.setText("width inside observer: " + mMapView.getWidth() + " and height: " + mMapView.getHeight());
                        mLogTextView.setText("max X camera position: " + mMapView.getMaximumCameraPosition().x + " and height: " + mMapView.getMaximumCameraPosition().y);
                    }
                });

        mZoomInAnimateButton = new AnimateImageButton();
        mZoomInAnimateButton.setImageButton(mZoomInButton);
        mZoomOutAnimateButton = new AnimateImageButton();
        mZoomOutAnimateButton.setImageButton(mZoomOutButton);
        mPathAnimateButton = new AnimateImageButton();
        mPathAnimateButton.setImageButton(mPathButton);


        mMapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mZoomInAnimateButton.setInactiveAlphaValue();
                        mZoomOutAnimateButton.setInactiveAlphaValue();
                        mPathAnimateButton.setInactiveAlphaValue();

                        mZoomInAnimateButton.startAnimate();
                        mZoomOutAnimateButton.startAnimate();
                        mPathAnimateButton.startAnimate();

                        startX = (int)motionEvent.getX();
                        startY = (int)motionEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        mZoomInAnimateButton.setActiveAlphaValue();
                        mZoomOutAnimateButton.setActiveAlphaValue();
                        mPathAnimateButton.setActiveAlphaValue();

                        mZoomInAnimateButton.startAnimate();
                        mZoomOutAnimateButton.startAnimate();
                        mPathAnimateButton.startAnimate();
                }

                float dx = motionEvent.getX() - startX;
                float dy = motionEvent.getY() - startY;
                startX = (int)motionEvent.getX();
                startY = (int)motionEvent.getY();
                Point curCameraPos = mMapView.getCameraPosition();
                int curCameraPosX = curCameraPos.x;
                int curCameraPosY = curCameraPos.y;

                curCameraPosX -= (int)dx * mMapView.getScaling();
                curCameraPosY -= (int)dy * mMapView.getScaling();
                mMapView.setCameraPosition(curCameraPosX, curCameraPosY);

                mLogTextView.setText("camera position: " + mMapView.getCameraPosition() + "; scaling: " + mMapView.getScaling());
                //mLogTextView.setText("width: " + mMapView.getWidth() + " height: " + mMapView.getHeight());
                //mLogTextView.setText("moving x: " + dx);

                mMapView.invalidate();

                return true;
            }
        });
        mZoomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.decreaseScale();
                mLogTextView.setText("camera position: " + mMapView.getCameraPosition() + "; scaling: " + mMapView.getScaling());
            }
        });
        mZoomOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.increaseScale();
                mLogTextView.setText("camera position: " + mMapView.getCameraPosition() + "; scaling: " + mMapView.getScaling());
            }
        });
    }






}
