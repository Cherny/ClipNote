<?php
include 'config.php';
include dirname(__FILE__).'/../entity/NoteItem.php';

class Database {
	
	private $connect;
	function __construct()
	{
        $this->connect = new mysqli(serverName, userName, password,database);
    }

    function __destruct()
    {
        $this->connect->close();
    }
    
    function query($sql)
    {
        $result = $this->connect->query($sql);
        if($result)
            return true;
        else
            return false;
    }

    function queryWithResult($sql)
    {
        $result = $this->connect->query($sql);

        $resultSet = array();

        if ($result->num_rows > 0) {
            // 输出数据
            while($row = $result->fetch_assoc()) {
                $item = new NoteItem($row["id"], "{$row["body"]}", "{$row["date_time"]}");
                array_push($resultSet, $item);
            }
        }
        return $resultSet;
    }
}
