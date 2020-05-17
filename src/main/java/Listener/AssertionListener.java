package Listener;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.util.Verify;


/**
 * @author wangy34 Tel:13145878905
 */
public class AssertionListener extends TestListenerAdapter {

  @Override
  public void onTestStart(ITestResult tr) {
    // 打印日志到文件中
    // Browser.sysSetOutToFile();

    Verify.flag = true;
    Verify.errors.clear();
    String className = tr.getTestClass().getRealClass().getSimpleName().trim();
    String testMethodName = tr.getMethod().getMethodName();
    System.out.println("【开始执行  " + className + "." + testMethodName + "】");
  }

  @Override
  public void onTestFailure(ITestResult tr) {
    this.handleAssertion(tr);
  }

  @Override
  public void onTestSkipped(ITestResult tr) {
    this.handleAssertion(tr);
  }

  @Override
  public void onTestSuccess(ITestResult tr) {
    this.handleAssertion(tr);
  }

  @Override
  public void onFinish(ITestContext testContext) {
    System.out.println("用例结束\n");
    super.onFinish(testContext);
  }

  // 用来处理Verify验证结果失败时，标记用例结果为Failure，但是不停止执行。
  private void handleAssertion(ITestResult tr) {
    if (!Verify.flag) {
      Verify.flag = true;
      Verify.errors.clear();
      tr.setStatus(ITestResult.FAILURE);
    }
  }



}
