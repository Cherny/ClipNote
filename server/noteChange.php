<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/19/18
 * Time: 11:36 PM
 */
require './database/database.php';
require './entity/ReturnCode.php';

function main()
{
    $json = $_POST['json'];
    $item = new NoteItem();
    $item->fromJson($json);

    $sql = "UPDATE cn_notebook SET body = '{$item->BODY}',date_time = '{$item->DATETIME}' WHERE id = {$item->ID}";
    $database = new Database();
    $re =  $database->query($sql);
    $id = $database->queryId();
    $code = new ReturnCode($id,$re);

    echo json_encode($code);
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();