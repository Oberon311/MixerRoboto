<?php
  $con=mysqli_connect("localhost", "root", "", "userdb");

  if(mysqli_connect_errno($con)) {
    echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

  //$userID = $_POST['username'];
  //$userID = $_POST['userid'];
  //$name = $_POST['name'];
  $userID = '11';
  $name = "Tricky Doggo";
  $sex = "M";
  $weight = "200";
  $height = "5-11";
  $dob = "2002-02-28";
  $address = "123 Ocean Ave";

  $email = $_POST['email'];
  
  $result = mysqli_query($con, "INSERT INTO user (userID, name, sex, weight, height, dob, address, adminCode, email)
                                  VALUES ('$userID','$name','$sex','$weight','$height','$dob','$address', NULL, '$email')");

  mysqli_close($con);
 ?>
