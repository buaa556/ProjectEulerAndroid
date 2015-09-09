package com.buaa556.projecteuler;

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
        String sql="INSERT INTO cache(id,title,description,solved,difficulty) VALUES(";
        sql+=i+",";
        sql+="\'"+(String)objs[0]+"\',";
        sql+="\'"+(String)objs[1]+"\',";
        sql+=(Integer)objs[2]+",";
        sql+=(Integer)objs[3]+")";
        Log.i("insert",""+objs[0]);
        db.execSQL(sql);

    }

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
