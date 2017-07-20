package com.ns.greg.fasthook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.ns.greg.fasthook.threadpool.CustomThreadManager;
import com.ns.greg.library.fasthook.BaseRunnable;
import com.ns.greg.library.fasthook.BaseThreadTask;
import com.ns.greg.library.fasthook.callback.RunCallback;
import com.ns.greg.library.fasthook.exception.EasyException;
import com.ns.greg.library.fasthook.functions.BaseRun;
import com.ns.greg.library.fasthook.observer.IThreadManagerInterface;
import java.lang.ref.WeakReference;

/**
 * Created by Gregory on 2017/2/10.
 */
public class ThreadActivity extends AppCompatActivity {

  private BaseThreadTask job0;

  private BaseThreadTask job1;

  private CustomObserver customObserver;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    CustomHandler customHandler = new CustomHandler(this);

    // #Subscribe
    //customObserver = new CustomObserver();
    //CustomThreadManager.getInstance().addObserver(customObserver);

    // Sample as EasyRun0
    CustomThreadManager.getInstance().addTask(new RunJob0()).addCallback(new RunCallback() {
      @Override public void done(BaseRun baseRun, EasyException e) {
        if (e == null) {
          System.out.println("job0 - " + " : [" + baseRun.getCommandType() + "]");
        } else {
          System.out.println("job0 - " + " : [" + false + "]");
        }
      }
    }).start();

    // Sample as EasyRun1
    job1 = CustomThreadManager.getInstance().addTask(new RunJob1()).addCallback(new RunCallback() {
      @Override public void done(BaseRun baseRun, EasyException e) {
        if (e == null) {
          System.out.println("job1 - " + "" + " : [" + baseRun.getResult1() + "]");
        } else {
          System.out.println("job1 - " + "" + " : [" + baseRun.getResult1() + "]");
        }
      }
    }).start();

    CustomThreadManager.getInstance().addTask(new BaseRunnable() {
      @Override protected BaseRun runImp() throws Exception {
        for (int i = 'a'; i <= 'z'; i++) {
          System.out.println((char) i);
        }

        return null;
      }
    }).addDelayTime(2000).addCallback(new RunCallback() {
      @Override public void done(BaseRun baseRun, EasyException e) {
        if (e == null) {
          System.out.println("job2 - " + " : [" + baseRun + "]");
        } else {
          System.out.println("job2 - " + " : [" + false + "]");
        }
      }
    }).start();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    // UnSubscribe
    if (customObserver != null) {
      CustomThreadManager.getInstance().removeObserver(customObserver);
    }

    // Release thread manager, this also clear all observers
    CustomThreadManager.clearInstance();
  }

  /**
   * The thread manager observer
   */
  private static class CustomObserver implements IThreadManagerInterface.ActionObserver {

    @Override public void onCompleted(BaseRun data) {
      Object commandType = data.getCommandType();
      if (commandType instanceof Boolean) {
        System.out.println("RunJob0 : onCompleted");
      } else if (commandType instanceof Integer) {
        int id = (int) commandType;
        switch (id) {
          case CustomThreadManager.COUNT_JOB:
            System.out.println("RunJob1 : onCompleted, counted : " + data.getResult1());
            break;

          default:
            break;
        }
      }
    }

    @Override public void onError(BaseRun data) {
      Object commandType = data.getCommandType();
      if (commandType instanceof Boolean) {
        System.out.println("RunJob0 : onError");
      } else if (commandType instanceof Integer) {
        int id = (int) commandType;
        switch (id) {
          case CustomThreadManager.COUNT_JOB:
            System.out.println("RunJob1 : onError");
            break;

          default:
            break;
        }
      }
    }
  }

  private static class CustomHandler extends Handler {

    private final ThreadActivity instance;

    CustomHandler(ThreadActivity reference) {
      WeakReference<ThreadActivity> weakReference = new WeakReference<ThreadActivity>(reference);
      instance = weakReference.get();
    }

    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
    }
  }
}
