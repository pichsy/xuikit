package com.pichs.xuikit.innerscroll;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * 嵌套在ScrollView中的GridView
 */
public class InnerGridView extends GridView {

    private int mMaxHeight = Integer.MAX_VALUE >> 2;

    public InnerGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InnerGridView(Context context) {
        super(context);
    }

    public InnerGridView(Context context, int maxHeight) {
        super(context);
        mMaxHeight = maxHeight;
    }

    public InnerGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setMaxHeight(int maxHeight) {
        if (mMaxHeight != maxHeight) {
            mMaxHeight = maxHeight;
            requestLayout();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup.LayoutParams lp = getLayoutParams();
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int maxHeight = Math.min(heightSize, mMaxHeight);
        int expandSpec;
        if (lp.height > 0 && lp.height <= mMaxHeight) {
            expandSpec = MeasureSpec.makeMeasureSpec(lp.height, MeasureSpec.EXACTLY);
        } else {
            expandSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.AT_MOST);
        }

        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
