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
      Scanner parser = new Scanner(input);
      String operand1 = parser.next();
      String operator = parser.next();
      String operand2 = parser.next(); 

      // parser.close(); to remove warning

      parseOperand(operand1);

      int op1Whole = whole;
      int op1Num = num;
      int op1Den = denom;

      parseOperand(operand2);

      int op2Whole = whole;
      int op2Num = num;
      int op2Den = denom;    //12 lines

      // To convert to improper frac:
      // check if num = 0 --> means whole number
      // then do whole / den

      
      int improperDen = op1Den * op2Den; // Get common denom

      int improperNumOp1 = improperNumerator(op1Num,op1Den, op1Whole);;
      int improperNumOp2 = improperNumerator(op2Num,op2Den, op2Whole);;
      
      improperNumOp1 *= op2Den;
      improperNumOp2 *= op1Den;

      int improperNumResult = improperNumOp1 + improperNumOp2; // Addition ONLY

      String result = "";
      if(improperDen == 1){
         result = improperNumResult + "";
      } else{
         result = improperNumResult + "/" + improperDen;

      int gcd = GCD(improperNumResult, improperDen);
     
      //Reduced fraction
      int newNum = improperNumResult / gcd;
      improperDen /= gcd;
      
      int newWhole = 0;
      while(newNum >= improperDen){
         newWhole++;
         newNum -= improperDen;
      }

      if(improperDen == 1){
         if(newWhole == 0 ){
            result = newNum + "";
         } else if(newNum == 0){
            result = newWhole + "";
         } else {
            int sum = newWhole + newNum;
            result = sum + "";
         }
      } else{
         if(newWhole == 0 ){
            result = newNum + "/" + improperDen;
         } else if(newNum == 0){
            result = newWhole + "";
         } else {
            result = newWhole + " " + newNum + "/" + improperDen;
         }
      }
      
      
      }
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

   public static int whole = 0;
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

   public static void performOperation(String operator){





      if(operator.equals("+")){
         //addition
         //performAddition();

         

         /*int op1Whole = whole;
      int op1Num = num;
      int op1Den = denom;

      parseOperand(operand2);

      int op2Whole = whole;
      int op2Num = num;
      int op2Den = denom; */
      } else if(operator.equals("-")){
         //subtract
         //performSubtraction();
      } else if(operator.equals("*")){
         //multiply
         //performMultiplication();
      } else{
         //divide
         //performDivision();
      }
   }

   public static void performAddition(){
      // convert fractions to improper 
      //determine if the denoms are the same
      // if not, correct using :
      // add the numerators
      //
      // return* num and denom
      // reasemble in proccessExpression?
   }

   public static int GCD(int a, int b){ 
      //Uses Euclid’s Division Algorithm
      a = Math.abs(a);
      b = Math.abs(b);
      if(a < b){ //Switches to make a the larger value for method
         int temp = a;
         a = b;
         b = temp;
      }

      int gcd = b;
      int remainder = a % b;
      while(remainder != 0){
         a = b;
         b = remainder;
         gcd = b;
         remainder = a % b;
      }
      
      return gcd;
   }

   
   public static int improperNumerator(int operatorNum, int operatorDen, int operatorWhole){
      int improperNum;
      if(operatorNum == 0){
         improperNum = operatorWhole;
      } else if(operatorWhole < 0){
         improperNum = operatorWhole * operatorDen - operatorNum;
      } else{
         improperNum = operatorWhole * operatorDen + operatorNum;
      }
      
      return improperNum;
   }
   
}

