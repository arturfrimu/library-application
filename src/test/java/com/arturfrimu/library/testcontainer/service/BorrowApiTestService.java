package com.arturfrimu.library.testcontainer.service;

import com.arturfrimu.library.dto.request.BorrowBookRequest;
import com.arturfrimu.library.dto.request.ReturnBookRequest;
import com.arturfrimu.library.dto.response.BorrowedBookResponse;
import lombok.experimental.FieldDefaults;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BorrowApiTestService extends BaseApiTestService {

    public BorrowApiTestService(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    public UUID borrowBook(BorrowBookRequest request) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForObject(
                baseUrl() + "/borrows",
                new HttpEntity<>(request, headers),
                UUID.class
        );
    }

    public UUID returnBook(ReturnBookRequest request) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.postForObject(
                baseUrl() + "/borrows/return",
                new HttpEntity<>(request, headers),
                UUID.class
        );
    }

    public List<BorrowedBookResponse> getBorrowedBooks(UUID userId) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl() + "/borrows/users/" + userId)
                .build()
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<BorrowedBookResponse>>() {}
        ).getBody();

        return response != null ? response : List.of();
    }
}

