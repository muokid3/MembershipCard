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



$email= $_POST['email'];
$password= $_POST['password'];
$hasshedPassword = md5($password);
$rate= $_POST['rate'];
$rateInt= intval($rate);


$user = array();


if ($rateInt >= 100)
{
$user[status] = "fail";
$user[email] = $email;
$user[password] = $password;
$user[rate] = $rateInt;
echo json_encode($user);
}
else
{
$stmt=$conn->query("UPDATE merchants SET rate = '$rateInt' WHERE email = '$email' AND password = '$hasshedPassword'");
if ($stmt)
{
$user[status] = "success";
}
else
{
$user[status] = "fail";
}


$user[email] = $email;
$user[password] = $password;
$user[rate] = $rateInt;
echo json_encode($user);
}







$conn->close();
?>