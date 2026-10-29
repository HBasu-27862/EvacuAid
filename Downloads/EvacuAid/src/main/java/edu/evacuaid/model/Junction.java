package edu.evacuaid.model;
import jakarta.persistence.DiscriminatorValue; import jakarta.persistence.Entity;
@Entity @DiscriminatorValue("JUNCTION") public class Junction extends Place { protected Junction(){} public Junction(String c,String l,int x,int y){super(c,l,x,y);} @Override public String getType(){return "JUNCTION";} }
