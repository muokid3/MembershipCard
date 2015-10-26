<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Login extends CI_Controller {

	public function __construct ()
	
	{
	
	    parent::__construct();
		
		// load library
		$this->load->library(array('table','form_validation'));
		
		// load helper
		//$this->load->helper('url');
		//$this->load->library('session');
		
		// load model
		$this->load->model('Login_model','',TRUE);
	    
	}
	
	public function index()
	
	{
	
		$this->load->view('Login/loginView');
	
	}
		   
	function verifylogin()
	{
		//This method will have the credentials validation
		$this->load->library('form_validation');

		$this->form_validation->set_rules('username', 'Username', 'trim|required|xss_clean');
		$this->form_validation->set_rules('password', 'Password', 'trim|required|xss_clean|callback_check_database');
		
		
		if($this->form_validation->run() == FALSE)
		{
		  //Field validation failed.  User redirected to login page
		  
			
			$this->load->view('Login/loginView');			
			
		}

		else

		{

		  //Go to private area
		
			redirect('Welcome', 'refresh');
		}
    
  }

  
  
  function check_database($password)
  {
    //Field validation succeeded.  Validate against database
    $username = $this->input->post('username');
    
    //query the database
    $result = $this->Login_model->login($username, $password);
    
    if($result)
    {
      $sess_array = array();
      foreach($result as $row)
      {
        $sess_array = array(
          'id' => $row->id,
          'username' => $row->username,
          'name' => $row->name
        );
        $this->session->set_userdata('logged_in', $sess_array);
      }

      return TRUE;
    }

    else

    {

      $this->form_validation->set_message('check_database', 'Invalid username or password');
      return false;
      
    }
  }


}
