import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CalculatorClient {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		// create socket + streams
		Socket clientSocket = new Socket("localhost", 1313);
		ObjectOutputStream outToServer = new ObjectOutputStream(
				clientSocket.getOutputStream());
		ObjectInputStream inFromServer = new ObjectInputStream(
				clientSocket.getInputStream());
		
		// send request to server
		try {
			BufferedReader stdIn = new BufferedReader (new InputStreamReader(System.in));
			String userInput;
			String repMsg = null;
			while ((userInput = stdIn.readLine()) != null) {
				outToServer.writeObject(userInput);
				outToServer.flush();
				
				if (userInput.equals("quit")) {
					break;
				}
				
				// receive response from server
				repMsg = (String) inFromServer.readObject();
				
				System.out.println(repMsg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// close socket + streams
		clientSocket.close();
		outToServer.close();
		inFromServer.close();
	}

}
