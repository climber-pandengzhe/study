package com.zsq.study.test;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.cdoframework.cdolib.security.AES;
import com.zsq.study.util.HutoolExcelUtils;
import org.apache.commons.codec.binary.Base64;
import sun.security.krb5.internal.crypto.Des3;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestReadBigCSV {
    public static void main(String[] args) throws FileNotFoundException {

        test2();
    }

    private static void test2()  throws FileNotFoundException{
        String path="/Users/zhoushengqiang/Desktop/1.csv";
        String path2="/Users/zhoushengqiang/Desktop/1.xlsx";
        InputStream inputStream= new FileInputStream(new File(path));
        List<Map<String, Object>> maps = HutoolExcelUtils.readCsv(inputStream, 1, 1);

        List<Map<String, Object>> maps2 = new LinkedList<>();

        int i=0;
        for(Map<String,Object> map: maps){

            i++;

 //strLoginId	strIdentity	strName	strDeptName	strDeptCode	strReserveMobile	strMobiles

            map.put("strLoginId",enc((String)map.get("strLoginId")));
            map.put("strIdentity",enc((String)map.get("strIdentity")));
            map.put("strReserveMobile",enc((String)map.get("strReserveMobile")));
//            map.put("手机号",enc2((String)map.get("手机号")));
//            map.put("银行开卡预留手机号",enc2((String)map.get("银行开卡预留手机号")));


            if(i==1){
                continue;
            }

            if(i%10000==0){
                System.out.println("i"+i);
            }
            maps2.add(map);

        }
        writeExcel2(maps2,path2);
    }


    private static void test1()  throws FileNotFoundException{
        String path="/Users/zhoushengqiang/Desktop/1.csv";
        String path2="/Users/zhoushengqiang/Desktop/1.xlsx";
        InputStream inputStream= new FileInputStream(new File(path));
        List<Map<String, Object>> maps = HutoolExcelUtils.readCsv(inputStream, 1, 1);



        List<List<String>> rows = CollUtil.newArrayList();
        int i=0;
        for(Map<String,Object> map: maps){
            i++;
            String date=(String)map.get("strLoginId\tstrIdentity\tstrName\tstrDeptName\tstrDeptCode\tstrReserveMobile\tstrMobiles");
//            if(date.equals("strLoginId\tstrIdentity\tstrName\tstrDeptName\tstrDeptCode\tstrReserveMobile\tstrMobiles")){
//                continue;
//            }


            String[] s = date.split("\\s");

            s[0]= enc( s[0]);
            s[1]= enc2( s[1]);;
            s[5]= enc2( s[5]);;


            List<String> row = CollUtil.newArrayList(s);
            rows.add(row);


            if(i==10){
                break;
            }

        }
        writeExcel(rows,path2);
    }

    /**
     * List 写
     * @param rows
     * @param path
     */
    private static void writeExcel(List<List<String>> rows,String path){
        //通过工具类创建writer
        BigExcelWriter writer = ExcelUtil.getBigWriter(path);
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
       // writer.merge(rows.size() - 1, "测试标题");
        //一次性写出内容
        writer.write(rows);
        //关闭writer，释放内存
        writer.close();
    }

    /**
     * Map 写
     * @param maps
     * @param path
     */
    private static void writeExcel2(List<Map<String, Object>> maps,String path){

            // 通过工具类创建writer
 //           File file = new File(path);
//            if(!file.exists()){
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        BigExcelWriter writer= ExcelUtil.getBigWriter(path);
// 一次性写出内容，使用默认样式
        writer.write(maps);
// 关闭writer，释放内存
        writer.close();

    }



    private static String enc(String str){
        AES aes=new AES();
        aes.init("DECSECURITYKEYABCDEFG", "EiJPWIgQQDgoJXlRy91SZncpdZgwQEHi");
        String moible ="";
        try{
            moible =","+aes.decode(str);
        }catch (Exception e){
            moible=str;
        }finally {

        }
        return moible;
    }

    // 4ZnymnA5c8jE+qfBwMr1cw==
    private  static String enc2(String str) {


            String   strOut = dec("EiJPWIgQQDgoJXlRy91SZncpdZgwQEHi", str);
            String moible ="";
            try{
                moible =","+strOut;
            }catch (Exception e){
                moible=str;
            }finally {

            }
            return  moible;
    }

    /**
     * 解密
     * @param encMsg
     * @return
     */
    public static String dec(String key, String encMsg){
        String decMsg="";
        //空或空字符串不要编码直接返回
        if(encMsg==null||"".equals(encMsg)){
            return decMsg;
        }
        try{
            byte[] srcBytes = decryptMode(key, Base64.decodeBase64(encMsg.getBytes()));
            decMsg =new String(srcBytes,"UTF-8");
        }catch(Exception e){
            System.out.println(encMsg+"解密异常--->"+e);
            //解密异常则原样返回
            decMsg=encMsg;
        }
        return decMsg;
    }


    private static final String Algorithm = "DESede"; // 定义 加密算法,可用
    // DES,DESede,Blowfish
    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(String keybyte, byte[] src) throws Exception {
        // 生成密钥
        SecretKey deskey = new SecretKeySpec(Base64.decodeBase64(keybyte.getBytes()), Algorithm);
        // 加密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] ptext=c1.doFinal(src);
        return Base64.encodeBase64(ptext);
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    public static byte[] decryptMode(String keybyte, byte[] src) throws Exception {
        byte[] base64Str = Base64.decodeBase64(keybyte.getBytes());
        // 生成密钥
        SecretKey deskey = new SecretKeySpec(base64Str, Algorithm);
        // 解密
        Cipher c1 = Cipher.getInstance(Algorithm);
        c1.init(Cipher.DECRYPT_MODE, deskey);
        return c1.doFinal(src);
    }

    // 转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }


}
