import java.sql.*;

/*
jdbc_insert_item.java    // java program that is called by php that just does the insert; calls jdbc_db.java to connect and do the actual insert
jdbc_db.java // class (no main program) that has useful methods
*/

public class createReport {
   // The main program that inserts a restaurant
    public static void main(String[] args) throws SQLException {
        String Username = "snorden"; // Change to your own username
        String mysqlPassword = "Ighei6ae"; // Change to your own mysql Password

        // Connect to the database
        jdbc_db myDB = new jdbc_db();
        myDB.connect(Username, mysqlPassword);
        myDB.initDatabase();
        // String buildingID = args[0];

        StringBuilder builder = new StringBuilder();
        String query1 = "SELECT " +
                            "BuildingId, " +
                            "COUNT(RoomNumber) AS TotalRooms, " +
                            "SUM(NumBedrooms) AS TotalBedrooms, " +
                            "SUM(CASE WHEN assigned < NumBedrooms THEN 1 ELSE 0 END) AS AvailableRooms, " +
                            "SUM(CASE WHEN assigned < NumBedrooms THEN NumBedrooms - assigned ELSE 0 END) AS AvailableBedrooms " +
                        "FROM ( " +
                            "SELECT " +
                                "BuildingId, " +
                                "RoomNumber, " +
                                "NumBedrooms, " +
                                "COUNT(StudentId) AS assigned " +
                                "FROM Room " +
                                "NATURAL LEFT JOIN Assignment " +
                                "GROUP BY BuildingId, RoomNumber, NumBedrooms " +
                            ") AS RoomStats " +
                        "GROUP BY BuildingId WITH ROLLUP";
                                        
        ResultSet rs = myDB.rawQuery(query1);
        if(!rs.next()) {
            System.out.println("something is very wrong");
            myDB.disConnect();
            return;
        }
        builder.append("<br> Building Summary Report: <br> ----------------------<br>");
        String buildingId = rs.getString("BuildingId");
        int totalRooms = rs.getInt("TotalRooms");
        int totalBedrooms = rs.getInt("TotalBedrooms");
        int availableRooms = rs.getInt("AvailableRooms");
        int availableBedrooms = rs.getInt("AvailableBedrooms");
        builder.append("Building ID: " + buildingId + " | Rooms: " + totalRooms + " | Bedrooms: " + totalBedrooms + " | Available Rooms: " + availableRooms + " | Available Bedrooms: " + availableBedrooms + "<br>");
        builder.append("--------------------------------------------------------------------------<br>");

        int totalCampusAvailableBedrooms = 0;
        while(rs.next()) {
            
        
        
            buildingId = rs.getString("BuildingId");
            totalRooms = rs.getInt("TotalRooms");
            totalBedrooms = rs.getInt("TotalBedrooms");
            availableRooms = rs.getInt("AvailableRooms");
            availableBedrooms = rs.getInt("AvailableBedrooms");

            builder.append("Building ID: " + buildingId + " | Rooms: " + totalRooms + " | Bedrooms: " + totalBedrooms + " | Available Rooms: " + availableRooms + " | Available Bedrooms: " + availableBedrooms + "<br>");
            builder.append("--------------------------------------------------------------------------<br>");



        
            // totalCampusAvailableBedrooms += availableBedrooms;
        } 
        
            // builder.append("<br><strong>Total Available Bedrooms on Campus: " + totalCampusAvailableBedrooms + "</strong><br>");
            // builder.append("<br> ---------------------- <br>");
        



            System.out.println(builder.toString());
   
        myDB.disConnect();
    }
}

