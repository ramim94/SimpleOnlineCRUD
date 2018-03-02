<?php
require_once 'connection.php';

$id=$_POST['id'];
$query="DELETE FROM `todolist` WHERE `todolist`.`id` =".$id;

if(mysqli_query($conn,$query)){
	echo "data Deleted";
}else{
	echo mysql_error();
}
?>