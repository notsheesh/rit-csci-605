/*
 *  Problem Description: Imagine you are developing a framework for building 
 *  various types of vehicles, including cars, motorcycles, and bicycles. You 
 * want to create a base structure that contains common attributes and methods 
 * for all vehicles, such as startEngine(), stopEngine(), and accelerate(). 
 * However, you also want to provide some default implementations for these 
 * methods.
 * Why an Abstract Class is More Appropriate:
 * 
 * In this case, an abstract class would be a more suitable choice than an 
 * interface. The reason is that you want to provide some default 
 * implementations for the common methods shared among all vehicles. Abstract 
 * classes can have method implementations, which can be inherited by derived 
 * classes while still allowing those classes to override and customize the 
 * behavior if needed.
 * 
 * You can create an AbstractVehicle class with default implementations of 
 * methods like startEngine() and stopEngine(). Subclasses like Car, Motorcycle, 
 * and Bicycle can extend this abstract class and inherit the default behavior 
 * while also having the flexibility to override methods when necessary. This 
 * approach provides a convenient way to share common code among related classes 
 * while maintaining the option for customization.
 *  
 * Using an Interface for Vehicle Framework:
 * In the vehicle framework scenario, where you have different types of
 * vehicles (e.g., cars, motorcycles, bicycles), using an interface would not be
 * the best choice because:
 * 
 * 1. Code Duplication: When using interfaces, each class implementing the 
 * interface must provide its own implementation for all methods defined in the 
 * interface. In this case, if you have common methods with identical 
 * implementations (e.g., startEngine() and stopEngine()) across different 
 * vehicle types, you would end up duplicating code in each class, leading to 
 * maintenance issues and code redundancy.
 * 
 * 2. No Default Implementations: Interfaces cannot contain method 
 * implementations. Therefore, you cannot provide default behavior for methods 
 * shared among all vehicle types. If you wanted to provide default 
 * implementations for common methods like startEngine() and stopEngine(), you 
 * would need to repeat the same code in each class implementing the interface.
 * 
 * Author: Shreesh Tripathi, Kyle Burke 
 * 
 */

// Abstract class for vehicles with default implementations
abstract class AbstractVehicle {
    public void startEngine() {
        System.out.println("Engine started.");
    }

    public void stopEngine() {
        System.out.println("Engine stopped.");
    }

    abstract void accelerate(); // To be implemented by subclasses
}

// Specific vehicle types by extending the abstract class
class Car extends AbstractVehicle {
    @Override
    void accelerate() {
        System.out.println("Car is accelerating.");
    }
}

class Motorcycle extends AbstractVehicle {
    @Override
    void accelerate() {
        System.out.println("Motorcycle is accelerating.");
    }
}

class Bicycle extends AbstractVehicle {
    @Override
    void accelerate() {
        System.out.println("Bicycle is accelerating.");
    }
}

public class VehicleAbstractClass {
    public static void main(String[] args) {
        // Different types of vehicles
        AbstractVehicle car = new Car();
        AbstractVehicle motorcycle = new Motorcycle();
        AbstractVehicle bicycle = new Bicycle();

        car.startEngine();
        car.accelerate();
        car.stopEngine();

        motorcycle.startEngine();
        motorcycle.accelerate();
        motorcycle.stopEngine();

        bicycle.accelerate();
    }
}
