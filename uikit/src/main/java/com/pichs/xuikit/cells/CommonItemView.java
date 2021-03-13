package com.pichs.xuikit.cells;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;

import com.pichs.common.widget.cardview.XCardImageView;
import com.pichs.common.widget.cardview.XCardRelativeLayout;
import com.pichs.common.widget.switcher.XSwitchButton;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.view.XImageView;
import com.pichs.common.widget.view.XTextView;
import com.pichs.switcher.Switcher;
import com.pichs.xuikit.R;

/**
 * Created by pichs
 */
public class CommonItemView extends XCardRelativeLayout {

    public static final int DEF_MARGIN = -100000;
    // 左侧
    private XCardImageView mImageView;
    private XTextView mTextView;
    // 右侧
    private Switcher mSwitcher;
    private XSwitchButton mXSwitchButton;
    private XTextView mSubTextView;
    private XCardImageView mArrowImageView;
    // 默认的 开关样式
    public static final int SWITCHER_TYPE_DEFAULT = 0;
    // 有弹性的 开关样式
    public static final int SWITCHER_TYPE_ELASTIC = 1;
    private int switcherType = SWITCHER_TYPE_DEFAULT;
    // 开关是否开着
    private boolean isSwitcherVisible = false;

    public CommonItemView(Context context) {
        this(context, null);
    }

