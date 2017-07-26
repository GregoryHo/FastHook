package com.ns.greg.library.fasthook.callback;

import com.ns.greg.library.fasthook.exception.EasyException;
import com.ns.greg.library.fasthook.functions.BaseRun;

/**
 * Created by Gregory on 2017/3/13.
 */
public interface RunCallback<T extends BaseRun> extends EasyCallback2<T, EasyException> {

  @Override void done(T baseRun, EasyException e);
}
