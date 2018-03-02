<?php
require_once 'connection.php';

$title=$_POST['title'];
$description=$_POST['description'];


$query="INSERT INTO `todolist` (`id`, `title`, `description`) VALUES (NULL, '".$title."', '".$description."')";

if(mysql_query($conn,$query)){
	echo "data Entered";
}else{
	echo mysql_error();
}
?>