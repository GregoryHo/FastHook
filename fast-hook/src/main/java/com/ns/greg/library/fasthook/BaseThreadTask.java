package com.ns.greg.library.fasthook;

/**
 * @author Gregory
 * @since 2016/5/5
 */
public class BaseThreadTask implements BaseRunnable.BaseRunnableObjectMethods {

  // The Thread on which this task is currently running.
  private Thread mCurrentThread;
  // Fields containing references to the two runnable objects that handle downloading and
  // decoding of the image.
  private BaseRunnable mRunnable;
  // An object that contains the ThreadPool singleton.
  private static BaseThreadManager sBaseThreadManager;

  public BaseThreadTask(BaseRunnable runnable) {
    this.mRunnable = runnable;
  }

  /**
   * Initializes the Task
   *
   * @param baseThreadManager A ThreadPool object
   */
  void initializeTask(BaseThreadManager baseThreadManager) {
    // Sets this object's ThreadPool field to be the input argument
    sBaseThreadManager = baseThreadManager;
  }

  // Returns the runnable instance
  public BaseRunnable getRunnableObject() {
    return mRunnable;
  }

  // Sets the runnable instance
  void setRunnableObject(BaseRunnable runnable) {
    mRunnable = runnable;
  }

  /**
   * Returns the Thread that this Task is running on. The method must first get a lock on a
   * static field, in this case the ThreadPool singleton. The lock is needed because the
   * Thread object reference is stored in the Thread object itself, and that object can be
   * changed by processes outside of this app.
   */
  private Thread getCurrentThread() {
    synchronized (BaseThreadTask.class) {
      return mCurrentThread;
    }
  }

  /**
   * Sets the identifier for the current Thread. This must be a synchronized operation; see the
   * notes for getCurrentThread()
   */
  private void setCurrentThread(Thread thread) {
    synchronized (BaseThreadTask.class) {
      mCurrentThread = thread;
    }
  }

  // Delegates handling the current state of the task to the PhotoManager object
  private void handleState(int state) {
    sBaseThreadManager.handleState(this, state);
  }

  /**
   * @param currentThread the current Thread
   * @param priority 1 ~ 10 (min ~ max)
   */
  @Override public void setThread(Thread currentThread, int priority) {
    currentThread.setPriority(priority);
    setCurrentThread(currentThread);
  }

  @Override public Thread getThread() {
    return getCurrentThread();
  }

  @Override public void handleTaskState(int state) {
    // Passes the state to the ThreadPool object.
    handleState(state);
  }
}
