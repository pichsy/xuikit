package com.pichs.xuikit.tabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.pichs.common.widget.cardview.XCardTextView;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.view.XImageView;
import com.pichs.common.widget.view.XLinearLayout;
import com.pichs.common.widget.view.XTextView;
import com.pichs.xuikit.R;

import java.util.Locale;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 底部tab条目
 */

public class BottomBarItem extends XLinearLayout {
    private Context context;
    private String text;//文本
    private int textStyle = Typeface.NORMAL;
    private int textSize = 12;//文字大小 默认为12sp
    private int textNormalColor;    //描述文本的默认显示颜色
    private int textSelectedColor;  //述文本的默认选中显示颜色
    private int textIconSpacing = 0;//文字和图标的距离,默认0dp
    private int iconWidth = 0;//图标的宽度
    private int iconHeight = 0;//图标的高度
    private int unreadTextSize = 10; //未读数默认字体大小10sp
    private int unreadMaxNumber = 99;//未读数阈值
    private int unreadTextColor;//未读数字体颜色
    private Drawable unreadTextBg;//未读数字体背景
    private int msgTextSize = 6; //消息默认字体大小6sp
    private int msgTextColor;//消息文字颜色
    private Drawable msgTextBg;//消息文字背景
    private String lottieJson; //lottie文件名
    private boolean useLottie;

    private XImageView mIvIcon;
    private LottieAnimationView mLottieView;
    private XCardTextView mTvUnread;
    private XCardTextView mTvNotify;
    private XCardTextView mTvMsg;
    private XTextView mTvTitle;
    // 圆点背景色
    @ColorInt
    private int notifyPointBgColor;
    // 圆点的半径
    private int notifyPointRadius;
    private boolean isIgnoreGlobalTypeface = true;
    private int iconPadding = 0;
    private Drawable iconSelectedDrawable;
    private Drawable iconNormalDrawable;
    private int iconSelectedColor;
    private int iconNormalColor;

    // 未读消息的背景圆角
    private int unreadTextBgRadius;

    // 消息提示背景圆角
    private int msgTextBgRadius;
    // 未读数量
    private int unreadNum = 0;
    // 消息文本
    private CharSequence msgText;


