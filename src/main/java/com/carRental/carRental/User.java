package com.carRental.carRental;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Setter
@Getter
public class User {
    @NonNull
    private String id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private Car rentedCar;

    public User(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}