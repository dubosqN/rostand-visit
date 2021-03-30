<?php
require_once "conn.php";

$sql = "
select count(*) as nb, 
YEAR(visite.date_visite) as annee_visite 
from visite, etudiant 
where visite.id = etudiant.visite_id 
group by annee_visite";

if(!$conn->query($sql)){
    echo 'Db error';
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['annees'] = array();
        while ($row = $result->fetch_array()){
            array_push($return_arr['annees'], array(
                'annee' => $row['annee_visite']. " (" . $row['nb'].")",
                'annee_y' => $row['annee_visite'],
            ));
        }
        echo json_encode($return_arr);
    }
}