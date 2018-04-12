<?php
  $con=mysqli_connect("localhost", "root", "", "bardb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $result = mysqli_query($con, "SELECT name, recipeID, price FROM recipe");

  while ($row = $result->fetch_array()) {
      echo $row['recipeID']."<br>";
      echo $row['name'];
      echo " - $";
      echo $row['price']."<br>";
  }

  mysqli_close($con);
 ?>
