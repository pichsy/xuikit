package com.pichs.app.xwidget.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.pichs.app.xwidget.R;
import com.pichs.common.utils.utils.ToastUtils;
import com.pichs.xuikit.indicator.LineIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 3/6/21 10:35 AM
 * @UpdateUser: 吴波
 * @UpdateDate: 3/6/21 10:35 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GiftsListFragment extends Fragment {

    private AppCompatActivity mActivity;
    private Context mContext;
    private int size;
    private List<GiftsBean> mList = new ArrayList<>();
    private List<List<GiftsBean>> mGiftsArray = new ArrayList<>();

    GiftsListFragment(int size) {
        this.size = size;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (AppCompatActivity) context;
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.live_fragemt_gifts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    private void initData() {
        for (int i = 0; i < size; i++) {
            mList.add(new GiftsBean()
                    .setGiftName("缤粉奶茶")
                    .setGiftIcon("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1357386296,4135433130&fm=26&gp=0.jpg")
                    .setGiftPrice("1金币"));
        }

        mGiftsArray = getGiftsArray(mList, 8);

    }

    private List<List<GiftsBean>> getGiftsArray(List<GiftsBean> list, int maxNumber) {
        int pageCount = list.size() % maxNumber == 0 ? list.size() / maxNumber : list.size() / maxNumber + 1;
        List<List<GiftsBean>> listArr = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            if (i == pageCount - 1) {
                List<GiftsBean> item = list.subList((i * maxNumber), list.size());
                listArr.add(item);
            } else {
                List<GiftsBean> item = list.subList(i * maxNumber, (i * maxNumber) + maxNumber);
                listArr.add(item);
            }
        }
        return listArr;
    }

    private void initView(View view) {
        LineIndicator magicIndicator = view.findViewById(R.id.magicIndicator);
        ViewPager2 viewPager2 = view.findViewById(R.id.rcv_gift_pager);
        GiftPageAdapter adapter = new GiftPageAdapter(mContext, mGiftsArray);
        viewPager2.setAdapter(adapter);
        magicIndicator.setMaxStep(mGiftsArray.size());
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        adapter.setOnItemClickChangedCallback(new OnItemClickChangedCallback() {

            @Override
            public void onItemSelected(int page, int position) {
                resetAllSelected(mGiftsArray);
                mGiftsArray.get(page).get(position).setSelected(true);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemSendClicked(int page, int position) {
                GiftsBean bean = mGiftsArray.get(page).get(position);
                ToastUtils.toast(mContext, "page: " + page + ", pos: " + position);
            }
        });
    }

    private void resetAllSelected(List<List<GiftsBean>> arr) {
        for (List<GiftsBean> list : arr) {
            for (GiftsBean bean : list) {
                bean.setSelected(false);
            }
        }
    }


    static class GiftPageAdapter extends RecyclerView.Adapter<GiftPageAdapter.GiftPageHolder> {

        private Context context;
        private List<List<GiftsBean>> giftsArray;
        private OnItemClickChangedCallback callback;

        public void setOnItemClickChangedCallback(OnItemClickChangedCallback callback) {
            this.callback = callback;
        }

        public GiftPageAdapter(Context context, List<List<GiftsBean>> giftsArray) {
            this.context = context;
            this.giftsArray = giftsArray;
        }


        @NonNull
        @Override
        public GiftPageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GiftPageHolder(LayoutInflater.from(context).inflate(R.layout.gifts_recycler_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull GiftPageHolder holder, int page) {
            List<GiftsBean> list = giftsArray.get(page);
            holder.rcv.setLayoutManager(new GridLayoutManager(context, 4, RecyclerView.VERTICAL, false));
            holder.rcv.setItemAnimator(null);
            GiftListAdapter adapter = new GiftListAdapter(context, list);
            holder.rcv.setAdapter(adapter);
            adapter.setOnItemClickChangedCallback(new OnItemClickChangedCallback() {
                @Override
                public void onItemSelected(int p, int position) {
                    callback.onItemSelected(page, position);
                }

                @Override
                public void onItemSendClicked(int p, int position) {
                    callback.onItemSendClicked(page, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return giftsArray == null ? 0 : giftsArray.size();
        }

        static class GiftPageHolder extends RecyclerView.ViewHolder {
            RecyclerView rcv;

            public GiftPageHolder(@NonNull View itemView) {
                super(itemView);
                rcv = itemView.findViewById(R.id.recycler_view);
            }
        }

    }


    static class GiftListAdapter extends RecyclerView.Adapter<GiftListAdapter.GiftListHolder> {

        private Context context;
        private List<GiftsBean> list;
        private OnItemClickChangedCallback callback;


        public void setOnItemClickChangedCallback(OnItemClickChangedCallback callback) {
            this.callback = callback;
        }

        public GiftListAdapter(Context context, List<GiftsBean> list) {
            this.context = context;
            this.list = list;
        }


        @NonNull
        @Override
        public GiftListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GiftListHolder(LayoutInflater.from(context).inflate(R.layout.live_gift_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull GiftListHolder holder, int position) {
            GiftsBean item = list.get(position);
            holder.tvPriceNor.setText(item.getGiftPrice());
            holder.tvPriceSel.setText(item.getGiftPrice());
            holder.tvTitle.setText(item.getGiftName());

            Glide.with(context)
                    .load(item.getGiftIcon())
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(holder.ivIconNor);

            Glide.with(context)
                    .load(item.getGiftIcon())
                    .into(holder.ivIconSel);

            if (item.isSelected()) {
                holder.llSel.setVisibility(View.VISIBLE);
                holder.llNor.setVisibility(View.GONE);
            } else {
                holder.llSel.setVisibility(View.GONE);
                holder.llNor.setVisibility(View.VISIBLE);
            }
            holder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!item.isSelected()) {
                        callback.onItemSelected(-1, position);
                    }
                }
            });

            holder.tvSendSel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.toast(context, "赠送了---");
                    callback.onItemSendClicked(-1, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        static class GiftListHolder extends RecyclerView.ViewHolder {
            View rootView;
            TextView tvTitle;
            TextView tvPriceNor;
            TextView tvPriceSel;
            TextView tvSendSel;
            ImageView ivIconNor;
            ImageView ivIconSel;
            View llNor;
            View llSel;

            public GiftListHolder(@NonNull View itemView) {
                super(itemView);
                rootView = itemView.findViewById(R.id.view_root);
                llNor = itemView.findViewById(R.id.ll_gift_nor);
                llSel = itemView.findViewById(R.id.ll_gift_sel);
                tvTitle = itemView.findViewById(R.id.tv_title);
                ivIconNor = itemView.findViewById(R.id.iv_icon);
                tvPriceNor = itemView.findViewById(R.id.tv_price);
                tvPriceSel = itemView.findViewById(R.id.tv_price_sel);
                tvSendSel = itemView.findViewById(R.id.tv_send_sel);
                ivIconSel = itemView.findViewById(R.id.iv_icon_sel);
            }
        }
    }

    interface OnItemClickChangedCallback {
        void onItemSelected(int page, int position);

        void onItemSendClicked(int page, int position);
    }
}
