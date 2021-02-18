package com.pichs.xuikit.cells;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XCardConstraintLayout;
import com.pichs.common.widget.cardview.XCardImageView;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.view.XTextView;
import com.pichs.xuikit.R;

/**
 * 通用 Section， 用于小模块的标题头
 * 大概长这样
 * -----------------------------
 * | |小色块| 标题          更多> |
 * -----------------------------
 */
public class CommonSection extends XCardConstraintLayout {

    private XCardImageView ivTitleIcon;
    private XTextView tvTitle;
    private XTextView tvMore;
    private XCardImageView ivMoreIcon;

    // src和背景可共存
    private Drawable titleIcon;
    private Drawable moreIcon;

    private CharSequence titleText;
    private CharSequence moreText;

    private int titleIconRadius;
    private int moreIconRadius;

    // 外边距
    private int titleIconMarginStart;
    private int titleIconMarginEnd;
    private int titleTextMarginStart;
    private int moreTextMarginEnd;
    private int moreIconMarginEnd;
    private int moreIconMarginStart;

    // padding间距
    private int titleIconPadding;
    private int moreIconPadding;

    // 标题图片的背景色，渐变色
    private int titleIconBgStartColor;
    private int titleIconBgEndColor;
    // 背景色，和渐变色二选一
    private int titleIconBgColor;
    private int titleIconWidth, titleIconHeight;
    // 更多渐变色，背景色
    private int moreIconBgStartColor;
    private int moreIconBgEndColor;
    private int moreIconBgColor;
    private int moreIconWidth, moreIconHeight;
    // 字体颜色
    private int titleTextColor;
    private float titleTextSize;
    private int moreTextColor;
    private float moreTextSize;
    @GradientOrientation
    private int titleIconBgColorOrientation;
    @GradientOrientation
    private int moreIconBgColorOrientation;
    private boolean titleIconVisibility = true;
    private boolean moreIconVisibility = true;
    private boolean titleTextIgnoreTypeface, moreTextIgnoreTypeface;

    public CommonSection(Context context) {
        super(context);
        init(context, null, 0);
    }


    public CommonSection(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CommonSection(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化属性
     *
     * @param context      context
     * @param attrs        xuikit_attrs
     * @param defStyleAttr defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.xuikit_common_section, this);
        ivTitleIcon = findViewById(R.id.iv_title_icon);
        tvTitle = findViewById(R.id.tv_title);
        tvMore = findViewById(R.id.tv_more);
        ivMoreIcon = findViewById(R.id.iv_more_icon);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonSection, defStyleAttr, 0);
        titleText = ta.getString(R.styleable.CommonSection_xp_section_titleText);
        moreText = ta.getString(R.styleable.CommonSection_xp_section_moreText);
        titleTextIgnoreTypeface = ta.getBoolean(R.styleable.CommonSection_xp_section_titleText_ignoreTypeface, false);
        moreTextIgnoreTypeface = ta.getBoolean(R.styleable.CommonSection_xp_section_moreText_ignoreTypeface, false);

        titleIcon = ta.getDrawable(R.styleable.CommonSection_xp_section_titleIcon);
        moreIcon = ta.getDrawable(R.styleable.CommonSection_xp_section_moreIcon);

        titleTextColor = ta.getColor(R.styleable.CommonSection_xp_section_titleTextColor, Color.BLACK);
        moreTextColor = ta.getColor(R.styleable.CommonSection_xp_section_moreTextColor, Color.GRAY);


