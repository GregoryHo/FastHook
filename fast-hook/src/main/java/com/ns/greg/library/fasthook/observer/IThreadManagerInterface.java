package com.ns.greg.library.fasthook.observer;

import com.ns.greg.library.fasthook.functions.BaseRun;

/**
 * Created by Gregory on 2016/5/31.
 */
public interface IThreadManagerInterface {

  /**
   * Task action subject
   */
  interface ActionSubject extends BaseSubject<BaseRun> {
    // Nothing to plug-in
  }

  /**
   * Task action observer
   */
  interface ActionObserver extends BaseObserver<BaseRun> {
    // Nothing to plug-in
  }
}
