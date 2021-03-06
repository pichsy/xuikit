package com.pichs.app.xwidget.bottom;

import androidx.annotation.ColorInt;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 3/6/21 10:15 AM
 * @UpdateUser: 吴波
 * @UpdateDate: 3/6/21 10:15 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BottomTabItemBean {

    private String title;
    @ColorInt
    private int titleSelectedColor;
    @ColorInt
    private int titleColor;
    // 单位dp
    private int titleTextSize;
    private boolean isSelected;

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public BottomTabItemBean setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BottomTabItemBean setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getTitleSelectedColor() {
        return titleSelectedColor;
    }

    public BottomTabItemBean setTitleSelectedColor(int titleSelectedColor) {
        this.titleSelectedColor = titleSelectedColor;
        return this;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public BottomTabItemBean setTitleColor(int titleColor) {
        this.titleColor = titleColor;
        return this;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public BottomTabItemBean setSelected(boolean selected) {
        isSelected = selected;
        return this;
    }
}