    public BottomBarItem(Context context) {
        this(context, null);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = View.inflate(context, R.layout.xuikit_item_bottom_bar, null);
        initView(view);
        addView(view);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarItem);
        if (ta != null) {
            initAttrs(ta); //初始化属性
        }
        ta.recycle();
        init();//初始化相关操作
    }

    private void initAttrs(TypedArray ta) {
        text = ta.getString(R.styleable.BottomBarItem_xp_btr_text);
        isIgnoreGlobalTypeface = ta.getBoolean(R.styleable.BottomBarItem_xp_ignoreGlobalTypeface, isIgnoreGlobalTypeface);
        textStyle = ta.getInt(R.styleable.BottomBarItem_xp_btr_textStyle, textStyle);
        textSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_textSize, XDisplayHelper.sp2px(context, textSize));
        textNormalColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_textNormalColor, 0);
        textSelectedColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_textSelectedColor, 0);
        textIconSpacing = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_textIconSpacing, XDisplayHelper.dp2px(context, textIconSpacing));
        iconWidth = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_iconWidth, XDisplayHelper.dp2px(context, 22f));
        iconHeight = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_iconHeight, XDisplayHelper.dp2px(context, 22f));
        iconPadding = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_iconPadding, iconPadding);
        iconSelectedDrawable = ta.getDrawable(R.styleable.BottomBarItem_xp_btr_iconSelected);
        iconNormalDrawable = ta.getDrawable(R.styleable.BottomBarItem_xp_btr_iconNormal);
        iconNormalColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_iconNormalColor, 0);
        iconSelectedColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_iconSelectedColor, 0);
        unreadNum = ta.getInt(R.styleable.BottomBarItem_xp_btr_unreadNum, 0);
        unreadTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_unreadTextSize, XDisplayHelper.sp2px(context, unreadTextSize));
        unreadTextColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_unreadTextColor, Color.WHITE);
        unreadTextBg = ta.getDrawable(R.styleable.BottomBarItem_xp_btr_unreadTextBackground);
        unreadTextBgRadius = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_unreadTextBackgroundRadius, XDisplayHelper.dp2px(context, 2f));
        msgTextSize = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_msgTextSize, XDisplayHelper.sp2px(context, msgTextSize));
        msgTextColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_msgTextColor, Color.WHITE);
        msgText = ta.getString(R.styleable.BottomBarItem_xp_btr_msgText);
        msgTextBg = ta.getDrawable(R.styleable.BottomBarItem_xp_btr_msgTextBackground);
        msgTextBgRadius = ta.getDimensionPixelSize(R.styleable.BottomBarItem_xp_btr_msgTextBackgroundRadius, XDisplayHelper.dp2px(context, 2f));
        notifyPointBgColor = ta.getColor(R.styleable.BottomBarItem_xp_btr_notifyPointBackgroundColor, Color.RED);
        notifyPointRadius = ta.getColor(R.styleable.BottomBarItem_xp_btr_notifyPointRadius, XDisplayHelper.dp2px(context, 2));
        unreadMaxNumber = ta.getInteger(R.styleable.BottomBarItem_xp_btr_unreadMaxNumber, unreadMaxNumber);
        lottieJson = ta.getString(R.styleable.BottomBarItem_xp_btr_lottieJson);
        useLottie = !TextUtils.isEmpty(lottieJson);
    }


    private void init() {
        this
                .setIconHeight(iconHeight)
                .setIconWidth(iconWidth)
                .setIconPadding(iconPadding)
                .setIconNormalColor(iconNormalColor)
                .setIconSelectedColor(iconSelectedColor)
                .setIconNormalDrawable(iconNormalDrawable)
                .setIconSelectedDrawable(iconSelectedDrawable)
                .setIgnoreGlobalTypeface(isIgnoreGlobalTypeface)
                .setLottieJson(lottieJson)
                .setText(text)
                .setTextIconSpacing(textIconSpacing)
                .setTextNormalColor(textNormalColor)
                .setTextSelectedColor(textSelectedColor)
                .setTextStyle(textStyle)
                .setTextSize(textSize)
                .setMsgTextBg(msgTextBg)
                .setMsgTextBgRadius(msgTextBgRadius)
                .setMsgTextColor(msgTextColor)
                .setMsgText(msgText)
                .setNotifyPointBgColor(notifyPointBgColor)
                .setNotifyPointRadius(notifyPointRadius)
                .setUnreadMaxNumber(unreadMaxNumber)
                .setUnreadTextBg(unreadTextBg)
                .setUnreadTextBgRadius(unreadTextBgRadius)
                .setUnreadTextColor(unreadTextColor)
                .setUnreadTextSize(unreadTextSize)
                .setUnreadNum(unreadNum)
                .updateTab()
        ;
    }

    private void initView(View view) {
        mIvIcon = view.findViewById(R.id.iv_btr_icon);
        mLottieView = view.findViewById(R.id.bottom_lottie_view);
        mTvUnread = view.findViewById(R.id.tv_unred_num);
        mTvMsg = view.findViewById(R.id.tv_btr_msg);
        mTvNotify = view.findViewById(R.id.tv_point);
        mTvTitle = view.findViewById(R.id.tv_btr_text);
        mIvIcon.setVisibility(useLottie ? GONE : VISIBLE);
        mLottieView.setVisibility(useLottie ? VISIBLE : GONE);
    }


    public BottomBarItem setText(String text) {
        if (text == null) {
            this.text = null;
            this.mTvTitle.setVisibility(GONE);
            this.mTvTitle.setText("");
            return this;
        }
        this.text = text;
        this.mTvTitle.setVisibility(VISIBLE);
        this.mTvTitle.setText(text);
        return this;
    }

    public BottomBarItem setTextSize(int textSizeSp) {
        this.textSize = textSizeSp;
        this.mTvTitle.setTextSize(this.textSize);
        return this;
    }

    /**
     * 调用之后请再次调用updateTab方法生效
     *
     * @param textNormalColor
     * @return
     */
    public BottomBarItem setTextNormalColor(@ColorInt int textNormalColor) {
        this.textNormalColor = textNormalColor;
        return this;
    }

    /**
     * 调用之后请再次调用updateTab方法生效
     *
     * @param textSelectedColor
     * @return
     */
    public BottomBarItem setTextSelectedColor(@ColorInt int textSelectedColor) {
        this.textSelectedColor = textSelectedColor;
        return this;
    }

    public BottomBarItem setTextStyle(int textStyle) {
        this.textStyle = textStyle;
        this.mTvTitle.setTypeface(this.mTvTitle.getTypeface(), this.textStyle);
        return this;
    }

    public BottomBarItem setTextIconSpacing(int textIconSpacing) {
        this.textIconSpacing = textIconSpacing;
        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) this.mTvTitle.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.topMargin = textIconSpacing;
            this.mTvTitle.setLayoutParams(layoutParams);
        }
        return this;
    }

    /**
     * 调用后请手动调用updateTab生效
     *
     * @param iconHeight
     * @return
     */
    public BottomBarItem setIconHeight(int iconHeight) {
        this.iconHeight = iconHeight;
        ViewGroup.LayoutParams layoutParams = this.mIvIcon.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = this.iconHeight;
            this.mIvIcon.setLayoutParams(layoutParams);
        }
        return this;
    }

    public BottomBarItem setIconWidth(int iconWidth) {
        this.iconWidth = iconWidth;
        ViewGroup.LayoutParams layoutParams = this.mIvIcon.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = this.iconWidth;
            this.mIvIcon.setLayoutParams(layoutParams);
        }
        return this;
    }


    /**
     * 调用后请手动调用updateTab生效
     *
     * @param iconNormalColor
     * @return
     */
    public BottomBarItem setIconNormalColor(@ColorInt int iconNormalColor) {
        this.iconNormalColor = iconNormalColor;
        return this;
    }

    /**
     * 调用后请手动调用updateTab生效
     *
     * @param iconSelectedColor
     * @return
     */
    public BottomBarItem setIconSelectedColor(@ColorInt int iconSelectedColor) {
        this.iconSelectedColor = iconSelectedColor;
        return this;
    }

    /**
     * 调用后请手动调用updateTab生效
     *
     * @param iconNormalDrawable
     * @return
     */
    public BottomBarItem setIconNormalDrawable(Drawable iconNormalDrawable) {
        this.iconNormalDrawable = iconNormalDrawable;
        return this;
    }

    /**
     * 调用后请手动调用updateTab生效
     *
     * @param iconSelectedDrawable
     * @return
     */
    public BottomBarItem setIconSelectedDrawable(Drawable iconSelectedDrawable) {
        this.iconSelectedDrawable = iconSelectedDrawable;
        return this;
    }

    public BottomBarItem setIgnoreGlobalTypeface(boolean ignoreGlobalTypeface) {
        this.isIgnoreGlobalTypeface = ignoreGlobalTypeface;
        mTvTitle.setIgnoreGlobalTypeface(this.isIgnoreGlobalTypeface);
        return this;
    }

    public BottomBarItem setIconPadding(int iconPadding) {
        this.iconPadding = iconPadding;
        this.mIvIcon.setPadding(this.iconPadding, this.iconPadding, this.iconPadding, this.iconPadding);
        return this;
    }

    public BottomBarItem setLottieJson(String lottieJson) {
        this.lottieJson = lottieJson;
        this.useLottie = !TextUtils.isEmpty(this.lottieJson);
        return this;
    }

    public BottomBarItem setUnreadTextBg(Drawable unreadTextBg) {
        this.unreadTextBg = unreadTextBg;
        this.mTvUnread.setBackground(unreadTextBg);
        return this;
    }

    public BottomBarItem setUnreadTextBgRadius(int unreadTextBgRadius) {
        this.unreadTextBgRadius = unreadTextBgRadius;
        this.mTvUnread.setRadius(this.unreadTextBgRadius);
        return this;
    }

    public BottomBarItem setUnreadTextColor(int unreadTextColor) {
        this.unreadTextColor = unreadTextColor;
        this.mTvUnread.setTextColor(this.unreadTextColor);
        return this;
    }

    public BottomBarItem setUnreadTextSize(int textSizeSp) {
        this.unreadTextSize = textSizeSp;
        this.mTvUnread.setTextSize(this.unreadTextSize);
        return this;
    }

    public int getUnreadMaxNumber() {
        return unreadMaxNumber;
    }

    public BottomBarItem setUnreadMaxNumber(int unreadNumThreshold) {
        this.unreadMaxNumber = unreadNumThreshold;
        if (unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (unreadNum <= unreadMaxNumber) {
            mTvUnread.setText(String.valueOf(unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadMaxNumber));
        }
        return this;
    }

    public BottomBarItem setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
        setTextViewVisible(mTvUnread);
        if (this.unreadNum <= 0) {
            mTvUnread.setVisibility(GONE);
        } else if (this.unreadNum <= unreadMaxNumber) {
            mTvUnread.setText(String.valueOf(this.unreadNum));
        } else {
            mTvUnread.setText(String.format(Locale.CHINA, "%d+", unreadMaxNumber));
        }
        return this;
    }

    public void showNotify() {
        setTextViewVisible(mTvNotify);
    }

    public void hideNotify() {
        mTvNotify.setVisibility(GONE);
    }

    public BottomBarItem setNotifyPointBgColor(int notifyPointBgColor) {
        this.notifyPointBgColor = notifyPointBgColor;
        this.mTvNotify.setBackgroundColor(this.notifyPointBgColor);
        return this;
    }

    public BottomBarItem setNotifyPointRadius(int notifyPointRadius) {
        this.notifyPointRadius = notifyPointRadius;
        this.mTvNotify.setRadius(this.notifyPointRadius);
        return this;
    }

    /**
     * 为null则隐藏
     *
     * @param msgText CharSequence
     * @return BottomBarItem
     */
    public BottomBarItem setMsgText(CharSequence msgText) {
        if (msgText == null) {
            this.mTvMsg.setVisibility(GONE);
            this.msgText = null;
            this.mTvMsg.setText("");
            return this;
        }
        this.msgText = msgText;
        setTextViewVisible(mTvMsg);
        this.mTvMsg.setText(msgText);
        return this;
    }

    public BottomBarItem setMsgTextColor(int msgTextColor) {
        this.msgTextColor = msgTextColor;
        this.mTvMsg.setTextColor(this.msgTextColor);
        return this;
    }

    public BottomBarItem setMsgTextBgRadius(int msgTextBgRadius) {
        this.msgTextBgRadius = msgTextBgRadius;
        return this;
    }

    public BottomBarItem setMsgTextBg(Drawable msgTextBg) {
        this.msgTextBg = msgTextBg;
        this.mTvMsg.setBackground(this.msgTextBg);
        return this;
    }

    public BottomBarItem setMsgTextSize(int msgTextSize) {
        this.msgTextSize = msgTextSize;
        this.mTvMsg.setTextSize(this.msgTextSize);
        return this;
    }

    private void setTextViewVisible(TextView tv) {
        //都设置为不可见
        mTvUnread.setVisibility(GONE);
        mTvMsg.setVisibility(GONE);
        mTvNotify.setVisibility(GONE);
        //设置为可见
        tv.setVisibility(VISIBLE);
    }

    public void updateTab(boolean isSelected) {
        setSelected(isSelected);
        updateTab();
    }

    public BottomBarItem updateTab() {
        if (useLottie) {
            mIvIcon.setVisibility(VISIBLE);
            if (isSelected()) {
                mLottieView.playAnimation();
            } else {
                //取消动画 进度设置为0
                mLottieView.cancelAnimation();
                mLottieView.setProgress(0);
            }
        } else {
            if (iconSelectedDrawable != null && iconNormalDrawable != null) {
                mIvIcon.setVisibility(VISIBLE);
                mIvIcon.setImageDrawable(isSelected() ? iconSelectedDrawable : iconNormalDrawable);
                mIvIcon.setColorFilterOverride(isSelected() ? iconSelectedColor : iconNormalColor);
            } else if (iconSelectedDrawable != null) {
                mIvIcon.setVisibility(VISIBLE);
                mIvIcon.setImageDrawable(iconSelectedDrawable);
                mIvIcon.setColorFilterOverride(isSelected() ? iconSelectedColor : iconNormalColor);
            } else if (iconNormalDrawable != null) {
                mIvIcon.setVisibility(VISIBLE);
                mIvIcon.setImageDrawable(iconNormalDrawable);
                mIvIcon.setColorFilterOverride(isSelected() ? iconSelectedColor : iconNormalColor);
            } else {
                mIvIcon.setVisibility(GONE);
            }
        }
        if (textNormalColor != 0) {
            mTvTitle.setTextColor(isSelected() ? (textSelectedColor == 0 ? textNormalColor : textSelectedColor) : textNormalColor);
        } else if ((textSelectedColor != 0)) {
            mTvTitle.setTextColor(!isSelected() ? (textNormalColor == 0 ? textSelectedColor : textNormalColor) : textSelectedColor);
        }
        return this;
    }


}
