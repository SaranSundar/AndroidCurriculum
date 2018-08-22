package com.sszg.doordash;

public class Restaurant {

    private String name, genre, money, distance, stars, reviews, deliveryFee;

    public Restaurant(String name, String genre, String money, String distance, String stars, String reviews, String deliveryFee) {
        this.name = name;
        this.genre = genre;
        this.money = money;
        this.distance = distance;
        this.stars = stars;
        this.reviews = reviews;
        this.deliveryFee = deliveryFee;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDistance() {
        return distance;
    }

    public String getMoney() {
        return money;
    }

    public String getStars() {
        return stars;
    }

    public String getReviews() {
        return reviews;
    }

    public String getFee() {
        return deliveryFee;
    }
}
