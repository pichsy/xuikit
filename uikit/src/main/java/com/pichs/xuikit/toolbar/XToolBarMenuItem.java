package com.pichs.xuikit.toolbar;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

import java.io.Serializable;

public class XToolBarMenuItem implements Serializable {

    // max 1000，越大显示越靠前
    private int priority = 0;
    @XToolBarShowType
    private int showType = XToolBarShowType.ALL;
    // 是否缩进在menuList中,这个比优先级更优先，true 必定会缩进去，false超过3个放进去
    private boolean showInRoom;
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

    private Drawable background;
    private Drawable backgroundPressed;
    private int radius = 0;

    public int getRadius() {
        return radius;
    }

    public XToolBarMenuItem setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public XToolBarMenuItem() {
    }

    public XToolBarMenuItem(String text) {
        this.text = text;
        this.showType = XToolBarShowType.TEXT_ONLY;
        this.tag = text;
    }

    public XToolBarMenuItem(String text, String tag) {
        this.text = text;
        this.showType = XToolBarShowType.TEXT_ONLY;
        this.tag = tag;
    }

    public XToolBarMenuItem(int iconResId, String tag) {
        this(iconResId, 0, tag);
    }

    public XToolBarMenuItem(Drawable iconDrawable, String tag) {
        this.iconDrawable = iconDrawable;
        this.tag = tag;
        this.showType = XToolBarShowType.ICON_ONLY;
    }

    public XToolBarMenuItem(Drawable iconDrawable, int iconPadding, String tag) {
        this.iconDrawable = iconDrawable;
        this.showType = XToolBarShowType.ICON_ONLY;
        this.iconPadding = iconPadding;
        this.tag = tag;
    }

    public XToolBarMenuItem(int iconResId, int iconPadding, String tag) {
        this.iconResId = iconResId;
        this.showType = XToolBarShowType.ICON_ONLY;
        this.iconPadding = iconPadding;
        this.tag = tag;
    }

    // 默认可以用text作为tag
    public XToolBarMenuItem(String text, int iconResId) {
        this(text, iconResId, text);
    }

    public XToolBarMenuItem(String text, int iconResId, String tag) {
        this(text, iconResId, 0, tag);
    }

    public XToolBarMenuItem(String text, int iconResId, int iconPadding, String tag) {
        this.text = text;
        this.iconResId = iconResId;
        this.iconPadding = iconPadding;
        this.tag = tag;
    }

    public int getPriority() {
        return priority;
    }

    public XToolBarMenuItem setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public int getShowType() {
        return showType;
    }

    public XToolBarMenuItem setShowType(int showType) {
        this.showType = showType;
        return this;
    }

    public boolean isShowInRoom() {
        return showInRoom;
    }

    public XToolBarMenuItem setShowInRoom(boolean showInRoom) {
        this.showInRoom = showInRoom;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public XToolBarMenuItem setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getText() {
        return text;
    }

    public XToolBarMenuItem setText(String text) {
        this.text = text;
        return this;
    }

    public int getTextColor() {
        return textColor;
    }

    public XToolBarMenuItem setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }

    public float getTextSize() {
        return textSize;
    }

    public XToolBarMenuItem setTextSize(float textSize) {
        this.textSize = textSize;
        return this;
    }

    public int getIconResId() {
        return iconResId;
    }

    public XToolBarMenuItem setIconResId(int iconResId) {
        this.iconResId = iconResId;
        return this;
    }

    public Drawable getIconDrawable() {
        return iconDrawable;
    }

    public XToolBarMenuItem setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
        return this;
    }

    public int getIconColorFilter() {
        return iconColorFilter;
    }

    public XToolBarMenuItem setIconColorFilter(int iconColorFilter) {
        this.iconColorFilter = iconColorFilter;
        return this;
    }

    public int getIconPadding() {
        return iconPadding;
    }

    public XToolBarMenuItem setIconPadding(int iconPadding) {
        this.iconPadding = iconPadding;
        return this;
    }

    public int getIconMarginEnd() {
        return iconMarginEnd;
    }

    public XToolBarMenuItem setIconMarginEnd(int iconMarginEnd) {
        this.iconMarginEnd = iconMarginEnd;
        return this;
    }

    public int getIconMarginStart() {
        return iconMarginStart;
    }

    public XToolBarMenuItem setIconMarginStart(int iconMarginStart) {
        this.iconMarginStart = iconMarginStart;
        return this;
    }

    public Drawable getBackground() {
        return background;
    }

    public XToolBarMenuItem setBackground(Drawable background) {
        this.background = background;
        return this;
    }

    public Drawable getBackgroundPressed() {
        return backgroundPressed;
    }

    public XToolBarMenuItem setBackgroundPressed(Drawable backgroundPressed) {
        this.backgroundPressed = backgroundPressed;
        return this;
    }
}
