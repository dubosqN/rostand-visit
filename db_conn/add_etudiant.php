<?php
if (isset($_POST['nom']) && isset($_POST['prenom']) && isset($_POST['mail']) && isset($_POST['etablissement']) && isset($_POST['section']) && isset($_POST['specialite'])) {
    require_once 'conn.php';
    require_once 'validate.php';

    $nom = validate($_POST['nom']);
    $prenom = validate($_POST['prenom']);
    $mail = validate($_POST['mail']);
    $etablissement = validate($_POST['etablissement']);
    $section = validate($_POST['section']);
    $specialite= validate($_POST['specialite']);


    $sql = "INSERT INTO ETUDIANT (id, nom, prenom, mail, etablissement_id, section_id, visite_id, option_specialite_id) 
            VALUES (NULL, '$nom', '$prenom', '$mail', '$etablissement', '$section', DEFAULT , '$specialite')";

    if(!$conn->query($sql)){
        echo "error";
    }
    else{
        echo "success";
    }

}