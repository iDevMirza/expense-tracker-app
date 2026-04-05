package com.example.expensetrackerapp;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BottomNavigationActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private int currentSelectedItemId = R.id.home_menu_id;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bottom_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);

        if (savedInstanceState == null) {
            currentSelectedItemId = R.id.home_menu_id;
            loadFragment(new HomeFragment());
            bottomNavigationView.setSelectedItemId(R.id.home_menu_id);
        } else {
            currentSelectedItemId = bottomNavigationView.getSelectedItemId();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Prevent reloading same fragment
        if (itemId == currentSelectedItemId) {
            return true;
        }

        Fragment selectedFragment = null;

        if(itemId == R.id.home_menu_id){
            selectedFragment = new HomeFragment();
        } else if(itemId == R.id.add_menu_id){
            selectedFragment = new AddFragment();
        } else if(itemId == R.id.history_menu_id){
            selectedFragment = new HistoryFragment();
        } else if(itemId == R.id.insights_menu_id){
            selectedFragment = new InsightsFragment();
        }

        if (selectedFragment != null) {
            currentSelectedItemId = itemId;
            loadFragment(selectedFragment);
            return true;
        }

        return false;
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_id, fragment);
        fragmentTransaction.commit();
    }
}