package com.womkk.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.womkk.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDelegate;
import java.io.File;
import java.util.Locale;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.content.Context;
import android.content.res.Configuration;

public class SettingsFragment extends Fragment {

    private Spinner languageSpinner;
    private Spinner themeSpinner;
    private Button saveSettingsButton;
    private Button requestPermissionsButton;
    private TextView cacheSizeText;
    private Button clearCacheButton;

    private SharedPreferences preferences;
    private static final String PREFERENCES_NAME = "settings";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        languageSpinner = view.findViewById(R.id.language_spinner);
        themeSpinner = view.findViewById(R.id.theme_spinner);
        saveSettingsButton = view.findViewById(R.id.save_settings_button);
        requestPermissionsButton = view.findViewById(R.id.request_permissions_button);
        cacheSizeText = view.findViewById(R.id.cache_size_text);
        clearCacheButton = view.findViewById(R.id.clear_cache_button);

        preferences = getContext().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        setupLanguageSpinner();
        setupThemeSpinner();
        loadSettings();

        saveSettingsButton.setOnClickListener(v -> saveSettings());

        requestPermissionsButton.setOnClickListener(v -> requestPermissions());

        clearCacheButton.setOnClickListener(v -> clearCache());

        displayCacheSize();

        return view;
    }

    private void setupLanguageSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
    }

    private void setupThemeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.theme_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeSpinner.setAdapter(adapter);
    }

    private void loadSettings() {
        int languagePosition = preferences.getInt("language", 0);
        int themePosition = preferences.getInt("theme", 0);

        languageSpinner.setSelection(languagePosition);
        themeSpinner.setSelection(themePosition);
    }

    private void saveSettings() {
        int newLanguagePosition = languageSpinner.getSelectedItemPosition();
        int newThemePosition = themeSpinner.getSelectedItemPosition();

        int currentLanguagePosition = preferences.getInt("language", 0);
        int currentThemePosition = preferences.getInt("theme", 0);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("language", newLanguagePosition);
        editor.putInt("theme", newThemePosition);
        editor.apply();

        if (newLanguagePosition != currentLanguagePosition || newThemePosition != currentThemePosition) {
            applySettings();
            Toast.makeText(getContext(), R.string.settings_applied, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.settings_saved, Toast.LENGTH_SHORT).show();
        }
    }

    private void applySettings() {
        // Apply language settings
        int languagePosition = languageSpinner.getSelectedItemPosition();
        String languageCode = "en"; // Default to English
        if (languagePosition == 1) {
            languageCode = "en";
        } else if (languagePosition == 2) {
            languageCode = "ru";
        }

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = getContext().getResources().getConfiguration();
        config.setLocale(locale);
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());

        // Apply theme settings
        int themePosition = themeSpinner.getSelectedItemPosition();
        int themeMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        if (themePosition == 1) {
            themeMode = AppCompatDelegate.MODE_NIGHT_NO;
        } else if (themePosition == 2) {
            themeMode = AppCompatDelegate.MODE_NIGHT_YES;
        }
        AppCompatDelegate.setDefaultNightMode(themeMode);

        // Restart activity to apply changes
        new Handler(Looper.getMainLooper()).post(() -> {
            if (getActivity() != null) {
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                startActivity(intent);
            }
        });
    }

    private void requestPermissions() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                Toast.makeText(getContext(), R.string.permissions_granted, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), R.string.permissions_granted, Toast.LENGTH_SHORT).show();  // Вы можете изменить это сообщение на более подходящее
            }
        }
    }

    private void clearCache() {
        File cacheDir = getContext().getCacheDir();
        deleteDir(cacheDir);
        displayCacheSize();
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void displayCacheSize() {
        long cacheSize = getDirSize(getContext().getCacheDir());
        String cacheSizeStr = formatSize(cacheSize);
        cacheSizeText.setText(getString(R.string.cache_size) + " " + cacheSizeStr);
    }

    private long getDirSize(File dir) {
        if (dir == null) return 0;
        if (!dir.isDirectory()) return dir.length();
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                size += getDirSize(file);
            } else {
                size += file.length();
            }
        }
        return size;
    }

    private String formatSize(long size) {
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int unitIndex = 0;
        double sizeDouble = size;
        while (sizeDouble >= 1024 && unitIndex < units.length - 1) {
            sizeDouble /= 1024;
            unitIndex++;
        }
        return String.format(Locale.getDefault(), "%.2f %s", sizeDouble, units[unitIndex]);
    }
}
