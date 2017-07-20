package com.ns.greg.fasthook.threadpool;

import com.ns.greg.library.fasthook.BaseThreadTask;
import com.ns.greg.library.fasthook.BaseRunnable;

/**
 * Created by Gregory on 2016/5/30.
 */
class CustomTask extends BaseThreadTask {

  CustomTask(BaseRunnable runnable) {
    super(runnable);
  }
}
