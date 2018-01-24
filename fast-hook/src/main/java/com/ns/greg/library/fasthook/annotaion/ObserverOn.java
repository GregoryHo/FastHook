package com.ns.greg.library.fasthook.annotaion;

import android.support.annotation.IntDef;
import com.ns.greg.library.fasthook.HookPlugins;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Gregory
 * @since 2018/1/24
 */

@IntDef({ HookPlugins.UI_THREAD, HookPlugins.CURRENT_THREAD }) @Retention(RetentionPolicy.SOURCE)
public @interface ObserverOn {
}
