package com.example.messagetimer;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{
    private ShareActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if(item.getItemId() == R.id.action_about)
        {
            //Starting new activity when about button was clicked
            Intent intentToStartAbout = new Intent(this, AboutActivity.class);
            startActivity(intentToStartAbout);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
