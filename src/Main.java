import http.Servidor;
import java.util.Scanner;
import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Iniciando servidor.");
		Servidor servidor = new Servidor();
		servidor.start();
		
		System.out.println("Pressione ENTER para finalizar.");
		new Scanner(System.in).nextLine();
		
		System.out.println("Finalizando servidor.");
		servidor.stop();
		
		System.out.println("Fim.");
	}
}