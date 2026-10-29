package edu.evacuaid.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity public class SimulationEvent {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long buildingId; @Column(nullable=false) private String action;
 private String fromCode; private String exitCode; private Double cost; private String routeCodes;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 protected SimulationEvent(){}
 public SimulationEvent(Long buildingId,String action,String fromCode,String exitCode,Double cost,String routeCodes){this.buildingId=buildingId;this.action=action;this.fromCode=fromCode;this.exitCode=exitCode;this.cost=cost;this.routeCodes=routeCodes;}
 public Long getId(){return id;} public Long getBuildingId(){return buildingId;} public String getAction(){return action;} public String getFromCode(){return fromCode;} public String getExitCode(){return exitCode;} public Double getCost(){return cost;} public String getRouteCodes(){return routeCodes;} public Instant getCreatedAt(){return createdAt;}
}
