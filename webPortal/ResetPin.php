<?php
$DB_HOST = "localhost";
$DB_USER = "cardplan_loyalty";
$DB_PASSWORD ="cardplanloyalty";
$DB_DATABASE = "cardplan_loyalty";

$conn=new mysqli($DB_HOST, $DB_USER, $DB_PASSWORD, $DB_DATABASE);

if ($conn->connect_error)
{
	die("Can not connect to the database".$conn->connect_error);
}



$account= $_POST['account'];
$account = intval($account);


$stmt = "SELECT * FROM users WHERE account = '$account'";
$res = $conn->query($stmt);

while ($row = $res->fetch_assoc())
{
	$name = $row['name'];
	$phone = $row['phone'];
}

$resetCode = "RC".rand(1000, 9999)."";

$stmt3=$conn->query("INSERT INTO resetrequests(account, name, resetcode, active) 
VALUES  ('$account ', '$name', '$resetCode', 1)");


//send sms
require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "Please use this reset code to finish resetting your PIN:  ".$resetCode."";
$gateway    = new AfricasTalkingGateway($username, $apikey);

try 
{ 
  
  $results = $gateway->sendMessage($phone, $message);
  
            
  foreach($results as $result) {
    // status is either "Success" or "error message"
    /*echo " Number: " .$result->number;
    echo " Status: " .$result->status;
    echo " MessageId: " .$result->messageId;
    echo " Cost: "   .$result->cost."\n";*/
  }
}
catch ( AfricasTalkingGatewayException $e )
{
  echo "Encountered an error while sending: ".$e->getMessage();
		
}






$user = array();

$user[status] = "success";
$user[account] = $account;

echo json_encode($user);







$conn->close();
?>