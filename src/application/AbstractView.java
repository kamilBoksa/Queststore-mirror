package application;

import java.util.Scanner;
import java.io.IOException;


public abstract class AbstractView {

    protected String emptyLines = "\n\n";
    protected String space = " ";
    private static final String ANSI_CLS = "\u001b[2J";
    private static final String ANSI_HOME = "\u001b[H";

    public void setEmptyLines(String newEmptyLines)
    {
        emptyLines = newEmptyLines;
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayObject(Object object) {
        System.out.println(object.toString());
    }

    public void displayHeaderAndElementsOfCollection(String[] collection, String header) {
        System.out.println(emptyLines + space + header);
        for(String element : collection) {
        System.out.println(space + element);
        }
    }

    public void displayEnumeratedElementsOfCollection(String[] collection) {
        System.out.println(emptyLines);
        int number = 0;
        for(String element : collection){
        System.out.println(space + "[" + number + "] " + element);
        number ++;
        }
    }

    public void displayElementsOfCollection(String[] collection) {
        for(String element : collection){
        System.out.println(element);
        }
    }

    public String getUserInput(String message) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        int minimumUserInputLength = 1;
        while(userInput.length() < minimumUserInputLength){
            System.out.print(message);
            scanner.useDelimiter("\n");
            userInput = scanner.next().trim();
        }
        return userInput;
    }

    public String getNumberFromUser(String message) {
        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        int minimumUserInputLength = 1;
        while(! userInput.matches(".*\\d+.*") && userInput.length() < minimumUserInputLength){
            System.out.print(message);
            scanner.useDelimiter("\n");
            userInput = scanner.next().trim();
            if(! userInput.matches(".*\\d+.*")){
                displayMessage("    - Wrong input (number required)!");
            }
        }
        return userInput;
    }

    public int getNotNegativeNumberFromUser(String message){
        int number = -1;
        while(number < 0) {
            String input = getNumberFromUser(message);
            try {
                number = Integer.parseInt(input);
                if (number < 0) {
                    displayMessage("    - Number shouldn't be negative!");
                }
            } catch (NumberFormatException e){
                displayMessage("    - have You type an integer number?");
                number = -1;
            }
        }
        return number;
    }



    public void clearScreen() {
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print(ANSI_CLS + ANSI_HOME);
                System.out.flush();
                System.out.println();
            }
        } catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void handlePause() {
        System.out.println(emptyLines + space + "Press enter to continue.. ");
        try {
            System.in.read();
        }catch(IOException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public int getUserChoice(String message) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;
        try {
            System.out.println(message);
            input = Integer.parseInt(scanner.nextLine());
        } catch( NumberFormatException e) {
            System.out.println("Wrong input!");
        }
        return input;
    }
}
