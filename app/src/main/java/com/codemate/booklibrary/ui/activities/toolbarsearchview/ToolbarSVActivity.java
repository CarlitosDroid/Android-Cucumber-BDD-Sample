package com.codemate.booklibrary.ui.activities.toolbarsearchview;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codemate.booklibrary.R;
import com.codemate.booklibrary.data.Book;
import com.codemate.booklibrary.data.Library;
import com.codemate.booklibrary.data.RandomBookGenerator;
import com.codemate.booklibrary.ui.activities.main.MainPresenter;
import com.codemate.booklibrary.ui.activities.main.MainView;
import com.codemate.booklibrary.ui.adapter.BookAdapter;

import java.util.List;

public class ToolbarSVActivity extends AppCompatActivity implements MainView, SearchView.OnQueryTextListener {

    private Toolbar toolbar;




    private SearchView searchView;
    private MenuItem menuItem;
    private RecyclerView rcvBooks;
    MainPresenter presenter;

    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_sv);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);



        initializeViews();

        presenter = new MainPresenter(this, new Library(), new RandomBookGenerator());
        presenter.fetchBooks();

//        searchView = (SearchView) findViewById(R.id.searchview);
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//
//                Log.e("ABB ", "AABB " + newText);
//                return false;
//            }
//        });
    }

    private void initializeViews() {
        bookAdapter = new BookAdapter();

        RecyclerView bookRecycler = (RecyclerView) findViewById(R.id.rcvBook);
        bookRecycler.setLayoutManager(new LinearLayoutManager(this));
        bookRecycler.setAdapter(bookAdapter);

//        SearchView searchView = (SearchView) findViewById(R.id.searchView);
//        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar_sv, menu);
        menuItem = menu.findItem(R.id.m_search);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.m_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);


        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                Log.e("MENU ITEM ACTION ","EXPAND ");

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                Log.e("MENU ITEM ACTION ","COLLAPSE ");
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.aboutMenuItem:
//                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
//                startActivity(aboutIntent);
//                break;
//            case R.id.exitMenuItem:
//                finish();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    public void animateSearchToolbar(int numberOfMenuIcon, boolean containsOverflow, boolean show) {
//
//        mToolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.black));
//        //mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.quantum_grey_600));
//
//        if (show) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                int width = mToolbar.getWidth() -
//                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
//                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
//                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(mToolbar,
//                        isRtl(getResources()) ? mToolbar.getWidth() - width : width, mToolbar.getHeight() / 2, 0.0f, (float) width);
//                createCircularReveal.setDuration(250);
//                createCircularReveal.start();
//            } else {
//                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (-mToolbar.getHeight()), 0.0f);
//                translateAnimation.setDuration(220);
//                mToolbar.clearAnimation();
//                mToolbar.startAnimation(translateAnimation);
//            }
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                int width = mToolbar.getWidth() -
//                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
//                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
//                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(mToolbar,
//                        isRtl(getResources()) ? mToolbar.getWidth() - width : width, mToolbar.getHeight() / 2, (float) width, 0.0f);
//                createCircularReveal.setDuration(250);
//                createCircularReveal.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
//                        mToolbar.setBackgroundColor(getThemeColor(Main3Activity.this, R.attr.colorPrimary));
//                        //mDrawerLayout.setStatusBarBackgroundColor(getThemeColor(MainActivity.this, R.attr.colorPrimaryDark));
//                    }
//                });
//                createCircularReveal.start();
//            } else {
//                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
//                Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-mToolbar.getHeight()));
//                AnimationSet animationSet = new AnimationSet(true);
//                animationSet.addAnimation(alphaAnimation);
//                animationSet.addAnimation(translateAnimation);
//                animationSet.setDuration(220);
//                animationSet.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        mToolbar.setBackgroundColor(getThemeColor(Main3Activity.this, R.attr.colorPrimary));
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                mToolbar.startAnimation(animationSet);
//            }
//            //mDrawerLayout.setStatusBarBackgroundColor(ThemeUtils.getThemeColor(Main3Activity.this, R.attr.colorPrimaryDark));
//        }
//    }

}
