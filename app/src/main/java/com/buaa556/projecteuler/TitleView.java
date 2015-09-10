package com.buaa556.projecteuler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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


    private SQLiteDatabase db;
    /**
     * Constructor.
     * id has to be set .
     * TitleView will be added to layout dynamically
     * @param context
     * @param id
     */
    public TitleView(Context context,int id,SQLiteDatabase db) {
        super(context);
        this.problemId=id;
        mPaint = new Paint();
        this.db=db;
        Cursor c=db.rawQuery("SELECT * FROM cache WHERE id="+id,null);
        c.moveToFirst();
        Log.i("query result","id:"+id+" "+c.getCount());
        String title=c.getString(1);
        int difficulty=c.getInt(4);
        this.setText(id+":"+title+" ("+difficulty/5+")");
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
