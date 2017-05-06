package com.codemate.booklibrary.ui.activities.dialogmaterialsearchview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codemate.booklibrary.R;

public class DialogMaterialSVActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private Toolbar toolbar_search;


    private MenuItem menuItem_seach;
    private Menu menu_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_material_sv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_search = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        toolbar_search.inflateMenu(R.menu.menu_search);

        menu_search = toolbar_search.getMenu();

        menuItem_seach = menu_search.findItem(R.id.action_filter_search);


        MenuItemCompat.setOnActionExpandListener(menuItem_seach, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Toast.makeText(DialogMaterialSVActivity.this, "EXPANDED", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Toast.makeText(DialogMaterialSVActivity.this, "COLLAPSED", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_status:
                Toast.makeText(this, "Home Status Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:

                menuItem_seach.expandActionView();


                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
