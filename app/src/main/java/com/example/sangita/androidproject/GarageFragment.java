package com.example.sangita.androidproject;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class GarageFragment extends Fragment {
    private Switch switchDoor;
    private Switch switchLight;
    private ImageView doorImage;
    private ImageView lightImage;
    private ProgressBar progressBar;
    private int progressBarStatus ;
    private Handler progressBarHandler = new Handler();


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
        //progressBar.setProgress(0);
        progressBar.setMax(10);
        progressBar.setVisibility(View.VISIBLE);

        switchDoor.setChecked(false);

        //attach a listener to check for changes in state
        switchDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {




                if (isChecked) {
                    progressBarStatus = 0;
                    new Thread(new Runnable() {
                        public void run() {
                            while (progressBarStatus < 100) {
                                progressBarStatus += 1;
                                progressBarHandler.post(new Runnable() {
                                    public void run() {
                                        progressBar.setProgress(progressBarStatus);
                                    }
                                });
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }).start();
                    if(progressBarStatus == 100) {
                        doorImage.setImageResource(R.drawable.garage_open);
                        lightImage.setImageResource(R.drawable.garage_lighton);
                    }
                }
                else {
                    doorImage.setImageResource(R.drawable.garage_closed);
                    lightImage.setImageResource(R.drawable.garage_lightoff);
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

}
