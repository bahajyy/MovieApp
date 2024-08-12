package dev.bahajyy.movies;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private ReviewService reviewService;

    public ReviewServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createReview_shouldCreateAndReturnReview() {
        String reviewBody = "Great movie!";
        String imdbId = "tt1234567";
        Review review = new Review(new ObjectId(), reviewBody, LocalDateTime.now(), LocalDateTime.now());

        when(reviewRepository.insert(review)).thenReturn(review);

        Review result = reviewService.createReview(reviewBody, imdbId);

        assertNotNull(result);
        verify(mongoTemplate).update(Movie.class);
    }
}
