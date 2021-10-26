package com.bs.epc.common;

import com.bs.epc.vo.MyUserExcelVo;
import com.bs.sys.common.excel.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyUserExcelParse {
    public static List<MyUserExcelVo> myUserExcelVos(File userUploadFile) {
        List<MyUserExcelVo> list = new ArrayList<MyUserExcelVo>();
        MyUserExcelVo myUserExcelVo = null;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(userUploadFile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            //获取第一个sheet页
            HSSFSheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HSSFRow row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    myUserExcelVo = new MyUserExcelVo();
                    //将单元格里每行数据存入myUserExcelVo对象中
                    //行政区划码
//                    myUserExcelVo.setCode(ExcelUtil.formatCell(row.getCell(0)));
//                    //姓名
//                    myUserExcelVo.setName(ExcelUtil.formatCell(row.getCell(1)).split("\\.")[0]);
//                    //户籍所在地
//                    myUserExcelVo.setDomicile(ExcelUtil.formatCell(row.getCell(2)));
//                    //证件类型
//                    myUserExcelVo.setDocumenttype(ExcelUtil.formatCell(row.getCell(3)));
//                    //证件号码
//                    myUserExcelVo.setDocumentphone(ExcelUtil.formatCell(row.getCell(4)));
//                    //手机号码
//                    myUserExcelVo.setPhone(ExcelUtil.formatCell(row.getCell(5)));
//                    //常住地址
//                    myUserExcelVo.setAddress(ExcelUtil.formatCell(row.getCell(6)));
//                    //户主身份证号码
//                    myUserExcelVo.setHousehold(ExcelUtil.formatCell(row.getCell(6)));
//                    //与户主关系
//                    myUserExcelVo.setRelation(ExcelUtil.formatCell(row.getCell(7)));
//                    //备注
//                    myUserExcelVo.setRemark(ExcelUtil.formatCell(row.getCell(8)));
                    list.add(myUserExcelVo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    }
