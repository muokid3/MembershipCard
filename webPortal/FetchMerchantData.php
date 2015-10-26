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



$username = $_POST['username'];
$unhashedPassword = $_POST['password'];
$password = md5($unhashedPassword );

$stmt=$conn->query("SELECT * FROM merchants WHERE email = '$username' AND password = '$password'");

$user = array();

while ($row=$stmt->fetch_assoc())
{
	$user[name] = $row['name'];
	$user[email] = $row['email'];
	$user[password] = $unhashedPassword ;
	$user[rate] = $row['rate'];

}


echo json_encode($user);

$stmt->close();

$conn->close();
?>