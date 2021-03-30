<?php
require_once "conn.php";
$sql = "SELECT * FROM section";

if(!$conn->query($sql)){
    echo 'Db error';
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['sections'] = array();
        while ($row = $result->fetch_array()){
            array_push($return_arr['sections'], array(
                'sections_id' => $row['id'],
                'sections_libelle' => $row['libelle'],
            ));
        }
        echo json_encode($return_arr);
    }
}