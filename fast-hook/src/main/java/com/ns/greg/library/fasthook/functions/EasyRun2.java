package com.ns.greg.library.fasthook.functions;

/**
 * @author Gregory
 * @since 2017/2/9
 *
 * A two-argument action.
 *
 * @param <T> the first argument for command type
 * @param <R1> the second argument type for return object.
 * @param <R2> the third argument for ret return object.
 */
public class EasyRun2<T, R1, R2> implements BaseRun {

  private final T t;
  private final R1 r1;
  private final R2 r2;

  public EasyRun2(T t, R1 r1, R2 r2) {
    this.t = t;
    this.r1 = r1;
    this.r2 = r2;
  }

  @Override public T getCommandType() {
    return t;
  }

  public R1 getResult1() {
    return r1;
  }

  public R2 getResult2() {
    return r2;
  }
}
