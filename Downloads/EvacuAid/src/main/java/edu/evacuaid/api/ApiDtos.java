package edu.evacuaid.api;

import jakarta.validation.constraints.*;
import java.util.List;

public final class ApiDtos {
 private ApiDtos(){}
 public record PlaceDto(String code,String label,String type,int x,int y){}
 public record PassageDto(String code,String fromCode,String toCode,double cost,boolean blocked){}
 public record BuildingDto(Long id,String name,String description,List<PlaceDto> places,List<PassageDto> passages){}
 public record CreateBuilding(@NotBlank String name,@Size(max=500) String description){}
 public record PlaceInput(@NotBlank String code,@NotBlank String label,@NotBlank String type,int x,int y){}
 public record PassageInput(@NotBlank String code,@NotBlank String fromCode,@NotBlank String toCode,@Positive double cost){}
 public record RouteRequest(@NotBlank String startCode,String exitCode,boolean automaticExit){}
 public record RouteDto(List<String> nodes,double cost,String exitCode){}
 public record BlockRequest(boolean blocked){}
}
