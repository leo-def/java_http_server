package cadastro.entidade;

public class Usuario {
	public static long TOKEN_MAX_AGE = 7 * 24 * 60 * 60 * 1000;
	
	private String email;
	private String nome;
	private String senha;
	private String token;
	private long ultimoAcesso;

	public long getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(long ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public Usuario() {
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public boolean validar(String senha) {
		return this.senha.equals(senha);
	}
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean validarAcesso() {
		if(System.currentTimeMillis() - ultimoAcesso <= TOKEN_MAX_AGE){
			//valid		
			ultimoAcesso = System.currentTimeMillis();
			return true;
		}else{
			//invalid
			return false;
		}
	}

}