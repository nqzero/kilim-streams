package stream2;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Log extends TestListenerAdapter {
  public void onTestFailure(ITestResult tr) {
      Throwable cause = tr.getThrowable();
      if (cause != null) cause.printStackTrace();
  }
}
