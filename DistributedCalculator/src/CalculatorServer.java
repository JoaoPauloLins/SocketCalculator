import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CalculatorServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		// create sockets + streams
		ServerSocket welcomeSocket = new ServerSocket(1313);
		Socket connectionSocket = welcomeSocket.accept();
		ObjectOutputStream outToClient = new ObjectOutputStream(
				connectionSocket.getOutputStream());
		ObjectInputStream inFromClient = new ObjectInputStream(
				connectionSocket.getInputStream());
		
		try {
			// receive request from client
			String rcvMsg = (String) inFromClient.readObject();
			
			CalculatorImpl calculator = new CalculatorImpl();
			float r = 0;
			float p1 = 0;
			float p2 = 0;
			String opName = null;
			String opSymbol = null;
			String opEquals = " = ";
			String[] pars = null;
			String repMsg = null;
			
			while (rcvMsg != null) {				
				// process request
				opName = rcvMsg.substring(0, 3);
				pars = rcvMsg.substring(rcvMsg.indexOf("(")+1,
						rcvMsg.indexOf(")")).split(",");
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
				outToClient.writeObject(repMsg);
				outToClient.flush();
				
				rcvMsg = (String) inFromClient.readObject();
				if (rcvMsg.equals("quit")) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// close sockets + streams
		connectionSocket.close();
		welcomeSocket.close();
		outToClient.close();
	}
}
