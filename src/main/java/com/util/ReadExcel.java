package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class ReadExcel {

  final static Logger Log = Logger.getLogger(ReadExcel.class);

  public static void main(String[] args) {
    readExcel(new File("./result/result.xls"), "Sheet4");
    readExcel(new File("./result/result.xls"), 4);
  }

  /**
   * List<List<String>> 里面的List<String>存在的是一行的数据; 外面的List存在的是所有行的数据。
   */
  public static List<List<String>> readExcel(File readExcelPath, int sheetNameIndex) {
    try {
      // 创建输入流，读取Excel
      InputStream is = new FileInputStream(readExcelPath);
      // jxl提供的Workbook类
      Workbook wb = Workbook.getWorkbook(is);
      // 每个页签创建一个Sheet对象
      Sheet sheet = wb.getSheet(sheetNameIndex);

      List<List<String>> sheetList = new ArrayList<List<String>>();
      for (int i = 1; i < sheet.getRows(); i++) {

        List<String> rowList = new ArrayList<String>();
        for (int j = 0; j < sheet.getColumns(); j++) {
          String cellinfo = sheet.getCell(j, i).getContents();
          if (cellinfo.isEmpty()) {
            continue;
          }
          rowList.add(cellinfo);
        }
        sheetList.add(rowList);
        System.out.println();
      }

      System.out.println("读取EXCEL数据：");
      for (List<String> row : sheetList) {
        System.out.println(row);
      }
      return sheetList;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * List<List<String>> 里面的List<String>存在的是一行的数据; 外面的List存在的是所有行的数据。
   */
  public static List<List<String>> readExcel(File readExcelPath, String sheetName) {
    try {
      // 创建输入流，读取Excel
      InputStream is = new FileInputStream(readExcelPath);
      // jxl提供的Workbook类
      Workbook wb = Workbook.getWorkbook(is);
      // 每个页签创建一个Sheet对象
      Sheet sheet = wb.getSheet(sheetName);

      List<List<String>> sheetList = new ArrayList<List<String>>();
      // i=0 是第一行，这是字段行。
      for (int i = 1; i < sheet.getRows(); i++) {
        List<String> rowList = new ArrayList<String>();
        for (int j = 0; j < sheet.getColumns(); j++) {
          String cellinfo = sheet.getCell(j, i).getContents();
          // if (cellinfo.isEmpty()) {
          // continue;
          // }
          rowList.add(cellinfo);
        }
        sheetList.add(rowList);
        System.out.println();
      }

      System.out.println("读取测试数据：");
      for (List<String> row : sheetList) {
        System.out.println(row);
      }
      return sheetList;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (BiffException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
      System.err.println("\t 请检查" + readExcelPath + ":" + sheetName + ",是否真实有效！");
      Log.info("\t 请检查" + readExcelPath + ":" + sheetName + ",是否真实有效！");
    }
    return null;
  }
}
