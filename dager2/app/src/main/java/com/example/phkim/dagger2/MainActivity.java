package com.example.phkim.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SampleComponent component = DaggerSampleComponent.builder()
                .sampleModule(new SampleModule())
                .build();

        component.inject(this);

        Log.d("MainActivity", owner.getPetName());

    }


}
