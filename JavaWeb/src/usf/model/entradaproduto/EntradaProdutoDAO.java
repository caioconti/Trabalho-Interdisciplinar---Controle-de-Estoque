package usf.model.entradaproduto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import usf.model.basic.BasicDAO;
import usf.model.basic.ModelBasic;

public class EntradaProdutoDAO extends BasicDAO{
	
	public EntradaProdutoDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		super(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	public EntradaProdutoDAO(String jdbcURL, String jdbcUsername, String jdbcPassword, String jdbcDriver) {
		super(jdbcURL, jdbcUsername, jdbcPassword, jdbcDriver);
	}

	@Override
	public boolean insert(ModelBasic model) throws SQLException {
		EntradaProduto entradaProduto = (EntradaProduto) model;
		
		//Inserindo no banco de dados
		String sql = "INSERT INTO movimentacao (tipo, data, produto, valorUnitario, quantidade,  valorTotal, fornecedor, usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		//Conecatando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, entradaProduto.getTipo());
		statement.setString(2, entradaProduto.getData());
		statement.setString(3, entradaProduto.getProduto());
		statement.setDouble(4, entradaProduto.getValorUnitario());
		statement.setInt(5, entradaProduto.getQuantidade());
		statement.setDouble(6, entradaProduto.getValorTotal());
		statement.setString(7, entradaProduto.getFornecedor());
		statement.setString(8, entradaProduto.getUsuario());
		
		boolean rowInserted = statement.executeUpdate() > 0;
		
		statement.close();
		disconnect();
		
		return rowInserted;
	}

	@Override
	public List<ModelBasic> listAll() throws SQLException {
		List<ModelBasic> listEntradaProduto = new ArrayList<>();
		
		//Buscando tudo no banco de dados de entradaProduto
		String sql = "SELECT * FROM movimentacao";
		
		//Conectando com o banco de dados
		connect();
		
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		
		while (resultSet.next()) {
			String tipo = resultSet.getString("tipo");
			String data = resultSet.getString("data");
			double valorUnitario = resultSet.getDouble("valorUnitario");
			int quantidade = resultSet.getInt("quantidade");
			double valorTotal = resultSet.getDouble("valorTotal");
			String produto = resultSet.getString("produto");
			String fornecedor = resultSet.getString("fornecedor");
			String usuario = resultSet.getString("usuario");
			
			EntradaProduto entradaProduto = new EntradaProduto(tipo, data, produto, valorUnitario, quantidade, valorTotal, fornecedor, usuario);
			listEntradaProduto.add(entradaProduto);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		
		return listEntradaProduto;
	}
	
	public List<ModelBasic> search(String nome) throws SQLException {
		List<ModelBasic> listEntradaProduto = new ArrayList<>();
		//Buscando produto pelo id
		String sql = "SELECT * FROM movimentacao WHERE nome LIKE ? ";
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, '%' + nome + '%');
		
		ResultSet resultSet = statement.executeQuery();
		
		while (resultSet.next()) {
			String tipo = resultSet.getString("tipo");
			String data = resultSet.getString("data");
			double valorUnitario = resultSet.getDouble("valorUnitario");
			int quantidade = resultSet.getInt("quantidade");
			double valorTotal = resultSet.getDouble("valorTotal");
			String produto = resultSet.getString("produto");
			String fornecedor = resultSet.getString("fornecedor");
			String usuario = resultSet.getString("usuario");
			
			EntradaProduto entradaProduto = new EntradaProduto(tipo, data, produto, valorUnitario, quantidade, valorTotal, fornecedor, usuario);
			listEntradaProduto.add(entradaProduto);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		
		return listEntradaProduto;
	}

	@Override
	public boolean delete(ModelBasic entradaProduto) throws SQLException {
		//Deletando do banco pelo id de EntradaProduto
		String sql = "DELETE FROM movimentacao WHERE id = ?";
		
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, entradaProduto.getId());
		
		boolean rowDeleted = statement.executeUpdate() > 0;
		
		statement.close();
		disconnect();
		
		return rowDeleted;
	}

	@Override
	public boolean update(ModelBasic model) throws SQLException {
		//Fazendo CAST do tipo ModelBasic para o tipo EntradaProduto
		EntradaProduto entradaProduto = (EntradaProduto) model;
		
		//Atualizando EntradaProduto no banco pelo id
		String sql = "UPDATE movimentacao SET tipo = ?, data = ?, produto = ?, valorUnitario = ?, quantidade = ?, valorTotal = ?, fornecedor = ?, usuario = ?";
		sql += " WHERE id = ?";
		
		//Conectando com o banco de dados
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, entradaProduto.getTipo());
		statement.setString(2, entradaProduto.getData());
		statement.setString(3, entradaProduto.getUsuario());
		statement.setString(4, entradaProduto.getProduto());
		statement.setString(5, entradaProduto.getFornecedor());
		statement.setInt(6, entradaProduto.getQuantidade());
		statement.setDouble(7, entradaProduto.getValorUnitario());
		statement.setDouble(8, entradaProduto.getValorTotal());
		statement.setInt(9, entradaProduto.getId());
		
		boolean rowUpdated = statement.executeUpdate() > 0;
		
		statement.close();
		disconnect();
		
		return rowUpdated;
	}

	@Override
	public ModelBasic getRecord(int id) throws SQLException {
		EntradaProduto entradaProduto = null;
		
		String sql = "SELECT * FROM movimentacao WHERE id = ?";
		
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, id);
		
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			String tipo = resultSet.getString("tipo");
			String data = resultSet.getString("data");
			String usuario = resultSet.getString("usuario");
			String produto = resultSet.getString("produto");
			String fornecedor = resultSet.getString("fornecedor");
			int quantidade = resultSet.getInt("quantidade");
			double valorUnitario = resultSet.getDouble("valorUnitario");
			double valorTotal = resultSet.getDouble("valorTotal");
			
			entradaProduto = new EntradaProduto(tipo, data, produto, valorUnitario, quantidade, valorTotal, fornecedor, usuario);
		}
		
		resultSet.close();
		statement.close();
		disconnect();
		
		return entradaProduto;
	}
}