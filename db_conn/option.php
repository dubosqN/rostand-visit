<?php
require_once 'conn.php';
if(isset($_GET['specialite_libelle'])){
    $sql = "SELECT option_specialite.id, option_specialite.libelle 
            FROM option_specialite 
            WHERE specialite_id=
                  (
	                SELECT id 
	                FROM specialite 
	                WHERE specialite.libelle=
	                    '".$_GET['specialite_libelle']."')";

    if(!$conn->query($sql)){
        echo "Db error";
    }
    else{
        $result = $conn->query($sql);
        if($result->num_rows > 0){
            $return_arr['options'] = array();
            while ($row = $result->fetch_array()){
                array_push($return_arr['options'], array(
                    'option_id' => $row['id'],
                    'option_libelle' => $row['libelle'],
                ));
            }
            echo json_encode($return_arr);
        }
    }

}