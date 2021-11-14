package com.owoa.calendify.category.create;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.owoa.calendify.R;


public class CategoryCreateActivity extends AppCompatActivity {
    CategoryCreatePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_create);

        Intent intent = getIntent();
        String uid = intent.getStringExtra(getString(R.string.uid));

        presenter = new CategoryCreatePresenter(this);

        EditText nameEditText = findViewById(R.id.category_name);
        EditText descriptionEditText = findViewById(R.id.category_description);

        Button colorButton = findViewById(R.id.category_color);
        Button confirmButton = findViewById(R.id.create_category_confirm);

        colorButton.setOnClickListener(v ->
                Toast.makeText(CategoryCreateActivity.this, "ColorPicker 연결 후 컬러값 String 호출", Toast.LENGTH_SHORT).show());

        confirmButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String color = "";
            presenter.createCategory(uid, name, description, color);
        });
    }
}