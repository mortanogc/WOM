// Generated by view binder compiler. Do not edit!
package com.womkk.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.womkk.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentReviewListBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FloatingActionButton fabAddReview;

  @NonNull
  public final ImageView ivStar;

  @NonNull
  public final RecyclerView rvReviews;

  @NonNull
  public final TextView tvAverageRating;

  private FragmentReviewListBinding(@NonNull ConstraintLayout rootView,
      @NonNull FloatingActionButton fabAddReview, @NonNull ImageView ivStar,
      @NonNull RecyclerView rvReviews, @NonNull TextView tvAverageRating) {
    this.rootView = rootView;
    this.fabAddReview = fabAddReview;
    this.ivStar = ivStar;
    this.rvReviews = rvReviews;
    this.tvAverageRating = tvAverageRating;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentReviewListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentReviewListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_review_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentReviewListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fab_add_review;
      FloatingActionButton fabAddReview = ViewBindings.findChildViewById(rootView, id);
      if (fabAddReview == null) {
        break missingId;
      }

      id = R.id.iv_star;
      ImageView ivStar = ViewBindings.findChildViewById(rootView, id);
      if (ivStar == null) {
        break missingId;
      }

      id = R.id.rv_reviews;
      RecyclerView rvReviews = ViewBindings.findChildViewById(rootView, id);
      if (rvReviews == null) {
        break missingId;
      }

      id = R.id.tv_average_rating;
      TextView tvAverageRating = ViewBindings.findChildViewById(rootView, id);
      if (tvAverageRating == null) {
        break missingId;
      }

      return new FragmentReviewListBinding((ConstraintLayout) rootView, fabAddReview, ivStar,
          rvReviews, tvAverageRating);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
