package com.ns.greg.library.fasthook.log;

import android.util.Log;
import com.ns.greg.library.fasthook.runnable.BaseRunnable;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Gregory on 2017/6/16.
 */

public class LogModel {

  private static final String TAG = "FAST-HOOK";
  private static final String TOP_BORDER = "=====================================================";
  private static final String LEFT_BORDER = "|| ";
  private static final String BOTTOM_BORDER =
      "=====================================================";

  public static void logStartTime(BaseRunnable runnable) {
    String str = TOP_BORDER
        + "\n"
        + LEFT_BORDER
        + "Runnable : "
        + runnable.getThreadName()
        + "_"
        + runnable.hashCode()
        + "\n"
        + LEFT_BORDER
        + "Status : "
        + "[START]"
        + "\n"
        + LEFT_BORDER
        + "Time : "
        + getCurrentTime()
        + "\n"
        + BOTTOM_BORDER;

    Log.d(TAG, str);
  }

  public static void logExecutedTime(BaseRunnable runnable, String status) {
    String str = TOP_BORDER
        + "\n"
        + LEFT_BORDER
        + "Runnable : "
        + runnable.getThreadName()
        + "_"
        + runnable.hashCode()
        + "\n"
        + LEFT_BORDER
        + "Status : "
        + "["
        + status
        + "]"
        + "\n"
        + LEFT_BORDER
        + "Time : "
        + getCurrentTime()
        + "\n"
        + LEFT_BORDER
        + "Executed time : "
        + (System.currentTimeMillis() - runnable.getExecuteStartTime())
        + " ms"
        + "\n"
        + BOTTOM_BORDER;

    Log.d(TAG, str);
  }

  private static String getCurrentTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    return simpleDateFormat.format(System.currentTimeMillis());
  }
}
