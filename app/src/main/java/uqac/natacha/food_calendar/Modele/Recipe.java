package uqac.natacha.food_calendar.Modele;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

    private String id;
    private String title;
    private String category;
    private String imageUrl;
    private int    numberPeople;
    private int    difficulty;
    private int    preparationTime;
    private int    cookingTime;

    private List<AlimentQuantifie> ingredients  = new ArrayList<>();
    private List<String>           instructions = new ArrayList<>();

    public Recipe() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public List<AlimentQuantifie> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public Recipe(String id, String title, String category, String imageUrl, int numberPeople, int difficulty, int preparationTime, int cookingTime, List<AlimentQuantifie> ingredients, List<String> instructions) {

        this.id = id;
        this.title = title;
        this.category = category;
        this.imageUrl = imageUrl;
        this.numberPeople = numberPeople;
        this.difficulty = difficulty;
        this.preparationTime = preparationTime;
        this.cookingTime = cookingTime;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
}
