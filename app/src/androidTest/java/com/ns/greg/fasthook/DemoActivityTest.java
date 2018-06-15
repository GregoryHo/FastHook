package com.ns.greg.fasthook;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author gregho
 * @since 2018/6/15
 */
@RunWith(AndroidJUnit4.class) @LargeTest public class DemoActivityTest {

  @Rule public ActivityTestRule<DemoActivity> activityTestRule =
      new ActivityTestRule<>(DemoActivity.class);

  @Test public void initText() {
    findView(R.id.result_tv).check(matches(withText("false")));
  }

  public void clickStartTaskButton() {
    findView(R.id.start_task_btn).perform(click());
    findView(R.id.result_tv).check(matches(withText("true")));
  }

  private ViewInteraction findView(int viewId) {
    return onView(withId(viewId));
  }
}
