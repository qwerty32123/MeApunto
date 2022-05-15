




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
