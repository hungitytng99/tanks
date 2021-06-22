import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginGUI  extends JFrame {
    int width=240,height=200;
    private JButton registerButton;
    private JPanel registerPanel;
    private JTextField ipaddressText;
    private JTextField portText;
    private JTextField nameText;
    private JLabel ipaddressLabel;
    private JLabel portLabel;
    private JLabel nameLabel;
    public LoginGUI() {
        setTitle("Tanks Game");
        setSize(width,height);
        setLocation(60,100);
        getContentPane().setBackground(new Color(179,226,131));

        registerPanel=new JPanel();
        registerPanel.setBackground(Color.WHITE);
        registerPanel.setSize(200,140);
        registerPanel.setBounds(560,50,200,140);
        registerPanel.setLayout(null);

        nameText=new JTextField("name");
        nameText.setBounds(90,25,100,25);

        ipaddressText=new JTextField("localhost");
        ipaddressText.setBounds(90,55,100,25);

        portText=new JTextField("11111");
        portText.setBounds(90,85,100,25);

        nameLabel=new JLabel("Your name: ");
        nameLabel.setBounds(10,25,70,25);

        ipaddressLabel=new JLabel("IP address: ");
        ipaddressLabel.setBounds(10,55,70,25);

        portLabel=new JLabel("Port: ");
        portLabel.setBounds(10,85,70,25);

        registerButton=new JButton("Register");
        registerButton.setBounds(60,130,90,25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               handleLogin(e);
            }
        });
        registerButton.setFocusable(true);

        registerPanel.add(ipaddressLabel);
        registerPanel.add(portLabel);
        registerPanel.add(nameLabel);

        registerPanel.add(ipaddressText);
        registerPanel.add(portText);
        registerPanel.add(nameText);

        registerPanel.add(registerButton);

        getContentPane().add(registerPanel);
        setVisible(true);
    }
    private void handleLogin(ActionEvent e){
        Object obj=e.getSource();
        if(obj==registerButton)
        {
            if(ipaddressText.getText().equals("") || portText.getText().equals("") || nameText.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Please fill all field!","Tanks Game",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Fill all field required!");
            }
            else {
                this.setVisible(false);
                new ClientGUI(this, ipaddressText.getText(), portText.getText(), nameText.getText());
            }

        }
    }
}
