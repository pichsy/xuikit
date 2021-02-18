# xuikit
最喜欢的自定义控件
可结合xskinloader 扩展换肤

### 引入控件
最新版本:  [![](https://jitpack.io/v/pichsy/xuikit.svg)](https://jitpack.io/#pichsy/xuikit)

        
       implementation 'com.github.pichsy:xuikit:1.5'
       

## 用法

    
      
1. 头部导航
    XToolbarLayout
    
       
       
        <com.pichs.xuikit.toolbar.XToolBarLayout
            android:id="@+id/xtoolbars"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#146CF8"
            app:xp_toolbar_backIcon_colorFilter="@color/white"
            app:xp_toolbar_fitSystemStatusBar="true"
            app:xp_toolbar_menu_horizontalSpacing="8dp"
            app:xp_toolbar_menu_maxInFrame="1"
            app:xp_toolbar_menu_optionIcon="@drawable/xuikit_icon_plus_black"
            app:xp_toolbar_menu_optionIcon_colorFilter="@color/white"
            app:xp_toolbar_menu_optionIcon_marginEnd="8dp"
            app:xp_toolbar_popupMenuItem_textColor="@color/white"
            app:xp_toolbar_title="首页"
            app:xp_toolbar_title_layoutGravity="left"
            app:xp_toolbar_title_marginEnd="166dp"
            app:xp_toolbar_title_marginStart="16dp"
            app:xp_toolbar_title_textColor="@color/white" />

   
2. 底部导航
    BottomBarLayout <br>
    底部导航item<br>
    BottomBarItem<br>
- 布局


      
      <?xml version="1.0" encoding="utf-8"?>
      <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
          android:layout_width="match_parent"
          xmlns:app="http://schemas.android.com/apk/res-auto"
          android:background="#f1f1f1"
          android:layout_height="match_parent"
          android:orientation="vertical">
      
      
          <FrameLayout
              android:id="@+id/container"
              android:layout_width="match_parent"
              android:layout_height="0dp"
              android:layout_weight="1" />
      
      
          <com.pichs.xuikit.tabbar.BottomBarLayout
              android:id="@+id/tablayout"
              android:background="#ffffff"
              app:xp_topDividerColor="@color/xp_config_color_red"
              app:xp_topDividerHeight="2dp"
              android:layout_width="match_parent"
              android:orientation="horizontal"
              android:gravity="center"
              android:layout_gravity="center"
              android:layout_height="48dp" />
      
      </LinearLayout>
- 代码


      
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
              return new BottomBarItem(this)
                      .setTextStyle(Typeface.BOLD)
                      .setTextSize(9)
                      .setIconWidth(XDisplayHelper.dp2px(this, 22))
                      .setIconHeight(XDisplayHelper.dp2px(this, 22))
                      .setTextIconSpacing(XDisplayHelper.dp2px(this, 3))
                      .setTextNormalColor(ContextCompat.getColor(this,R.color.tab_normal_color))
                      .setTextSelectedColor(ContextCompat.getColor(this,R.color.tab_selected_color))
                      .setIconSelectedDrawable(ContextCompat.getDrawable(this, mSelectedIconIds[i]))
                      .setIconNormalDrawable(ContextCompat.getDrawable(this, mNormalIconIds[i]))
                      .setText(mTitles[i])
                      .updateTab();
          }
      }
      
      
      

    
4. 弧形头部，或者衔接布局
- HeadArcView
    
    
    
        <com.pichs.xuikit.arcview.HeadArcView
               android:layout_width="match_parent"
               android:layout_height="50dp"
               android:layout_marginTop="10dp"
               android:background="#FB9292"
               app:xp_arc_arcHeight="30dp"
               app:xp_arc_endColor="#FF00E1"
               app:xp_arc_gradientOrientation="vertical"
               app:xp_arc_reverse="true"
               app:xp_arc_startColor="#1167F2"
               app:xp_arc_type="inside" />
           
       
5.  常用cells布局

- CommonItemView
   
        
        
       <com.pichs.xuikit.cells.CommonItemView
             android:layout_width="match_parent"
             android:layout_height="48dp"
             android:background="#f09"
             app:common_arrow_colorFilter="#fff"
             app:common_arrow_visible="true"
             app:common_icon="#00f"
             app:common_sub_text="吃素的"
             app:common_sub_text_textColor="#fff"
             app:common_sub_text_textStyle="bold"
             app:common_title_text="用户收藏"
             app:common_title_textColor="#fff"
             app:common_title_textStyle="bold" />
   
   
- CommonSection
  
  
  
     <com.pichs.xuikit.cells.CommonSection
            android:id="@+id/commonSection"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:background="#FFFFFF"
            app:xp_section_moreIcon="@drawable/xuikit_common_item_view_arrow_right_gray"
            app:xp_section_moreIconHeight="24dp"
            app:xp_section_moreIconWidth="10dp"
            app:xp_section_moreText="更多"
            app:xp_section_moreTextColor="#7B07D5"
            app:xp_section_moreTextSize="16sp"
            app:xp_section_titleIconBgEndColor="#9808EB"
            app:xp_section_titleIconBgStartColor="#42A5F5"
            app:xp_section_titleIconHeight="20dp"
            app:xp_section_titleIconRadius="4dp"
            app:xp_section_titleIconWidth="8dp"
            app:xp_section_titleText="资讯"
            app:xp_section_titleTextColor="#0138FF"
            app:xp_section_titleTextSize="20sp" />



## 升级内容

## 升级日志
