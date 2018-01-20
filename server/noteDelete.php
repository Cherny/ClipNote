<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/19/18
 * Time: 11:42 PM
 */

require './database/database.php';

function main()
{
    $json = $_POST['json'];
    $item = new NoteItem();
    $item->fromJson($json);

    $sql = "DELETE from cn_notebook WHERE id = {$item->ID}";
    $database = new Database();
    $re =  $database->query($sql);
    $id = $item->ID;

    echo "{\"id\":$id,\"result\":$re}";
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();