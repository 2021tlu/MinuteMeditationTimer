package com.example.finalproject_thomaslu;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Main extends Fragment {

    // Name and minute display variables
    TextView nameText;
    String name = "";
    TextView minuteText;

    // Timer variables
    TextView timerText;
    Button timerButton;
    CountDownTimer timer;
    Boolean counting = false;

    // Sound variables
    MediaPlayer mediaPlayer;

    // Shared Preferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Main(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // name set-up
        nameText = getView().findViewById(R.id.nameText);
        nameText.setText("Welcome, " + name + ".");
        minuteText = getView().findViewById(R.id.minuteText);
        // show minutes meditated
        updateMinutes(sharedPreferences.getInt(name,0));

        // timer set-up
        timerText = getView().findViewById(R.id.timerText);
        timerText.setText("60");
        timerButton = getView().findViewById(R.id.timerButton);
        timerButton.setText("Start");

        // create mediaPlayer; use same each time
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.birds); // get Context of get activity?
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
    }

    // Called when switch to main fragment
    public void setName(String name){
        this.name = name;
    }

    // Called when timer button is pressed
    public void timerPress(){
        if(counting){
            clearTimer();
        }
        else{
            timerButton.setText("Clear");

            counting = true;

            // start music
            mediaPlayer.start();

            timer = new CountDownTimer(6000, 1000) {
                public void onTick(long millisUntilFinished) {
                    timerText.setText("" + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    timerText.setText("0");

                    // increase minute count
                    updateMinutes(sharedPreferences.getInt(name,-2)+1);

                    // vibration
                    Vibrator v= (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

                    // stop music
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
            }.start();
        }

    }

    private void clearTimer(){
        timerButton.setText("Start");
        timerText.setText("60");
        timer.cancel();
        counting = false;

        // stop music
        mediaPlayer.pause();
        mediaPlayer.seekTo(0); // prepare for next start()
    }

    private void updateMinutes(int min){
        editor.putInt(name, min);
        editor.apply();
        minuteText.setText(min + " minutes meditated.");
    }
}
