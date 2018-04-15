package com.example.hp.test25.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.hp.test25.R;
import com.example.hp.test25.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView mBottomNav;
    private ViewPager mPager;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
    }

    private void initView(){
        mBottomNav = (BottomNavigationView)findViewById(R.id.bottom_nav);
        mPager = (ViewPager)findViewById(R.id.viewpager);

        //BottomNavigationViewHelper.disableShiftMode(mBottomNav);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item ){
                switch (item.getItemId()){
                    case R.id.item_statistics:
                        mPager.setCurrentItem(0);
                        break;
                    case R.id.item_budget:
                        mPager.setCurrentItem(1);
                        break;
                    case R.id.item_change:
                        mPager.setCurrentItem(2);
                        break;
                    case R.id.item_shares:
                        mPager.setCurrentItem(3);
                        break;
                    case R.id.item_me:
                        mPager.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(menuItem != null){
                    menuItem.setChecked(false);
                }else{
                    mBottomNav.getMenu().getItem(0).setChecked(false);
                }
                menuItem = mBottomNav.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        //禁止ViewPager滑动
//        mPager.setOnTouchListener(new ViewPager.OnTouchListener(){
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(mPager);

    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(BaseFragment.newInstance("统计"));
        adapter.addFragment(BaseFragment.newInstance("预算"));
        adapter.addFragment(BaseFragment.newInstance("收支"));
        //adapter.addFragment(BaseFragment.newInstance("股票"));
        adapter.addFragment(new ShareFragment());
        adapter.addFragment(BaseFragment.newInstance("我"));

        mPager.setAdapter(adapter);

    }
}