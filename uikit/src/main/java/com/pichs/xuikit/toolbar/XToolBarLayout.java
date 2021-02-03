package com.pichs.xuikit.toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.core.content.ContextCompat;

import com.pichs.common.utils.utils.ToastUtils;
import com.pichs.common.widget.cardview.XCardRelativeLayout;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.utils.XStatusBarHelper;
import com.pichs.common.widget.view.XImageView;
import com.pichs.common.widget.view.XTextView;
import com.pichs.xuikit.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @author pichs 2020-02-25
 * 自定义toolbar，解决系统toolbar图标大小不能调的问题
 * 增加自动弹窗设置配置
 */
public class XToolBarLayout extends XCardRelativeLayout {

    private Context mContext;
    private int mCustomSize;
    private int mCustomTextPadding;
    private int mHorizontalSpacing = 0;

    private ImageView mIvClose;
    private LinearLayout mBackLayout;
    private TextView mTvTitle;
    private LinearLayout mMenuLayout;

    private int maxInFrame = 3;
    // menu + 图片
    // 最大为3，最小为1

    private ImageView mIvBack;
    private int optionColor;
    private Drawable optionDrawable;
    // 一个Drawable只用给一个View用，否则点击效果会联动
    private Drawable optionBackground;
    private int optionMarginEnd;
    private int optionPadding;

    private List<XToolBarMenuItem> mMenuList;
    private List<XToolBarMenuItem> mMenuInToolBarList;
    private List<XToolBarMenuItem> mMenuInPopList;

    // popupWindow 的背景
    private int mPopupMenuWidth;

    private int statusBarHeight;
    private boolean isStatusBarFitted = false;
    private XPopupMenu mPopupMenu;

    // 点击事件返回
    private OnXToolBarBackClickListener mBackClickListener;
    private OnXToolBarMenuClickListener mMenuClickListener;
    //
    private int item_textColor;
    private int item_iconColorFilter;
    private int item_layoutPressedColor;
    private int menu_backgroundColor;
    private int item_dividerColor;
    private int item_dividerHeight;
    private int item_dividerMarginEnd;
    private int item_dividerMarginStart;
    // 菜单弹窗的圆角
    private int mPopMenuBackgroundRadius;
    private int mPopupMenuArrowWidth;
    private int mPopupMenuArrowHeight;
    private int mPopupMenuItemHeight;

    public void setOnBackClickListener(OnXToolBarBackClickListener onBackClickListener) {
        this.mBackClickListener = onBackClickListener;
    }

    public void setOnMenuClickListener(OnXToolBarMenuClickListener onMenuClickListener) {
        this.mMenuClickListener = onMenuClickListener;
    }

    public XToolBarLayout(Context context) {
        this(context, null);
    }

