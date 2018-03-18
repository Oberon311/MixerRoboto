<?php
  $con=mysqli_connect("localhost", "root", "", "bardb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $drink = $_POST['name'];
  //$drink = 'Harvey Wallbanger';

  //get ID of drink by name. Not best method, but can be modified later
  $result = mysqli_query($con, "SELECT recipeID FROM recipe WHERE name = '$drink'");
  $row = mysqli_fetch_array($result);
  $id = $row[0];

  //Create transaction for drink order
  //transacitonID must be NULL so that the ID auto-increments
  $result = mysqli_query($con, "INSERT INTO transactions (transactionID, userID, date, time, price, recipeID, ipAddress)
                                  VALUES (NULL, '1', '2018-03-15', '13:21:00', '9.00', '$id', '123.123.123.132')");

  mysqli_close($con);
 ?>
