package edu.evacuaid.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"building_id","floor_number"}))
public class FloorBlueprint {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Building building;
 @Column(name="floor_number",nullable=false) private int floorNumber;
 @Column(nullable=false) private String contentType;
 @Lob @Column(nullable=false) private byte[] imageData;
 @Column(nullable=false) private Instant updatedAt=Instant.now();
 protected FloorBlueprint(){}
 public FloorBlueprint(Building building,int floorNumber,String contentType,byte[] imageData){this.building=building;this.floorNumber=floorNumber;this.contentType=contentType;this.imageData=imageData;this.updatedAt=Instant.now();}
 public Long getId(){return id;} public Building getBuilding(){return building;} public int getFloorNumber(){return floorNumber;} public String getContentType(){return contentType;} public byte[] getImageData(){return imageData;} public Instant getUpdatedAt(){return updatedAt;}
 public void replace(String contentType,byte[] imageData){this.contentType=contentType;this.imageData=imageData;this.updatedAt=Instant.now();}
}
