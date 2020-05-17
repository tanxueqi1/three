package com.tests;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONException;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import RestClient.SendRequest;
import com.util.CopyFile;
import com.util.ReadExcel;
import com.util.Verify;
import com.util.WriteExcel;

public class TestCase extends SendRequest {

    final static Logger Log = Logger.getLogger(TestCase.class);
    Verify verify = new Verify();

    // 复制文件
    String apidataExcel = "1.xls";
    String resultExcel = "1result.xls";
    File readTestcaseExcel = new File(CopyFile.fromDir + "/" + apidataExcel);
    File writeResultExcel = new File(CopyFile.toDir + "/" + resultExcel);

    @BeforeTest
    public void copy() {
        // 复制文件
        CopyFile.copyFiles(apidataExcel, resultExcel);
    }

    // 参数化
    @DataProvider(name = "ExcelSheet")
    public static Object[][] dataBase() {
        return new Object[][]{
                {"Sheet2"}, {"Sheet3"}};
    }

    // 读取每一个sheet表格中的数据，批量执行接口用例
    @Test(dataProvider = "ExcelSheet", description = "执行表格中的接口用例")
    public void testCase1(String sheetName) throws Throwable {
        Reporter.log("表格名：" + sheetName);
        // 定义每一条接口执行结果
        boolean isPass = true;

        // 读取测试数据
        List<List<String>> testData = ReadExcel.readExcel(readTestcaseExcel, sheetName);

        // 准备请求头信息
        HashMap<String, String> headermap = new HashMap<String, String>();
        headermap.put("Content-Type", "application/json"); // 这个在postman中可以查询到
        if ("Sheet3".equals(sheetName)) {
            // 添加鉴权认证
            headermap.put("Authorization",
                    "7B5EC1298B277706EF056566B4134DF3E103583BC0362A37CB4EF94A09FE117CE79789914FD346EDFDDD17EB20CFB8E5DACC7AD3A495FFEE459A33350DB19402ABE2803CBEACCEC39EF3C7D2FE29BFCB9A1370CD32E31148A07539404AE0F533702C9DAEED1FEF02DB9DCA0BB5A2FDBCF60FD36B2F3300D6DE34129A294B9773"); // 这个在postman中可以查询到
        }

        // 执行测试数据
        for (List<String> rowData : testData) {
            Log.info("\n开始执行单元格(" + sheetName + ")第(" + (testData.indexOf(rowData) + 1) + ")行");
            String rowNumber = rowData.get(0);
            String url = rowData.get(2) + rowData.get(3);
            String requestMethod = rowData.get(4);
            String json = rowData.get(6);
            String expectedStatusCode = rowData.get(7);
            // String expectedEntity = rowData.get(9);

            try {
                // 发送请求
                List<String> rowResponseData =
                        SendRequest.sendHttpRequest(requestMethod, url, headermap, json);
                // 保存请求结果
                String statusCode = rowResponseData.get(0);
                String responseEntity = rowResponseData.get(1);

                // 校验两个值是否相等
                Assert.assertEquals(statusCode, expectedStatusCode, "检验响应码是否与期望的一致");
                System.out.println("写入实际statusCode到" + writeResultExcel);
                // 写入请求响应码,statusCode
                WriteExcel.writeExcel(String.valueOf(statusCode), writeResultExcel, sheetName, 8,
                        testData.indexOf(rowData) + 1);
                // 写入请求响应消息,responseEntity
                System.out.println("写入实际responseEntity到" + writeResultExcel);
                WriteExcel.writeExcel(responseEntity, writeResultExcel, sheetName, 10,
                        testData.indexOf(rowData) + 1);
                Reporter
                        .log(rowNumber + "&nbsp&nbspSUCCESS&nbsp&nbsp" + requestMethod + "&nbsp&nbsp" + url);
                Log.info("接口校验PASS");

            } catch (JSONException jsonEx) {
                jsonEx.printStackTrace();
                Reporter
                        .log(rowNumber + "&nbsp&nbsp<font size=\"2.7\" color=\"red\">FAILURE</font>&nbsp&nbsp"
                                + requestMethod + "&nbsp&nbsp" + url);
                Log.info("\t接口" + url + " 发送请求失败,多数是因为接口传参的问题！");
                Log.error("接口校验FAIL");
                isPass = false;
            } catch (AssertionError er) {
                er.printStackTrace();
                Reporter
                        .log(rowNumber + "&nbsp&nbsp<font size=\"2.7\" color=\"red\">FAILURE</font>&nbsp&nbsp"
                                + requestMethod + "&nbsp&nbsp" + url);
                Log.info("\t接口" + url + " 发送请求失败,响应码与期望的(" + expectedStatusCode + ")不一致！");
                Log.error("接口校验FAIL");
                isPass = false;
            } catch (Exception ex) {
                ex.printStackTrace();
                Reporter
                        .log(rowNumber + "&nbsp&nbsp<font size=\"2.7\" color=\"red\">FAILURE</font>&nbsp&nbsp"
                                + requestMethod + "&nbsp&nbsp" + url);
                Log.info("\t接口" + url + " 发送请求失败,出现不可知的异常！");
                Log.error("接口校验FAIL");
                isPass = false;
            }

        }

        if (!isPass) {
            verify.assertFail();
        }

    }


}
