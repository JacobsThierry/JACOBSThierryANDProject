package com.example.jacobsthierryandroidproject.Pojo;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Comment implements Serializable {

    public String author;
    public String content;
    @Exclude
    private int recipeId;

    public Comment(){}

    public Comment(String author, String content, int recipeId) {
        this.author = author;
        this.content = content;
        this.recipeId = recipeId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", recipeId=" + recipeId +
                '}';
    }
}
