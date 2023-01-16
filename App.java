package src.shoppingcart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
                    cartItems = readCartItemsFromFile(dirPath, fileName);
                    listCart(cartItems);
                    break;
                case "users":
                    listUsers(dirPath);
                    break;
                default:
            }

            if (input.startsWith("login")) {
                fileName = createLoginFile(input, dirPath, fileName);
            }

            String strValue = "";
            if (input.startsWith("add")) {
                input = input.replace(',', ' ');
                Scanner scanner = new Scanner(input.substring(4));

                FileWriter fw = new FileWriter(dirPath + File.separator + fileName);
                PrintWriter pw = new PrintWriter(fw);

                while (scanner.hasNext()) {
                    strValue = scanner.next();
                    cartItems.add(strValue);

                    pw.printf("%s\n", strValue);
                }

                pw.flush(); 
                fw.flush();
                pw.close();
                fw.close();
            }
            if (input.startsWith("delete")) {
                cartItems = deleteCartItem(cartItems, input);
            }

        }
    }


    // Methods
    public static void listUsers(String dirPath) {
        File directoryPath = new File(dirPath);

        String contents[] = directoryPath.list();
        for (String fileNames : contents) {
            displayMessage(fileNames);
        }
    }

    public static String createLoginFile(String input, String dirPath, String fileName) throws IOException {
        input = input.replace(',', ' ');
        Scanner scanner = new Scanner(input.substring(6));

        while (scanner.hasNext()) {
            fileName = scanner.next();
        }

        File loginFile = new File(dirPath + File.separator + fileName); // file.separator = backslash \
        boolean isCreated = loginFile.createNewFile();

        if (isCreated) {
            displayMessage("New file created successfully " + loginFile.getCanonicalFile());
        } else {
            displayMessage("File already created.");
        }
        return fileName;
    }

    public static void updateCartItemToFile(List<String> cartItems, String dirPath, String fileName) throws IOException{
        FileWriter fw = new FileWriter(dirPath + File.separator + fileName, false); //append set to false to overwrite
        BufferedWriter bw = new BufferedWriter(fw);
        
        int listCount = 0;
        while (listCount < cartItems.size()) {
            bw.write(cartItems.get(listCount));
            bw.newLine();
            listCount++;
        }
        bw.flush();
        fw.flush();
        bw.close();
        fw.close();
    }

    public static List<String> deleteCartItem(List<String> cartItems, String input) {
        String strValue = "";
        Scanner scanner = new Scanner(input.substring(6));

        while (scanner.hasNext()) {
            strValue = scanner.next();
            int listItemIndex = Integer.parseInt(strValue);

            if (listItemIndex < cartItems.size()) {
                cartItems.remove(listItemIndex);
            } else {
                displayMessage("Incorrect item index");
            }
        }
        return cartItems;
    }

    public static List<String> readCartItemsFromFile(String dirPath, String fileName) throws FileNotFoundException, IOException {
        
        List<String> items = new ArrayList<String>();
        File file = new File(dirPath + File.separator + fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String sr;
        while ((sr = br.readLine()) != null) {
            items.add(sr);
        }
        br.close();
        return items;
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
