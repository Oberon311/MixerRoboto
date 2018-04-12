<?php
  $con=mysqli_connect("localhost", "root", "", "bardb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $id = $_POST['id'];
  $ip = $_POST['ip'];

  //get ID of drink by name. Not best method, but can be modified later
//  $result = mysqli_query($con, "SELECT name FROM recipe WHERE recipeID = '$id'");
//  $row = mysqli_fetch_array($result);
//  $id = $row[0];

  $result = mysqli_query($con, "SELECT price FROM recipe WHERE recipeID = '$id'");
  $row = mysqli_fetch_array($result);
  $price = $row[0];

  $date = date('Y-m-d');
  $time = date('H:i:s');

  //Create transaction for drink order
  //transacitonID must be NULL so that the ID auto-increments
  $result = mysqli_query($con, "INSERT INTO transactions (transactionID, userID, date, time, price, recipeID, ipAddress, drinkDone)
                                  VALUES (NULL, '1', '$date', '$time', '$price', '$id', '$ip', '0')");

  mysqli_close($con);
 ?>
