package com.example.sangita.androidproject;


import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class IndoorTempFragment extends Fragment {
    protected static final String ACTIVITY_NAME= "Indoor Temperature";
    protected  static EditText inputTime;
    protected  static EditText  inputTemp;

    protected ListView tempListview;
    protected Button tempScheduleButton;
    protected ArrayList<String> tempScheduleList;
    protected TempAdapter<String> temperatureAdapter;
    protected TempDatabaseHelper tempDbHelper ;

    protected SQLiteDatabase db;

    Cursor cursor;
    private long idToDelete;
    private int positionID;

    public IndoorTempFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Indoor Temparature");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indoor_temp, container, false);


        //Hide the image from fragment
        ImageView houseImage = ((HouseAutomation)getActivity()).getHouseImage();
        houseImage.setVisibility(View.GONE);

        Toast toast = Toast.makeText(getActivity(), "You are in Temperature Setting", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 200);
        toast.show();

        inputTime = (EditText) view.findViewById(R.id.input_time);
        inputTemp = (EditText) view.findViewById(R.id.input_temp);
        tempScheduleList = new ArrayList<String>();
        tempListview = (ListView) view.findViewById(R.id.temperature_Listview);
        tempListview .setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        tempScheduleButton = (Button)view. findViewById(R.id.save_temp_Button);

        /*
        create the database
         */
          tempDbHelper = new TempDatabaseHelper(getActivity());
          db =   tempDbHelper .getWritableDatabase();


        /*
         After opening the database, execute a query for any existing temperature settings and
         add them into the ArrayList of schedules
         */

        cursor = db.query( TempDatabaseHelper.TABLE_NAME,
                new String[] { TempDatabaseHelper.KEY_ID,TempDatabaseHelper.DATE_TIME,TempDatabaseHelper.TEMPERATURE},
                null, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast() ){
            String schedule = cursor.getString(cursor.getColumnIndex(TempDatabaseHelper.DATE_TIME))+"     "
                    + cursor.getString(cursor.getColumnIndex(TempDatabaseHelper.TEMPERATURE))+"C\u00b0";
            tempScheduleList.add(schedule);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + schedule);
            cursor.moveToNext();
        }
       // cursor.close();

          temperatureAdapter = new TempAdapter(getActivity());
          tempListview.setAdapter(temperatureAdapter);

        tempScheduleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User wants to save temperature setting");

                String inTime = inputTime.getText().toString().trim();
                String inTemp = inputTemp.getText().toString().trim();

                if(!isValidTemp(inTemp)) {
                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getContext());
                    builder1.setMessage("Please enter temperature value between 10 ~ 40");
                    builder1.setCancelable(true);
                    android.app.AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else {
                    tempScheduleList.add(inTime + "  " + inTemp + "\u2103");


                    //save message to database
                    ContentValues values = new ContentValues();
                    values.put(TempDatabaseHelper.DATE_TIME, inTime);
                    values.put(TempDatabaseHelper.TEMPERATURE, inTemp);
                    db.insert(TempDatabaseHelper.TABLE_NAME, null, values);

                    Toast.makeText(getActivity(), "Temperature Schedule saved!", Toast.LENGTH_LONG).show();

                    temperatureAdapter.notifyDataSetChanged();

                    // make chat window blank after temperature setting added
                    inputTime.setText("");
                    inputTemp.setText("");
                }
            }
        });


        tempListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(
                        getActivity(),
                        tempScheduleList.get(position).toString(),
                        Toast.LENGTH_LONG).show();

                idToDelete = id;
                positionID = position;

                //  IndoorTempFragment.this.position = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage("Do you want to delete this temperature setting?") //Add a dialog message to strings.xml
                        .setTitle("Delete temperature setting")
                        .setMessage( tempScheduleList.get(position).toString())
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                removeItem( positionID);
                                //onResume();
                            }

                        })
                        .setNegativeButton("no", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });


        inputTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showTimePickerDialog(v);
                showDatePickerDialog(v);
            }
        });

        NumberPicker numberPicker = (NumberPicker)view.findViewById(R.id.numberpicker);
        numberPicker.setMaxValue(40);
        numberPicker.setMinValue(10);
        numberPicker.setWrapSelectorWheel(true);
        //numberPicker.setFocusable(true);
        //numberPicker.setFocusableInTouchMode(true);
        numberPicker.setOnValueChangedListener( new NumberPicker.
                OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                inputTemp.setText("" +newVal);
            }
        });

        return view;
    }

    private void removeItem(int position) {
        db.delete(TempDatabaseHelper.TABLE_NAME, TempDatabaseHelper.KEY_ID + "=" + String.valueOf(idToDelete), null);
        tempScheduleList.remove(positionID);
        temperatureAdapter.notifyDataSetChanged();
    }

    public long getItemId(int position) {
        cursor.moveToPosition(position);
        return cursor.getLong(cursor.getColumnIndex(TempDatabaseHelper.KEY_ID));
    }

    public void updateList(){
        temperatureAdapter.notifyDataSetChanged();
    }

    //    public void reload()
