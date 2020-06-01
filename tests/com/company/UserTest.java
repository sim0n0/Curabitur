package com.company;

import static org.junit.Assert.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private Utilisateur u;
	private String color;
	private ServerSocket server;
	Socket clientSocket;
	private List<Utilisateur> clients;
	
	@Before
	public void setUp() throws Exception {
		u = new Utilisateur(clientSocket, "Nom");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChangeColor() {
		u.changecouleur("#3079ab");
	}

}
