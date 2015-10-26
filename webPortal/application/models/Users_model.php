<?php
class Users_model extends CI_Model {
	
	private $admin= 'admin';
	
	function __construct(){
		parent::__construct();
	}
	
	

	
	function get_user_list($searchvalue = null)
	{
		if ($searchvalue == null) 
		{
			$this->db->select('admin.*');
		   $this->db->from('admin');	   
		   return $this->db->get();
		}
		else
		{
			$search_where = "admin.name LIKE '%".$searchvalue."%' OR admin.username LIKE '%".$searchvalue."%'";
			$this->db->select('admin.*');
		   $this->db->from('admin'); 
		   $this->db->where($search_where);		   
		   return $this->db->get();
		}
       

    }

     function save($user)
    {
		$this->db->insert($this->admin, $user);
		return $this->db->insert_id();
	}

	function delete($id)
	{
		$this->db->where('id', $id);
		return $this->db->delete($this->admin);
	}

	function update($id, $user){
		$this->db->where('id', $id);
		$this->db->update($this->admin, $user);
	}

  
}
?>