<?php
  $con=mysqli_connect("localhost", "root", "", "userdb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  $email = $_POST['email'];
  $result = mysqli_query($con, "SELECT userID FROM user WHERE email = '$email'");
  $row = mysqli_fetch_array($result);
  $userid = $row[0];

//switch to bar database
  $con2=mysqli_connect("localhost", "root", "", "bardb");
  mysql_select_db('bardb', $con2);

  $id = $_POST['id'];
  $ip = $_POST['ip'];

  $result = mysqli_query($con2, "SELECT price FROM recipe WHERE recipeID = '$id'");
  $row = mysqli_fetch_array($result);
  $price = $row[0];

  $date = date('Y-m-d');
  $time = date('H:i:s');


  //Create transaction for drink order
  //transacitonID must be NULL so that the ID auto-increments
  $result = mysqli_query($con2, "INSERT INTO transactions (transactionID, user, date, time, price, recipe, ip, drinkDone)
                                  VALUES (NULL, '$userid', '$date', '$time', '$price', '$id', '$ip', '0')");

  mysqli_close($con2);
 ?>
