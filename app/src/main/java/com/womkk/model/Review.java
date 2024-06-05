package com.womkk.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Review implements Serializable {
    private String reviewId;
    private String userId;
    private String authorName;
    private float rating;
    private String title;
    private String reviewText;
    private List<String> photos;
    private Date reviewDate;
    private boolean approved;

    // Пустой конструктор для Firestore
    public Review() {}

    public Review(String reviewId, String userId, String authorName, float rating, String title, String reviewText, List<String> photos, boolean approved) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.authorName = authorName;
        this.rating = rating;
        this.title = title;
        this.reviewText = reviewText;
        this.photos = photos;
        this.reviewDate = new Date(); // Устанавливает текущую дату и время
        this.approved = approved;
    }

    // Геттеры и сеттеры
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public float getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getReviewText() {
        return reviewText;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static Bitmap base64ToBitmap(String base64) {
        byte[] decodedBytes = Base64.decode(base64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", userId='" + userId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", photos=" + photos +
                ", reviewDate=" + reviewDate +
                ", approved=" + approved +
                '}';
    }
}

