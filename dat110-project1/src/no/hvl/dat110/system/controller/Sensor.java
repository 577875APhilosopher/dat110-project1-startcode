package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCStub;
import no.hvl.dat110.rpc.RPCUtils;

public class Sensor extends RPCStub {

	private byte RPCID = 1;
	
	public int read() {
		
		int temp = 0;
		
        // implements the client-side stub for the int read() RPC method.
		rpcclient.connect();
		byte[] tempTemp = RPCUtils.marshallInteger(RPCID, temp);
		byte[] response = rpcclient.call(tempTemp);
		temp = RPCUtils.unmarshallInteger(response);

		return temp;
	}
	
}