//    {
//        tempDbHelper = new TempDatabaseHelper(getActivity());
//        db =   tempDbHelper .getWritableDatabase();
//        temperatureAdapter = new TempAdapter(getActivity());
//        tempListview.setAdapter(temperatureAdapter);
//        if(temperatureAdapter!= null)
//            temperatureAdapter.notifyDataSetChanged();
//    }


    private boolean isValidTemp(String s) {
        try {
            int value = Integer.parseInt(s);
            if (value >= 10 && value <= 40) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }





    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
           //return new DatePickerDialog(getActivity(), this, year, month, day);
            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            // DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
            pickerDialog.getDatePicker().setMinDate(new Date().getTime());
            return pickerDialog;
            }

        public void onDateSet(DatePicker view, int year, int month, int day) {

            // Do something with the date chosen by the user
            inputTime.setText(day + "/" + (month + 1) + "/" + year);

        }
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }


    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            // Create a new instance of TimePickerDialog and return it
           // return new TimePickerDialog(getActivity(), this, hour, minute,
            //DateFormat.is24HourFormat(getActivity()));

            TimePickerDialog pickerDialog = new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
            return pickerDialog;
        }
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            // Do something with the time chosen by the user
            Calendar current = Calendar.getInstance();
            if((hourOfDay <= (current.get(Calendar.HOUR_OF_DAY)))&&
                    ((minute <= (current.get(Calendar.MINUTE)))||(minute > (current.get(Calendar.MINUTE))))){
                Toast.makeText(getActivity(), "Wrong hour",
                        Toast.LENGTH_SHORT).show();
            }else {
                inputTime.setText(inputTime.getText() + " - " + hourOfDay + ":" + minute);
            }
        }
    }


    public class TempAdapter<String> extends ArrayAdapter {
        private Context context;

        TempAdapter(Context context) {
            super(context, 0);
            this.context = getActivity();
        }

        public long getItemId(int position)
        {
            cursor.moveToPosition(position); return cursor.getLong(cursor.getColumnIndex(TempDatabaseHelper.KEY_ID));
        }

        public int getCount() {
            return tempScheduleList.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return (String) tempScheduleList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);

            View result = null;
            result = inflater.inflate(R.layout.temp_list_row, null);

            TextView tempSchedule = (TextView) result.findViewById(R.id.temperature_text);
            tempSchedule.setText(getItem(position).toString()); // get the string at position

            return result;

        }
    }
    @Override
    public  void onResume(){
        super.onResume();
//       IndoorTempFragment fragment = (IndoorTempFragment) getFragmentManager()
//                .findFragmentById(R.id.content_indoor_temperature);
//        if (fragment != null) {
//            fragment.reload();
//            if (temperatureAdapter != null)
//                temperatureAdapter.notifyDataSetChanged();
//        }
    }



    public void onDestroy() {
        super.onDestroy();
        tempDbHelper.close();
    }
}

