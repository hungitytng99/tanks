import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import javax.swing.*;
/*
 * ServerGUI.java
 *
 * Created on 07 √»—Ì·, 2008, 06:32 „
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class ServerGUI extends JFrame implements ActionListener {
    
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLabel;
    private JLabel portLable;
    private JTextField portTextField;
    
    private Server server;
    /** Creates a new instance of ServerGUI */
    public ServerGUI() 
    {
        setTitle("Game Server GUI");
        setBounds(350,200,300,200);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(null);

        portLable = new JLabel("Enter Port:");
        portLable.setBounds(50,15,100,30);

        portTextField = new JTextField("1234");
        portTextField.setBounds(150,10,120,40);
        portTextField.addActionListener(this);

        startServerButton=new JButton("Start Server");
        startServerButton.setBounds(20,60,120,25);
        startServerButton.addActionListener(this);
        
        stopServerButton=new JButton("Stop Server");
        stopServerButton.setBounds(150,60,120,25);
        stopServerButton.addActionListener(this);
        
        statusLabel=new JLabel();
        statusLabel.setBounds(80,90,200,25);
        
        getContentPane().add(statusLabel);
        getContentPane().add(startServerButton);
        getContentPane().add(stopServerButton);
        getContentPane().add(portTextField);
        getContentPane().add(portLable);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==startServerButton)
        {
            if(portTextField.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please fill all field!","Tanks Game",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Fill all field required!");
            }else{
                try {

                    server=new Server(Integer.parseInt(portTextField.getText()));
                } catch (SocketException ex) {
                    ex.printStackTrace();
                }
                server.start();
                startServerButton.setEnabled(false);
                statusLabel.setText("Server is running.....");
            }

        }
        
        if(e.getSource()==stopServerButton)
        {
            try {
                server.stopServer();
                statusLabel.setText("Server is stopping.....");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
