import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class addStudent {
   // The main program that inserts a restaurant
    public static void main(String[] args) throws SQLException {
        String Username = "snorden"; // Change to your own username
        String mysqlPassword = "Ighei6ae"; // Change to your own mysql Password

        // Connect to the database
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();





        // For debugging purposes: Show the database before the insert
        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT * from Student";
        // builder.append("<br> Table Student before:" + myDB.query(query1) + "<br>");

        // Parse input string to get restauranrestaurant Name and Address
        // $studentID . ' ' . $name . ' ' . $wantAC. ' ' . $wantsDining. ' ' . $wantsKitchen. ' ' . $wantsBathroom;
        String studentID;
        String name;
        String wantAC;
        String wantsDining;
        String wantsKitchen;
        String wantsBathroom;

        // Read command line arguments
        // args[0] is the first parameter
        studentID = args[0];
        name = args[1];
        wantAC = args[2];
        wantsDining = args[3];
        wantsKitchen = args[4];
        wantsBathroom = args[5];

        String checkQuery = "SELECT * FROM Student WHERE studentID = '" + studentID + "'";
        ResultSet checkResult = myDB.rawQuery(checkQuery);

        if (checkResult.next()) {
            System.out.println("Student ID " + studentID + " has already been entered. Please try again.");
            myDB.disConnect();
            return;
        }

        if (!isValid(wantAC) || !isValid(wantsDining) || !isValid(wantsKitchen) || !isValid(wantsBathroom)) {
            System.out.println("Error: AC, Dining, Kitchen, and Bathroom fields must be 'y' or 'n'. Please try again.");
            myDB.disConnect();
            return;
        }
        // Get the next id
        // String q = "select IFNULL(max(ID), 0) as max_id from Student";
        // ResultSet result = myDB.rawQuery(q);
        // int next_id = 1;
        // if (result.next()) // get first row of result set
        //     next_id += result.getInt("max_id");

        // Insert the new restaurant
        String input = "'" + studentID + "','" + name + "','" + wantAC + "','" + wantsDining + "','" + wantsKitchen + "','" + wantsBathroom + "'";
        myDB.insert("Student", input); // insert new restaurant

        // Student: StudentId, Name, WantsAC, WantsDining, WantsKitchen, WantsPrivateBathroom
        builder.append("<br> Table Assignment: <br> ---------------------- <br>");
        String query2 = "SELECT * from Student";
        ResultSet printString = myDB.rawQuery(query2);
        while(printString.next()) {
            String stuID = printString.getString("StudentId");
            String stuName = printString.getString("Name");
            String ac = printString.getString("WantsAC");
            String dining = printString.getString("WantsDining");
            String kitchen = printString.getString("WantsKitchen");
            String bathroom = printString.getString("WantsPrivateBathroom");
            builder.append("Student Name: " + stuName + " <br> Student ID: " + stuID + " | Wants AC: " + ac + " | Wants Dining: " + dining + " | Wants Kitchen: " + kitchen + " | Wants Bathroom: " + bathroom + "<br>");
            builder.append("<br> ---------------------- <br>");


        }
        // For debugging purposes: Show the database after the insert
        // builder.append("<br><br><br> Table Student: " + myDB.query(query1));
        System.out.println(builder.toString());

        myDB.disConnect();
    }

    private static boolean isValid(String value) {
        return value.equals("y") || value.equals("n");
    }
}
