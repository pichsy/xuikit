package com.pichs.xuikit.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xuikit.R;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


/**
 * 底部tab条目
 */

public class BottomBarItem extends LinearLayout {

    private Context context;
    private Drawable normalIcon;//普通状态图标的资源id
    private Drawable selectedIcon;//选中状态图标的资源id
    private String title;//文本
    private boolean titleTextBold = false;//文字加粗
    private int titleTextSize = 12;//文字大小 默认为12sp
    private int titleNormalColor;    //描述文本的默认显示颜色
    private int titleSelectedColor;  //述文本的默认选中显示颜色
    private int marginTop = 0;//文字和图标的距离,默认0dp
    private boolean openTouchBg = false;// 是否开启触摸背景，默认关闭
    private Drawable touchDrawable;//触摸时的背景
    private int iconWidth;//图标的宽度
    private int iconHeight;//图标的高度
    private int itemPadding;//BottomBarItem的padding
    private int unreadTextSize = 10; //未读数默认字体大小10sp
    private int unreadNumThreshold = 99;//未读数阈值
    private int unreadTextColor;//未读数字体颜色
    private Drawable unreadTextBg;//未读数字体背景
    private int msgTextSize = 6; //消息默认字体大小6sp
    private int msgTextColor;//消息文字颜色
    private Drawable msgTextBg;//消息文字背景
    private Drawable notifyPointBg;//小红点背景
    private String lottieJson; //lottie文件名
    private boolean useLottie;


    private ImageView mImageView;
    private LottieAnimationView mLottieView;
    private TextView mTvUnread;
    private TextView mTvNotify;
    private TextView mTvMsg;
    private TextView mTextView;

    public BottomBarItem(Context context) {
        super(context);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarItem);

        initAttrs(ta); //初始化属性

        ta.recycle();

        checkValues();//检查值是否合法

