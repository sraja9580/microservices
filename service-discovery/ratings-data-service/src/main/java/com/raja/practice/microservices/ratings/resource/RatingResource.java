package com.raja.practice.microservices.ratings.resource;

import java.util.Arrays;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.practice.microservices.ratings.model.Rating;
import com.raja.practice.microservices.ratings.model.UserRatings;

@RestController
@RequestMapping("/ratingsdata")
public class RatingResource {

	@GetMapping("/{movieId}")
	public Rating getRating(@PathVariable String movieId) {
		return new Rating(movieId, 8);
	}

	@GetMapping("/users/{userId}")
	public UserRatings getUserRatings() {
		return new UserRatings(Arrays.asList(new Rating("zk24", 8), new Rating("erg3", 7)));
	}

}
