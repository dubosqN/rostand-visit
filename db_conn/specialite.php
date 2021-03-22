<?php
require_once "conn.php";
$sql = "SELECT * FROM specialite";

if(!$conn->query($sql)){
    echo 'Db error';
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['specialites'] = array();
        while ($row = $result->fetch_array()){
            array_push($return_arr['specialites'], array(
                'specialite_id' => $row['id'],
                'specialite_libelle' => $row['libelle'],
            ));
        }
        echo json_encode($return_arr);
    }
}