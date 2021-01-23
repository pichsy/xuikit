package com.pichs.xuikit.toolbar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pichs.common.widget.cardview.XCardConstraintLayout;
import com.pichs.common.widget.utils.XDisplayHelper;
import com.pichs.common.widget.view.XImageView;
import com.pichs.common.widget.view.XTextView;
import com.pichs.xdialog.action.PopActions;
import com.pichs.xuikit.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class XPopupMenu {

    private List<XPopupMenuItem> mMenuItemList = new ArrayList<>();
    private Context mContext;
    private PopListAdapter mAdapter;
    private Builder builder;
    private RecyclerView mListView;
    private int mMenuWidth = 0;
    private int mMenuHeight = 0;

    private XPopupMenu(Context context, @NotNull Builder builder) {
        this.mContext = context;
        this.builder = builder;
        this.mMenuHeight = builder.menuHeight;
        this.mMenuWidth = builder.menuWidth;
        init(context);
    }

    public static class Builder {
        private Context context;
        private int menuWidth = 0;
        private int menuHeight = 0;
        private int menuArrowHeight;
        private int menuArrowWidth;
        private List<XPopupMenuItem> menuItemList;
        @ColorInt
        private int itemLayoutPressedColor = Color.TRANSPARENT;
        @ColorInt
        private int itemTextColor = Color.LTGRAY;
        @ColorInt
        private int dividerColor = Color.parseColor("#555555");
        private int dividerHeight = 1;
        private int dividerMarginStart = 0;
        private int dividerMarginEnd = 0;
        @ColorInt
        private int iconColorFilter = Color.TRANSPARENT;
        // 条目的圆角
        private int popupMenuItemRadius;
        // 整个弹窗的背景
        private int radius;
        // 弹窗的背景色
        private int popupMenuBgColor;
        private boolean isDimAmountEnable = false;


        private int itemHeight;
        private int popupMenuAnimationStyle = R.style.XP_Animation_PopDownMenu_Right;
        private OnMenuItemClickListener itemClickListener;

        public Builder(Context context) {
            this.context = context;
            this.menuArrowHeight = XDisplayHelper.dp2px(context, 22f);
            this.menuWidth = XDisplayHelper.dp2px(context, 44f);
        }

        public Builder setMenuHeight(int menuHeight) {
            this.menuHeight = menuHeight;
            return this;
        }

        public Builder setMenuWidth(int menuWidth) {
            this.menuWidth = menuWidth;
            return this;
        }

        public Builder setDimAmountEnable(boolean dimAmountEnable) {
            isDimAmountEnable = dimAmountEnable;
            return this;
        }

        public Builder setMenuArrowHeight(int menuArrowHeight) {
            this.menuArrowHeight = menuArrowHeight;
            return this;
        }

        public Builder setMenuArrowWidth(int menuArrowWidth) {
            this.menuArrowWidth = menuArrowWidth;
            return this;
        }

        public Builder setItemClickListener(OnMenuItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public Builder setPopupMenuItemRadius(int popupMenuItemRadius) {
            this.popupMenuItemRadius = popupMenuItemRadius;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setPopupMenuAnimationStyle(int style) {
            this.popupMenuAnimationStyle = style;
            return this;
        }

        public Builder setIconColorFilter(int iconColorFilter) {
            this.iconColorFilter = iconColorFilter;
            return this;
        }

        public Builder setItemTextColor(int color) {
            this.itemTextColor = color;
            return this;
        }

        public Builder setMenuItemList(List<XPopupMenuItem> menuItemList) {
            this.menuItemList = menuItemList;
            return this;
        }

        public Builder setItemLayoutPressedColor(int itemLayoutPressedColor) {
            this.itemLayoutPressedColor = itemLayoutPressedColor;
            return this;
        }

        public Builder setPopupMenuBgColor(int popupMenuBgColor) {
            this.popupMenuBgColor = popupMenuBgColor;
            return this;
        }

        public Builder setItemHeight(int itemHeight) {
            this.itemHeight = itemHeight;
            return this;
        }

        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        public Builder setDividerHeight(int dividerHeight) {
            this.dividerHeight = dividerHeight;
            return this;
        }

        public Builder setDividerMarginStart(int margin) {
            this.dividerMarginStart = margin;
            return this;
        }

        public Builder setDividerMarginEnd(int margin) {
            this.dividerMarginEnd = margin;
            return this;
        }

        public Builder setOnMenuItemClickListener(OnMenuItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            return this;
        }

        public XPopupMenu create() {

            return new XPopupMenu(context, this);
        }
    }

    private void init(Context context) {
        if (builder.menuItemList != null) {
            mMenuItemList.clear();
            mMenuItemList.addAll(builder.menuItemList);
        }
        mListView = new RecyclerView(context);
        mListView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new PopListAdapter(context, mMenuItemList, builder.itemLayoutPressedColor,
                builder.iconColorFilter, builder.itemTextColor, builder.itemHeight,
                builder.dividerColor, builder.dividerHeight,
                builder.dividerMarginStart, builder.dividerMarginEnd, builder.popupMenuItemRadius);
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PopListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (builder.itemClickListener != null) {
                    builder.itemClickListener.onMenuItemClick(XPopupMenu.this, position, mMenuItemList.get(position).getTag());
                }
            }
        });
    }

    public void refreshMenuItemList(List<XPopupMenuItem> popupMenuItems) {
        if (popupMenuItems != null) {
            this.mMenuItemList.clear();
            this.mMenuItemList.addAll(popupMenuItems);
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }

    }

    private PopActions popActions;

    public void dismiss() {
        if (popActions != null) {
            popActions.dismiss();
        }
    }

    // 显示
    public void show(View anchor) {
        if (mMenuWidth == 0) {
            mMenuWidth = XDisplayHelper.getScreenWidth(mContext) * 2 / 5;
        }
        Log.d("XPopupMenu==>", "mMenuHeight:" + mMenuHeight);
        Log.d("XPopupMenu==>", "builder.itemHeight:" + builder.itemHeight);
        Log.d("XPopupMenu==>", "mMenuItemList-size:" + mMenuItemList.size());

        if (mMenuHeight <= 0) {
            int size = mMenuItemList.size();
            if (size <= 0) {
                size = 1;
            }
            Log.d("XPopupMenu==>", "size:" + size);
            if (builder.itemHeight <= 0) {
                mMenuHeight = XDisplayHelper.dp2px(mContext, 48f) * size;
                Log.d("XPopupMenu==>", "48dp:" + XDisplayHelper.px2dp(mContext, 48));
            } else {
                mMenuHeight = builder.itemHeight * size;
            }
        }
        Log.d("XPopupMenu==>", "mMenuHeight:" + mMenuHeight);
        Log.d("XPopupMenu==>", "mMenuWidth:" + mMenuWidth);
        popActions = new PopActions.Builder(mContext)
                .setOffsetY(XDisplayHelper.dp2px(mContext, 7f))
                .setRadius(builder.radius)
                .setContentWidth(mMenuWidth)
                .setContentHeight(mMenuHeight)
                .setContentView(mListView)
                .setArrowWidth(builder.menuArrowWidth)
                .setArrowHeight(builder.menuArrowHeight)
                .setAnimationStyle(builder.popupMenuAnimationStyle)
                .setSideMargin(XDisplayHelper.dp2px(mContext, 6f))
                .setBackgroundColor(builder.popupMenuBgColor)
                .build();
        popActions.show(anchor);
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(XPopupMenu popupMenu, int position, String tag);
    }

    private static class PopListAdapter extends RecyclerView.Adapter<PopListAdapter.PopMenuListViewHolder> {

        private List<XPopupMenuItem> list;
        private Context context;
        // item 点击颜色特效
        @ColorInt
        private int itemPressedColor;

        // 统一设置icon的颜色，优先级大于分别设置
        @ColorInt
        private int iconColorFilter;
        @ColorInt
        private int textColor;
        private int itemHeight;
        @ColorInt
        private int dividerColor;
        private int dividerHeight;
        private int dividerMarginStart;
        private int dividerMarginEnd;
        private int radius;


        public PopListAdapter(Context context, List<XPopupMenuItem> list,
                              @ColorInt int itemPressedColor, @ColorInt int iconColorFilter,
                              @ColorInt int textColor, @ColorInt int itemHeight,
                              @ColorInt int dividerColor, int dividerHeight,
                              int dividerMarginStart, int dividerMarginEnd, int radius) {
            this.list = list;
            this.context = context;
            this.itemPressedColor = itemPressedColor;
            this.iconColorFilter = iconColorFilter;
            this.textColor = textColor;
            this.itemHeight = itemHeight;
            this.dividerColor = dividerColor;
            this.dividerHeight = dividerHeight;
            this.dividerMarginStart = dividerMarginStart;
            this.dividerMarginEnd = dividerMarginEnd;
            this.radius = radius;
        }

        private OnItemClickListener mItemClickListener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mItemClickListener = listener;
        }

        @NonNull
        @Override
        public PopMenuListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View convertView = LayoutInflater.from(context).inflate(R.layout.xuikit_xpopupmenu_layout_item, parent, false);
            return new PopMenuListViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(@NonNull PopMenuListViewHolder holder, int position) {
            if (itemHeight > 0) {
                ViewGroup.LayoutParams layoutParams = holder.mRootView.getLayoutParams();
                layoutParams.height = itemHeight;
                holder.mRootView.setLayoutParams(layoutParams);
            }
            // 设置数据
            XPopupMenuItem menuItem = list.get(position);

            if (radius >= 0) {
                holder.mRootView.setRadius(radius);
            }

            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mTitle.setVisibility(View.VISIBLE);
            if (menuItem.getShowType() == XToolBarShowType.TEXT_ONLY) {
                holder.mIcon.setVisibility(View.GONE);
                holder.mTitle.setVisibility(View.VISIBLE);
            } else if (menuItem.getShowType() == XToolBarShowType.ICON_ONLY) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mTitle.setVisibility(View.GONE);
            }
            // tv设置
            if (menuItem.getShowType() == XToolBarShowType.ALL || menuItem.getShowType() == XToolBarShowType.TEXT_ONLY) {
                holder.mTitle.setText(menuItem.getText());
                if (textColor != 0) {
                    holder.mTitle.setTextColor(textColor);
                } else if (menuItem.getTextColor() != 0) {
                    holder.mTitle.setTextColor(menuItem.getTextColor());
                }
                if (menuItem.getTextSize() > 0) {
                    holder.mTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuItem.getTextSize());
                }
            }
            if (menuItem.getShowType() == XToolBarShowType.ALL || menuItem.getShowType() == XToolBarShowType.ICON_ONLY) {
                if (menuItem.getIconDrawable() != null) {
                    holder.mIcon.setImageDrawable(menuItem.getIconDrawable());
                } else if (menuItem.getIconResId() != 0) {
                    holder.mIcon.setImageResource(menuItem.getIconResId());
                }
                if (menuItem.getIconPadding() >= 0) {
                    holder.mIcon.setPadding(menuItem.getIconPadding(), menuItem.getIconPadding(),
                            menuItem.getIconPadding(), menuItem.getIconPadding());
                }
                if (menuItem.getIconMarginStart() >= 0) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mIcon.getLayoutParams();
                    layoutParams.leftMargin = menuItem.getIconMarginStart();
                    holder.mIcon.setLayoutParams(layoutParams);
                }
                if (menuItem.getIconMarginEnd() >= 0) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mIcon.getLayoutParams();
                    layoutParams.rightMargin = menuItem.getIconMarginEnd();
                    holder.mIcon.setLayoutParams(layoutParams);
                }
                if (iconColorFilter != 0) {
                    holder.mIcon.setColorFilter(iconColorFilter);
                } else if (menuItem.getIconColorFilter() != 0) {
                    holder.mIcon.setColorFilter(menuItem.getIconColorFilter());
                }
            }

            holder.mRootView.setRadius(menuItem.getRadius());
            if (menuItem.getBackgroundPressed() != null) {
                holder.mRootView.setPressedBackground(menuItem.getBackgroundPressed());
            } else if (itemPressedColor != 0) {
                holder.mRootView.setPressedBackground(new ColorDrawable(itemPressedColor));
            }
            if (menuItem.getBackground() != null) {
                holder.mRootView.setBackground(menuItem.getBackground());
            }

            holder.mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, position);
                    }
                }
            });

            if ((position == list.size() - 1) || dividerColor == 0 || dividerHeight <= 0) {
                holder.mDivider.setVisibility(View.INVISIBLE);
            } else {
                holder.mDivider.setVisibility(View.VISIBLE);
                holder.mDivider.setBackgroundColor(dividerColor);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.mDivider.getLayoutParams();
                if (layoutParams != null) {
                    layoutParams.height = dividerHeight;
                    layoutParams.setMarginStart(dividerMarginStart);
                    layoutParams.setMarginEnd(dividerMarginEnd);
                    holder.mDivider.setLayoutParams(layoutParams);
                }
            }

        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public static class PopMenuListViewHolder extends RecyclerView.ViewHolder {
            public XImageView mIcon;
            public XTextView mTitle;
            public XCardConstraintLayout mRootView;
            public View mDivider;

            public PopMenuListViewHolder(@NonNull View itemView) {
                super(itemView);
                mIcon = itemView.findViewById(R.id.x_popup_menu_item_image);
                mTitle = itemView.findViewById(R.id.x_popup_menu_item_text);
                mRootView = itemView.findViewById(R.id.x_popup_menu_item_root);
                mDivider = itemView.findViewById(R.id.x_popup_menu_item_divider);
            }
        }

        public interface OnItemClickListener {
            void onItemClick(View v, int position);
        }
    }

}
