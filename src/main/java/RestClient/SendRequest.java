package RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import com.alibaba.fastjson.JSON;

public class SendRequest extends RestClient {

  static RestClient restClient = new RestClient();
  static CloseableHttpResponse closeableHttpResponse;
  static int statusCode;
  static String responseEntity;

  /**
   * 调用接口，并返回结果。index(0):状态码，index(1):返回的消息体
   * 
   */
  public static List<String> sendHttpRequest(String requestMethod, String url, HashMap<String, String> headermap, Object json) throws ClientProtocolException, IOException {
    // 存储响应后的数据
    List<String> httpRespond = new ArrayList<String>();    
    if ("get".equalsIgnoreCase(requestMethod)) {
      System.out.println("调用get方法 ");          
      closeableHttpResponse = restClient.get(url, headermap);
      statusCode = restClient.getStatusCode(closeableHttpResponse);
      responseEntity = restClient.getResponseEntity(closeableHttpResponse);

    } else if ("post".equalsIgnoreCase(requestMethod)) {
      System.out.println("调用post方法 ");
      JSONObject object = JSONObject.fromObject(json);// 转为json对象
      String jsonString = JSON.toJSONString(object);// json转json字符串
      closeableHttpResponse = restClient.post(url, headermap, jsonString);
      statusCode = restClient.getStatusCode(closeableHttpResponse);
      responseEntity = restClient.getResponseEntity(closeableHttpResponse);

    } else if ("put".equalsIgnoreCase(requestMethod)) {
      System.out.println("调用put方法 ");
      JSONObject object = JSONObject.fromObject(json);// 转为json对象
      String jsonString = JSON.toJSONString(object);// json转json字符串
      closeableHttpResponse = restClient.put(url, headermap, jsonString);
      statusCode = restClient.getStatusCode(closeableHttpResponse);
      responseEntity = restClient.getResponseEntity(closeableHttpResponse);

    } else if ("delete".equalsIgnoreCase(requestMethod)) {
      System.out.println("调用Delete方法 ");
      closeableHttpResponse = restClient.delete(url);
      statusCode = restClient.getStatusCode(closeableHttpResponse);
      responseEntity = restClient.getResponseEntity(closeableHttpResponse);

    }    
    httpRespond.add(0, String.valueOf(statusCode));
    httpRespond.add(1, responseEntity);
    return httpRespond;

  }
}
