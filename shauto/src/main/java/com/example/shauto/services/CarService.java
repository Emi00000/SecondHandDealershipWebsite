package com.example.shauto.services;

import com.example.shauto.models.Car;
import com.example.shauto.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllAvailableCars() {
        return carRepository.findBySoldFalse();
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public String purchaseCar(Long id, String username) {
        Optional<Car> carOpt = carRepository.findById(id);
        if (carOpt.isPresent() && !carOpt.get().isSold()) {
            Car car = carOpt.get();
            car.setSold(true);
            car.setBuyer(username);
            carRepository.save(car);
            return "Thank you for your purchase!";
        }
        return "Car not available for purchase anymore.";
    }
}
