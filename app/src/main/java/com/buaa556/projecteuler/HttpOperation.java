package com.buaa556.projecteuler;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by zhangtianyu on 15-9-7.
 */
public class HttpOperation {

    /**
     * number must bigger than 1
     * when reaching max,return null
     * else return an Object array. string[0] is title,string [1] is description(html),int [2] is solved,
     * int [3] is difficulty rating(0 if not stated in the website)
     * @param number
     */
    public Object []  httpGet(int number){
       // SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
        Object [] array=new Object[4];

        try{
            String url="https://projecteuler.net/problem="+number;
            Document doc=Jsoup.connect(url).timeout(20000).get();

            Elements title = doc.getElementsByTag("h2");
            array[0]=title.text();
            Elements description=doc.getElementsByAttributeValue("class", "problem_content");
            array[1]=description.outerHtml();
            Elements info=doc.getElementsByAttributeValue("id", "problem_info");
            String solveAndDiff=info.text();
            String [] splited=solveAndDiff.split(";");
            array[2]=Integer.parseInt(splited[1].trim().split(" ")[2]);
            if(splited.length==2){
                array[3]=0;
            }else if(splited.length==3){
                array[3]=Integer.parseInt(splited[2].trim().split(":")[1].replace('%',' ').trim());
            }


        }catch(Exception e){
            Log.e("exception","exception"+e.getClass());
            return null;
        }

        return array;
    }
}
