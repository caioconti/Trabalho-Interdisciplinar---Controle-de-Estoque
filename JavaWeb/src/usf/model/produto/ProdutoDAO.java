package usf.model.produto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import usf.model.basic.BasicDAO;
import usf.model.basic.ModelBasic;

public class ProdutoDAO extends BasicDAO{

	public ProdutoDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	public ProdutoDAO(String jdbcURL, String jdbcUsername, String jdbcPassword, String jdbcDriver) {
		super(jdbcURL, jdbcUsername, jdbcPassword, jdbcDriver);
	}

	@Override
	public boolean insert(ModelBasic model) throws SQLException {
		Produto produto = (Produto) model;
		
		//Inserindo no banco de dados
		String sql = "INSERT INTO produto (nome, descricao, fornecedor) VALUES (?, ?, ?)";
		
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, produto.getNome());
		statement.setString(2, produto.getDescricao());
		statement.setString(3, produto.getFornecedor());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		
		statement.close();
		disconnect();
		
		return rowInserted;
	}

	@Override
	public List<ModelBasic> listAll() throws SQLException {
		List<ModelBasic> listProduto = new ArrayList<>();
		
		//Buscando tudo no banco de dados de produto
		String sql = "SELECT * FROM produto";
		
		//Conectando com o banco de dados
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String nome = resultSet.getString("nome");
			String descricao = resultSet.getString("descricao");
			String fornecedor = resultSet.getString("fornecedor");
			
			Produto produto = new Produto(id, nome, descricao, fornecedor);
			listProduto.add(produto);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		
		return listProduto;
	}
	
	public List<ModelBasic> searchProduto(String nome) throws SQLException {
		List<ModelBasic> listProduto = new ArrayList<>();
		//Buscando produto pelo id
		String sql = "SELECT * FROM produto WHERE nome LIKE ? ";
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, '%' + nome + '%');
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String nomeRS = resultSet.getString("nome");
			String descricao = resultSet.getString("descricao");
			String fornecedor = resultSet.getString("fornecedor");
			
			Produto produto = new Produto(id, nomeRS, descricao, fornecedor);
			listProduto.add(produto);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		
		return listProduto;
	}

	@Override
	public boolean delete(ModelBasic produto) throws SQLException {
		//Deletando do banco pelo id de produto
		String sql = "DELETE FROM produto WHERE id = ?";
		
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, produto.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		
		statement.close();
		disconnect();
		
		return rowDeleted;
	}

	@Override
	public boolean update(ModelBasic model) throws SQLException {
		//Fazendo CAST do tipo ModelBasic para o tipo Produto
		Produto produto = (Produto) model;
		
		//Atualizando produto no banco pelo id
		String sql = "UPDATE produto SET nome = ?, descricao = ?, fornecedor = ?";
		sql += " WHERE id = ?";
		
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, produto.getNome());
		statement.setString(2, produto.getDescricao());
		statement.setString(3, produto.getFornecedor());
		statement.setInt(4, produto.getId());
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		
		statement.close();
		disconnect();
		
		return rowUpdated;
	}

	@Override
	public ModelBasic getRecord(int id) throws SQLException {
		Produto produto = null;
		
		//Buscando produto pelo id
		String sql = "SELECT * FROM produto WHERE id = ?";
		
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String nome = resultSet.getString("nome");
			String descricao = resultSet.getString("descricao");
			String fornecedor = resultSet.getString("fornecedor");
			
			produto = new Produto(id, nome, descricao, fornecedor);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		
		return produto;
	}
}
