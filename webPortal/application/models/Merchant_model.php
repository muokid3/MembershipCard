<?php
class Merchant_model extends CI_Model {
	
	private $merchants= 'merchants';
	
	function __construct(){
		parent::__construct();
	}
	
	

	
	function get_merchant_list($searchvalue = null)
	{
		if ($searchvalue == null) 
		{
			$this->db->select('merchants.*');
		   $this->db->from('merchants');	   
		   return $this->db->get();
		}
		else
		{
			$search_where = "merchants.name LIKE '%".$searchvalue."%' OR merchants.location LIKE '%".$searchvalue."%' OR merchants.email LIKE '%".$searchvalue."%'";
			$this->db->select('merchants.*');
		   $this->db->from('merchants'); 
		   $this->db->where($search_where);		   
		   return $this->db->get();
		}
       

    }

    function save($merchant)
    {
		$this->db->insert($this->merchants, $merchant);
		return $this->db->insert_id();
	}

	function delete($id)
	{
		$this->db->where('id', $id);
		return $this->db->delete($this->merchants);
	}

  
}
?>