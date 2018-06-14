package com.ns.greg.library.fasthook;

import com.ns.greg.library.fasthook.functions.BaseRun;
import com.ns.greg.library.fasthook.functions.EasyRun0;
import com.ns.greg.library.fasthook.functions.EasyRun1;
import java.util.concurrent.ThreadPoolExecutor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author gregho
 * @since 2018/6/14
 */
public class ThreadPoolManagerTest {

  @Test public void getRunnableTypeSucceeded() {
    TestHook testHook = new TestHook();
    testHook.addTask(new BaseRunnable<EasyRun0<Integer>>() {
      @Override protected EasyRun0<Integer> runImp() throws Exception {
        return new EasyRun0<>(1);
      }
    }).addCallback((baseRun, e) -> {
      int command = baseRun.getCommandType();
      Assert.assertEquals(1, command);
      Assert.assertNull(e);
    }).start();
  }

  @Test public void getRunnableTypeFailure() {
    TestHook testHook = new TestHook();
    testHook.addTask(new BaseRunnable<EasyRun0<Integer>>() {
      @Override protected EasyRun0<Integer> runImp() throws Exception {
        throw new UnknownError("Something went wrong");
      }

      @Override protected EasyRun0<Integer> interruptedImp() {
        return null;
      }
    }).addCallback((baseRun, e) -> {
      Assert.assertNull(null, baseRun);
      Assert.assertNotNull(e);
    }).start();
  }

  @Test public void getOneResultSucceeded() {
    TestHook testHook = new TestHook();
    testHook.addTask(new BaseRunnable<EasyRun1<Integer, String>>() {
      @Override protected EasyRun1<Integer, String> runImp() throws Exception {
        return new EasyRun1<>(1, "1");
      }
    }).addCallback((baseRun, e) -> {
      int command = baseRun.getCommandType();
      String result = baseRun.getResult1();
      Assert.assertNotNull(result);
      Assert.assertEquals(1, command);
      Assert.assertEquals("2", result);
      Assert.assertNull(e);
    }).start();
  }

  @Test public void getOneResultFailure() {
    TestHook testHook = new TestHook();
    testHook.addTask(new BaseRunnable<EasyRun1<Integer, String>>() {
      @Override protected EasyRun1<Integer, String> runImp() throws Exception {
        throw new UnknownError("Something went wrong");
      }
    }).addCallback((baseRun, e) -> {
      Assert.assertNull(null, baseRun);
      Assert.assertNotNull(e);
    }).start();
  }

  @Test public void shutdownImmediately() {
    TestHook testHook = new TestHook();
    testHook.shutdownAndAwaitTermination(0);
    Assert.assertEquals(true, testHook.isShutdown());
  }

  private static final class TestHook extends BaseThreadManager<ThreadPoolExecutor> {

    @Override protected ThreadPoolExecutor createThreadPool() {
      return ThreadExecutorFactory.newCacheThreadPool();
    }

    @Override protected BaseThreadTask createBaseThreadTask(BaseRunnable job) {
      return new BaseThreadTask(job);
    }
  }
}
