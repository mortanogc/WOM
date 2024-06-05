package com.womkk.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.womkk.databinding.ActivityMainBinding;
import com.womkk.ui.navigation.NavigationHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavigationHelper navigationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set the Toolbar as the ActionBar
        setSupportActionBar(binding.toolbar);

        navigationHelper = new NavigationHelper(this, binding);
        navigationHelper.setupNavigation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        navigationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigationHelper.saveState();
    }
}
