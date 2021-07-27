COLDIGO.relatorioCompra = new Object();

$(document).ready(function() {

	//Busca no BD e exibe na página os produtos que atendam à solicitação do usuário 
	COLDIGO.relatorioCompra.buscar = function(){
		//Esse Ajax dispensa apresentações
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "compra/relatorio",
			success: function(dados){
				console.log(dados);
				
				//dados = JSON.parse(dados);
				$("#listaCompras").html(COLDIGO.relatorioCompra.exibir(dados));			
			},
			error: function(info){
				console.log(info);
				COLDIGO.exibirAviso("Erro ao consultar as compras: "+ info.status + " - " + info.statusText);
			}
		});
		
	};
	
	//Executa a função ao carregar a página
	COLDIGO.relatorioCompra.buscar();


	//Transforma os dados dos produtos recebidos do servidor em uma tabela HTML 
	COLDIGO.relatorioCompra.exibir = function(listaDeCompras) {
		
		//Cria uma variável pra receber o HTML das vendas do relatório
		var vendas = "";
		
		//Se houverem vendas...
		if (listaDeCompras != undefined && listaDeCompras.length > 0){
			//Para cada venda...
			for (var i=0; i<listaDeCompras.length; i++){
				//Cria uma tabela, e duas linhas com os dados gerais da venda
				vendas += "<table>" +
					"<tr>" +
						"<th colspan='5'>Id:"+listaDeCompras[i].id+"</th>" +
					"</tr>" +
					"<tr>" +
						"<th>Data</th>" +
						"<td>"+listaDeCompras[i].data+"</td>" +
						"<th>Fornecedor</th>" +
						"<td colspan='2'>"+listaDeCompras[i].fornecedor+"</td>" +
					"</tr>";
				
				//Cria uma linha de título para os produtos dessa compra				
				vendas += "<tr>" +
						"<th>Categoria</th>" +
						"<th>Marca</th>" +
						"<th>Modelo</th>" +
						"<th>Quantidade</th>" +
						"<th>Valor</th>" +
					"</tr>"
				
				//Converte os dados de cada produto dessa compra em objeto JS 
				//(necessário devido a conversão realizada no backend da aplicação em JDBCCompraDAO.relatorio)
				//listaDeCompras[i].produtos = JSON.parse(listaDeCompras[i].produtos)
				
				//Para cada produto da compra
				for (var j=0; j<listaDeCompras[i].produtos.length; j++){
					console.log(listaDeCompras[i].produtos[j]);
					//Cria uma linha com os dados do produto
					vendas += "<tr>" +
							"<td>"+listaDeCompras[i].produtos[j].produto.categoria+"</td>" +
							"<td>"+listaDeCompras[i].produtos[j].produto.marca.nome+"</td>" +
							"<td>"+listaDeCompras[i].produtos[j].produto.modelo+"</td>" +
							"<td>"+listaDeCompras[i].produtos[j].quantidade+"</td>" +
							"<td>R$ "+COLDIGO.formatarDinheiro(listaDeCompras[i].produtos[j].valor)+"</td>" +
						"</tr>"
				}	

				//Fecha a tabela daquela compra
				vendas += "</table>";
			}
		//Mas se não houver nenhuma compra no sistema,	
		} else if (listaDeCompras == ""){
			//Cria uma mensagem informando que não foram encontrados registros
			vendas += "<h3>Nenhuma venda encontrada!</h3>";
		}
		
		//retorna a variável, com as tabelas criadas para cada venda ou com a mensagem de que não há vendas
		return vendas;
	};
	
});

/*	
	*/