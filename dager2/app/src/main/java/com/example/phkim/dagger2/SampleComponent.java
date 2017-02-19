package com.example.phkim.dagger2;

import dagger.Component;

/**
 * Created by pyeonghokim on 2017. 2. 13..
 */
@Component(modules = SampleModule.class)
public interface SampleComponent {
    void inject(MainActivity activity);
}