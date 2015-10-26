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

            location: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in a Location!"
                    }
                }
            },
            
            email: {
                validators: {
                    notEmpty: {
                        message: "You're required to fill in an Email Address!"
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
      url: '<?php echo base_url(); ?>Merchants/add',
      type: 'post',
      data: $('#addForm :input'),
      dataType: 'html',   
      success: function(html) {
      bootbox.alert(html);
       if(html == 'Merchant Added Successfully')
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
    url: '<?php echo base_url(); ?>Merchants/delete/' + id,
    type: 'post',
    data: {id: id},
    dataType: 'html',   
    success: function(html) {
        bootbox.alert(html);
        if(html == 'Merchant Deleted Successfully')
        location.reload();
    }
  });
    
    }
    
  }); 
}


function decline(id){
  bootbox.confirm("Are you sure you want to decline this transaction? ", function(result) {
      if(result) {
      
    $.ajax({
    url: '<?php echo base_url(); ?>processing/decline/' + id,
    type: 'post',
    data: {id: id},
    dataType: 'html',   
    success: function(html) {
        bootbox.alert(html);
        if(html == 'Transaction Declined')
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
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addMerchant"><span class="fa fa-plus"></span> Add Merchant</button>
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
                      <th>Location</th>
                      <th>Email</th>
                      <th>Action</th>
                      
                    </tr>

                    
                    <?php  
                
                foreach ($merchants->result() as $merchant){?>
                
                <tr>
                    <td><?php echo $merchant->id; ?></td>
                    <td><?php echo $merchant->name; ?></td>
                    <td><?php echo $merchant->location; ?></td>
                    <td><?php echo $merchant->email; ?></td>

                     <td>
                      <a href="javascript:void(0);" onclick="rm('<?php echo $merchant->name; ?>','<?php echo $merchant->id; ?>');">
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








        <div id="addMerchant" class="modal fade" style="">
          <div class="modal-dialog">
              <div class="modal-content">
                  <div class="modal-header" style="color:#0093af">
                      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                      <center>
                        <h4 class="modal-title">Add New Merchant</h4>
                      </center>
                  </div>
                  <div class="modal-body" >
                      <form id="addForm" class="form-horizontal style-form" method="post">
                              <div class="row">
                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">Merchant Name</label>
                                    <div class="col-sm-6">
                                      <input type="text" name="name" placeholder="Merchant Name" class="form-control">
                                    </div>
                              </div>
                              
                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">Location</label>
                                    <div class="col-sm-6">
                                      <input type="text" name="location" placeholder="Location" class="form-control">
                                    </div>
                              </div>

                              <div class="form-group col-sm-12" style="border-bottom:none; padding:0px">
                                  <label style="color:#000; padding-left:20%" class="col-sm-5 col-sm-5 control-label">E-mail</label>
                                    <div class="col-sm-6">
                                      <input type="email" name="email" placeholder="E Mail" class="form-control">
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