package com.hauhung.views;

import com.hauhung.model.Client;
import com.hauhung.helper.Protocol;
import com.hauhung.model.Tank;
import com.hauhung.model.Wall;
import com.hauhung.contants.ContantsStorage;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIClient extends JFrame implements WindowListener
{
    private  static JLabel nameLabel;
    private static JLabel scoreLabel;
    public static JPanel gameStatusPanel;
    private Client  client;
    private Tank clientTank;
    private ArrayList<Wall> wallList = new ArrayList<Wall>();
    private static int score;
    
    int width=720,height=600;
    boolean isRunning=true;
    private GameBoardPanel boardPanel;
    private UserPanel userPanel;
    JFrame loginGUI;

    String ipaddressText;
    String portText;
    String nameText;

    public GUIClient(JFrame loginGUI, String ipaddressText, String portText, String nameText, String teamText)
    {
        this.loginGUI = loginGUI;
        this.ipaddressText = ipaddressText;
        this.portText = portText;
        this.nameText = nameText;
        ContantsStorage.TEAM = teamText;
        // Khoi tao tuong
        int wallAx = 150;
        int wallAy = 150;
        int wallBx = 240;
        int wallBy = 300;
        int wallCx = 410;
        int wallCy = 215;
        ContantsStorage.WALL_LISTS.add(new Wall(wallAx,wallAy));
        ContantsStorage.WALL_LISTS.add(new Wall(wallBx,wallBy));
        ContantsStorage.WALL_LISTS.add(new Wall(wallCx,wallCy));

        score=0;

        nameLabel = new JLabel(nameText);
        nameLabel.setBounds(100,100,100,25);

        gameStatusPanel=new JPanel();
        gameStatusPanel.setBackground(new Color(179,226,131));
        gameStatusPanel.setSize(200,300);
        gameStatusPanel.setBounds(530,180,200,311);
        gameStatusPanel.setLayout(null);
        
        scoreLabel=new JLabel("Score : 0");
        scoreLabel.setBounds(10,90,100,25);
        // tao ra client co Protocol
        client=Client.getGameClient();
//        client.sendToServer(new Protocol().getWallsPackage());
        // tao tank va them event cho tank
        clientTank=new Tank();
        //tao map, them tank nhung set = false de chua active panel. Chi khi tank, client co day du thong tin moi active
        boardPanel=new GameBoardPanel(clientTank,false);
        // print user information and logout
        userPanel=new UserPanel(nameText,scoreLabel, this, clientTank);
        gameStatusPanel.add(scoreLabel);

        initClient();
        setTitle("Tanks Game");
        setSize(width,height);
        setLocation(60,100);
        getContentPane().setBackground(new Color(179,226,131));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        addWindowListener(this);

        getContentPane().add(boardPanel);
        getContentPane().add(userPanel);
        getContentPane().add(gameStatusPanel);
    }
    
    public static int getScore()
    {
        return score;
    }
    
    public static void setScore(int scoreParametar)
    {
        score+=scoreParametar;
        scoreLabel.setText("Score : "+score);
    }
    
    public void initClient()
    {
            try
            {
                 client.register(nameText,ipaddressText,Integer.parseInt(portText),clientTank.getXposition(),clientTank.getYposition(),ContantsStorage.TEAM);
                 boardPanel.setGameStatus(true);
                 boardPanel.repaint();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                 new ClientRecivingThread(client.getSocket()).start();
                 boardPanel.setFocusable(true);
                 this.setVisible(true);
            } catch (IOException ex)
            {
                this.setVisible(false);
                new GUILogin();
                JOptionPane.showMessageDialog(this,"The Server is not running, try again later!","Tanks Game",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("The Server is not running!");
            }
    }

    public void windowOpened(WindowEvent e) 
    {

    }

    public void windowClosing(WindowEvent e) 
    {
        Client.getGameClient().sendToServer(new Protocol().ExitMessagePacket(clientTank.getTankID()));
    }
    public void windowClosed(WindowEvent e) {
        
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    
    public class ClientRecivingThread extends Thread
    {
        Socket clientSocket;
        DataInputStream reader;
        public ClientRecivingThread(Socket clientSocket)
        {
            this.clientSocket=clientSocket;
            try {
                reader=new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        public void run()
        {
            while(isRunning) 
            {
                String sentence="";
                try {
                    sentence=reader.readUTF();                
                } catch (IOException ex) {
                    ex.printStackTrace();
                }                
               if(sentence.startsWith("ID"))
               {
                    int id=Integer.parseInt(sentence.substring(2));
                    clientTank.setTankID(id);
                    System.out.println("My tank ID= "+id);

              }
               else if(sentence.startsWith("NewClient"))
               {
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int pos4=sentence.indexOf('/');
                    int x=Integer.parseInt(sentence.substring(9,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,pos4));
                    String team =sentence.substring(pos4+1,sentence.length());
                    if(id!=clientTank.getTankID()) {
                        System.out.println("TEAM --- " + team);
                        if(team.equals(ContantsStorage.TEAM)){
                            boardPanel.registerNewTank(new Tank(x, y, dir, id, 8, team));
                        } else {
                            boardPanel.registerNewTank(new Tank(x, y, dir, id, 0, team));
                        }
                    }
               }   
               else if(sentence.startsWith("Update"))
               {
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('-');
                    int pos3=sentence.indexOf('|');
                    int x=Integer.parseInt(sentence.substring(6,pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1,pos2));
                    int dir=Integer.parseInt(sentence.substring(pos2+1,pos3));
                    int id=Integer.parseInt(sentence.substring(pos3+1,sentence.length()));
                
                    if(id!=clientTank.getTankID())
                    {
                        boardPanel.getTank(id).setXpoistion(x);
                        boardPanel.getTank(id).setYposition(y);
                        boardPanel.getTank(id).setDirection(dir);
                        boardPanel.repaint();
                    }
                    
               }
               else if(sentence.startsWith("Shot"))
               {
                    int id=Integer.parseInt(sentence.substring(4));
                
                    if(id!=clientTank.getTankID())
                    {
                        boardPanel.getTank(id).Shot();
                    }
                    
               }
               else if(sentence.startsWith("Remove"))
               {
                  int id=Integer.parseInt(sentence.substring(6));
                  
                  if(id==clientTank.getTankID())
                  {
                        int response=JOptionPane.showConfirmDialog(null,"Sorry, You are loss. Do you want to try again ?","Tanks Game",JOptionPane.OK_CANCEL_OPTION);
                        if(response==JOptionPane.OK_OPTION)
                        {
                            //client.closeAll();
                            setVisible(false);
                            dispose();
                            new GUIClient(loginGUI, ipaddressText, portText, nameText, ContantsStorage.TEAM);
                        }
                        else
                        {
                            loginGUI.setVisible(true);
                            setVisible(false);
                        }
                  }
                  else
                  {
                      boardPanel.removeTank(id);
                  }
               }
               else if(sentence.startsWith("Exit"))
               {
                   int id=Integer.parseInt(sentence.substring(4));
                  
                  if(id!=clientTank.getTankID())
                  {
                      boardPanel.removeTank(id);
                  }
               }
                      
            }
           
            try {
                reader.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }
    
}
