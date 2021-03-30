<?php
session_start();
$id = $_SESSION['id_user'];
echo $id;