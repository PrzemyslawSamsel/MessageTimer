package com.example.messagetimer;


import android.Manifest;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment
{
    // False - setTimer || True  - setClock
    private boolean setTimerOrClock = true;

    //Dialog handler to choose time for sending messasge
    private TimePickerDialog timePickerDialog;

    //Buttons and textView handlers - app logic
    private Button time_set;
    private Button send_message;
    private TextView phoneNumberView;
    private TextView messageView;


    //Timer variables - state and it's current time
    private boolean isTimerRunning = false;
    private long TIMEOUT;
    private String hms;

    private View inflatedView;

    //For saving state of text message
    private String messageText;
    private String messageNumber;

    @Override
    public void onPause()
    {
        super.onPause();
        EditText textMessageEdit = (EditText) inflatedView.findViewById(R.id.message_text);
        EditText numberEdit = (EditText) inflatedView.findViewById(R.id.phone_number);

        MainActivity.messageContent = textMessageEdit.getText().toString();
        MainActivity.messageNumber  = numberEdit.getText().toString();
    }

    public MainFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        //Setting up inflater for the fragment and button for adding templates
        this.inflatedView = inflater.inflate(R.layout.fragment_main, container, false);

        // Inflate the layout for this fragment
        return inflatedView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EditText textMessageEdit = (EditText) inflatedView.findViewById(R.id.message_text);
        EditText numberEdit = (EditText) inflatedView.findViewById(R.id.phone_number);

        //Getting saved state
        if (null != savedInstanceState)
        {
            messageText = savedInstanceState.getString("messageText");
            messageNumber = savedInstanceState.getString("messageNumber");
            textMessageEdit.setText(messageText);
            numberEdit.setText(messageNumber);
        }
        if (null != MainActivity.messageContent)
        {
            textMessageEdit.setText(MainActivity.messageContent);
        }
        if (null != MainActivity.messageNumber)
        {

            numberEdit.setText(MainActivity.messageNumber);
        }

        // Setting buttons handlers
        time_set = (Button) inflatedView.findViewById(R.id.set_time);
        send_message = (Button) inflatedView.findViewById(R.id.send);
        RadioGroup groupRadio = (RadioGroup) inflatedView.findViewById(R.id.radio_group);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                //Allows user to switch between clock and timer modes
                    RadioGroup radioGroup = inflatedView.findViewById(R.id.radio_group);
                    int id = radioGroup.getCheckedRadioButtonId();

                    switch(id)
                    {
                        case R.id.radio_setclock:
                            setTimerOrClock = true;
                            break;
                        case R.id.radiosettimer:
                            setTimerOrClock = false;
                            break;
                    }
            }
        });

        // perform click event listener on set time button
        time_set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                TIMEOUT = 0;
                time_set.setText(getResources().getString(R.string.empty_timer));
                isTimerRunning = false;
                openTimePickerDialog();
            }

        });

        //perform click event listener on send message button
        send_message.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Toast.makeText(getActivity(), "This is my Toast message!",
                //        Toast.LENGTH_LONG).show();

                if (ContextCompat.checkSelfPermission(inflatedView.getContext(), Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED)
                {
                    if (false == isTimerRunning)
                    {
                        isTimerRunning = true;
                        runTimer();
                    }
                    else
                    {
                        isTimerRunning = !isTimerRunning;
                    }
                }
            }
        });


    }

    private void runTimer()
    {

        final Handler handler = new Handler();

        handler.post(new Runnable()
        {

            @Override
            public void run()
            {
                if (isTimerRunning)
                {
                    TIMEOUT--;

                    long hours = TimeUnit.SECONDS.toHours(TIMEOUT);
                    long minutes = TimeUnit.SECONDS.toMinutes(TIMEOUT);

                    hms = String.format(getResources().getString(R.string.hmsFormat), hours, minutes - TimeUnit.HOURS.toMinutes(hours), TIMEOUT%60);

                    if(TIMEOUT == 0)
                    {
                        isTimerRunning = false;
                        onTimerStop();
                    }

                    time_set.setText(hms);
                    handler.postDelayed(this, 1000);
                }
                else
                {
                    handler.removeCallbacks(this);
                }
            }
        });

    }

    private void openTimePickerDialog()
    {
        int hour,minute;

        // If user choosed setting clock mode:
        if (true == setTimerOrClock)
        {
            //We need to know exact time
            Calendar mcurrentTime = Calendar.getInstance();
            hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            minute = mcurrentTime.get(Calendar.MINUTE);
        }
        else
        {
            //If it's timer - then just set time to 0:0
            hour = 0;
            minute = 0;
        }

        // Create dialog that will ask user to specify time
        timePickerDialog = new TimePickerDialog
            (
                    inflatedView.getContext(),
                onTimeSetListener,
                hour,
                minute,
                true
            );

        // Set title for the dialog
        if (true == setTimerOrClock)
            timePickerDialog.setTitle(getResources().getString(R.string.Clock));
        else
            timePickerDialog.setTitle(getResources().getString(R.string.Timer));

        timePickerDialog.show();
    }

    //Creates listener that will be executed when user picks the time on the dialog
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
    {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute)
        {
            long millis;

            // If user choosed setting clock mode:
            if (true == setTimerOrClock)
            {
                //Get exact time for setting it on a time_set button
                Calendar calendar = Calendar.getInstance();
                int hourNow = calendar.get(Calendar.HOUR_OF_DAY);
                int minuteNow = calendar.get(Calendar.MINUTE);

                minute = minute - minuteNow;
                hour = hour - hourNow;
            }

                TIMEOUT = TimeUnit.HOURS.toSeconds(hour) + TimeUnit.MINUTES.toSeconds(minute);
        }

    };

    public void onTimerStop()
    {
        String phoneNr = phoneNumberView.getText().toString();
        String message = messageView.getText().toString();

        SmsManager.getDefault().sendTextMessage(phoneNr, null, message, null, null);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if("" != MainActivity.templateString)
        {
            EditText textMessageEdit = (EditText) inflatedView.findViewById(R.id.message_text);
            textMessageEdit.setText(MainActivity.templateString);
            MainActivity.templateString = "";
        }

    }

    //If the MainActivity is newly created, use this method do display chosen fragment
    @Override
    public void onSaveInstanceState(Bundle OutState)
    {
        super.onSaveInstanceState(OutState);
        OutState.putString("messageText", messageText);
        OutState.putString("messageNumber",messageNumber);
    }

}
