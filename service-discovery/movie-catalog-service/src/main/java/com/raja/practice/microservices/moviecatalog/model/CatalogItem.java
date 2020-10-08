package com.raja.practice.microservices.moviecatalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter 
public class CatalogItem {
	private String name;
	private String description;
	private int rating;
}
