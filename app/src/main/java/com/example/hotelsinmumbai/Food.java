package com.example.hotelsinmumbai;

public class Food {
    private int foodId;
    private String foodName;
    private String food_category;
    private int foodPrice;
    private String isVegetarian;

    public Food(int foodId, String foodName, String food_category, int foodPrice, String isVegetarian) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.food_category = food_category;
        this.foodPrice = foodPrice;
        this.isVegetarian = isVegetarian;
    }

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFood_category() {
        return food_category;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public String getIsVegetarian() {
        return isVegetarian;
    }
}
