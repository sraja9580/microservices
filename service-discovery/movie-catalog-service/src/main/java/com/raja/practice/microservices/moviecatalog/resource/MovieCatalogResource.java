package com.raja.practice.microservices.moviecatalog.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.raja.practice.microservices.moviecatalog.model.CatalogItem;
import com.raja.practice.microservices.moviecatalog.model.Movie;
import com.raja.practice.microservices.moviecatalog.model.Rating;
import com.raja.practice.microservices.moviecatalog.model.UserRatings;

 


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplte;
	
	@GetMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
		
		//UserRatings userRatings =  restTemplte.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRatings.class);
		UserRatings userRatings =  restTemplte.getForObject("http://RATINGS-DATA-SERVICE/ratingsdata/users/"+userId, UserRatings.class);
		
		List<Rating> userRatingsLst =  userRatings.getUserRating();
		
		List<CatalogItem> catLogList = userRatingsLst.stream().map(
				rating-> {
					//Movie movie = restTemplte.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
					Movie movie = restTemplte.getForObject("http://MOVIE-INFO-SERVICE/movies/"+rating.getMovieId(), Movie.class);
					return new CatalogItem(movie.getName(),"Heart Warming",rating.getRating());
				}
		).collect(Collectors.toList());
		
		return catLogList;
	}
}
