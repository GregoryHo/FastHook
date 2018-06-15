package com.ns.greg.fasthook.handle;

import com.ns.greg.library.fasthook.BaseRunnable;
import com.ns.greg.library.fasthook.BaseThreadManager;
import com.ns.greg.library.fasthook.BaseThreadTask;
import com.ns.greg.library.fasthook.ThreadExecutorFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Gregory on 2016/5/30.
 */
public class DemoHook extends BaseThreadManager<ThreadPoolExecutor> {

  public static final int COUNT_JOB = 0;
  private volatile static DemoHook instance;

  private DemoHook() {
  }

  public static DemoHook getInstance() {
    if (instance == null) {
      synchronized (DemoHook.class) {
        if (instance == null) {
          instance = new DemoHook();
        }
      }
    }

    return instance;
  }

  public static void clearInstance() {
    instance.clearObservers();
    instance = null;
  }

  @Override protected ThreadPoolExecutor createThreadPool() {
    return ThreadExecutorFactory.newCoreSizeThreadPool(new CustomRejectedExecutionHandler());
  }

  @Override protected BaseThreadTask createBaseThreadTask(BaseRunnable job) {
    return new DemoThreadTask(job);
  }
}
