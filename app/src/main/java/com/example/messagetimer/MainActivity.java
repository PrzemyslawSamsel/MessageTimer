package com.example.messagetimer;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    //For share action in ActionBar
    private ShareActionProvider shareActionProvider;

    //Application Drawer settings
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;

    //Allows to listen to DrawerLayout events like normal DrawerListener
    //And also allows opening and closing the drawer
    private ActionBarDrawerToggle drawerToggle;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            //Makes chosen by position fragment visible
            itemSelection(position);

            //Sets title for action bar
            setActionBarTitle(position);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles = getResources().getStringArray(R.array.drawer_options);
        drawerList = findViewById(R.id.drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        drawerList.setAdapter(new ArrayAdapter<String>
                (
                        this,
                        android.R.layout.simple_list_item_activated_1,
                        titles
                ));

        //Add an instance of OnItemClickListener to the drawer's ListView
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (null == savedInstanceState)
        {
            itemSelection(0);
        }

        //Create the ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer) {
            //Called when a drawer has settled in a completely closed state
            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

        };


        drawerLayout.addDrawerListener(drawerToggle);
        //Enabling UP icon so that it can be used by action bar drawer toggle
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }


    //Adds any items to the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate the menu - adds items to the action bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Creating action share menu handler
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        //TODO: Pass here something more intrresting
        setIntent("This is a sample text");
        return super.onCreateOptionsMenu(menu);
    }

    //Creates intent and passes it to shareActionProvider
    private void setIntent(String text)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    //Argument to this function is handler of item that was selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //When actionBarDrawer Toggle is clicked
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        if (R.id.action_about == id)
        {
            //Starting new activity when about button was clicked
            Intent intentToStartAbout = new Intent(this, AboutActivity.class);
            startActivity(intentToStartAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Called after invalidateOptionsMenu()
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        boolean isDrawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!isDrawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    //Sync the state of the ActionBarDrawerToggle with the state of the drawer
    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    //Pass any configuration changes to the ActionBarDrawerToggle
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        //Pass any configuration changes to the action bar drawer toggle
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    //Function called when option in drawer is clicked - it creates and
    //sets as visible fragment choosed by user
    private void itemSelection(int position)
    {
        //Update main content by replacing fragments
        //currentPosition = position;

        Fragment fragment;

        switch (position)
        {
            case 1:
                fragment = new TemplatesFragment();
                break;
            case 2:
                fragment = new SendOrderFragment();
                break;
            default:
                fragment = new MainFragment();
                break;
        }



        android.support.v4.app.FragmentTransaction drawerFragmentTransaction = getSupportFragmentManager().beginTransaction();
        //Replace current transaction with new fragment
        drawerFragmentTransaction.replace(R.id.content_frame, fragment, "visible_fragment");
        //Add it to back stack so that when user clicks back button it wiil get user back to it
        drawerFragmentTransaction.addToBackStack(null);
        //Set transition for this fragment transaction and commit changes
        drawerFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        drawerFragmentTransaction.commit();

        //Set Action Bar title accordingly to chosen fragment
        setActionBarTitle(position);

        //Close the drawer
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(drawerList);


    }

    //Setting Action Bar title according to which Fragment is currently active
    private void setActionBarTitle(int position)
    {
        String title;
        //0 means MainFragment
        if (0 == position)
        {
            title = getResources().getString(R.string.app_name);
        }
        //In other cases refer to the table of titles of Fragments
        else
        {
            title = titles[position];
        }

        //Set choosed title
        getSupportActionBar().setTitle(title);
    }


    //If the MainActivity is newly created, use this method do display chosen fragment
//    @Override
//    public void onSaveInstanceState(Bundle OutState)
//    {
//        itemSelection(0);
//        super.onSaveInstanceState(OutState);
//        //OutState.putInt("position", currentPosition);
//    }


}
