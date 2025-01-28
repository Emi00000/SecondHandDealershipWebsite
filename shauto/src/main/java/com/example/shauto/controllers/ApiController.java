package com.example.shauto.controllers;

import com.example.shauto.models.Car;
import com.example.shauto.models.User;
import com.example.shauto.services.CarService;
import com.example.shauto.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carService.getAllAvailableCars();
    }

    @GetMapping("/cars/{id}")
    public Optional<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    @PostMapping("/cars/buy/{id}")
    public String purchaseCar(@PathVariable Long id, @RequestParam String username) {
        return carService.purchaseCar(id, username);
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        boolean isAuthenticated = userService.authenticate(user.getUsername(), user.getPassword());
        System.out.println(user.getPassword());
        System.out.println(user.getUsername());
        if (isAuthenticated) {
            return "Login successful";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
    @GetMapping("/user/details")
    public User getUserDetails(@RequestParam String username) {
        return userService.getUserDetails(username);
    }

    @PostMapping("/logout")
    public String logout(@RequestParam String username) {
        // Handle user logout (e.g., invalidate session/token)
        return "Logout successful";
    }
}
