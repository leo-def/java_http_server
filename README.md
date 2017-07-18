# java_http_server
Java HTTP Server with a simple web application running on it




Testes:

	STATUS: 
	-INVALID_CONTENT_TYPE


	Mensagem: Login
	Descrição: Efetuar Login
	URL......: /WSLogin
	ENVIO.....: email + senha
	RESPOSTA..: "OK + TOKEN", "EMAIL E/OU SENHA INVÁLIDOS"

	Mensagem: Logout
	Descrição: Faz logout
	URL......: /WSLogout
	ENVIO.....: TOKEN
	RESPOSTA..: "OK", "ERRO"

	Mensagem: Listar Clientes
	Descrição: Retorna a lista de clientes
	URL......: /WSListarCLientes
	ENVIO.....: TOKEN
	RESPOSTA..: Lista de Clientes (id, nome, lat, lng), "ERRO"

	Mensagem: Cadastrar Cliente
	Descrição: Cadastra um novo cliente
	URL......: /WSCadastrar
	ENVIO.....: nome + endereço + complemento + bairro + cidade + estado
	RESPOSTA..: "OK", +id + nome + lat + lng, "ERRO"

	Mensagem: Consultar Cliente
	Descrição: Consulta um cliente
	URL......: /WSConsultar
	ENVIO.....: TOKEN, id
	RESPOSTA..: "OK" + nome + endereço + complemento + bairro + cidade + estado, "ERRO"

	Mensagem: Alterar Cliente
	Descrição: Altera um cliente
	URL......: /WSAlterar
	ENVIO.....: TOKEN, id + nome + endereço + complemento + bairro + cidade + estado, "ERRO"
	RESPOSTA..: "OK" + "OK", +id + nome + lat + lnt "ERRO"



	FORMATO DA MENSAGEM JSON(ENVIO):

	{
		"TOKEN" : "<TOKEN>",
		
		"DADOS" : {
			"CAMPO1" : "<VALOR1>",
			"CAMPO2" : "<VALOR2>",
			"CAMPO3" : "<VALOR3>",
			...
	}


	FORMATO DA MENSAGEM JSON(RESPOSTA):

	{
		"STATUS" : "<STATUS>",
		
		"TOKEN" : "<TOKEN>",
		
		"DADOS" : {... } | [{...}, {...}, {...} ...]  
	}
