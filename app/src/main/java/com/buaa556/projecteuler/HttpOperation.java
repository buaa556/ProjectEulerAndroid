package com.buaa556.projecteuler;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.StringFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.io.IOException;

/**
 * Created by zhangtianyu on 15-9-7.
 */
public class HttpOperation {

    /**
     * number must bigger than 1
     * when reaching max,return null
     * else return an Object array. string[0] is title,string [1] is description(html),int [2] is difficulty, int [3] is solved
     * @param number
     */
    public Object []  httpGet(int number){
        SSLSocketFactory.getSocketFactory().setHostnameVerifier(new AllowAllHostnameVerifier());
        HttpClient client=new DefaultHttpClient();
        HttpGet getMethod=new HttpGet("https://projecteuler.net/problem="+number);

        Object [] array=new Object[4];
        try {
            HttpResponse response = client.execute(getMethod); //发起GET请求
            String origin=EntityUtils.toString(response.getEntity(), "utf-8");//获取服务器响应内容
            //using HTMLParse
            Parser parser=new Parser(origin);

            //test if　it's a problem page
            NodeFilter filter = new StringFilter("Problem not accessible");

            if(parser.extractAllNodesThatMatch(filter).size()!=0){
                //reach max
                return null;
            }

            //start to construct the array returned


            //title
            NodeFilter titleFilter=new TagNameFilter("h2");

            NodeList titleNode = parser.extractAllNodesThatMatch(titleFilter);//size == 1,or error occurs
            if(titleNode.size()!=1){
                throw new ParserException();
            }
            Node titleN =  titleNode.elementAt(0);
            array[0]=titleN.toPlainTextString();

            //description(html)
            NodeFilter descriptionFilter=new HasAttributeFilter("class", "problem_content");
            NodeList descriptionNode=parser.extractAllNodesThatMatch(descriptionFilter);
            if(descriptionNode.size()!=1){
                throw new ParserException();
            }
            Node decriptionN= descriptionNode.elementAt(0);
            array[1]=decriptionN.getChildren().toHtml();

            //the numbers of sovled people and difficulty
            NodeFilter stringFilter = new StringFilter("Published on");
            NodeList stringNodeList=parser.extractAllNodesThatMatch(stringFilter);
            if(stringNodeList.size()!=1){
                throw new ParserException();
            }
            Node stringNode=stringNodeList.elementAt(0);
            String sovledAndDifficulty = stringNode.toPlainTextString();

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }finally {
            return array;
        }
    }
}
