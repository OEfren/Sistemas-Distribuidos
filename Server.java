package mx.com.test.uag.socket;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;

public class Server {

	public static void main (String args[]) {
		
		DatagramSocket socketUDP = null;
		try {
			socketUDP = new DatagramSocket(10001);
			
			byte[] bufer = new byte[1000];
			
			while (true) {
				
				// Construimos el DatagramPacket para recibir peticiones
				DatagramPacket peticion = new DatagramPacket(bufer, bufer.length);
				
				// Leemos una petición del DatagramSocket
				socketUDP.receive(peticion);        
				
				System.out.print(String.format("Recibido del host %s puerto %s ",  peticion.getAddress(), peticion.getPort()));
				
				byte[] content = peticion.getData();
				String messageText = new String(content, StandardCharsets.UTF_8);
				System.out.println("Mensaje del cliente " + messageText);
				
				// Construimos el DatagramPacket para enviar la respuesta
				DatagramPacket respuesta = new DatagramPacket(
						peticion.getData(), 
						peticion.getLength(),
						peticion.getAddress(), 
						peticion.getPort()
				);
				
				// Enviamos la respuesta, que es un eco
				socketUDP.send(respuesta);
			}
		} 
		catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} 
		catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		finally {
			if (socketUDP != null && !socketUDP.isClosed())
				socketUDP.close();
		}  
	}

}
