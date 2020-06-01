package com.company;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.net.ServerSocket;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CurabiturServerTest {
	private static int port= 4444;
	protected User u;
	private ServerSocket server;
	Socket clientSocket;
	private List<User> clients;

	
	@Before
	public void setUp() throws Exception {
		server = new ServerSocket(port);
		clientSocket = server.accept();
		u = new User(clientSocket, "Nom");
		clients = new ArrayList<User>();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddUser() {
		assertTrue("User added successfully", clients.add(u));
	}

}
