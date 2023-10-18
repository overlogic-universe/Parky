package com.lucky7.parky.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.lucky7.parky.R;
import com.lucky7.parky.adapter.SlideAdapter;
import com.lucky7.parky.model.SlideItem;

import java.util.ArrayList;
import java.util.List;

public class HomeAdminActivity extends AppCompatActivity implements View.OnClickListener  {
    private ImageView ivHistory;
    private ImageView ivUser;
    ViewPager2 viewPager2;
    private final Handler slideHandler = new Handler();

    private void initView(){
        ivHistory = findViewById(R.id.iv_history);
        ivUser = findViewById(R.id.iv_user);
        viewPager2 = findViewById(R.id.viewPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        initView();

        List<SlideItem> slideItems = new ArrayList<>();

        slideItems.add(new SlideItem(R.drawable.carousel_1));
        slideItems.add(new SlideItem(R.drawable.carousel_2));
        slideItems.add(new SlideItem(R.drawable.carousel_3));
        slideItems.add(new SlideItem(R.drawable.carousel_4));
        slideItems.add(new SlideItem(R.drawable.carousel_5));

        viewPager2.setAdapter(new SlideAdapter(slideItems, viewPager2));

        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setCurrentItem(2, false);
        viewPager2.setOffscreenPageLimit(5);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransform = new CompositePageTransformer();
        compositePageTransform.addTransformer(new MarginPageTransformer(30));
        compositePageTransform.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
               float r = 1- Math.abs(position);
               page.setScaleY(0.85f + r * 0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransform);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                slideHandler.removeCallbacks(sliderRunnable);
                slideHandler.postDelayed(sliderRunnable, 5000);
            }
        });

        //onclick listener
        ivHistory.setOnClickListener(this);
        ivUser.setOnClickListener(this);

    }

    //onclick listener
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (v.getId() == R.id.iv_user) {
            startActivity(new Intent(this, UserListActivity.class));
        }
    }



    private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        slideHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        slideHandler.postDelayed(sliderRunnable, 5000);
    }
}