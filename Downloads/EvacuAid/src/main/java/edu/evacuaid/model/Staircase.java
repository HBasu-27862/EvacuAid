package edu.evacuaid.model;
import jakarta.persistence.DiscriminatorValue; import jakarta.persistence.Entity;
@Entity @DiscriminatorValue("STAIR") public class Staircase extends Place { protected Staircase(){} public Staircase(String c,String l,int x,int y){super(c,l,x,y);} @Override public String getType(){return "STAIR";} }
