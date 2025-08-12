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

    System.out.println("1) Create new database\n2) Open database \n3) Close database \n4) Read Record \n5) Display record \n6) Update record \n7) Create report \n8) Delete record \n9) Add record \n10) Quit");
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
        break;
      case "2":
        if (db == null){
          System.out.println("Please open a database first (Option 1)");
        }
        else{
          if(isOpen){
            System.out.println("Sorry, you already have an open Database, you'll have to close that first\n");
          }
          else{
            System.out.println("What would you like to open?\n");
            input = userInput.nextLine();
            db.open(input);
            isOpen = true;
          }
        }

        break;

      case "3":
        if (db == null) {
          System.out.println("Error: No database to close.");
          break;
        }
        if(isOpen == false){
          System.out.println("No Database to close. Please open one");
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
        if (isOpen == false){
          System.out.println("Please open a database");
          break;
        }
        System.out.println("What record number would you like?\n");
        input = userInput.nextLine();
        System.out.println(db.readRecord(Integer.parseInt(input)));
      break;
      case "5":
        if(db == null){
          System.out.println("No database open. Please open one");
          break;
        }
        if(isOpen == true){
          System.out.println("What record number would you like to display?\n");
          input = userInput.nextLine();
          db.displayRecord(input);
        }
        else{
          System.out.println("Please open a database first\n");

        }
        break;

      case "6":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
        }
        if(isOpen == false){
          System.out.println("Please open a database (Option 2)");
          break;
        }
        System.out.println("What is the ID number: ");
        String idnum = userInput.nextLine();
        System.out.println("Which feild would you like to update?\n1) State\n2) City\n 3) Name");
        String inputt = userInput.nextLine();
        System.out.println("eneter the new value: ");
        String newvalue = userInput.nextLine();
        db.updateRecord(idnum, inputt, newvalue);
        break;
      case "7":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
        }
        if(isOpen == false){
          System.out.println("Please open a database (Option 2)");
          break;
        }
        db.createReport();
        break;
        case "8":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
        }
        if(isOpen == false){
          System.out.println("Please open a database (Option 2)");
          break;
        }
        System.out.println("Please give me the ID of the record you'd like to delete: ");
        input = userInput.nextLine();
        if(db.deleteRecord(input) == true){
          System.out.println("Successfully deleted record.");
        }
        else{
          System.out.println("Record not deleted, see above error message.");
        }
        break;
        case "9":
        if (db == null) {
          System.out.println("Error: No database object found. Open the database first (Option 2).");
          break;
        }
        if(isOpen == false){
          System.out.println("Please open a database (Option 2)");
          break;
        }
        db.addRecord();
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
