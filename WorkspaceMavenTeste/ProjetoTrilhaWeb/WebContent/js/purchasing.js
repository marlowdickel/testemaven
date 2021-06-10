COLDIGO.compra = new Object();

$(document).ready(function() {

/***********************************
 *    Funções do mestre-detalhe
 **********************************/	

	//Aqui iniciamos os arrays categoriaAntigo e marcaAntigo.
	//Com eles controlaremos cada mudança feita nos respectivos campos do formulário.
	//Agora eles só terão uma posição, a 0, com valores vazios. 
	categoriaAntigo = new Array();
	categoriaAntigo[0] = "";
	marcaAntigo = new Array();
	marcaAntigo[0] = "";
	
/*   FUNÇÃO PARA CAPTAR OS VALORES ATUAIS DOS CAMPOS DO FORMULÁRIO   */
	
	//Ela recebe os valores dos campos existentes e guarda nos arrays criados no início desse arquivo.
	//Com isso, podemos saber qual linha foi alterada para carregarmos os produtos na linha correta
	COLDIGO.compra.guardaValores = function(){
		//Recebe todas as posições dos campos selCategoria[] e selMarca[]
		var categoriaAtual = document.getElementsByName('selCategoria[]');
		var marcaAtual = document.getElementsByName('selMarca[]');
		//Para cada linha existente no formulário...
		for (var i=0;i<categoriaAtual.length;i++){
			//...captura e salva apenas os valores atuais nos arrays criados no início do arquivo
			categoriaAntigo[i] = categoriaAtual[i].value;
			marcaAntigo[i] = marcaAtual[i].value;
		}
	}
	
/*   FUNÇÃO PARA CARREGAR AS MARCAS NOS CAMPOS DO FORMULÁRIO   */
						
	//Carrega as marcas registradas no BD no select do formulário, usando o id para fazer isso no campo certo
	COLDIGO.compra.carregarMarcas = function(id){
		
		//Recebe os valores de todos os campos de marca do formulário e armazena em camposMarcas
		var camposMarcas = document.getElementsByName('selMarca[]');
		
		//Inicia o Ajax que busca no BD as marcas cadastradas
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "marca/buscar",
			//Caso dê certo
			success: function (marcas) {
				//Esvazia o select de marca cujo id foi recebido
				$(camposMarcas[id]).html("");
				
				//Se houver mais de uma marca nesse objeto
				if (marcas.length) {
					//Cria uma opção vazia, para validarmos o campo depois
					var option = document.createElement("option");
					option.setAttribute ("value", "");
					option.innerHTML = ("Escolha");
					//Coloca o campo no select correto 
					$(camposMarcas[id]).append(option);
					//Para cada marca...
					for (var i = 0; i < marcas.length; i++) {
						
						//Cria uma opção com o valor do id e o nome da marca
						var option = document.createElement("option");
						option.setAttribute ("value", marcas[i].id);
						option.innerHTML = (marcas[i].nome);
						//Coloca essa opção no select
						$(camposMarcas[id]).append(option);
					
					}
				//Senão tiverem marcas no BD	
				}else{
					//Cria uma opção destacando que deve-se criar uma marca primeiro
					var option = document.createElement("option");
					option.setAttribute ("value", "");
					option.innerHTML = ("Cadastre uma marca primeiro!");
					//Adiciona uma classe que destaca essa opção em vermelho
					$(camposMarcas[id]).addClass("aviso");
					//Coloca a opção no select
					$(camposMarcas[id]).append(option);
				} 
			//Fim do SUCCESS		
			},
			//Se der erro no processo de busca de marcas
			error: function (info) {
				//Exibe aviso
				COLDIGO.exibirAviso("Erro ao buscar as marcas: "+ info.status + " - " + info.statusText);
				//Cria opção de erro e a coloca no select, adicionando uma classe que destaca a opção em vermelho
				$(camposMarcas[id]).html("");
				var option = document.createElement("option");
				option.setAttribute ("value", "");
				option.innerHTML = ("Erro ao carregar marcas!");
				$(camposMarcas[id]).addClass("aviso");
				$(camposMarcas[id]).append(option);
			}
		});
	}
		
	//Executa a função ao carregar a página, afetando a única opção de marca disponível (0)
	COLDIGO.compra.carregarMarcas(0);

