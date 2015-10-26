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
$merchUsername= $_POST['merchUsername'];
$merchPassword= $_POST['merchPassword'];
$merchPassword = md5($merchPassword);
$accountNo= $_POST['account'];
$accountNo= intval($accountNo);
$amount = $_POST['amount'];
$amount = intval($amount);
$pin = $_POST['pin'];

$stmt1=$conn->query("SELECT * FROM merchants WHERE email = '$merchUsername' AND password = '$merchPassword'");
while ($row1=$stmt1->fetch_assoc())
{
$rate = $row1['rate'];
}


$stmt=$conn->query("SELECT * FROM users WHERE account= '$accountNo' AND pin= '$pin'");

$user = array();

if ($stmt->num_rows > 0) 
{
while ($row=$stmt->fetch_assoc())
{
	$balance = $row['balance'] - $amount; 
	
	$newPoints = $amount/10;
	$oldPoints = $row['points'];
	$totalPoints = $newPoints + $oldPoints;
	
	$phone = $row['phone'];
	
	$active = $row['active'];
	
	
	$successStatus = "success";
	$successName = $row['name'];
	$successAmount = $amount;
	
	
	
}


if ($active = 1)
{
/************************************/
//update table users
$stmt2=$conn->query("UPDATE users SET points = '$totalPoints' WHERE account = '$accountNo' AND pin = '$pin'");
//insert into table transactions

$stmt3=$conn->query("INSERT INTO transactions (account, name, amount, points, datetime, merchName, transType) 
VALUES  ('$accountNo', '$successName', '$amount', '$newPoints', '$datetime_formatted', '$merchName', 'Cash')");

//send sms

require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "You have purchased goods worth ".$amount.", and gained ".$newPoints." points. Your new points balance is ".$totalPoints."";
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
/******************************************/
$user[status] = $successStatus;
$user[name] = $successName;
$user[amount] = $successAmount;
echo json_encode($user);
}
else
{
$user[status] = "activeFail";
$user[name] = $successName;
$user[amount] = $successAmount;


echo json_encode($user);
}





}
else
{
$user[status] = "payFail";


echo json_encode($user);
}


$stmt->close();

$conn->close();
?>