package com.zsq.study.test;

import com.cdoframework.cdolib.data.cdo.CDO;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Test {
    


    public static void main(String[] args) {
//        String userId="5054569,2118596,2271109,5054569,4695828,1411511,8729643,2118596,1202294,1202294,2960768,1834051,2994479,5003564,5003564,3779730,8967701,1221696,2271109,8967701,7392143,7392143,3779730,7044647,5059495,5355572,11097587,7044647,8484969,7044647,5354089,5355572,1468523,11097587,11097587,9010527,14592501,14592501,14592501,6101339,7498043,15068217,15068217,7880463,12148615,7088216,11810839,11128159,9029207,2568594,1838054,4058700,11128159,7880463,12156613,4020111,4020111,4058700,12156613,4020111,11808321,4058700,12148615,4058700,9282231,9376295,1116376,1116376,1672799,2830264,1672799,1632962,3019566,3019566,1672799,11808321,1632962,2830264,3019566,2830264,3399198,3399198,3421751,3421751,8850775,13490001,6330933,5574635,3399198,11808321,8850775,6330933,1524633,5574635,4671419,3047429,8235455,5783670,8235455,5051136,3047429,9354191,9354191,3047429,17228163,3047429,5051136,7915905,5783670,3368872,15089221,15089221,6297083,3368872";
//
//        String[] userIds = userId.split(",");
//
//        for(int i=0;i<userIds.length;i++){
//          Long tableIndex =  Long.valueOf(userIds[i])/10000;
//
//          Long dbIndex = tableIndex/100;
//            System.out.println("use Notice"+dbIndex+";");
//            System.out.println("select * from tbPersonalMessage"+tableIndex+" where lUserId="+userIds[i]+";");
//        }


        CDO abc= new CDO();
        long [] ads= new long[]{11,12,13};

        abc.setLongArrayValue("lIds",ads);
        System.out.println(abc.toXML());


    }

   



    public static String md5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] bymd5 = md.digest(message.getBytes());
            BASE64Encoder base64 = new BASE64Encoder();   //以BASE64 显示，不然MD5后会查码
            return base64.encode(bymd5).toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
