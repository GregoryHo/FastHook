package com.ns.greg.fasthook;

import com.ns.greg.fasthook.threadpool.CustomThreadManager;
import com.ns.greg.library.fasthook.functions.EasyRun1;
import com.ns.greg.library.fasthook.runnable.BaseRunnable;

/**
 * Created by Gregory on 2017/2/10.
 */
public class RunJob1 extends BaseRunnable<EasyRun1<Integer, Integer>> {

	@Override
	public EasyRun1<Integer, Integer> runImp() throws Exception {
		int i = 0;
		while (i < 100) {
			i++;
			Thread.sleep(100);
		}

		return new EasyRun1<>(CustomThreadManager.COUNT_JOB, i);
	}

	@Override
	protected EasyRun1<Integer, Integer> interruptedImp() {
		return new EasyRun1<>(CustomThreadManager.COUNT_JOB, null);
	}

	@Override
	public String getThreadName() {
		return "CountJob";
	}
}
