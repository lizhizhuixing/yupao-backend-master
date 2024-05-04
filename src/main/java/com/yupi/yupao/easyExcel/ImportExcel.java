package com.yupi.yupao.easyExcel;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImportExcel {
    /**
     * 最简单的读
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link XingQiuTableUserInfo}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link TableListener}
     * <p>
     * 3. 直接读即可
     */
    public static void main(String[] args) {
        String fileName = "D:\\javacode\\UserCenter\\usercenter-backend\\src\\main\\resources\\testExcel.xlsx";
        //readByListener(fileName);
        synchronousRead(fileName);
        // 写法1：JDK8+ ,不用额外写一个DemoDataListener
        // since: 3.0.0-beta1
        // 这里默认每次会读取100条数据 然后返回过来 直接调用使用数据就行
        // 具体需要返回多少行可以在`PageReadListener`的构造函数设置

    }

    //需要使用监听器
    public static void readByListener(String fileName) {
        EasyExcel.read(fileName, XingQiuTableUserInfo.class, new TableListener()).sheet().doRead();
    }

    //
    public static void synchronousRead(String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<XingQiuTableUserInfo> list = EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
        for (XingQiuTableUserInfo data : list) {
            log.info("读取到数据:{}",data);
        }

    }
}

