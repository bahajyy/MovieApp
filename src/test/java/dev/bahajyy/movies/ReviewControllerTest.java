package dev.bahajyy.movies;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Test
    public void createReview_shouldReturnOkStatus() throws Exception {
        Review review = new Review(new org.bson.types.ObjectId(), "Great movie!", LocalDateTime.now(), LocalDateTime.now());

        when(reviewService.createReview(anyString(), anyString())).thenReturn(review);

        Map<String, String> payload = new HashMap<>();
        payload.put("reviewBody", "Great movie!");
        payload.put("imdbId", "tt1234567");

        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"reviewBody\": \"Great movie!\", \"imdbId\": \"tt1234567\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"body\":\"Great movie!\"}"));
    }
}
