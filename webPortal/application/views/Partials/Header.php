<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Paykind Loyalty | Dashboard</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="<?php echo base_url(); ?>assets/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- jvectormap -->
    <link rel="stylesheet" href="<?php echo base_url(); ?>assets/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<?php echo base_url(); ?>assets/dist/css/AdminLTE.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<?php echo base_url(); ?>assets/dist/css/skins/_all-skins.min.css">


    <!-- jQuery 2.1.4 -->
    <script src="<?php echo base_url(); ?>assets/plugins/jQuery/jQuery-2.1.4.min.js"></script>
    <!-- Bootstrap 3.3.5 -->
    <script src="<?php echo base_url(); ?>assets/bootstrap/js/bootstrap.min.js"></script>


    <script src="<?php echo base_url(); ?>assets/bootstrap/js/bootbox.min.js"></script>
    <script src="<?php echo base_url(); ?>assets/bootstrap/js/bootstrapValidator.js"></script>
    <script src="<?php echo base_url(); ?>assets/bootstrap/js/bootstrapValidator.min.js"></script>


    <!-- FastClick -->
    <script src="<?php echo base_url(); ?>assets/plugins/fastclick/fastclick.min.js"></script>
    <!-- AdminLTE App -->
    <script src="<?php echo base_url(); ?>assets/dist/js/app.min.js"></script>
    <!-- Sparkline -->
    <script src="<?php echo base_url(); ?>assets/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!-- jvectormap -->
    <script src="<?php echo base_url(); ?>assets/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="<?php echo base_url(); ?>assets/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <!-- SlimScroll 1.3.0 -->
    <script src="<?php echo base_url(); ?>assets/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <!-- ChartJS 1.0.1 -->
    <script src="<?php echo base_url(); ?>assets/plugins/chartjs/Chart.min.js"></script>
    <!-- AdminLTE dashboard demo (This is only for demo purposes) -->
    <!--<script src="<?php echo base_url(); ?>assets/dist/js/pages/dashboard2.js"></script>-->
    <!-- AdminLTE for demo purposes -->
    <script src="<?php echo base_url(); ?>assets/dist/js/demo.js"></script>



    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->


    <script type="text/javascript">



$(document).ready(function() {

  
    $('#changePassForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            oldpass: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in the Old Password!"
                    }
                }
            },

            newpass: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in a New Password!"
                    }
                }
            },

            confpass: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in a Confirmation Password!"
                    },

                    identical: {
                      field: 'newpass',
                      message: "New Password and Confirm Password must match!"
                    }

                }
            }
        }
    })
  .on('success.form.bv', function(e) {
      // Prevent form submission
      e.preventDefault();
      // Get the form instance
      var $form = $(e.target);

      // Get the BootstrapValidator instance
      var bv = $form.data('bootstrapValidator');

      // Use Ajax to submit form data
   $.ajax({
      url: '<?php echo base_url(); ?>Welcome/changePass',
      type: 'post',
      data: $('#changePassForm :input'),
      dataType: 'html',
      success: function(html) {
        bootbox.alert(html);
       if(html == 'Password Changed Successfully')
      {
         $('#changePassForm')[0].reset();
         $('#changePass').modal('hide');
         location.reload();
      }

      if(html == 'Wrong Old Password')
      {
         $('#changePassForm')[0].reset();
         $('#changePass').modal('hide');
         location.reload();
      }
      

      }
    });
  });
});


</script>  




  </head>
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">

      <header class="main-header">

        <!-- Logo -->
        <a href="<?php echo base_url(); ?>Welcome" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>P</b>L</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>Pay</b>Kind</span>
        </a>

        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <!-- Navbar Right Menu -->
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
             
              

              <?php 
                  $login=$this->session->userdata('logged_in');                  
              ?>


             
              <!-- User Account: style can be found in dropdown.less -->
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  
                  <span class="hidden-xs"><?php echo $login['name']; ?></span>
                </a>
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    
                    <p>
                      <?php echo $login['name']; ?> - <?php echo $login['username']; ?>
                      <small>System Administrator</small>
                    </p>
                  </li>
                  <!-- Menu Body -->
                 
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="#" data-toggle="modal" data-target="#changePass" class="btn btn-default btn-flat">Change Password</a>                      
                    </div>
                    <div class="pull-right">
                      <a href="<?php echo base_url(); ?>Welcome/logout" class="btn btn-default btn-flat">Log out</a>
                    </div>
                  </li>
                </ul>
              </li>
              <!-- Control Sidebar Toggle Button -->
              <li>
                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
              </li>
            </ul>
          </div>

        </nav>
      </header>


       <div id="changePass" class="modal fade">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header" style="color:#0093af">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                      <center>
                        <h4 class="modal-title">Change Password</h4>
                      </center>
                  </div>
                  <div class="modal-body" id="edit-user-content">
                      <form  id="changePassForm" class="form-horizontal style-form" method="post">
                          
                              <div class="form-group">
                                  <label class="col-sm-4 control-label">Old Password</label>
                                  <div class="col-sm-8">
                                      <input type="hidden" name="hidden_id" value="<?php echo $login['id']; ?>" class="form-control">
                                      <input type="password" name="oldpass" class="form-control">
                                  </div>
                              </div>

                              <div class="form-group">
                                  <label class="col-sm-4 control-label">New Password</label>
                                  <div class="col-sm-8">
                                      <input type="password" name="newpass" class="form-control">
                                  </div>
                              </div>

                              <div class="form-group">
                                  <label class="col-sm-4 control-label">Confirm Password</label>
                                  <div class="col-sm-8">
                                      <input type="password" name="confpass" class="form-control">
                                  </div>
                              </div>

                              

                          <div class="modal-footer">
                            <center>
                              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                              <button type="submit" class="btn btn-primary">Save</button>
                            </center>
                          </div>
                              
                    </form>
                      
                  </div>
                  
              </div>
          </div>
      </div>








      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          
          
          <!-- search form -->
          <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
              <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i></button>
              </span>
            </div>
          </form>
          <!-- /.search form -->
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="header">MAIN NAVIGATION</li>
            <li class="active treeview">
              <a href="<?php echo base_url(); ?>Welcome">
                <i class="fa fa-dashboard"></i> <span>Dashboard</span>
              </a>
              
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-credit-card"></i>
                <span>Card Holders</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="<?php echo base_url(); ?>Card_holders"><i class="fa fa-circle-o"></i> Active Card Holders</a></li>
                <li><a href="<?php echo base_url(); ?>Card_holders/inactive"><i class="fa fa-circle-o"></i> Inactive Card Holders</a></li>
               
              </ul>
            </li>

            <li class="treeview">
              <a href="#">
                <i class="fa fa-exchange"></i>
                <span>Transactions</span>
                <i class="fa fa-angle-left pull-right"></i>
              </a>
              <ul class="treeview-menu">
                <li><a href="<?php echo base_url(); ?>Purchases"><i class="fa fa-circle-o"></i> Purchases</a></li>
                <li><a href="<?php echo base_url(); ?>Purchases/points"><i class="fa fa-circle-o"></i> Point Redemptions</a></li>
              </ul>
            </li>
            
            <li class="treeview">
              <a href="<?php echo base_url(); ?>Merchants">
                <i class="fa fa-pie-chart"></i>
                <span>Merchants</span>
                
              </a>
              
            </li>

            <li>
              <a href="<?php echo base_url(); ?>Users">
                <i class="fa fa-users"></i> <span>Users</span>
              </a>
            </li>


            
                 
         
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>




      


