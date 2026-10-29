package edu.evacuaid.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"building_id","code"}))
public class Passage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) private Building building;
    @Column(nullable=false) private String code;
    @Column(name="from_code",nullable=false) private String fromCode;
    @Column(name="to_code",nullable=false) private String toCode;
    private double cost;
    private boolean blocked;
    protected Passage(){}
    public Passage(String code,String fromCode,String toCode,double cost){this.code=code;this.fromCode=fromCode;this.toCode=toCode;this.cost=cost;}
    public Long getId(){return id;} public Building getBuilding(){return building;} public void setBuilding(Building b){building=b;}
    public String getCode(){return code;} public String getFromCode(){return fromCode;} public String getToCode(){return toCode;} public double getCost(){return cost;} public boolean isBlocked(){return blocked;}
    public void setBlocked(boolean v){blocked=v;} public void setCost(double v){cost=v;}
}
