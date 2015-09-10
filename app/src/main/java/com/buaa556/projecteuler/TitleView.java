package com.buaa556.projecteuler;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by zhangtianyu on 15-9-10.
 * This view is used in MainActivity. Display id,title, solved and difficulty.
 * Click it to open a ProblemView
 */
public class TitleView extends Button {
    private int problemId=0;

    private Paint mPaint;

    public TitleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleView(Context context){
        super(context);
    }


    /**
     * Constructor.
     * id has to be set .
     * TitleView will be added to layout dynamically
     * @param context
     * @param id
     */
    public TitleView(Context context,int id) {
        super(context);
        this.problemId=id;
        mPaint = new Paint();

    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.darkorange));

        canvas.drawCircle(72, 72, 60, mPaint);
        mPaint.setColor(getResources().getColor(R.color.white));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(80f);
        canvas.drawText(problemId + "", 72, 72, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=150;
        setMeasuredDimension(width,height);

    }
}
