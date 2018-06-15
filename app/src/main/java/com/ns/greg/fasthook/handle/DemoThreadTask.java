package com.ns.greg.fasthook.handle;

import com.ns.greg.library.fasthook.BaseRunnable;
import com.ns.greg.library.fasthook.BaseThreadTask;

/**
 * @author gregho
 * @since 2018/6/15
 */
public class DemoThreadTask extends BaseThreadTask {

  public DemoThreadTask(BaseRunnable runnable) {
    super(runnable);
  }
}
