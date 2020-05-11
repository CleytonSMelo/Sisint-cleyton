$(document).ready( function () {
	 $('.link-adicionar').click( function () {
	        var valorUrl = $(this).attr("url-adicionar");
	        console.log(valorUrl);
	        $('#btn-ativar').attr("href", valorUrl);
	    });
});