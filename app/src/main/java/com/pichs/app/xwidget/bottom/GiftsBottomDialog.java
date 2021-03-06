package com.pichs.app.xwidget.bottom;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pichs.app.xwidget.R;
import com.pichs.common.utils.utils.ToastUtils;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.xdialog.base.BaseDialogFragment;
import com.pichs.xdialog.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 礼物不嫌多, 疯狂刷刷刷
 */
public class GiftsBottomDialog extends BaseDialogFragment {

    private AppCompatActivity mActivity;
    private List<BottomTabItemBean> mTabList = new ArrayList<>();
    private List<Fragment> mFragmentList = new ArrayList<>();
    private int mCurrentPosition = 0;


    public GiftsBottomDialog(AppCompatActivity activity) {
        mActivity = activity;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setHeight(XDisplayHelper.dp2px(activity, 330f))
                .setDimAmount(0.5f)
                .setOutCancel(true)
                .setAnimStyle(R.style.XP_Animation_Bottom)
                .setGravity(Gravity.BOTTOM)
        ;
        mTabList.add(new BottomTabItemBean()
                .setTitle("推荐")
                .setTitleColor(Color.WHITE)
                .setSelected(true)
                .setTitleSelectedColor(Color.BLUE)
                .setTitleTextSize(13));

        mTabList.add(new BottomTabItemBean()
                .setTitle("豪华")
                .setTitleColor(Color.WHITE)
                .setTitleSelectedColor(Color.BLUE)
                .setTitleTextSize(13));

        mFragmentList.add(new GiftsListFragment(10));
        mFragmentList.add(new GiftsListFragment(28));

    }

    @Override
    public int intLayoutId() {
        return R.layout.gifts_bottom_dialog;
    }

    public void show() {
        if (!isAdded() && !isVisible()) {
            show(mActivity.getSupportFragmentManager());
        } else {
            FragmentTransaction ft = mActivity.getSupportFragmentManager().beginTransaction();
            ft.show(this).commit();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void convertView(ViewHolder holder, BaseDialogFragment dialog) {
        FragmentManager childFragmentManager = this.getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        for (Fragment fragment : mFragmentList) {
            transaction.add(R.id.fl_container, fragment);
            transaction.hide(fragment);
        }
        transaction.show(mFragmentList.get(mCurrentPosition));
        transaction.commit();

        RecyclerView recycler = holder.findViewById(R.id.rcv_tab);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        BottomTabAdapter adapter = new BottomTabAdapter(getContext(), mTabList);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener((view, position) -> {
            ToastUtils.toast(mActivity.getApplicationContext(), "pos:" + position);
            if (mCurrentPosition != position) {
                mTabList.get(mCurrentPosition).setSelected(false);
                mTabList.get(position).setSelected(true);
                adapter.notifyItemChanged(mCurrentPosition);
                adapter.notifyItemChanged(position);
                showOrHideFragment(position);
                mCurrentPosition = position;
            }
        });
    }

    private void showOrHideFragment(int position) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();
        for (Fragment fragment : mFragmentList) {
            transaction.hide(fragment);
        }
        transaction.show(mFragmentList.get(position));
        transaction.commit();
    }


    private static class BottomTabAdapter extends RecyclerView.Adapter<BottomTabAdapter.BottomTabHolder> {

        private Context mContext;
        private List<BottomTabItemBean> mData;
        private OnItemClickedListener mOnItemClickedListener;

        BottomTabAdapter(Context context, List<BottomTabItemBean> data) {
            this.mContext = context;
            this.mData = data;

        }

        public void setOnItemClickListener(OnItemClickedListener l) {
            mOnItemClickedListener = l;
        }

        @NonNull
        @Override
        public BottomTabHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BottomTabHolder(LayoutInflater.from(mContext).inflate(R.layout.gifts_bottom_tab_item_layout, null));
        }

        @Override
        public void onBindViewHolder(@NonNull BottomTabHolder holder, int position) {

            BottomTabItemBean item = mData.get(position);
            holder.tvTitle.setText(item.getTitle());
            holder.tvTitle.setTextSize(item.getTitleTextSize());

            if (item.isSelected()) {
                holder.tvTitle.setTextColor(item.getTitleSelectedColor());
            } else {
                holder.tvTitle.setTextColor(item.getTitleColor());
            }

            holder.rlRoot.setOnClickListener(v -> {
                if (mOnItemClickedListener != null) {
                    mOnItemClickedListener.onItemClick(v, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        private static class BottomTabHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;
            View rlRoot;

            public BottomTabHolder(@NonNull View itemView) {
                super(itemView);
                rlRoot = itemView.findViewById(R.id.rl_root);
                tvTitle = itemView.findViewById(R.id.tv_title);
            }
        }

    }

    interface OnItemClickedListener {
        void onItemClick(View view, int position);
    }
}
