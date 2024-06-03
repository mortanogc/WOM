package com.womkk.model;

public class News {
    private String titleEn;
    private String titleRu;
    private String contentEn;
    private String contentRu;
    private String imageUrl;
    private String date;

    // Конструктор с параметрами
    public News(String titleEn, String titleRu, String contentEn, String contentRu, String imageUrl, String date) {
        this.titleEn = titleEn;
        this.titleRu = titleRu;
        this.contentEn = contentEn;
        this.contentRu = contentRu;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    // Конструктор без параметров
    public News() {
    }

    // Геттеры
    public String getTitleEn() {
        return titleEn;
    }

    public String getTitleRu() {
        return titleRu;
    }

    public String getContentEn() {
        return contentEn;
    }

    public String getContentRu() {
        return contentRu;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDate() {
        return date;
    }

    // Метод toString для удобства отображения информации об объекте
    @Override
    public String toString() {
        return "News{" +
                "titleEn='" + titleEn + '\'' +
                ", titleRu='" + titleRu + '\'' +
                ", contentEn='" + contentEn + '\'' +
                ", contentRu='" + contentRu + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
