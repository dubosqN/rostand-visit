<?php
require_once "conn.php";
$sql = "SELECT * FROM etablissement";

if(!$conn->query($sql)){
    echo 'Db error';
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['etablissements'] = array();
        while ($row = $result->fetch_array()){
            array_push($return_arr['etablissements'], array(
                'etablissement_id' => $row['id'],
                'etablissement_libelle' => $row['libelle'],
            ));
        }
        echo json_encode($return_arr);
    }
}