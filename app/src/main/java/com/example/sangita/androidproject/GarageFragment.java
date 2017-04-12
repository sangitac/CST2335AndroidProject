package com.example.sangita.androidproject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 */
public class GarageFragment extends Fragment {
    private Switch switchDoor;
    private Switch switchLight;
    private ImageView doorImage;
    private ImageView lightImage;
    private ProgressBar progressBar;
    private TextView textView;
    //private int count =1;


    public GarageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Garage Automation");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_garage, container, false);

        switchDoor = (Switch) view.findViewById(R.id.garage_door_switch);
        switchLight = (Switch) view.findViewById(R.id.garage_light_switch);
        doorImage = (ImageView) view.findViewById(R.id.garageDoorImage);
        lightImage = (ImageView) view.findViewById(R.id.garageLightImage);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        textView = (TextView)view.findViewById(R.id.output);
//        progressBar.setProgress(0);
//        progressBar.setMax(50);
        //textView.setText("Garage Door Closed");
        switchDoor.setChecked(false);


        //attach a listener to check for changes in state
        switchDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    progressBar.setProgress(0);
                    progressBar.setMax(50);
                    DoorOpenTask doorOpen = new DoorOpenTask ();
                    doorOpen.execute(50);

                }
                else {
                    progressBar.setProgress(0);
                    progressBar.setMax(50);
                    DoorCloseTask doorClose = new DoorCloseTask();
                    doorClose.execute(50);

                }
            }
        });

        switchLight.setChecked(false);
        switchLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    lightImage.setImageResource(R.drawable.garage_lighton);
                } else {
                    lightImage.setImageResource(R.drawable.garage_lightoff);
                }
            }
        });

        return view;
    }
    class DoorOpenTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {

            int count =1;
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(10);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            doorImage.setImageResource(R.drawable.garage_open);
            lightImage.setImageResource(R.drawable.garage_lighton);
            //textView.setText(result);
            textView.setText("Garage Door Open");

        }
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("Door Opening..."+ values[0]);
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }
    class DoorCloseTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            int count =1;
            for (; count <= params[0]; count++) {
                try {
                    Thread.sleep(10);
                    publishProgress(count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.INVISIBLE);
            doorImage.setImageResource(R.drawable.garage_closed);
            lightImage.setImageResource(R.drawable.garage_lightoff);
            //textView.setText(result);
            textView.setText("Garage Door Close");
            // btn.setText("Restart");
        }
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            textView.setText("Door Closing..."+ values[0]);
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }
    }
}




