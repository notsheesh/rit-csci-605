/*
 * In the provided code, both the generic and wildcard approaches yield the same 
 * result, but there is a subtle difference in how the type information is managed. 
 * Let's highlight the contrast in how they handle type information:
 * **Using Generics:**
 * ```java
 * NumberProcessor<Number> genericProcessor = new NumberProcessor<>();
 * double genericResult = genericProcessor.calculateSum(mixedNumbers);
 * ```
 * 
 * In the case of generics, you need to create an instance of the `NumberProcessor` 
 * class with a specific type parameter (`Number`). This means you need to specify 
 * the type in advance when creating the processor instance. Here, we used `<Number>` 
 * to indicate that the processor works with numbers of type `Number`. This provides 
 * strong type checking but requires you to specify the type explicitly.
 * 
 * **Using Wildcards:**
 * 
 * ```java
 * WildcardNumberProcessor wildcardProcessor = new WildcardNumberProcessor();
 * double wildcardResult = wildcardProcessor.calculateSum(mixedNumbers);
 * ```
 * 
 * With wildcards, you create an instance of `WildcardNumberProcessor` without 
 * specifying a specific type parameter. This is because the `calculateSum` method 
 * uses a wildcard (`? extends Number`) in its parameter, allowing it to accept lists 
 * of any type that extends `Number`. You don't need to specify the exact type when 
 * creating the `WildcardNumberProcessor` instance, providing more flexibility in terms 
 * of what kind of numbers it can process.
 * 
 * In summary, the key difference here is that with generics, you need to specify 
 * the type explicitly when creating the processor instance, while with wildcards, 
 * you don't need to specify the type, making the code more flexible and accommodating 
 * of various numeric types without requiring explicit type declarations during instance creation.
 * 
 * With wildcards, you can create more flexible code that can accept lists with 
 * elements of types that are within the bounds specified by the wildcard. You 
 * don't need to explicitly specify the data type when creating the instance of 
 * the class that uses wildcards.
 * 
 * In contrast, with generics, you have to specify the data type explicitly when 
 * creating the instance, which means you are telling the code to work with a 
 * specific type. This provides strong type checking and enforcement, but it can 
 * be less flexible when you want to work with various types.
 * 
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberProcessor<T extends Number> {
    public double calculateSum(List<T> numbers) {
        double sum = 0.0;
        for (T number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }
}

public class WildcardNumberProcessor {
    public double calculateSum(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }
        return sum;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Number> mixedNumbers = Arrays.asList(1, 2, 3, 4.0, 5.0);
        
        NumberProcessor<Number> genericProcessor = new NumberProcessor<>();
        double genericResult = genericProcessor.calculateSum(mixedNumbers);
        System.out.println("Using Generics: " + genericResult); // This will output 15.0
        
        WildcardNumberProcessor wildcardProcessor = new WildcardNumberProcessor();
        double wildcardResult = wildcardProcessor.calculateSum(mixedNumbers);
        System.out.println("Using Wildcards: " + wildcardResult); // This will output 15.0
    }
}
