package com.example.android.mgtumap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Anna on 13.11.2017.
 */

public class MainMapView extends View {

    final int MAX_SCALE = 4;
    final int MIN_SCALE = 1;

    //full map image measures
    int mapWidth;
    int mapHeight;

    //canvas measures
    float halfCanvasWidth;
    float halfCanvasHeight;
    Point canvasMeasures;

    Point minimumCameraPosition;
    Point maximumCameraPosition;
    Bitmap bitmap;
    Point cameraPosition;
    int scaling;

    public MainMapView(Context context) {
        super(context);
        init(context);
    }
    public MainMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public MainMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context) {
        scaling = 1;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.image2);

        mapWidth = bitmap.getWidth();
        mapHeight = bitmap.getHeight();

        //initial camera position - on the center of the map
        cameraPosition = new Point(bitmap.getWidth()/2, bitmap.getHeight()/2);

        //will be calculated later (after creating the main layout)
        canvasMeasures = new Point();
        minimumCameraPosition = new Point();
        maximumCameraPosition = new Point();
    }
    public Point getCameraPosition() {
        return cameraPosition;
    }
    public void setCameraPosition(int _x, int _y) {
        if(_x < minimumCameraPosition.x)
            _x = minimumCameraPosition.x;
        if(_y < minimumCameraPosition.y)
            _y = minimumCameraPosition.y;
        if(_x > maximumCameraPosition.x)
            _x = maximumCameraPosition.x;
        if(_y > maximumCameraPosition.y)
            _y = maximumCameraPosition.y;
        cameraPosition.set(_x, _y);
    }
    public int getScaling() {
        return scaling;
    }
    public void increaseScale() {
        scaling = scaling >= MAX_SCALE? MAX_SCALE: scaling + 1;
        setBorderCameraPositions();
        this.invalidate();
    }
    public void decreaseScale() {
        scaling = scaling <= MIN_SCALE? MIN_SCALE: scaling - 1;
        setBorderCameraPositions();
        this.invalidate();
    }
    public void setCanvasMeasures() {
        canvasMeasures.set(this.getWidth(), this.getHeight());
        setBorderCameraPositions();
    }
    public void setHalfCanvasMeasures() {
        halfCanvasWidth = (float)canvasMeasures.x / 2;
        halfCanvasHeight = (float)canvasMeasures.y / 2;
    }
    public void setBorderCameraPositions() {
        setMinimumCameraPosition();
        setMaximumCameraPosition();
    }
    private void setMinimumCameraPosition() {
        minimumCameraPosition.set((int)(halfCanvasWidth * scaling), (int)(halfCanvasHeight * scaling));
        //comparing current camera position with borders
        if(cameraPosition.x < minimumCameraPosition.x)
            cameraPosition.x = minimumCameraPosition.x;
        if(cameraPosition.y < minimumCameraPosition.y)
             cameraPosition.y = minimumCameraPosition.y;
    }
    private void setMaximumCameraPosition() {
        maximumCameraPosition.set(mapWidth - minimumCameraPosition.x - 1, mapHeight - minimumCameraPosition.y - 1);
        //comparing current camera position with borders
        if(cameraPosition.x > maximumCameraPosition.x)
            cameraPosition.x = maximumCameraPosition.x;
        if(cameraPosition.y > maximumCameraPosition.y)
            cameraPosition.y = maximumCameraPosition.y;
    }

    //for debugging
    public Point getMaximumCameraPosition() {
        return maximumCameraPosition;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.MAGENTA);
        Bitmap square = Bitmap.createBitmap(bitmap,
                cameraPosition.x - (int)(halfCanvasWidth * scaling),
                cameraPosition.y - (int)(halfCanvasHeight * scaling),
                canvas.getWidth() * scaling,
                canvas.getHeight() * scaling);

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(square, canvas.getWidth(), canvas.getHeight(), false);
        canvas.drawBitmap(scaledBitmap, 0, 0, new Paint());

    }
}
