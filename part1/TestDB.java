import java.io.IOException;
import java.util.Scanner;  // Import the Scanner class

//-----------------------------------------------------
// Example code to read from fixed length records (random access file)
//-----------------------------------------------------


//Notes: I decided to use a scanner to read the input from the user.
public class TestDB {

  static Record record;
  static DB db;   
  private static boolean isOpen = false;
  




public static void main(String[] args) throws IOException {

  Scanner userInput = new Scanner(System.in);  // Create a Scanner object
  String input;
  while(true){

    System.out.println("1) Create new database\n2) Open database \n3) Close database \n4) Read Record \n5)Display record \n6) Update record \n7) Create report \n8) Add record \n9) Delete record \n10) Quit");
    input = userInput.nextLine();
    // calls constructor
    switch (input){
      case "1":
        db = new DB();  
        System.out.println("Which file would you like to create new database from?\n");
        input = userInput.nextLine();
       // String work = input;
        // creates database from csv file 
        db.createDB(input);
        System.out.println("Database object created.");
        break;
      case "2":
        if(isOpen){
          System.out.println("Sorry, you already have an open Database, you'll have to close that first\n");
        }
        else{
          System.out.println("What would you like to open?\n");
          input = userInput.nextLine();
          db.open(input);
          isOpen = true;
        }
        break;

      case "3":
        if (db == null) {
          System.out.println("Error: No database to close.");
          break;
        }
        db.close();
        isOpen = false;
        System.out.println("Database closed.");
        break;

      case "4":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
      }
        System.out.println("What record number would you like?\n");
        input = userInput.nextLine();
        System.out.println(db.readRecord(Integer.parseInt(input)));
      break;
      case "5":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
        }
        //displayRecord();
        break;

      case "6":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
        }
        // updateRecord();
        break;

      case "10":
        System.out.println("Exiting...");
        userInput.close();
        return;

      default:
        System.out.println("Invalid option. Please try again.");
        break;
    // Close database
    }
  }
  }
}
