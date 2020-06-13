package com.example.jacobsthierryandroidproject.View.fragment.recipePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.jacobsthierryandroidproject.Pojo.foodObjects.Recipe;
import com.example.jacobsthierryandroidproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class foodFragmentStateAdapter extends Fragment {

    DemoCollectionAdapter demoCollectionAdapter;
    ViewPager2 viewPager;
    Recipe recipe;

    public static foodFragmentStateAdapter getInstance(Recipe rec){
        foodFragmentStateAdapter foodItemFragment = new foodFragmentStateAdapter();
        Bundle arg = new Bundle();
        arg.putSerializable("food", rec);
        foodItemFragment.setArguments(arg);
        return foodItemFragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            if (args.getSerializable("food") != null) {
                recipe = (Recipe) args.getSerializable("food");
            }
        }

        return inflater.inflate(R.layout.food_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        demoCollectionAdapter = new DemoCollectionAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(demoCollectionAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String text;
                switch(position){
                    case 0 : text = getString(R.string.Recipe) ; break;
                    case 1 : text = getString(R.string.Comments); break;
                    default: text = "tab";
                }
                tab.setText(text);
            }
        }).attach();

    }

    public class DemoCollectionAdapter extends FragmentStateAdapter{
        public DemoCollectionAdapter(Fragment fragment){
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            Fragment fragment;
            switch(position){
                case 0 : fragment = foodItemFragment.newInstance(recipe); break;
                case 1 : fragment = CommentsFragment.newInstance(recipe);break;
                default : fragment = null;
            }

            return fragment;
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }



}
