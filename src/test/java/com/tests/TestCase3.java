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
import com.util.TestUtil;
import com.util.Verify;
import com.util.WriteExcel;
import RestClient.SendRequest;
import net.sf.json.JSONException;

public class TestCase3 extends SendRequest {

  final static Logger Log = Logger.getLogger(TestCase3.class);
  Verify verify = new Verify();
  TestUtil jsonValue = new TestUtil();
  String SheetName = "Sheet1";

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
      interfaceData.setL(oneRowData.get(11));
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
  public void testCase3(DefineData dd) throws Throwable {
    // 标记用例执行结果
    Boolean isPass = true;

    // 准备请求头信息
    HashMap<String, String> headermap = new HashMap<String, String>();
    headermap.put("Content-Type", "application/json"); // 这个在postman中可以查询到

    Log.info("\n开始执行单元格(" + SheetName + ")第(" + dd.getA() + ")行");
    String rowNumber = dd.getA();
    String url = dd.getC() + dd.getD();
    String requestMethod = dd.getE();
    String json = dd.getF();
    String expectedStatusCode = dd.getH();
    String expectedEntity = dd.getJ();
    expectedEntity = jsonValue.compressJson(expectedEntity);

    try {
      // 发送请求
      List<String> rowResponseData =
          SendRequest.sendHttpRequest(requestMethod, url, headermap, json);
      // 保存请求返回的结果
      String statusCode = rowResponseData.get(0);
      String responseEntity = rowResponseData.get(1);    
                 
      // 写入请求响应码,statusCode
      System.out.println("写入实际statusCode");
      WriteExcel.writeExcel(statusCode, writeResultExcel, SheetName, 8, Integer.valueOf(dd.getA()));
 
      // 写入请求响应消息,responseEntity
      System.out.println("写入实际responseEntity");
      WriteExcel.writeExcel(responseEntity, writeResultExcel, SheetName, 10,
          Integer.valueOf(dd.getA()));
      Reporter.log(rowNumber + "&nbsp&nbspSUCCESS&nbsp&nbsp" + requestMethod + "&nbsp&nbsp" + url);
      
      // 检验响应码
      verify.assertIsEquals(statusCode, expectedStatusCode, "检验响应码是否与期望的一致");     
      // 检验接口返回的json串
      System.out.println("responseEntity:"+responseEntity);
      System.out.println("JPath:"+dd.getL());
      String actual = jsonValue.getValueByJPath(responseEntity,dd.getL());
      System.out.println("actual:"+actual);
      String expected = expectedEntity;
      System.out.println("expected:"+expected);
      verify.assertIsEquals(actual, expected, "检验接口返回的数据是否与期望的一致");
      Log.info("接口校验PASS");

    } catch (AssertionError er) {
      Log.error(er.getMessage());
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
    private String L;

    /**  提取json表达式  **/
    public String getL() {
      return L;
    }

    public void setL(String l) {
      L = l;
    }

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
