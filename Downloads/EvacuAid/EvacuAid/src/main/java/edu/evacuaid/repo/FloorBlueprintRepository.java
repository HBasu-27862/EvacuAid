package edu.evacuaid.repo;
import edu.evacuaid.model.FloorBlueprint; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface FloorBlueprintRepository extends JpaRepository<FloorBlueprint,Long>{Optional<FloorBlueprint> findByBuilding_IdAndFloorNumber(long buildingId,int floorNumber);List<FloorBlueprint> findByBuilding_Id(long buildingId);void deleteByBuilding_Id(long buildingId);}
