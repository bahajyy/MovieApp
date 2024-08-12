package dev.bahajyy.movies;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    public MovieServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllMovies_shouldReturnMovieList() {
        Movie movie = new Movie(new ObjectId(), "tt1234567", "Sample Movie", "2024-08-13", "trailerLink", "poster",
                Arrays.asList("backdrop1", "backdrop2"), Arrays.asList("Action", "Drama"));

        when(movieRepository.findAll()).thenReturn(Collections.singletonList(movie));

        List<Movie> result = movieService.findAllMovies();

        assertEquals(1, result.size());
        assertEquals("Sample Movie", result.get(0).getTitle());
    }

    @Test
    public void findMovieByImdbId_shouldReturnMovie() {
        String imdbId = "tt1234567";
        Movie movie = new Movie(new ObjectId(), imdbId, "Sample Movie", "2024-08-13", "trailerLink", "poster",
                Arrays.asList("backdrop1", "backdrop2"), Arrays.asList("Action", "Drama"));

        when(movieRepository.findMovieByImdbId(imdbId)).thenReturn(Optional.of(movie));

        Optional<Movie> result = movieService.findMovieByImdbId(imdbId);

        assertEquals(true, result.isPresent());
        assertEquals(imdbId, result.get().getImdbId());
    }
}
