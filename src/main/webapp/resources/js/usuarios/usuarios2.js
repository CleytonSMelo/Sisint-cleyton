$(document).ready( function () {  
    var pathAtivar = $('#btnAtivar').attr('href');
    
//Caminho para desativar usuario selecionado é atribuido ao campo HREF do botão de id 'btnAtivar'(ATIVAR)
$('.ativar-usuario').click( function () {
    var id = $(this).attr("id-usuario");
    var url = pathAtivar + id;
    console.log(url);
    $('#btnAtivar').attr('href', url);
    console.log("url: " + $('#btnAtivar').attr('href'));
  });
});