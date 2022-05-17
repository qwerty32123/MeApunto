<?php
//getting the database connection
require_once 'conn.php';
$dni=$_POST['dni'];
$nombre=$_POST['nombre'];
$apellidos=$_POST['apellidos'];
$correo=$_POST['correo'];
$telefono=$_POST['telefono'];
$descripcion=$_POST['descripcion'];
$direccion=$_POST['direccion'];
$contraseña=$_POST['contraseña'];
$usuario=$_POST['usuario'];
$update="UPDATE usuario SET  dni='".$dni."', nombre='".$nombre."', apellidos='".$apellidos."', direccion='".$direccion."', telefono ='".$telefono."',  correo ='".$correo."', descripcion ='".$descripcion."', contraseña ='".$contraseña."' WHERE correo ='".$usuario."'";
//
mysqli_query($conn,$update) or die (mysqli_error());
mysqli_close($conn);

?>