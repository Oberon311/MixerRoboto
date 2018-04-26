<?php
  $con=mysqli_connect("localhost", "root", "", "userdb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }


  $email = $_POST['email'];
  $lname = $_POST['lname'];
  $fname = $_POST['fname'];
  $dob = $_POST['dob'];
  $expDate = $_POST['expDate'];
  $address = $_POST['address'];
  $city = $_POST['city'];
  $state = $_POST['state'];
  $sex = $_POST['sex'];
  $weight = $_POST['weight'];
  $height = $_POST['height'];

  $result = mysqli_query($con, "INSERT INTO user (userID, fname, lname, sex, height, weight, dob, address, adminCode, email, city, state, expdate)
                                  VALUES (NULL, '$fname', '$lname', '$sex','$height','$weight', STR_TO_DATE('$dob', '%m-%d-%Y'), '$address', NULL, '$email', '$city', '$state', STR_TO_DATE('$expDate', '%m-%d-%Y') )");

  mysqli_close($con);
 ?>
