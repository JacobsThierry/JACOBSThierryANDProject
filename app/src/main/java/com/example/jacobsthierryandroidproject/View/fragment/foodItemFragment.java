package com.example.jacobsthierryandroidproject.View.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;

import java.io.InputStream;
import java.net.URL;

public class foodItemFragment extends Fragment {

    private Recipe food;
    private Bitmap image;



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
        TextView IngredientAmountTextView = getView().findViewById(R.id.IngredientAmountText);
        TextView recipe_nameView = getView().findViewById(R.id.recipe_name);
        TextView textPricePerServingAmountView = getView().findViewById(R.id.textPricePerServingAmount);
        TextView textIngredientListView = getView().findViewById(R.id.textIngredientList);
        TextView textInstructionView = getView().findViewById(R.id.textInstruction);
        ImageView food_item_imageview = getView().findViewById(R.id.food_item_image);



        if (food != null) {
            new DownloadImageTask(food_item_imageview).execute(food.getImage());

            IngredientAmountTextView.setText(Integer.toString(food.getExtendedIngredients().size()));
            recipe_nameView.setText(food.getTitle());
            textPricePerServingAmountView.setText(Float.toString(food.getPricePerServing()));
            textIngredientListView.setText(food.get_ingredient_list());
            textInstructionView.setText(food.get_instructions_Spanned());

        }

    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage){
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;

            try{
                InputStream in = new URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

            }catch (Exception e){
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {

            bmImage.setImageBitmap(bitmap);

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



