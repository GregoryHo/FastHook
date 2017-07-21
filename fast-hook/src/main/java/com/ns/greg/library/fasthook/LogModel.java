package com.ns.greg.library.fasthook;

import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Gregory on 2017/6/16.
 */

public class LogModel {

  private static final String TAG = "FAST-HOOk";

  /**
   * Drawing toolbox, reference from Logger by Orhan Obut
   */
  private static final char TOP_LEFT_CORNER = '┌';
  private static final char BOTTOM_LEFT_CORNER = '└';
  private static final char MIDDLE_CORNER = '├';
  private static final char HORIZONTAL_LINE = '│';
  private static final String DOUBLE_DIVIDER =
      "────────────────────────────────────────────────────────";
  private static final String SINGLE_DIVIDER =
      "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
  private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

  static void logStartTime(BaseRunnable runnable) {
    Log.d(TAG, TOP_BORDER
        + "\n"
        + HORIZONTAL_LINE
        + " "
        + Thread.currentThread().getName()
        + "\n"
        + MIDDLE_BORDER
        + "\n"
        + HORIZONTAL_LINE
        + " "
        + "Runnable:["
        + runnable.getThreadName()
        + "_"
        + runnable.hashCode()
        + "], Status:[START]"
        + ", Start Time:["
        + getCurrentTime()
        + "]"
        + "\n"
        + BOTTOM_BORDER);
  }

  static void logExecutedTime(BaseRunnable runnable, String status) {
    Log.d(TAG, TOP_BORDER
        + "\n"
        + HORIZONTAL_LINE
        + " "
        + Thread.currentThread().getName()
        + "\n"
        + MIDDLE_BORDER
        + "\n"
        + HORIZONTAL_LINE
        + " "
        + "Runnable:["
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
        + "]"
        + "\n"
        + BOTTOM_BORDER);
  }

  private static String getCurrentTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    return simpleDateFormat.format(System.currentTimeMillis());
  }
}
