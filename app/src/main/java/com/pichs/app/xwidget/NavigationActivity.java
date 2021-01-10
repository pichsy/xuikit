package com.pichs.app.xwidget;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.pichs.xuikit.tabbar.BottomBarItem;
import com.pichs.xuikit.tabbar.BottomBarLayout;
import com.pichs.app.xwidget.fragment.TabFragment;
import com.pichs.common.widget.utils.XDisplayHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: 吴波
 * @CreateDate: 2021/1/8 16:00
 * @UpdateUser: 吴波
 * @UpdateDate: 2021/1/8 16:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NavigationActivity extends AppCompatActivity {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private BottomBarLayout mBottomBarLayout;

    private int[] mNormalIconIds = new int[]{
            R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher
    };

    private int[] mSelectedIconIds = new int[]{
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round,
            R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round
    };

    private String[] mTitles = new String[]{
            "首页",
            "消息",
            "发现",
            "我的"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mBottomBarLayout = findViewById(R.id.tablayout);

        for (int i = 0; i < mTitles.length; i++) {
            //创建item
            BottomBarItem item = createBottomBarItem(i);
            mBottomBarLayout.addItem(item);
            TabFragment homeFragment = new TabFragment();
            mFragmentList.add(homeFragment);
        }
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, final int currentPosition) {
                Log.i("MainActivity", "position: " + currentPosition);

                changeFragment(currentPosition);
            }
        });
    }


    private void changeFragment(int currentPosition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, mFragmentList.get(currentPosition));
        transaction.commit();
    }

    private BottomBarItem createBottomBarItem(int i) {
        return new BottomBarItem.Builder(this)
                .titleTextBold(true)
                .titleTextSize(9)
                .iconHeight(XDisplayHelper.dp2px(this, 24))
                .iconWidth(XDisplayHelper.dp2px(this, 24))
                .titleNormalColor(R.color.tab_normal_color)
                .titleSelectedColor(R.color.tab_selected_color)

                //还有很多属性，详情请查看Builder里面的方法
                //There are still many properties, please see the methods in the Builder for details.
                .create(mNormalIconIds[i], mSelectedIconIds[i], mTitles[i]);
    }
}
