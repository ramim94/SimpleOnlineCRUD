<?php
require_once 'connection.php';

$id=$_POST['id'];
$title=$_POST['title'];
$description=$_POST['description'];


$query="UPDATE `todolist` SET `title` = '".$title."', `description` = '".$description."' WHERE `todolist`.`id` =".$id;

if(mysql_query($conn,$query)){
	echo "data Updated";
}else{
	echo mysql_error();
}
?>