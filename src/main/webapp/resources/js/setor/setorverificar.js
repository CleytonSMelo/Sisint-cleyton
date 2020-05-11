window.onload = function() {

	var table = document.getElementById('tabela-servico').rows.length;
	//var table = document.querySelector("DataTables_Table_0_wrapper").rows.length;
	console.log(table);
	
	
	  for (var i = 1; i < table; i++) {
		
		  var texto =  $('#tabela-servico tr:nth-child('+i+') td:nth-child(5)').text();
		 
	    	var result = (texto);
	    	
	    	
	    	console.log(result);
	    	if (result=="true"){
	 			//console.log("deletado");
	 			$("#tabela-servico > tbody > tr:nth-child("+i+") > td:nth-child(6) > a.link-remover.remover").css("display","none");
	 			
	 		}else if(result=="false"){
	 			//console.log("não deletado");
	 			
	 			
	 			 $("#tabela-servico > tbody > tr:nth-child("+i+") > td:nth-child(6) > a.link-adicionar.adicionar").css("display","none");
	 			
	 		}else{
	 			console.log("nenhuma das opções");
	 			
	 		}
	 }
	  	
	
};

//document.querySelector("#DataTables_Table_0 > tbody > tr:nth-child(1) > td:nth-child(6) > a.link-remover.remover")
//document.querySelector("#DataTables_Table_0 > tbody > tr:nth-child(2) > td:nth-child(6) > a.link-remover.remover")
//
//document.querySelector("#DataTables_Table_0 > tbody > tr:nth-child(2) > td:nth-child(6) > a.link-adicionar.adicionar")

//document.querySelector("#DataTables_Table_0_wrapper")

