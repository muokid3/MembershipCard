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




$name = $_POST['name'];
$email= $_POST['email'];
$phone= $_POST['phone'];
$merchName= $_POST['merchName'];


$phone=ltrim($phone, '+254');
$phone=ltrim($phone, '254');
$phone=ltrim($phone, '0');
$append='+254';
$phone=$append.$phone;


$pin= $_POST['pin'];
$balance = 0;
$points = 0;

$sqll="SELECT account FROM users ORDER BY id DESC LIMIT 1";
$resl=$conn->query($sqll);

while($row1=$resl->fetch_assoc())
{
$accountSelected=$row1['account'];
}

$account = $accountSelected+1;


$stmt = $conn->prepare("INSERT INTO users(name, email, phone, account, pin, balance, points, merchName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
$stmt->bind_param("sssisiis", $name, $email, $phone, $account, $pin, $balance, $points, $merchName);

if ($stmt->execute())
{
echo $account;
}

//send message



require_once('AfricasTalkingGateway.php');

$username   = "muokid3";
$apikey     = "62762a1d705438fb5595e83bd7ae10623151ddaa500e42092d96bf0ade0e5cc6";

$recipients = $lphone;

$message    = "Welcome to the Paykind Loyalty card ".$name.", your secret PIN is ".$pin."";
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












$stmt->close();
	


$conn->close();

?>