    public CommonItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.xuikit_common_item_view, this, true);
        initView();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView);
        // text
        setTitle(ta.getString(R.styleable.CommonItemView_xp_itemview_title));
        setTitleIgnoreGlobalTypeface(ta.getBoolean(R.styleable.CommonItemView_xp_itemview_title_ignoreGlobalTypeface, false));
        setTitleColor(ta.getColor(R.styleable.CommonItemView_xp_itemview_title_textColor, 0));
        setTitleTextSize(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_title_textSize, -1));
        setTitleTextStyle(ta.getInt(R.styleable.CommonItemView_xp_itemview_title_textStyle, 0));
        setTitleMarginStart(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_title_marginStart, DEF_MARGIN));
        // icon
        setIcon(ta.getDrawable(R.styleable.CommonItemView_xp_itemview_icon));
        setIconColorFilter(ta.getColor(R.styleable.CommonItemView_xp_itemview_icon_colorFilter, 0));
        setIconSize(
                ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_icon_width, XDisplayHelper.dp2px(context, 22f)),
                ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_icon_height, XDisplayHelper.dp2px(context, 22f))
        );
        setIconMarginStart(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_icon_marginStart, DEF_MARGIN));
        setIconMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_icon_marginEnd, DEF_MARGIN));
        setIconPadding(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_icon_padding, 0));
        setIconRadius(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_icon_radius, 0));
        setArrowRadius(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_arrow_radius, 0));

        // arrow
        setArrowSize(
                ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_arrow_width, XDisplayHelper.dp2px(context, 22f)),
                ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_arrow_height, XDisplayHelper.dp2px(context, 22f))
        );
        setArrowVisible(ta.getBoolean(R.styleable.CommonItemView_xp_itemview_arrow_visible, false));
        setArrowColorFilter(ta.getColor(R.styleable.CommonItemView_xp_itemview_arrow_colorFilter, 0));
        setArrowMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_arrow_marginEnd, DEF_MARGIN));
        setArrowPadding(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_arrow_padding, 0));

        // subtext
        setSubText(ta.getString(R.styleable.CommonItemView_xp_itemview_subtext));
        setSubTextIgnoreGlobalTypeface(ta.getBoolean(R.styleable.CommonItemView_xp_itemview_subtext_ignoreGlobalTypeface, false));
        setSubTextColor(ta.getColor(R.styleable.CommonItemView_xp_itemview_subtext_textColor, 0));
        setSubTextTextSize(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_subtext_textSize, -1));
        setSubTextTextStyle(ta.getInt(R.styleable.CommonItemView_xp_itemview_subtext_textStyle, 0));
        setSubTextMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_subtext_marginEnd, DEF_MARGIN));
        switcherType = ta.getInt(R.styleable.CommonItemView_xp_itemview_switcher_type, SWITCHER_TYPE_DEFAULT);
        setSwitcherType(switcherType);
        // switcher
        setSwitcherSize(
                ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_switcher_width, ViewGroup.LayoutParams.WRAP_CONTENT),
                ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_switcher_height, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        setSwitcherColor(
                ta.getColor(R.styleable.CommonItemView_xp_itemview_switcher_iconColor_switch, Color.WHITE),
                ta.getColor(R.styleable.CommonItemView_xp_itemview_switcher_bgColor_switchOn, Color.parseColor("#00C853")),
                ta.getColor(R.styleable.CommonItemView_xp_itemview_switcher_bgColor_switchOff, Color.parseColor("#d8d8d8"))
        );
        setSwitcherElevation(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_switcher_elevation, 0));
        setSwitcherMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_xp_itemview_switcher_marginEnd, 0));
        setSwitcherChecked(ta.getBoolean(R.styleable.CommonItemView_xp_itemview_switcher_on, false), false);
        setSwitcherVisible(ta.getBoolean(R.styleable.CommonItemView_xp_itemview_switcher_visible, false));
        ta.recycle();
        setClickable(true);
        setShadowAlpha(0);
        setShadowColor(Color.TRANSPARENT);
        setShadowElevation(0);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setArrowRadius(int radius) {
        mArrowImageView.setRadius(radius);
    }


    public XCardImageView getIconView() {
        return mImageView;
    }


    public XCardImageView getArrowView() {
        return mArrowImageView;
    }

    public void setIconRadius(int radius) {
        mImageView.setRadius(radius);
    }

    public void setArrowPadding(int padding) {
        mArrowImageView.setPadding(padding, padding, padding, padding);
    }

    public void setIconPadding(int padding) {
        mImageView.setPadding(padding, padding, padding, padding);
    }

    /**
     * 设置图片的尺寸
     *
     * @param width
     * @param height
     */
    public void setArrowSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mArrowImageView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
            layoutParams.height = height;
            mArrowImageView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置图片的尺寸
     *
     * @param width
     * @param height
     */
    public void setIconSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mImageView.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.width = width;
            layoutParams.height = height;
            mImageView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 屏蔽title全局字体变化
     */
    public void setTitleIgnoreGlobalTypeface(boolean ignoreGlobalTypeface) {
        mTextView.setIgnoreGlobalTypeface(ignoreGlobalTypeface);
    }

    /**
     * 屏蔽subtext全局字体变化
     */
    public void setSubTextIgnoreGlobalTypeface(boolean ignoreGlobalTypeface) {
        mSubTextView.setIgnoreGlobalTypeface(ignoreGlobalTypeface);
    }

    public void setSwitcherType(int switcherType) {
        if (!isSwitcherVisible) {
            return;
        }
        if (switcherType == SWITCHER_TYPE_ELASTIC) {
            mSwitcher.setVisibility(VISIBLE);
            mXSwitchButton.setVisibility(GONE);
        } else {
            mSwitcher.setVisibility(GONE);
            mXSwitchButton.setVisibility(VISIBLE);
        }
    }

    public void setSwitcherElevation(int elevation) {
        mSwitcher.setElevation(elevation);
        invalidate();
    }

    public void setSwitcherChecked(boolean isChecked) {
        mSwitcher.setChecked(isChecked, true);
        mXSwitchButton.setChecked(isChecked);
    }

    public void setSwitcherChecked(boolean isChecked, boolean withAnimation) {
        mSwitcher.setChecked(isChecked, withAnimation);
        mXSwitchButton.setChecked(isChecked);
    }

    public void setSwitcherMarginEnd(int marginEnd) {
        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) mSwitcher.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.setMarginEnd(marginEnd);
            mSwitcher.setLayoutParams(layoutParams);
        }
        ViewGroup.MarginLayoutParams xswLP = (MarginLayoutParams) mXSwitchButton.getLayoutParams();
        if (xswLP != null) {
            xswLP.setMarginEnd(marginEnd);
            mXSwitchButton.setLayoutParams(xswLP);
        }
    }

    private OnSwitcherChangedListener mOnSwitcherChangedListener;

    public void setOnSwitcherCheckedChangeListener(OnSwitcherChangedListener onSwitcherChangedListener) {
        this.mOnSwitcherChangedListener = onSwitcherChangedListener;
        mSwitcher.setOnCheckedChangeListener(isChecked -> {
            if (mOnSwitcherChangedListener != null) {
                mOnSwitcherChangedListener.onCheckChanged(isChecked);
            }
            return null;
        });
        mXSwitchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (mOnSwitcherChangedListener != null) {
                mOnSwitcherChangedListener.onCheckChanged(isChecked);
            }
        });
    }

    public void setSwitcherVisible(boolean visible) {
        this.isSwitcherVisible = visible;
        if (!this.isSwitcherVisible) {
            mSwitcher.setVisibility(GONE);
            mXSwitchButton.setVisibility(GONE);
        } else {
            if (switcherType == SWITCHER_TYPE_DEFAULT) {
                mXSwitchButton.setVisibility(VISIBLE);
            } else {
                mSwitcher.setVisibility(VISIBLE);
            }
        }
    }

    public void setSwitcherSize(int width, int height) {
        mSwitcher.setSize(width, height);
        ViewGroup.LayoutParams layoutParams = mXSwitchButton.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = height;
            layoutParams.width = width;
            mXSwitchButton.setLayoutParams(layoutParams);
        }
    }

    public void setSwitcherColor(int iconColor, int switchOnColor, int switchOffColor) {
        mSwitcher.setSwitcherColor(iconColor, switchOnColor, switchOffColor);
        mXSwitchButton.setThumbColor(iconColor, iconColor, iconColor, iconColor);
        mXSwitchButton.setBackgroundColor(switchOffColor, switchOnColor);
    }

    /**
     * 设置 margin值
     *
     * @param arrowMarginEnd 箭头的marginEnd
     */
    public void setArrowMarginEnd(int arrowMarginEnd) {
        if (arrowMarginEnd != DEF_MARGIN) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mArrowImageView.getLayoutParams();
            layoutParams.rightMargin = arrowMarginEnd;
            mArrowImageView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置 margin值
     *
     * @param subTextMarginEnd 显示内容的marginEnd
     */
    public void setSubTextMarginEnd(int subTextMarginEnd) {
        if (subTextMarginEnd != DEF_MARGIN) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mSubTextView.getLayoutParams();
            layoutParams.rightMargin = subTextMarginEnd;
            mSubTextView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置 margin值
     *
     * @param titleMarginStart title的marginStart
     */
    public void setTitleMarginStart(int titleMarginStart) {
        if (titleMarginStart != DEF_MARGIN) {
            LayoutParams layoutParams = (LayoutParams) mTextView.getLayoutParams();
            layoutParams.leftMargin = titleMarginStart;
            mTextView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置 margin值
     *
     * @param iconMarginEnd 图片的marginEnd
     */
    public void setIconMarginEnd(int iconMarginEnd) {
        if (iconMarginEnd != DEF_MARGIN) {
            LayoutParams layoutParams = (LayoutParams) mImageView.getLayoutParams();
            layoutParams.rightMargin = iconMarginEnd;
            mImageView.setLayoutParams(layoutParams);
        }
    }

    /**
     * 设置 margin值
     *
     * @param iconMarginStart 图片的marginStart
     */
    public void setIconMarginStart(int iconMarginStart) {
        if (iconMarginStart != DEF_MARGIN) {
            LayoutParams layoutParams = (LayoutParams) mImageView.getLayoutParams();
            layoutParams.leftMargin = iconMarginStart;
            mImageView.setLayoutParams(layoutParams);
        }
    }

    public void setIcon(Drawable drawable) {
        if (drawable != null) {
            mImageView.setVisibility(VISIBLE);
            mImageView.setImageDrawable(drawable);
        } else {
            mImageView.setVisibility(GONE);
        }
        requestLayout();
    }

    public void setIconColorFilter(@ColorInt int color) {
        mImageView.setColorFilter(color);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTextView.setVisibility(VISIBLE);
            mTextView.setText(title);
        }
    }

    public void setTitleColor(@ColorInt int color) {
        if (color != 0) {
            mTextView.setTextColor(color);
        }
    }

    public void setTitleTextSize(int textSize) {
        if (textSize != -1) {
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    public void setTitleTextStyle(int type) {
        if (type == 0) {
            mTextView.setTypeface(Typeface.DEFAULT);
        } else if (type == 1) {
            mTextView.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (type == 2) {
            mTextView.setTypeface(null, Typeface.ITALIC);
        }
    }

    public void setTitleTextTypeface(Typeface typeface) {
        if (typeface != null) {
            mTextView.setTypeface(typeface);
        }
    }


    public void setSubText(String subText) {
        if (!TextUtils.isEmpty(subText)) {
            mSubTextView.setVisibility(VISIBLE);
            mSubTextView.setText(subText);
        }
    }

    public void setSubTextColor(@ColorInt int color) {
        if (color != 0) {
            mSubTextView.setTextColor(color);
        }
    }


    public void setSubTextTextSize(int textSize) {
        if (textSize != -1) {
            mSubTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

    public void setSubTextTextStyle(int type) {
        if (type == 0) {
            mSubTextView.setTypeface(Typeface.DEFAULT);
        } else if (type == 1) {
            mSubTextView.setTypeface(Typeface.DEFAULT_BOLD);
        } else if (type == 2) {
            mSubTextView.setTypeface(null, Typeface.ITALIC);
        }
    }

    public void setSubTextTextTypeface(Typeface typeface) {
        if (typeface != null) {
            mSubTextView.setTypeface(typeface);
        }
    }

    public void setArrowVisible(boolean visible) {
        mArrowImageView.setVisibility(visible ? VISIBLE : GONE);
        requestLayout();
    }

    public void setArrowColorFilter(@ColorInt int color) {
        mArrowImageView.setColorFilter(color);
    }

    private void initView() {
        mImageView = findViewById(R.id.common_item_iv);
        mArrowImageView = findViewById(R.id.common_item_iv_arrow);
        mTextView = findViewById(R.id.common_item_tv);
        mSubTextView = findViewById(R.id.common_item_sub_tv);
        mSwitcher = findViewById(R.id.common_item_switcher);
        mXSwitchButton = findViewById(R.id.common_item_switch_button);
    }

    public interface OnSwitcherChangedListener {
        void onCheckChanged(boolean isChecked);
    }
}
