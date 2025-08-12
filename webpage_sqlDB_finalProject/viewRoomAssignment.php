<html>
<style>
    body {
        background-image: url('backgroundImage.jpg');
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center;
        background-attachment: fixed;
        font-family: "Times New Roman", Times, serif; 
        color: white;
    }

    .content {
        background-color: rgba(0, 0, 0, 0.6);
        padding: 2rem;
        border-radius: 15px;
        margin: 5rem auto;
        max-width: 700px;
        text-align: center;
    }

    .btn-custom {
        margin: 0.5rem;
    }
    a:link {
        color: white;
        background-color: transparent;
        text-decoration: underline;
    }
    a:visited {
        color: white;
        background-color: transparent;
        text-decoration: underline;
    }
    a:hover {
        color: red;
        background-color: transparent;
        text-decoration: underline;
    }
    a:active {
        color: purple;
        background-color: transparent;
        text-decoration: underline;
    }
    </style>
</html>
<body>

<div class="content text-center mt-5">

    <h3>All Room Assignments:</h3>
    <form action="viewRoomAssignment.php" method="post">
            Building ID: <input type="text" name="buildingID"><br>
            <input name="submit" type="submit" >
        </form>
        <br><br>
        
        <br><br>
        </body>
        
        <?php
            if (isset($_POST['submit'])) 
            {
                // Assignment: StudentId, BuildingId, RoomNumber
                // replace ' ' with '\ ' in the strings so they are treated as single command line args
                $buildingID = escapeshellarg($_POST[buildingID]);



                $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar viewRoomAssignment ' . $buildingID;

                // remove dangerous characters from command to protect web server
                $escaped_command = escapeshellcmd($command);
                // echo "<p>command: $command <p>"; 
                // run jdbc_insert_item.exe
                system($escaped_command);           
            }
        ?>
    </body>
    <br><br>
    <a href="https://csce.uark.edu/~snorden/project_java/main.php">Back to Main Page</a>
    </html>
</div>
