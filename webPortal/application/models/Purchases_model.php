<?php
class Purchases_model extends CI_Model {
	
	private $transactions = 'transactions';
	private $redemptions = 'redemptions';
	
	function __construct(){
		parent::__construct();
	}
	
	
	
	
	function get_purchases_list($searchvalue = null){
		if ($searchvalue == null) 
		{
			$this->db->select('transactions.*');
		   $this->db->from('transactions');	
		   $this->db->order_by('id','desc');	  	   
		   return $this->db->get();
		}
		else
		{
			$search_where = "transactions.account LIKE '%".$searchvalue."%' OR transactions.name LIKE '%".$searchvalue."%' 
			OR transactions.amount LIKE '%".$searchvalue."%' OR transactions.points LIKE '%".$searchvalue."%'";
			$this->db->select('transactions.*');
		   $this->db->from('transactions');	   
		   $this->db->where($search_where);	
		   $this->db->order_by('id','desc');	   
		   return $this->db->get();
		}
       

    }

    function get_points_list($searchvalue = null)
    {
		if ($searchvalue == null) 
		{
			$this->db->select('redemptions.*');
		   $this->db->from('redemptions');		  
		   	$this->db->order_by('id','desc');   
		   return $this->db->get();
		}
		else
		{
			$search_where = "redemptions.name LIKE '%".$searchvalue."%' OR redemptions.account LIKE '%".$searchvalue."%' 
			OR redemptions.points LIKE '%".$searchvalue."%'";
			$this->db->select('redemptions.*');
		   $this->db->from('redemptions');		   
		    $this->db->order_by('id','desc');
		   $this->db->where($search_where);		   
		   return $this->db->get();
		}
       

    }

  
}
?>