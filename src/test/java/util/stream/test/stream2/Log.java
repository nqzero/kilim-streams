/** 
 * copyright 2016 lytles/nqzero, all rights reserved. offered under the GPL+classpath exception terms
 * or the MIT license, at your choice
 */

package stream2;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Log extends TestListenerAdapter {
  public void onTestFailure(ITestResult tr) {
      Throwable cause = tr.getThrowable();
      if (cause != null)
          cause.printStackTrace();
  }
}
