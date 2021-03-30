<?php
session_start();
if (isset($_POST['password']) && isset($_POST['mail'])) {
    require_once "conn.php";
    require_once "validate.php";


    $password = validate($_POST['password']);
    $mail = validate($_POST['mail']);

    $hash = password_hash($password, PASSWORD_DEFAULT);

    $sql = "UPDATE USER SET PASSWORD ='$hash' WHERE user.mail = '$mail' ";

    if($conn->query($sql)){
        echo "modif";
    }
    else{
        echo "fail";
    }
}