        init();//初始化相关操作
    }

    private void initAttrs(TypedArray ta) {
        normalIcon = ta.getDrawable(R.styleable.BottomBarItem_xp_iconNormal);
        selectedIcon = ta.getDrawable(R.styleable.BottomBarItem_xp_iconSelected);

        title = ta.getString(R.styleable.BottomBarItem_xp_itemText);
        titleTextBold = ta.getBoolean(R.styleable.BottomBarItem_xp_itemTextBold, titleTextBold);
        titleTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_itemTextSize, XDisplayHelper.sp2px(context, titleTextSize));

        titleNormalColor = ta.getColor(R.styleable.BottomBarItem_xp_textColorNormal, Color.GRAY);
        titleSelectedColor = ta.getColor(R.styleable.BottomBarItem_xp_textColorSelected, Color.BLUE);

        marginTop = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_itemMarginTop, XDisplayHelper.dp2px(context, marginTop));

        openTouchBg = ta.getBoolean(R.styleable.BottomBarItem_xp_openTouchBg, openTouchBg);
        touchDrawable = ta.getDrawable(R.styleable.BottomBarItem_xp_touchDrawable);

        iconWidth = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_iconWidth, 0);
        iconHeight = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_iconHeight, 0);
        itemPadding = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_itemPadding, 0);

        unreadTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_unreadTextSize, XDisplayHelper.sp2px(context, unreadTextSize));
        unreadTextColor = ta.getColor(R.styleable.BottomBarItem_xp_unreadTextColor, Color.WHITE);
        unreadTextBg = ta.getDrawable(R.styleable.BottomBarItem_xp_unreadTextBg);

        msgTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_msgTextSize, XDisplayHelper.sp2px(context, msgTextSize));
        msgTextColor = ta.getColor(R.styleable.BottomBarItem_xp_msgTextColor, Color.WHITE);
        msgTextBg = ta.getDrawable(R.styleable.BottomBarItem_xp_msgTextBg);

        notifyPointBg = ta.getDrawable(R.styleable.BottomBarItem_xp_notifyPointBg);

        unreadNumThreshold = ta.getInteger(R.styleable.BottomBarItem_xp_unreadThreshold, unreadNumThreshold);

        lottieJson = ta.getString(R.styleable.BottomBarItem_xp_lottieJson);
        useLottie = !TextUtils.isEmpty(lottieJson);
    }

    /**
     * 检查传入的值是否完善
     */
    private void checkValues() {
        if (!useLottie && normalIcon == null) {
            throw new IllegalStateException("You have not set the normal icon");
        }

        if (!useLottie && selectedIcon == null) {
            throw new IllegalStateException("You have not set the selected icon");
        }

        if (openTouchBg && touchDrawable == null) {
            //如果有开启触摸背景效果但是没有传对应的drawable
            throw new IllegalStateException("Touch effect is turned on, but touchDrawable is not specified");
        }

        if (unreadTextBg == null) {
            unreadTextBg = ContextCompat.getDrawable(context, R.drawable.xuikit_shape_unread);
        }

        if (msgTextBg == null) {
            msgTextBg = ContextCompat.getDrawable(context, R.drawable.xuikit_shape_msg);
        }

        if (notifyPointBg == null) {
            notifyPointBg = ContextCompat.getDrawable(context, R.drawable.xuikit_shape_notify_point);
        }
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        View view = initView();

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mImageView.getLayoutParams();
        if (iconWidth != 0 && iconHeight != 0) {
            //如果有设置图标的宽度和高度，则设置ImageView的宽高
            layoutParams.width = iconWidth;
            layoutParams.height = iconHeight;
        }

        if (useLottie) {
            mLottieView.setLayoutParams(layoutParams);
            mLottieView.setAnimation(lottieJson);
            mLottieView.setRepeatCount(0);
        } else {
            mImageView.setImageDrawable(normalIcon);
            mImageView.setLayoutParams(layoutParams);
        }

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);//设置底部文字字体大小
        mTextView.getPaint().setFakeBoldText(titleTextBold);
        mTvUnread.setTextSize(TypedValue.COMPLEX_UNIT_PX, unreadTextSize);//设置未读数的字体大小
        mTvUnread.setTextColor(unreadTextColor);//设置未读数字体颜色
        mTvUnread.setBackground(unreadTextBg);//设置未读数背景

        mTvMsg.setTextSize(TypedValue.COMPLEX_UNIT_PX, msgTextSize);//设置提示文字的字体大小
        mTvMsg.setTextColor(msgTextColor);//设置提示文字的字体颜色
        mTvMsg.setBackground(msgTextBg);//设置提示文字的背景颜色

        mTvNotify.setBackground(notifyPointBg);//设置提示点的背景颜色

        mTextView.setTextColor(titleNormalColor);//设置底部文字字体颜色
        mTextView.setText(title);//设置标签文字

        LayoutParams textLayoutParams = (LayoutParams) mTextView.getLayoutParams();
        textLayoutParams.topMargin = marginTop;
        mTextView.setLayoutParams(textLayoutParams);

        if (openTouchBg) {
            //如果有开启触摸背景
            setBackground(touchDrawable);
        }

        addView(view);
    }

    @NonNull
    private View initView() {
        View view = View.inflate(context, R.layout.xuikit_item_bottom_bar, null);
        if (itemPadding != 0) {
            //如果有设置item的padding
            view.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
        }
        mImageView = view.findViewById(R.id.iv_icon);
        mLottieView = view.findViewById(R.id.bottom_lottie_view);
        mTvUnread = view.findViewById(R.id.tv_unred_num);
        mTvMsg = view.findViewById(R.id.tv_msg);
        mTvNotify = view.findViewById(R.id.tv_point);
        mTextView = view.findViewById(R.id.tv_text);

        mImageView.setVisibility(useLottie ? GONE : VISIBLE);
        mLottieView.setVisibility(useLottie ? VISIBLE : GONE);

        return view;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setNormalIcon(Drawable normalIcon) {
        this.normalIcon = normalIcon;
        refreshTab();
    }

    public void setNormalIcon(int resId) {
        setNormalIcon(ContextCompat.getDrawable(context, resId));
    }

    public void setSelectedIcon(Drawable selectedIcon) {
        this.selectedIcon = selectedIcon;
        refreshTab();
    }

    public void setSelectedIcon(int resId) {
        setSelectedIcon(ContextCompat.getDrawable(context, resId));
    }

    public void refreshTab(boolean isSelected) {
        setSelected(isSelected);
        refreshTab();
    }

    public void refreshTab() {
        if (useLottie) {
            if (isSelected()) {
                mLottieView.playAnimation();
            } else {
                //取消动画 进度设置为0
                mLottieView.cancelAnimation();
                mLottieView.setProgress(0);
            }
        } else {
            mImageView.setImageDrawable(isSelected() ? selectedIcon : normalIcon);
        }

        mTextView.setTextColor(isSelected() ? titleSelectedColor : titleNormalColor);
    }

    private void setTvVisible(TextView tv) {
        //都设置为不可见
        mTvUnread.setVisibility(GONE);
        mTvMsg.setVisibility(GONE);
        mTvNotify.setVisibility(GONE);

        tv.setVisibility(VISIBLE);//设置为可见
    }

    public int getUnreadNumThreshold() {
        return unreadNumThreshold;
    }

    public void setUnreadNumThreshold(int unreadNumThreshold) {
        this.unreadNumThreshold = unreadNumThreshold;
    }

    public void setUnreadNum(int unreadNum) {
        setTvVisible(mTvUnread);
        if (unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (unreadNum <= unreadNumThreshold) {
            mTvUnread.setText(String.valueOf(unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadNumThreshold));
        }
    }

    public void setMsg(String msg) {
        setTvVisible(mTvMsg);
        mTvMsg.setText(msg);
    }

    public void hideMsg() {
        mTvMsg.setVisibility(GONE);
    }

    public void showNotify() {
        setTvVisible(mTvNotify);
    }

    public void hideNotify() {
        mTvNotify.setVisibility(GONE);
    }

    public BottomBarItem create(Builder builder) {
        this.context = builder.context;
        this.normalIcon = builder.normalIcon;
        this.selectedIcon = builder.selectedIcon;
        this.title = builder.title;
        this.titleTextBold = builder.titleTextBold;
        this.titleTextSize = builder.titleTextSize;
        this.titleNormalColor = builder.titleNormalColor;
        this.titleSelectedColor = builder.titleSelectedColor;
        this.marginTop = builder.marginTop;
        this.openTouchBg = builder.openTouchBg;
        this.touchDrawable = builder.touchDrawable;
        this.iconWidth = builder.iconWidth;
        this.iconHeight = builder.iconHeight;
        this.itemPadding = builder.itemPadding;
        this.unreadTextSize = builder.unreadTextSize;
        this.unreadTextColor = builder.unreadTextColor;
        this.unreadTextBg = builder.unreadTextBg;
        this.unreadNumThreshold = builder.unreadNumThreshold;
        this.msgTextSize = builder.msgTextSize;
        this.msgTextColor = builder.msgTextColor;
        this.msgTextBg = builder.msgTextBg;
        this.notifyPointBg = builder.notifyPointBg;
        this.lottieJson = builder.lottieJson;

        checkValues();
        init();
        return this;
    }

    public static final class Builder {
        private Context context;
        private Drawable normalIcon;//普通状态图标的资源id
        private Drawable selectedIcon;//选中状态图标的资源id
        private String title;//标题
        private boolean titleTextBold;//文字加粗
        private int titleTextSize;//字体大小
        private int titleNormalColor;    //描述文本的默认显示颜色
        private int titleSelectedColor;  //述文本的默认选中显示颜色
        private int marginTop;//文字和图标的距离
        private boolean openTouchBg;// 是否开启触摸背景，默认关闭
        private Drawable touchDrawable;//触摸时的背景
        private int iconWidth;//图标的宽度
        private int iconHeight;//图标的高度
        private int itemPadding;//BottomBarItem的padding
        private int unreadTextSize; //未读数字体大小
        private int unreadNumThreshold;//未读数阈值
        private int unreadTextColor;//未读数字体颜色
        private Drawable unreadTextBg;//未读数文字背景
        private int msgTextSize; //消息字体大小
        private int msgTextColor;//消息文字颜色
        private Drawable msgTextBg;//消息提醒背景颜色
        private Drawable notifyPointBg;//小红点背景颜色
        private String lottieJson; //lottie文件名

        public Builder(Context context) {
            this.context = context;
            titleTextBold = false;
            titleTextSize = XDisplayHelper.sp2px(context, 12f);
            titleNormalColor = Color.parseColor("#999999");
            titleSelectedColor = Color.parseColor("#F44336");
            unreadTextSize = XDisplayHelper.sp2px(context, 10f);
            msgTextSize = XDisplayHelper.sp2px(context, 6f);
            unreadTextColor = Color.WHITE;
            unreadNumThreshold = 99;
            msgTextColor = Color.WHITE;
        }

        /**
         * Sets the default icon's resourceId
         */
        public Builder normalIcon(Drawable normalIcon) {
            this.normalIcon = normalIcon;
            return this;
        }

        /**
         * Sets the selected icon's resourceId
         */
        public Builder selectedIcon(Drawable selectedIcon) {
            this.selectedIcon = selectedIcon;
            return this;
        }

        /**
         * Sets the title's resourceId
         */
        public Builder title(int titleId) {
            this.title = context.getString(titleId);
            return this;
        }

        /**
         * Sets the title string
         */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets the title's text bold
         */
        public Builder titleTextBold(boolean titleTextBold) {
            this.titleTextBold = titleTextBold;
            return this;
        }

        /**
         * Sets the title's text size
         */
        public Builder titleTextSize(int titleTextSize) {
            this.titleTextSize = XDisplayHelper.sp2px(context, titleTextSize);
            return this;
        }

        /**
         * Sets the title's normal color resourceId
         */
        public Builder titleNormalColor(int titleNormalColor) {
            this.titleNormalColor = getColor(titleNormalColor);
            return this;
        }

        /**
         * Sets the title's selected color resourceId
         */
        public Builder titleSelectedColor(int titleSelectedColor) {
            this.titleSelectedColor = getColor(titleSelectedColor);
            return this;
        }

        /**
         * Sets the item's margin top
         */
        public Builder marginTop(int marginTop) {
            this.marginTop = marginTop;
            return this;
        }

        /**
         * Sets whether to open the touch background effect
         */
        public Builder openTouchBg(boolean openTouchBg) {
            this.openTouchBg = openTouchBg;
            return this;
        }

        /**
         * Sets touch background
         */
        public Builder touchDrawable(Drawable touchDrawable) {
            this.touchDrawable = touchDrawable;
            return this;
        }

        /**
         * Sets icon's width
         */
        public Builder iconWidth(int iconWidth) {
            this.iconWidth = iconWidth;
            return this;
        }

        /**
         * Sets icon's height
         */
        public Builder iconHeight(int iconHeight) {
            this.iconHeight = iconHeight;
            return this;
        }


        /**
         * Sets padding for item
         */
        public Builder itemPadding(int itemPadding) {
            this.itemPadding = itemPadding;
            return this;
        }

        /**
         * Sets unread font size
         */
        public Builder unreadTextSize(int unreadTextSize) {
            this.unreadTextSize = XDisplayHelper.sp2px(context, unreadTextSize);
            return this;
        }

        /**
         * Sets the number of unread array thresholds greater than the threshold to be displayed as n + n as the set threshold
         */
        public Builder unreadNumThreshold(int unreadNumThreshold) {
            this.unreadNumThreshold = unreadNumThreshold;
            return this;
        }

        /**
         * Sets the message font size
         */
        public Builder msgTextSize(int msgTextSize) {
            this.msgTextSize = XDisplayHelper.sp2px(context, msgTextSize);
            return this;
        }

        /**
         * Sets the message font background
         */
        public Builder unreadTextBg(Drawable unreadTextBg) {
            this.unreadTextBg = unreadTextBg;
            return this;
        }

        /**
         * Sets unread font color
         */
        public Builder unreadTextColor(int unreadTextColor) {
            this.unreadTextColor = getColor(unreadTextColor);
            return this;
        }

        /**
         * Sets the message font color
         */
        public Builder msgTextColor(int msgTextColor) {
            this.msgTextColor = getColor(msgTextColor);
            return this;
        }

        /**
         * Sets the message font background
         */
        public Builder msgTextBg(Drawable msgTextBg) {
            this.msgTextBg = msgTextBg;
            return this;
        }

        /**
         * Set the message prompt point background
         */
        public Builder notifyPointBg(Drawable notifyPointBg) {
            this.notifyPointBg = notifyPointBg;
            return this;
        }

        /**
         * Set the name of lottie json file
         */
        public Builder lottieJson(String lottieJson) {
            this.lottieJson = lottieJson;
            return this;
        }

        /**
         * Create a BottomBarItem object
         */
        public BottomBarItem create(Drawable normalIcon, Drawable selectedIcon, String text) {
            this.normalIcon = normalIcon;
            this.selectedIcon = selectedIcon;
            title = text;

            BottomBarItem bottomBarItem = new BottomBarItem(context);
            return bottomBarItem.create(this);
        }

        public BottomBarItem create(int normalIconId, int selectedIconId, String text) {
            return create(ContextCompat.getDrawable(context, normalIconId), ContextCompat.getDrawable(context, selectedIconId), text);
        }

        private int getColor(int colorId) {
            return context.getResources().getColor(colorId);
        }
    }
}
