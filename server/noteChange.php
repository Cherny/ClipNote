<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/19/18
 * Time: 11:36 PM
 */
require './database/database.php';

function main()
{
    $json = $_POST['json'];
    $item = new NoteItem();
    $item->fromJson($json);

    $sql = "UPDATE cn_notebook SET body = '{$item->BODY}' WHERE id = {$item->ID}";
    $database = new Database();
    echo $database->query($sql)? 0: -1;
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();