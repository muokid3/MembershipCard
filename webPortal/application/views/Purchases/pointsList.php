<div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Transactions
            
          </h1>
          
        </section>



<section class="content">
<div class="row">
            <div class="col-xs-12">
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">Point Redemptions</h3>
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
                      <th>Account NO.</th>                      
                      <th>Points Redeemed</th>
                      <th>Transacted By</th>
                      <th>Date and Time</th>
                      
                    </tr>

                    
                    <?php  
                
                foreach ($points->result() as $point){?>
                
                <tr>
                    <td><?php echo $point->name; ?></td>
                    <td><?php echo $point->account; ?></td>
                    <td><?php echo $point->points; ?></td>
                    <td><?php echo $point->merchName; ?></td>
                    <td><?php echo $point->datetime; ?></td>
                    
                    
                </tr>
                    <?php } ?>



                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
          </div>
        </section>

        </div>