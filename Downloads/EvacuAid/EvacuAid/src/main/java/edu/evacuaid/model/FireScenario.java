package edu.evacuaid.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
public class FireScenario {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long buildingId;
 @Column(nullable=false) private String originCode;
 @Column(nullable=false) private int spreadSteps;
 @Column(nullable=false,length=4000) private String affectedNodeCodes="";
 @Column(nullable=false,length=4000) private String hazardPassageCodes="";
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected FireScenario(){}
 public FireScenario(Long buildingId,String originCode,int spreadSteps,String nodes,String passages){this.buildingId=buildingId;this.originCode=originCode;this.spreadSteps=spreadSteps;this.affectedNodeCodes=nodes;this.hazardPassageCodes=passages;}
 public Long getId(){return id;} public Long getBuildingId(){return buildingId;} public String getOriginCode(){return originCode;} public int getSpreadSteps(){return spreadSteps;} public String getAffectedNodeCodes(){return affectedNodeCodes;} public String getHazardPassageCodes(){return hazardPassageCodes;} public boolean isActive(){return active;} public Instant getCreatedAt(){return createdAt;} public void deactivate(){active=false;}
}
