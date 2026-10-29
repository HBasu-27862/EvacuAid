package edu.evacuaid.model;
import jakarta.persistence.DiscriminatorValue; import jakarta.persistence.Entity;
@Entity @DiscriminatorValue("ROOM") public class Room extends Place { protected Room(){} public Room(String c,String l,int x,int y){super(c,l,x,y);} @Override public String getType(){return "ROOM";} }
