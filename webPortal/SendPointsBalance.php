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
$account= intval($account);


$stmt=$conn->query("SELECT * FROM users WHERE account = '$account'");

$user = array();

if ($stmt->num_rows > 0) 
{
while ($row=$stmt->fetch_assoc())
{
	$name = $row['name']; 
	
	$points = $row['points'];
	$phone = $row['phone'];

	$user[status] = "success";
	$user[account] = $account;
	$user[name] = $name;
	echo json_encode($user);
}



//send smss


require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "Dear ".$name.", your Paykind Points balance is ".$points."";
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
$user[status] = "accFail";


echo json_encode($user);
}


$stmt->close();

$conn->close();




?>