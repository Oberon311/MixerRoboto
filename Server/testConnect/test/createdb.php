<?php
  $con=mysqli_connect("localhost", "root", "");
  $sql="CREATE DATABASE my_db";
  if (mysqli_query($con,$sql)) {
    echo "Database my_db created successfully";
  }
 ?>
