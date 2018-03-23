import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CalculatorServer {
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		String sentence;
		InetAddress IPAddress;
		int port;
		String capitalizedSentence;
		DatagramPacket sendPacket;
		
		CalculatorImpl calculator = new CalculatorImpl();
		float r = 0;
		float p1 = 0;
		float p2 = 0;
		String opName = null;
		String opSymbol = null;
		String opEquals = " = ";
		String[] pars = null;
		String repMsg = null;
		
		while(true){
			serverSocket.receive(receivePacket);
			sentence = new String( receivePacket.getData());
			
			if (sentence.equals("quit")) {
				break;
			}
			
			// process request
			opName = sentence.substring(0, 3);
			pars = sentence.substring(sentence.indexOf("(")+1,
					sentence.indexOf(")")).split(",");
			p1 = Float.parseFloat(pars[0]);
			p2 = Float.parseFloat(pars[1]);
			
			if (opName.equals("sub")) {
				r = calculator.sub(p1, p2);	
				opSymbol = " - ";
			} else if (opName.equals("sum")) {
				r = calculator.sum(p1, p2);
				opSymbol = " + ";
			} else if (opName.equals("div")) {
				r = calculator.div(p1, p2);
				opSymbol = " / ";
			} else if (opName.equals("mul")) {
				r = calculator.mul(p1, p2);
				opSymbol = " * ";
			}
			
			// send response to client
			repMsg = Float.toString(p1) 
					+ opSymbol 
					+ Float.toString(p2) 
					+ opEquals 
					+ Float.toString(r);
			
			IPAddress = receivePacket.getAddress();
			port = receivePacket.getPort();
			capitalizedSentence = repMsg.toUpperCase();
			sendData = capitalizedSentence.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
		serverSocket.close();
	}
}
