package com.ns.greg.library.fasthook.callback;

import com.ns.greg.library.fasthook.exception.EasyException;
import com.ns.greg.library.fasthook.functions.BaseRun;

/**
 * Created by Gregory on 2017/3/13.
 */
public interface RunCallback extends EasyCallback2<BaseRun, EasyException> {

  @Override void done(BaseRun baseRun, EasyException e);
}
