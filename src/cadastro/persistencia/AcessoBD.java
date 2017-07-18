package cadastro.persistencia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cadastro.entidade.Cliente;
import cadastro.entidade.Usuario;

public class AcessoBD {
	private static AcessoBD instancia;
	
	public static synchronized AcessoBD getInstancia() throws Exception {
		if (instancia == null) {
			instancia = new AcessoBD();
		}
		return instancia;
	}
	
	private static final String URL_CONNECTION = "jdbc:mysql://localhost:3306/Matriz";
	
	private AcessoBD() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	}
	
	private Connection getConnection() throws Exception {
		return DriverManager.getConnection(URL_CONNECTION, "root", "1234");
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rslt) {
		if (rslt != null) {
			try {
				rslt.close();
			} catch(Exception e) {
				e.printStackTrace();
			}				
		}
		
		if (stmt != null) {
			try {
				stmt.close();
			} catch(Exception e) {
				e.printStackTrace();
			}				
		}

		if (conn != null) {
			try {
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}				
		}
	}
	
	public Usuario consultarUsuario(String email) throws Exception {
		Connection        conn = null;
		PreparedStatement stmt = null;
		ResultSet         rslt = null;
		Usuario           user = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from usuarios where email = ?");
			stmt.setString(1, email);
			rslt = stmt.executeQuery();
			
			if (rslt.next()) {
				user = new Usuario();
				user.setEmail(email);
				user.setNome(rslt.getString("nome"));
				user.setSenha(rslt.getString("senha"));
				user.setToken(rslt.getString("token"));
				user.setUltimoAcesso(rslt.getLong("ultimo_acesso"));
			}
		} finally {
			close(conn, stmt, rslt);
		}
		
		return user;
	}

	
	public Usuario consultarUsuarioPorToken(String token) throws Exception {
		Connection        conn = null;
		PreparedStatement stmt = null;
		ResultSet         rslt = null;
		Usuario           user = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from usuarios where token = ?");
			stmt.setString(1, token);
			rslt = stmt.executeQuery();
			
			if (rslt.next()) {
				user = new Usuario();
				user.setEmail(rslt.getString("email"));
				user.setNome(rslt.getString("nome"));
				user.setSenha(rslt.getString("senha"));
				user.setToken(token);
				user.setUltimoAcesso(Long.getLong(rslt.getString("ultimo_acesso")));
			}
		} finally {
			close(conn, stmt, rslt);
		}
		
		return user;
	}

	
	
	public List<Cliente> pesquisarTodosClientes() throws Exception {
		Connection        conn = null;
		PreparedStatement stmt = null;
		ResultSet         rslt = null;
		List<Cliente>     list = new ArrayList<>();
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from clientes order by nome");
			rslt = stmt.executeQuery();
			
			while (rslt.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rslt.getInt("id"));
				cliente.setNome(rslt.getString("nome"));
				cliente.setEndereco(rslt.getString("endereco"));
				cliente.setComplemento(rslt.getString("complemento"));
				cliente.setBairro(rslt.getString("bairro"));
				cliente.setCidade(rslt.getString("cidade"));
				cliente.setEstado(rslt.getString("estado"));
				cliente.setLat(new BigDecimal(rslt.getString("lat")));
				cliente.setLng(new BigDecimal(rslt.getString("lng")));
				list.add(cliente);
			}
		} finally {
			close(conn, stmt, rslt);
		}
		
		return list;
	}
	
	public Cliente consultarCliente(int id) throws Exception {
		Connection        conn    = null;
		PreparedStatement stmt    = null;
		ResultSet         rslt    = null;
		Cliente           cliente = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from clientes where id=?");
			stmt.setInt(1, id);
			rslt = stmt.executeQuery();
			
			if (rslt.next()) {
				cliente = new Cliente();
				cliente.setId(rslt.getInt("id"));
				cliente.setNome(rslt.getString("nome"));
				cliente.setEndereco(rslt.getString("endereco"));
				cliente.setComplemento(rslt.getString("complemento"));
				cliente.setBairro(rslt.getString("bairro"));
				cliente.setCidade(rslt.getString("cidade"));
				cliente.setEstado(rslt.getString("estado"));
				cliente.setLat(new BigDecimal(rslt.getString("lat")));
				cliente.setLng(new BigDecimal(rslt.getString("lng")));
			}
		} finally {
			close(conn, stmt, rslt);
		}
		
		return cliente;
	}

	public void salvarOuAtualizarCliente(Cliente cliente) throws Exception {
		if (cliente.getId() == 0) {
			inserirCliente(cliente);
		} else {
			atualizarCliente(cliente);
		}
	}
	
	public void inserirCliente(Cliente cliente) throws Exception {
		Connection        conn    = null;
		PreparedStatement stmt    = null;
		ResultSet         rslt    = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("insert into clientes (nome, endereco, complemento, bairro, cidade, estado, lat, lng) values (?,?,?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEndereco());
			stmt.setString(3, cliente.getComplemento());
			stmt.setString(4, cliente.getBairro());
			stmt.setString(5, cliente.getCidade());
			stmt.setString(6, cliente.getEstado());
			stmt.setString(7, cliente.getLat().toString());
			stmt.setString(8, cliente.getLng().toString());
			stmt.executeUpdate();
			System.out.println("passou por aqui");
			rslt = stmt.getGeneratedKeys();
			
			if(rslt.next()){
				cliente.setId(rslt.getInt(1));
			}
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void atualizarCliente(Cliente cliente) throws Exception {
		Connection        conn    = null;
		PreparedStatement stmt    = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("update clientes set nome=?, endereco=?, complemento=?, bairro=?, cidade=?, estado=?, lat=?, lng=? where id=?");
			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getEndereco());
			stmt.setString(3, cliente.getComplemento());
			stmt.setString(4, cliente.getBairro());
			stmt.setString(5, cliente.getCidade());
			stmt.setString(6, cliente.getEstado());
			stmt.setString(7, cliente.getLat().toString());
			stmt.setString(8, cliente.getLng().toString());
			stmt.setInt(9, cliente.getId());
			stmt.executeUpdate();
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void removerCliente(int id) throws Exception {
		Connection        conn    = null;
		PreparedStatement stmt    = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("delete from clientes where id=?");
			stmt.setInt(1, id);
			stmt.executeUpdate();
		} finally {
			close(conn, stmt, null);
		}
	}

	public void atualizarAcesso(Usuario usuario) throws Exception {
		Connection        conn    = null;
		PreparedStatement stmt    = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("update usuarios set token=?, ultimo_acesso=? where email=?");
			stmt.setString(1,  usuario.getToken());
			stmt.setLong  (2,  usuario.getUltimoAcesso());
			stmt.setString(3,  usuario.getEmail());
			stmt.executeUpdate();
		} finally {
			close(conn, stmt, null);
		}
		
	}
}