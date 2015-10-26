<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Purchases extends CI_Controller {

	
	
	function __construct()
	{
		parent::__construct();
		
		// load library
		$this->load->library(array('table','form_validation'));
		
		
		// load model
		$this->load->model('Purchases_model','',TRUE);
		
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
				$data['purchases'] = $this->Purchases_model->get_purchases_list($searchvalue);
				//$data['roles'] = $this->Service_provider_model->get_roles();
				$this->load->view('Partials/Header');
				$this->load->view('Purchases/purchasesList', $data);
				$this->load->view('Partials/Footer');
			
		} 
		else
		{
			redirect('login', 'refresh');
		}

		
	}

	function points($offset = 0)
	{
		
		/*if ($this->session->userdata('logged_in')) 
		{*/
			
				// offset
				$uri_segment = 3;
				$offset = $this->uri->segment($uri_segment);
				
				
				$searchvalue = $this->input->post('search');
				// load view
				$data['points'] = $this->Purchases_model->get_points_list($searchvalue);
				//$data['roles'] = $this->Service_provider_model->get_roles();
				$this->load->view('Partials/Header');
				$this->load->view('Purchases/pointsList', $data);
				$this->load->view('Partials/Footer');
			
		/*} 
		else
		{
			redirect('login', 'refresh');
		}*/

		
	}

	
}
?>