package mx.com.test.uag.socket.tcp;

import java.net.*;
import java.io.*;

public class Cliente {
	
	public static final int PORT = 10000;
	
	public static void main(String[] args)  throws IOException {
		
		Socket socketCliente = null;    
		BufferedReader entrada = null;
		PrintWriter salida = null;
		
		try {
			socketCliente = new Socket("localhost", Cliente.PORT);
			// Canal de entrada      
			entrada = new BufferedReader(
					new InputStreamReader(socketCliente.getInputStream()));
			// Canal de salida
			salida = new PrintWriter(new BufferedWriter(new
					OutputStreamWriter(socketCliente.getOutputStream())), true);
		} 
		catch (IOException e) {
			System.err.println("No puede establer canales de E/S para la conexión");    
			System.exit(-1);
		}
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    
		try {  
			while (true) {		
				System.out.println("Por favor, escriba su mensaje:");
				// Entrada consola
				String mensaje = stdIn.readLine();
				// Envio servidor        
				salida.println(mensaje);
				// Responde servidor
				mensaje = entrada.readLine();
				System.out.println("Respuesta servidor: " + mensaje);
				// Mensaje "Adios" es que finaliza la comunicación	
				if (mensaje.equals("Adios")) break;
      
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
			if (stdIn != null)
				stdIn.close();
			if (socketCliente != null && !socketCliente.isClosed())
				socketCliente.close();
		}
	}
}
