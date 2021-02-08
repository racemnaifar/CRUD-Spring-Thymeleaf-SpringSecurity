var table = $("#records_table");
var infoModal = $('#edit');


function deleteCompte(id,index){
swal({
  title: "Are you sure?",
  text: "Once deleted, you will not be able to recover this Account !",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {
  if (willDelete) {
  		$.ajax({
            url: "/delete-compte/"+id,
            type: "GET",
            //data:  {"action":"deleteCompte","id" : id},
            success: function(){
                $("#"+id).remove();
                 swal("Poof! Account has been deleted!", {
      				icon: "success",
   				 });
            },
            error: function(){
                alert("error");
            }          
		});
  } else {
    swal("Account is safe!");
  }
});
}

function deleteClient(id,index){
swal({
  title: "Are you sure?",
  text: "Once deleted, you will not be able to recover this Customer !",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {
  if (willDelete) {
  		$.ajax({
            url: "/delete-client/"+id,
            type: "GET",
            //data:  {"action":"deleteClient","id" : id},
            success: function(){
                $("#"+id).remove();
                 swal("Poof! Customer has been deleted!", {
      				icon: "success",
   				 });
            },
            error: function(){
                alert("error");
            }          
		});
  } else {
    swal("Customer is safe!");
  }
});
}

// create convention rapport pdf
function createPDF() {
    $('table tr td:last-child').remove();
    $('table tr td:last-child').remove();
    $('table tr td:last-child').remove();
    $('table th:last-child').remove();
    $('table th:last-child').remove();
    $('table th:last-child').remove();
    var sTable = document.getElementById('comptesTable').innerHTML;

    var style = "<style>";
    style = style + "table {width: 100%;font: 17px Calibri;}";
    style = style + "table, th, td {border: solid 1px #DDD; border-collapse: collapse;";
    style = style + "padding: 2px 3px;text-align: center;}";
    style = style + "</style>";

    // CREATE A WINDOW OBJECT.
    var win = window.open('', '', 'height=700,width=700');

    win.document.write('<html><head>');
    win.document.write('<title>Lists Accounts</title>');
    win.document.write(style);
    win.document.write('</head>');
    win.document.write('<body>');
    win.document.write('<h1>Lists Accounts :</h1>');
    win.document.write(sTable);
    win.document.write('</body></html>');

    win.document.close();

    win.print();

    location.reload(); // Reload Page

}