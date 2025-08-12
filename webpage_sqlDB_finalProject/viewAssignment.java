import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class viewAssignment {
   // The main program that inserts a restaurant
    public static void main(String[] args) throws SQLException {
        String Username = "snorden"; // Change to your own username
        String mysqlPassword = "Ighei6ae"; // Change to your own mysql Password

        // Connect to the database
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();
        String buildingID = args[0];

        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT Name, RoomNumber from Assignment NATURAL JOIN Student WHERE BuildingId = '" + buildingID +"' ORDER BY Name";

        ResultSet checkResult = myDB.rawQuery(query1);


        if (!checkResult.next()) {
            System.out.println("There are no assignments in that building. Please try again.");
            myDB.disConnect();
            return;
        }
        else {
            String name = checkResult.getString("Name");
            String roomNum = checkResult.getString("RoomNumber");
            builder.append("<br> Table Assignment: <br> ---------------------- <br><br> Name: " + name + " | Room Number: " + roomNum + "<br>");
            builder.append("<br> ---------------------- <br>");
            // System.out.println("Name: " + name);
            // System.out.println("Room Number: " + roomNum);
            while(checkResult.next()){
                name = checkResult.getString("Name");
                roomNum = checkResult.getString("RoomNumber");
                builder.append("<br>Name: " + name + " | Room Number: " + roomNum + "<br>");
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
