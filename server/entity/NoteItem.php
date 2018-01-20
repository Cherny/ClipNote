<?php

/**
* entity og note that passing on the internet
 */
class NoteItem
{
	public $ID = -1;
    public $BODY = "";
    public $DATETIME = "";
	
	function __construct($id=0, $body="", $dateTime="")
	  {
		$this->ID = $id;
		$this->BODY = $body;
		$this->DATETIME = $dateTime;
	}

    function fromJson($json)
    {
        $val = json_decode($json);
        $this->ID = $val->ID;
        $this->BODY = $val->BODY;
        $this->DATETIME = $val->DATETIME;
    }
	
	function toJson()
	  {
		return json_encode($this);
	}

}

?>
