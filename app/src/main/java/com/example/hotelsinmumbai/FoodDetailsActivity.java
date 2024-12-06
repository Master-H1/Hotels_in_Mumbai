package com.example.hotelsinmumbai;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FoodDetailsActivity extends AppCompatActivity {
    MyDatabaseHelper myDB;
    ImageButton backFromFoodDetails;
    String id;
    String name;
    String category;
    String price;
    int vegetarian;
    TextView foodId, foodName, foodCategory, foodPrice, foodToolTitle, is_vegetarian_text;
    AppCompatButton deteteButton, goToupdateFood;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // setting window above toolbar color
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkBlue));

        // Back button
        backFromFoodDetails = findViewById(R.id.backFromFoodDetails);
        backFromFoodDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetailsActivity.this, TabbedActivity.class);
                startActivity(intent);
                finish();
            }
        });

        foodId = findViewById(R.id.food_id_text);
        foodName = findViewById(R.id.food_name_text);
        foodCategory = findViewById(R.id.food_category_text);
        foodPrice = findViewById(R.id.food_price_text);
        foodToolTitle = findViewById(R.id.food_details);
        is_vegetarian_text = findViewById(R.id.is_vegetarian_text);

        //set data to textviews
        getAndSetData();

        // Go to Update Food Data
        goToupdateFood = findViewById(R.id.goToupdateFood);
        goToupdateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodDetailsActivity.this, UpdateFoodActivity.class);
                intent.putExtra("id",foodId.getText().toString());
                intent.putExtra("name",foodName.getText().toString());
                intent.putExtra("category",foodCategory.getText().toString());
                intent.putExtra("price",foodPrice.getText().toString());
                intent.putExtra("vegetarian",String.valueOf(vegetarian));
                startActivity(intent);
            }
        });
        // Delete Data
        deteteButton = findViewById(R.id.deleteData);
        deteteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

    }
    @SuppressLint("SetTextI18n")
    void getAndSetData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") && getIntent().hasExtra("category") &&
                getIntent().hasExtra("price") && getIntent().hasExtra("vegetarian")) {
            // Get Intent data
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            category = getIntent().getStringExtra("category");
            price = getIntent().getStringExtra("price");
            vegetarian = Integer.parseInt( getIntent().getStringExtra("vegetarian"));

            // Set Intent data
            foodToolTitle.setText(name);
            foodId.setText(id);
            foodName.setText(name);
            foodCategory.setText(category);
            foodPrice.setText(price);
            if (vegetarian == 1){
                is_vegetarian_text.setText("true");
            }else {
                is_vegetarian_text.setText("false");
            }
        }
    }

    // create a delete confirmation message
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(FoodDetailsActivity.this);
        builder.setTitle("Delete Food Data.");
        builder.setMessage("Are you sure you want to delete `"+foodName.getText().toString()+"`?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB = new MyDatabaseHelper(FoodDetailsActivity.this);
                myDB.deleteRowData(id);
                Intent intent = new Intent(FoodDetailsActivity.this, TabbedActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}