package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class Connection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {
		
		  try {
	            outStream.write(message.encapsulate(), 0, MessageConfig.SEGMENTSIZE);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		  
	}

	/**
	* Leser data fra TCP connection
	*/
	public Message receive() {

		Message message;
		byte[] recvbuf;

		recvbuf = new byte[MessageConfig.SEGMENTSIZE];

        try {
         inStream.read(recvbuf, 0, MessageConfig.SEGMENTSIZE);

        }catch (IOException e) {
            e.printStackTrace();
        }

        message = new Message();
        message.decapsulate(recvbuf);

        return message;

	}

	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}