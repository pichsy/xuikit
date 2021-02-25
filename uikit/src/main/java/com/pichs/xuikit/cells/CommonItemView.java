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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import com.pichs.common.widget.cardview.XCardImageView;
import com.pichs.common.widget.cardview.XCardRelativeLayout;
import com.pichs.common.widget.switcher.XSwitchButton;
import com.pichs.common.widget.view.XImageView;
import com.pichs.common.widget.view.XTextView;
import com.pichs.switcher.Switcher;
import com.pichs.xuikit.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
    private XImageView mArrowImageView;
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
        setTitle(ta.getString(R.styleable.CommonItemView_common_title_text));
        setTitleColor(ta.getColor(R.styleable.CommonItemView_common_title_textColor, 0));
        setTitleTextSize(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_title_textSize, -1));
        setTitleTextStyle(ta.getInt(R.styleable.CommonItemView_common_title_textStyle, 0));
        setTitleMarginStart(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_title_text_marginStart, DEF_MARGIN));
        // icon
        setIcon(ta.getDrawable(R.styleable.CommonItemView_common_icon));
        setIconColorFilter(ta.getColor(R.styleable.CommonItemView_common_icon_colorFilter, 0));
        setIconMarginStart(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_icon_marginStart, DEF_MARGIN));
        setIconMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_icon_marginEnd, DEF_MARGIN));

        // arrow
        setArrowVisible(ta.getBoolean(R.styleable.CommonItemView_common_arrow_visible, false));
        setArrowColorFilter(ta.getColor(R.styleable.CommonItemView_common_arrow_colorFilter, 0));
        setArrowMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_arrow_marginEnd, DEF_MARGIN));

        // subtext
        setSubText(ta.getString(R.styleable.CommonItemView_common_sub_text));
        setSubTextColor(ta.getColor(R.styleable.CommonItemView_common_sub_text_textColor, 0));
        setSubTextTextSize(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_sub_text_textSize, -1));
        setSubTextTextStyle(ta.getInt(R.styleable.CommonItemView_common_sub_text_textStyle, 0));
        setSubTextMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_sub_text_marginEnd, DEF_MARGIN));
        switcherType = ta.getInt(R.styleable.CommonItemView_common_switcher_type, 0);
        setSwitcherType(switcherType);
        // switcher
        setSwitcherSize(
                ta.getDimensionPixelSize(R.styleable.CommonItemView_common_switcher_width, ViewGroup.LayoutParams.WRAP_CONTENT),
                ta.getDimensionPixelSize(R.styleable.CommonItemView_common_switcher_height, ViewGroup.LayoutParams.WRAP_CONTENT)
        );
        setSwitcherElevation(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_switcher_elevation, 0));
        setSwitcherVisible(ta.getBoolean(R.styleable.CommonItemView_common_switcher_visible, false));
        setSwitcherChecked(ta.getBoolean(R.styleable.CommonItemView_common_switcher_on, false));
        setSwitcherColor(
                ta.getColor(R.styleable.CommonItemView_common_switcher_iconColor_switch, Color.WHITE),
                ta.getColor(R.styleable.CommonItemView_common_switcher_bgColor_switchOn, Color.parseColor("#00C853")),
                ta.getColor(R.styleable.CommonItemView_common_switcher_bgColor_switchOff, Color.parseColor("#d8d8d8"))
        );
        setSwitcherMarginEnd(ta.getDimensionPixelSize(R.styleable.CommonItemView_common_switcher_marginEnd, 0));
        ta.recycle();
        setClickable(true);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    private void setSwitcherType(int switcherType) {
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

    private void setSwitcherElevation(int elevation) {
        mSwitcher.setElevation(elevation);
        invalidate();
    }

    private void setSwitcherChecked(boolean isChecked) {
        mSwitcher.setChecked(isChecked, true);
        mXSwitchButton.setChecked(isChecked);
    }

    public void setSwitcherChecked(boolean isChecked, boolean withAnimation) {
        mSwitcher.setChecked(isChecked, withAnimation);
        mXSwitchButton.setChecked(isChecked);
    }

    private void setSwitcherMarginEnd(int marginEnd) {
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

    private void setSwitcherVisible(boolean visible) {
        this.isSwitcherVisible = visible;
        mSwitcher.setVisibility(visible ? VISIBLE : GONE);
        mXSwitchButton.setVisibility(visible ? VISIBLE : GONE);
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
    }

    /**
     * 设置 margin值
     *
     * @param arrowMarginEnd 箭头的marginEnd
     */
    private void setArrowMarginEnd(int arrowMarginEnd) {
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
    private void setSubTextMarginEnd(int subTextMarginEnd) {
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
    private void setTitleMarginStart(int titleMarginStart) {
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
    private void setIconMarginEnd(int iconMarginEnd) {
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
    private void setIconMarginStart(int iconMarginStart) {
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
