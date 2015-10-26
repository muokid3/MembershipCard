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


date_default_timezone_set('Africa/Nairobi');     

$datetime_variable = new DateTime();
$datetime_formatted = date_format($datetime_variable, 'Y-m-d H:i:s');


$account= $_POST['account'];
$accountNo= intval($account);
$pinResetCode= $_POST['pinResetCode'];
$newResetPin= $_POST['newResetPin'];

$stmt=$conn->query("SELECT * FROM resetrequests WHERE account = '$accountNo' AND resetcode = '$pinResetCode' AND active = 1");

$user = array();

if ($stmt->num_rows > 0) 
{
while ($row=$stmt->fetch_assoc())
{
	$name = $row['name']; 
	

	
	$user[status] = "success";
	$user[name] = $name;
	echo json_encode($user);
}

$stmt2=$conn->query("SELECT * FROM users WHERE account = '$accountNo'");

while($row=$stmt2->fetch_assoc())
{
$phone = $row['phone'];
}

//update tables
$stmt2=$conn->query("UPDATE users SET pin= '$newResetPin' WHERE account = '$accountNo'");

$stmt3=$conn->query("UPDATE resetrequests SET active = 0 WHERE account = '$accountNo'");


//send smss
require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "Dear ".$name.", you have successfuly reset your PIN to ".$newResetPin."";
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


}
else
{
$user[status] = "resetFail";


echo json_encode($user);
}


$stmt->close();

$conn->close();
?>