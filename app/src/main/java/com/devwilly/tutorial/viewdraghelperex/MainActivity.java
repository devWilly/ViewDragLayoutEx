package com.devwilly.tutorial.viewdraghelperex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        FirstVerticalFragment firstFragment = new FirstVerticalFragment();
        SecondVerticalFragment secondFragment = new SecondVerticalFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.first_fragment_view, firstFragment)
                .add(R.id.second_fragment_view, secondFragment).commit();
    }
}
