<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Merchants extends CI_Controller {

	// num of records per page
	private $limit = 10;
	
	function __construct()
	{
		parent::__construct();
		
		// load library
		$this->load->library(array('table','form_validation'));
		
		
		// load model
		$this->load->model('Merchant_model','',TRUE);
		
	}
	
	function index($offset = 0)
	{
		
		if ($this->session->userdata('logged_in')) 
		{
			
				// offset
				$uri_segment = 3;
				$offset = $this->uri->segment($uri_segment);
				
				
				$searchvalue = $this->input->post('search');
				// load view
				$data['merchants'] = $this->Merchant_model->get_merchant_list($searchvalue);
				//$data['roles'] = $this->Service_provider_model->get_roles();
				$this->load->view('Partials/Header');
				$this->load->view('Merchants/merchantList', $data);
				$this->load->view('Partials/Footer');
			
		} 
		else
		{
			redirect('login', 'refresh');
		}

		
	}

	function add()
	
	{
	
			$merchant = array('name' => $this->input->post('name'),
					'location' => $this->input->post('location'),
					'email' => $this->input->post('email'),										
					'password' => md5($this->input->post('password')));

			if ($this->Merchant_model->save($merchant)) 
			{
				echo "Merchant Added Successfully";
			}
			else
			{
				echo "Merchant Not Added";
			}
		
	}

	public function delete($id)	
	{
	
		if ($this->Merchant_model->delete($id)) {
			echo "Merchant Deleted Successfully";
		}		
		
	}

	
}
?>