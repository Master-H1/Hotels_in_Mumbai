package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateFoodActivity extends AppCompatActivity {
    ImageButton backFromFoodUpdate;
    String id, name, category, price;
    int is_vegetarian;
    TextView add_food_name_update, add_food_price_update, foodToolTitle;
    Spinner add_food_category_update;
    CheckBox is_it_vegetarian_update;

    Button update_food;
    MyDatabaseHelper myDB;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_food);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // setting window above toolbar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkBlue));

        //back button
        backFromFoodUpdate = findViewById(R.id.backFromFoodUpdate);
        backFromFoodUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateFoodActivity.this, FoodDetailsActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("category", category);
                intent.putExtra("price", price);
                intent.putExtra("vegetarian",String.valueOf(is_vegetarian));
                startActivity(intent);
                finish();
            }
        });
        foodToolTitle = findViewById(R.id.food_details);
        add_food_name_update = findViewById(R.id.add_food_name_update);
        add_food_category_update = findViewById(R.id.add_food_category_update);
        add_food_price_update = findViewById(R.id.add_food_price_update);
        is_it_vegetarian_update = findViewById(R.id.is_it_vegetarian_update);
        getAndSetData();

        //update food button
        update_food = findViewById(R.id.update_food);
        update_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food_category = add_food_category_update.getSelectedItem().toString();

                myDB = new MyDatabaseHelper(UpdateFoodActivity.this);
                if (is_it_vegetarian_update.isChecked()){
                    is_vegetarian = 1;
                }else{
                    is_vegetarian = 0;
                }
                //call update method
                myDB.updateRowData(id, add_food_name_update.getText().toString().trim(),
                        food_category,
                        Integer.parseInt(add_food_price_update.getText().toString()),
                        is_vegetarian);

                Intent intent = new Intent(UpdateFoodActivity.this, TabbedActivity.class);
                startActivity(intent);

            }
        });
    }


    @SuppressLint("SetTextI18n")
    void getAndSetData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")&& getIntent().hasExtra("category") &&
                getIntent().hasExtra("price") && getIntent().hasExtra("vegetarian")){
            // Get Intent data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            category = getIntent().getStringExtra("category");
            price = getIntent().getStringExtra("price");
            is_vegetarian = Integer.parseInt(getIntent().getStringExtra("vegetarian"));

            // Set Intent data
            foodToolTitle.setText(String.valueOf(name));
            add_food_name_update.setText(String.valueOf(name));
            int spinnerPosition = ((ArrayAdapter<String>) add_food_category_update.getAdapter()).getPosition(category);
            add_food_category_update.setSelection(spinnerPosition);
            add_food_price_update.setText(String.valueOf(price));
            if(is_vegetarian == 1){
                is_it_vegetarian_update.setChecked(true);
            }else{
                is_it_vegetarian_update.setChecked(false);
            }
        }
    }
}