package com.womkk.ui.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.womkk.R;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {
    private TextView nameTextView, emailTextView;
    private EditText firstNameEditText, lastNameEditText, newPasswordEditText, currentPasswordEditText;
    private EditText newEmailEditText, currentPasswordForEmailEditText;
    private Button changePasswordButton, updateNameButton, changeEmailButton, logoutButton;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_account);

        nameTextView = view.findViewById(R.id.nameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        currentPasswordEditText = view.findViewById(R.id.currentPasswordEditText);
        newEmailEditText = view.findViewById(R.id.newEmailEditText);
        currentPasswordForEmailEditText = view.findViewById(R.id.currentPasswordForEmailEditText);
        changePasswordButton = view.findViewById(R.id.changePasswordButton);
        updateNameButton = view.findViewById(R.id.updateNameButton);
        changeEmailButton = view.findViewById(R.id.changeEmailButton);
        logoutButton = view.findViewById(R.id.logoutButton);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        checkUserAuthentication();

        changePasswordButton.setOnClickListener(v -> changePassword());
        updateNameButton.setOnClickListener(v -> updateName());
        changeEmailButton.setOnClickListener(v -> changeEmail());
        logoutButton.setOnClickListener(v -> logoutUser());

        return view;
    }

    private void checkUserAuthentication() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            loadUserData(user);
        } else {
            navigateToLoginFragment();
        }
    }

    private void loadUserData(FirebaseUser user) {
        emailTextView.setText(user.getEmail());
        db.collection("users").document(user.getUid()).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String firstName = documentSnapshot.getString("firstName");
                        String lastName = documentSnapshot.getString("lastName");
                        nameTextView.setText(firstName + " " + lastName);
                        firstNameEditText.setText(firstName);
                        lastNameEditText.setText(lastName);
                    } else {
                        Toast.makeText(getContext(), R.string.user_data_not_found, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), getString(R.string.error_loading_user_data) + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateName() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            firstNameEditText.setError(getString(R.string.first_name_required));
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            lastNameEditText.setError(getString(R.string.last_name_required));
            return;
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("firstName", firstName);
            updates.put("lastName", lastName);

            db.collection("users").document(user.getUid()).update(updates)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), R.string.name_updated_successfully, Toast.LENGTH_SHORT).show();
                        nameTextView.setText(firstName + " " + lastName);
                    })
                    .addOnFailureListener(e -> Toast.makeText(getContext(), getString(R.string.error_updating_name) + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void changePassword() {
        String newPassword = newPasswordEditText.getText().toString().trim();
        String currentPassword = currentPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            newPasswordEditText.setError(getString(R.string.password_required));
            return;
        }

        if (TextUtils.isEmpty(currentPassword)) {
            currentPasswordEditText.setError(getString(R.string.password_required));
            return;
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(getContext(), R.string.password_changed_successfully, Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), getString(R.string.error_changing_password) + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), getString(R.string.reauthentication_failed) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void changeEmail() {
        String newEmail = newEmailEditText.getText().toString().trim();
        String currentPassword = currentPasswordForEmailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(newEmail)) {
            newEmailEditText.setError(getString(R.string.email_required));
            return;
        }

        if (TextUtils.isEmpty(currentPassword)) {
            currentPasswordForEmailEditText.setError(getString(R.string.password_required));
            return;
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.updateEmail(newEmail)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            db.collection("users").document(user.getUid())
                                                    .update("email", newEmail)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Toast.makeText(getContext(), R.string.email_updated_successfully, Toast.LENGTH_SHORT).show();
                                                        emailTextView.setText(newEmail);
                                                    })
                                                    .addOnFailureListener(e -> Toast.makeText(getContext(), getString(R.string.error_updating_email) + e.getMessage(), Toast.LENGTH_SHORT).show());
                                        } else {
                                            Toast.makeText(getContext(), getString(R.string.error_changing_email) + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(getContext(), getString(R.string.reauthentication_failed) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void logoutUser() {
        auth.signOut();
        Toast.makeText(getContext(), R.string.logged_out, Toast.LENGTH_SHORT).show();
        navigateToLoginFragment();
    }

    private void navigateToLoginFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.loginFragment);
    }
}
