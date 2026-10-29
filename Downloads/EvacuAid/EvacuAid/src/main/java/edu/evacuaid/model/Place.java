package edu.evacuaid.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"building_id","code"}))
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="place_type")
public abstract class Place {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) private Building building;
    @Column(nullable=false) private String code;
    @Column(nullable=false) private String label;
    private int x; private int y;
    @Column(nullable=false,columnDefinition="integer default 1") private int floorNumber=1;
    protected Place() {}
    protected Place(String code,String label,int x,int y){this.code=code;this.label=label;this.x=x;this.y=y;}
    public abstract String getType();
    public Long getId(){return id;} public Building getBuilding(){return building;} public void setBuilding(Building b){building=b;}
    public String getCode(){return code;} public String getLabel(){return label;} public int getX(){return x;} public int getY(){return y;} public int getFloorNumber(){return floorNumber;}
    public void setCode(String v){code=v;} public void setLabel(String v){label=v;} public void setX(int v){x=v;} public void setY(int v){y=v;} public void setFloorNumber(int v){floorNumber=v;}
}
