package com.raja.practice.microservices.movieinfo.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.practice.microservices.movieinfo.model.Movie;

@RestController
@RequestMapping("/movies")
public class MovieInfoResource {
	@GetMapping("/{movieId}")
	public Movie getMovie(@PathVariable("movieId") String movieId) {
		return new Movie(movieId,"96");
	}
}
