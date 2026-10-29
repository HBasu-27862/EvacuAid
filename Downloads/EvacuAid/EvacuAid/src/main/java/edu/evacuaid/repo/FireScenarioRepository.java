package edu.evacuaid.repo;
import edu.evacuaid.model.FireScenario; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface FireScenarioRepository extends JpaRepository<FireScenario,Long>{List<FireScenario> findByBuildingIdAndActiveTrue(Long buildingId);Optional<FireScenario> findFirstByBuildingIdAndActiveTrueOrderByCreatedAtDesc(Long buildingId);}
