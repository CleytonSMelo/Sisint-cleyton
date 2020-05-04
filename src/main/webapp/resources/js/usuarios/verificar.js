       
window.onload = function() {

	var table = document.getElementById('tabela-servico').rows.length;
	
	
	
	
	  for (var i = 1; i < table; i++) {
		
		  var texto =  $('#tabela-servico tr:nth-child('+i+') td:nth-child(8)').text();
		 
	    	var result = (texto);
	    	
	    	
	    	//console.log(result);
	    	if (result=="true"){
	 			//console.log("deletado");
	 			$("#tabela-servico > tbody > tr:nth-child("+i+") > td:nth-child(9) > a.desativar-usuario.remover").css("display","none");
	 			
	 		}else if(result=="false"){
	 			//console.log("não deletado");
	 			
	 			
	 			 $("#tabela-servico > tbody > tr:nth-child("+i+") > td:nth-child(9) > a.ativar-usuario.adicionar").css("display","none");
	 			
	 		}else{
	 			console.log("nenhuma das opções");
	 			
	 		}
	 }
	  	
	
};
	   	

        	 

          
        

//document.querySelector("#del > input[type=hidden]")
//document.querySelector("#del > input[type=hidden]")