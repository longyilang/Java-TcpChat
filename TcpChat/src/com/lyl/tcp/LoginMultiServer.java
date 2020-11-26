package com.lyl.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginMultiServer {
	public static void main(String[] args) throws IOException {
		System.out.println("---server---");
		ServerSocket server = new ServerSocket(8888);
		while (true) {
			Socket client = server.accept();
			new Thread(new Chennel(client)).start();
		}
	}
	
	static  class Chennel implements Runnable{
		private Socket client;
		private DataInputStream dis;
		private DataOutputStream dos;
		
		public Chennel(Socket client) {
			this.client = client;
			try {
				dis = new DataInputStream(this.client.getInputStream());
				dos = new DataOutputStream(this.client.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String uname = "";
			String upwd = "";
			
			String datas = receiveMessage();
			String[] dataArray = datas.split("&");
			for (String info : dataArray) {
				String[] userInfo = info.split("=");
				if (userInfo[0].equals("uname")) {
					System.out.println("你的用户名为："+userInfo[1]);
					uname = userInfo[1];
				}else if(userInfo[0].equals("upwd")) {
					System.out.println("你的密码为："+userInfo[1]);
					upwd = userInfo[1];
				}
			}
			
			if (uname.equals("lyl") && upwd.equals("123")) {
				sendMessage("登录成功");
			}else {
				sendMessage("登录失败");
			}
		}
		
		private String receiveMessage() {
			String datas = null;
			try {
				datas = dis.readUTF();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return datas;
		}
		private void sendMessage(String string) {
			try {
				dos.writeUTF(string);
				dos.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				release();
			}
		}
		
		private void release(){
			try {
				dis.close();
				dos.close();
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				release();
			}
		}
	}
}
