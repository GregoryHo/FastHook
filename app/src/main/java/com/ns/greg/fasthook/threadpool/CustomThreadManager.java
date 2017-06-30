package com.ns.greg.fasthook.threadpool;

import com.ns.greg.library.fasthook.BaseThreadManager;
import com.ns.greg.library.fasthook.BaseThreadTask;
import com.ns.greg.library.fasthook.ThreadExecutorFactory;
import com.ns.greg.library.fasthook.runnable.BaseRunnable;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Created by Gregory on 2016/5/30.
 */
public class CustomThreadManager extends BaseThreadManager<ScheduledThreadPoolExecutor> {

  public static final int COUNT_JOB = 0;
  private static final String TAG = "CustomThreadManager";
  private volatile static CustomThreadManager instance;

  private CustomThreadManager() {

  }

  public static CustomThreadManager getInstance() {
    if (instance == null) {
      synchronized (CustomThreadManager.class) {
        if (instance == null) {
          instance = new CustomThreadManager();
        }
      }
    }

    return instance;
  }

  public static void clearInstance() {
    instance.clearAllObserver();
    instance = null;
  }

  @Override protected ScheduledThreadPoolExecutor createThreadPool() {
    return ThreadExecutorFactory.newSingleScheduledThreadExecutor();
  }

  @Override protected BaseThreadTask createBaseThreadTask(BaseRunnable job) {
    return new CustomTask(job);
  }
}
