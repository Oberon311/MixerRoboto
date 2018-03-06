<?php
   $con=mysqli_connect("localhost", "root", "","my_db");
   $sql="INSERT INTO table1 (Username, Password, Role) VALUES ('admin', 'password','adminstrator')";
   if (mysqli_query($con,$sql)) {
      echo "Values have been inserted successfully";
   }
?>
