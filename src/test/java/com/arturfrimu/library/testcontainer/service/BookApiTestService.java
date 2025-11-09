package com.arturfrimu.library.testcontainer.service;

import com.arturfrimu.library.dto.request.CreateBookRequest;
import com.arturfrimu.library.dto.response.BookResponse;
import com.arturfrimu.library.testcontainer.util.PageDto;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookApiTestService extends BaseApiTestService {

    public BookApiTestService(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    public BookResponse createBook(CreateBookRequest request) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForObject(
                baseUrl() + "/books",
                new HttpEntity<>(request, headers),
                BookResponse.class
        );
    }

    public Page<BookResponse> findBooks(int page, int size) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl() + "/books")
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<PageDto<BookResponse>>() {}
        ).getBody();

        if (response == null) {
            return Page.empty();
        }

        return new PageImpl<>(
                response.content(),
                PageRequest.of(page, size),
                response.totalElements()
        );
    }
}