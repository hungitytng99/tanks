package com.hauhung.model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
public class Wall extends Entity{
    private Image wallImg;
    private BufferedImage ImageBuff;
    public Wall(int positionX, int positionY){
        super(positionX,positionY);
        loadImage();
    }

    private void loadImage(){
        wallImg = new ImageIcon("Images/wall.png").getImage();
        ImageBuff = new BufferedImage(wallImg.getWidth(null), wallImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
        ImageBuff.createGraphics().drawImage(wallImg, 0, 0, null);
    }

    public BufferedImage getBuffImage()
    {
        return ImageBuff;
    }


}