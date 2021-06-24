package com.hauhung.model;

import com.hauhung.contants.ContantsStorage;
import com.hauhung.views.GameBoardPanel;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Tank extends Entity{
    
    private Image[] tankImg;
    private BufferedImage ImageBuff;
    private Bomb bomb[]=new Bomb[1000];
    private int curBomb=0;
    private int tankID;
    private int direction=1;
    private String team;
    private float velocityX=0.03125f,velocityY=0.03125f;
    private int width=559,height=473;

    public int getDirection() 
    {
        return direction;
    }
    
    /** Creates a new instance of object.Tank */
    public Tank()
    {
        // random ra 1 vi tri nam trong ban do.
        while(true){
            posiX = (int) (Math.random() * width);
            posiY = (int) (Math.random() * height);
            //chi cho phep random trong map
            if(posiX<70|posiY<50|posiY>height-43|posiX>width-43){
                continue;
            }
            // chi cho phep random khong trung tuong`
            if(posiY > (ContantsStorage.WALL_LISTS.get(0).getYposition()-43)
                    && posiY < (ContantsStorage.WALL_LISTS.get(0).getYposition()+43)
                    && posiX > (ContantsStorage.WALL_LISTS.get(0).getXposition()-43)
                    && posiX < (ContantsStorage.WALL_LISTS.get(0).getXposition()+43)){
                System.out.println("CHECK" + 0);
                continue;
            }
            if(posiY > (ContantsStorage.WALL_LISTS.get(1).getYposition()-43)
                    && posiY < (ContantsStorage.WALL_LISTS.get(1).getYposition()+43)
                    && posiX > (ContantsStorage.WALL_LISTS.get(1).getXposition()-43)
                    && posiX < (ContantsStorage.WALL_LISTS.get(1).getXposition()+43)){
                System.out.println("CHECK" + 1);
                continue;
            }
            if(posiY > (ContantsStorage.WALL_LISTS.get(2).getYposition()-43)
                    && posiY < (ContantsStorage.WALL_LISTS.get(2).getYposition()+43)
                    && posiX > (ContantsStorage.WALL_LISTS.get(2).getXposition()-43)
                    && posiX < (ContantsStorage.WALL_LISTS.get(2).getXposition()+43)){
                System.out.println("CHECK" + 2);
                continue;
            }
            break;
        }
        loadImage(4);
        
    }
    public Tank(int x,int y,int dir,int id, int indexImage, String team)
    {
        super(x,y);
        tankID=id;
        direction=dir;
        this.team = team;
        loadImage(indexImage);
    }
    public void loadImage(int a)
    {
        //co 8 anh tuong ung voi 2 loai: client va client khac. load image tuong uon voi role o day. xu ly viec cac team dong minh co cung hinh o day
        tankImg=new Image[4];
        for(int i=a;i<tankImg.length+a;i++)
        {
            tankImg[i-a]=new ImageIcon("Images/"+i+".png").getImage();
        }
        
        ImageBuff=new BufferedImage(tankImg[direction-1].getWidth(null),tankImg[direction-1].getHeight(null),BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(tankImg[direction-1],0,0,null);
    }
    public BufferedImage getBuffImage()
    {
        return ImageBuff;
    }
    public String getTeam() {return this.team;};

    public void moveLeft()
    {
        if(direction==1|direction==3) //neu dang o vi tri tren
        {
           ImageBuff=new BufferedImage(tankImg[3].getWidth(null),tankImg[3].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[3],0,0,null);
           direction=4;
        }
        else
        {
            System.out.println("move left");
            int temp;
            temp=(int)(posiX-velocityX*posiX);
            if(checkCollision(temp,posiY)==false&&temp<70)
            {
                posiX=70;
            }
            else if(checkCollision(temp,posiY)==false)
            {
                posiX=temp;
            }

        }
        
    }
    public void moveRight()
    {
        if(direction==1|direction==3)
        {
            ImageBuff=new BufferedImage(tankImg[1].getWidth(null),tankImg[1].getHeight(null),BufferedImage.TYPE_INT_RGB);
            ImageBuff.createGraphics().drawImage(tankImg[1],0,0,null);
            direction=2;
        }
        else
        {
            int temp;
            temp=(int)(posiX+velocityX*posiX);
            if(checkCollision(temp,posiY)==false&&temp>width-43+20)
            {

                posiX=width-43+20;
            }
            else if(checkCollision(temp,posiY)==false)
            {
                posiX=temp;
            }
        }
        
    }


    public void moveForward()
    {
        if(direction==2|direction==4)
        {
           ImageBuff=new BufferedImage(tankImg[0].getWidth(null),tankImg[0].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[0],0,0,null);
           direction=1;
        }
        else
        {
            int temp;
            temp=(int)(posiY-velocityY*posiY);
            if(checkCollision(posiX,temp)==false&&temp<50)
            {
                posiY=50;
            }
            else if(checkCollision(posiX,temp)==false)
            {
                posiY=temp;
            }
        }
    }
    public void moveBackward()
    {
        if(direction==2|direction==4)
        {
           ImageBuff=new BufferedImage(tankImg[2].getWidth(null),tankImg[2].getHeight(null),BufferedImage.TYPE_INT_RGB);
           ImageBuff.createGraphics().drawImage(tankImg[2],0,0,null);
           direction=3;
        }
        else
        {
            int temp;
            temp=(int)(posiY+velocityY*posiY);
            if(checkCollision(posiX,temp)==false&&temp>height-43+50)
            {
                posiY=height-43+50;
            }
            else if(checkCollision(posiX,temp)==false)
            {
                posiY=temp;
            }
        }
    }


    public void shot()
    {
        bomb[curBomb]=new Bomb(this.getXposition(),this.getYposition(),direction);
        bomb[curBomb].startBombThread(true);
        curBomb++;
    }
    public Bomb[] getBomb()
    {
        return bomb;
    }
    public void setTankID(int id)
    {
        tankID=id;
    }
    public int getTankID()
    {
        return tankID;
    }
    public void setDirection(int dir)
    {
        ImageBuff=new BufferedImage(tankImg[dir-1].getWidth(null),tankImg[dir-1].getHeight(null),BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(tankImg[dir-1],0,0,null);
        direction=dir;
    }

    public void Shot()
    {
        bomb[curBomb]=new Bomb(this.getXposition(),this.getYposition(),direction);
        bomb[curBomb].startBombThread(false);
        curBomb++;

    }
    public boolean checkWallCollision(int direction, int xP, int yP){
        for(int i = 0; i < ContantsStorage.WALL_LISTS.size(); i ++){
            System.out.println("Check Wall + " + i + "direction: " + direction);
            Wall wall = ContantsStorage.WALL_LISTS.get(i);
            int y = wall.getYposition();
            int x = wall.getXposition();
            if(direction==1)
            {
                if(((yP<=y+43)&&yP>=y)&&((xP<=x+43&&xP>=x)||(xP+43>=x&&xP+43<=x+43)))
                {
                    return true;
                }
            }
            else if(direction==2)
            {
                if(((xP+43>=x)&&xP+43<=x+43)&&((yP<=y+43&yP>=y)||(yP+43>=y&&yP+43<=y+43)))
                {
                    return true;
                }
            }
            else if(direction==3)
            {
                if(((yP+43>=y)&&yP+43<=y+43)&&((xP<=x+43&&xP>=x)||(xP+43>=x&&xP+43<=x+43)))
                {
                    return true;
                }
            }
            else if(direction==4)
            {
                if(((xP<=x+43)&&xP>=x)&&((yP<=y+43&&yP>=y)||(yP+43>=y&&yP+43<=y+43)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkCollision(int xP,int yP)
    {
        if(checkWallCollision(direction,xP,yP)){
            return true;
        }
        ArrayList<Tank>clientTanks= GameBoardPanel.getClients();
        int x,y;
        for(int i=1;i<clientTanks.size();i++) {
            if(clientTanks.get(i)!=null) 
            {
                x=clientTanks.get(i).getXposition();
                y=clientTanks.get(i).getYposition();
                if(direction==1)
                {       
                    if(((yP<=y+43)&&yP>=y)&&((xP<=x+43&&xP>=x)||(xP+43>=x&&xP+43<=x+43))) 
                    { 
                        return true;
                    }
                }
                else if(direction==2)
                {
                    if(((xP+43>=x)&&xP+43<=x+43)&&((yP<=y+43&yP>=y)||(yP+43>=y&&yP+43<=y+43))) 
                    { 
                        return true;
                    }
                }
                else if(direction==3)
                {
                    if(((yP+43>=y)&&yP+43<=y+43)&&((xP<=x+43&&xP>=x)||(xP+43>=x&&xP+43<=x+43))) 
                    { 
                        return true;
                    }
                }
                else if(direction==4)
                {
                    if(((xP<=x+43)&&xP>=x)&&((yP<=y+43&&yP>=y)||(yP+43>=y&&yP+43<=y+43))) 
                    { 
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return
                ", tankID=" + tankID +
                ", posiX=" + posiX +
                ", posiY=" + posiY +
                ", direction=" + direction +
                ", team='" + team + '\''
                ;
    }
}
