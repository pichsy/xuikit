package com.pichs.xuikit.arcview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xuikit.R;


public class HeadArcView extends View {

    @IntDef({
            ArcType.OUT_SIDE,
            ArcType.IN_SIDE
    })
    public @interface ArcType {
        int IN_SIDE = 0;
        int OUT_SIDE = 1;
    }

    private static final int GRADIENT_DIRECTION_HORIZONTAL = 0;
    private static final int GRADIENT_DIRECTION_VERTICAL = 1;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path mPath = new Path();
    private Shader mShader;

    @ArcType
    private int arcType = ArcType.IN_SIDE;
    private int arcHeight = XDisplayHelper.dpToPx(16);

    private int startColor = Color.TRANSPARENT;
    private int endColor = Color.TRANSPARENT;
    //    private int startColor = Color.BLUE;
//    private int endColor = Color.BLUE;
    private int gradientOrientation = GRADIENT_DIRECTION_VERTICAL;
    private boolean isReverse = true;

    public HeadArcView(Context context) {
        this(context, null);
    }

    public HeadArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HeadArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HeadArcView, defStyleAttr, defStyleRes);
        arcHeight = ta.getDimensionPixelSize(R.styleable.HeadArcView_xp_arc_arcHeight, XDisplayHelper.dp2px(context, 20));
        arcType = ta.getInteger(R.styleable.HeadArcView_xp_arc_type, ArcType.IN_SIDE);
        startColor = ta.getColor(R.styleable.HeadArcView_xp_arc_startColor, 0);
        endColor = ta.getColor(R.styleable.HeadArcView_xp_arc_endColor, 0);
        gradientOrientation = ta.getInteger(R.styleable.HeadArcView_xp_arc_gradientOrientation, GRADIENT_DIRECTION_VERTICAL);
        isReverse = ta.getBoolean(R.styleable.HeadArcView_xp_arc_reverse, true);
        ta.recycle();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calPath();
        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 默认就是上下线性渐变，由上而下
     *
     * @param startColor startColor
     * @param endColor   endColor
     */
    public void setArcColors(int startColor, int endColor) {
        clearShader();
        this.startColor = startColor;
        this.endColor = endColor;
        postInvalidate();
    }

    /**
     * 默认就是上下线性渐变，由上而下
     *
     * @param startColor          startColor
     * @param endColor            endColor
     * @param gradientOrientation 渐变色方向，由上而下，由左到右
     */
    public void setArcColors(int startColor, int endColor, int gradientOrientation) {
        clearShader();
        this.gradientOrientation = gradientOrientation;
        this.startColor = startColor;
        this.endColor = endColor;
        postInvalidate();
    }

    /**
     * 设置缺口的高度，默认100px。
     *
     * @param arcHeight 缺口高度 px单位
     */
    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
        postInvalidate();
    }

    /**
     * 缺口方向，默认朝里，可以设置朝外
     *
     * @param arcType
     */
    public void setArcType(@ArcType int arcType) {
        this.arcType = arcType;
        postInvalidate();
    }

    private void clearShader() {
        mShader = null;
    }

    private void calPath() {
        int width = getWidth();
        int height = getHeight();
        if (mShader == null) {
            if (gradientOrientation == GRADIENT_DIRECTION_VERTICAL) {
                mShader = new LinearGradient(0, 0, 0, height, startColor, endColor, Shader.TileMode.CLAMP);
            } else {
                mShader = new LinearGradient(0, 0, width, 0, startColor, endColor, Shader.TileMode.CLAMP);
            }
        }
        mPaint.setShader(mShader);
        mPath.reset();
        if (isReverse) {
            mPath.moveTo(width, height);
        } else {
            mPath.moveTo(0, 0);
        }
        switch (arcType) {
            case ArcType.OUT_SIDE:
                if (isReverse) {
                    mPath.lineTo(width, arcHeight);
                    mPath.quadTo(width / 2.0f, -arcHeight, 0, arcHeight);
                    mPath.lineTo(0, height);
                } else {
                    mPath.lineTo(0, height - arcHeight);
                    mPath.quadTo(width / 2.0f, height + arcHeight, width, height - arcHeight);
                    mPath.lineTo(width, 0);
                }
                break;
            case ArcType.IN_SIDE:
                if (isReverse) {
                    mPath.lineTo(width, 0);
                    mPath.quadTo(width / 2.0f, arcHeight * 2, 0, 0);
                    mPath.lineTo(0, height);
                } else {
                    mPath.lineTo(0, height);
                    mPath.quadTo(width / 2.0f, height - arcHeight * 2, width, height);
                    mPath.lineTo(width, 0);
                }
                break;
        }
        mPath.close();
    }
}
