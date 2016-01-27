import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class Network implements ActionListener {
    JButton ServerButton, ClientButton;
    JFrame SorC, gameFrame;
    Network() {
	SorC = new JFrame();
	gameFrame = new JFrame();
	SorC.setTitle("Server or Client");
	SorC.setResizable(false);
	SorC.setVisible(true);
	SorC.add(new JLabel("Server or Client?"), BorderLayout.NORTH);
	ServerButton = new JButton("Server");
	ClientButton = new JButton("Client");
	ServerButton.addActionListener(this);
	ClientButton.addActionListener(this);
	SorC.add(ServerButton, BorderLayout.WEST);
	SorC.add(ClientButton, BorderLayout.EAST);
	SorC.pack();
    }

    public void actionPerformed(ActionEvent e) {
	final int port = 10800;
	if (e.getSource() == ServerButton) {
	    try {
		ServerSocket srvSock = new ServerSocket(port);
		Socket socket = srvSock.accept();
		System.out.println("接続先: " + socket.getInetAddress());
		JFrame selectFrame = new JFrame();
		Select select = new Select(SorC, SorC);
		selectFrame.setSize(640, 480);
		selectFrame.setResizable(false);
		new Thread(select).start();
		selectFrame.add(select);
		selectFrame.setVisible(true);

	    } catch (Exception ex) { }
	} else if (e.getSource() == ClientButton) {
	    try {
		InetSocketAddress sAddress = 
		    new InetSocketAddress("127.0.0.1", port);
		Socket socket = new Socket();
		socket.connect(sAddress, 10000);
		InetAddress inetadrs;
		if ((inetadrs = socket.getInetAddress()) != null) {
		    System.out.println("接続先: " + inetadrs);
		} else {
		    System.out.println("接続に失敗しました");
		    System.exit(0);
		}
	    } catch  (Exception ex) { }
	}
    }
}
