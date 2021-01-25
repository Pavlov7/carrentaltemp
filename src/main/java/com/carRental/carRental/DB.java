package com.carRental.carRental;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DB {
    private Map<String, User> userList;
    private Map<String, Car> carList;

    private final String NO_CAR_EXISTS = "No car exists with that id!";
    private final String NO_USER_EXISTS = "No user exists with that id!";
    private final String USER_WITH_SAME_ID_EXISTS = "An user with the same ID already exists!";
    private final String CAR_WITH_SAME_ID_EXISTS = "A car with the same ID already exists!";
    private final String USER_CANT_RENT_MULTIPLE_CARS = "User can't rent more than one car!";
    private final String CAR_ALREADY_RENTED = "Car has already been rented to another User!";
    private final String CAR_CANT_BE_NULL = "Car cannot be null";


    public DB() {
        this.carList = new HashMap<>();
        this.userList = new HashMap<>();
    }

    public User getUser(String id) {
        if (!userList.containsKey(id)) throw new IllegalArgumentException(NO_USER_EXISTS);
        return userList.get(id);
    }

    public Car getCar(String id) {
        if (!carList.containsKey(id)) throw new IllegalArgumentException(NO_CAR_EXISTS);
        return carList.get(id);
    }

    public void addUser(User user) {
        if (userList.containsKey(user.getId()))
            throw new UnsupportedOperationException(USER_WITH_SAME_ID_EXISTS);
        userList.put(user.getId(), user);
    }

    public void replaceUser(User user) {
        if (!userList.containsKey(user.getId())) throw new IllegalArgumentException(NO_USER_EXISTS);
        userList.replace(user.getId(), user);
    }

    public void replaceCar(Car car) {
        if (!carList.containsKey(car.getId())) throw new IllegalArgumentException(NO_CAR_EXISTS);
        carList.replace(car.getId(), car);
    }

    public Collection<User> getAllUsers() {
        return userList.values();
    }

    public Collection<Car> getAllCars() {
        return carList.values();
    }

    public void removeUser(String id) {
        if (!userList.containsKey(id)) throw new IllegalArgumentException(NO_USER_EXISTS);
        userList.remove(id);
    }

    public void removeCar(String id) {
        if (!carList.containsKey(id)) throw new IllegalArgumentException(NO_CAR_EXISTS);
        for (User value : userList.values()) {
            if (value.getRentedCar() != null && value.getRentedCar().getId().equals(id)) {
                value.setRentedCar(null);
            }
        }
        carList.remove(id);
    }


    public void addCar(Car car) {
        if (carList.containsKey(car.getId()))
            throw new UnsupportedOperationException(CAR_WITH_SAME_ID_EXISTS);
        carList.put(car.getId(), car);
    }

    public void rentCar(User user, Car car) {
        if (car == null) throw new IllegalArgumentException(CAR_CANT_BE_NULL);
        if (user.getRentedCar() != null) throw new UnsupportedOperationException(USER_CANT_RENT_MULTIPLE_CARS);
        for (User value : userList.values()) {
            if (value.getRentedCar() != null && value.getRentedCar().equals(car)) {
                throw new UnsupportedOperationException(CAR_ALREADY_RENTED);
            }
        }
        user.setRentedCar(car);
    }


}
