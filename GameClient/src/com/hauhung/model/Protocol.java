package com.hauhung.model;

public class Protocol {
    private String message="";
    public Protocol() 
    {
        
    }
    
    public String RegisterPacket(int x,int y, String team)
    {
        message="Hello"+x+","+y+"/"+team;
        return message;
    }
    public String UpdatePacket(int x,int y,int id,int dir)
    {
        message="Update"+x+","+y+"-"+dir+"|"+id;
        return message;
    }
    public String ShotPacket(int id)
    {
        message="Shot"+id;
        return message;
    }
    
    public String RemoveClientPacket(int id)
    {
        message="Remove"+id;
        return message;
    }
    public String ExitMessagePacket(int id)
    {
        message="Exit"+id;
        return message;
    }
}
