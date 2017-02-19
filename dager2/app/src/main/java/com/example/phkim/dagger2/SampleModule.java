package com.example.phkim.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by pyeonghokim on 2017. 2. 13..
 */


@Module
public class SampleModule {

    @Provides
    Pet providePet() {
        return new Dog();
    }
}
