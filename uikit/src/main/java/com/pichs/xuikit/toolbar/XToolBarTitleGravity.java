package com.pichs.xuikit.toolbar;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({XToolBarTitleGravity.CENTER, XToolBarTitleGravity.LEFT})
@Retention(RetentionPolicy.SOURCE)
public @interface XToolBarTitleGravity {
    int CENTER = 0;// 居中
    int LEFT = 1;// 靠左
    // int RIGHT = 2;// 靠右
}
