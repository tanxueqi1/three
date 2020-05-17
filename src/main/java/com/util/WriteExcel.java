package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteExcel {

  final static Logger Log = Logger.getLogger(WriteExcel.class);
  private static final String EXCEL_XLS = "xls";
  private static final String EXCEL_XLSX = "xlsx";

  public static void main(String[] args) {
    writeExcel("AAA1", new File("./result/result.xls"), 4, 1, 10);
    writeExcel("AAA1", new File("./result/result1.xls"), 4, 1, 10);
    writeExcel("AAA1", new File("./result/result.xls"), 0, 1, 10);
    writeExcel("C1C1C1", new File("./result/result.xls"), 0, 8, 2);

  }

  /* 写入测试结果 */
  public static void writeExcel(String writeData, File writeExcelPath, String sheetName, int colNum, int rowNum) {
    try {
      // 构造一个单元格
      Workbook wb = getWorkbook(writeExcelPath);
      Sheet sheet = wb.getSheet(sheetName);
      Row row = sheet.getRow(rowNum);
      if (!(row == null)) {
        Cell cell = row.createCell(colNum);
        // 设置单元格的值
        cell.setCellValue(writeData);

        // 设置单元格样式
        {
          CellStyle cellStyle = wb.createCellStyle();
          // 设置字体
          Font font = wb.createFont();
          font.setFontName("Tahoma");// 设置字体名称
          font.setFontHeightInPoints((short) 11);// 设置字号
          font.setItalic(false);// 设置是否为斜体
          font.setBold(true);// 设置是否加粗
          font.setColor(IndexedColors.BRIGHT_GREEN.index);// 设置字体颜色
          cellStyle.setFont(font);
          // 设置背景
          // cellStyle.setFillPattern(FillPatternType.DIAMONDS);//填充图案，然没有什么实际用处。
          cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
          // 渲染单元格
          cell.setCellStyle(cellStyle);
        }

        // 写入Excel文件
        OutputStream out = new FileOutputStream(writeExcelPath);
        wb.write(out);
        out.flush();
        out.close();
        System.out.println("写入数据到Excel: " + writeExcelPath);
      } else {
        System.err.println("指定的行(" + writeExcelPath + ":" + rowNum + ")没有测试数据。请检查输入行是否为空行。");
        Log.info("指定的行(" + writeExcelPath + ":" + rowNum + ")没有测试数据。请检查输入行是否为空行。");
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
      System.err.println("\t  请检查" + writeExcelPath + ":" + sheetName + ",是否真实有效！");
      Log.info("\t  请检查" + writeExcelPath + ":" + sheetName + ",是否真实有效！");
    }

  }

  public static void writeExcel(String writeData, File writeExcelPath, int sheetNameIndex, int colNum, int rowNum) {
    try {
      Workbook workBook = getWorkbook(writeExcelPath);
      Sheet sheet = workBook.getSheetAt(sheetNameIndex);
      Row row = sheet.getRow(rowNum);
      if (!(row == null)) {
        Cell cell = row.createCell(colNum);
        cell.setCellValue(writeData);

        OutputStream out = new FileOutputStream(writeExcelPath);
        workBook.write(out);
        out.flush();
        out.close();
        System.out.println("写入数据到Excel: " + writeExcelPath);
      } else {
        System.out.println("指定的行(" + writeExcelPath + ":" + rowNum + ")没有测试数据。请检查输入行是否为空行。");
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
      System.err.println("\t  请检查" + writeExcelPath + ":sheetNameIndex(" + sheetNameIndex + "),下标是否越界！");
    }

  }

  // 判断Excel的版本,获取Workbook
  public static Workbook getWorkbook(File file) throws IOException {
    Workbook wb = null;
    FileInputStream in = new FileInputStream(file);
    if (file.getName().endsWith(EXCEL_XLS)) { // Excel&nbsp;2003
      wb = new HSSFWorkbook(in);
    } else if (file.getName().endsWith(EXCEL_XLSX)) { // Excel 2007/2010
      wb = new XSSFWorkbook(in);
    }
    return wb;
  }

  // java POI实现Excel单元格自定义颜色
  @SuppressWarnings("resource")
  public static void setCellStyle(File writeExcelPath, String sheetName, int colNum, int rowNum) {

    try {
      // 创建Excel工作簿对象
      Workbook wb = getWorkbook(writeExcelPath);
      // 在工作簿中创建工作表对象
      Sheet sheet = wb.getSheet(sheetName);
      // 在工作表中创建行对象
      Row row = sheet.createRow(rowNum);
      // 在第一行创建单元格对象
      Cell cell = row.createCell(colNum);
      // 单元格赋值
      // cell.setCellValue("姓名");
      CellStyle cellStyle = wb.createCellStyle();
      // // 设置水平居中
      // cellStyle.setAlignment(HorizontalAlignment.CENTER);
      // // 设置垂直居中
      // cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
      // // 设置下边框
      // cellStyle.setBorderBottom(BorderStyle.THIN);
      // // 设置上边框
      // cellStyle.setBorderTop(BorderStyle.THIN);
      // // 设置走边框
      // cellStyle.setBorderLeft(BorderStyle.THIN);
      // // 设置右边框
      // cellStyle.setBorderRight(BorderStyle.THIN);
      // 设置字体
      HSSFFont font = new HSSFWorkbook().createFont();
      font.setFontName("华文行楷");// 设置字体名称
      font.setFontHeightInPoints((short) 28);// 设置字号
      font.setItalic(false);// 设置是否为斜体
      font.setBold(true);// 设置是否加粗
      font.setColor(IndexedColors.GREEN.index);// 设置字体颜色
      cellStyle.setFont(font);
      // 设置背景
      cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      cellStyle.setFillForegroundColor(IndexedColors.WHITE.index);
      // 设置宽度和高度
      // row.setHeightInPoints(30);// 设置行的高度
      // sheet.setColumnWidth(0, 20 * 256);// 设置列的宽度
      // 渲染单元格
      cell.setCellStyle(cellStyle);
      OutputStream out = new FileOutputStream(writeExcelPath);
      wb.write(out);
      out.flush();
      out.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
