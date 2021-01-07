package com.pichs.xuikit.toolbar;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({XToolBarShowType.ICON_ONLY, XToolBarShowType.TEXT_ONLY, XToolBarShowType.ALL})
@Retention(RetentionPolicy.SOURCE)
public @interface XToolBarShowType {

    int ICON_ONLY = 2;
    int TEXT_ONLY = 1;
    // 如果是ALL 在ToolBar上则会只显示图片（效果=ICON_ONLY）
    int ALL = 0;

}
