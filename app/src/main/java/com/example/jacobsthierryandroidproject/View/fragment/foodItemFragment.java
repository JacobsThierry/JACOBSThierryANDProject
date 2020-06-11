package com.example.jacobsthierryandroidproject.View.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.jacobsthierryandroidproject.Model.RecipeModel;
import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.URL;

public class foodItemFragment extends Fragment {

    private Recipe food;
    private Bitmap image;
    private RecipeModel viewModel = new RecipeModel();
    private boolean isLoading;
    private ProgressBar progressBar;



    public static foodItemFragment newInstance(Recipe position) {
        foodItemFragment foodItemFragment = new foodItemFragment();
        Bundle arg = new Bundle();
        arg.putSerializable("food", position);
        foodItemFragment.setArguments(arg);
        return foodItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            if (args.getSerializable("food") != null) {
                food = (Recipe) args.getSerializable("food");
            }
        }
        return inflater.inflate(R.layout.fragment_food_item, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        progressBar = getView().findViewById(R.id.progressBar);
        if(food != null){
            if(food.getExtendedIngredients() == null || food.get_instructions_Spanned() == null ){
                viewModel.getSingleQueryResult().observe(this, new Observer<Recipe>() {
                    @Override
                    public void onChanged(Recipe recipe) {
                        food = recipe;
                        init();
                    }
                });

                viewModel.queryById(food.getId());
                partialInit();

            }else{
                init();
            }

        }
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                int visibility = isLoading? View.VISIBLE : View.INVISIBLE;
                progressBar.setVisibility(visibility);
            }
        });

    }

    private void partialInit(){
        TextView recipe_nameView = getView().findViewById(R.id.recipe_name);
        ImageView food_item_imageview = getView().findViewById(R.id.food_item_image);

        TextView IngredientAmountTextView = getView().findViewById(R.id.IngredientAmountText);
        TextView textPricePerServingAmountView = getView().findViewById(R.id.textPricePerServingAmount);
        TextView textIngredientListView = getView().findViewById(R.id.textIngredientList);
        TextView textInstructionView = getView().findViewById(R.id.textInstruction);

        if(food != null){
            if(food_item_imageview.getDrawable() == null) Picasso.get().load(food.getImage()).fit().into(food_item_imageview);
            recipe_nameView.setText(food.getTitle());
            IngredientAmountTextView.setText("");
            textPricePerServingAmountView.setText("");
            textIngredientListView.setText("");
            textInstructionView.setText("");
        }
    }

    private void init(){
        super.onStart();
        partialInit();
        TextView IngredientAmountTextView = getView().findViewById(R.id.IngredientAmountText);
        TextView textPricePerServingAmountView = getView().findViewById(R.id.textPricePerServingAmount);
        TextView textIngredientListView = getView().findViewById(R.id.textIngredientList);
        TextView textInstructionView = getView().findViewById(R.id.textInstruction);




        if (food != null) {

            IngredientAmountTextView.setText(Integer.toString(food.getExtendedIngredients().size()));
            textPricePerServingAmountView.setText(Float.toString(food.getPricePerServing()));
            textIngredientListView.setText(food.get_ingredient_list());
            textInstructionView.setText(food.get_instructions_Spanned());

        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_food_item_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent =  food.createIntent();
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}



