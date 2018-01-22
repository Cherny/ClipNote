<?php
/**
 * Created by PhpStorm.
 * User: cherny
 * Date: 1/23/18
 * Time: 12:13 AM
 */

class ReturnCode
{
    public $id = 0;
    public $result = false;

    function __construct($id,$result)
    {
        $this->id = $id;
        $this->result = $result;
    }
}