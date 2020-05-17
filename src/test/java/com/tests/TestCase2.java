package com.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.util.CopyFile;
import com.util.ReadExcel;
import com.util.Verify;
import com.util.WriteExcel;
import RestClient.SendRequest;
import net.sf.json.JSONException;

public class TestCase2 extends SendRequest {

  final static Logger Log = Logger.getLogger(TestCase2.class);
  Verify verify = new Verify();
  static private String SheetName = "Sheet3";

  // 复制文件
  String apidataExcel = "1.xls";
  String resultExcel = "1result.xls";
  File readTestcaseExcel = new File(CopyFile.fromDir + "/" + apidataExcel);
  File writeResultExcel = new File(CopyFile.toDir + "/" + resultExcel);

  @BeforeClass
  public void copy() {
    // 复制文件
    CopyFile.copyFiles(apidataExcel, resultExcel);
  }

  // 参数化,返回Iterator<Object[]>
  @DataProvider(name = "onlyOneSheet")
  public Iterator<Object[]> onlyOneSheet() {
    // 读取测试数据
    List<List<String>> sheetData = ReadExcel.readExcel(readTestcaseExcel, SheetName);
    //
    List<Object> listObject = new ArrayList<Object>();
    for (List<String> oneRowData : sheetData) {
      // 添加一行数据
      DefineData interfaceData = new DefineData();
      interfaceData.setA(oneRowData.get(0));
      interfaceData.setB(oneRowData.get(1));
      interfaceData.setC(oneRowData.get(2));
      interfaceData.setD(oneRowData.get(3));
      interfaceData.setE(oneRowData.get(4));
      interfaceData.setF(oneRowData.get(5));
      interfaceData.setG(oneRowData.get(6));
      interfaceData.setH(oneRowData.get(7));
      interfaceData.setI(oneRowData.get(8));
      interfaceData.setJ(oneRowData.get(9));
      interfaceData.setK(oneRowData.get(10));
      listObject.add(interfaceData);
    }

    List<Object[]> ArrayObject = new ArrayList<Object[]>();
    for (Object obj : listObject) {
      // 做一个形式转换
      ArrayObject.add(new Object[] {obj});
    }
    return ArrayObject.iterator();

  }

  // 只读取单一个sheet表格中的数据
  @Test(dataProvider = "onlyOneSheet", description = "执行一个表格中的接口用例")
  public void testCase2(DefineData dd) throws Throwable {
    // 标记用例执行结果
    Boolean isPass = true;

    // 准备请求头信息
    HashMap<String, String> headermap = new HashMap<String, String>();
    headermap.put("Content-Type", "application/json"); // 这个在postman中可以查询到

    // 添加鉴权认证
    headermap.put("Authorization",
        "7B5EC1298B277706EF056566B4134DF3E103583BC0362A37CB4EF94A09FE117CE79789914FD346EDFDDD17EB20CFB8E5DACC7AD3A495FFEE459A33350DB19402ABE2803CBEACCEC39EF3C7D2FE29BFCB9A1370CD32E31148A07539404AE0F533702C9DAEED1FEF02DB9DCA0BB5A2FDBCF60FD36B2F3300D6DE34129A294B9773"); // 这个在postman中可以查询到

    Log.info("\n开始执行单元格(" + SheetName + ")第(" + dd.getA() + ")行");
    String rowNumber = dd.getA();
    String url = dd.getC() + dd.getD();
    String requestMethod = dd.getE();
    String json = dd.getF();
    String expectedStatusCode = dd.getH();
    String expectedEntity = dd.getJ();

    try {
      // 发送请求
      List<String> rowResponseData =
          SendRequest.sendHttpRequest(requestMethod, url, headermap, json);
      // 保存请求结果
      String statusCode = rowResponseData.get(0);
      String responseEntity = rowResponseData.get(1);

      // 校验两个值是否相等
      verify.assertIsEquals(statusCode, expectedStatusCode, "检验响应码是否与期望的一致");
      // 写入请求响应码,statusCode
      System.out.println("写入实际statusCode到" + writeResultExcel);
      WriteExcel.writeExcel(statusCode, writeResultExcel, SheetName, 8, Integer.valueOf(dd.getA()));
      //
      verify.assertIsEquals(responseEntity, expectedEntity, "检验接口返回的数据是否与期望的一致");
      // 写入请求响应消息,responseEntity
      System.out.println("写入实际responseEntity到" + writeResultExcel);
      WriteExcel.writeExcel(responseEntity, writeResultExcel, SheetName, 10,
          Integer.valueOf(dd.getA()));
      Reporter.log(rowNumber + "&nbsp&nbspSUCCESS&nbsp&nbsp" + requestMethod + "&nbsp&nbsp" + url);
      Log.info("接口校验PASS");

    } catch (AssertionError er) {
      er.printStackTrace();
      Reporter.log(rowNumber + "&nbsp&nbsp<font size=\"2.7\" color=\"red\">FAILURE</font>&nbsp&nbsp"
          + requestMethod + "&nbsp&nbsp" + url);
      Log.info("\t接口" + url + "  " + er.getMessage());
      Log.error("接口校验FAIL");
      isPass = false;
    } catch (JSONException jsonEx) {
      jsonEx.printStackTrace();
      Reporter.log(rowNumber + "&nbsp&nbsp<font size=\"2.7\" color=\"red\">FAILURE</font>&nbsp&nbsp"
          + requestMethod + "&nbsp&nbsp" + url);
      Log.info("\t接口" + url + " 发送请求失败,多数是因为接口传参的问题！");
      Log.error("接口校验FAIL");
      isPass = false;
    } catch (Exception ex) {
      ex.printStackTrace();
      Reporter.log(rowNumber + "&nbsp&nbsp<font size=\"2.7\" color=\"red\">FAILURE</font>&nbsp&nbsp"
          + requestMethod + "&nbsp&nbsp" + url);
      Log.info("\t接口" + url + " 发送请求失败,出现不可知的异常！");
      Log.error("接口校验FAIL");
      isPass = false;
    }

    //
    if (!isPass) {
      verify.assertFail();
    }

  }

  public class DefineData {
    private String A;
    private String B;
    private String C;
    private String D;
    private String E;
    private String F;
    private String G;
    private String H;
    private String I;
    private String J;
    private String K;

    /** 行号 **/
    public String getA() {
      return A;
    }

    public void setA(String a) {
      A = a;
    }

    /** 接口说明 **/
    public String getB() {
      return B;
    }

    public void setB(String b) {
      B = b;
    }

    /** 接口地址 **/
    public String getC() {
      return C;
    }

    public void setC(String c) {
      C = c;
    }

    /** 接口请求地址 **/
    public String getD() {
      return D;
    }

    public void setD(String d) {
      D = d;
    }

    /** 接口请求方法 **/
    public String getE() {
      return E;
    }

    public void setE(String e) {
      E = e;
    }

    /** 请求数据类型 **/
    public String getF() {
      return F;
    }

    public void setF(String f) {
      F = f;
    }

    /** 请求数据内容 **/
    public String getG() {
      return G;
    }

    public void setG(String g) {
      G = g;
    }

    /** 响应码 **/
    public String getH() {
      return H;
    }

    public void setH(String h) {
      H = h;
    }

    /** 实际响应码 **/
    public String getI() {
      return I;
    }

    public void setI(String i) {
      I = i;
    }

    /** 期望响应结果 **/
    public String getJ() {
      return J;
    }

    public void setJ(String j) {
      J = j;
    }

    /** 实际响应结果 **/
    public String getK() {
      return K;
    }

    public void setK(String k) {
      K = k;
    }

  }

}
