package com.ochoa.yeisson.movies_api.resolver;

import com.ochoa.yeisson.movies_api.dto.DirectorDTO;
import com.ochoa.yeisson.movies_api.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DirectorQueryResolver {
    private final DirectorService directorService;

    @QueryMapping
    public List<DirectorDTO> directors() {
        return directorService.getAllDirectors();
    }

    @QueryMapping
    public DirectorDTO directorById(Long id) {
        return directorService.getDirectorById(id);
    }
}
