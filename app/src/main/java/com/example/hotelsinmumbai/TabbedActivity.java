package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Objects;

public class TabbedActivity extends AppCompatActivity {
    ImageButton backFromViewPager;
    TabLayout tabLoyout2;
    ViewPager2 view_pager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabbed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // setting window above toolbar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkBlue));

        //back button to main activity
        backFromViewPager = findViewById(R.id.backFromViewPager);
        backFromViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabbedActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // tab names
        ArrayList<String> tabsArray = new ArrayList<>();
        tabsArray.add("Hotel Food Form");
        tabsArray.add("List of Available Food");

        // Tab items
        ArrayList<Integer> iconsArray = new ArrayList<>();
        iconsArray.add(R.drawable.dynamic_form);
        iconsArray.add(R.drawable.list_details);

        MyViewPagerAdapter myAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        tabLoyout2 = findViewById(R.id.tabLoyout2);
        view_pager = findViewById(R.id.view_pager);
        view_pager.setAdapter(myAdapter);

        // pass tablayout and viewPager in tabLayoutMediator
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLoyout2, view_pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setIcon(iconsArray.get(position));
                tab.setText(tabsArray.get(position));
            }
        });
        tabLayoutMediator.attach();

        // Assuming the first tab is selected initially
        TabLayout.Tab initialTab = tabLoyout2.getTabAt(0);
       // Set the color filter for the initial tab's icon
        if (initialTab != null && initialTab.getIcon() != null) {
            int tabIconColor = ContextCompat.getColor(TabbedActivity.this, R.color.white);
            initialTab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        }
        // set icon color for all tabs
        tabLoyout2.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(TabbedActivity.this, R.color.white);
                Objects.requireNonNull(tab.getIcon()).setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(TabbedActivity.this, R.color.black);
                Objects.requireNonNull(tab.getIcon()).setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}