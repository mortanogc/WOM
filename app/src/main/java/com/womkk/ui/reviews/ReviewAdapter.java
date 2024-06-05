package com.womkk.ui.reviews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.womkk.R;
import com.womkk.model.Review;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<Review> reviews;
    private Context context;

    public ReviewAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.authorName.setText(review.getAuthorName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(review.getReviewDate());
        holder.reviewDate.setText(formattedDate);

        holder.rating.setText(String.valueOf(review.getRating()));
        holder.title.setText(review.getTitle());
        holder.reviewText.setText(review.getReviewText());

        List<String> photos = review.getPhotos();
        if (photos != null && !photos.isEmpty()) {
            View.OnClickListener onImageClickListener = v -> {
                int index = (int) v.getTag();
                String imageUrl = photos.get(index);
                com.womkk.ui.reviews.FullscreenImageFragmentArgs args = new com.womkk.ui.reviews.FullscreenImageFragmentArgs.Builder(imageUrl).build();
                Bundle bundle = args.toBundle();
                Navigation.findNavController(v).navigate(R.id.fullscreenImageFragment, bundle);
            };

            if (photos.size() >= 1) {
                holder.reviewImage1.setVisibility(View.VISIBLE);
                holder.reviewImage1.setTag(0);
                holder.reviewImage1.setOnClickListener(onImageClickListener);
                Glide.with(context).load(Review.base64ToBitmap(photos.get(0))).into(holder.reviewImage1);
            }
            if (photos.size() >= 2) {
                holder.reviewImage2.setVisibility(View.VISIBLE);
                holder.reviewImage2.setTag(1);
                holder.reviewImage2.setOnClickListener(onImageClickListener);
                Glide.with(context).load(Review.base64ToBitmap(photos.get(1))).into(holder.reviewImage2);
            }
            if (photos.size() >= 3) {
                holder.reviewImage3.setVisibility(View.VISIBLE);
                holder.reviewImage3.setTag(2);
                holder.reviewImage3.setOnClickListener(onImageClickListener);
                Glide.with(context).load(Review.base64ToBitmap(photos.get(2))).into(holder.reviewImage3);
            }
        }

        // Проверка текущего пользователя для отображения меню
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && review.getUserId().equals(user.getUid())) {
            holder.reviewMenu.setVisibility(View.VISIBLE);
            holder.reviewMenu.setOnClickListener(v -> {
                PopupMenu popupMenu = new PopupMenu(context, holder.reviewMenu);
                popupMenu.inflate(R.menu.review_item_menu);
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.action_edit_review:
                            // Логика для редактирования отзыва
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("review", reviewToHashMap(review));
                            Navigation.findNavController(v).navigate(R.id.action_reviewListFragment_to_addReviewFragment, bundle);
                            return true;
                        case R.id.action_delete_review:
                            // Логика для удаления отзыва
                            deleteReview(review);
                            return true;
                        default:
                            return false;
                    }
                });
                popupMenu.show();
            });
        } else {
            holder.reviewMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    private void deleteReview(Review review) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("reviews").document(review.getReviewId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    reviews.remove(review);
                    notifyDataSetChanged();
                    Toast.makeText(context, R.string.review_deleted, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, R.string.error_deleting_review, Toast.LENGTH_SHORT).show();
                });
    }

    private HashMap<String, Object> reviewToHashMap(Review review) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", review.getUserId());
        map.put("authorName", review.getAuthorName());
        map.put("rating", review.getRating());
        map.put("title", review.getTitle());
        map.put("reviewText", review.getReviewText());
        map.put("photos", review.getPhotos());
        map.put("reviewDate", review.getReviewDate());
        map.put("approved", review.isApproved());
        return map;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorName;
        TextView reviewDate;
        TextView rating;
        TextView title;
        TextView reviewText;
        ImageView reviewImage1;
        ImageView reviewImage2;
        ImageView reviewImage3;
        ImageView reviewMenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.review_author);
            reviewDate = itemView.findViewById(R.id.review_date);
            rating = itemView.findViewById(R.id.review_rating);
            title = itemView.findViewById(R.id.review_title);
            reviewText = itemView.findViewById(R.id.review_text);
            reviewImage1 = itemView.findViewById(R.id.review_image_1);
            reviewImage2 = itemView.findViewById(R.id.review_image_2);
            reviewImage3 = itemView.findViewById(R.id.review_image_3);
            reviewMenu = itemView.findViewById(R.id.review_menu);
        }
    }
}
