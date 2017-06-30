package com.ns.greg.library.fasthook.observer;

/**
 * Created by Gregory on 2016/5/20.
 */
public interface BaseObserver<T> {

  void onCompleted(T data);

  void onError(T data);
}
