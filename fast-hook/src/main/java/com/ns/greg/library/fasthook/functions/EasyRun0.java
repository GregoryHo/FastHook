package com.ns.greg.library.fasthook.functions;

/**
 * Created by Gregory on 2017/2/3.
 */

/**
 * A zero-argument action.
 */
public class EasyRun0<C> implements BaseRun {

  private C c;

  public EasyRun0(C c) {
    this.c = c;
  }

  @Override public C getCommandType() {
    return c;
  }

  @Override public Object getResult1() {
    return null;
  }

  @Override public Object getResult2() {
    return null;
  }
}
