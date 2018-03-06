<?php
//mysqli_query( "domain/host", "username", "password", "database name")
  $con=mysqli_connect("localhost", "root", "","my_db");
  $sql="CREATE TABLE table1(Username CHAR(30),Password CHAR(30),Role CHAR(30))";
  if (mysqli_query($con,$sql)) {
    echo "Table have been created successfully";
 }
 ?>
