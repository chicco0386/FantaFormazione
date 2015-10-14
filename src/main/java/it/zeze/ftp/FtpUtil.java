package it.zeze.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtil {

	public static void main(String[] args) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		BufferedReader dataIn = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("Inserisci URL FTP: ");
			String ftpURL = dataIn.readLine();
			ftpClient.connect(ftpURL);
			ftpClient.enterRemotePassiveMode();
			System.out.print("Inserisci username FTP: ");
			String user = dataIn.readLine();
			System.out.print("Inserisci password FTP: ");
			String pass = dataIn.readLine();
			ftpClient.login(user, pass);
			FTPFile[] listFile = ftpClient.listFiles();
			System.out.println("File list " + listFile.length);
		} finally {
			ftpClient.disconnect();
		}
	}

}