/*   FUNÇÃO PARA CRIAR UMA NOVA LINHA DE DETALHE NO FORMULÁRIO   */
		
	//Ao clicar no botão botaoAdd:
	$(".botaoAdd").click(function () {
	
		//Cria um clone da primeira linha de detalhe (veja a classe 'detalhes') e salva na variável novoCampo
		var novoCampo = $("tr.detalhes:first").clone();
		//Esvazia os valores de todos os inputs do clone
		novoCampo.find("input").val("");
		//Insere o clone na página, logo após a última linha já existente
		novoCampo.insertAfter("tr.detalhes:last");
		
		//Chama a função guardaValores para guardar os valores atuais das categorias e marcas
		COLDIGO.compra.guardaValores();
		
	});
	
/*   FUNÇÃO PARA REMOVER UMA LINHA DE DETALHE NO FORMULÁRIO   */
	
	//Cria a função, recebendo o botão clicado por parâmetro
	COLDIGO.compra.removeCampo = function(botao) {
		//Se houver mais de uma linha no mestre-detalhe,
		if($("tr.detalhes").length > 1){
			//Remove a linha que contém o botão de excluir clicado. Para entender, pense na estrutura HTML: 
			//botao seria o botão, o primeiro parent é a CÉLULA onde está o botão, 
			//e o segundo parent é a linha onde está o botão
			$(botao).parent().parent().remove();
			
			//Chama a função guardaValores para guardar os valores atuais das categorias e marcas
			COLDIGO.compra.guardaValores();
		
		//senão, é porque só tem uma linha, então...
		}else{
			//... avisa que a linha não pode ser removida
			COLDIGO.exibirAviso("A última linha não pode ser removida.");
		}
	//fecha a função removeCampo
	}	
	
/*   FUNÇÃO PARA CARREGAR OS PRODUTOS CORRETOS EM CADA LINHA DO FORMULÁRIO   */
		
	COLDIGO.compra.carregarProdutos = function(){
		//Recebe todas as posições dos campos selproduto[], selmarca[] e selCategoria[] e salva em respectivas variáveis
		var selectProduto = document.getElementsByName('selProduto[]');
		var marcaAtual = document.getElementsByName('selMarca[]');
		var categoriaAtual = document.getElementsByName('selCategoria[]');
		//Para cada linha existente no formulário...
		for (var j=0;j<selectProduto.length;j++){
			//...se o valor antigo da marca ou categoria dessa linha for diferente do valor atual...
			if ((marcaAntigo[j] != marcaAtual[j].value)||(categoriaAntigo[j] != categoriaAtual[j].value)){
				//é porque essa foi a linha alterada, portanto repassa a posição verificada para a variável 'linhaAlterada'
				linhaAlterada = j;
			}
		}
		
	    //Se um dos campos marca ou categoria dessa linha estiverem vazios,
		//ainda não dá pra carregar o produto, pois precisamos de ambas as coisas selecionadas, então...
		if ((marcaAtual[linhaAlterada].value=="")||(categoriaAtual[linhaAlterada].value=="")){
			//Guardamos a alteração, "registrando" seu acontecimento 
			COLDIGO.compra.guardaValores();
			//e encerramos a execução da função.
			return false;
		}
	
		//Caso chegue aqui, é porque os dois campos foram preenchidos na linha modificada, então
		//passa os valores do selCategoria e selMarca dessa linha para as variáveis cod
		marcaCod = marcaAtual[linhaAlterada].value;
		categoriaCod = categoriaAtual[linhaAlterada].value;
		//mostra o Aguarde no campo
		$(selectProduto[linhaAlterada]).html("<option>Aguarde</option>");
	
		//Inicia a busca no BD desses produtos, passando como dados a categoria e marca selecionadas
		$.ajax({
			type: "GET",
			url: COLDIGO.PATH + "produto/buscarParaVenda",
			data: "categoria="+categoriaCod+"&idMarca="+marcaCod,
			//Se a busca der certo,
			success: function (produtos) {
				
				//Converte os dados recebidos em formato de objeto do JS
				produtos = JSON.parse(produtos);
				
				//Esvazia o select de produto da linha alterada
				$(selectProduto[linhaAlterada]).html("");
								
				//Se houver algum produto daquela categoria e marca,
				if (produtos.length) {
					//Remove a classe que destaca em vermelho o select caso não hajam produtos
					$(selectProduto[linhaAlterada]).removeClass("aviso");
					//Cria uma opção vazia para validarmos depois e a coloca no select da linha alterada
					var option = document.createElement("option");
					option.setAttribute ("value", "");
					option.innerHTML = ("Escolha");
					$(selectProduto[linhaAlterada]).append(option);
		
					//Para cada produto encontrado,
					for (var i = 0; i < produtos.length; i++) {
						//Cria uma opção no select da linha alterada para ele
						var option = document.createElement("option");
						option.setAttribute ("value", produtos[i].id);
						option.innerHTML = (produtos[i].modelo);
						$(selectProduto[linhaAlterada]).append(option);
					}
				
				//Mas se não houverem produtos...	
				}else{
				
					//Cria uma opção destacando que não há produtos dessa marca e categoria
					var option = document.createElement("option");
					option.setAttribute ("value", "");
					option.innerHTML = ("Não há produtos correspondentes!");
					$(selectProduto[linhaAlterada]).append(option);
					//Destaca em vermelho
					$(selectProduto[linhaAlterada]).addClass("aviso");
				} 	
			},
			//Em caso de erro na busca,
			error: function (info) {
				//Avisa o usuário
				COLDIGO.exibirAviso("Erro ao buscar os produtos: "+ info.status + " - " + info.statusText);
				//Cria um option vazio para destacar o erro e o coloca no select da linha alterada
				$(selectProduto[linhaAlterada]).html("");
				var option = document.createElement("option");
				option.setAttribute ("value", "");
				option.innerHTML = ("Erro ao carregar produtos!");
				$(selectProduto[linhaAlterada]).append(option);
				$(selectProduto[linhaAlterada]).addClass("aviso");			
			}
		});
			
		//Chama a função guardaValores para guardar os valores atuais das categorias e marcas
		COLDIGO.compra.guardaValores();
	    
	//fecha a função 
	}	
	