        titleTextMarginStart = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleTextMarginStart, XDisplayHelper.dp2px(context, 10f));
        moreTextMarginEnd = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreTextMarginEnd, XDisplayHelper.dp2px(context, 5f));
        titleTextSize = XDisplayHelper.px2sp(context, ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleTextSize, XDisplayHelper.sp2px(context, 16f)));
        moreTextSize = XDisplayHelper.px2sp(context, ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreTextSize, XDisplayHelper.sp2px(context, 14f)));

        titleIconMarginStart = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleIconMarginStart, 0);
        titleIconMarginEnd = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleIconMarginEnd, 0);
        titleIconPadding = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleIconPadding, 0);
        titleIconWidth = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleIconWidth, XDisplayHelper.dp2px(context, 4f));
        titleIconHeight = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleIconHeight, XDisplayHelper.dp2px(context, 16f));
        titleIconRadius = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_titleIconRadius, 0);
        titleIconBgColor = ta.getColor(R.styleable.CommonSection_xp_section_titleIconBgColor, 0);
        titleIconBgStartColor = ta.getColor(R.styleable.CommonSection_xp_section_titleIconBgStartColor, 0);
        titleIconBgEndColor = ta.getColor(R.styleable.CommonSection_xp_section_titleIconBgEndColor, 0);
        titleIconBgColorOrientation = ta.getInt(R.styleable.CommonSection_xp_section_titleIconBgColorOrientation, GradientOrientation.VERTICAL);

        moreIconMarginEnd = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreIconMarginEnd, 0);
        moreIconMarginStart = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreIconMarginStart, 0);
        moreIconPadding = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreIconPadding, 0);
        moreIconWidth = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreIconWidth, XDisplayHelper.dp2px(context, 16f));
        moreIconHeight = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreIconHeight, XDisplayHelper.dp2px(context, 16f));
        moreIconRadius = ta.getDimensionPixelSize(R.styleable.CommonSection_xp_section_moreIconRadius, 0);
        moreIconBgColor = ta.getColor(R.styleable.CommonSection_xp_section_moreIconBgColor, 0);
        moreIconBgEndColor = ta.getColor(R.styleable.CommonSection_xp_section_moreIconBgEndColor, 0);
        moreIconBgStartColor = ta.getColor(R.styleable.CommonSection_xp_section_moreIconBgStartColor, 0);
        moreIconBgColorOrientation = ta.getInt(R.styleable.CommonSection_xp_section_moreIconBgColorOrientation, GradientOrientation.VERTICAL);

        ta.recycle();
        setTitleTextIgnoreTypeface(titleTextIgnoreTypeface);
        setTitleText(titleText);
        setTitleTextSize(titleTextSize);
        setTitleTextColor(titleTextColor);
        setTitleTextMarginStart(titleTextMarginStart);

        setMoreTextIgnoreTypeface(moreTextIgnoreTypeface);
        setMoreText(moreText);
        setMoreTextSize(moreTextSize);
        setMoreTextColor(moreTextColor);
        setMoreTextMarginEnd(moreTextMarginEnd);

        setTitleIcon(titleIcon);
        setTitleIconPadding(titleIconPadding);
        setTitleIconMargin(titleIconMarginStart, titleIconMarginEnd);
        setTitleIconRadius(titleIconRadius);
        setTitleIconSize(titleIconWidth, titleIconHeight);
        if (titleIcon == null && titleIconBgColor == 0 && titleIconBgStartColor == 0 && titleIconBgEndColor == 0) {
            setTitleIconVisibility(false);
        }
        setMoreIcon(moreIcon);
        setMoreIconPadding(moreIconPadding);
        setMoreIconRadius(moreIconRadius);
        setMoreIconMargin(moreIconMarginStart, moreIconMarginEnd);
        setMoreIconSize(moreIconWidth, moreIconHeight);
        if (moreIcon == null && moreIconBgColor == 0 && moreIconBgStartColor == 0 && moreIconBgEndColor == 0) {
            setMoreIconVisibility(false);
        }
        if (titleIconBgStartColor != 0 && titleIconBgEndColor != 0) {
            setTitleIconBackgroundColor(titleIconBgStartColor, titleIconBgEndColor, titleIconBgColorOrientation);
        } else {
            setTitleIconBackgroundColor(titleIconBgColor);
        }

        if (moreIconBgStartColor != 0 && moreIconBgEndColor != 0) {
            setMoreIconBackgroundColor(moreIconBgStartColor, moreIconBgEndColor, moreIconBgColorOrientation);
        } else {
            setMoreIconBackgroundColor(moreIconBgColor);
        }
    }

    public CommonSection setTitleTextIgnoreTypeface(boolean ignoreTypeface) {
        this.titleTextIgnoreTypeface = ignoreTypeface;
        if (tvTitle != null) {
            tvTitle.setIgnoreGlobalTypeface(this.titleTextIgnoreTypeface);
        }
        return this;
    }

    public CommonSection setMoreTextIgnoreTypeface(boolean ignoreTypeface) {
        this.moreTextIgnoreTypeface = ignoreTypeface;
        if (tvMore != null) {
            tvMore.setIgnoreGlobalTypeface(this.moreTextIgnoreTypeface);
        }
        return this;
    }

    public CommonSection setTitleIconResource(int resId) {
        if (resId != 0) {
            titleIcon = ContextCompat.getDrawable(getContext(), resId);
        }
        if (titleIcon == null) {
            if (ivTitleIcon != null) {
                ivTitleIcon.setVisibility(GONE);
            }
            return this;
        }
        if (ivTitleIcon != null) {
            ivTitleIcon.setImageDrawable(titleIcon);
            ivTitleIcon.setVisibility(VISIBLE);
        }
        return this;
    }

    public CommonSection setTitleIcon(Drawable drawable) {
        this.titleIcon = drawable;
        if (this.titleIcon == null) {
            return this;
        }
        if (ivTitleIcon != null) {
            ivTitleIcon.setImageDrawable(this.titleIcon);
        }
        return this;
    }

    public CommonSection setTitleIconVisibility(boolean titleIconVisibility) {
        this.titleIconVisibility = titleIconVisibility;
        if (ivTitleIcon != null) {
            ivTitleIcon.setVisibility(this.titleIconVisibility ? VISIBLE : GONE);
        }
        return this;
    }

    public CommonSection setMoreIconVisibility(boolean moreIconVisibility) {
        this.moreIconVisibility = moreIconVisibility;
        if (ivMoreIcon != null) {
            ivMoreIcon.setVisibility(this.moreIconVisibility ? VISIBLE : GONE);
        }
        return this;
    }

    public CommonSection setTitleIconRadius(int radius) {
        if (radius < 0) {
            radius = 0;
        }
        if (ivTitleIcon != null) {
            ivTitleIcon.setRadius(radius);
        }
        return this;
    }

    // px 单位
    public CommonSection setTitleIconPadding(int padding) {
        if (padding < 0) {
            padding = 0;
        }
        titleIconPadding = padding;
        if (ivTitleIcon != null) {
            ivTitleIcon.setPadding(titleIconPadding, titleIconPadding, titleIconPadding, titleIconPadding);
        }
        return this;
    }

    public CommonSection setTitleIconBackgroundColor(int startColor, int endColor, @GradientOrientation int orientation) {
        titleIconBgStartColor = startColor;
        titleIconBgEndColor = endColor;
        titleIconBgColorOrientation = orientation;
        if (ivTitleIcon != null) {
            ivTitleIcon.setBackgroundGradient(titleIconBgStartColor, titleIconBgEndColor, orientation);
        }
        return this;
    }

    public CommonSection setTitleIconBackgroundColor(int color) {
        titleIconBgColor = color;
        if (ivTitleIcon != null) {
            ivTitleIcon.setBackgroundColor(titleIconBgColor);
        }
        return this;
    }

    /**
     * 标题左边的图片的左右margin  单位px
     *
     * @param start 左间距
     * @param end   右间距
     * @return CommonSection
     */
    public CommonSection setTitleIconMargin(int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 1;
        }
        titleIconMarginEnd = end;
        titleIconMarginStart = start;
        if (ivTitleIcon != null) {
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) ivTitleIcon.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.setMarginStart(start);
                layoutParams.setMarginEnd(end);
                ivTitleIcon.setLayoutParams(layoutParams);
            }
        }
        return this;
    }


    public CommonSection setMoreIconResource(int resId) {
        if (resId != 0) {
            moreIcon = ContextCompat.getDrawable(getContext(), resId);
        }
        if (moreIcon != null) {
            if (ivMoreIcon != null) {
                ivMoreIcon.setVisibility(VISIBLE);
                ivMoreIcon.setImageDrawable(moreIcon);
            }
        } else {
            if (ivMoreIcon != null) {
                ivMoreIcon.setVisibility(GONE);
            }
        }
        return this;
    }

    public CommonSection setMoreIcon(Drawable drawable) {
        moreIcon = drawable;
        if (moreIcon != null) {
            if (ivMoreIcon != null) {
                ivMoreIcon.setVisibility(VISIBLE);
                ivMoreIcon.setImageDrawable(moreIcon);
            }
        } else {
            if (ivMoreIcon != null) {
                ivMoreIcon.setVisibility(GONE);
            }
        }
        return this;
    }

    public CommonSection setMoreIconRadius(int radius) {
        if (radius < 0) {
            radius = 0;
        }
        if (ivMoreIcon != null) {
            ivMoreIcon.setRadius(radius);
        }
        return this;
    }

    // px 单位
    public CommonSection setMoreIconPadding(int padding) {
        if (padding < 0) {
            padding = 0;
        }
        moreIconPadding = padding;
        if (ivMoreIcon != null) {
            ivMoreIcon.setPadding(moreIconPadding, moreIconPadding, moreIconPadding, moreIconPadding);
        }
        return this;
    }

    public CommonSection setMoreIconBackgroundColor(int startColor, int endColor, @GradientOrientation int orientation) {
        moreIconBgStartColor = startColor;
        moreIconBgEndColor = endColor;
        if (ivMoreIcon != null) {
            ivMoreIcon.setBackgroundGradient(moreIconBgStartColor, moreIconBgEndColor, orientation);
        }
        return this;
    }

    public CommonSection setMoreIconBackgroundColor(int color) {
        moreIconBgColor = color;
        if (ivMoreIcon != null) {
            ivMoreIcon.setBackgroundColor(moreIconBgColor);
        }
        return this;
    }

    public CommonSection setMoreIconMargin(int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 1;
        }
        moreIconMarginEnd = end;
        moreIconMarginStart = start;
        if (ivMoreIcon != null) {
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) ivMoreIcon.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.setMarginStart(start);
                layoutParams.setMarginEnd(end);
                ivMoreIcon.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public CommonSection setTitleText(CharSequence title) {
        titleText = title;
        if (titleText != null) {
            if (tvTitle != null) {
                tvTitle.setVisibility(VISIBLE);
                tvTitle.setText(titleText);
            }
        } else {
            if (tvTitle != null) {
                tvTitle.setVisibility(GONE);
            }
        }
        return this;
    }

    public CommonSection setTitleTextColor(int titleTextColor) {
        this.titleTextColor = titleTextColor;
        tvTitle.setTextColor(this.titleTextColor);
        return this;
    }

    public CommonSection setTitleTextSize(float titleTextSizeDp) {
        this.titleTextSize = titleTextSizeDp;
        tvTitle.setTextSize(this.titleTextSize);
        return this;
    }

    public CommonSection setMoreTextSize(float moreTextSizeDp) {
        this.moreTextSize = moreTextSizeDp;
        tvMore.setTextSize(this.moreTextSize);
        return this;
    }

    public CommonSection setMoreTextColor(int moreTextColor) {
        this.moreTextColor = moreTextColor;
        tvMore.setTextColor(this.moreTextColor);
        return this;
    }

    public CommonSection setMoreText(CharSequence text) {
        this.moreText = text;
        if (moreText == null) {
            if (tvMore != null) {
                tvMore.setVisibility(GONE);
            }
        } else {
            if (tvMore != null) {
                tvMore.setVisibility(VISIBLE);
                tvMore.setText(moreText);
            }
        }
        return this;
    }


    public CommonSection setMoreIconMarginEnd(int moreIconMarginEnd) {
        this.moreIconMarginEnd = moreIconMarginEnd;
        if (tvMore != null) {
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) tvMore.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.setMarginEnd(moreIconMarginEnd);
                tvMore.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public CommonSection setMoreIconMarginStart(int moreIconMarginStart) {
        this.moreIconMarginStart = moreIconMarginStart;
        if (tvMore != null) {
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) tvMore.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.setMarginStart(moreIconMarginEnd);
                tvMore.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public CommonSection setTitleTextMarginStart(int titleTextMarginStart) {
        this.titleTextMarginStart = titleTextMarginStart;
        if (tvTitle != null) {
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) tvTitle.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.setMarginStart(this.titleTextMarginStart);
                tvTitle.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public CommonSection setMoreTextMarginEnd(int moreTextMarginEnd) {
        this.moreTextMarginEnd = moreTextMarginEnd;
        if (tvMore != null) {
            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) tvMore.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.setMarginEnd(this.moreTextMarginEnd);
                tvMore.setLayoutParams(layoutParams);
            }
        }
        return this;
    }


    /**
     * 设置更多图标的大小
     *
     * @param width  宽
     * @param height 高
     * @return this
     */
    public CommonSection setMoreIconSize(int width, int height) {
        this.moreIconWidth = width;
        this.moreIconHeight = height;
        if (ivMoreIcon != null) {
            ViewGroup.LayoutParams layoutParams = ivMoreIcon.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.moreIconWidth;
                layoutParams.height = this.moreIconHeight;
                ivMoreIcon.setLayoutParams(layoutParams);
            }
        }
        return this;
    }

    public CommonSection setTitleIconSize(int width, int height) {
        this.titleIconWidth = width;
        this.titleIconHeight = height;

        if (ivTitleIcon != null) {
            ViewGroup.LayoutParams layoutParams = ivTitleIcon.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = this.titleIconWidth;
                layoutParams.height = this.titleIconHeight;
            }
        }

        return this;
    }

    public CommonSection setMoreClickListener(OnClickListener moreClickListener) {
        if (moreClickListener != null) {
            tvMore.setOnClickListener(moreClickListener);
            ivMoreIcon.setOnClickListener(moreClickListener);
        }
        return this;
    }

    public CommonSection setTitleClickListener(OnClickListener titleClickListener) {
        if (titleClickListener != null) {
            tvTitle.setOnClickListener(titleClickListener);
            ivTitleIcon.setOnClickListener(titleClickListener);
        }
        return this;
    }
}
