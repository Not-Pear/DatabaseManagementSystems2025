import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class addAssignment {
   // The main program that inserts a restaurant
    public static void main(String[] args) throws SQLException {
        String Username = "snorden"; // Change to your own username
        String mysqlPassword = "Ighei6ae"; // Change to your own mysql Password

        // Connect to the database
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();

        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT * from Assignment";
        // builder.append("<br> Table Student before:" + myDB.query(query1) + "<br>");

        String studentID = args[0];
        String buildingID = args[1];
        String roomNum = args[2];

        if (studentInDB(myDB, studentID)){
            System.out.println("Student ID " + studentID + " already has a room. Please try again.");
            myDB.disConnect();
            return;
        }

        // Check if Room Exists
        if (!checkRoomExits(myDB, buildingID, roomNum)) {
            System.out.println("Room Number " + roomNum + " in building " + buildingID + " does not exist. Please try again.");
            myDB.disConnect();
            return;
        }
        // Check if room is full
        if (isRoomFull(myDB, buildingID, roomNum)) {
            System.out.println("Room is already full. Assignment rejected.");
            myDB.disConnect();
            return;
        }

        // Check student exists wit preferences, get preferences, make sure room exists, check if room accomidations meet students preferences
        String studentPrefQuery = "SELECT WantsAC, WantsDining, WantsKitchen, WantsPrivateBathroom FROM Student WHERE StudentId = '" + studentID + "'";
        ResultSet studentPrefs = myDB.rawQuery(studentPrefQuery);

        if (!studentPrefs.next()) {
            System.out.println("Student ID " + studentID + " not found. Exiting.");
            myDB.disConnect();
            return;
        }

        String wantsAC = studentPrefs.getString("WantsAC");
        String wantsDining = studentPrefs.getString("WantsDining");
        String wantsKitchen = studentPrefs.getString("WantsKitchen");
        String wantsBathroom = studentPrefs.getString("WantsPrivateBathroom");

        String roomDetails = "SELECT HasKitchen, PrivateBathrooms, HasAC, HasDining FROM Room NATURAL JOIN Building WHERE BuildingID = '" + buildingID + "' AND RoomNumber = '" + roomNum + "'";
        ResultSet roomAttrs = myDB.rawQuery(roomDetails);

        if (!roomAttrs.next()) {
            System.out.println("Room not found in specified building. Exiting.");
            myDB.disConnect();
            return;
        }

        String hasKitchen = roomAttrs.getString("HasKitchen");
        String hasBathroom = roomAttrs.getString("PrivateBathrooms");
        String hasAC = roomAttrs.getString("HasAC");
        String hasDining = roomAttrs.getString("HasDining");

        if ((wantsAC.equals("y") && !hasAC.equals("y")) ||
            (wantsDining.equals("y") && !hasDining.equals("y")) ||
            (wantsKitchen.equals("y") && !hasKitchen.equals("y")) ||
            (wantsBathroom.equals("y") && !hasBathroom.equals("y"))) {

            System.out.println("Room does not meet student's preferences. Assignment rejected.");
            myDB.disConnect();
            return;
        }

        // I couldn't think of a way to put line 46-82 in a function without it being more complicated and diluted. 

        String input = "'" + studentID + "','" + buildingID + "','" + roomNum + "'";
        myDB.insert("Assignment", input);

        // Assignment: StudentId, BuildingId, RoomNumber
        builder.append("<br> Table Assignment: <br> ---------------------- <br>");
        String query2 = "SELECT * from Assignment";
        ResultSet printString = myDB.rawQuery(query2);
        while(printString.next()) {
            String stuID = printString.getString("StudentId");
            String buildID = printString.getString("BuildingId");
            String rmNum = printString.getString("RoomNumber");
            builder.append("Student ID: " + stuID + " | Building ID: " + buildID + " | Room Number: " + rmNum + "<br>");

        }

        // builder.append("<br><br><br> Table Assignment after:" + myDB.query(query1));
        System.out.println(builder.toString());

        myDB.disConnect();
    }

    private static boolean studentInDB(jdbc_db db, String studentID) throws SQLException {
        String checkQuery = "SELECT * FROM Assignment WHERE studentID = '" + studentID + "'";
        ResultSet checkResult = db.rawQuery(checkQuery);

        if (checkResult.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean checkRoomExits(jdbc_db db, String buildingID, String roomNum) throws SQLException {
        String checkRoom = "SELECT * FROM Room WHERE RoomNumber = '" + roomNum + "' AND BuildingId = '" + buildingID + "'";
        ResultSet checkRoomNum = db.rawQuery(checkRoom);

        if (checkRoomNum.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean checkStudentPref(jdbc_db db, String buildingID, String roomNum) throws SQLException {
        String checkRoom = "SELECT * FROM Room WHERE RoomNumber = '" + roomNum + "' AND BuildingId = '" + buildingID + "'";
        ResultSet checkRoomNum = db.rawQuery(checkRoom);

        if (checkRoomNum.next()) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isRoomFull(jdbc_db db, String buildingID, String roomNum) throws SQLException {
        String countQuery = "SELECT COUNT(*) AS currentCount FROM Assignment WHERE BuildingId = '" + buildingID + "' AND RoomNumber = '" + roomNum + "'";
        ResultSet currentCountResult = db.rawQuery(countQuery);
        int currentCount = 0;
        if (currentCountResult.next()) {
            currentCount = currentCountResult.getInt("currentCount");
        }

        String capacityQuery = "SELECT NumBedrooms FROM Room WHERE BuildingId = '" + buildingID + "' AND RoomNumber = '" + roomNum + "'";
        ResultSet capacityResult = db.rawQuery(capacityQuery);
        if (!capacityResult.next()) {
            return true; // room doesn't exist, treat as full
        }

        int maxCapacity = capacityResult.getInt("NumBedrooms");
        return currentCount >= maxCapacity;
    }
}
