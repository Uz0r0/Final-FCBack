package com.example.FiteClub;

public enum Category {
    MUSIC,
    MUSICIAN,
    ACTOR,
    ANIME,
    FILM,
    MEME;
    public static Category getRandomCategory() {
        Category[] categories = Category.values();
        return categories[(int) (Math.random() * categories.length)];
    }
}
