package com.lyl.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8888);
		Socket client = server.accept();
		System.out.println("一个客户端建立了连接");
	}
}
