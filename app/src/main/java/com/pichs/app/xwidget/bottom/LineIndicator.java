package com.pichs.app.xwidget.bottom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.pichs.app.xwidget.R;
import com.pichs.common.widget.utils.XDisplayHelper;

/**
 * @Description: $
 * @Author: WuBo
 * @CreateDate: 2020/10/23$ 11:13$
 * @UpdateUser: WuBo
 * @UpdateDate: 2020/10/23$ 11:13$
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class LineIndicator extends View {

    // 选中的颜色，
    private int selectedColor;
    // 非选中的颜色
    private int normalColor;
    private Paint selectedPaint;
    private Paint normalPaint;

    // 【1-4】 = 4步  使用时为（0，1，2，3）
    private int maxStep = 3;

    private float width;

    private float height;

    private float indicatorHeight;
    private float stepWidth;

    private float offsetX = 0;
    private int position = 0;

    public LineIndicator(Context context) {
        this(context, null);
    }

    public LineIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    // 初始化
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LineIndicator);
        maxStep = ta.getInt(R.styleable.LineIndicator_zyt_indicator_max_step, 3) - 1;
        position = ta.getInt(R.styleable.LineIndicator_zyt_indicator_current_step, 1) - 1;
        selectedColor = ta.getColor(R.styleable.LineIndicator_zyt_indicator_color, Color.BLUE);
        normalColor = ta.getColor(R.styleable.LineIndicator_zyt_indicator_background_color, Color.LTGRAY);
        indicatorHeight = ta.getDimensionPixelOffset(R.styleable.LineIndicator_zyt_indicator_height, 0);

        // 理性判断
        if (maxStep <= 0) {
            maxStep = 1;
        }
        if (position < 0) {
            position = 0;
        }
        if (position > maxStep) {
            position = maxStep;
        }
        ta.recycle();
        selectedPaint = new Paint();
        selectedPaint.setColor(selectedColor);
        selectedPaint.setAntiAlias(true);
        normalPaint = new Paint();
        normalPaint.setColor(normalColor);
        normalPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        stepWidth = width / maxStep;
        offsetX = stepWidth * position;
        if (indicatorHeight <= 0) {
            indicatorHeight = height - XDisplayHelper.dp2px(getContext(), 2f);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0f, 0f, width, height, height / 2, height / 2, normalPaint);
    }


    /**
     * 0 - n
     */
    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
        if (this.maxStep <= 0) {
            this.maxStep = 1;
        }
        stepWidth = width / maxStep;
        offsetX = stepWidth * position;
        invalidate();
    }

    public void setCurrentPosition(int position){
        if (this.position==position){
            return;
        }
        this.position = position;
        offsetX = stepWidth * position;
        invalidate();
    }

    public void setIndicatorColor(int color) {
        this.selectedColor = color;
        selectedPaint = new Paint();
        selectedPaint.setColor(selectedColor);
        selectedPaint.setAntiAlias(true);
        invalidate();
    }

    public void setBackgroundColor(int color) {
        this.normalColor = color;
        normalPaint = new Paint();
        normalPaint.setColor(normalColor);
        normalPaint.setAntiAlias(true);
        invalidate();
    }

    public void setIndicatorHeight(int height) {
        this.indicatorHeight = height;
        invalidate();
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        canvas.drawRoundRect(offsetX, (height - indicatorHeight) / 2, offsetX + stepWidth, height / 2 + indicatorHeight / 2, indicatorHeight / 2, indicatorHeight / 2, selectedPaint);
    }



    public void onPageSelected(int position) {
        Log.d("onPageScrolled", "onPageSelected= 11> position:" + position);
        if (this.position != position) {
            this.position = position;
            offsetX = stepWidth * position;
            invalidate();
        }
    }

}
