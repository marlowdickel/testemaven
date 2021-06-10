//Assim que o documento HTML for carregado por completo...
$(document).ready(function() {
	//Carrega cabeçalho, menu e rodapé aos respectivos locais
	$("header").load("/MeuTesteMaven/pages/site/general/cabecalho.html");
	$("nav").load("/MeuTesteMaven/pages/site/general/menu.html");
	$("footer").load("/MeuTesteMaven/pages/site/general/rodape.html");
	
	login = function(){

		var login = new Object();
		login.usuario = document.frmLogin.txtusuario.value;
		login.senha = document.frmLogin.pwdsenha.value;
		
		if((login.usuario=="")||(login.senha=="")){
			
			alert("Preencha todos os campos!");

		} else {
			
			$.ajax({
				type: "POST",
				url: "/MeuTesteMaven/rest/login/entrar",
				data:JSON.stringify(login),
				success: function () {
					alert("OK");

				},
				error: function (info) {
					alert("Erro ao cadastrar um novo produto: "+ info.status + " - " + info.statusText);
				}
			});
			
		}
		
		return false;
		
	}//fim da função login
});



