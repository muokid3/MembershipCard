<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Users extends CI_Controller {

	// num of records per page
	private $limit = 10;
	
	function __construct()
	{
		parent::__construct();
		
		// load library
		$this->load->library(array('table','form_validation'));
		
		
		// load model
		$this->load->model('Users_model','',TRUE);
		
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
				$data['users'] = $this->Users_model->get_user_list($searchvalue);
				//$data['roles'] = $this->Service_provider_model->get_roles();
				$this->load->view('Partials/Header');
				$this->load->view('Users/userList', $data);
				$this->load->view('Partials/Footer');
			
		} 
		else
		{
			redirect('login', 'refresh');
		}

		
	}

	function add()
	
	{
	
			$user = array('name' => $this->input->post('name'),
					'username' => $this->input->post('username'),																				
					'password' => md5($this->input->post('password')));

			if ($this->Users_model->save($user)) 
			{
				echo "User Added Successfully";
			}
			else
			{
				echo "User Not Added";
			}
		
	}

	public function delete($id)
	
	{
	
		if ($this->Users_model->delete($id)) {
			echo "User Deleted Successfully";
		}		
		
	}

	
}
?>