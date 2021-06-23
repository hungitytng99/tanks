package com.hauhung.model;

public class Entity{
    protected int posiX = -1;
    protected int posiY = -1;

    public Entity() {
    }

    public Entity(int posiX, int posiY) {
        this.posiX = posiX;
        this.posiY = posiY;
    }

    public int getXposition()
    {
        return posiX;
    }
    public int getYposition()
    {
        return posiY;
    }

    public void setXpoistion(int x)
    {
        posiX=x;
    }
    public void setYposition(int y)
    {
        posiY=y;
    }
}
