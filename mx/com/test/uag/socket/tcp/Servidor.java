package mx.com.test.uag.socket.tcp;

import java.io.*;
import java.net.*;

public class Servidor {  
	
	public static final int PORT = 10000;
	
	public static void main(String[] args) throws IOException {
    
		ServerSocket socketServidor = null;
		try {
			socketServidor = new ServerSocket(Servidor.PORT);
		} 
		catch (IOException e) {
			System.out.println("Error en el uso del puerto: " + Servidor.PORT);  
			System.exit(-1);
		}

		Socket socketCliente = null;
		BufferedReader entrada = null;
		PrintWriter salida = null;
		System.out.println("Escuchando: " + socketServidor);
		 
		try {
			 
			// Se bloquea hasta que recibe alguna petición de un cliente
			socketCliente = socketServidor.accept();
			System.out.println("Connexión acceptada: "+ socketCliente);
			 
			// Establece canal de entrada 
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream())); 
			
			// Establece canal de salida      
			salida = new PrintWriter(new BufferedWriter(new
					OutputStreamWriter(socketCliente.getOutputStream())),true);
						 
			// Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios" 
			while (true) {  
				String str = entrada.readLine();
				System.out.println("Cliente: " + str);
				salida.println(str);
				if (str.equals("Adios")) break; 
			}
		 } 
		 catch (IOException e) {
			 System.out.println("IOException: " + e.getMessage());
		 }
		 finally {
			if (salida != null)
				salida.close();
			if (entrada != null)
				entrada.close();
			if (socketCliente != null && !socketCliente.isClosed())
				socketCliente.close();
			if (socketServidor != null && !socketServidor.isClosed())
				socketServidor.close();
		 }
	}
}
