package com.ns.greg.fasthook;

import com.ns.greg.library.fasthook.functions.EasyRun0;
import com.ns.greg.library.fasthook.BaseRunnable;

/**
 * Created by Gregory on 2017/2/10.
 */
public class RunJob0 extends BaseRunnable<EasyRun0<Boolean>> {

	@Override
	public EasyRun0<Boolean> runImp() throws Exception {
		Thread.sleep(5000);

		return new EasyRun0<>(true);
	}

	@Override
	protected EasyRun0<Boolean> interruptedImp() {
		return super.interruptedImp();
	}

	@Override
	public String getThreadName() {
		return "SleepJob";
	}
}
