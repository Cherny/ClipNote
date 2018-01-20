<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/19/18
 * Time: 7:35 PM
 */

require './database/database.php';

function main()
{
    $json = $_POST['json'];
    $item = new NoteItem();
    $item->fromJson($json);

    $sql = "INSERT INTO cn_notebook (body) VALUES ('{$item->BODY}');";
    $database = new Database();
    $re =  $database->query($sql);
    $id = $database->queryId();

    echo "{\"id\":$id,\"result\":$re}";
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();