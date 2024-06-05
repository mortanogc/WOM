package com.womkk.ui.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.womkk.R;
import com.womkk.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewListFragment extends Fragment {

    private ReviewAdapter reviewAdapter;
    private FirebaseFirestore db;
    private List<Review> reviews;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviews = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(reviews, getContext());
        recyclerView.setAdapter(reviewAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::loadReviews);

        db = FirebaseFirestore.getInstance();
        loadReviews();

        FloatingActionButton fabAddReview = view.findViewById(R.id.fab_add_review);
        fabAddReview.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_reviewListFragment_to_addReviewFragment));

        return view;
    }

    private void loadReviews() {
        swipeRefreshLayout.setRefreshing(true);
        db.collection("reviews")
                .whereEqualTo("approved", true)
                .get()
                .addOnCompleteListener(task -> {
                    swipeRefreshLayout.setRefreshing(false);
                    if (task.isSuccessful()) {
                        reviews.clear();
                        QuerySnapshot querySnapshot = task.getResult();
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            Review review = document.toObject(Review.class);
                            if (review != null) {
                                review.setReviewId(document.getId());
                                reviews.add(review);
                            }
                        }
                        reviewAdapter.notifyDataSetChanged();
                    } else {
                        // handle the error
                    }
                });
    }
}
