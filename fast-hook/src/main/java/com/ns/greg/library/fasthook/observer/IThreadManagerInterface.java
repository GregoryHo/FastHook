package com.ns.greg.library.fasthook.observer;

import com.ns.greg.library.fasthook.functions.BaseRun;

/**
 * Created by Gregory on 2016/5/31.
 */
public interface IThreadManagerInterface {

  interface ActionSubject extends BaseSubject<BaseRun> {

  }

  interface ActionObserver extends BaseObserver<BaseRun> {

  }
}
