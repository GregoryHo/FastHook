package com.ns.greg.library.fasthook.observer;

/**
 * Created by Gregory on 2016/5/20.
 */
public interface BaseSubject<T> {

  void addObserver(BaseObserver<T> observer);

  void removeObserver(BaseObserver<T> observer);

  void clearAllObserver();

  void notifyObserversOnCompleted(T data);

  void notifyObserversOnError(T data);
}
