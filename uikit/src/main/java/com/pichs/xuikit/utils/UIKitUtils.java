package com.pichs.xuikit.utils;

import android.content.Context;

import com.pichs.common.widget.utils.XDisplayHelper;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 2021/1/7 11:29
 * @UpdateUser: 吴波
 * @UpdateDate: 2021/1/7 11:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UIKitUtils {


    public static int dp2px(Context context, int dpValue) {
        return XDisplayHelper.dp2px(context, dpValue);
    }

}
