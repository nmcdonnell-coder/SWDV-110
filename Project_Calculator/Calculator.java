package Project_Calculator;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please tell me your first number: ");
        String firstInput = scanner.nextLine();
        System.out.print("Please tell me your second number: ");
        String secondInput = scanner.nextLine();
    try {
        double firstNumber = Double.parseDouble(firstInput);
        double secondNumber = Double.parseDouble(secondInput);
            double sum = firstNumber + secondNumber;
            double difference = firstNumber - secondNumber;
            double product = firstNumber * secondNumber; 
            double quotient;
                if (secondNumber != 0) {
                quotient = firstNumber / secondNumber;
                } else {
                quotient = Double.NaN;
                System.out.println("Sorry, but I can't divide anything by zero.");
                }   
                ArrayList<Double> results = new ArrayList<>();
                results.add(sum);
                results.add(difference);
                results.add(product);
                results.add(quotient);                 
                    System.out.println("\nCalculation results:");
                    String[] operationNames = {
                        "Addition", "Subtraction", "Multiplication", "Division"
                    };
                for (int i = 0; i < results.size(); i++) {
                    System.out.println(operationNames[i] + ": " + results.get(i));
                }
    try (FileWriter writer = new FileWriter("Project_Calculator/results_from_calculations.txt", true)) {
        writer.write("Calculation: " + firstNumber + " and " + secondNumber + "\n");
        for (int i = 0; i < results.size(); i++) {
            writer.write(operationNames[i] + ": " + results.get(i) + "\n");
        }
        writer.write("--------------------\n");
        System.out.println("Transactions saved to file.");
    } catch (IOException e) {
        System.out.println("Error writing transactions to file.");
    }
        System.out.println("Calculations were completed successfully!");
    } catch (NumberFormatException e) {
        System.out.println("There was an error during calculations; please enter valid numbers and try again.");
        }          
        scanner.close();
    }
}
