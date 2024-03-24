package dev.matt.movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//business logic
@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(String reviewBody, String imdbID){
        Review review = reviewRepository.insert(new Review(reviewBody));
        mongoTemplate.update(Movie.class).matching(Criteria.where("imdbId").is(imdbID))
                .apply(new Update().push("reviewIds").value(review))
                .first();
        return review;
    }

}
