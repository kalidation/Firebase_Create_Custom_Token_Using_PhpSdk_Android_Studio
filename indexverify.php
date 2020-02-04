<?php

require_once './vendor/autoload.php';

use Kreait\Firebase\Factory;
use Firebase\Auth\Token\Exception\InvalidToken;

$factory = (new Factory)
    ->withServiceAccount('./Your Service Accout ID.json');
    // The following line is optional if the project id in your credentials file
    // is identical to the subdomain of your Firebase project. If you need it,
    // make sure to replace the URL with the URL of your project.
    //->withDatabaseUri('https://my-project.firebaseio.com');

    $idTokenString = $_POST["token"];
    $auth = $factory->createAuth();
    
    try {
        $verifiedIdToken = $auth->verifyIdToken($idTokenString);
    } catch (InvalidToken $e) {
        echo $e->getMessage();
    }
    
    $uid = $verifiedIdToken->getClaim('sub');
    $message = $verifiedIdToken->getClaim('iss');
    $user = $auth->getUser($uid);

    if($user){
        echo json_encode("'.$uid.''.$message.'");
    }else{
        echo json_encode('false');
    }

?>
