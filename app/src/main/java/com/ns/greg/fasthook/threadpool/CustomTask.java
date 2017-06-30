package com.ns.greg.fasthook.threadpool;

import com.ns.greg.library.fasthook.BaseThreadTask;
import com.ns.greg.library.fasthook.runnable.BaseRunnable;

/**
 * Created by Gregory on 2016/5/30.
 */
public class CustomTask extends BaseThreadTask {

  public CustomTask(BaseRunnable runnable) {
    super(runnable);
  }
}
