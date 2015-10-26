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


$name= $_POST['name'];
$merchName= $_POST['merchName'];
$accountNo= $_POST['account'];
$accountNo= intval($accountNo);
$points= $_POST['amount'];
$points= intval($points);
$pin = $_POST['pin'];

$stmt=$conn->query("SELECT * FROM users WHERE account = '$accountNo' AND pin = '$pin' AND active = 1 AND points >= '$points'");

$user = array();

if ($stmt->num_rows > 0) 
{
while ($row=$stmt->fetch_assoc())
{
	$balance = $row['balance'] - $amount; 
	
	$oldPoints = $row['points'];
	$newPoints = $oldPoints - $points;
	$phone = $row['phone'];
	
	
	$user[status] = "success";
	$user[name] = $row['name'];
	$user[amount] = $points;
	echo json_encode($user);
}

//update table users
$stmt2=$conn->query("UPDATE users SET points = '$newPoints' WHERE account = '$accountNo' AND pin = '$pin'");
//insert into table transactions

$stmt3=$conn->query("INSERT INTO redemptions(account, name, points, datetime, merchName) 
VALUES  ('$accountNo', '$user[name]', '$points', '$datetime_formatted', '$merchName')");

//send sms


require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "You have redeemed ".$points.", points. Your new points balance is ".$newPoints."";
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
$user[status] = "redeemFail";


echo json_encode($user);
}


$stmt->close();

$conn->close();
?>