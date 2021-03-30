<?php
session_start();
if (isset($_POST['username']) && isset($_POST['password'])) {
    require_once "conn.php";
    require_once "validate.php";

    $username = validate($_POST['username']);
    $password = validate($_POST['password']);

    /*AND password='$password'*/
    $sql = "SELECT * FROM USER WHERE username='$username' ";
    $result = $conn->query($sql);


    if($result->num_rows > 0){
        while ($row = $result->fetch_array()){
            $id_user = $row['id'];
            if (password_verify($password, $row['password'])) {
                echo "Conn";
                break;
            }
            else{
                echo "Err";
            }
        }

    }
    else{
        echo "Err";
        session_destroy();
    }
}