/*   FUNÇÃO PARA VALIDAR OS CAMPOS DO FORMULÁRIO   */	
		
	COLDIGO.compra.validaDetalhe = function(){
		//Recebe os produtos preenchidos
		var produtosValidar = document.getElementsByName('selProduto[]');
		//Recebe as quantidades e valores preenchidas
		var qtdeValidar = document.getElementsByName('txtQuantidade[]');
		var valorValidar = document.getElementsByName('txtValor[]');
		//Para cada linha de detalhe...
		for (var i = 0;i < produtosValidar.length; i++){
			//Cria a variável linha com valor de "i+1" para a mensagem avisar corretamente qual linha não foi preenchida
			var linha = i+1;
			//Se a posição atual dos arrays de marca ou quantidade ou produto estiverem vazios,
			if ((produtosValidar[i].value=="")||(qtdeValidar[i].value=="")||(valorValidar[i].value=="")){
				//Avisa que essa linha não foi preenchida
				COLDIGO.exibirAviso("A linha "+linha+" não foi completamente preenchida.");
				//Retorna falso, encerrando a função
				return false;
			}
		}
		//Se chegar aqui, está tudo OK, então retorna verdadeiro
		return true;
		
	}//fecha a função carregarProdutos
	
/***********************************
 *       Funções de compras
 **********************************/
	
	COLDIGO.compra.cadastrar = function(){
		
		if(COLDIGO.compra.validaDetalhe()){
			
			var compra = new Object();
			compra.data = document.frmAddCompra.txtData.value;
			compra.fornecedor = document.frmAddCompra.txtFornecedor.value;
			var produtos = document.getElementsByName('selProduto[]');
		    var quantidades = document.getElementsByName('txtQuantidade[]');
		    var valores = document.getElementsByName('txtValor[]');
    		compra.produtos = new Array(produtos.length);
		    for (var i = 0;i < produtos.length; i++){
				compra.produtos[i]=new Object();
				compra.produtos[i].idProduto = produtos[i].value;
				compra.produtos[i].quantidade = quantidades[i].value;
				compra.produtos[i].valor = valores[i].value;
			}

			$.ajax({
				type: "POST",
				url: COLDIGO.PATH + "compra/inserir",
				data:JSON.stringify(compra),
				success: function (msg) {
					COLDIGO.exibirAviso(msg);
				},
				error: function (info) {
					COLDIGO.exibirAviso("Erro ao cadastrar um novo produto: "+ info.status + " - " + info.statusText);
				}
			});
			
		}
		
	}
		
});


