package com.ns.greg.library.fasthook.functions;

/**
 * Created by Gregory on 2017/2/9.
 */

/**
 * A two-argument action.
 *
 * @param <C> the first argument for command type
 * @param <R1> the second argument type for return object.
 * @param <R2> the third argument for ret return object.
 */
public class EasyRun2<C, R1, R2> implements BaseRun {

  private C c;

  private R1 r1;

  private R2 r2;

  public EasyRun2(C c, R1 r1, R2 r2) {
    this.c = c;
    this.r1 = r1;
    this.r2 = r2;
  }

  @Override public C getCommandType() {
    return c;
  }

  @Override public R1 getResult1() {
    return r1;
  }

  @Override public R2 getResult2() {
    return r2;
  }
}
