<script type="text/javascript">





$(document).ready(function() {

    $('#addForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            name: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in a Merchant Name!"
                    }
                }
            },

            username: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in a Username!"
                    }
                }
            },
            
            

            password: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in a Password!"
                    }
                }
            },

         

            confpass: {
                validators: {
                    notEmpty: {
                        message: "You're required to Confirm your Password!"
                    },

                     identical: {
                      field: 'password',
                      message: 'Password and Confirm Password must match'
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
      url: '<?php echo base_url(); ?>Users/add',
      type: 'post',
      data: $('#addForm :input'),
      dataType: 'html',   
      success: function(html) {
      bootbox.alert(html);
       if(html == 'User Added Successfully')
      {
         $('#addForm')[0].reset();
         $('#addMerchant').modal('hide');
         location.reload();
      }    

      }
    });
  });
});




function rm(nm,id){
  bootbox.confirm("Are you sure you want to delete " + nm + "?", function(result) {
      if(result) {
      
    $.ajax({
    url: '<?php echo base_url(); ?>Users/delete/' + id,
    type: 'post',
    data: {id: id},
    dataType: 'html',   
    success: function(html) {
        bootbox.alert(html);
        if(html == 'User Deleted Successfully')
        location.reload();
    }
  });
    
    }
    
  }); 
}




</script>  


<div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Users
            
          </h1>
          
        </section>



<section class="content">
<div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
                  
                  <div class="panel-heading">              
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addServiceProvider"><span class="fa fa-plus"></span> Add User</button>
                  </div>

                  <div class="box-tools">
                    <div class="input-group" style="width: 150px;">
                      

                      <form name="search" method="post" action="">
                    <div class="input-group custom-search-form">
                      <input type="text" id="search" name="search" class="form-control input-sm pull-right" placeholder="Search...">
                      <span class="input-group-btn">
                        
                     </span>
                    </div>
                  </form>


                      
                     
                    </div>
                  </div>
                </div><!-- /.box-header -->
                <div class="box-body table-responsive no-padding">
                  <table class="table table-hover">
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Username</th>
                      <th>Action</th>
                      
                    </tr>

                    
                    <?php  
                
                foreach ($users->result() as $user){?>
                
                <tr>
                    <td><?php echo $user->id; ?></td>
                    <td><?php echo $user->name; ?></td>
                    <td><?php echo $user->username; ?></td>

                     <td>

                      <a href="javascript:void(0);" onclick="rm('<?php echo $user->name; ?>','<?php echo $user->id; ?>');">
                    <span class="btn btn-danger"><span class="fa fa-times"></span>&nbsp;Delete</span></a>
                    </td>
                                    
                    
                    
                </tr>
                    <?php } ?>



                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
          </div>
        </section>

        </div>








        <div id="addServiceProvider" class="modal fade" style="">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header" style="color:#0093af">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                      <center>
                        <h4 class="modal-title">Add New User</h4>
                      </center>
                  </div>
                  <div class="modal-body" >
                      <form id="addForm" class="form-horizontal style-form" method="post">
                              
                              <div class="row">
                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">Full Name</label>
                                    <div class="col-sm-6">
                                      <input type="text" name="name" placeholder="Full Name" class="form-control">
                                    </div>
                              </div>
                              
                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">Username</label>
                                    <div class="col-sm-6">
                                      <input type="text" name="username" placeholder="Username" class="form-control">
                                    </div>
                              </div>
                              
                              

                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">Password</label>
                                  <div class="col-sm-6">
                                      <input type="password" name="password" placeholder="Password" class="form-control">
                                  </div>
                              </div>

                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">Confirm Password</label>
                                  <div class="col-sm-6">
                                      <input type="password" name="confpass" placeholder="Confirm Password" class="form-control">
                                  </div>
                              </div>
                              </div>

                              
                        
                      
                  </div>
                  <div class="modal-footer">
                      <center>
                      
                          <button type="button" class="btn btn-default col-sm-3 col-sm-offset-5" style="margin-right:5%" data-dismiss="modal">Close</button>
                          <button type="submit" class="btn btn-primary col-sm-3">Add</button>
                      
                      </center>
                  </div>

                    </div>
                  </form>
              </div>
          </div>
      </div>