package edu.evacuaid.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Building {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String name;
    private String description;
    @OneToMany(mappedBy="building", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<Place> places = new ArrayList<>();
    @OneToMany(mappedBy="building", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<Passage> passages = new ArrayList<>();
    protected Building() {}
    public Building(String name, String description) { this.name=name; this.description=description; }
    public void addPlace(Place p) { p.setBuilding(this); places.add(p); }
    public void addPassage(Passage p) { p.setBuilding(this); passages.add(p); }
    public Long getId(){return id;} public String getName(){return name;} public String getDescription(){return description;}
    public List<Place> getPlaces(){return places;} public List<Passage> getPassages(){return passages;}
    public void setName(String name){this.name=name;} public void setDescription(String description){this.description=description;}
}
