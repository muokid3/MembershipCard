<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Card_holders extends CI_Controller {

	// num of records per page
	private $limit = 10;
	
	function __construct()
	{
		parent::__construct();
		
		// load library
		$this->load->library(array('table','form_validation'));
		
		
		// load model
		$this->load->model('Card_holders_model','',TRUE);
		
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
				$data['cardholders'] = $this->Card_holders_model->get_active_list($searchvalue);
				//$data['roles'] = $this->Service_provider_model->get_roles();
				$this->load->view('Partials/Header');
				$this->load->view('Card_holders/activeList', $data);
				$this->load->view('Partials/Footer');
			
		} 
		else
		{
			redirect('Login', 'refresh');
		}

		
	}

	function inactive($offset = 0)
	{
		
		if ($this->session->userdata('logged_in')) 
		{
			
				// offset
				$uri_segment = 3;
				$offset = $this->uri->segment($uri_segment);
				
				
				$searchvalue = $this->input->post('search');
				// load view
				$data['cardholders'] = $this->Card_holders_model->get_inactive_list($searchvalue);
				$this->load->view('Partials/Header');
				$this->load->view('Card_holders/inActiveList', $data);
				$this->load->view('Partials/Footer');
			
		} 
		else
		{
			redirect('login', 'refresh');
		}

		
	}


	public function suspend($id)	
	{
		$sus = array('active' => 0);
	
		$this->Card_holders_model->suspend($id, $sus);
		echo "Card Suspended";
				
	}

	public function activate($id)	
	{
	
		$sus = array('active' => 1);
	
		$this->Card_holders_model->suspend($id, $sus);
		echo "Card Activated";	
		
	}

	

	
	
	
}
?>