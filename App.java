package src.shoppingcart;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    private App() {
    }

    public static void main(String[] args) throws IOException {
        String dirPath = "/Users/andreayong/IBFB2/sdf05/src/shoppingcart/cartdirectory";
        String fileName = "";
        
        File newDirectory = new File(dirPath);

        // Instantiate a file/directory object
        if (newDirectory.exists()) {
            System.out.println("Directory already exists");
        } else {
            newDirectory.mkdir();
        }

        System.out.println("Welcome to My Shopping Cart");

        List<String> cartItems = new ArrayList<String>();

        Console con = System.console();
        String input = "";

        while (!input.equals("quit")) {
            input = con.readLine("What do you want to perform? (Type 'quit' to exit program)");

            switch (input) {
                case "help":
                    displayMessage("'list' to show a list of items in shopping cart");
                    displayMessage("'login <name>' to access items in shopping cart");
                    displayMessage("'add <item>' to add items in shopping cart");
                    displayMessage("'delete <item>' to delete items in shopping cart");
                    displayMessage("'quit' to exit program");
                    break;
                case "list":
                    listCart(cartItems);
                    break;
                case "users":
                    listUsers(dirPath);
                    break;
                default:
            }

            if (input.startsWith("login")) {
                createLoginFile(input, dirPath, fileName);
            }
           


            String strValue = "";
            if (input.startsWith("add")) {
                input = input.replace(',', ' ');
                Scanner scanner = new Scanner(input.substring(4));

                while (scanner.hasNext()) {
                    strValue = scanner.next();
                    cartItems.add(strValue);
                }
            }

            if (input.startsWith("delete")) {
                cartItems = deleteCartItem(cartItems, input);
            }

        }
    }

    public static void listUsers(String dirPath){
        File directoryPath = new File(dirPath);

        String contents[] = directoryPath.list();
        for (String fileNames: contents) {
            displayMessage(fileNames);
        }
    }

    public static void createLoginFile (String input, String dirPath, String fileName) throws IOException{
        input = input.replace(',', ' ');
                Scanner scanner = new Scanner(input.substring(6));

                while(scanner.hasNext()){
                    fileName = scanner.next();
                }

                File loginFile = new File(dirPath + File.separator + fileName); //file.separator = backslash \
                boolean isCreated = loginFile.createNewFile();

                if (isCreated) {
                    displayMessage("New file created successfully " + loginFile.getCanonicalFile());
                } else {
                    displayMessage("File already created.");
                }
    }

    public static List<String> deleteCartItem(List<String> cartItems, String input) {
        String strValue = "";
        Scanner scanner = new Scanner(input.substring(6));

        while(scanner.hasNext()) {
            strValue = scanner.next();
            int listItemIndex = Integer.parseInt(strValue);
                    
            if (listItemIndex < cartItems.size()){
                cartItems.remove(listItemIndex);
            } else {
                displayMessage("Incorrect item index");
            }
        } 
        return cartItems;
}


    public static void listCart(List<String> cartItems) {
        if (cartItems.size() > 0) {
            for (String item : cartItems) {
                System.out.println(item);
            }
        } else {
            displayMessage("Your cart is empty");
        }
    }

    public static void displayMessage(String message) {
        System.out.println(message);
    }

}
