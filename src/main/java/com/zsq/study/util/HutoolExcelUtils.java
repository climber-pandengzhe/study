package com.zsq.study.util;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.hutool.core.bean.BeanDesc;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import cn.hutool.poi.exceptions.POIException;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/***
 * @ClassName HutoolExcelUtils
 * @Description: 读取大表的excel工具
 * @Author Ahuan
 * @Date 2020/5/26 
 * @Version V1.0
 **/
public class HutoolExcelUtils {
    private static Logger logger = LoggerFactory.getLogger(HutoolExcelUtils.class);
    private List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
    private List<String> headers = new ArrayList<>();
    private Map<String, String> aliasHeader;
    private int headerRow = 1;
    private boolean checkHeader = false;
 
    public HutoolExcelUtils(Map<String, String> aliasHeader, int headerRow) {
        this.aliasHeader = aliasHeader;
        this.headerRow = headerRow;
    }
 
    public HutoolExcelUtils(Map<String, String> aliasHeader, int headerRow, boolean checkHeader) {
        this.aliasHeader = aliasHeader;
        this.headerRow = headerRow;
        this.checkHeader = checkHeader;
    }
 
    /**
     * 读取大表的实现方法
     *
     * @return
     */
    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
                if (isEmpty(rowlist)) {
                    return;
                }
                if (headerRow > rowIndex + 1) {
                    return;
                } else if (headerRow == rowIndex + 1) {
                    rowlist.forEach(e -> headers.add(e.toString()));
                    //比对表头是否正确;
                    if (checkHeader) {
                        boolean headerHandler = checkHeaderHandler();
                        if (!headerHandler) {
                            throw new POIException("表头格式不正确!");
                        }
                    }
                } else {
                    if (aliasHeader == null || aliasHeader.isEmpty()) {
                        rows.add(IterUtil.toMap(headers, rowlist));
                    } else {
                        rows.add(IterUtil.toMap(aliasHeader(headers, aliasHeader), rowlist));
                    }
                }
            }
        };
    }
 
 
    public boolean isEmpty(List<Object> list) {
        if (list.isEmpty()) {
            return true;
        }
        List<Object> collect = list.stream().filter(e -> e == null || StringUtils.isBlank(e.toString())).collect(Collectors.toList());
        return collect.size() == list.size() ? true : false;
    }
 
    /**
     * @param inputStream 文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param sheet       读取的sheet  第一个sheet为 0
     * @param headerRow   表头行 第一行是 1
     * @param beanType    定义的对象 属性有excel的注解
     * @param <T>
     * @return
     */
    public static <T> List<T> readBySax(InputStream inputStream, int sheet, int headerRow, Class<T> beanType) {
        Map<String, String> aliasHeader = getFiledMap(beanType);
        List<Map<String, Object>> mapList = readBySax(inputStream, sheet, aliasHeader, headerRow, false);
        if (Map.class.isAssignableFrom(beanType)) {
            return (List<T>) mapList;
        } else {
            List<T> beanList = new ArrayList(mapList.size());
            Iterator iterator = mapList.iterator();
 
            while (iterator.hasNext()) {
                Map<String, Object> map = (Map) iterator.next();
                beanList.add(BeanUtil.mapToBean(map, beanType, false));
            }
            return beanList;
        }
    }
 
 
    /**
     * @param inputStream 文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param sheet       读取的sheet  第一个sheet为 0
     * @param headerRow   表头行 第一行是 1
     * @param beanType    定义的对象 属性有excel的注解
     * @param <T>
     * @param checkHeader
     * @return
     */
    public static <T> List<T> read07BySax(InputStream inputStream, int sheet, int headerRow, Class<T> beanType, boolean checkHeader) {
        Map<String, String> aliasHeader = getFiledMap(beanType);
        List<Map<String, Object>> mapList = readBySax(inputStream, sheet, aliasHeader, headerRow, checkHeader);
        if (Map.class.isAssignableFrom(beanType)) {
            return (List<T>) mapList;
        } else {
            List<T> beanList = new ArrayList(mapList.size());
            Iterator iterator = mapList.iterator();
 
            while (iterator.hasNext()) {
                Map<String, Object> map = (Map) iterator.next();
                beanList.add(BeanUtil.mapToBean(map, beanType, false));
            }
            return beanList;
        }
    }
 
    /**
     * @param inputStream 文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param sheet       读取的sheet  第一个sheet为 0
     * @param headerRow   表头行 第一行是 1
     * @return
     */
    public static List<Map<String, Object>> readBySax(InputStream inputStream, int sheet, int headerRow) {
        return readBySax(inputStream, sheet, null, headerRow, false);
    }
 
    /**
     * @param inputStream
     * @param sheet       读取的sheet  第一个sheet为 0
     * @param headerRow   表头行 第一行是 1
     * @param checkHeader
     * @return
     */
    public static List<Map<String, Object>> readBySax(InputStream inputStream, int sheet, int headerRow, boolean checkHeader) {
        return readBySax(inputStream, sheet, null, headerRow, checkHeader);
    }
 
    /**
     * @param inputStream 文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param sheet       读取的sheet  第一个sheet为 0
     * @param aliasHeader 定义的表头转换map {"姓名":name,"年龄":age}
     * @param headerRow   表头行 第一行是 1
     * @return
     */
    public static List<Map<String, Object>> readBySax(InputStream inputStream, int sheet, Map<String, String> aliasHeader, int headerRow, boolean checkHeader) {
        long start = System.currentTimeMillis();
        HutoolExcelUtils excelUtils = new HutoolExcelUtils(aliasHeader, headerRow, checkHeader);
        ExcelUtil.readBySax(inputStream, sheet, excelUtils.createRowHandler());
        System.out.println("read  used ：" + (System.currentTimeMillis() - start) + " ms");
        return excelUtils.getRows();
    }
 
    /**
     * 读取csv文件
     *
     * @param inputStream    文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param headerRowIndex 表头行
     * @param startRowIndex  数据开始行
     * @return
     */
    public static List<Map<String, Object>> readCsv(InputStream inputStream, int headerRowIndex, int startRowIndex) {
        return readCsv(inputStream, null, headerRowIndex, startRowIndex, "gbk");
    }
 
    /**
     * 读取csv文件
     *
     * @param inputStream    文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param headerRowIndex 表头行
     * @param startRowIndex  数据开始行
     * @param charset        字符集
     * @return
     */
    public static List<Map<String, Object>> readCsv(InputStream inputStream, int headerRowIndex, int startRowIndex, String charset) {
        return readCsv(inputStream, null, headerRowIndex, startRowIndex, charset);
    }
 
    /**
     * 读取csv文件
     *
     * @param inputStream    文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param aliasHeader    转换后的表头
     * @param headerRowIndex 表头行
     * @param startRowIndex  数据开始行
     * @param charset        字符集
     * @return
     */
    public static List<Map<String, Object>> readCsv(InputStream inputStream, Map<String, String> aliasHeader, int headerRowIndex, int startRowIndex, String charset) {
        long start = System.currentTimeMillis();
        List<Map<String, Object>> resList = new ArrayList<>();
        CsvReader reader = CsvUtil.getReader();
        //从文件中读取CSV数据
        InputStreamReader is = null;
        try {
            is = new InputStreamReader(inputStream, charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("get inputStreamReader failed");
        }
 
        CsvData data = reader.read(is);
        List<CsvRow> rows = data.getRows();
        //空表格；
        if (rows.isEmpty() || rows.size() - headerRowIndex == 0) {
            return null;
        }
        //获取表头；
        CsvRow headerRow = rows.get(headerRowIndex - 1);
        //遍历行
        for (int i = startRowIndex - 1; i < rows.size(); i++) {
            CsvRow csvRow = rows.get(i);
            //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            List<String> rawList = csvRow.getRawList();
            if (aliasHeader == null) {
                Map map = IterUtil.toMap(headerRow, (Iterable) rawList);
                resList.add(map);
            } else {
                Map map = IterUtil.toMap(aliasHeader(headerRow, aliasHeader), (Iterable) rawList);
                resList.add(map);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("read  used ：" + (end - start) + " ms");
        return resList;
    }
 
    /**
     * 读取csv文件 返回期望的beanType
     *
     * @param inputStream    文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param headerRowIndex 表头行
     * @param startRowIndex  数据开始行
     * @param beanType       javaBean  属性包含@excel注解
     * @param <T>
     * @return
     */
    public static <T> List<T> readCsv(InputStream inputStream, int headerRowIndex, int startRowIndex, Class<T> beanType) {
        return readCsv(inputStream, headerRowIndex, startRowIndex, beanType, "utf-8");
    }
 
    /**
     * 读取csv文件 返回期望的beanType
     *
     * @param inputStream    文件流 获取方法推荐使用 new ByteArrayInputStream(multipartFile.getBytes())
     * @param headerRowIndex 表头行
     * @param startRowIndex  数据开始行
     * @param beanType       javaBean  属性包含@excel注解
     * @param charset        字符集
     * @param <T>
     * @return
     */
    public static <T> List<T> readCsv(InputStream inputStream, int headerRowIndex, int startRowIndex, Class<T> beanType, String charset) {
        Map<String, String> aliasHeader = getFiledMap(beanType);
        List<Map<String, Object>> mapList = readCsv(inputStream, aliasHeader, headerRowIndex, startRowIndex, charset);
        if (Map.class.isAssignableFrom(beanType)) {
            return (List<T>) mapList;
        } else {
            List<T> beanList = new ArrayList(mapList.size());
            Iterator i$ = mapList.iterator();
 
            while (i$.hasNext()) {
                Map<String, Object> map = (Map) i$.next();
                beanList.add(BeanUtil.mapToBean(map, beanType, false));
            }
            return beanList;
        }
    }
 
 
    /**
     * 校验表头是否是我们需要的格式
     *
     * @return
     */
    public boolean checkHeaderHandler() {
        boolean flag = true;
        List<String> aliasHeaders = Lists.newArrayList(aliasHeader.keySet());
        if (headers.size() == 0 || aliasHeaders == null) {
            return false;
        }
        //判断长度；
        if (aliasHeaders.size() > headers.size()) {
            return false;
        }
        //比较每个元素是否相等
        List<String> collect = aliasHeaders.parallelStream().filter(e -> headers.contains(e)).collect(Collectors.toList());
        return collect.size() == aliasHeaders.size();
    }
 
    /**
     * 表头转换 将excel/csv的表头中文转换成可以入库的表字段
     *
     * @param headerList
     * @param headerAlias
     * @return
     */
    private static List<String> aliasHeader(List<String> headerList, Map<String, String> headerAlias) {
        ArrayList<String> result = new ArrayList();
        if (CollUtil.isEmpty(headerList)) {
            return result;
        } else {
            String alias = null;
 
            for (Iterator iterator = headerList.iterator(); iterator.hasNext(); result.add(alias)) {
                Object headerObj = iterator.next();
                if (null != headerObj) {
                    String header = headerObj.toString();
                    alias = (String) headerAlias.get(header);
                    if (null == alias) {
                        alias = header;
                    }
                }
            }
            return result;
        }
    }
 
    /**
     * 根据excel注解将属性名和注解name值转成map
     * el：{"姓名":name,"年龄":age}
     *
     * @param clazz
     * @return
     */
    public static Map<String, String> getFiledMap(Class<?> clazz) {
        BeanDesc beanDesc = BeanUtil.getBeanDesc(clazz);
        Collection<BeanDesc.PropDesc> props = beanDesc.getProps();
        Map<String, String> filedMap = new HashMap<>(props.size());
        for (BeanDesc.PropDesc p : props) {
            Field field = beanDesc.getField(p.getFieldName());
            Excel annotation = field.getAnnotation(Excel.class);
            if (annotation != null) {
                filedMap.put(annotation.name(), p.getFieldName());
            }
        }
        return filedMap;
    }
 
    public List<Map<String, Object>> getRows() {
        return rows;
    }
}