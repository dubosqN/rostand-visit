<?php
require_once "conn.php";
$annee = $_POST['etudiant_annee'];
$section = $_POST['etudiant_diplome'];

$sql = "SELECT
       etudiant.id,
       etudiant.nom,
       etudiant.prenom,
       etudiant.mail,
       etablissement.libelle as etablissement,
       visite.libelle as visite,
       section.libelle as section,
       option_specialite.libelle
FROM
     etudiant,
     visite,
     section,
     option_specialite,
     etablissement,
     specialite
WHERE
      visite.id = etudiant.visite_id
AND
      etudiant.option_specialite_id = option_specialite.id
AND
      etudiant.section_id = section.id    
AND 
	etudiant.etablissement_id = etablissement.id      
AND   
	YEAR(visite.date_visite) = '$annee'
AND option_specialite.specialite_id = specialite.id
AND specialite.libelle like '%$section%'";

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
                'etudiant_mail' => $row['mail'],
                'etudiant_etablissement' => $row['etablissement'],
                'etudiant_visite' => $row['visite'],
                'etudiant_section' => $row['section'],
                'etudiant_libelle' => $row['libelle'],

            ));
        }
        echo json_encode($return_arr);
    }
}