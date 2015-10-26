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



$account = $_POST['account'];
$account = intval($account);
$pin = $_POST['pin'];
$amount = $_POST['amount'];

$stmt = "SELECT * FROM users WHERE account = '$account' AND pin = '$pin'";
$res = $conn->query($stmt);

$user = array();
if ($res->num_rows > 0) 
{
while ($row=$res->fetch_assoc())
{
	$active = $row['active'];
	
	if ($active == 1)
	{
		$user[status] = "success";
	}
	else
	{
		$user[status] = "activeFail";
	}
	
	
	$user[name] = $row['name'];
	echo json_encode($user);
	

}


}
else
{
$user[status] = "pinFail";


echo json_encode($user);
}




$conn->close();
?>