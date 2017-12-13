/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab1.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import pl.polsl.java.lab1.model.Model;
import pl.polsl.java.lab1.model.ModelException;
import pl.polsl.java.lab1.view.View;

/**
 * Main class of application - controls the course of the program CalculatorBCD.
 *
 * @author Łukasz Nowak
 * @version 2.1
 */
public class Controller {

    /**
     * Main method of application - manages the program.
     *
     * @param args the command line arguments - represent two binary numbers
     * and sign of mathematical operation (example: 1001 + 0001)
     */
    public static void main(String[] args){

        //creating model instance
        Model model = new Model();
        
        //creating view instance
        View view = new View();
        
        //creating two variables to represent input numbers
        String firstNumber = new String();
        String secondNumber = new String();
        
        //creting list for data from user
        List<String> inputDataList = new LinkedList();
        
        //creating variable to represent sign of mathematical operation
        char sign = 'x';

        if (args.length != 3) {// wrong number of input parameters
            
            //information for the user
            view.show("Provide data in form 'number sign number':");

            Scanner scanner = new Scanner(System.in);
            
            //additing to the list successive parameters from input
            for (int i = 0; i < 3; i++) {
                inputDataList.add(scanner.next());
            }

            //searching sign of mathematical operation
            for(int i = 0; i < inputDataList.size(); i++){
                if (inputDataList.get(i).equals("+") || inputDataList.get(i).equals("-")) {
                    sign = inputDataList.get(i).charAt(0);
                    inputDataList.remove(i);
                }
            }           
            //loading numbers from input list
            firstNumber = inputDataList.get(0);
            secondNumber = inputDataList.get(1);           

        } else {//correct number of input parameters

            //additing to the list successive parameters from command line
            for (int i = 0; i < 3; i++) {
                inputDataList.add(args[i]);
            }     
            //searching sign of mathematical operation
            for(int i = 0; i <inputDataList.size(); i++){
                if (inputDataList.get(i).equals("+") || inputDataList.get(i).equals("-")) {
                    sign = inputDataList.get(i).charAt(0);
                    inputDataList.remove(i);
                }
            }
            //loading numbers from command line
            firstNumber = inputDataList.get(0);
            secondNumber = inputDataList.get(1);
        }
        //creating calculations map for mathematical operation
        Map<Character, CalculationInterface> calculationMap = new HashMap<>();
        
        //lambda lambda expression for addition
        calculationMap.put('+', (a, b) -> {

            String additionResult = new String();

            try {
                additionResult = model.addition(a, b);
            } catch (ModelException e) {
                view.show(e.getMessage());
            }
            return additionResult;
        });
        //lambda lambda expression for subtraction
        calculationMap.put('-', (a, b) -> {

            String substractionResult = new String();

            try {
                substractionResult = model.subtraction(a, b);
            } catch (ModelException e) {
                view.show(e.getMessage());
            }
            return substractionResult;
        });
        
        
        if (sign == '-' || sign == '+') {//the sign was found

            //performing calculations
            String result = calculationMap.get(sign).calculate(firstNumber, 
                    secondNumber);
            
            //display the result
            view.show("First number:      " + firstNumber + "\n"
                    + "Sign:                " + sign + "\n"
                    + "Second number:     " + secondNumber + "\n"
                    + "RESULT:            " + result);      
            
        } else {//the sign wasn't found
            view.show("Wrong input data!");
        }
    }
}
