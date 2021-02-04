package com.pichs.app.xwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pichs.common.widget.cardview.XCardButton;
import com.pichs.common.widget.utils.XTypefaceHelper;
import com.pichs.common.widget.view.XButton;
import com.pichs.xdialog.action.PopActions;
import com.pichs.xuikit.toolbar.OnXToolBarBackClickListener;
import com.pichs.xuikit.toolbar.OnXToolBarMenuClickListener;
import com.pichs.xuikit.toolbar.XToolBarLayout;
import com.pichs.xuikit.toolbar.XToolBarMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.tv1);
        XCardButton btn = findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
            }
        });
        XButton normalBtn = findViewById(R.id.normalBtn);

        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
//                XTypefaceHelper.clearObserver();

//                XTypefaceHelper.setGlobalTypeface(getApplicationContext(), XTypefaceHelper.TYPEFACE_BOLD);
                XTypefaceHelper.resetTypeface(MainActivity.this);
//                XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
            }
        });
        XToolBarLayout toolBarLayout = findViewById(R.id.xtoolbars);

        List<XToolBarMenuItem> list = new ArrayList<>();
        list.add(new XToolBarMenuItem("你好", R.mipmap.ic_launcher_round, "_me_good2"));
        list.add(new XToolBarMenuItem("我好", R.mipmap.ic_launcher_round, "_me_good3"));
        list.add(new XToolBarMenuItem("他好", R.mipmap.ic_launcher_round, "_me_good4"));
        list.add(new XToolBarMenuItem("她好", R.mipmap.ic_launcher_round, "_me_good5"));
        list.add(new XToolBarMenuItem("走好", R.mipmap.ic_launcher_round, "_me_good6"));
        list.add(new XToolBarMenuItem("打本", R.mipmap.ic_launcher_round, "_me_good7"));
        list.add(new XToolBarMenuItem("特斯", R.mipmap.ic_launcher_round, "_me_good8"));
        toolBarLayout.setOnBackClickListener(new OnXToolBarBackClickListener() {
            @Override
            public void onBackClick(View v) {
                Toast.makeText(MainActivity.this, "返回", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCloseClick(View v) {
                Toast.makeText(MainActivity.this, "关闭", Toast.LENGTH_SHORT).show();
            }
        });

        toolBarLayout.setMenuList(list);
        toolBarLayout.setOnMenuClickListener(new OnXToolBarMenuClickListener() {
            @Override
            public void onItemClick(String tag, View v, int position) {
                Toast.makeText(MainActivity.this, tag, Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.showMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new PopActions(v.getContext(),300,800)
//                        .setDimAmountEnable(false)
//                        .addView(new TextView(MainActivity.this))
//                        .setRadius(20)
//                        .setAnimationStyle(R.style.XP_Animation_PopDownMenu_Right)
//                        .show(findViewById(R.id.showMenu));
            }
        });

        findViewById(R.id.tv3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NavigationActivity.class));
            }
        });
    }
}