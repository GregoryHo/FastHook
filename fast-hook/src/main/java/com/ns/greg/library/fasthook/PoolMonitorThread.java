package com.ns.greg.library.fasthook;

import android.util.Log;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Gregory
 * @since 2016/5/6
 */
public class PoolMonitorThread implements Runnable {

  private static final String TAG = "PoolMonitorThread";

  private ThreadPoolExecutor executor;
  private int seconds;
  private boolean isMonitor = true;

  PoolMonitorThread(ThreadPoolExecutor executor, int delay) {
    this.executor = executor;
    this.seconds = delay;
  }

  void setMonitor(boolean enable) {
    this.isMonitor = enable;
  }

  @Override public void run() {
    while (isMonitor) {
      Log.d(TAG, String.format(
          "[Size : %d, Core : %d] Active : %d, Completed : %d, Task : %d, isShutdown : %s, isTerminated : %s",
          this.executor.getPoolSize(), this.executor.getCorePoolSize(),
          this.executor.getActiveCount(), this.executor.getCompletedTaskCount(),
          this.executor.getTaskCount(), this.executor.isShutdown(), this.executor.isTerminated()));
      try {
        Thread.sleep(seconds * 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
