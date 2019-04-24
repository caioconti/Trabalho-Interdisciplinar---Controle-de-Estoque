<%@page import="usf.model.usuario.Usuario"%>
<%@page import="usf.model.usuario.UsuarioDAO"%>
<%@page import="usf.model.produto.Produto"%>
<%@page import="usf.model.basic.ModelBasic"%>
<%@page import="java.util.List"%>
<%@page import="usf.model.produto.ProdutoDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700|Pacifico|Roboto+Slab:400,700" rel="stylesheet">
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
	<title>Insert title here</title>
	<style>
		body {
			background: #9777da;
		}
		
		h3 {
			text-align: center;
		}
		
		.form-group {
			padding: 0 10px;
		}
		
		.form {
			padding-bottom: 10px;
		}
		
		.botoes {
			padding-top: 10px;
			padding-bottom: 20px;
		}
		
		.btn-cancelar {
			color: white;
		}
	</style>
</head>
<%
	String path = this.getServletContext().getContextPath();
%>
<body>
	<header>
		<nav class="navbar navbar-expand-lg navbar-light bg-dark">
			<a class="navbar-link text-white btn btn-outline-primary" href="../produto/list">Voltar</a>
		</nav>
	</header>

	<div class="container">
		<br>
		<div class="form-group text-white bg-dark">
			<br>
			<h3>
				<c:if test="${saidaproduto != null}">
					<p>Editar sa�da de produto</p>
				</c:if>
				<c:if test="${saidaproduto == null}">
					<p>Adicionar sa�da de produto</p>
				</c:if>
			</h3>
			<br>
			<c:if test="${saidaproduto != null}">
				<form action="<%=path%>/saidaproduto/update" method="post">
			</c:if>
			<c:if test="${saidaproduto == null}">
				<form action="<%=path%>/estoque/insert" method="post">
			</c:if>
			
			<c:if test="${saidaproduto != null}">
				<input type="hidden" name="id" value="<c:out value='${saidaproduto.id}' />" />
			</c:if>
			
			<div class="form-group text-white">
				<label for="inputTipo">Tipo: </label>
				<input type="text" name="tipo" readonly="readonly" class="form-control" id="inputTipo" value="Sa�da" />
			</div>
				
			<div class="form-group text-white">
				<label for="inputData">Data: </label> 
				<input type="date" name="data" class="form-control" id="inputData" placeholder="Data" value="<c:out value='${produto.data}' />" required />
			</div>
			
			<div class="form-group text-white">
				<label for="inputDescricao">Produto: </label> 
				<select class="form-control" name="produto" required>
					<c:if test="${saidaproduto != null}">
						<option><c:out value='${saidaproduto.produto}' /></option>
					</c:if>
					<c:if test="${saidaproduto == null}">
						<option selected>SELECIONE O PRODUTO</option>
					</c:if>
					<%
						String jdbcURL = getServletContext().getInitParameter("jdbcURL");
						String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
						String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
						String jdbcDriver = getServletContext().getInitParameter("jdbcDriver");
	
						ProdutoDAO produtoDAO = new ProdutoDAO(jdbcURL, jdbcUsername, jdbcPassword, jdbcDriver);
						List<ModelBasic> listProduto = produtoDAO.listAll();
						for (int i = 0; i < listProduto.size(); i++) {
							String nome = ((Produto)listProduto.get(i)).getNome();
					%>	
						<option value="<%=nome%>"><%=nome%></option>
					<%
						}
						
						request.getAttribute("");
					%>
				</select>
			</div>
			
			<div class="form-group text-white">
				<label for="inputValorUnitario">Valor UN: </label> 
				<input type="number" step="any" min="0" name="valorUnitario" class="form-control" id="inputValorUnitario" placeholder="Valor UN" value="<c:out value='${produto.valorUnitario}' />" required />
			</div>
			
			<div class="form-group text-white">
				<label for="inputQuantidade">Quantidade: </label> 
				<input type="number" name="quantidade" class="form-control" id="inputQuantidade" placeholder="Quantidade" value="<c:out value='${produto.quantidade}' />" required />
			</div>
			
			<div class="form-group text-white">
				<label for="inputValorTotal">Valor Total: </label> 
				<input type="number" step="any" min="0" name="valorTotal" class="form-control" id="inputValorTotal" placeholder="Valor Total" value="<c:out value='${produto.valorTotal}' />" required />
			</div>
			
			<div class="form-group text-white">
				<label for="inputDescricao">Usu�rio: </label> 
				<select class="form-control" name="usuario" required>
					<c:if test="${saidaproduto != null}">
						<option><c:out value='${saidaproduto.usuario}' /></option>
					</c:if>
					<c:if test="${saidaproduto == null}">
						<option selected>SELECIONE O USU�RIO</option>
					</c:if>
					<%
						UsuarioDAO usuarioDAO = new UsuarioDAO(jdbcURL, jdbcUsername, jdbcPassword, jdbcDriver);
						List<ModelBasic> listUsuario = usuarioDAO.listAll();
						for (int i = 0; i < listUsuario.size(); i++) {
							String nome = ((Usuario)listUsuario.get(i)).getNome();
					%>	
						<option value="<%=nome%>"><%=nome%></option>
					<%
						}
						
						request.getAttribute("");
					%>
				</select>
			</div>
			
			<div class="form-group botoes text-white">
				<button class="btn btn-success" type="submit">Inserir</button>
				<a class="btn btn-danger float-right" href='list' role="button">Cancelar</a>
			</div>
			
			</form>
		</div>
	</div>

	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>