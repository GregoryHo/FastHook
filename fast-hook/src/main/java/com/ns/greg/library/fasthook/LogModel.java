package com.ns.greg.library.fasthook;

import com.orhanobut.logger.Logger;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Gregory on 2017/6/16.
 */

public class LogModel {

  static void logStartTime(BaseRunnable runnable) {
    Logger.d("Runnable:["
        + runnable.getThreadName()
        + "_"
        + runnable.hashCode()
        + "], Status:[START]"
        + ", Start Time:["
        + getCurrentTime()
        + "]");
  }

  static void logExecutedTime(BaseRunnable runnable, String status) {
    Logger.d("Runnable:["
        + runnable.getThreadName()
        + "_"
        + runnable.hashCode()
        + "], Status:"
        + "["
        + status
        + "]"
        + ", End Time:["
        + getCurrentTime()
        + "], Executed time:["
        + (System.currentTimeMillis() - runnable.getExecuteStartTime())
        + "]");
  }

  private static String getCurrentTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    return simpleDateFormat.format(System.currentTimeMillis());
  }
}
