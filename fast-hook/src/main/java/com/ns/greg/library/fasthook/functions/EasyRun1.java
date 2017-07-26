package com.ns.greg.library.fasthook.functions;

/**
 * Created by Gregory on 2017/2/3.
 */

/**
 * A two-argument action.
 *
 * @param <T> the first argument for command type
 * @param <R1> the second argument type for return object.
 */
public class EasyRun1<T, R1> implements BaseRun {

  private T t;

  private R1 r1;

  public EasyRun1(T t, R1 r1) {
    this.t = t;
    this.r1 = r1;
  }

  @Override public T getCommandType() {
    return t;
  }

  public R1 getResult1() {
    return r1;
  }
}
