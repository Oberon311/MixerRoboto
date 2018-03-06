<?php
  $con=mysqli_connect("localhost", "root", "", "my_db");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $username = $_POST['username'];
  $result = mysqli_query($con, "INSERT INTO table2 (name) VALUES ('$username')");

  mysqli_close($con);
 ?>
