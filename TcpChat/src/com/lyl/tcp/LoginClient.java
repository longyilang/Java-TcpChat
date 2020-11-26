package com.lyl.tcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("---Client---");
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入用户名：");
		String uname = console.readLine();
		System.out.println("请输入密码：");
		String upassword = console.readLine();
		Socket client = new Socket("localhost",8888);
		DataOutputStream dos = new DataOutputStream(client.getOutputStream());
		dos.writeUTF("uname="+uname+"&"+"upwd="+upassword);
		dos.flush();
		
		DataInputStream dis = new DataInputStream(client.getInputStream());
		String result = dis.readUTF();
		System.out.println(result);
		dis.close();
		
		dos.close();
		client.close();
	}
}
