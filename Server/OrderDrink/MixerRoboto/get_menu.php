<?php
  $con=mysqli_connect("localhost", "root", "", "bardb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $result = mysqli_query($con, "SELECT name FROM recipe");

  while ($row = $result->fetch_array()) {
      echo $row['name']."<br>";
  }

  mysqli_close($con);
 ?>
