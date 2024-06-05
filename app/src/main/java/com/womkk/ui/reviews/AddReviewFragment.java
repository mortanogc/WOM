package com.womkk.ui.reviews;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.womkk.R;
import com.womkk.model.Review;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddReviewFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private static final int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE = 2;

    private EditText editTextReviewTitle;
    private EditText editTextReview;
    private RatingBar ratingBar;
    private Button buttonChoosePhoto;
    private Button buttonSubmitReview;
    private LinearLayout imagesContainer;
    private CheckBox checkBoxAnonymous;
    private TextView textViewNameValue;

    private ArrayList<Bitmap> selectedImages = new ArrayList<>();
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private String reviewId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_review, container, false);

        editTextReviewTitle = view.findViewById(R.id.edit_text_review_title);
        editTextReview = view.findViewById(R.id.edit_text_review);
        ratingBar = view.findViewById(R.id.rating_bar);
        buttonChoosePhoto = view.findViewById(R.id.button_choose_photo);
        buttonSubmitReview = view.findViewById(R.id.button_submit_review);
        imagesContainer = view.findViewById(R.id.images_container);
        checkBoxAnonymous = view.findViewById(R.id.checkBox_anonymous);
        textViewNameValue = view.findViewById(R.id.text_view_name_value);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        buttonChoosePhoto.setOnClickListener(v -> chooseImageFromGallery());
        buttonSubmitReview.setOnClickListener(v -> submitReview());

        loadUserName();
        loadReviewDataIfNeeded();

        return view;
    }

    private void loadUserName() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            DocumentReference userRef = db.collection("users").document(user.getUid());
            userRef.get().addOnSuccessListener(documentSnapshot -> {
                if (documentSnapshot.exists()) {
                    String firstName = documentSnapshot.getString("firstName");
                    textViewNameValue.setText(firstName);
                } else {
                    textViewNameValue.setText(R.string.anonymous);
                }
            }).addOnFailureListener(e -> {
                textViewNameValue.setText(R.string.anonymous);
            });
        } else {
            textViewNameValue.setText(R.string.anonymous);
        }
    }

    private void loadReviewDataIfNeeded() {
        Bundle args = getArguments();
        if (args != null) {
            reviewId = args.getString("reviewId");
            if (reviewId != null) {
                db.collection("reviews").document(reviewId).get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                Review review = documentSnapshot.toObject(Review.class);
                                if (review != null) {
                                    editTextReviewTitle.setText(review.getTitle());
                                    editTextReview.setText(review.getReviewText());
                                    ratingBar.setRating(review.getRating());
                                    if (review.getPhotos() != null) {
                                        for (String photo : review.getPhotos()) {
                                            Bitmap bitmap = Review.base64ToBitmap(photo);
                                            selectedImages.add(bitmap);
                                            ImageView imageView = new ImageView(requireActivity());
                                            imageView.setImageBitmap(bitmap);
                                            imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                                            imageView.setAdjustViewBounds(true);
                                            imageView.setPadding(10, 10, 10, 10);
                                            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                            imagesContainer.addView(imageView);
                                        }
                                    }
                                }
                            }
                        });
            }
        }
    }

    private void chooseImageFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        } else {
            if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImageFromGallery();
            } else {
                Toast.makeText(requireActivity(), "Permission to access gallery is required to choose an image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == requireActivity().RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream imageStream = requireActivity().getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImages.add(selectedImage);
                ImageView imageView = new ImageView(requireActivity());
                imageView.setImageBitmap(selectedImage);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
                imageView.setAdjustViewBounds(true);
                imageView.setPadding(10, 10, 10, 10);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imagesContainer.addView(imageView);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(requireActivity(), "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void submitReview() {
        String reviewTitle = editTextReviewTitle.getText().toString();
        String reviewText = editTextReview.getText().toString();
        float rating = ratingBar.getRating();
        boolean isAnonymous = checkBoxAnonymous.isChecked();

        if (TextUtils.isEmpty(reviewTitle) || TextUtils.isEmpty(reviewText) || rating == 0) {
            Toast.makeText(requireActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(requireActivity(), "You need to be logged in to submit a review", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = user.getUid();
        String authorName = isAnonymous ? getString(R.string.anonymous) : textViewNameValue.getText().toString();

        ArrayList<String> photoStrings = new ArrayList<>();
        for (Bitmap bitmap : selectedImages) {
            photoStrings.add(Review.bitmapToBase64(bitmap));
        }

        Review review = new Review(reviewId, userId, authorName, rating, reviewTitle, reviewText, photoStrings, false);

        DocumentReference reviewRef;
        if (reviewId == null) {
            reviewRef = db.collection("reviews").document();
            review.setReviewId(reviewRef.getId());
        } else {
            reviewRef = db.collection("reviews").document(reviewId);
        }

        reviewRef.set(review)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireActivity(), R.string.review_submitted_for_approval, Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_addReviewFragment_to_reviewListFragment);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireActivity(), getString(R.string.error_submitting_review) + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
