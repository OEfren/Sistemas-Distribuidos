package mx.com.test.uag.socket;

import java.net.*;
import java.io.*;

public class Cliente {

	public static void main(String args[]) {

		DatagramSocket socketUDP = null;
		
		try {
			System.out.println("Escriba un mensaje: ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
	        // Reading data using readLine
	        String mensajeText = reader.readLine();
    	
			socketUDP = new DatagramSocket();
			byte[] mensaje = mensajeText.getBytes();
			InetAddress hostServidor = InetAddress.getByName("127.0.0.1");
			int puertoServidor = 10001;

			// Construimos un datagrama para enviar el mensaje al servidor
			DatagramPacket peticion = new DatagramPacket(mensaje, mensajeText.length(), hostServidor, puertoServidor);

			// Enviamos el datagrama      
			socketUDP.send(peticion);
      
			// Construimos el DatagramPacket que contendrá la respuesta
			byte[] bufer = new byte[1000];
      
			DatagramPacket respuesta = new DatagramPacket(bufer, bufer.length);
			socketUDP.receive(respuesta);
			// Enviamos la respuesta del servidor a la salida estandar
			System.out.println("Respuesta servidor: " + new String(respuesta.getData()));
			
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
