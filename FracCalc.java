// Bella Vasilescu
// Period 6
// Fraction Calculator Project

import java.util.*;

// TODO: Description of what this program does goes here.
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

      int improperNumOp1 = improperNumerator(op1Num,op1Den, op1Whole);; // Convert op1 to improper fraction
      int improperNumOp2 = improperNumerator(op2Num,op2Den, op2Whole);; // Convert op2 to improper fraction
      int improperDen = op1Den * op2Den; // Get common denom
      System.out.println(improperDen);
      System.out.println(op1Den);
      System.out.println(op1Num);
      
      improperNumOp1 *= op2Den; // change num to match common denom
      improperNumOp2 *= op1Den; // change num to match common denom

      int improperNumResult = 0;

      improperNumResult = performOperation(operator, improperNumOp1, improperNumOp2, improperDen); //Perform operation

      // if multiplication/ division
      if(operator.equals("*")){
         improperDen *= improperDen;
      } else if(operator.equals("/")){
         improperDen *= improperNumOp2;
      }

      String result = "";
      result = reduceFraction(improperNumResult, improperDen); // Reduce result
      return result;
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

   public static int whole = 0; // Placed here for easier understanding of parseOperand
   public static int num = 0;
   public static int denom = 0;

   public static void parseOperand(String operandString){
      
      //Check for indexes of '_' and "/"
      int underIndex = operandString.indexOf('_');
      int fracIndex = operandString.indexOf('/');

      
      if(underIndex != -1 && fracIndex != -1){ //If both exist...
         String wholeString = operandString.substring(0, underIndex);
         whole = Integer.parseInt(wholeString);
         String numString = operandString.substring(underIndex + 1, fracIndex);
         String denomString = operandString.substring(fracIndex + 1);
         num = Integer.parseInt(numString);
         denom = Integer.parseInt(denomString);
      } else if(underIndex == -1 && fracIndex != -1){ //If only '/' exists...
         whole = 0;
         String numString = operandString.substring(underIndex + 1, fracIndex);
         String denomString = operandString.substring(fracIndex + 1);
         num = Integer.parseInt(numString);
         denom = Integer.parseInt(denomString);
      } else { //If none exist...
         whole = Integer.parseInt(operandString);
         num = 0;
         denom = 1;
      }
      
      removeDoubleNeg();
   }

   public static void removeDoubleNeg(){
      if(num < 0 && denom < 0){
         num = Math.abs(num);
         denom = Math.abs(denom);
      } else if(num > 0 && denom < 0){
         num = num * -1;
         denom = Math.abs(denom);
      }
   }

   public static int performOperation(String operator, int improperNumOp1, int improperNumOp2, int improperDen){
      if(operator.equals("+")){
         int improperNumResult = improperNumOp1 + improperNumOp2;
         System.out.println("performs addition"); //DELETE LATER!
         System.out.println("improperNumResult: " + improperNumResult); //DELETE LATER!
         return improperNumResult;
      } else if(operator.equals("-")){
         int improperNumResult = improperNumOp1 - improperNumOp2;
         System.out.println("performs subtraction"); //DELETE LATER!
         System.out.println("improperNumResult: " + improperNumResult); //DELETE LATER!
         return improperNumResult;
      } else if(operator.equals("*")){
         int improperNumResult = improperNumOp1 * improperNumOp2;
         System.out.println("performs multiplication"); //DELETE LATER!
         System.out.println("improperNumResult: " + improperNumResult); //DELETE LATER!
         return improperNumResult;
      } else{
         // DIVISION is like MULTPLYING by the RECIPROCAL
         int improperNumResult = improperNumOp1 * improperDen;
         System.out.println("performs division"); //DELETE LATER!
         System.out.println("improperNumResult: " + improperNumResult); //DELETE LATER!
         return improperNumResult;
      }
   }

   public static int GCD(int a, int b){ 
      //Uses Euclid’s Division Algorithm
      a = Math.abs(a);
      b = Math.abs(b);
      if(a < b){ //Switches to make 'a' the larger value for method
         int temp = a;
         a = b;
         b = temp;
      }

      int gcd = b; // assumes b is gcd

      if(gcd == 0){
         return 1; //MEANS NO COMMON DIVISORS, SO GCD CANT BE USED
      } else{
         int remainder = a % b;
         while(remainder != 0){
            a = b;
            b = remainder;
            gcd = b;
            remainder = a % b;
         }
         System.out.println("gcd: " + gcd); //DELETE LATER!
         return gcd;
      }
   }

   
   public static int improperNumerator(int operatorNum, int operatorDen, int operatorWhole){
      int improperNum;
      if(operatorNum == 0){
         improperNum = operatorWhole;
      } 
      if(operatorWhole < 0){
         improperNum = operatorWhole * operatorDen - operatorNum;
      } else{
         improperNum = operatorWhole * operatorDen + operatorNum;
      }
      
      return improperNum;
   }
   
   
   public static String reduceFraction(int improperNumResult, int improperDen){
      String result = "";
      if(improperDen == 1){
         result = improperNumResult + ""; // If only whole nums
      } else{
         if(improperNumResult == 0){
            result = 0 + "";
         } else{
           //result = improperNumResult + "/" + improperDen; //If fractions?

         int gcd = GCD(improperNumResult, improperDen); // Get Greastest Common Denominator/Divisor
         
         improperNumResult /= gcd; //Simplify the result fraction
         improperDen /= gcd;
         int newWhole = 0;
         System.out.println(improperNumResult); //DELETE

         if(improperNumResult < 0 && improperDen < 0){
            improperNumResult = Math.abs(improperNumResult); //CHECK FUNCTIONALITY!!!!
            improperDen = Math.abs(improperDen);
            while(improperNumResult >= improperDen){
               newWhole++;
               improperNumResult -= improperDen;  // Check order of these
            }
         } else if(improperNumResult < 0){
            improperNumResult = Math.abs(improperNumResult);
            while(improperNumResult >= improperDen){
               newWhole++;
               improperNumResult -= improperDen;  // Check order of these
            }
            newWhole *= -1;
         } else{
            while(improperNumResult >= improperDen){
               newWhole++;
               improperNumResult -= improperDen;  // Check order of these
            }
         }

         System.out.println(improperNumResult); //DELETE

         if(newWhole == 0 ){
            result = improperNumResult + "/" + improperDen;
            System.out.println("result:" + result); // DELETE
         } else if(improperNumResult == 0){
            result = newWhole + "";
            System.out.println("result:" + result); // DELETE
         } else {
            result = newWhole + " " + improperNumResult + "/" + improperDen;
            System.out.println("result:" + result); // DELETE
         }
         
      }
   }

   System.out.println("result final:" + result);
   return result;

   }

   /*
   public static String mixedFraction(){
   
      // ex. use 9/2
      // 9-2 = 7   --> 1 7/2
      // 7-2 = 5   --> 1+1 = 2 5/2
      // 5-2 = 3   --> 1+1+1 = 3 3/2
      // 3-2 = 1   --> 1+1+1+1 = 4 1/2
      // 1 not greater than 2, so end loop
      // mixed = newWhole" "Num"/"Den

      int whole = 0;
         while(num >= den){
            whole++;
            num -= den;
         }
   
   }
   
   */
}