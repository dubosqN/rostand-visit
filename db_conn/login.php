<?php

if (isset($_POST['username']) && isset($_POST['password'])) {
    require_once "conn.php";
    require_once "validate.php";

    $username = validate($_POST['username']);
    $password = validate($_POST['password']);

    $sql = "SELECT * FROM USER WHERE username='$username' AND password='$password'";
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        echo "Conn";
    }
    else{
        echo "Err";
    }
}