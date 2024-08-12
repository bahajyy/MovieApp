package dev.bahajyy.movies;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void getMovies_shouldReturnOkStatus() throws Exception {
        Movie movie = new Movie(new ObjectId(), "tt1234567", "Sample Movie", "2024-08-13",
                "trailerLink", "poster",
                Arrays.asList("backdrop1", "backdrop2"), Arrays.asList("Action", "Drama"));

        when(movieService.findAllMovies()).thenReturn(Collections.singletonList(movie));

        MvcResult result = mockMvc.perform(get("/api/v1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"imdbId\":\"tt1234567\",\"title\":\"Sample Movie\"," +
                        "\"releaseDate\":\"2024-08-13\",\"trailerLink\":\"trailerLink\",\"poster\":\"poster\",\"backdrops\":[\"backdrop1\",\"backdrop2\"],\"genres\":[\"Action\",\"Drama\"]}]"))
                .andReturn();
    }

    @Test
    public void getSingleMovie_shouldReturnOkStatus() throws Exception {
        String imdbId = "tt1234567";
        Movie movie = new Movie(new ObjectId(), imdbId, "Sample Movie", "2024-08-13", "trailerLink", "poster",
                Arrays.asList("backdrop1", "backdrop2"), Arrays.asList("Action", "Drama"));

        when(movieService.findMovieByImdbId(imdbId)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/api/v1/movies/" + imdbId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"imdbId\":\"tt1234567\",\"title\":\"Sample Movie\",\"releaseDate\":\"2024-08-13\",\"trailerLink\":\"trailerLink\",\"poster\":\"poster\",\"backdrops\":[\"backdrop1\",\"backdrop2\"],\"genres\":[\"Action\",\"Drama\"]}"));
    }
}
