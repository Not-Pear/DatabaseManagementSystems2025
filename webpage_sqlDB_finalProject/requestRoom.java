import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class requestRoom {
   // The main program that inserts a restaurant
    public static void main(String[] args) throws SQLException {
        String Username = "snorden"; // Change to your own username
        String mysqlPassword = "Ighei6ae"; // Change to your own mysql Password

        // Connect to the database
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();
        String studentID = args[0];
    

        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT RoomNumber FROM Building NATURAL JOIN Room JOIN Student on StudentId = '" + studentID + "' WHERE (WantsPrivateBathroom = 'n' OR PrivateBathrooms = WantsPrivateBathroom) AND (WantsKitchen = 'n' OR HasKitchen = WantsKitchen) AND (WantsAC = 'n' OR HasAC = WantsAC) AND (WantsDining = 'n' OR HasDining = WantsDining) ORDER BY RoomNumber";
        ResultSet checkResult = myDB.rawQuery(query1);


        if (!checkResult.next()) {
            System.out.println("There are no rooms that fit student requirements or Student doesn't exist. Please try again.");
            myDB.disConnect();
            return;
        }
        else {
           
            String roomNum = checkResult.getString("RoomNumber");
            builder.append("<br> Table Assignment: <br> ---------------------- <br> <br> Room Number: " + roomNum + "<br>");
            builder.append("<br> ---------------------- <br>");
            // System.out.println("Name: " + name);
            // System.out.println("Room Number: " + roomNum);
            while(checkResult.next()){
                roomNum = checkResult.getString("RoomNumber");
                builder.append("<br>Room Number: " + roomNum + "<br>");
                // System.out.println("Name: " + name);
                // System.out.println("Room Number: " + roomNum);
                builder.append("<br> ---------------------- <br>");
            }
        }

        // builder.append("<br> Table Assignment:" + myDB.query(query1) + "<br>");
        System.out.println(builder.toString());

        myDB.disConnect();
    }
}
