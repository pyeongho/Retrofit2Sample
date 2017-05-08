package com.example.phkim.mycleanmvp.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.phkim.mycleanmvp.Injection;
import com.example.phkim.mycleanmvp.R;
import com.example.phkim.mycleanmvp.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {
    private SearchPresenter mSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchFragment searchFragment =
                (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (searchFragment == null) {
            // Create the fragment
            searchFragment = SearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), searchFragment, R.id.contentFrame);
        }

        // Create the presenter
        mSearchPresenter = new SearchPresenter(
                Injection.provideUseCaseHandler(),
                searchFragment,
                Injection.provideGetSearch(getApplicationContext())
        );

    }
}
