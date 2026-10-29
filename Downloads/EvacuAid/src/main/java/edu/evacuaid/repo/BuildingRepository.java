package edu.evacuaid.repo;
import edu.evacuaid.model.Building; import org.springframework.data.jpa.repository.JpaRepository;
public interface BuildingRepository extends JpaRepository<Building,Long> { boolean existsByNameIgnoreCase(String name); }
