package com.ns.greg.library.fasthook.runnable;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Gregory on 2016/5/24.
 */
public abstract class BaseFutureTask<T> extends FutureTask<T> {

  public BaseFutureTask(Callable<T> callable) {
    super(callable);
  }

  public BaseFutureTask(Runnable runnable, T result) {
    super(runnable, result);
  }
}
