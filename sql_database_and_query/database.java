/*
 *  DO:  more $HOME/.my.cnf to see your MySQL username and  password
 *  CHANGE:  MYUSERNAME  and   MYMYSQLPASSWORD  in the main function of
 *  this program to your username and mysql password 
 *  MAKE SURE that you download mysql-connector-java-5.1.40-bin.jar from this assignment description on the class website
 *  COMPILE AND RUN: 
 *  javac *.java
 *  java -cp .:mysql-connector-java-5.1.40-bin.jar jdbc_example
 *  */

import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class database {

    // The instance variables for the class
    private Connection connection;
    private Statement statement;

    // The constructor for the class
    public database() {
        connection = null;
        statement = null;
    }

    // The main program", that tests the methods
    public static void main(String[] args) throws SQLException {
        String Username = "snorden"; // Change to your own username
        String mysqlPassword = "Ighei6ae"; // Change to your own mysql Password
        String input;
        String bookstore;
        String bookstoreCity;
        String bookName;
        String copyID;
        Scanner userInput = new Scanner(System.in);
        // Create a jdbc_example instance called test
       // jdbc_example test = new jdbc_example();
        //jdbc_example test = new Database();
        database test = new database();

        // Connect and set up the database for use
        test.initDatabase(Username, mysqlPassword);
        //main loop
        while (true){
            System.out.println("1) Find all available copies at a given bookstore\n2) Purchase an available copy from a particular bookstore\n3) List all purchases for a particular bookstore\n4) Cancel a purchase\n5) Add a new book for a bookstore\n6) Quit");
            input = userInput.nextLine();
        
            switch(input){
                case "1":
                    System.out.println("What bookstore would you like to search?");
                    bookstore = userInput.nextLine();
                    System.out.println("Which city is the bookstore located in?");
                    bookstoreCity = userInput.nextLine();
                    System.out.println("Finding all copies from " + bookstore + " located in " + bookstoreCity + "...");

                    test.getCopies(bookstore, bookstoreCity);

                    break;
                case "2":
                    System.out.println("What book would you like to purchase?");
                    bookName = userInput.nextLine();
                    if(test.getBookName(bookName) == true)
                    {
                        System.out.println("Which copyID would you like to order?");
                        copyID = userInput.nextLine();
                        test.purchaseCopy(copyID);
                    }
                    
                    break;
                case "3":
                    System.out.println("Case 3");
                    System.out.println("What bookstore would you like to search?");
                    bookstore = userInput.nextLine();

                    System.out.println("Which city is the bookstore located in?");
                    bookstoreCity = userInput.nextLine();

                    System.out.println("Pulling all purchases from " + bookstore + " located in " + bookstoreCity + "...");
                    test.getPurchases(bookstore, bookstoreCity);

                    break;
                case "4":
                    System.out.println("Case 4");
                    test.displayPurchases();
                    System.out.println("What purchase would you like to cancel?");
                    input = userInput.nextLine();
                    test.cancelPurchase(input);
                    break;
                case "5":
                    System.out.println("Case 5");
                    System.out.println("What bookstore would you like to search?");
                    bookstore = userInput.nextLine();

                    System.out.println("Which city is the bookstore located in?");
                    bookstoreCity = userInput.nextLine();
                    int num = test.checkValidStore(bookstore, bookstoreCity);
                    if(num != -1){
                        String author;
                        String date;
                        String genre;
                        String cost;
                        System.out.println("What is the name of the book?");
                        bookName =  userInput.nextLine();
                        System.out.println("What is the name of the author?");
                        author =  userInput.nextLine();
                        System.out.println("What is the date of publication?");
                        date =  userInput.nextLine();
                        System.out.println("What type of book is it?");
                        genre =  userInput.nextLine();
                        System.out.println("What is the price of the book?");
                        cost =  userInput.nextLine();
                        test.addBook(num, bookName, author, date, genre, cost);
                    }
                    break;
                case "6":
                    System.out.println("Exiting...");
                    test.disConnect();
                    userInput.close();
                    return;
                case "7":
                    test.displayAll();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        
    }

    // Connect to the database
    public void connect(String Username, String mysqlPassword) throws SQLException {
        try {
            String url = "jdbc:mysql://localhost/" + Username + "?useSSL=false";
            System.out.println(url);
            connection = DriverManager.getConnection(url, Username, mysqlPassword);
        } catch (Exception e) {
            throw e;
        }
    }

    // Disconnect from the database
    public void disConnect() throws SQLException {
        connection.close();
        statement.close();
    }

    // Execute an SQL query passed in as a String parameter
    // and print the resulting relation
    public boolean query(String q) {
        boolean returnQuery = false;
        try {
            ResultSet resultSet = statement.executeQuery(q);
            System.out.println("---------------------------------");
            System.out.println("Query: \n" + q + "\n\nResult: ");
            //print(resultSet);
            
            if (!resultSet.isBeforeFirst()) {
                // No rows returned
                returnQuery = false;
                System.out.println("No rows detected");
            } else {
                print(resultSet);
                System.out.println("Rows detected");
                returnQuery = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnQuery;
    }
    public void getCopies(String bookstore, String bookstoreCity) 
    {
        bookstore = bookstore.replace("'", "''");
        bookstoreCity = bookstoreCity.replace("'", "''");
        String queryStr = "SELECT bookName, price FROM Copy NATURAL JOIN Bookstore NATURAL JOIN Book WHERE bookstoreName = '" + bookstore + "' AND city = '" + bookstoreCity + "';";
        if (this.query(queryStr) == true){
            System.out.println("Query complete");
        }
        else{
            System.out.println("Bookstore at location not found");
        }
    }

    public boolean getBookName(String bookName) 
    {
        String queryStr = "SELECT bookName, copyID, bookstoreName, city, price FROM Copy NATURAL JOIN Bookstore NATURAL JOIN Book WHERE bookName = '" + bookName + "';";
        if (this.query(queryStr) == true){
            System.out.println("Query complete");
            return true;
        }
        else{
            System.out.println("This book is not available");
            return false;
        }
    }
    public boolean getPurchases(String bookstore, String bookstoreCity){
        
        bookstore = bookstore.replace("'", "''");
        bookstoreCity = bookstoreCity.replace("'", "''");
        String queryStr = "SELECT Purchase.* FROM Copy NATURAL JOIN Purchase NATURAL JOIN Bookstore WHERE bookstoreName = '" + bookstore + "' AND city = '" + bookstoreCity + "';";
        if (this.query(queryStr) == true){
            System.out.println("Query complete");
            return true;
        }
        else{
            System.out.println("Bookstore at location not found");
            return false;
        }
    }
    public void displayPurchases(){
        String queryStr = "SELECT * FROM Purchase;";
        this.query(queryStr);
    }
    public boolean cancelPurchase(String num){
        num = num.replace("'", "''");
        //System.out.println("you made it inside of cancel purchase");
        String queryStr = "DELETE FROM Purchase WHERE purchaseID = " + num + ";";
        try {
            int rowsAffected = statement.executeUpdate(queryStr);
            if (rowsAffected > 0) {
                System.out.println("Purchase canceled successfully.");
                return true;
            } else {
                System.out.println("No purchase with that ID was found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Print the results of a query with attribute names on the first line
    // Followed by the tuples, one per line
    public void print(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int numColumns = metaData.getColumnCount();

        printHeader(metaData, numColumns);
        printRecords(resultSet, numColumns);
    }

    // Print the attribute names
    public void printHeader(ResultSetMetaData metaData, int numColumns) throws SQLException {
        for (int i = 1; i <= numColumns; i++) {
            if (i > 1)
                System.out.print(",  ");
            System.out.print(metaData.getColumnName(i));
        }
        System.out.println();
    }

    // Print the attribute values for all tuples in the result
    public void printRecords(ResultSet resultSet, int numColumns) throws SQLException {
        String columnValue;
        while (resultSet.next()) {
            for (int i = 1; i <= numColumns; i++) {
                if (i > 1)
                    System.out.print(",  ");
                columnValue = resultSet.getString(i);
                System.out.print(columnValue);
            }
            System.out.println("");
        }
    }

    // Insert into any table, any values from data passed in as String parameters
    public void insert(String table, String values) {
        String q = "INSERT into " + table + " values (" + values + ")";
        try {
            statement.executeUpdate(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int checkValidStore(String storename, String city){
        storename = storename.replace("'", "''");
        city = city.replace("'", "''");
        String queryStr = "SELECT bookstoreID FROM Bookstore WHERE bookstoreName = '" + storename + "' AND city = '" + city + "';";
    
        // if (this.query(queryStr)) {
        //     System.out.println("Store found.");
        //     return this.query(queryStr);
        // } else {
        //     System.out.println("No matching bookstore found.");
        //     return -1;
        // }
    try {
        ResultSet resultSet = statement.executeQuery(queryStr);

        if (resultSet.next()) {
            int bookstoreID = resultSet.getInt("bookstoreID");
            System.out.println("Store found: ID = " + bookstoreID);
            return bookstoreID;
        } else {
            System.out.println("No matching bookstore found.");
            return -1;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return -1;
    }
    }
    public void addBook(int storeNum, String name, String author, String date, String genre, String cost){
        //String queryStr = "INSERT INTO Book (bookName,author,publicationDate,type) VALUES(9,'Brave New World','Aldous Huxley',STR_To_DATE('02,04,1932','%m,%d,%Y'),'fic');";
           try {
        // Convert input string to a Date object
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM,dd,yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = inputFormat.parse(date); // Convert string to Date
        String formattedDate = outputFormat.format(parsedDate); // Format as "yyyy-MM-dd"
        String values = "'" + (this.getMaxBookID() + 1) + "', '" + name + "', '" + author + "', '" + formattedDate + "', '" + genre + "'";
        this.insert("Book", values);

        // Insert into Copy table if book was successfully added
        int checknsee = this.getBookID(name);
        if (checknsee != -1) {
            values = "'" + (this.getMaxCopyID() + 1) + "', '" + storeNum + "', '" + this.getMaxBookID() + "', '" + cost + "'";
            this.insert("Copy", values);
        } else {
            System.out.println("Failed to insert into Copy");
        }
    } catch (ParseException e) {
        System.out.println("Invalid date format. Please enter the date in MM,DD,YYYY format.");
        e.printStackTrace();
    }
    }
    public int getMaxBookID() {
        int maxBookID = -1; 
    
        String queryStr = "SELECT MAX(bookID) AS maxBookID FROM Book;";
        
        try {
            ResultSet resultSet = statement.executeQuery(queryStr);
            
            if (resultSet.next()) {
                maxBookID = resultSet.getInt("maxBookID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return maxBookID;
    }
    public int getMaxCopyID() {
        int maxCopyID = -1; 
    
        String queryStr = "SELECT MAX(CopyID) AS maxCopyID FROM Copy;";
        
        try {
            ResultSet resultSet = statement.executeQuery(queryStr);
            
            if (resultSet.next()) {
                maxCopyID = resultSet.getInt("maxCopyID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return maxCopyID;
    }
    public void displayAll(){
        
        String queryStr = "SELECT * FROM Book;";
        query(queryStr);
        queryStr = "SELECT * FROM Bookstore;";
        query(queryStr);
        queryStr = "SELECT * FROM Copy;";
        query(queryStr);
        queryStr = "SELECT * FROM Purchase;";
        query(queryStr);

    }
    public int getBookID(String bookName) {
        bookName = bookName.replace("'", "''");
    
        String queryStr = "SELECT bookID FROM Book WHERE bookName = '" + bookName + "';";
    
        try {
            ResultSet resultSet = statement.executeQuery(queryStr);
    
            if (resultSet.next()) {
                int bookID = resultSet.getInt("bookID");
                return bookID;
            } else {
                System.out.println("Book not found, something aint right");
                return -1;
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void purchaseCopy(String copyID) {
        try {
            String queryStr = "SELECT copyID FROM Purchase WHERE copyID = '" + copyID + "';";
            ResultSet resultSet = statement.executeQuery(queryStr);
            if (!resultSet.isBeforeFirst()) {
                // No rows returned

                queryStr = "SELECT copyID FROM Purchase WHERE copyID = '" + copyID + "';";
                resultSet = statement.executeQuery(queryStr);
                if (!resultSet.isBeforeFirst() == false) {
                    System.out.println("That copy is not available for purchase");
                    return;
                }
                queryStr = "SELECT copyID FROM Copy WHERE copyID = '" + copyID + "';";
                resultSet = statement.executeQuery(queryStr);
                if (!resultSet.isBeforeFirst() == false) {
                    System.out.println("That Copy is not available");
                    return;
                }
                Date currentDate = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(currentDate);

                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTime = currentDateTime.format(formatter1);

                ResultSet rs = statement.executeQuery("SELECT COUNT(*) FROM Purchase;");
                rs.next();
                int count = rs.getInt(1);
                String purchaseIDString = Integer.toString(count);

                String values = "'" + purchaseIDString + "', '" + copyID + "', '" + formattedDate + "', '" + formattedTime + "'";
                this.insert("Purchase", values);

                System.out.println("Purchase successful!");
                
            } else {
                System.out.println("This book has already been purchased");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // init and testing
    // Assumes that the tables are already created
    public void initDatabase(String Username, String Password) throws SQLException {

        connect(Username, Password);
        // create a statement to hold mysql queries
        statement = connection.createStatement();
    }
}
