package com.ns.greg.library.fasthook.functions;

/**
 * Created by Gregory on 2017/2/3.
 */

/**
 * A zero-argument action.
 */
public class EasyRun0<T> implements BaseRun {

  private T t;

  public EasyRun0(T t) {
    this.t = t;
  }

  @Override public T getCommandType() {
    return t;
  }
}
