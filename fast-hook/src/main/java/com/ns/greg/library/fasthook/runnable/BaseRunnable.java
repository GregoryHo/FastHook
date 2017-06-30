package com.ns.greg.library.fasthook.runnable;

import com.ns.greg.library.fasthook.callback.RunCallback;
import com.ns.greg.library.fasthook.exception.EasyException;
import com.ns.greg.library.fasthook.functions.BaseRun;
import com.ns.greg.library.fasthook.log.LogModel;

/**
 * Created by Gregory on 2016/5/5.
 */
public abstract class BaseRunnable<T extends BaseRun> implements Runnable {

  // Runnable state
  public static final int EXCEPTION_STATUS = -1;

  public static final int COMPLETE_STATUS = 0;

  private BaseRunnableObjectMethods runnableObjectMethods;

  private boolean isException = false;

  private long executeStartTime = 0L;

  private long delayTime = 0L;

  private T result;

  private RunCallback runCallback;

  private boolean isLog;

  public interface BaseRunnableObjectMethods {

    /**
     * Sets the Thread that this instance is running on
     *
     * @param currentThread the current Thread
     * @param priority the thread priority
     */
    void setThread(Thread currentThread, int priority);

    /**
     * Defines the actions for each state of the task instance.
     *
     * @param state The current state of the task
     */
    void handleTaskState(int state);

    /**
     * Gets the thread that this instance is running on
     */
    Thread getThread();
  }

  /**
   * Sets the runnable object methods
   */
  public void setRunnableObjectMethods(BaseRunnableObjectMethods runnableObjectMethods) {
    this.runnableObjectMethods = runnableObjectMethods;
  }

  public abstract T runImp() throws Exception;

  protected abstract T interruptedImp();

  public abstract String getThreadName();

  public void setLog(boolean isLog) {
    this.isLog = isLog;
  }

  @Override public void run() {
    try {
      if (runnableObjectMethods == null) {
        throw new EasyException(EasyException.RUNNABLE_OBJECT_METHOD_ERROR,
            "Runnable object methods is null");
      }

      // Set start time
      if (executeStartTime == 0L) {
        setExecuteStartTime(System.currentTimeMillis());
      }

      if (isLog) {
        LogModel.logStartTime(this);
      }

      // Set thread priority
      runnableObjectMethods.setThread(Thread.currentThread(), 5);

      // Sleep delay time
      Thread.sleep(delayTime);

      result = runImp();
    } catch (Exception e) {
      e.printStackTrace();
      isException = true;
      result = interruptedImp();
    } finally {
      // Get current task state
      int state = getTaskState();

      // Clear task state
      isException = false;

      if (isLog) {
        LogModel.logExecutedTime(this, state == COMPLETE_STATUS ? "COMPLETED" : "INTERRUPTED");
      }

      if (runnableObjectMethods != null) {
        runnableObjectMethods.handleTaskState(state);
      }
    }
  }

  public void setExecuteStartTime(long l) {
    executeStartTime = l;
  }

  private int getTaskState() {
    if (isException) {
      return EXCEPTION_STATUS;
    } else {
      return COMPLETE_STATUS;
    }
  }

  public long getExecuteStartTime() {
    return executeStartTime;
  }

  public void setDelayTime(long delayTime) {
    this.delayTime = delayTime;
  }

  public T getResult() {
    return result;
  }

  public void setRunCallback(RunCallback callback) {
    this.runCallback = callback;
  }

  public RunCallback getRunCallback() {
    return runCallback;
  }

  @Override public String toString() {
    return "WorkThread : "
        + Thread.currentThread().getName()
        + ", id : "
        + Thread.currentThread()
        .getId()
        + ", priority : "
        + Thread.currentThread().getPriority()
        + ", group : "
        + Thread.currentThread().getThreadGroup();
  }
}
