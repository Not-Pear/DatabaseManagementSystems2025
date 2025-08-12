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
<body>
<div class="content text-center mt-5">
<h3>Ready to see everything?</h3>

<form action="createReport.php" method="post">
    <input type="submit" name="submit" value="Generate Report">
</form>

<br><br>


<?php
if (isset($_POST['submit'])) {
$command = 'java -cp .:mysql-connector-java-5.1.40-bin.jar createReport';

// Sanitize and execute the command
$escaped_command = escapeshellcmd($command);
system($escaped_command);
}
?>
<a href="https://csce.uark.edu/~snorden/project_java/main.php">Back to Main Page</a>
</div>

