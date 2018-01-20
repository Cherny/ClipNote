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
    echo $database->query($sql)? 0: -1;
}

if ($_SERVER['REQUEST_METHOD'] == 'POST')
    main();