package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class NetworkActivity extends AppCompatActivity {
    ImageButton backFromNetwork;
    TabLayout tabLayoutNetwork;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_network);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // setting window above toolbar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkBlue));

        // back to home page
        backFromNetwork = findViewById(R.id.backFromNetwork);
        backFromNetwork.setOnClickListener(v -> {
            finish();
        });

        // tab layout
        tabLayoutNetwork = findViewById(R.id.tabLayoutNetwork);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutNetworked, new NetworkedFragment1())
                .addToBackStack(null).commit();

        tabLayoutNetwork.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new NetworkedFragment1();
                        break;
                    case 1:
                        fragment = new NetworkedFragment2();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutNetworked, fragment)
                        .addToBackStack(null).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}