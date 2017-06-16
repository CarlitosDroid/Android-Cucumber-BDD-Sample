package com.codemate.booklibrary.ui.activities.materialsearchview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codemate.booklibrary.R;
import com.codemate.booklibrary.data.Book;
import com.codemate.booklibrary.data.Library;
import com.codemate.booklibrary.data.RandomBookGenerator;
import com.codemate.booklibrary.ui.adapter.BookAdapter;

import java.lang.reflect.Field;
import java.util.List;


public class MaterialSVActivity extends AppCompatActivity implements MaterialSVView, SearchView.OnQueryTextListener {

    Toolbar toolbar, secondToolbar;
    Menu secondToolbarMenu;
    MenuItem secondToolbarMenuItem;

    RecyclerView bookRecycler;

    MaterialSVPresenter presenter;

    private BookAdapter bookAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_sv);

        initializeViews();
        setupSearchToolbar();
        setupSearchView();
    }

    private void initializeViews() {

        bookRecycler = (RecyclerView) findViewById(R.id.bookRecycler);
        secondToolbar = (Toolbar) findViewById(R.id.secondToolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup RecyclerView
        bookRecycler.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter();
        bookRecycler.setAdapter(bookAdapter);

        //Setup Presenter
        presenter = new MaterialSVPresenter(this, new Library(), new RandomBookGenerator());
        presenter.fetchBooks();
    }

    //setUp the secondToolbar and all its menuItem's event
    public void setupSearchToolbar() {
        //R.menu.menu_search is a especial xml Menu because contains app:actionViewClass property and
        //we can inflate a widget class for this case the SearchViewWidget
        //Inflating a menu to the secondToolbar
        secondToolbar.inflateMenu(R.menu.menu_search);
        //Getting the menu of the secondToolbar for access to its menuItems
        secondToolbarMenu = secondToolbar.getMenu();

        //find a MenuItem in the secondToolbarMenu
        secondToolbarMenuItem = secondToolbarMenu.findItem(R.id.action_filter_search);


        //add a listener for detect when appear or disappear the SearchView widget when we click the secondToolbarMenuItem
        MenuItemCompat.setOnActionExpandListener(secondToolbarMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                // Do something when collapsed
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    //When disappear the SearchViewWidget , we have to hide(INVISIBLE) this second toolbar
                    //and we'll do it using the reveal animation
                    circleReveal(1, true, false);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                    secondToolbar.setVisibility(View.GONE);
                }
                return true;
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;
            }
        });
    }

    public void setupSearchView() {
        final SearchView searchView =
                (SearchView) secondToolbarMenu.findItem(R.id.action_filter_search).getActionView();

        // Enable/Disable Submit button in the keyboard
        searchView.setSubmitButtonEnabled(false);

        // Change search close button image
        ImageView closeButton = (ImageView) searchView.findViewById(R.id.search_close_btn);
        closeButton.setImageResource(R.drawable.ic_close_grey_700_24dp);

        // set hint and the text colors
        EditText txtSearch = ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text));
        txtSearch.setHint("Search..");
        txtSearch.setHintTextColor(Color.DKGRAY);

        // set the cursor
        AutoCompleteTextView searchTextView = (AutoCompleteTextView) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(searchTextView, R.drawable.search_cursor); //This sets the cursor resource ID to 0 or @null which will make it visible on white background
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView.setOnQueryTextListener(this);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    circleReveal(1, true, true);
                } else {
                    toolbar.setVisibility(View.GONE);
                    secondToolbar.setVisibility(View.VISIBLE);
                }
                //After secondToolbar is shown we need to expand its actionView for showing the SearchView directly
                secondToolbarMenuItem.expandActionView();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Home Settings Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //This method show or hide the secondToolbar with the Reveal Animation
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void circleReveal(int menuItemPositionFromRight, boolean containsOverflow, final boolean shouldShowSecondToolbar) {

        // make the view visible and start the animation
        if (shouldShowSecondToolbar)
            secondToolbar.setVisibility(View.VISIBLE);

        int width = secondToolbar.getWidth();

        // getDimensionPixelOffset() in my case 48dp returns 96 pixels
        if (menuItemPositionFromRight > 0)
            //we subtract menuItem size from the right
            width -= (menuItemPositionFromRight * getResources().getDimensionPixelOffset(R.dimen.action_button_min_width_material)) - (getResources().getDimensionPixelSize(R.dimen.action_button_min_width_material) / 2);

        //if we are in a toolbar we should subtract a overflowSize
        if (containsOverflow)
            width -= getResources().getDimensionPixelSize(R.dimen.action_button_min_width_overflow_material);

        int cx = width;
        int cy = secondToolbar.getHeight() / 2;
        Animator anim;
        if (shouldShowSecondToolbar)
            anim = ViewAnimationUtils.createCircularReveal(secondToolbar, cx, cy, 0, (float) width);
        else
            anim = ViewAnimationUtils.createCircularReveal(secondToolbar, cx, cy, (float) width, 0);

        anim.setDuration((long) 220);



        // make the view invisible when the animation is done
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!shouldShowSecondToolbar) {
                    super.onAnimationEnd(animation);
                    secondToolbar.setVisibility(View.INVISIBLE);
                }
            }
        });
        anim.start();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        presenter.searchForBooks(newText);
        return false;
    }

    @Override
    public void showBooks(List<Book> books) {
        bookAdapter.updateItems(books);
    }
}
