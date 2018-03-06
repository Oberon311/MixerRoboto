<?php
  $con=mysqli_connect("localhost", "root", "", "my_db");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  //$variableName
  $username = $_GET['username'];
  //$username = "admin";
//  $password = $_GET['password'];
  $result = mysqli_query($con, "SELECT Role FROM table1 WHERE Username='$username'");
  $row = mysqli_fetch_array($result);
  $data = $row[0];

  if($data){
    echo $data;
  }
  mysqli_close($con);
 ?>
