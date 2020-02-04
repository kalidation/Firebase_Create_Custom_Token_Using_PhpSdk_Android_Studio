<?php

require_once './vendor/autoload.php';

use Kreait\Firebase\Factory;
use Firebase\Auth\Token\Exception\InvalidToken;

$factory = (new Factory)
    ->withServiceAccount('./Your Service Account ID.json);
    // The following line is optional if the project id in your credentials file
    // is identical to the subdomain of your Firebase project. If you need it,
    // make sure to replace the URL with the URL of your project.
    //->withDatabaseUri('https://my-project.firebaseio.com');
//$database = $factory->createDatabase();
$uid = $_POST['UID'];

$customToken = $factory->createAuth()->createCustomToken($uid);
$customTokenString = (string) $customToken;
echo json_encode($customTokenString);

?>
