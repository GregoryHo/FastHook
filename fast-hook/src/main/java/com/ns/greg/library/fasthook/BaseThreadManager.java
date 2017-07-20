package com.ns.greg.library.fasthook;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.ns.greg.library.fasthook.callback.RunCallback;
import com.ns.greg.library.fasthook.exception.EasyException;
import com.ns.greg.library.fasthook.functions.BaseRun;
import com.ns.greg.library.fasthook.observer.BaseObserver;
import com.ns.greg.library.fasthook.observer.IThreadManagerInterface;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gregory on 2016/5/5.
 */
public abstract class BaseThreadManager<T extends ThreadPoolExecutor>
    implements IThreadManagerInterface.ActionSubject {

  // A queue of Runnable for the Thread pool
  private final BlockingQueue<Runnable> mWorkQueue;

  // A queue of ThreadManager tasks. Tasks are handed to a ThreadPool.
  private final Queue<BaseThreadTask> mThreadTaskWorkQueue;

  // A managed pool of test Thread
  private T mThreadPool;

  // Monitor thread
  private PoolMonitorThread monitorThread;

  // Is log
  private boolean isLog = true;

  // The observers
  private final List<BaseObserver<BaseRun>> observers = new ArrayList<>();

  // Handler
  private ThreadHandler handler;

  protected BaseThreadManager() {
    // Creates a work queue for the set of of task objects,
    // using a linked list queue that blocks when the queue is empty.
    mThreadTaskWorkQueue = new LinkedBlockingQueue<BaseThreadTask>();

    // Creates a new pool type according to T
    mThreadPool = createThreadPool();

    // Gets work queue form thread pool
    mWorkQueue = mThreadPool.getQueue();

    // Creates a handler for subscribe at UI thread
    handler = new ThreadHandler(this, Looper.getMainLooper());

    Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
        .methodCount(0)
        .tag(mThreadPool.getClass().getSimpleName())
        .build()));
  }

  /**
   * Create thread pool type according to the T,
   * customize or create it by {@link ThreadExecutorFactory}
   */
  protected abstract T createThreadPool();

  /**
   * Handle the thread state {@link BaseRunnable#COMPLETE_STATUS}, {@link
   * BaseRunnable#EXCEPTION_STATUS}
   */
  void handleState(BaseThreadTask baseThreadTask, int state) {
    switch (state) {
      case BaseRunnable.COMPLETE_STATUS:
        handler.obtainMessage(state, baseThreadTask).sendToTarget();
        break;

      case BaseRunnable.EXCEPTION_STATUS:
        handler.obtainMessage(state, baseThreadTask).sendToTarget();
        break;

      default:
        break;
    }
  }

  protected abstract BaseThreadTask createBaseThreadTask(BaseRunnable job);

  /**
   * @param runnable runnable object
   */
  public Builder addTask(BaseRunnable runnable) {
    return new Builder(this, runnable);
  }

  private BaseThreadTask startTask(Builder taskOption) {
    // Gets a task from the pool of tasks, returning null if the pool is empty
    BaseThreadTask threadTask = mThreadTaskWorkQueue.poll();

    // If the queue was empty, create a new task instead.
    if (threadTask == null) {
      threadTask = createBaseThreadTask(taskOption.runnable);
    } else {
      threadTask.setRunnableObject(taskOption.runnable);
    }

    taskOption.runnable.setExecuteStartTime(0L);
    taskOption.runnable.setLog(isLog);
    taskOption.runnable.setRunnableObjectMethods(threadTask);
    threadTask.initializeTask(this);

    if (mThreadPool instanceof ScheduledThreadPoolExecutor) {
      taskOption.runnable.setExecuteStartTime(System.currentTimeMillis());
      ((ScheduledThreadPoolExecutor) mThreadPool).schedule(taskOption.runnable,
          taskOption.delayTime, TimeUnit.MILLISECONDS);
    } else {
      taskOption.runnable.setDelayTime(taskOption.delayTime);
      mThreadPool.execute(taskOption.runnable);
    }

    return threadTask;
  }

  /**
   * Interrupted thread task
   *
   * @param threadTask target task
   */
  public void removeWork(BaseThreadTask threadTask) {
    // If the Thread object still exists
    if (threadTask != null) {

      //Locks on this class to ensure that other processes aren't mutating Threads.
      synchronized (this) {

        // Gets the Thread that the downloader task is running on
        Thread thread = threadTask.getThread();

        // If the Thread exists, posts an interrupt to it
        if (thread != null) {
          thread.interrupt();

          // Removes the runnable from the ThreadPool. This opens a Thread in the
          // ThreadPool's work queue, allowing a task in the queue to start.
          mThreadPool.remove(threadTask.getRunnableObject());
        }
      }
    }
  }

  /**
   * Cancel the thread that is ing awaiting in queue
   */
  public void cancelAllWork() {
    // Creates an array of tasks that's the same size as the task work queue
    BaseRunnable[] taskArray = new BaseRunnable[mWorkQueue.size()];

    // Populates the array with the task objects in the queue
    mWorkQueue.toArray(taskArray);

    // Stores the array length in order to iterate over the array
    int taskArrayLen = taskArray.length;

    // Locks on the singleton to ensure that other processes aren't mutating Threads, then
    // iterates over the array of tasks and interrupts the task's current Thread.
    synchronized (this) {
      // Iterates over the array of tasks
      for (int taskArrayIndex = 0; taskArrayIndex < taskArrayLen; taskArrayIndex++) {
        mWorkQueue.remove(taskArray[taskArrayIndex]);
      }
    }
  }

  /**
   * Recycles tasks by calling their internal recycle() method and then putting them back into
   * the task queue.
   *
   * @param threadTask The task to recycle
   */
  private void recycleTask(BaseThreadTask threadTask) {
    // Puts the task object back into the queue for re-use.
    mThreadTaskWorkQueue.offer(threadTask);
  }

  /**
   * Is thread pool shut down
   */
  public boolean isShutdown() {
    return mThreadPool.isShutdown();
  }

  /**
   * Shuts down thread pool
   */
  public void shutDownThreadPool() {
    mThreadPool.shutdown();
  }

  /**
   * Shuts down now thread pool
   */
  public void shutDownNowThreadPool() {
    mThreadPool.shutdownNow();
  }

  /**
   * Gets the active count in thread pool
   */
  public int getActiveCount() {
    return mThreadPool.getActiveCount();
  }

  /**
   * Gets the completed task count in thread pool
   */
  public long getCompletedTaskCount() {
    return mThreadPool.getCompletedTaskCount();
  }

  /**
   * Gets the task count in thread pool
   */
  public long getTaskCount() {
    return mThreadPool.getTaskCount();
  }

  /**
   * Creates monitor that monitor thread pool
   */
  public void createMonitor(int period) {
    if (monitorThread == null) {
      ExecutorService cachedThreadPool = Executors.newSingleThreadExecutor();
      monitorThread = new PoolMonitorThread(mThreadPool, period);
      cachedThreadPool.execute(monitorThread);
    }
  }

  /**
   * Sets monitor is enable or not
   */
  public void setMonitorEnable(boolean enable) {
    if (monitorThread != null) {
      monitorThread.setMonitor(enable);
    }
  }

  /**
   * Print thread manager log
   */
  public void setLog(boolean log) {
    isLog = log;
  }

  @Override public void addObserver(BaseObserver<BaseRun> observer) {
    synchronized (observers) {
      observers.add(observer);
    }
  }

  @Override public void removeObserver(BaseObserver<BaseRun> observer) {
    synchronized (observers) {
      int i = observers.indexOf(observer);
      if (i >= 0) {
        observers.remove(i);
      }
    }
  }

  @Override public void clearAllObserver() {
    observers.clear();
  }

  @Override public void notifyObserversOnCompleted(BaseRun data) {
    synchronized (observers) {
      Iterator<BaseObserver<BaseRun>> iterator = observers.iterator();
      while (iterator.hasNext()) {
        iterator.next().onCompleted(data);
      }
    }
  }

  @Override public void notifyObserversOnError(BaseRun data) {
    synchronized (observers) {
      Iterator<BaseObserver<BaseRun>> iterator = observers.iterator();
      while (iterator.hasNext()) {
        iterator.next().onError(data);
      }
    }
  }

  private static class ThreadHandler extends Handler {

    private final BaseThreadManager instance;

    ThreadHandler(BaseThreadManager reference, Looper looper) {
      super(looper);
      WeakReference<BaseThreadManager> weakReference =
          new WeakReference<BaseThreadManager>(reference);
      instance = weakReference.get();
    }

    @Override public void handleMessage(Message inputMessage) {
      super.handleMessage(inputMessage);

      BaseThreadTask threadTask = (BaseThreadTask) inputMessage.obj;
      switch (inputMessage.what) {
        case BaseRunnable.COMPLETE_STATUS:
          BaseRun completeRun = threadTask.getRunnableObject().getResult();

          RunCallback completeCallback = threadTask.getRunnableObject().getRunCallback();
          if (completeCallback != null) {
            completeCallback.done(completeRun, null);
          }

          instance.notifyObserversOnCompleted(completeRun);
          instance.recycleTask(threadTask);

          break;

        case BaseRunnable.EXCEPTION_STATUS:
          BaseRun exceptionRun = threadTask.getRunnableObject().getResult();

          RunCallback exceptionCallback = threadTask.getRunnableObject().getRunCallback();
          if (exceptionCallback != null) {
            exceptionCallback.done(exceptionRun, new EasyException(EasyException.INTERRUPTED_ERROR,
                threadTask.getRunnableObject().getThreadName() + " got exception."));
          }

          instance.notifyObserversOnError(exceptionRun);
          instance.recycleTask(threadTask);

          break;

        default:
          // Otherwise, calls the super method
          super.handleMessage(inputMessage);
          break;
      }
    }
  }

  public static final class Builder {

    private BaseThreadManager instance;

    private BaseRunnable runnable;

    private long delayTime = 0;

    private Builder(BaseThreadManager reference, BaseRunnable runnable) {
      WeakReference<BaseThreadManager> weakReference =
          new WeakReference<BaseThreadManager>(reference);
      instance = weakReference.get();
      this.runnable = runnable;
    }

    /**
     * Sets delay time as unit MILLISECONDS
     */
    public Builder addDelayTime(long delayTime) {
      this.delayTime = delayTime;
      return this;
    }

    /**
     * Task call back, received this at [UI THREAD].
     */
    public Builder addCallback(RunCallback runCallback) {
      runnable.setRunCallback(runCallback);
      return this;
    }

    /**
     * [NOTICED] must be called, otherwise the task won't start
     */
    public BaseThreadTask start() {
      return instance.startTask(this);
    }
  }
}
