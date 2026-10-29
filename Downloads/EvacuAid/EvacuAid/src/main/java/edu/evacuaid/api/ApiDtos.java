package edu.evacuaid.api;

import jakarta.validation.constraints.*;
import java.util.List;

public final class ApiDtos {
 private ApiDtos(){}
 public record PlaceDto(String code,String label,String type,int x,int y,int floorNumber){}
 public record PassageDto(String code,String fromCode,String toCode,double cost,boolean blocked){}
 public record BuildingDto(Long id,String name,String description,List<PlaceDto> places,List<PassageDto> passages,String fireOriginCode,int fireSpreadSteps,List<String> hazardPassageCodes){}
 public record CreateBuilding(@NotBlank String name,@Size(max=500) String description){}
 public record PlaceInput(@NotBlank String code,@NotBlank String label,@NotBlank String type,int x,int y,@Min(1) int floorNumber){}
 public record PositionInput(@Min(35) @Max(965) int x,@Min(35) @Max(605) int y){}
 public record PassageInput(@NotBlank String code,@NotBlank String fromCode,@NotBlank String toCode,@Positive double cost){}
 public record RouteRequest(@NotBlank String startCode,String exitCode,boolean automaticExit){}
 public record RouteDto(List<String> nodes,double cost,String exitCode){}
 public record BlockRequest(boolean blocked){}
 public record FireScenarioRequest(@NotBlank String originCode,@NotBlank String startCode,String exitCode,boolean automaticExit,@Min(0) @Max(8) int spreadSteps){}
 public record FireScenarioResult(String originCode,int spreadSteps,List<String> affectedNodes,List<String> hazardPassageCodes,List<RouteDto> routes){}
 public record FloorDto(int floorNumber,boolean hasBlueprint){}
}
