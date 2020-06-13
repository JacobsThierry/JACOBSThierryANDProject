package com.example.jacobsthierryandroidproject.Repository.Firebase;

import com.example.jacobsthierryandroidproject.Pojo.Comment;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;

import org.junit.Test;

import static org.junit.Assert.*;

public class firebaseTest {

    @Test
    public void addComment() {
        firebase firebase = new firebase();
        firebase.addComment(new Comment("Thierry", "I loved it", 5));

    }

    @Test
    public void getComments() {
        firebase firebase = new firebase();
        firebase.getComments(1);

    }
}