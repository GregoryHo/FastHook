package com.ns.greg.library.fasthook.functions;

/**
 * Created by Gregory on 2017/2/3.
 */

/**
 * A two-argument action.
 *
 * @param <C> the first argument for command type
 * @param <R1> the second argument type for return object.
 */
public class EasyRun1<C, R1> implements BaseRun {

  private C c;

  private R1 r1;

  public EasyRun1(C c, R1 r1) {
    this.c = c;
    this.r1 = r1;
  }

  @Override public C getCommandType() {
    return c;
  }

  @Override public R1 getResult1() {
    return r1;
  }

  @Override public Object getResult2() {
    return null;
  }
}
