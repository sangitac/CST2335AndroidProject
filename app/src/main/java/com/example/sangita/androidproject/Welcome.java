package com.example.sangita.androidproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome extends AppCompatActivity {
    private final String ACTIVITY_NAME = "Welcome";
    private String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

            Toast toast = Toast.makeText(this, "Welcome to Automation Application", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 0, 200);
            toast.show();

        ImageView appImage = (ImageView) findViewById(R.id.app_Image);
        appImage.setImageResource(R.drawable.welcome);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please select an activity from menu bar ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.menu_welcome, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {   //when an item was clicked
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_setting);
            dialog.setTitle("Setting Instruction");

            TextView help_head = (TextView) dialog.findViewById(R.id.helpBody);
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
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_house:
                Log.d(ACTIVITY_NAME, "House Selected");
                //show a snackbar
                Snackbar snack1 = Snackbar.make(findViewById(android.R.id.content),
                        (message.equals("") ? "House Automation selected": message),
                        Snackbar.LENGTH_SHORT);
                snack1.show();
                startActivity(new Intent(Welcome.this, HouseAutomation.class));
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
                Toast toast = Toast.makeText(this, "Wecome to Automation Application", Toast.LENGTH_LONG);
                toast.show();
                return true;
        }
        return false;
    }

}