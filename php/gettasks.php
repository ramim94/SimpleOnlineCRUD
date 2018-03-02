<?php
require_once 'connection.php';
$query="SELECT * FROM `todolist`";

if($result=mysqli_query($conn,$query)){
	$alldata=array();
	while($row=mysqli_fetch_assoc($result)){
		$alldata[]=$row;
	}
	echo json_encode($alldata);
}else{
	echo mysql_error();
}
?>