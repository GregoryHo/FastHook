package com.ns.greg.library.fasthook.callback;

/**
 * Created by Gregory on 2017/3/13.
 */
interface EasyCallback2<T1, T2 extends Throwable> {

  void done(T1 t1, T2 t2);
}
