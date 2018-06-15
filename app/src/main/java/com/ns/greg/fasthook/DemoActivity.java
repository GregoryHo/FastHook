package com.ns.greg.fasthook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.ns.greg.fasthook.handle.DemoHook;
import com.ns.greg.library.fasthook.BaseThreadTask;
import com.ns.greg.library.fasthook.HookPlugins;
import com.ns.greg.library.fasthook.functions.BaseRun;
import com.ns.greg.library.fasthook.functions.EasyRun1;
import com.ns.greg.library.fasthook.observer.IThreadManagerInterface;

/**
 * Created by Gregory on 2017/2/10.
 */
public class DemoActivity extends AppCompatActivity {

  private BaseThreadTask job0;
  private BaseThreadTask job1;
  private CustomObserver customObserver;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // #Subscribe
    customObserver = new CustomObserver();
    DemoHook.getInstance().addObserver(customObserver);
    findViewById(R.id.start_task_btn).setOnClickListener(v -> demoJob());
  }

  private void demoJob() {
    // Sample as EasyRun0
    DemoHook.getInstance().addTask(new DemoJob()).addCallback((baseRun, e) -> {
      if (e != null && baseRun != null) {
        boolean result = baseRun.getCommandType();
        ((TextView) findViewById(R.id.result_tv)).setText(Boolean.toString(result));
      }
    }).observerOn(HookPlugins.UI_THREAD).start();
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    // UnSubscribe
    if (customObserver != null) {
      DemoHook.getInstance().removeObserver(customObserver);
    }

    // Release thread manager, this also clear all observers
    DemoHook.clearInstance();
  }

  /**
   * The thread manager observer
   */
  private static class CustomObserver implements IThreadManagerInterface.ActionObserver {

    @Override public void onCompleted(BaseRun data) {
      Object commandType = data.getCommandType();
      if (commandType instanceof Boolean) {
        System.out.println("DemoJob : onCompleted");
      } else if (commandType instanceof Integer) {
        int id = (int) commandType;
        switch (id) {
          case DemoHook.COUNT_JOB:
            System.out.println(
                "RunJob1 : onCompleted, counted : " + ((EasyRun1) data).getResult1());
            break;

          default:
            break;
        }
      }
    }

    @Override public void onError(BaseRun data) {
      Object commandType = data.getCommandType();
      if (commandType instanceof Boolean) {
        System.out.println("DemoJob : onError");
      } else if (commandType instanceof Integer) {
        int id = (int) commandType;
        switch (id) {
          case DemoHook.COUNT_JOB:
            System.out.println("RunJob1 : onError");
            break;

          default:
            break;
        }
      }
    }
  }
}
