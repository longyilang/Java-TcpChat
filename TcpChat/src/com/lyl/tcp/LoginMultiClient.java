package com.lyl.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginMultiClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("---Client---");
		
		Socket client = new Socket("localhost",8888);
		new Send(client).send();
		new Receive(client).receive();
	}
	
	static class Send{
		private Socket client;
		private BufferedReader console;
		private DataOutputStream dos;
		private String msg;
		public Send(Socket client) throws IOException {
			this.client = client;
			console = new BufferedReader(new InputStreamReader(System.in));
			msg = init();
			try {
				dos = new DataOutputStream(this.client.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private String init() throws IOException { 
			System.out.println("请输入用户名：");
			String uname = console.readLine();
			System.out.println("请输入密码：");
			String upassword = console.readLine();
			String string = "uname="+uname+"&"+"upwd="+upassword;
			return string;
		}
		
		private void send() {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void release() {
			try {
				dos.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static class Receive{
		private Socket client;
		private  DataInputStream dis;
		public Receive(Socket client) throws IOException {
			this.client = client;
			
		    try {
				dis = new DataInputStream(this.client.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			// TODO Auto-generated constructor stub
		}
		
		private void receive() {
			try {
				String result = dis.readUTF();
				System.out.println(result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				release();
			}
		}
		
		private void release(){
			try {
				dis.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
