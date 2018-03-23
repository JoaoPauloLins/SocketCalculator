import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class CalculatorClient {
	public static void main(String args[]) throws Exception {
		BufferedReader inFromUser;
	    DatagramSocket clientSocket = new DatagramSocket();
	    InetAddress IPAddress = InetAddress.getByName("localhost");
	    byte[] sendData = new byte[1024];
	    byte[] receiveData = new byte[1024];
	    String sentence;
	    DatagramPacket sendPacket;
	    DatagramPacket receivePacket;
	    String modifiedSentence;
		while (true) {	
			inFromUser = new BufferedReader(new InputStreamReader(System.in));
			sentence = inFromUser.readLine();
			if (sentence.equals("quit")) {
				break;
			}
			sendData = sentence.getBytes();
			sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			clientSocket.send(sendPacket);
			receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			modifiedSentence = new String(receivePacket.getData());
			System.out.println(modifiedSentence);
		}
		clientSocket.close();
	}
}
