package com.ns.greg.library.fasthook.runnable;

import java.util.concurrent.Callable;

/**
 * @author Gregory
 * @since 2016/5/24
 */
public abstract class BaseCallable<T> implements Callable<T> {

  @Override public T call() throws Exception {
    return callImpl();
  }

  public abstract T callImpl();
}
