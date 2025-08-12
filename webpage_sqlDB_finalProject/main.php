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
        text-decoration: none;
    }
    a:visited {
        color: white;
        background-color: transparent;
        text-decoration: none;
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

<div class="content text-center mt-5">
    <h1 class="mb-4">University of Arkansas Housing Portal</h1>
    <p class="lead mb-5">
        Welcome to the housing management system. Add students, assign rooms, and manage residential housing preferences with ease.
    </p>
    <div class="row text-center mb-5">
        <div class="col">
        <h2>24,001</h2>
        <p>Registered Students</p>
        </div>
        <div class="col">
        <h2>Over 100+</h2>
        <p>Buildings</p>
        </div>
        <div class="col">
        <h2>100+</h2>
        <p>Courses</p>
    </div>
    </div>
        <div>
        <h3>Housing Portal:</h3>
        <table>
        <tbody>
        <tr>
            <td>-- </td>
            <td><b>Table of Operations --</b></td>
        </tr>
        <tr>
            <td>1. </td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/addStudent.php ">Add A Student Here!</a></td>
        </tr>
        <tr>
            <td>2. </td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/addAssignment.php ">Add Assignment Here!</a></td>
        </tr>
        <tr>
            <td>3. </td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/viewAssignment.php ">View Assignments Here!</a></td>
        </tr>
        <tr>
            <td>4. </td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/viewRoomAssignment.php ">View All Rooms Here!</a></td>
        </tr>
        <tr>
            <td>5. </td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/requestRoom.php ">Make a Room Request Here!</a></td>
        </tr>
        <tr>
            <td>6</td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/requestRoommate.php ">Make a Roommate Request Here!</a></td>
        </tr>
        <tr>
            <td>7. </td>
            <td><a href="http://www.csce.uark.edu/~snorden/project_java/createReport.php ">View Report Here!</a></td>
        </tr>
        <tr>
            <td>9. </td>
            <td><a href="https://www.youtube.com/watch?v=S_HGa6AipoM ">Cute Dog Video!</a></td>
        </tr>
        </tbody>
        </table>
    </div>

</div>


<!-- <form action="main.php" method="post">
    Name: <input type="text" name="name"><br>
    Supplier id: <input type="text" name="supplier_id"><br>
    Quantity: <input type="text" name="quantity"><br>
    Unit Price: <input type="text" name="unit_price"><br>
    <input name="submit" type="submit" >
</form> -->
<br><br>

</body>

</html>

<?php
// if (isset($_POST['submit'])) 
// {
//     // replace ' ' with '\ ' in the strings so they are treated as single command line args
//     $name = escapeshellarg($_POST[name]);
//     $supplier_id = escapeshellarg($_POST[supplier_id]);
//     $quantity = escapeshellarg($_POST[quantity]);
//     $unit_price = escapeshellarg($_POST[unit_price]);

//     $command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar main ' . $name . ' ' . $supplier_id . ' ' . $quantity. ' ' . $unit_price;

//     // remove dangerous characters from command to protect web server
//     $escaped_command = escapeshellcmd($command);
//     echo "<p>command: $command <p>"; 
//     // run jdbc_insert_item.exe
//     system($escaped_command);           
// }
?>


