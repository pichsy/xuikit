package com.pichs.xuikit.update;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;

import com.pichs.common.widget.cardview.GradientOrientation;
import com.pichs.common.widget.cardview.XCardLinearLayout;
import com.pichs.common.widget.progressbar.XProgressBar;
import com.pichs.common.widget.view.XTextView;
import com.pichs.xuikit.R;


public class UpdateDialog extends Dialog {
    private static final String TAG = UpdateDialog.class.getSimpleName();
    private XTextView mTitle;
    private XTextView mMessage;
    private XProgressBar mProgressBar;
    private XTextView mCancel;
    private XTextView mSure;
    private View mLayoutBtn;
    private XCardLinearLayout mRootView;

    public UpdateDialog(@NonNull Context context) {
        this(context, R.style.NiceDialogStyle);
    }

    public UpdateDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    private void init(Context context) {
        View root = View.inflate(context, R.layout.xuikit_update_dialog_layout, null);
        setContentView(root);
        mRootView = findViewById(R.id.update_root_ll);
        mTitle = findViewById(R.id.update_title);
        mMessage = findViewById(R.id.update_message);
        mCancel = findViewById(R.id.update_cancel);
        mSure = findViewById(R.id.update_sure);
        mProgressBar = findViewById(R.id.update_progress_bar);
        mLayoutBtn = findViewById(R.id.update_layout_btn);
    }

    public UpdateDialog setRadius(int radius) {
        mRootView.setRadius(radius);
        return this;
    }

    public UpdateDialog setBackgroundGradient(int startColor, int endColor, @GradientOrientation int orientation) {
        mRootView.setBackgroundGradient(startColor, endColor, orientation);
        return this;
    }

    public UpdateDialog setBackgroundColor(int color) {
        mRootView.setBackgroundColor(color);
        return this;
    }

    public UpdateDialog setBackgroundResource(int resId) {
        mRootView.setBackgroundResource(resId);
        return this;
    }

    public UpdateDialog setBackground(Drawable background) {
        mRootView.setBackground(background);
        return this;
    }

    public UpdateDialog setTitle(String title) {
        setShowTitle(true);
        mTitle.setText(title);
        return this;
    }

    public UpdateDialog setMessage(String message) {
        setShowMessage(true);
        mMessage.setText(message);
        return this;
    }

    public UpdateDialog setProgress(int progress) {
        setShowProgressBar(true);
        mProgressBar.setProgress(progress);
        return this;
    }

    public UpdateDialog setProgressColor(int progressColor) {
        setShowProgressBar(true);
        mProgressBar.setProgressColor(progressColor);
        return this;
    }

    public UpdateDialog setProgressBackgroundColor(int backgroundColor) {
        setShowProgressBar(true);
        mProgressBar.setBackgroundColor(backgroundColor);
        return this;
    }

    public UpdateDialog setCancelBtn(String cancel, final OnBtnClickCallback callback) {
        mCancel.setText(cancel);
        if (callback != null) {
            setShowBtnLayout(true);
            mCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(UpdateDialog.this, v);
                }
            });
        }
        return this;
    }

    public UpdateDialog setCancelBtnColor(int color) {
        if (mCancel != null) {
            mCancel.setTextColor(color);
        }
        return this;
    }

    public UpdateDialog setOkBtnColor(int color) {
        if (mSure != null) {
            mSure.setTextColor(color);
        }
        return this;
    }

    public UpdateDialog setOkBtn(String ok, final OnBtnClickCallback callback) {
        mSure.setText(ok);
        if (callback != null) {
            setShowBtnLayout(true);
            mSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(UpdateDialog.this, v);
                }
            });
        }
        return this;
    }

    public UpdateDialog setShowBtnLayout(boolean isShow) {
        mLayoutBtn.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public UpdateDialog setShowCancelBtn(boolean isShow) {
        mCancel.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public UpdateDialog setShowProgressBar(boolean isShow) {
        mProgressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public UpdateDialog setShowMessage(boolean isShow) {
        mMessage.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public UpdateDialog setShowTitle(boolean isShow) {
        mTitle.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

    public UpdateDialog cancelable(boolean cancelable) {
        this.setCancelable(cancelable);
        return this;
    }

    public UpdateDialog canceledOnTouchOutside(boolean cancelable) {
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public interface OnBtnClickCallback {
        void onClick(UpdateDialog dialog, View view);
    }

}
