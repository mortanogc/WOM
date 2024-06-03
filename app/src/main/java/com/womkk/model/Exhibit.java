package com.womkk.model;

public class Exhibit {
    private String id;
    private boolean isARCapable;
    private String nameEn;
    private String nameRu;
    private String descriptionEn;
    private String descriptionRu;
    private String imageUrl;
    private String mapLink;

    // Конструктор с параметрами
    public Exhibit(String id, boolean isARCapable, String nameEn, String nameRu, String descriptionEn, String descriptionRu, String imageUrl, String mapLink) {
        this.id = id;
        this.isARCapable = isARCapable;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.descriptionEn = descriptionEn;
        this.descriptionRu = descriptionRu;
        this.imageUrl = imageUrl;
        this.mapLink = mapLink;
    }

    // Конструктор без параметров
    public Exhibit() {
    }

    // Геттеры
    public String getId() {
        return id;
    }

    public boolean isARCapable() {
        return isARCapable;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getMapLink() {
        return mapLink;
    }

    // Метод toString для удобства отображения информации об объекте
    @Override
    public String toString() {
        return "Exhibit{" +
                "id='" + id + '\'' +
                ", isARCapable=" + isARCapable +
                ", nameEn='" + nameEn + '\'' +
                ", nameRu='" + nameRu + '\'' +
                ", descriptionEn='" + descriptionEn + '\'' +
                ", descriptionRu='" + descriptionRu + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", mapLink='" + mapLink + '\'' +
                '}';
    }
}
