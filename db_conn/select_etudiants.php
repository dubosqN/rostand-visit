<?php
require_once "conn.php";
$sql = "SELECT
       etudiant.id,
       etudiant.nom,
       etudiant.prenom,
       etudiant.mail,
       etudiant.etablissement,
       visite.libelle as visite,
       section.libelle as section,
       option_specialite.libelle
FROM
     etudiant,
     visite,
     section,
     option_specialite
WHERE
      visite.id = etudiant.visite_id
AND
      etudiant.option_specialite_id = option_specialite.id
AND
      etudiant.section_id = section.id;";

if(!$conn->query($sql)){
    echo 'Db error';
}
else{
    $result = $conn->query($sql);
    if($result->num_rows > 0){
        $return_arr['etudiants'] = array();
        while ($row = $result->fetch_array()){
            array_push($return_arr['etudiants'], array(
                'etudiant_id' => $row['id'],
                'etudiant_nom' => $row['nom'],
                'etudiant_prenom' => $row['prenom'],
                'etudiant_mail' => $row['id'],
                'etudiant_etablissement' => $row['etablissement'],
                'etudiant_visite' => $row['visite'],
                'etudiant_section' => $row['section'],
                'etudiant_libelle' => $row['libelle'],

            ));
        }
        echo json_encode($return_arr);
    }
}