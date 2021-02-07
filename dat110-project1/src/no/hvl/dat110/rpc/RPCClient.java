package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingClient;

public class RPCClient {

	private MessagingClient msgclient;
	private Connection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}
	
	public void register(RPCStub remote) {
		remote.register(this);
	}
	
	public void connect() {
		
		if (connection == null) {
			try {
				connection = msgclient.connect();
			} catch (Exception e) {
				System.out.println("An error occurred: " + e);
			}
		}
	}
	
	public void disconnect() {

		try{
			if(connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			System.out.println("An error occurred: " + e);
		}
	}
	
	public byte[] call(byte[] rpcrequest) {
		byte[] rpcreply;

		Message requestMessage = new Message(rpcrequest);
		
		connection.send(requestMessage);
		
		Message replyMessage = connection.receive();
		
		rpcreply = replyMessage.getData();

		return rpcreply;
	}

}
