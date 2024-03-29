package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.application.ReviewService;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        reviewService = new ReviewService(reviewRepository);
    }
    @Test
    public void addReview() {
        Review review = Review.builder()
                .name("Joker")
                .score(3)
                .description("good")
                .build();

        reviewService.addReview(1004L, "JUNG",3,"good taste");

        verify(reviewRepository).save(any());

    }
}