    public XToolBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XToolBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        this.mCustomSize = XDisplayHelper.dp2px(context, 34f);
        this.mCustomTextPadding = XDisplayHelper.dp2px(context, 4f);
        statusBarHeight = XStatusBarHelper.getStatusBarHeight(context);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.xuikit_xtoolbar_layout, this);
        mIvBack = (ImageView) findViewById(R.id.xid_iv_back);
        mIvClose = (ImageView) findViewById(R.id.xid_iv_close);
        mBackLayout = (LinearLayout) findViewById(R.id.xid_back_ll);
        mTvTitle = (TextView) findViewById(R.id.xid_title);
        // menu
        mMenuLayout = (LinearLayout) findViewById(R.id.xid_menu_ll);
        // 处理属性数据
        TypedArray ta = context.getResources().obtainAttributes(attrs, R.styleable.XToolBarLayout);

        // 返回键
        setBackIcon(ta.getDrawable(R.styleable.XToolBarLayout_xp_toolbar_backIcon));
        setBackIconBackground(ta.getDrawable(R.styleable.XToolBarLayout_xp_toolbar_backIcon_background));
        setBackIconColorFilter(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_backIcon_colorFilter, 0));
        setBackIconPadding(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_backIcon_padding, 0));
        setBackIconMarginStart(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_backIcon_marginStart, 0));
        setBackIconVisibility(ta.getInt(R.styleable.XToolBarLayout_xp_toolbar_backIcon_visibility, 0));

        // 关闭键
        setCloseIcon(ta.getDrawable(R.styleable.XToolBarLayout_xp_toolbar_closeIcon));
        setCloseIconBackground(ta.getDrawable(R.styleable.XToolBarLayout_xp_toolbar_closeIcon_background));
        setCloseIconPadding(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_closeIcon_padding, 0));
        setCloseIconColorFilter(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_closeIcon_colorFilter, 0));
        setCloseIconMarginStart(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_closeIcon_marginStart, 0));
        setCloseIconVisibility(ta.getInt(R.styleable.XToolBarLayout_xp_toolbar_closeIcon_visibility, 1));

        // 是否适应StatusBar ， 配合XStatusBarHelper.translucent(); 实现沉浸式状态栏
        setFitSystemStatusBar(ta.getBoolean(R.styleable.XToolBarLayout_xp_toolbar_fitSystemStatusBar, false));

        // title
        setTitle(ta.getText(R.styleable.XToolBarLayout_xp_toolbar_title));
        setTitleTextSize(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_title_textSize, 0));
        setTitleTextColor(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_title_textColor, 0));
        setTitleTextStyle(ta.getInt(R.styleable.XToolBarLayout_xp_toolbar_title_TextStyle, 0));
        // 0 center， 1 left
        setTitleGravity(ta.getInt(R.styleable.XToolBarLayout_xp_toolbar_title_layoutGravity, 0));
        setTitleMarginStart(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_title_marginStart, 0));
        setTitleMarginEnd(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_title_marginEnd, 0));

        // menu
        setMaxInFrame(ta.getInteger(R.styleable.XToolBarLayout_xp_toolbar_menu_maxInFrame, 3));
        // menu color
        setOptionColor(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_menu_optionIcon_colorFilter, 0));
        // menu marginEnd
        setOptionMarginEnd(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_menu_optionIcon_marginEnd, 0));
        setOptionPadding(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_menu_optionIcon_padding, 0));
        setOptionDrawable(ta.getDrawable(R.styleable.XToolBarLayout_xp_toolbar_menu_optionIcon));
        setOptionBackground(ta.getDrawable(R.styleable.XToolBarLayout_xp_toolbar_menu_optionIconBackground));

        // toolbar上的 menu 水平间距
        setHorizontalSpacing(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_menu_horizontalSpacing, 0));
        setPopupMenuWidth(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenu_width, 0));
        // 0，light 1，dark
        setPopupMenuBackgroundColor(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_popupMenu_backgroundColor, Color.WHITE));
        setPopupMenuItemTextColor(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_textColor, 0));
        setPopupMenuItemDividerColor(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_dividerColor, Color.LTGRAY));
        setPopupMenuItemDividerHeight(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_dividerHeight, 1));
        setPopupMenuItemDividerMarginEnd(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_dividerMarginEnd, XDisplayHelper.dp2px(context, 16)));
        setPopupMenuItemDividerMarginStart(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_dividerMarginStart, XDisplayHelper.dp2px(context, 16)));
        setPopupMenuItemLayoutPressedColor(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_layoutPressedColor, Color.parseColor("#555555")));
        setPopupMenuItemIconColorFilter(ta.getColor(R.styleable.XToolBarLayout_xp_toolbar_popupMenuItem_iconColorFilter, 0));
        setPopupMenuBackgroundRadius(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenu_backgroundRadius, XDisplayHelper.dp2px(context, 6)));
        setPopupMenuArrowHeight(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenu_arrowHeight, XDisplayHelper.dp2px(context, 6)));
        setPopupMenuArrowWidth(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenu_arrowWidth, XDisplayHelper.dp2px(context, 12)));
        setPopupMenuItemHeight(ta.getDimensionPixelSize(R.styleable.XToolBarLayout_xp_toolbar_popupMenu_itemHeight, 0));
        ta.recycle();

        initListener();
    }

    private void initListener() {
        mIvBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackClickListener != null) {
                    mBackClickListener.onBackClick(v);
                }
            }
        });
        mIvClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBackClickListener != null) {
                    mBackClickListener.onCloseClick(v);
                }
            }
        });
    }

    private XToolBarLayout setPopupMenuItemDividerMarginStart(int marginStart) {
        this.item_dividerMarginStart = marginStart;
        return this;
    }


    private XToolBarLayout setPopupMenuItemDividerMarginEnd(int marginEnd) {
        this.item_dividerMarginEnd = marginEnd;
        return this;
    }

    private XToolBarLayout setPopupMenuItemDividerHeight(int height) {
        this.item_dividerHeight = height;
        return this;
    }

    private XToolBarLayout setPopupMenuItemHeight(int itemHeight) {
        this.mPopupMenuItemHeight = itemHeight;
        return this;
    }

    private XToolBarLayout setPopupMenuArrowWidth(int arrowWidth) {
        this.mPopupMenuArrowWidth = arrowWidth;
        return this;
    }

    private XToolBarLayout setPopupMenuArrowHeight(int arrowHeight) {
        this.mPopupMenuArrowHeight = arrowHeight;
        return this;
    }

    private XToolBarLayout setPopupMenuBackgroundRadius(int radius) {
        this.mPopMenuBackgroundRadius = radius;
        return this;
    }

    public XToolBarLayout setPopupMenuItemIconColorFilter(int color) {
        this.item_iconColorFilter = color;
        return this;
    }

    public XToolBarLayout setPopupMenuItemLayoutPressedColor(int color) {
        this.item_layoutPressedColor = color;
        return this;
    }

    public XToolBarLayout setPopupMenuItemDividerColor(int color) {
        this.item_dividerColor = color;
        return this;
    }

    public XToolBarLayout setPopupMenuItemTextColor(int color) {
        this.item_textColor = color;
        return this;
    }

    /**
     * ==========================================
     * =========   FitSystemStatusBar   =========
     * ==========================================
     *
     * @param isFitSystemStatusBar 设置沉浸式，toolbar将设置 状态栏高度大小（自动计算）的padding
     */
    public void setFitSystemStatusBar(boolean isFitSystemStatusBar) {
        if (isFitSystemStatusBar) {
            setPadding(getPaddingLeft(), statusBarHeight + getPaddingTop(), getPaddingRight(), getPaddingBottom());
            isStatusBarFitted = true;
            if (mContext instanceof Activity) {
                XStatusBarHelper.translucent((Activity) mContext);
            }
        } else {
            if (isStatusBarFitted) {
                setPadding(getPaddingLeft(), getPaddingTop() - statusBarHeight, getPaddingRight(), getPaddingBottom());
            }
        }
    }


    /**
     * ==========================================
     * =============   back icon   ==============
     * ==========================================
     */
    /**
     * 设置返回按钮 图片
     *
     * @param resId DrawableRes
     */
    public void setBackIcon(@DrawableRes int resId) {
        if (resId != 0) {
            setBackIcon(ContextCompat.getDrawable(mContext, resId));
        }
    }

    /**
     * 设置返回按钮 图片
     *
     * @param backIcon DrawableRes
     */
    public void setBackIcon(Drawable backIcon) {
        if (backIcon != null) {
            mIvBack.setImageDrawable(backIcon);
        }
    }

    /**
     * 设置返回按钮的背景
     *
     * @param background Drawable
     */
    public void setBackIconBackground(Drawable background) {
        if (background != null) {
            mIvBack.setBackground(background);
        }
    }

    /**
     * 设置返回按钮隐藏显示
     *
     * @param visibility {@link View#VISIBLE} or {@link View#GONE}
     */
    public void setBackIconVisibility(int visibility) {
        mIvBack.setVisibility((visibility == 0) ? VISIBLE : GONE);

    }

    /**
     * 设置返回按钮的Padding
     *
     * @param padding int
     */
    public void setBackIconPadding(int padding) {
        mIvBack.setPadding(padding, padding, padding, padding);
    }

    /**
     * 设置 关闭按钮的MarginStart
     *
     * @param backIconMarginStart int
     */
    private void setBackIconMarginStart(int backIconMarginStart) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIvBack.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        layoutParams.leftMargin = backIconMarginStart;
        mIvBack.setLayoutParams(layoutParams);

    }

    /**
     * 设置 关闭按钮的颜色过滤器
     *
     * @param color Color
     */
    public void setBackIconColorFilter(@ColorInt int color) {
        mIvBack.setColorFilter(color);
    }

    /**
     * ==========================================
     * =============   close icon  ==============
     * ==========================================
     */
    /**
     * 设置关闭按钮 图片
     *
     * @param resId DrawableRes
     */
    public void setCloseIcon(@DrawableRes int resId) {
        if (resId != 0) {
            setCloseIcon(ContextCompat.getDrawable(mContext, resId));
        }
    }

    /**
     * 设置关闭按钮 图片
     *
     * @param closeIcon Drawable
     */
    public void setCloseIcon(Drawable closeIcon) {
        if (closeIcon != null) {
            mIvClose.setImageDrawable(closeIcon);
        }
    }

    /**
     * 设置关闭按钮的背景
     *
     * @param background Drawable
     */
    public void setCloseIconBackground(Drawable background) {
        if (background != null) {
            mIvClose.setBackground(background);
        }
    }

    /**
     * 设置关闭按钮隐藏显示
     *
     * @param visibility {@link View#VISIBLE} or {@link View#GONE}
     */
    public void setCloseIconVisibility(int visibility) {
        mIvClose.setVisibility((visibility == 0) ? VISIBLE : GONE);

    }

    /**
     * 设置关闭按钮的Padding
     *
     * @param padding int
     */
    public void setCloseIconPadding(int padding) {
        mIvClose.setPadding(padding, padding, padding, padding);
    }

    /**
     * 设置 关闭按钮的MarginStart
     *
     * @param closeIconMarginStart int
     */
    public void setCloseIconMarginStart(int closeIconMarginStart) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIvClose.getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        layoutParams.leftMargin = closeIconMarginStart;
        mIvClose.setLayoutParams(layoutParams);

    }

    /**
     * 设置 关闭按钮的颜色过滤器
     *
     * @param color Color
     */
    public void setCloseIconColorFilter(@ColorInt int color) {
        mIvClose.setColorFilter(color);
    }

    /**
     * ==========================================
     * =============    title text ==============
     * ==========================================
     */

    /**
     * 获取标题
     *
     * @return TextView
     */
    public TextView getTitleView() {
        return mTvTitle;
    }

    /**
     * 标题
     *
     * @param title CharSequence
     */
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(GONE);
        } else {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        }
    }

    /**
     * 标题的字体颜色
     *
     * @param color TextColor
     */
    public void setTitleTextColor(@ColorInt int color) {
        if (color != 0) {
            mTvTitle.setTextColor(color);
        }
    }

    /**
     * 标题的字体大小
     *
     * @param textSize textSize
     */
    public void setTitleTextSize(int textSize) {
        if (textSize != 0) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    /**
     * 设置title的textStyle
     *
     * @param textStyle textStyle
     *                  {0 normal,1 Bold, 2 Italic}
     */
    public void setTitleTextStyle(@IntRange(from = 0, to = 2) int textStyle) {
        if (textStyle == 0) {
            mTvTitle.setTypeface(Typeface.DEFAULT);
        } else if (textStyle == 1) {
            mTvTitle.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            mTvTitle.setTypeface(null, Typeface.ITALIC);
        }
    }

    /**
     * 设置Title 的LayoutGravity
     *
     * @param gravity {@link XToolBarTitleGravity#CENTER is default value}
     */
    public void setTitleGravity(@XToolBarTitleGravity int gravity) {
        if (gravity == XToolBarTitleGravity.LEFT) {
            mTvTitle.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        } else {
            // 这里需要计算一下margin最左边距
            mTvTitle.setGravity(Gravity.CENTER);
        }
    }

    /**
     * 设置Title 的 marginEnd
     *
     * @param marginEnd int
     */
    public void setTitleMarginEnd(int marginEnd) {
        LayoutParams layoutParams = (LayoutParams) mTvTitle.getLayoutParams();
        layoutParams.rightMargin = marginEnd;
        mTvTitle.setLayoutParams(layoutParams);

    }

    /**
     * 设置Title 的 marginStart
     *
     * @param marginStart int
     */
    public void setTitleMarginStart(int marginStart) {
        LayoutParams layoutParams = (LayoutParams) mTvTitle.getLayoutParams();
        layoutParams.leftMargin = marginStart;
        mTvTitle.setLayoutParams(layoutParams);

    }

    /**
     * 设置 最大允许在上面显示个数
     * 最小是1 ，最大是3， 建议显示在toolbar上的字小于3个
     *
     * @param max 最大显示个数 1~3 个
     */
    public XToolBarLayout setMaxInFrame(@IntRange(from = 1, to = 3) int max) {
        if (max < 1) {
            max = 1;
        } else if (max > 3) {
            max = 3;
        }
        this.maxInFrame = max;
        refreshMenuList();

        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param optionColor 扩展菜单图标的 Color
     * @return XToolBarLayout
     */
    public XToolBarLayout setOptionColor(@ColorInt int optionColor) {
        this.optionColor = optionColor;
        refreshMenuList();

        return this;
    }


    public XToolBarLayout setOptionBackground(Drawable drawable) {
        this.optionBackground = drawable;
        refreshMenuList();

        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param optionMarginEnd 扩展菜单图标的 MarginEnd
     * @return XToolBarLayout
     */
    public XToolBarLayout setOptionMarginEnd(int optionMarginEnd) {
        this.optionMarginEnd = optionMarginEnd;
        refreshMenuList();

        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param optionPadding 扩展菜单图标 padding
     * @return XToolBarLayout
     */
    public XToolBarLayout setOptionPadding(int optionPadding) {
        this.optionPadding = optionPadding;
        refreshMenuList();

        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param optionDrawable 扩展菜单图标
     * @return XToolBarLayout
     */
    public XToolBarLayout setOptionDrawable(Drawable optionDrawable) {
        this.optionDrawable = optionDrawable;
        if (optionDrawable == null) {
            this.optionDrawable = ContextCompat.getDrawable(mContext, R.drawable.xuikit_icon_plus_black);
        }
        refreshMenuList();
        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param color 弹出菜单的背景气泡 的背景颜色
     * @return XToolBarLayout
     */
    public XToolBarLayout setPopupMenuBackgroundColor(int color) {
        this.menu_backgroundColor = color;
        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param popupMenuWidth 弹出菜单的宽度
     * @return XToolBarLayout
     */
    public XToolBarLayout setPopupMenuWidth(int popupMenuWidth) {
        this.mPopupMenuWidth = popupMenuWidth;
        return this;
    }

    /**
     * 菜单栏的横向间距，只对在菜单上的item有效
     * 设置完属性以后调用 {@link #refreshMenuList()} 刷新状态
     *
     * @param horizontalSpacing 间距
     * @return XToolBarLayout
     */
    public XToolBarLayout setHorizontalSpacing(int horizontalSpacing) {
        this.mHorizontalSpacing = horizontalSpacing;
        return this;
    }

    /**
     * 刷新布局数据
     *
     * @param list 列表
     */
    public void refreshMenuList(List<XToolBarMenuItem> list) {
        this.mMenuList = list;
        refreshMenuList();

    }

    /**
     * ==============================================================
     * ---------               menu 数据处理               -----------
     * ==============================================================
     */
    public void setMenuList(List<XToolBarMenuItem> list) {
        this.mMenuList = list;
        refreshMenuList();

    }

    public List<XToolBarMenuItem> getMenuList() {
        return this.mMenuList;
    }


    public XToolBarMenuItem getMenuListItemByTag(String tag) {
        if (this.mMenuList != null && this.mMenuList.size() > 0) {
            for (XToolBarMenuItem item : mMenuList) {
                if (TextUtils.equals(tag, item.getTag())) {
                    return item;
                }
            }
        }
        return null;
    }

    // update menu list
    public void refreshMenuList() {
        if (mMenuList != null && !mMenuList.isEmpty()) {
            mMenuLayout.setVisibility(VISIBLE);
            initMenuList();
        } else {
            // 没数据
            mMenuLayout.setVisibility(GONE);
        }
    }

    // 初始化数据列表，分类
    private void initMenuList() {
        if (mMenuInPopList == null) {
            mMenuInPopList = new ArrayList<>();
        }
        if (mMenuInToolBarList == null) {
            mMenuInToolBarList = new ArrayList<>();
        }
        mMenuInPopList.clear();
        mMenuInToolBarList.clear();

        Collections.sort(mMenuList, (item1, item2) -> item1.getPriority() - item2.getPriority());

        /*
         * 1、存放规则，有要求缩进菜单里，就缩进
         * 没有求的，按priority优先级排序
         */
        List<XToolBarMenuItem> temp = new ArrayList<>();
        for (int i = 0; i < mMenuList.size(); i++) {
            XToolBarMenuItem item = mMenuList.get(i);
            if (item.isShowInRoom()) {
                mMenuInPopList.add(item);
            } else {
                temp.add(item);
            }
        }

        // 如果 普通数据大于3，必须缩进, optionIcon占一位
        if (temp.size() > maxInFrame) {
            copyList(temp, mMenuInPopList, mMenuInToolBarList, maxInFrame - 1);
        } else if (!mMenuInPopList.isEmpty()) {
            // 没有特殊要求，但是总数大于3
            copyList(temp, mMenuInPopList, mMenuInToolBarList, maxInFrame - 1);
        } else {
            /*
             * 2、如果 poplist中没有数据，并且toolbarlist中数据少于三个
             * 则不用弹窗，不显示加号（optionIcon）
             */
            mMenuInToolBarList.addAll(temp);
        }
        temp.clear();

        // 数据处理好,数据初始化
        boolean isAddOptionIcon = !mMenuInPopList.isEmpty();
        List<XToolBarMenuItem> addedList = new ArrayList<>();
        if (isAddOptionIcon) {
            if (!mMenuInToolBarList.isEmpty()) {
                // 都添加进去，最后再添加一个optionIcon
                addedList.addAll(mMenuInToolBarList);
            }
            XToolBarMenuItem optionMenuItem = new XToolBarMenuItem(optionDrawable, "__xtb_plus_00_optionIcon__");
            optionMenuItem.setBackground(optionBackground);
            optionMenuItem.setIconColorFilter(optionColor);
            optionMenuItem.setIconPadding(optionPadding);
            optionMenuItem.setIconMarginEnd(optionMarginEnd);
            addedList.add(optionMenuItem);
            // 初始化popupWindow
            initPopupMenuView();
        } else {
            // 不需要，则说明数量少于=3
            addedList.addAll(mMenuInToolBarList);
        }
        // 初始化toolbar上的数据
        initMenuLayout(addedList);
    }

    // toobar menu上的数据
    private void initMenuLayout(List<XToolBarMenuItem> addedList) {
        mMenuLayout.setGravity(CENTER_VERTICAL);
        mMenuLayout.setOrientation(LinearLayout.HORIZONTAL);
        int childCount = mMenuLayout.getChildCount();
        if (childCount > 0) {
            mMenuLayout.removeAllViews();
        }
        for (int i = 0; i < addedList.size(); i++) {
            XToolBarMenuItem item = addedList.get(i);

            if (item.getShowType() == XToolBarShowType.TEXT_ONLY) {
                if (TextUtils.isEmpty(item.getText())) {
                    throw new RuntimeException("this is TEXT_ONLY type ,text can't be null when index== " + i);
                }
                XTextView textView = new XTextView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        mCustomSize + mCustomTextPadding * 2, mCustomSize);
                params.gravity = Gravity.CENTER;
                textView.setGravity(Gravity.CENTER);
                textView.setMaxWidth(mCustomSize + mCustomTextPadding * 2);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                textView.setSingleLine();
                if (i != addedList.size() - 1) {
                    params.setMarginEnd(item.getIconMarginEnd() + mHorizontalSpacing);
                } else {
                    params.setMarginEnd(item.getIconMarginEnd());
                }
                textView.setLayoutParams(params);
                textView.setText(item.getText());
                if (item.getTextColor() != 0) {
                    textView.setTextColor(item.getTextColor());
                } else if (optionColor != 0) {
                    textView.setTextColor(optionColor);
                }
                if (item.getTextSize() != 0) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, item.getTextSize());
                }
                // 设置背景的点击效果
                if (item.getBackground() != null) {
                    textView.setBackground(item.getBackground());
                } else {
                    textView.setBackground(cloneDrawable(optionBackground));
                }
                final int position = i;
                textView.setOnClickListener(v -> {
                    if (mMenuClickListener != null) {
                        mMenuClickListener.onItemClick(item.getTag(), textView, position);
                    }
                });
                mMenuLayout.addView(textView);
            } else { // 其他两种在toolbar上效果一样
                XImageView imageView = new XImageView(mContext);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mCustomSize, mCustomSize);
                params.gravity = Gravity.CENTER;
                if (i != addedList.size() - 1) {
                    params.setMarginEnd(item.getIconMarginEnd() + mHorizontalSpacing);
                } else {
                    params.setMarginEnd(item.getIconMarginEnd());
                }
                imageView.setLayoutParams(params);
                // 设置背景的点击效果
                if (item.getBackground() != null) {
                    imageView.setBackground(item.getBackground());
                } else {
                    imageView.setBackground(cloneDrawable(optionBackground));
                }

                if (item.getIconDrawable() != null) {
                    imageView.setImageDrawable(item.getIconDrawable());
                } else if (item.getIconResId() != 0) {
                    imageView.setImageResource(item.getIconResId());
                }

                if (item.getIconPadding() != 0) {
                    imageView.setPadding(item.getIconPadding(), item.getIconPadding(),
                            item.getIconPadding(), item.getIconPadding());
                }

                if (item.getIconColorFilter() != -1) {
                    imageView.setColorFilterOverride(item.getIconColorFilter());
                } else if (optionColor != 0) {
                    imageView.setColorFilterOverride(optionColor);
                }

                // menu + 点击事件处理
                if ("__xtb_plus_00_optionIcon__".equals(item.getTag())) {
                    imageView.setOnClickListener(this::showPopMenu);
                } else {
                    final int position = i;
                    imageView.setOnClickListener(v -> {
                        if (mMenuClickListener != null) {
                            mMenuClickListener.onItemClick(item.getTag(), v, position);
                        }
                    });
                }
                mMenuLayout.addView(imageView);
            }
        }
        mMenuLayout.invalidate();
    }

    // 数据拷贝
    private static void copyList(List<XToolBarMenuItem> temp,
                                 List<XToolBarMenuItem> popList,
                                 List<XToolBarMenuItem> toolbarList,
                                 int index) {
        for (int i = 0; i < temp.size(); i++) {
            XToolBarMenuItem item = temp.get(i);
            if (i < index) {
                toolbarList.add(item);
            } else {
                popList.add(item);
            }
        }
    }

    /**
     * 克隆Drawable对象
     *
     * @param drawable Drawable 目标对象
     * @return 克隆一个新的Drawable对象
     */
    public static Drawable cloneDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState != null) {
            return constantState.newDrawable();
        }
        return null;
    }

    // init 弹窗上的数据
    private void initPopupMenuView() {
        mPopupMenu = new XPopupMenu.Builder(mContext)
                .setMenuArrowWidth(mPopupMenuArrowWidth)
                .setMenuArrowHeight(mPopupMenuArrowHeight)
                .setMenuWidth(mPopupMenuWidth)
                .setPopupMenuAnimationStyle(R.style.XP_Animation_PopDownMenu_Right)
                .setRadius(mPopMenuBackgroundRadius)
                .setItemTextColor(item_textColor)
                .setIconColorFilter(item_iconColorFilter)
                .setItemHeight(mPopupMenuItemHeight)
                .setDividerColor(item_dividerColor)
                .setDividerHeight(item_dividerHeight)
                .setDividerMarginEnd(item_dividerMarginEnd)
                .setDividerMarginStart(item_dividerMarginStart)
                .setItemLayoutPressedColor(item_layoutPressedColor)
                .setMenuItemList(copyPopupMenuData(mMenuInPopList))
                .setOnMenuItemClickListener((popupMenu, position, tag) -> {
                    if (mMenuClickListener != null) {
                        mMenuClickListener.onItemClick(tag, null, (mMenuInToolBarList.size() + position));
                    }
                    popupMenu.dismiss();
                })
                .create();
    }


    private void showPopMenu(View anchor) {
        ToastUtils.toast(mContext, "点击了菜单+");
        if (mPopupMenu != null) {
            mPopupMenu.show(anchor);
        }
    }

    private static List<XPopupMenuItem> copyPopupMenuData(List<XToolBarMenuItem> list) {
        List<XPopupMenuItem> result = new ArrayList<>();
        for (XToolBarMenuItem item : list) {
            result.add(copyItemData(item));
        }
        return result;
    }

    private static XPopupMenuItem copyItemData(XToolBarMenuItem item) {
        if (item == null) {
            return new XPopupMenuItem();
        }
        XPopupMenuItem result = new XPopupMenuItem();
        result.setTag(item.getTag());
        result.setText(item.getText());
        result.setTextColor(item.getTextColor());
        result.setTextSize(item.getTextSize());
        result.setIconResId(item.getIconResId());
        result.setIconDrawable(item.getIconDrawable());
        result.setIconPadding(item.getIconPadding());
        result.setIconMarginEnd(item.getIconMarginEnd());
        result.setIconMarginStart(item.getIconMarginStart());
        result.setIconColorFilter(item.getIconColorFilter());
        result.setBackground(item.getBackground());
        result.setBackgroundPressed(item.getBackgroundPressed());
        result.setRadius(item.getRadius());
        result.setShowType(item.getShowType());
        return result;
    }

}
