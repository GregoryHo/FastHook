package com.ns.greg.library.fasthook.observer;

/**
 * @author Gregory
 * @since 2016/5/20
 */
public interface BaseSubject<T> {

  /**
   * Observe the task status
   *
   * @param observer observer
   */
  void addObserver(BaseObserver<T> observer);

  /**
   * Remove the observer
   *
   * @param observer observer
   */
  void removeObserver(BaseObserver<T> observer);

  /**
   * Clears all the observer
   */
  void clearObservers();

  /**
   * Notify the observer the task is completed
   *
   * @param data result data
   */
  void notifyObserversOnCompleted(T data);

  /**
   * Notify the observer the task is interrupted
   *
   * @param data result data
   */
  void notifyObserversOnError(T data);
}
