package com.pichs.xuikit.toolbar;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

public class XPopupMenuItem {

    private String tag;
    private String text;
    // 文字的颜色
    @ColorInt
    private int textColor;
    // 文字的大小 px 单位
    private float textSize;

    // 二选一，优先网络
    private int iconResId;
    private Drawable iconDrawable;
    @ColorInt
    private int iconColorFilter;
    // 图片的padding值，方便调整图片大小
    private int iconPadding = -1;
    private int iconMarginEnd = -1;
    private int iconMarginStart = -1;

    // 0：都显示, 1：只显示文字, 2：只显示图片
    private int showType = 0;

    private Drawable background;
    private Drawable backgroundPressed;

    private int radius = 0;

    public int getRadius() {
        return radius;
    }

    public XPopupMenuItem setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public XPopupMenuItem setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getText() {
        return text;
    }

    public XPopupMenuItem setText(String text) {
        this.text = text;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public XPopupMenuItem setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public float getTextSize() {
        return textSize;
    }

    public XPopupMenuItem setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getIconResId() {
        return iconResId;
    }

    public XPopupMenuItem setIconResId(int iconResId) {
        this.iconResId = iconResId;
        return this;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public XPopupMenuItem setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }

    public int getIconColorFilter() {
        return iconColorFilter;
    }

    public XPopupMenuItem setIconColorFilter(int iconColorFilter) {
        this.iconColorFilter = iconColorFilter;
        return this;
    }

    public int getIconPadding() {
        return iconPadding;
    }

    public XPopupMenuItem setIconPadding(int iconPadding) {
        this.iconPadding = iconPadding;
        return this;
    }

    public int getIconMarginEnd() {
        return iconMarginEnd;
    }

    public XPopupMenuItem setIconMarginEnd(int iconMarginEnd) {
        this.iconMarginEnd = iconMarginEnd;
        return this;
    }

    public int getIconMarginStart() {
        return iconMarginStart;
    }

    public XPopupMenuItem setIconMarginStart(int iconMarginStart) {
        this.iconMarginStart = iconMarginStart;
        return this;
    }

    public int getShowType() {
        return showType;
    }

    public XPopupMenuItem setShowType(int showType) {
        this.showType = showType;
        return this;
    }

    public Drawable getBackground() {
        return background;
    }

    public XPopupMenuItem setBackground(Drawable background) {
        this.background = background;
        return this;
    }

    public XPopupMenuItem setBackground(@ColorInt int color) {
        this.background = new ColorDrawable(color);
        return this;
    }

    public Drawable getBackgroundPressed() {
        return backgroundPressed;
    }

    public XPopupMenuItem setBackgroundPressed(Drawable backgroundPressed) {
        this.backgroundPressed = backgroundPressed;
        return this;
    }

    public XPopupMenuItem setBackgroundPressed(@ColorInt int color) {
        this.backgroundPressed = new ColorDrawable(color);
        return this;
    }
}
