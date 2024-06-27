package com.lucky7.parky.features.auth.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.lucky7.parky.MyApp;
import com.lucky7.parky.R;
import com.lucky7.parky.core.adapter.SlideAdapter;
import com.lucky7.parky.core.di.AppComponent;
import com.lucky7.parky.core.entity.SlideItem;
import com.lucky7.parky.features.auth.domain.repository.AuthRepository;
import com.lucky7.parky.features.park.presentation.HistoryActivity;
import com.lucky7.parky.features.park.presentation.QRCodeScannerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    AuthRepository authRepository;
    public static final String EXTRA_ADMIN = "extra_admin";
    private ImageView ivHistory, ivUser, ivAdmin, ivScanCode, ivLogout;
    ViewPager2 viewPager2;
    private Handler slideHandler;

    private void initView() {
        ivHistory = findViewById(R.id.iv_history);
        ivUser = findViewById(R.id.iv_user);
        ivAdmin = findViewById(R.id.iv_admin);
        ivScanCode = findViewById(R.id.iv_scan_code_admin);
        viewPager2 = findViewById(R.id.viewPager);
        ivLogout = findViewById(R.id.iv_logout_admin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp myApp = (MyApp) getApplicationContext();
        AppComponent appComponent = myApp.getAppComponent();
        appComponent.inject(this);

        setContentView(R.layout.activity_admin_home);

        initView();

        slideHandler = new Handler();

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
                float r = 1 - Math.abs(position);
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

        ivHistory.setOnClickListener(this);
        ivUser.setOnClickListener(this);
        ivAdmin.setOnClickListener(this);
        ivScanCode.setOnClickListener(this);
        ivLogout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (v.getId() == R.id.iv_user) {
            startActivity(new Intent(this, UserListActivity.class));
        } else if (v.getId() == R.id.iv_scan_code_admin) {
            startActivity(new Intent(this, QRCodeScannerActivity.class));
        } else if (v.getId() == R.id.iv_admin) {
            startActivity(new Intent(this, AddUserActivity.class));
        } else if (v.getId() == R.id.iv_logout_admin) {
            showLogoutDialog();
        }
    }

    private void showLogoutDialog() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirm_logout);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.findViewById(R.id.tv_confirm_logout).setOnClickListener(v -> {
            logout();
            dialog.dismiss();
        });

        dialog.findViewById(R.id.tv_cancel_logout).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void logout(){
        authRepository.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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