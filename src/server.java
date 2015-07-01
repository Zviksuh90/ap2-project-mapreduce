import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.FileUtils;

public class server {
	
	private static final int SOME_PORT = 12344;

	static class ServerThread implements Runnable {
		Socket client = null;

		public ServerThread(Socket c) {
			this.client = c;
		}

		public void run() {
			try (BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(client.getInputStream()));
					DataOutputStream outToClient = new DataOutputStream(
							client.getOutputStream());) {

				String inputLine = new String();

				String inputPath = new String();

				inputPath = "./input";
				File theDir = new File(inputPath);

				// if the directory does not exist, create it
				if (!theDir.exists()) {

					boolean result = false;

					theDir.mkdir();
					result = true;

					if (result) {
						System.out.println("DIR created");
					}
				}

				BufferedWriter writer = new BufferedWriter(new FileWriter(
						new File(inputPath + "/input" + ".txt")));

				while (inputLine != null) {
					inputLine = inFromClient.readLine();
					writer.write(inputLine);
				}
				writer.close();
				String args[] = { inputPath, inputPath };
				// doing mapreduce
				Driver.drive(args);

				// returning to client results
				BufferedReader br = new BufferedReader(new FileReader(inputPath
						+ "/closest_results/part-r-00000"));
				try {

					String line = br.readLine();
					outToClient.writeBytes(line);
				} finally {
					br.close();
				}
				FileUtils.deleteDirectory(new File("./input"));
			} catch (Exception e) {
			} finally {
				try {
					client.close();
				} catch (Exception ex) {
				}
			}
		}
	}

	public static void main(String args[]) throws IOException {
		ServerSocket server = new ServerSocket(SOME_PORT);
		while (true) {
			Socket p = server.accept();
			new Thread(new ServerThread(p)).start();
		}
	}
}