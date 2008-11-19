package com.googlecode.jsendnsca.mocks;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class MockNscaDaemon implements Runnable {
	private static final int NSCA_PORT = 5667;
	private static final int INITIALISATION_VECTOR_SIZE = 128;

	public void run() {

		try {
			ServerSocket serverSocket = null;
			serverSocket = new ServerSocket(NSCA_PORT);
			Socket clientSocket = null;
			clientSocket = serverSocket.accept();

			DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());

			// write init vector
			byte[] initVector = new byte[INITIALISATION_VECTOR_SIZE];
			outputStream.write(initVector);
			outputStream.writeInt((int) new Date().getTime());
			outputStream.flush();

			// read passive check bytes
			byte[] passiveCheckBytes = new byte[720];
			inputStream.readFully(passiveCheckBytes, 0, 720);

			clientSocket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
