package com.ssc.util;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssc.com.ssc.vo.PrizeVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
    public static void main(String[] args) {
        //writeCanada30SExcel();
    }

    public static void writeCanada30SExcel(List<PrizeVo> prizeVoList, String fileName) {
        if (null == prizeVoList && prizeVoList.size() == 0)  {
            return;
        }
        //第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet("加拿大30s开奖数据");
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row = sheet.createRow(0);
        //第四步，创建单元格，设置表头
       /* HSSFCell cell = row.createCell(0);
        cell.setCellValue("用户名");
        cell = row.createCell(1);
        cell.setCellValue("密码");*/

        //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        for (int i = 0; i < prizeVoList.size(); i++) {
            try {
                PrizeVo prizeVo = prizeVoList.get(i);
                HSSFRow row1 = sheet.createRow(i + 1);
                //创建单元格设值
                row1.createCell(0).setCellValue(prizeVo.getNo());
                String prize = prizeVo.getPrize();
                row1.createCell(1).setCellValue(String.valueOf(prize.charAt(0)));
                row1.createCell(2).setCellValue(String.valueOf(prize.charAt(1)));
                row1.createCell(3).setCellValue(String.valueOf(prize.charAt(2)));
                row1.createCell(4).setCellValue(String.valueOf(prize.charAt(3)));
                row1.createCell(5).setCellValue(String.valueOf(prize.charAt(4)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}