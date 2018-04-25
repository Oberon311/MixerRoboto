<?php
  $con=mysqli_connect("localhost", "root", "", "userdb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $email = $_GET['email'];

  $result = mysqli_query($con, "SELECT userID FROM user WHERE email= '$email'");

  $row = mysqli_fetch_array($result);
  echo $row[0];

  mysqli_close($con);
 ?>
