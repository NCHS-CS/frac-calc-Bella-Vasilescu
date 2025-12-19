// Bella Vasilescu
// Period 6
// Fraction Calculator Project

import java.util.*;

// This program performs arithmetic calculations (+,-,*,/) on whole
// numbers, fractions, and mixed fractions. The program will parse
// a user's input, perform an operation, remove double negatives and
// return the result as a formatted String. For example;
//       "3/4 + 1/2" returns "1 1/4"
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      System.out.println("Enter: ");
      String userLine = console.nextLine();
      return userLine;

   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4
   public static String processExpression(String input) {
      Scanner parser = new Scanner(input); // Use Scanner to parse the 3 tokens of
      String operand1 = parser.next();     // operands 1 and 2 and the operator
      String operator = parser.next();
      String operand2 = parser.next(); 

      parser.close(); // Closes parser to remove warning

      parseOperand(operand1); //Split the first operand into the following:
      int op1Whole = whole;   
      int op1Num = num;
      int op1Den = denom;

      parseOperand(operand2); //Split the second operand into the following:
      int op2Whole = whole;
      int op2Num = num;
      int op2Den = denom;

      if((op1Den == 0) || (op2Den == 0)){
         System.out.println("Invalid expression! Please try again");
         return "";
      } else{
         

      int improperNumOp1 = convertToImproper(op1Num,op1Den, op1Whole); // Converts operator1 to improper fraction form
      int improperNumOp2 = convertToImproper(op2Num,op2Den, op2Whole); // Converts operator2 to improper fraction form
      
      int improperDen = op1Den * op2Den; // Get the common denom
      
      improperNumOp1 *= op2Den; // Convert fraction (num) to match common denom
      improperNumOp2 *= op1Den; // Convert fraction (num) to match common denom

      int improperNumResult = 0;

      improperNumResult = performOperation(operator, improperNumOp1, improperNumOp2, improperDen); //Performs specified operation

      if(operator.equals("*")){         // if operation was multiplication,
         improperDen *= improperDen;              // quare the denominator
      } else if(operator.equals("/")){  // if operation was division,
         if(improperNumOp2 == 0){
            System.out.println("Invalid expression! Please try again");
            return "";
         } else{
            improperDen *= improperNumOp2;           // multiply with reciprocal
         }
      }
      
      String result = "";
      result = reduceFraction(improperNumResult, improperDen); // Reduce/simplify result
      
      return result;
      
      }
   }
   
   
   
   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      String help = "This program performs arithmetic (+,-,/,*) operations.\n";
      help += "Enter your expression in the format \"number operator number\" or \"fraction operator fraction\".\n";
      help += "For example, 1 + 2, 1 + 1/2, or 2/3 + 4/5\n";
      help += "To create mixed fractions, place a '_' where there is a space. For example, 1_1/2 (3/2).";
      
      return help;
   }

   public static int whole = 0; // variables used for parseOperand
   public static int num = 0;
   public static int denom = 0;

   // Method to parse an operand into three components; 
   // whole, numerator, and denominator
   // Input: the operand (String)
   // Return: None
   public static void parseOperand(String operandString){
      
      int underIndex = operandString.indexOf('_'); //Check for indexes of '_' and "/"
      int fracIndex = operandString.indexOf('/');

      
      if(underIndex != -1 && fracIndex != -1){ //If both characters exist...
         String wholeString = operandString.substring(0, underIndex); // Parse and assign to global variables
         whole = Integer.parseInt(wholeString);                                   // Includes whole
         String numString = operandString.substring(underIndex + 1, fracIndex);
         String denomString = operandString.substring(fracIndex + 1);
         num = Integer.parseInt(numString);
         denom = Integer.parseInt(denomString);
      } else if(underIndex == -1 && fracIndex != -1){ //If only '/' exists...
         whole = 0;
         String numString = operandString.substring(underIndex + 1, fracIndex); // No whole number
         String denomString = operandString.substring(fracIndex + 1);           
         num = Integer.parseInt(numString);
         denom = Integer.parseInt(denomString);
      } else { //If none exist...
         whole = Integer.parseInt(operandString); // Only a whole number
         num = 0;
         denom = 1;
      }
      
      removeDoubleNeg(); // Remove any double negatives (-1/-4)
   }

   // Method to remove any double negatives (-num/-den)
   // and moves a negative to the num (if num/-den). Uses
   // global variables whole, num, and den
   // Input: None
   // Return: None
   public static void removeDoubleNeg(){
      if(num < 0 && denom < 0){
         num = Math.abs(num);
         denom = Math.abs(denom);
      } else if(num > 0 && denom < 0){
         num = num * -1;
         denom = Math.abs(denom);
      }
   }

   // Method to perform each operation. First determines the
   // operator, then performs operation on the nums
   // Input: operator (String), num1 (int), num2 (int), den (int)
   // Return: numResult (int)
   public static int performOperation(String operator, int num1, int num2, int den){
      if(operator.equals("+")){ // determine which operator to perform
         int numResult = num1 + num2;    // find new num result
         return numResult;
      } else if(operator.equals("-")){
         int numResult = num1 - num2;
         return numResult;
      } else if(operator.equals("*")){
         int numResult = num1 * num2;
         return numResult;
      } else{
         int numResult = num1 * den; // Division is like multiplying by the reciprocal
         return numResult;
      }
   }

   // Method to find the GCD of 2 numbers
   // Input: a (int), b (int)
   // Return: gcd (int)
   public static int GCD(int a, int b){ //Uses Euclid’s Division Algorithm
      a = Math.abs(a);
      b = Math.abs(b);
      if(a < b){ //Switches to make 'a' the larger value for method
         int temp = a;
         a = b;
         b = temp;
      }

      int gcd = b; // assumes b is gcd

      if(gcd == 0){
         return 1; //means no common divisors, so GCD can't be used
      } else{
         int remainder = a % b;  //Find GCD using Euclid's method
         while(remainder != 0){
            a = b;
            b = remainder;
            gcd = b;
            remainder = a % b;
         }
         return gcd;
      }
   }

   // Method to convert a mixed fraction to an improper fraction's num
   // only affects the numerator
   // Input: num (int), den (int), whole (int)
   // Return: improperNum (int)
   public static int convertToImproper(int num, int den, int whole){
      int improperNum;
      if(num == 0){                      // If num is 0
         improperNum = whole;            // the new num is the whole
      } 
      if(whole < 0){                     // If the whole is negative, whole * den - num
         improperNum = whole * den - num;// ex. -5 1/2 --> -5 * 2 - 1 --> -11 (-11/2)
      } else{                            // If whole is positive and num isn't 0
         improperNum = whole * den + num;// ex. 5 1/2  --> 5 * 2 + 1 --->  11  (11/2)
      }
      return improperNum;
   }
   
   // Method to simplify and reduce an improper fraction
   // into its mixed form; Also works for cases including
   // only whole numbers and regular fractions (ex. 1/2)
   // Input: num (int), den (int)
   // Return: result (String)
   public static String reduceFraction(int num, int den){
      String result = "";
      if(den == 1){         // If the fraction's den is 1,
         result = num + ""; // then the num becomes a whole number
      } else{
         if(num == 0){       // If the num is 0,
            result = 0 + ""; // then the fraction is 0
         } else{

         int gcd = GCD(num, den); // Get Greastest Common Divisor
         
         num /= gcd; //Simplify the fraction's num
         den /= gcd; //Simplify the fraction's num

         int newWhole = 0;
         if(num < 0 && den < 0){
            num = Math.abs(num); 
            den = Math.abs(den);
            while(num >= den){
               newWhole++;
               num -= den;
            }
         } else if(num < 0){     // if only the num is negative
            num = Math.abs(num); // get abs. value of num
            while(num >= den){   // convert to mixed fraction
               newWhole++;
               num -= den; 
            }
                                 //Then...
            if(newWhole != 0){   // If the fraction was a mixed fraction
               newWhole *= -1;   // multiply the whole by -1 to match negative num
            } else{              // If the fraction was NOT mixed
               num *= -1;        // return the num to be negative
            }
         } else if(den < 0){     //If only the den is negative
            num = Math.abs(num); // get abs. val of both num and den
            den = Math.abs(den); 
            while(num >= den){   // Convert to mixed fraction
               newWhole++;
               num -= den;  
            }
            num *= -1;           // Make the num negative (instead of den for formatting)
         } else{                 // If neither num or den are negative
            while(num >= den){   // Convert to mixed fracion
               newWhole++;
               num -= den;
            }
         }

         if(newWhole == 0 ){            // If fraction was not mixed
            result = num + "/" + den;   // result is simple fraction (ex. 1/2)
         } else if(num == 0){           // If num is 0
            result = newWhole + "";     // result is only the new whole (ex. 2 0/2 --> 2)
         } else {                       // Else use mixed fraction format (ex. 3 1/2)
            result = newWhole + " " + num + "/" + den;
         }
         
      }
   }
   return result;
   }
}