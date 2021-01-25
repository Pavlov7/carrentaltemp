package com.carRental.carRental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @Autowired
    private DB db;

    @GetMapping("/users")
    private Flux<User> getAllUsers() {
        return Flux.fromIterable(db.getAllUsers());
    }

    @PostMapping("/users")
    private Mono<ResponseEntity<String>> createUser(@RequestBody User user) {
        if (user.getRentedCar() != null) {
            db.addCar(user.getRentedCar());
        }
        db.addUser(user);
        return Mono.just(new ResponseEntity<>("User successfully added!", HttpStatus.OK));
    }

    @PutMapping("/users")
    private Mono<ResponseEntity<String>> updateUser(@RequestBody User user) {
        Car rentedCar = db.getUser(user.getId()).getRentedCar();
        db.replaceUser(user);
        if (rentedCar != null) db.rentCar(user, rentedCar);
        return Mono.just(new ResponseEntity<>("User successfully updated!", HttpStatus.OK));
    }

    @PatchMapping("/users/{id}")
    private Mono<ResponseEntity<String>> patchUser(@PathVariable String id, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        User u = db.getUser(id);
        if (firstName != null) u.setFirstName(firstName);
        if (lastName != null) u.setLastName(lastName);
        db.replaceUser(u);
        return Mono.just(new ResponseEntity<>("User successfully updated!", HttpStatus.OK));
    }

    @PostMapping("/users/rent")
    private Mono<ResponseEntity<String>> rentCar(@RequestParam String userId, @RequestParam String carId) {
        db.rentCar(db.getUser(userId), db.getCar(carId));
        return Mono.just(new ResponseEntity<>("Car successfully rented!", HttpStatus.OK));
    }

    @GetMapping("/users/{id}")
    private Mono<User> getUser(@PathVariable String id) {
        return Mono.just(db.getUser(id));
    }

    @DeleteMapping("/users/{id}")
    private Mono<ResponseEntity<String>> deleteUser(@PathVariable String id) {
        db.removeUser(id);
        return Mono.just(new ResponseEntity<>("User removed successfully!", HttpStatus.OK));
    }

    @DeleteMapping("/users/rent/{id}")
    private Mono<ResponseEntity<String>> removeRentedCar(@PathVariable String id) {
        if (db.getUser(id).getRentedCar() == null) throw new IllegalArgumentException("User has not rented a car!");
        db.getUser(id).setRentedCar(null);
        return Mono.just(new ResponseEntity<>("Car successfully returned!", HttpStatus.OK));
    }

    @PatchMapping("/cars/{id}")
    private Mono<ResponseEntity<String>> patchCar(@PathVariable String id, @RequestParam String model) {
        Car c = db.getCar(id);
        c.setModel(model);
        db.replaceCar(c);
        return Mono.just(new ResponseEntity<>("Car successfully updated!", HttpStatus.OK));
    }

    @GetMapping("/cars")
    private Flux<Car> getAllCars() {
        return Flux.fromIterable(db.getAllCars());
    }

    @PostMapping("/cars")
    private Mono<ResponseEntity<String>> createCar(@RequestBody Car car) {
        db.addCar(car);
        return Mono.just(new ResponseEntity<>("Car successfully created!", HttpStatus.OK));
    }

    @GetMapping("/cars/{id}")
    private Mono<Car> getCar(@PathVariable String id) {
        return Mono.just(db.getCar(id));
    }

    @DeleteMapping("/cars/{id}")
    private Mono<ResponseEntity<String>> deleteCar(@PathVariable String id) {
        db.removeCar(id);
        return Mono.just(new ResponseEntity<>("Car removed successfully!", HttpStatus.OK));
    }

    @Bean
    private DB createDB() {
        return new DB();
    }

}
