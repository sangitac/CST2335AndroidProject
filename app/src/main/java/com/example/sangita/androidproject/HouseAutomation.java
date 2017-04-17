package com.example.sangita.androidproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HouseAutomation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String ACTIVITY_NAME = "HouseAutomation";
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_automation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getFloatingActionButton();
        getHouseImage();

//        ImageView houseImage = (ImageView) findViewById(R.id.house_Image);
//        houseImage.setImageResource(R.drawable.home_automation);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }

    public ImageView getHouseImage(){
        ImageView houseImage = (ImageView)findViewById(R.id.house_Image);
        houseImage.setImageResource(R.drawable.home_automation);
        return houseImage;
    }

    public FloatingActionButton getFloatingActionButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HouseAutomation.this, Welcome.class);
                startActivity(intent);

                //Snackbar.make(view, "You are in House Automation, Please select a service from menu", Snackbar.LENGTH_LONG)
                     // .setAction("Action", null).show();

            }
        });
        return fab;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.house_automation, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_setting);
            dialog.setTitle("Setting Instruction");

            TextView help_head = (TextView) dialog.findViewById(R.id.helpHeader);
            help_head.setText(R.string.setting_header);

            TextView help_body = (TextView) dialog.findViewById(R.id.helpBody);
            help_body.setText(R.string.setting_body);

            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            return true;

        }
        //==========================for menu on every page ========================
        switch (item.getItemId()) {

            case R.id.action_house:
                Log.d(ACTIVITY_NAME, "House Selected");
                //show a snackbar
                Snackbar snack1 = Snackbar.make(findViewById(android.R.id.content),
                        (message.equals("") ? "House Automation selected": message),
                        Snackbar.LENGTH_SHORT);
                snack1.show();
                startActivity(new Intent(this, HouseAutomation.class));
                return true;

            case R.id.action_kitchen:
                Log.d(ACTIVITY_NAME, "Kitchen Selected");

                Snackbar snack2 = Snackbar.make(findViewById(android.R.id.content),
                        (message.equals("") ? "Kitchen Activity selected": message),
                        Snackbar.LENGTH_SHORT);
                snack2.show();
                return true;

            case R.id.action_livingroom:
                Log.d(ACTIVITY_NAME, "Livingroom Selected");

                Snackbar snack3 = Snackbar.make(findViewById(android.R.id.content),
                        (message.equals("") ? "Livingroom Activity selected": message),
                        Snackbar.LENGTH_SHORT);
                snack3.show();
                return true;

            case R.id.action_automobile:
                Log.d(ACTIVITY_NAME, "Automobile Selected");

                Snackbar snack4 = Snackbar.make(findViewById(android.R.id.content),
                        (message.equals("") ? "Automobile Activity selected": message),
                        Snackbar.LENGTH_SHORT);
                snack4.show();
                return true;

            case R.id.action_about:
                Log.d(ACTIVITY_NAME, "About selected");
                Toast toast = Toast.makeText(this, "V 1.0 by Sangita Chowdhury", Toast.LENGTH_LONG);
                toast.show();
                return true;
        }
        //===========================================end of  menu on every page menu======================================
        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id){
        Fragment fragment = null;
        switch(id){
            case R.id.nav_garage:
                fragment = new GarageFragment();
                break;

            case R.id.nav_indoortemperature:
                fragment = new IndoorTempFragment();
                break;

            case R.id.nav_outsideweather:
                fragment = new OutdoorWeatherFragment();
                break;

        }
        if ( fragment !=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_house_automation,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_garage) {

        } else if (id == R.id.nav_indoortemperature) {

        } else if (id == R.id.nav_outsideweather) {

        }

        displaySelectedScreen(id);
        return true;
    }

    @Override
    public  void onResume(){
        super.onResume();

    }
}





