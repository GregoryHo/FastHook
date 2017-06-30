package com.ns.greg.fasthook.threadpool;

import com.ns.greg.library.fasthook.executionHandler.BaseRejectedExecutionHandler;
import com.ns.greg.library.fasthook.runnable.BaseRunnable;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Gregory on 2016/6/15.
 */
public class CustomRejectedExecutionHandler extends BaseRejectedExecutionHandler<BaseRunnable> {

  private static final String TAG = "CustomRejectedExecutionHandler";

  @Override public void customRejectedExecutionImp(BaseRunnable r, ThreadPoolExecutor executor) {
    System.out.println(r.getThreadName() + " is rejected");
  }
}
