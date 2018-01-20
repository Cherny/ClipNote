<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/19/18
 * Time: 11:47 PM
 */

require './database/database.php';

function main()
{
    $json = $_POST['json'];
    $arg = json_decode($json);

    $sql = "SELECT * FROM cn_notebook ORDER BY id DESC LIMIT {$arg->{'index'}}, {$arg->{'num'}}";
    $database = new Database();
    $resultSet =  $database->queryWithResult($sql);
    echo json_encode($resultSet);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();