package com.example.hotelsinmumbai;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {
    RecyclerView recyclerView;
    MyDatabaseHelper myBD;
    MyFoodRecyclerViewAdapter adapter;
    ArrayList food_id, food_name, food_category, food_price, is_vegetarian;
    LinearLayout emptyRecyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        food_id = new ArrayList();
        food_name = new ArrayList();
        food_category = new ArrayList();
        food_price = new ArrayList();
        is_vegetarian = new ArrayList();

        //calling the linear layout containing empty message when no data found
        emptyRecyclerView = view.findViewById(R.id.emptyRecyclerView);

        myBD = new MyDatabaseHelper(getContext());
        storeDataInArray();
        adapter = new MyFoodRecyclerViewAdapter(getContext(), food_id, food_name, food_category, food_price, is_vegetarian);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        ArrayList<Food> foods = new ArrayList<>();
//        foods.add(new Food(1,"Veg food",5,true));
//        foods.add(new Food(2,"Non veg food",7,false));
//        foods.add(new Food(3,"Chinese food",10,true));
//        foods.add(new Food(4,"North Indian food",8,true));
//        foods.add(new Food(5,"South Indian food",15,true));
//        foods.add(new Food(6,"Sweet food",20,false));
//        foods.add(new Food(7,"Desserts",14,true));
//        foods.add(new Food(8,"Beverages",4,true));
//        foods.add(new Food(9,"Snacks",6,false));
//        foods.add(new Food(10,"Pizza",3,false));
//        foods.add(new Food(12,"Salad",12,true));
//        foods.add(new Food(13,"Sandwich",3,false));
//
//        // call food Adapter
//        MyFoodRecyclerViewAdapter adapter = new MyFoodRecyclerViewAdapter();
//        adapter.setFood(foods);
//
//        // call and set my recyclerView
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



    }
    void storeDataInArray(){
        Cursor cursor = myBD.readAllData();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                food_id.add(cursor.getString(0));
                food_name.add(cursor.getString(1));
                food_category.add(cursor.getString(2));
                food_price.add(cursor.getString(3));
                is_vegetarian.add(cursor.getString(4));
            }
        }
        else {
            emptyRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}