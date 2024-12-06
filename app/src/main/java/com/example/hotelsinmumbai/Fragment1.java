package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment1.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view  =  inflater.inflate(R.layout.fragment_1, container, false);
        EditText add_food_name = view.findViewById(R.id.add_food_name);
        Spinner add_food_category = view.findViewById(R.id.add_food_category);
        EditText add_food_price = view.findViewById(R.id.add_food_price);
        CheckBox is_it_vegetarian = view.findViewById(R.id.is_it_vegetarian);

        // call save food button and functions
        Button save_food = view.findViewById(R.id.save_food);
        save_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try(MyDatabaseHelper myDB = new MyDatabaseHelper(getContext())){
                    // Lets validate our form before saving data
                    String food_name = add_food_name.getText().toString();
                    String food_category = add_food_category.getSelectedItem().toString();
                    String food_price = add_food_price.getText().toString();
                    if (food_name.isEmpty()){
                        add_food_name.setError("Please enter a food name");
                        add_food_name.setBackgroundResource(R.drawable.input_error_btn);
                    }
                    else if (food_category.equals("Select food category")) {
                        add_food_name.setBackgroundResource(R.drawable.reserve_food_bg);
                        Toast.makeText(getContext(), "Please select a food category", Toast.LENGTH_SHORT).show();
                        add_food_category.setBackgroundResource(R.drawable.input_error_btn);
                    }
                    else if (food_price.isEmpty()) {
                        add_food_category.setBackgroundResource(R.drawable.reserve_food_bg);
                        add_food_price.setError("Please Enter food price");
                        add_food_price.setBackgroundResource(R.drawable.input_error_btn);
                    }
                    else {
                        add_food_price.setBackgroundResource(R.drawable.reserve_food_bg);
                        // this will be implemented if all validations are successfully
                        int is_vegetarian;
                        if (is_it_vegetarian.isChecked()){
                            is_vegetarian = 1;
                        }else {
                            is_vegetarian = 0;
                        }
                        // call add food function
                        myDB.addFood(add_food_name.getText().toString(),
                                food_category,
                                Integer.parseInt(add_food_price.getText().toString()),
                                is_vegetarian);

                        // clear form
                        add_food_name.setText("");
                        add_food_category.setSelected(false);
                        add_food_price.setText("");
                        is_it_vegetarian.setSelected(false);
                        Intent intent = new Intent(getContext(),TabbedActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });
        return view;
    }
}