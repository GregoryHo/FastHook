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
  private static final char SPACE = ' ';
  private static final String DOUBLE_DIVIDER =
      "────────────────────────────────────────────────────────";
  private static final String SINGLE_DIVIDER =
      "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
  private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
  private static final String NEW_LINE = "\n";
  private static final String RUNNABLE_TITLE = "Hook {";

  static void logMessage(BaseRunnable runnable, String status) {
    long startTime = runnable.getExecuteStartTime();
    long endTime = System.currentTimeMillis();

    StringBuilder builder = new StringBuilder();
    builder.append(TOP_BORDER)
        .append(NEW_LINE)
        .append(HORIZONTAL_LINE)
        .append(SPACE)
        .append(Thread.currentThread().getName())
        .append(NEW_LINE)
        .append(MIDDLE_BORDER)
        .append(NEW_LINE)
        .append(HORIZONTAL_LINE)
        .append(SPACE)
        .append(RUNNABLE_TITLE)
        .append(NEW_LINE);

    addMessage(builder, "Name: ", runnable.getThreadName() + "_" + runnable.hashCode());
    addMessage(builder, "Status: ", status);
    addMessage(builder, "Start: ", getTime(startTime));
    addMessage(builder, "End: ", getTime(endTime));
    addMessage(builder, "Executed: ", (endTime - startTime) + " ms");
    builder.append(HORIZONTAL_LINE)
        .append(SPACE)
        .append('}')
        .append(NEW_LINE)
        .append(BOTTOM_BORDER);

    Log.d(TAG, builder.toString());
  }

  private static void addMessage(StringBuilder builder, String name, String value) {
    builder.append(HORIZONTAL_LINE);
    int subSpace = 3;
    for (int s = 0; s < subSpace; s++) {
      builder.append(SPACE);
    }

    builder.append(name).append('[').append(value).append(']').append(NEW_LINE);
  }

  private static String getTime(long time) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    return simpleDateFormat.format(time);
  }
}
