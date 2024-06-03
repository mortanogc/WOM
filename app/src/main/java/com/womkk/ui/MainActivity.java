package com.womkk.ui;

import static android.net.http.SslCertificate.restoreState;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.FirebaseApp;
import com.womkk.R;
import com.womkk.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.yandex.mapkit.MapKitFactory;
import com.womkk.ui.about.AppInfoFragment;
import com.womkk.ui.about.MuseumInfoFragment;
import com.womkk.ui.account.AccountFragment;
import com.womkk.ui.exhibits.ExhibitsListFragment;
import com.womkk.ui.map.MuseumMapFragment;
import com.womkk.ui.news.NewsListFragment;
import com.womkk.ui.reviews.ReviewListFragment;
import com.womkk.ui.settings.SettingsFragment;
import com.womkk.ui.tickets.TicketsFragment;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences preferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация Firebase
        FirebaseApp.initializeApp(this);

        preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

        // Применение настроек языка и темы
        applySavedSettings();

        MapKitFactory.initialize(this);

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = binding.bottomNavView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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

        if (savedInstanceState == null) {
            navController.navigate(R.id.nav_news); // Устанавливаем стартовый фрагмент как новости
        }
    }

    private void applySavedSettings() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);

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
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

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
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, R.string.permissions_not_granted, Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveState();
    }

    private void saveState() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (currentFragment != null) {
            String fragmentTag = currentFragment.getTag();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("last_fragment", fragmentTag);
            editor.apply();
        }
    }
}
