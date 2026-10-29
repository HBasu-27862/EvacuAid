package edu.evacuaid.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity @DiscriminatorValue("EXIT")
public class EmergencyExit extends Place {
    protected EmergencyExit() {}
    public EmergencyExit(String code,String label,int x,int y){super(code,label,x,y);}
    @Override public String getType(){return "EXIT";}
}
