<?php
class Card_holders_model extends CI_Model {
	
	private $users= 'users';
	
	function __construct(){
		parent::__construct();
	}
	
	
	
	
	function get_active_list($searchvalue = null){
		if ($searchvalue == null) 
		{
			$this->db->select('users.*');
		   $this->db->from('users');		  
		   $this->db->where('users.active', 1);		   
		   return $this->db->get();
		}
		else
		{
			$search_where = "users.name LIKE '%".$searchvalue."%' OR users.email LIKE '%".$searchvalue."%' OR users.phone LIKE '%".$searchvalue."%'
			 OR users.account LIKE '%".$searchvalue."%'";
			$this->db->select('users.*');
		   $this->db->from('users');		   
		   $this->db->where('users.active', 1);
		   $this->db->where($search_where);		   
		   return $this->db->get();
		}
       

    }

    function get_inactive_list($searchvalue = null)
    {
		if ($searchvalue == null) 
		{
			$this->db->select('users.*');
		   $this->db->from('users');		  
		   $this->db->where('users.active', 0);		   
		   return $this->db->get();
		}
		else
		{
			$search_where = "users.name LIKE '%".$searchvalue."%' OR users.email LIKE '%".$searchvalue."%' OR users.phone LIKE '%".$searchvalue."%'
			 OR users.account LIKE '%".$searchvalue."%'";
			$this->db->select('users.*');
		   $this->db->from('users');		   
		   $this->db->where('users.active', 0);
		   $this->db->where($search_where);		   
		   return $this->db->get();
		}
       

    }

    function suspend ($id, $sus)
    {
    	$this->db->where('id', $id);
    	$this->db->update($this->users, $sus);
    }

  
}
?>