



<?php
//getting the database connection
require_once 'conn.php';

//an array to display response
$response = array();

//if it is an api call
//that means a get parameter named api call is set in the URL
//and with this parameter we are concluding that it is an api call
if(isset($_GET['apicall'])){

    switch($_GET['apicall']){



        case 'get':

///////// START PISTAS GET
            case 'get_timeslot':




                //checking if the user is already exist with this username or email
                //as the email and username should be unique for every user
                $stmt = $conn->prepare("SELECT id,description FROM timeslot");
                $stmt->execute();
                $stmt->bind_result($id,$description);

                $pistas_array = array();


                while($stmt->fetch()){
                    $temp = array();

                    $temp['description'] = $description;
                    $temp['id'] = $id;


                    array_push($pistas_array, $temp);
                }
                echo json_encode($pistas_array);
                $stmt->close();

                //adding the user data in response
                $response['error'] = false;




            break;

        case 'get_pistas':




            //checking if the user is already exist with this username or email
            //as the email and username should be unique for every user
            $stmt = $conn->prepare("SELECT id,nombre FROM pista");
            $stmt->execute();
            $stmt->bind_result($id,$nombre);

            $pistas_array = array();


            while($stmt->fetch()){
                $temp = array();

                $temp['nombre'] = $nombre;
                $temp['id'] = $id;


                array_push($pistas_array, $temp);
            }
            echo json_encode($pistas_array);
            $stmt->close();

            //adding the user data in response
            $response['error'] = false;




            break;




        case 'get_agendas':

///////// START PISTAS GET
            if(isTheseParametersAvailable(array('dia'))){

                //getting the values

                $dia = $_POST['dia'];



                //checking if the user is already exist with this username or email
                //as the email and username should be unique for every user
                $stmt = $conn->prepare("SELECT dia,timeSlot,disponible,id_pista,id_partido FROM agenda WHERE dia =   ?");
                $stmt->bind_param("s",$dia);
                $stmt->execute();
                $stmt->bind_result($day,$timeslot,$disponible,$id_pista,$id_partido);

                $pistas_array = array();


                while($stmt->fetch()){
                    $temp = array();
                    $temp['dia'] = $day;
                    $temp['$timeslot'] = $timeslot;
                    $temp['disponible'] = $disponible;
                    $temp['id_partido'] = $id_partido;
                    $temp['id_pista'] = $id_pista;

                    array_push($pistas_array, $temp);
                }
                echo json_encode($pistas_array);
                $stmt->close();

                //adding the user data in response
                $response['error'] = false;





                       }else{
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }
            break;






            //in this part we will handle the registration





        case 'signup':

            if(isTheseParametersAvailable(array('DNI','nombre','apellidos','direccion','telefono','genero','fecha_nacimiento','correo','nivel_juego','preferencia','descripcion','validado','contraseña','activo'))){

                //getting the values

                $DNI = $_POST['DNI'];
                $nombre = $_POST['nombre'];
                $apellidos = $_POST['apellidos'];
                $direccion = $_POST['direccion'];
                $telefono = $_POST['telefono'];
                $genero =  $_POST['genero'];
                $fecha_nacimiento =  $_POST['fecha_nacimiento'];
                $correo =  $_POST['correo'];
                $nivel_juego =  $_POST['nivel_juego'];
                $preferencia =  $_POST['preferencia'];
                $descripcion =  $_POST['descripcion'];
                $validado =  $_POST['validado'];
                $contraseña =  $_POST['contraseña'];
                $activo =  $_POST['activo'];







                //checking if the user is already exist with this username or email
                //as the email and username should be unique for every user
                $stmt = $conn->prepare("SELECT dni FROM usuario WHERE correo =   ?");
                $stmt->bind_param("s",$correo);
                $stmt->execute();
                $stmt->store_result();

                //if the user already exist in the database
                if($stmt->num_rows > 0){
                    $response['error'] = true;
                    $response['message'] = 'User already registered';
                    $stmt->close();
                }else{

                    //if user is new creating an insert query
                    $stmt = $conn->prepare("INSERT INTO usuario (DNI, nombre, apellidos, direccion,telefono,genero, fecha_nacimiento,correo,nivel_juego,preferencia,descripcion,validado,contraseña,activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    $stmt->bind_param("ssssssssssssss", $DNI,$nombre,$apellidos,$direccion,$telefono,$genero,$fecha_nacimiento,$correo,$nivel_juego,$preferencia,$descripcion,$validado,$contraseña,$activo);

                    //if the user is successfully added to the database
                    if($stmt->execute()){

                        //fetching the user back
                        $stmt = $conn->prepare("SELECT DNI, nombre, apellidos, direccion,telefono,genero, fecha_nacimiento,correo,nivel_juego,preferencia,descripcion,validado,contraseña,activo FROM usuario WHERE correo = ?");
                        $stmt->bind_param("s",$correo);
                        $stmt->execute();
                        $stmt->bind_result($DNI,$nombre,$apellidos,$direccion,$telefono,$genero,$fecha_nacimiento,$correo,$nivel_juego,$preferencia,$descripcion,$validado,$contraseña,$activo);
                        $stmt->fetch();

                        $user = array(
                            'DNI'=>$DNI,
                            'nombre'=>$nombre,
                            'apellidos'=>$apellidos,
                            'direccion'=>$direccion,
                            'telefono'=>$telefono,
                            'genero'=>$genero,
                            'fecha_nacimiento'=>$fecha_nacimiento,
                            'correo'=>$correo,
                            'nivel_juego'=>$nivel_juego,
                            'preferencia'=>$preferencia,
                            'descripcion'=>$descripcion,
                            'validado'=>$validado,
                            'contraseña'=>$contraseña,
                            'activo'=>$activo




                        );

                        $stmt->close();

                        //adding the user data in response
                        $response['error'] = false;
                        $response['message'] = 'User registered successfully';
                        $response['user'] = $user;
                    }
                }

            }else{
                $response['error'] = true;
                $response['message'] = 'required parameters are not available';
            }



            //in this part we will handle the registration

            break;
///////////////////////////////////////////////// LOGIN  START /////////////////////////////////////////// L/////////////////////////////////////////// L
        case 'login':
            //for login we need the username and password
            if(isTheseParametersAvailable(array('correo', 'contraseña'))){
                //getting values
                $correo = $_POST['correo'];
                $contraseña = $_POST['contraseña'];

                //creating the query
                $stmt = $conn->prepare("SELECT DNI, nombre, apellidos, direccion,telefono,genero, fecha_nacimiento,correo,nivel_juego,preferencia,descripcion,validado,contraseña,activo FROM usuario WHERE correo = ? AND contraseña = ?");
                $stmt->bind_param("ss",$correo, $contraseña);

                $stmt->execute();

                $stmt->store_result();

                //if the user exist with given credentials
                if($stmt->num_rows > 0){

                    $stmt->bind_result($DNI,$nombre,$apellidos,$direccion,$telefono,$genero,$fecha_nacimiento,$correo,$nivel_juego,$preferencia,$descripcion,$validado,$contraseña,$activo);
                    $stmt->fetch();

                    $user = array(
                        'DNI'=>$DNI,
                        'nombre'=>$nombre,
                        'apellidos'=>$apellidos,
                        'direccion'=>$direccion,
                        'telefono'=>$telefono,
                        'genero'=>$genero,
                        'fecha_nacimiento'=>$fecha_nacimiento,
                        'correo'=>$correo,
                        'nivel_juego'=>$nivel_juego,
                        'preferencia'=>$preferencia,
                        'descripcion'=>$descripcion,
                        'validado'=>$validado,
                        'contraseña'=>$contraseña,
                        'activo'=>$activo




                    );
                    $response['error'] = false;
                    $response['message'] = 'Login successfull';
                    $response['user'] = $user;
                }else{
                    //if the user not found
                    $response['error'] = false;
                    $response['message'] = 'Invalid username or password';
                }
            }
            //this part will handle the login

            break;
///////////////////////////////////////////////// LOGIN END/////////////////////////////////////////// L/////////////////////////////////////////// L/////////////////////////////////////////// L






















        default:
            $response['error'] = true;
            $response['message'] = 'Invalid Operation Called';
    }

}else{
    //if it is not api call
    //pushing appropriate values to response array
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}

//displaying the response in json structure
echo json_encode($response);



//function validating all the paramters are available
//we will pass the required parameters to this function
function isTheseParametersAvailable($params){

    //traversing through all the parameters
    foreach($params as $param){
        //if the paramter is not available
        if(!isset($_POST[$param])){
            //return false
            return false;
        }
    }
    //return true if every param is available
    return true;
}