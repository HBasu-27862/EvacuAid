package edu.evacuaid.repo;
import edu.evacuaid.model.SimulationEvent; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface SimulationEventRepository extends JpaRepository<SimulationEvent,Long> { List<SimulationEvent> findTop100ByOrderByCreatedAtDesc(); }
