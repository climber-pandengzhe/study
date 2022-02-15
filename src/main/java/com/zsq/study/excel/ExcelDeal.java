package com.zsq.study.excel;


import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.zsq.study.test.AppendToFile;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDeal {

    static Map<String,String> aa= new HashMap<String,String>();
    private static Logger log = LoggerFactory.getLogger(ExcelDeal.class);
    public static void main(String[] args) {
        System.out.println("--开始处理");
        ExcelUtil.readBySax("/Users/zhoushengqiang/Desktop/信息修复/全量DL数据-第二批111.xlsx", 0, createRowHandler());
        ExcelUtil.readBySax("/Users/zhoushengqiang/Desktop/信息修复/修复数据全部数据2.xlsx", 0, createRowHandler());
        ExcelUtil.readBySax("/Users/zhoushengqiang/Desktop/信息修复/修复数据全部数据2.xlsx", 1, createRowHandler());


    }

    private static RowHandler createRowHandler() {

        return (sheetIndex, rowIndex, row) -> {
            rowIndex=rowIndex+1;
            try {
                String name = row.get(0) + "";
                String strIdentity = row.get(1) + "";

                if(strIdentity.length()!=18){
                    System.out.println("非法身份证号:"+name);
                }else if(aa.containsKey(strIdentity)){
                    System.out.println("重复:"+strIdentity);
                }else{
                    aa.put(strIdentity,strIdentity);
                    int size = row.size();
                    String strMobile="";
                    for(int j=2;j<size;j++){
                        //  System.out.println("---"+j);
                        if(row.get(j)!=null &&!"".equals(row.get(j))){
                            strMobile=strMobile+row.get(j)+",";
                        }else{
                            continue;
                        }
                    }
                    if(!strMobile.equals("")){
                        String sql =" insert into `User`.`t_customer` ( `strName`, `strIdentity`, `strMobile`, `flag`) values ('"+name+"','"+strIdentity+"','"+strMobile+"',"+"0);";
                        AppendToFile.appendMethodA("src/main/java/com/zsq/study/excel/sql.sql", sql);
                        AppendToFile.appendMethodA("src/main/java/com/zsq/study/excel/sql.sql","\n");
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }





}