<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/19/18
 * Time: 7:35 PM
 */

require './database/database.php';
require './entity/ReturnCode.php';

function main()
{
    $json = $_POST['json'];
    $item = new NoteItem();
    $item->fromJson($json);

    $sql = "INSERT INTO cn_notebook (body,date_time) VALUES ('{$item->BODY}','{$item->DATETIME}');";
    $database = new Database();
    $re =  $database->query($sql);
    $id = $database->queryId();

    $code = new ReturnCode($id,$re);
    echo json_encode($code);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();