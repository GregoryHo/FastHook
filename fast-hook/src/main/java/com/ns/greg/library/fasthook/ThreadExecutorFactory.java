package com.ns.greg.library.fasthook;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Gregory
 * @since 2017/6/15
 */

public class ThreadExecutorFactory {

  /**
   * NOTE: This is the number of total available cores. On current versions of
   * Android, with devices that use plug-and-play cores, this will return less
   * than the total number of cores. The total number of cores is not
   * available in current Android implementations.
   */
  private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

  private ThreadExecutorFactory() {
    throw new AssertionError("No instances.");
  }

  /**
   * Returns single thread executor service
   *
   * @return single thread executor service
   */
  public static ThreadPoolExecutor newSingleThreadExecutor() {
    return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());
  }

  /**
   * Returns single thread executor service with rejected execution handler
   *
   * @param rejectedExecutionHandler rejected handler
   */
  public static ThreadPoolExecutor newSingleThreadExecutor(
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(), rejectedExecutionHandler);
  }

  /**
   * Returns custom threads of executor service
   *
   * @param nThreads size of thread pool
   * @return custom threads of executor service
   */
  public static ThreadPoolExecutor newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>());
  }

  /**
   * Returns custom threads executor service with rejected execution handler
   *
   * @param nThreads size of thread pool
   * @param rejectedExecutionHandler rejected handler
   * @return custom threads of executor service
   */
  public static ThreadPoolExecutor newFixedThreadPool(int nThreads,
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(), rejectedExecutionHandler);
  }

  /**
   * Returns flexible size executor service
   *
   * @return flexible executor service
   */
  public static ThreadPoolExecutor newCacheThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>());
  }

  /**
   * Returns flexible size executor service with rejected execution handler
   *
   * @param rejectedExecutionHandler rejected handler
   * @return flexible executor service
   */
  public static ThreadPoolExecutor newCacheThreadPool(
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
        new SynchronousQueue<Runnable>(), rejectedExecutionHandler);
  }

  /**
   * Returns core size of current device executor service
   *
   * @return core size executor service
   */
  public static ThreadPoolExecutor newCoreSizeThreadPool() {
    return new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, 1L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>());
  }

  /**
   * Returns core size of current device executor service with rejected execution handler
   *
   * @param rejectedExecutionHandler rejected handler
   * @return core size executor service
   */
  public static ThreadPoolExecutor newCoreSizeThreadPool(
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ThreadPoolExecutor(NUMBER_OF_CORES, NUMBER_OF_CORES * 2, 1L, TimeUnit.SECONDS,
        new LinkedBlockingQueue<Runnable>(), rejectedExecutionHandler);
  }

  /**
   * Returns single thread scheduled executor service
   *
   * @return single thread scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newSingleScheduledThreadExecutor() {
    return new ScheduledThreadPoolExecutor(1);
  }

  /**
   * Returns single thread scheduled executor service with rejected execution handler
   *
   * @param rejectedExecutionHandler rejected handler
   */
  public static ScheduledThreadPoolExecutor newSingleScheduledThreadExecutor(
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ScheduledThreadPoolExecutor(1, rejectedExecutionHandler);
  }

  /**
   * Returns custom threads scheduled executor service
   *
   * @param nThreads size of thread pool
   * @return custom threads scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newFixedScheduledThreadPool(int nThreads) {
    return new ScheduledThreadPoolExecutor(nThreads);
  }

  /**
   * Returns custom threads scheduled executor service with rejected execution handler
   *
   * @param nThreads size of thread pool
   * @param rejectedExecutionHandler rejected handler
   * @return custom threads scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newFixedScheduledThreadPool(int nThreads,
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ScheduledThreadPoolExecutor(nThreads, rejectedExecutionHandler);
  }

  /**
   * Returns flexible size scheduled executor service
   *
   * @return flexible scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newCacheScheduledThreadPool() {
    return new ScheduledThreadPoolExecutor(0);
  }

  /**
   * Returns flexible size scheduled executor service with rejected execution handler
   *
   * @param rejectedExecutionHandler rejected handler
   * @return flexible scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newCacheScheduledThreadPool(
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ScheduledThreadPoolExecutor(0, rejectedExecutionHandler);
  }

  /**
   * Returns core size of current device scheduled executor service
   *
   * @return core size scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newCoreSizeScheduledThreadPool() {
    return new ScheduledThreadPoolExecutor(NUMBER_OF_CORES);
  }

  /**
   * Returns core size of current device scheduled executor service with rejected execution handler
   *
   * @param rejectedExecutionHandler rejected handler
   * @return core size scheduled executor service
   */
  public static ScheduledThreadPoolExecutor newCoreSizeScheduledThreadPool(
      RejectedExecutionHandler rejectedExecutionHandler) {
    return new ScheduledThreadPoolExecutor(NUMBER_OF_CORES, rejectedExecutionHandler);
  }

  public static final class Builder {

    int corePoolSize;
    int maximumPoolSize;
    long keepAliveTime;
    TimeUnit unit;
    BlockingQueue<Runnable> workQueue;
    RejectedExecutionHandler handler;

    public Builder() {
      corePoolSize = 1;
      maximumPoolSize = 1;
      keepAliveTime = 1L;
      unit = TimeUnit.SECONDS;
      workQueue = new LinkedBlockingQueue<>();
    }

    /**
     * Sets size of core pool
     *
     * @param corePoolSize pool size
     */
    public Builder setCorePoolSize(int corePoolSize) {
      this.corePoolSize = corePoolSize;
      return this;
    }

    /**
     * Sets size of maximum pool size
     *
     * @param maximumPoolSize maximum pool size
     */
    public Builder setMaximumPoolSize(int maximumPoolSize) {
      this.maximumPoolSize = maximumPoolSize;
      return this;
    }

    /**
     * Sets keep alive time of thread pool
     *
     * @param keepAliveTime keep alive time
     */
    public Builder setKeepAliveTime(long keepAliveTime) {
      this.keepAliveTime = keepAliveTime;
      return this;
    }

    /**
     * Sets unit of keep alive time
     *
     * @param unit time unit
     */
    public Builder setUnit(TimeUnit unit) {
      this.unit = unit;
      return this;
    }

    /**
     * Sets the work queue type {@link BlockingQueue}
     *
     * @param workQueue work queue
     */
    public Builder setWorkQueue(BlockingQueue<Runnable> workQueue) {
      this.workQueue = workQueue;
      return this;
    }

    /**
     * Sets rejected execution handler
     *
     * @param handler rejected handler
     */
    public Builder setHandler(RejectedExecutionHandler handler) {
      this.handler = handler;
      return this;
    }

    public ThreadPoolExecutor build() {
      if (handler == null) {
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit,
            workQueue);
      }

      return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
          handler);
    }
  }
}
