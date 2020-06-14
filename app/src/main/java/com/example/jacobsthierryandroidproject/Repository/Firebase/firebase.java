package com.example.jacobsthierryandroidproject.Repository.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jacobsthierryandroidproject.Pojo.Comment;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class firebase {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    MutableLiveData<ArrayList<Comment>> mutableLiveData = new MutableLiveData<>();
    ArrayList<Comment> comments = new ArrayList<>();
    DatabaseReference reference;
    static String COMMENT_PATH = "comments";


    public firebase(Recipe recipe) {
        comments = new ArrayList<>();
        reference = database.getReference().child(COMMENT_PATH);
        getComments(recipe);
    }

    public void addComment(Comment com) {

        reference.child(Integer.toString(com.getRecipeId())).push().setValue(com);

    }

    public LiveData<ArrayList<Comment>> getLiveData() {
        return mutableLiveData;
    }

    public void getComments(Recipe recipe) {
        getComments(recipe.getId());
    }

    private void getComments(int r) {

        final DatabaseReference ref = reference.child(Integer.toString(r));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    comments.add(child.getValue(Comment.class));
                }
                mutableLiveData.postValue(comments);

                Log.d("fodd add", "listener");
                ref.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                        comments.add(dataSnapshot.getValue(Comment.class));
                        mutableLiveData.postValue(comments);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
