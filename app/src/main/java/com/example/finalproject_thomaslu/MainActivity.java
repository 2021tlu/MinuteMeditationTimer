package com.example.finalproject_thomaslu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Fragment-related variables
    FragmentTransaction fragmentTransaction;
    Landing landingFragment; // for landing page
    Main mainFragment; // for main (timer) page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate fragment variables
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        landingFragment = new Landing();
        mainFragment = new Main();

        // Set to landing fragment on start-up
        fragmentTransaction.add(R.id.fragment_container, landingFragment);
        fragmentTransaction.commit();

        // Finally, changing app name so Mr. Tra won't be mad at me :)
        getActionBar().setTitle("Minute Meditation Timer");
    }

    // Button Click Methods

    // Landing: Switch to main page; pass name from editText
    public void clickContinue(View v){
        if(landingFragment.getName().equals("")){
            Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_LONG).show();
        }
        else {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.replace(R.id.fragment_container, mainFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            mainFragment.setName(landingFragment.getName());
        }
    }

    // Main: Switch to landing page
    public void clickSwitch(View v){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, landingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Main: Toggle Timer on/off
    public void clickTimer(View v){
        mainFragment.timerPress();
    }
}