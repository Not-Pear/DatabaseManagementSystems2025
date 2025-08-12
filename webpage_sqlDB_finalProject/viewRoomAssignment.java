import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class viewRoomAssignment {
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
        // String query1 = "SELECT Room.BuildingId, Room.RoomNumber, Room.NumBedrooms, " +
        //                 "COUNT(Assignment.StudentId) AS AssignedRoom, " +
        //                 "(Room.NumBedrooms - COUNT(Assignment.StudentId)) AS AvailableRoom " +
        //                 "FROM Room " +
        //                 "LEFT JOIN Assignment ON Room.BuildingId = Assignment.BuildingId AND Room.RoomNumber = Assignment.RoomNumber " +
        //                 "GROUP BY Room.BuildingId, Room.RoomNumber, Room.NumBedrooms " +
        //                 "ORDER BY Room.BuildingId;";
        String query1 = "SELECT BuildingId, RoomNumber, NumBedrooms FROM Room NATURAL JOIN Building WHERE BuildingId = '" + buildingID + "' GROUP BY BuildingId, RoomNumber, NumBedrooms ORDER BY RoomNumber";
                                        
                        ResultSet rs = myDB.rawQuery(query1);
                        // System.out.println("Room Assignments:");
                        // while (rs.next()) {
                        //     int buildingId = rs.getInt("BuildingId");   //this whole print statement might be unessecary w the rawQuery func but i cant get any results from either
                        //     String roomNumber = rs.getString("RoomNumber");
                        //     int totalBeds = rs.getInt("NumBedrooms");
                        //     int assignedBeds = rs.getInt("AssignedRoom");
                        //     int availableBeds = rs.getInt("AvailableRoom");
                        
                        

        if (!rs.next()) {
            System.out.println("There are no available rooms. Please try again.");
            myDB.disConnect();
            return;
        }
        builder.append("<br> All Rooms in Building " + buildingID + ": <br> ---------------------- <br>");
        String query2 = "SELECT BuildingId, RoomNumber, NumBedrooms FROM Room NATURAL JOIN Building WHERE BuildingId = '" + buildingID + "' GROUP BY BuildingId, RoomNumber, NumBedrooms ORDER BY RoomNumber";
        ResultSet printString = myDB.rawQuery(query2);
        while(printString.next()) {
            String buildID = printString.getString("BuildingId");
            String rmNum = printString.getString("RoomNumber");
            String numRooms = printString.getString("NumBedrooms");
            builder.append("<br>Building ID: " + buildID + " | Room Number: " + rmNum + " | Number of Rooms: " + numRooms + "<br>");
            builder.append("<br> ---------------------- <br>");
        }
        // builder.append("<br> Table Assignment:" + myDB.query(query1) + "<br>");
        System.out.println(builder.toString());

        myDB.disConnect();
    }
}

