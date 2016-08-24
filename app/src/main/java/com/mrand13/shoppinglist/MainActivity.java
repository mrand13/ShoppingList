package com.mrand13.shoppinglist;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mrand13.shoppinglist.adapters.ShoppingListAdapter;
import com.mrand13.shoppinglist.fragments.ShoppingListFragment;


public class MainActivity extends AppCompatActivity {

    //Toolbar
    private Toolbar mToolbar;

    //Navigation View
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private MenuItem mPreviousMenuItem;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up the toolbar
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Set up the navigation view
        initNavigationView();

        //Set up the list fragment
        initShoppingListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;

        //Toolbar handlers
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void initShoppingListFragment(){
        if(findViewById(R.id.content_frame) != null){
            //Create a new fragment
            ShoppingListFragment fragment = new ShoppingListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame,fragment,ShoppingListFragment.TAG);
            transaction.addToBackStack(ShoppingListFragment.TAG);
            transaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }

    private void initNavigationView(){
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView)findViewById(R.id.nav_drawer);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                if(mPreviousMenuItem != null)
                    mPreviousMenuItem.setChecked(false);
                mPreviousMenuItem = menuItem;
                selectDrawerItem(menuItem);
                return true;
            }
        });
        mNavigationView.getMenu().getItem(0).setChecked(true);
        mPreviousMenuItem = mNavigationView.getMenu().getItem(0);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close);
    }

    private void selectDrawerItem(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.nav_drawer_shoppinglist){
            //initShoppingLIst
        }
        else if(id == R.id.nav_drawer_quit){
            finish();
        }
        menuItem.setCheckable(true);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }
}
