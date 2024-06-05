package com.womkk.ui.navigation;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.womkk.R;
import com.womkk.databinding.ActivityMainBinding;
import com.yandex.mapkit.MapKitFactory;

import java.util.Locale;

public class NavigationHelper {

    private static final String PREFERENCES_NAME = "app_preferences";
    private static final int REQUEST_CODE_PERMISSIONS = 101;
    private static final String[] REQUIRED_PERMISSIONS = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };
    private static final String MAPKIT_API_KEY = "d0c42135-50be-4de5-8cff-1429297cad25";

    static {
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }

    private final AppCompatActivity activity;
    private final ActivityMainBinding binding;
    private final SharedPreferences preferences;

    public NavigationHelper(AppCompatActivity activity, ActivityMainBinding binding) {
        this.activity = activity;
        this.binding = binding;
        this.preferences = activity.getSharedPreferences(PREFERENCES_NAME, AppCompatActivity.MODE_PRIVATE);
        FirebaseApp.initializeApp(activity);
        applySavedSettings();
        MapKitFactory.initialize(activity);
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    public void setupNavigation() {
        BottomNavigationView bottomNavigationView = binding.bottomNavView;
        NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_news, R.id.nav_map, R.id.nav_about_museum, R.id.nav_about_app, R.id.nav_exhibits, R.id.reviewListFragment, R.id.nav_settings, R.id.nav_account, R.id.nav_tickets)
                .build();
        NavigationUI.setupActionBarWithNavController(activity, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_news:
                    navController.navigate(R.id.nav_news);
                    break;
                case R.id.nav_tickets:
                    navController.navigate(R.id.nav_tickets);
                    break;
                case R.id.nav_map:
                    navController.navigate(R.id.nav_map);
                    break;
                case R.id.nav_exhibits:
                    navController.navigate(R.id.nav_exhibits);
                    break;
                case R.id.nav_old_menu:
                    binding.drawerLayout.openDrawer(binding.navView);
                    break;
                default:
                    return false;
            }
            return true;
        });

        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(item -> {
            item.setChecked(true);
            binding.drawerLayout.closeDrawers();

            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.nav_account:
                    navController.navigate(R.id.nav_account);
                    break;
                case R.id.nav_settings:
                    navController.navigate(R.id.nav_settings);
                    break;
                case R.id.nav_about_museum:
                    navController.navigate(R.id.nav_about_museum);
                    break;
                case R.id.nav_about_app:
                    navController.navigate(R.id.nav_about_app);
                    break;
                case R.id.nav_reviews:
                    navController.navigate(R.id.reviewListFragment);
                    break;
                default:
                    return false;
            }
            return true;
        });

        if (activity.getIntent().getExtras() == null) {
            navController.navigate(R.id.nav_news); // Устанавливаем стартовый фрагмент как новости
        }
    }

    private void applySavedSettings() {
        // Применение языка
        int languagePosition = preferences.getInt("language", 0);
        String languageCode = "ru"; // Default to Russian
        if (languagePosition == 1) {
            languageCode = "ru";
        } else if (languagePosition == 2) {
            languageCode = "en";
        }

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());

        // Применение темы
        int themePosition = preferences.getInt("theme", 0);
        int themeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        if (themePosition == 1) {
            themeMode = AppCompatDelegate.MODE_NIGHT_NO;
        } else if (themePosition == 2) {
            themeMode = AppCompatDelegate.MODE_NIGHT_YES;
        }
        AppCompatDelegate.setDefaultNightMode(themeMode);
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(activity, R.string.permissions_not_granted, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveState() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (currentFragment != null) {
            String fragmentTag = currentFragment.getTag();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("last_fragment", fragmentTag);
            editor.apply();
        }
    }
}
