package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.Connection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private Connection connection;
	private HashMap<Integer,RPCImpl> services; 	// hashmap to register RPC methods which are required to implement RPCImpl
	
	public RPCServer(int port) {
		
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<Integer,RPCImpl>();
		
		// the stop RPC methods is built into the server
		services.put((int)RPCCommon.RPIDSTOP,new RPCServerStopImpl());
	}
	
	public void run() {
		
		System.out.println("RPC SERVER RUN - Services: " + services.size());
		
		connection = msgserver.accept(); 
		
		System.out.println("RPC SERVER ACCEPTED");
		
		boolean stop = false;
		
		while (!stop) {
	    
		   int rpcid;
		   
		   // TODO
		    
		   
		   Message msg = connection.receive();
		   byte[] data = msg.getData(); // receive message containing RPC request
		   
		   rpcid = data [0]; // - receive message containing RPC request
		   
		   RPCImpl impl = services.get(rpcid); // - receive message containing RPC request
		   
		   byte[] svar = impl.invoke(data); // - invoke the method
		   
		   Message response = new Message(svar);
		   
		   connection.send(response); // - send back message containing RPC reply

		   if (rpcid == RPCCommon.RPIDSTOP) {
			   stop = true;
		   }
		}
	
	}
	
	public void register(int rpcid, RPCImpl impl) {
		services.put(rpcid, impl);
	}
	
	public void stop() {
		connection.close();
		msgserver.stop();
		
	}
}
