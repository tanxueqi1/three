package com.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestUtil {

  // 1 json解析方法
  /** 
   * @param responseJson 这个变量是拿到响应字符串通过json转换成json对象
   * @param jpath 这个jpath指的是用户想要查询json对象的值的路径。 
   * jpath写法举例： 1) per_page 元素的名称
   * 			 2) data[1]/first_name   data是json数组,first_name是数组中的元素。
   * @return 返回元素对应的值
   */
  public String getValueByJPath(String jsonStr, String jpath) {
    JSONObject responseJson = JSONObject.fromObject(jsonStr);
    
    Object obj = responseJson;

    for (String s : jpath.split("/")) {

      if (!s.isEmpty()) {

        if (!(s.contains("[") || s.contains("]"))) {

          obj = ((JSONObject) obj).get(s);

        } else if (s.contains("[") || s.contains("]")) {

          obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replaceAll("]", "")));

        }
      }
    }
    return obj.toString();
  }
  
  // 2 将json字符串压缩为一行( https://www.sojson.com/yasuoyihang.html )  
  /**
   * 压缩json<br/>
   * 将格式化的json字符串压缩为一行，去掉空格、tab，并把换行符改为显式的\r\n <br/>
   * ！！！只能处理正确json字符串，不对json字符串做校验
   * @param json
   * @return
   */
  public String compressJson(String json)
  {
      if (json == null)
      {
          return null;
      }
      StringBuilder sb = new StringBuilder();
      boolean skip = true;// true 允许截取(表示未进入string双引号)
      boolean escaped = false;// 转义符
      for (int i = 0; i < json.length(); i++)
      {
          char c = json.charAt(i);
          if (!escaped && c == '\\')
          {
              escaped = true;
          }
          else
          {
              escaped = false;
          }
          if (skip)
          {
              if (c == ' ' || c == '\r' || c == '\n' || c == '\t')
              {
                  continue;
              }
          }
          sb.append(c);
          if (c == '"' && !escaped)
          {
              skip = !skip;
          }
      }
      return sb.toString().replaceAll("\r\n", "\\\\r\\\\n");
  }
  
  public static void main(String[] args) {
    TestUtil jsonValue = new TestUtil();
	  String jsonStr = "{\r\n" + 
	      "  \"code\": 200,\r\n" + 
	      "  \"data\": {\r\n" + 
	      "    \"deductibleTotal\": \"\",\r\n" + 
	      "    \"dutyList\": [\r\n" + 
	      "      {\r\n" + 
	      "        \"attributeList\": [],\r\n" + 
	      "        \"dutyCode\": \"H068PD11\",\r\n" + 
	      "        \"dutyDeductible\": \"\",\r\n" + 
	      "        \"dutyId\": \"zr88pd005\",\r\n" + 
	      "        \"dutyItemList\": [\r\n" + 
	      "          {\r\n" + 
	      "            \"attributeList\": [],\r\n" + 
	      "            \"dutyCode\": \"H068PX01\",\r\n" + 
	      "            \"dutyDeductible\": \"\",\r\n" + 
	      "            \"dutyId\": \"zr88px001\",\r\n" + 
	      "            \"dutyItemList\": [\r\n" + 
	      "              {\r\n" + 
	      "                \"attributeList\": [],\r\n" + 
	      "                \"dutyCode\": \"H068PX03\",\r\n" + 
	      "                \"dutyDeductible\": \"\",\r\n" + 
	      "                \"dutyId\": \"zr88px003\",\r\n" + 
	      "                \"dutyItemList\": [],\r\n" + 
	      "                \"dutyLimitCount\": 1,\r\n" + 
	      "                \"dutyLimitCountRemain\": 1,\r\n" + 
	      "                \"dutyLimitCountUsed\": 0,\r\n" + 
	      "                \"dutyLimitType\": \"限次\",\r\n" + 
	      "                \"dutyLimitation\": \"\",\r\n" + 
	      "                \"dutyLimitationRemain\": \"\",\r\n" + 
	      "                \"dutyLimitationUsed\": \"\",\r\n" + 
	      "                \"dutyName\": \"全口涂氟\",\r\n" + 
	      "                \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "                \"dutyUnit\": \"\",\r\n" + 
	      "                \"factorGroupNo\": \"\",\r\n" + 
	      "                \"factorList\": [],\r\n" + 
	      "                \"productGroupId\": \"cpxzzr0005\",\r\n" + 
	      "                \"productId\": \"cp68pd001\",\r\n" + 
	      "                \"waiting\": \"0\"\r\n" + 
	      "              }\r\n" + 
	      "            ],\r\n" + 
	      "            \"dutyLimitCount\": 1,\r\n" + 
	      "            \"dutyLimitCountRemain\": 1,\r\n" + 
	      "            \"dutyLimitCountUsed\": 0,\r\n" + 
	      "            \"dutyLimitType\": \"限次\",\r\n" + 
	      "            \"dutyLimitation\": \"\",\r\n" + 
	      "            \"dutyLimitationRemain\": \"\",\r\n" + 
	      "            \"dutyLimitationUsed\": \"\",\r\n" + 
	      "            \"dutyName\": \"挂号建档\",\r\n" + 
	      "            \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "            \"dutyUnit\": \"\",\r\n" + 
	      "            \"factorGroupNo\": \"\",\r\n" + 
	      "            \"factorList\": [],\r\n" + 
	      "            \"productGroupId\": \"cpxzzr0003\",\r\n" + 
	      "            \"productId\": \"cp68pd001\",\r\n" + 
	      "            \"waiting\": \"0\"\r\n" + 
	      "          },\r\n" + 
	      "          {\r\n" + 
	      "            \"attributeList\": [],\r\n" + 
	      "            \"dutyCode\": \"H068PX02\",\r\n" + 
	      "            \"dutyDeductible\": \"\",\r\n" + 
	      "            \"dutyId\": \"zr88px002\",\r\n" + 
	      "            \"dutyItemList\": [],\r\n" + 
	      "            \"dutyLimitCount\": 1,\r\n" + 
	      "            \"dutyLimitCountRemain\": 1,\r\n" + 
	      "            \"dutyLimitCountUsed\": 0,\r\n" + 
	      "            \"dutyLimitType\": \"限次\",\r\n" + 
	      "            \"dutyLimitation\": \"\",\r\n" + 
	      "            \"dutyLimitationRemain\": \"\",\r\n" + 
	      "            \"dutyLimitationUsed\": \"\",\r\n" + 
	      "            \"dutyName\": \"全面口腔检查\",\r\n" + 
	      "            \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "            \"dutyUnit\": \"\",\r\n" + 
	      "            \"factorGroupNo\": \"\",\r\n" + 
	      "            \"factorList\": [],\r\n" + 
	      "            \"productGroupId\": \"cpxzzr0004\",\r\n" + 
	      "            \"productId\": \"cp68pd001\",\r\n" + 
	      "            \"waiting\": \"0\"\r\n" + 
	      "          },\r\n" + 
	      "          {\r\n" + 
	      "            \"attributeList\": [],\r\n" + 
	      "            \"dutyCode\": \"H068PX03\",\r\n" + 
	      "            \"dutyDeductible\": \"\",\r\n" + 
	      "            \"dutyId\": \"zr88px003\",\r\n" + 
	      "            \"dutyItemList\": [],\r\n" + 
	      "            \"dutyLimitCount\": 1,\r\n" + 
	      "            \"dutyLimitCountRemain\": 1,\r\n" + 
	      "            \"dutyLimitCountUsed\": 0,\r\n" + 
	      "            \"dutyLimitType\": \"限次\",\r\n" + 
	      "            \"dutyLimitation\": \"\",\r\n" + 
	      "            \"dutyLimitationRemain\": \"\",\r\n" + 
	      "            \"dutyLimitationUsed\": \"\",\r\n" + 
	      "            \"dutyName\": \"全口涂氟\",\r\n" + 
	      "            \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "            \"dutyUnit\": \"\",\r\n" + 
	      "            \"factorGroupNo\": \"\",\r\n" + 
	      "            \"factorList\": [],\r\n" + 
	      "            \"productGroupId\": \"cpxzzr0005\",\r\n" + 
	      "            \"productId\": \"cp68pd001\",\r\n" + 
	      "            \"waiting\": \"0\"\r\n" + 
	      "          },\r\n" + 
	      "          {\r\n" + 
	      "            \"attributeList\": [],\r\n" + 
	      "            \"dutyCode\": \"H068PX04\",\r\n" + 
	      "            \"dutyDeductible\": \"\",\r\n" + 
	      "            \"dutyId\": \"zr88px004\",\r\n" + 
	      "            \"dutyItemList\": [],\r\n" + 
	      "            \"dutyLimitCount\": 2,\r\n" + 
	      "            \"dutyLimitCountRemain\": 2,\r\n" + 
	      "            \"dutyLimitCountUsed\": 0,\r\n" + 
	      "            \"dutyLimitType\": \"限次\",\r\n" + 
	      "            \"dutyLimitation\": \"\",\r\n" + 
	      "            \"dutyLimitationRemain\": \"\",\r\n" + 
	      "            \"dutyLimitationUsed\": \"\",\r\n" + 
	      "            \"dutyName\": \"窝沟封闭\",\r\n" + 
	      "            \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "            \"dutyUnit\": \"\",\r\n" + 
	      "            \"factorGroupNo\": \"\",\r\n" + 
	      "            \"factorList\": [],\r\n" + 
	      "            \"productGroupId\": \"cpxzzr0006\",\r\n" + 
	      "            \"productId\": \"cp68pd001\",\r\n" + 
	      "            \"waiting\": \"0\"\r\n" + 
	      "          },\r\n" + 
	      "          {\r\n" + 
	      "            \"attributeList\": [],\r\n" + 
	      "            \"dutyCode\": \"H068PX07\",\r\n" + 
	      "            \"dutyDeductible\": \"\",\r\n" + 
	      "            \"dutyId\": \"zr88px007\",\r\n" + 
	      "            \"dutyItemList\": [],\r\n" + 
	      "            \"dutyLimitCount\": -1,\r\n" + 
	      "            \"dutyLimitCountRemain\": -1,\r\n" + 
	      "            \"dutyLimitCountUsed\": 0,\r\n" + 
	      "            \"dutyLimitType\": \"限次\",\r\n" + 
	      "            \"dutyLimitation\": \"\",\r\n" + 
	      "            \"dutyLimitationRemain\": \"\",\r\n" + 
	      "            \"dutyLimitationUsed\": \"\",\r\n" + 
	      "            \"dutyName\": \"口腔健康宣教\",\r\n" + 
	      "            \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "            \"dutyUnit\": \"\",\r\n" + 
	      "            \"factorGroupNo\": \"\",\r\n" + 
	      "            \"factorList\": [],\r\n" + 
	      "            \"productGroupId\": \"cpxzzr0054\",\r\n" + 
	      "            \"productId\": \"cp68pd001\",\r\n" + 
	      "            \"waiting\": \"0\"\r\n" + 
	      "          }\r\n" + 
	      "        ],\r\n" + 
	      "        \"dutyLimitCount\": -1,\r\n" + 
	      "        \"dutyLimitCountRemain\": -1,\r\n" + 
	      "        \"dutyLimitCountUsed\": 0,\r\n" + 
	      "        \"dutyLimitType\": \"限次\",\r\n" + 
	      "        \"dutyLimitation\": \"\",\r\n" + 
	      "        \"dutyLimitationRemain\": \"\",\r\n" + 
	      "        \"dutyLimitationUsed\": \"\",\r\n" + 
	      "        \"dutyName\": \"预防齿科医疗保险金\",\r\n" + 
	      "        \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "        \"dutyUnit\": \"\",\r\n" + 
	      "        \"factorGroupNo\": \"\",\r\n" + 
	      "        \"factorList\": [],\r\n" + 
	      "        \"productGroupId\": \"cpxzzr0001\",\r\n" + 
	      "        \"productId\": \"cp68pd001\",\r\n" + 
	      "        \"waiting\": \"0\"\r\n" + 
	      "      },\r\n" + 
	      "      {\r\n" + 
	      "        \"attributeList\": [],\r\n" + 
	      "        \"dutyCode\": \"H088PD12\",\r\n" + 
	      "        \"dutyDeductible\": \"\",\r\n" + 
	      "        \"dutyId\": \"zr88pd002\",\r\n" + 
	      "        \"dutyItemList\": [],\r\n" + 
	      "        \"dutyLimitCount\": null,\r\n" + 
	      "        \"dutyLimitCountRemain\": null,\r\n" + 
	      "        \"dutyLimitCountUsed\": null,\r\n" + 
	      "        \"dutyLimitType\": \"限额\",\r\n" + 
	      "        \"dutyLimitation\": \"3000.00\",\r\n" + 
	      "        \"dutyLimitationRemain\": \"3000.00\",\r\n" + 
	      "        \"dutyLimitationUsed\": \"0\",\r\n" + 
	      "        \"dutyName\": \"保险金\",\r\n" + 
	      "        \"dutyPayRate\": \"1.0000\",\r\n" + 
	      "        \"dutyUnit\": \"\",\r\n" + 
	      "        \"factorGroupNo\": \"\",\r\n" + 
	      "        \"factorList\": [],\r\n" + 
	      "        \"productGroupId\": \"cpxzzr0002\",\r\n" + 
	      "        \"productId\": \"cp68pd001\",\r\n" + 
	      "        \"waiting\": \"0\"\r\n" + 
	      "      }\r\n" + 
	      "    ],\r\n" + 
	      "    \"dutyPlanCode\": \"H068PJ01\",\r\n" + 
	      "    \"dutyPlanName\": \"牙无忧齿科医疗保险计划一\",\r\n" + 
	      "    \"insuranceArea\": \"1\",\r\n" + 
	      "    \"payRateTotal\": \"\",\r\n" + 
	      "    \"policyEffictiveDate\": null,\r\n" + 
	      "    \"policyExpiredDate\": null,\r\n" + 
	      "    \"policyState\": \"\",\r\n" + 
	      "    \"policyStateCode\": \"\",\r\n" + 
	      "    \"productCode\": \"H068P\",\r\n" + 
	      "    \"productId\": \"cp68pd001\",\r\n" + 
	      "    \"productName\": \"护牙无忧齿科医疗保险\"\r\n" + 
	      "  },\r\n" + 
	      "  \"message\": \"查询成功\",\r\n" + 
	      "  \"success\": true,\r\n" + 
	      "  \"timestamp\": 1587525185\r\n" + 
	      "}";
	  
	  String value1 = jsonValue.getValueByJPath(jsonStr,"code");
	  String value2 = jsonValue.getValueByJPath(jsonStr,"data/dutyPlanCode");
	  String value3 = jsonValue.getValueByJPath(jsonStr,"data/dutyList[0]/dutyCode");
	  String value4 = jsonValue.getValueByJPath(jsonStr,"data/dutyList[0]/dutyItemList[0]/dutyCode");
	  String value5 = jsonValue.getValueByJPath(jsonStr,"data/dutyList[0]/dutyItemList[0]/dutyItemList[0]/dutyCode");	
	  String value6 = jsonValue.getValueByJPath(jsonStr,"data/dutyList[0]/dutyItemList[2]/dutyCode");
	  System.out.println(value1);
	  System.out.println(value2);
	  System.out.println(value3);
	  System.out.println(value4);	 
	  System.out.println(value5); 
	  System.out.println(value6); 	  
	 
  }


}
