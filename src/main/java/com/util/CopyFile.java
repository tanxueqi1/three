package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class CopyFile {

  public static String fromDir = "./apidata";
  public static String toDir = "./result"; // result文件夹


  public static void main(String[] args) {
    copyFiles("1.xls", "result.xls");
  }

  /**
   * 将指定路径下的文件复制到指定文件夹中
   * 
   * @param fromFileName 被复制的excel.xls文件名称
   * @param toFileName 复制后的excel.xls文件名称
   */
  public static void copyFiles(String fromFileName, String toFileName) {
    // 被复制文件
    File copyFile = new File(fromDir + "/" + fromFileName);

    // 指定文件夹
    File copiedFolder = new File(toDir);
    try {
      // 判断文件夹是否存在,不存在需要创建，否则无法正常创建该文件夹下的文件
      if (!copiedFolder.exists()) {
        System.out.println("copiedFolder not exists " + copiedFolder);
        System.out.println("create the folder " + copiedFolder);
        copiedFolder.mkdirs();
      } // 复制后文件的路径与命名
      String copiedFilePath = toDir + "/" + toFileName;
      File copiedFile = new File(copiedFilePath);
      if (!copiedFile.exists()) {
        System.out.println("copiedFile not exists " + copiedFilePath);
        System.out.println("create the file " + copiedFilePath);
        copiedFile.createNewFile();
      }
      copyFileContent(copyFile, copiedFile);
    } catch (IOException e) {
      System.out.println("error" + e);
    }
  }

  private static void copyFileContent(File fromFile, File toFile) throws IOException {
    // TODO Auto-generated method stub
    FileInputStream ins = new FileInputStream(fromFile);
    FileOutputStream out = new FileOutputStream(toFile);
    byte[] b = new byte[1024];
    int n = 0;
    while ((n = ins.read(b)) != -1) {
      out.write(b, 0, n);
    }
    ins.close();
    out.close();
  }

}
