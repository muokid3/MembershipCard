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
$oldpin = $_POST['oldpin'];
$newpin= $_POST['newpin'];

$stmt = "SELECT * FROM users WHERE account = '$account' AND pin = '$oldpin'";
$res = $conn->query($stmt);

while ($row = $res->fetch_assoc())
{
$phone = $row['phone'];
}

$user = array();
if ($res->num_rows > 0) 
{
$stmt2=$conn->query("UPDATE users SET pin= '$newpin' WHERE account = '$account' AND pin = '$oldpin'");

//send message

require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "You have changed your PIN to ".$newpin.", please memorize it and keep it safe. PIN yako, siri yako";
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


$user[status] = "success";
$user[account] = $account;
$user[oldpin] = $oldpin;
$user[newpin] = $newpin;
$user[confpin] = $newpin;
echo json_encode($user);


}
else
{
$user[status] = "pinFail";


echo json_encode($user);
}




$conn->close();
?>