package Listener;

import java.util.HashMap;
import java.util.Map;
import com.vimalselvam.testng.SystemInfo;


public class MySystemInfo implements SystemInfo {
  public Map<String, String> getSystemInfo() {

    Map<String, String> systemInfo = new HashMap<String, String>();
    systemInfo.put("测试人员", "淘宝店铺:军临天下2015");

    return systemInfo;

  }
}
