package com.ns.greg.library.fasthook.executionHandler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Gregory
 * @since 2016/5/6
 */
public abstract class BaseRejectedExecutionHandler<T extends Runnable>
    implements RejectedExecutionHandler {

  @Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    customRejectedExecutionImp((T) r, executor);
  }

  public abstract void customRejectedExecutionImp(T r, ThreadPoolExecutor executor);
}
