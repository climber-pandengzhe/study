package com.zsq.study.test.httpReq;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cdoframework.cdolib.security.AES;
import com.zsq.study.test.AppendToFile;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class getLvShiShiWuSuoInfo {

    @SneakyThrows
    public static void main(String[] args) throws IOException  {



        FileInputStream inputStream = new FileInputStream("src/main/java/com/zsq/study/test/httpReq/行政区.txt");
        //     FileInputStream inputStream = new FileInputStream("/Users/zhoushengqiang/Desktop/mobileOut.txt");
        //设置inputStreamReader的构造方法并创建对象设置编码方式为gbk
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

        String code = "";
        while ((code = bufferedReader.readLine()) != null) {
            //  System.out.println(str);

            String city=code.substring(7,code.length());
            code=code.substring(0,6);

            System.out.println(city);
            int Index=1;
            String url = "https://credit.acla.org.cn/api/credit/MapDataService/list?code="+code+"&pageIndex="+Index;

            JSONObject info = getInfo(url);

            String strTotle=info.getString("total");
            Integer totle = new Integer(strTotle);

            for(int index=1;index*20<totle;index++ ){
                url = "https://credit.acla.org.cn/api/credit/MapDataService/list?code="+code+"&pageIndex="+index;
                JSONObject infoIndex = getInfo(url);
                JSONArray jsonArray= infoIndex.getJSONArray("items");
                for(int i=0;i<jsonArray.size();i++) {
                    System.out.println(city+"      "+jsonArray.getJSONObject(i));
                    AppendToFile.appendMethodA("src/main/java/com/zsq/study/test/httpReq/行政区律师事务所.txt", city+"   "+ code+"      "+jsonArray.getJSONObject(i));
                    AppendToFile.appendMethodA("src/main/java/com/zsq/study/test/httpReq/行政区律师事务所.txt", "\n");
                }

            }

        }


    }

    public static  JSONObject getInfo(String url) throws InterruptedException {
        Thread.sleep(10000);
        String result  =HttpClient.doGet(url);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject;
    }


}
