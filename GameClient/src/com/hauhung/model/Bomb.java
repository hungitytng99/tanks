package com.hauhung.model;

import com.hauhung.contants.ContantsStorage;
import com.hauhung.views.GUIClient;
import com.hauhung.views.GameBoardPanel;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Bomb extends Entity{
    private Image bombImg;
    private BufferedImage bombBuffImage;

    private int direction;
    public boolean stop=false;
    private float velocityX=0.05f,velocityY=0.05f;
    String teamTank;
    
    public Bomb(int x,int y,int direction,String team) {
        posiX=x;
        posiY=y;
        this.direction=direction;
        this.teamTank = team;
        stop=false;
        bombImg=new ImageIcon("Images/bomb.png").getImage();
        
        bombBuffImage=new BufferedImage(bombImg.getWidth(null),bombImg.getHeight(null),BufferedImage.TYPE_INT_RGB);
        bombBuffImage.createGraphics().drawImage(bombImg,0,0,null);
    }
    public BufferedImage getBomBufferdImg() {
        return bombBuffImage;
    }
    
    public BufferedImage getBombBuffImage() {
        return bombBuffImage;
    }
    
    public boolean checkCollision() 
    {
        ArrayList<Tank>clientTanks= GameBoardPanel.getClients();
        int x,y;
        String team;
        for(int i=1;i<clientTanks.size();i++) {
            if(clientTanks.get(i)!=null) {
                x=clientTanks.get(i).getXposition();
                y=clientTanks.get(i).getYposition();
                if((posiY>=y&&posiY<=y+43)&&(posiX>=x&&posiX<=x+43))
                {
                    team = clientTanks.get(i).getTeam();
                    if(team.equals(this.teamTank)){
                        return false;
                    }
                    GUIClient.setScore(50);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    if(clientTanks.get(i)!=null)
                     Client.getGameClient().sendToServer(new Protocol().RemoveClientPacket(clientTanks.get(i).getTankID()));
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    public void startBombThread(boolean chekCollision) {
            new BombShotThread(chekCollision).start();
    }
    
    private class BombShotThread extends Thread 
    {    
        boolean checkCollis;
        public BombShotThread(boolean chCollision)
        {
            checkCollis=chCollision;
        }
        public boolean checkWallCollision(){
//            if((posiY>=y&&posiY<=y+43)&&(posiX>=x&&posiX<=x+43))
            for(int i = 0 ; i < ContantsStorage.WALL_LISTS.size(); i ++){
                if((posiY >= ContantsStorage.WALL_LISTS.get(i).getYposition() && posiY <= (ContantsStorage.WALL_LISTS.get(i).getYposition() + 43))
                        &&( posiX >= ContantsStorage.WALL_LISTS.get(i).getXposition() && posiX <= (ContantsStorage.WALL_LISTS.get(i).getXposition() + 43))){
                    return true;
                }
            }
            return false;
        }
        public void run() 
        {
            if(checkCollis) {
                
                if(direction==1) 
                {
                    posiX=17+posiX;
                    while(posiY>50) 
                    {
                        if(checkWallCollision()) break;
                        posiY=(int)(posiY-posiY*velocityY);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    
                }
                else if(direction==2) 
                {
                    posiY=17+posiY;
                    posiX+=30;
                    while(posiX<564) 
                    {
                        if(checkWallCollision()) break;
                        posiX=(int)(posiX+posiX*velocityX);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==3) 
                {
                    posiY+=30;
                    posiX+=20;
                    while(posiY<505) 
                    {
                        if(checkWallCollision()) break;
                        posiY=(int)(posiY+posiY*velocityY);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==4) 
                {
                    posiY=21+posiY;
                    
                    while(posiX>70) 
                    {
                        if(checkWallCollision()) break;
                        posiX=(int)(posiX-posiX*velocityX);
                        if(checkCollision()) 
                        {
                            break;
                        }
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                stop=true;
            } 
            else 
            {
                 if(direction==1) 
                {
                    posiX=17+posiX;
                    while(posiY>50) 
                    {
                        if(checkWallCollision()) break;
                        posiY=(int)(posiY-posiY*velocityY);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                    
                } 
                else if(direction==2) 
                {
                    posiY=17+posiY;
                    posiX+=30;
                    while(posiX<564) 
                    {
                        if(checkWallCollision()) break;
                        posiX=(int)(posiX+posiX*velocityX);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==3) 
                {
                    posiY+=30;
                    posiX+=20;
                    while(posiY<505) 
                    {
                        if(checkWallCollision()) break;
                        posiY=(int)(posiY+posiY*velocityY);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                else if(direction==4) 
                {
                    posiY=21+posiY;
                    
                    while(posiX>70) 
                    {
                        if(checkWallCollision()) break;
                        posiX=(int)(posiX-posiX*velocityX);
                        
                        try {
                            
                            Thread.sleep(40);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                    }
                }
                
                stop=true;
            }
        }
    }
}
