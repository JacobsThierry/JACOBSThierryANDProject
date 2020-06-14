package com.example.jacobsthierryandroidproject.Pojo.foodObjects;

import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.jacobsthierryandroidproject.Pojo.Ingredient;
import com.example.jacobsthierryandroidproject.Pojo.Instructions.instruction;
import com.example.jacobsthierryandroidproject.Pojo.Instructions.step;
import com.example.jacobsthierryandroidproject.Repository.RemoteDataSource.API.ServiceGenerator;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "recipes_favourites")
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = false)
    protected int id;
    protected String summary;
    protected String instructions;
    protected float pricePerServing;

    protected List<instruction> analyzedInstructions;
    protected List<Ingredient> extendedIngredients;
    protected String image;
    protected String imageUrls;
    protected int readyInMinutes;
    protected int servings;
    protected String title;


    public Recipe(int id, String summary, String instructions, float pricePerServing, List<instruction> analyzedInstructions, List<Ingredient> extendedIngredients, String image, String imageUrls, int readyInMinutes, int servings, String title) {
        this.id = id;
        this.summary = summary;
        this.instructions = instructions;
        this.pricePerServing = pricePerServing;
        this.analyzedInstructions = analyzedInstructions;
        this.extendedIngredients = extendedIngredients;
        this.image = image;
        this.imageUrls = imageUrls;
        this.readyInMinutes = readyInMinutes;
        this.servings = servings;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        if (image == null) return null;
        if (image.startsWith(ServiceGenerator.imageUrl)) return image;
        else return ServiceGenerator.imageUrl + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrls() {
        return imageUrls;

    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Intent createIntent() {
        Intent message = new Intent(Intent.ACTION_SEND);
        message.setType("text/plain");
        message.putExtra(Intent.EXTRA_SUBJECT, "I cooked something today !");
        message.putExtra(Intent.EXTRA_TEXT, "Today I cooked a " + getTitle() + " !");
        return message;

    }

    public Spanned get_ingredient_list() {
        String list = "";
        for (int i = 0; i < extendedIngredients.size(); i++) {
            String s = extendedIngredients.get(i).getOriginalString();

            //this is not pretty to look at, but needed
            list += "&#8226; " + s + "<br/>";
        }
        Spanned span;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            span = Html.fromHtml(list, Html.FROM_HTML_MODE_LEGACY);
        } else {
            span = Html.fromHtml(list);
        }

        return span;
    }

    public Spanned get_instructions_Spanned() {
        String inst = "";
        if (analyzedInstructions.size() > 0) {
            instruction instruction = analyzedInstructions.get(0);
            List<step> s = instruction.getSteps();
            for (int i = 0; i < s.size(); i++) {
                inst += s.get(i).getStep() + "<br/>";

            }


            Spanned span;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                span = Html.fromHtml(inst, Html.FROM_HTML_MODE_COMPACT);
            } else {
                span = Html.fromHtml(inst);
            }

            return span;
        } else {
            return Html.fromHtml("");
        }
    }

    public float getPricePerServing() {
        return pricePerServing;
    }

    public void setPricePerServing(float pricePerServing) {
        this.pricePerServing = pricePerServing;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    public List<instruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public void setAnalyzedInstructions(List<instruction> analyzedInstructions) {
        this.analyzedInstructions = analyzedInstructions;
    }

    public List<Ingredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<Ingredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", instructions='" + instructions + '\'' +
                ", pricePerServing=" + pricePerServing +
                ", analyzedInstructions=" + analyzedInstructions +
                ", extendedIngredients=" + extendedIngredients +
                ", image='" + image + '\'' +
                ", imageUrls='" + imageUrls + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", servings=" + servings +
                ", title='" + title + '\'' +
                '}';
    }
}
