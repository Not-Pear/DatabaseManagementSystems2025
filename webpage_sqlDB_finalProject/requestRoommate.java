import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class requestRoommate {
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
        String query1 = "SELECT * FROM Student WHERE StudentId != '" + studentID + "'  AND (WantsAC, WantsDining, WantsKitchen, WantsPrivateBathroom) = (SELECT WantsAC, WantsDining, WantsKitchen, WantsPrivateBathroom FROM Student WHERE StudentId = " + studentID + ")";
        ResultSet checkResult = myDB.rawQuery(query1);


        if (!checkResult.next()) {
            System.out.println("There are no Students that fit your preferences or students with that ID. Please try again.");
            myDB.disConnect();
            return;
        }
        else {
            String stuName = checkResult.getString("Name");
            builder.append("<br> Table Assignment: <br> ---------------------- <br> <br> Possible Roommate: " + stuName + "<br>");
            builder.append("<br> ---------------------- <br>");
            // System.out.println("Name: " + name);
            // System.out.println("Room Number: " + roomNum);
            while(checkResult.next()){
                stuName = checkResult.getString("Name");
                builder.append("<br>Possible Roommate: " + stuName + "<br>");
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
