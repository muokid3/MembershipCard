<script type="text/javascript">



function suspend(id){
  bootbox.confirm("Are you sure you want to suspend this card? ", function(result) {
      if(result) {
      
    $.ajax({
    url: '<?php echo base_url(); ?>Card_holders/suspend/' + id,
    type: 'post',
    data: {id: id},
    dataType: 'html',   
    success: function(html) {
        bootbox.alert(html);
        if(html == 'Card Suspended')
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
            Card Holders
            
          </h1>
          
        </section>



<section class="content">
<div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">Active Holders</h3>
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
                      <th>Name</th>
                      <th>Email</th>
                      <th>Phone</th>
                      <th>Account</th>
                      <th>Balance</th>
                      <th>Points</th>
                      <th>Registered By</th>
                      <th>Action</th>
                    </tr>

                    
                    <?php  
                
                foreach ($cardholders->result() as $cardholder){?>
                
                <tr>
                    <td><?php echo $cardholder->name; ?></td>
                    <td><?php echo $cardholder->email; ?></td>
                    <td><?php echo $cardholder->phone; ?></td>
                    <td><?php echo $cardholder->account; ?></td>
                    <td><?php echo $cardholder->balance; ?></td>                    
                    <td><?php echo $cardholder->points; ?></td>
                    <td><?php echo $cardholder->merchName; ?></td>
                    


                    <td>
                      <a href="javascript:void(0);" onclick="suspend('<?php echo $cardholder->id ;?>')">
                      <span class="btn btn-warning"><span class="fa fa-unlink"></span>&nbsp;Suspend</span></a>
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