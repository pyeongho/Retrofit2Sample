package com.example.phkim.dagger2;

import javax.inject.Inject;

/**
 * Created by pyeonghokim on 2017. 2. 13..
 */
public class Owner {

    private Pet pet;

    @Inject
    public Owner(Pet pet) {
        this.pet = pet;
    }

    public String getPetName() {
        return pet.getName();
    }
}