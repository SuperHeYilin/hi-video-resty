package cn.diffpi.resource.track;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client() {
		try {

			for (int i = 0; i < 5; i++) {
				socket = new Socket("bepier.com", 16539);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader line = new BufferedReader(new InputStreamReader(System.in));

				//out.println(line.readLine());
				out.println("asdasdsad");

				line.close();
				out.close();
				in.close();

				socket.close();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Client();
	}

}
