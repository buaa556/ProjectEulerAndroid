package com.buaa556.projecteuler;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by zhangtianyu on 15-9-8.
 */
public class DataBaseHelper  {


    private void generate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE cache(id INTEGER PRIMARY KEY,title TEXT,description TEXT,solved INTEGER,difficulty INTEGER)");
        HttpOperation httpOp=new HttpOperation();

        Object[] array=httpOp.httpGet(1);
        if(array==null) {
            Log.e("exception","null");
            return;
        }
        for(int i=2;array[0]!=null;i++){
            this.insert(array,db);
            array=httpOp.httpGet(i);
        }
    }

    /**
     * update the database db from problem i.
     * attention :problem i will be visited
     * @param i
     * @param db
     * @return
     */
    public int generateFrom(int i,SQLiteDatabase db){
        HttpOperation httpOp=new HttpOperation();
        int o=i;
        Object[] array;
        for(;(array=httpOp.httpGet(i))!=null;i++){
            this.insert(array,db,i);
        }
        return i-o;
    }

    private void insert(Object[] objs, SQLiteDatabase db, int i) {
        ContentValues cv = new ContentValues();
        cv.put("id", i);
        cv.put("title",(String)objs[0]);
        cv.put("description",(String)objs[1]);
        cv.put("solved",(Integer)objs[2]);
        cv.put("difficulty",(Integer)objs[3]);
        //插入ContentValues中的数据
        db.insert("cache",null,cv);
        Log.i("insert",i+" "+(String)objs[0]);
    }


    /**
     * have bug in it. never mind  won't be used again
     * @param objs
     * @param db
     */
    private void insert(Object [] objs,SQLiteDatabase db){
        String sql="INSERT INTO cache(title,description,solved,difficulty) VALUES(";
        sql+="\'"+(String)objs[0]+"\',";
        sql+="\'"+(String)objs[1]+"\',";
        sql+=(Integer)objs[2]+",";
        sql+=(Integer)objs[3]+")";
        Log.i("insert",""+objs[0]);
        db.execSQL(sql);
    }
    private void deleteTable(SQLiteDatabase db){

        db.execSQL("DROP TABLE cache");
    }
    public void update(SQLiteDatabase db){

    }

}
