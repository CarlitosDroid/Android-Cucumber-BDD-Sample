package com.codemate.booklibrary.ui.activities.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codemate.booklibrary.R;
import com.codemate.booklibrary.ui.activities.main.MainActivity;
import com.codemate.booklibrary.ui.activities.searchview_toolbar.SearchViewToolbarActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnSearchView;
    private Button btnMaterialSearchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnSearchView = (Button) findViewById(R.id.btnSearchView);
        btnMaterialSearchView = (Button) findViewById(R.id.btnMaterialSearchView);

        btnSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        btnMaterialSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SearchViewToolbarActivity.class));
            }
        });
    }
}
