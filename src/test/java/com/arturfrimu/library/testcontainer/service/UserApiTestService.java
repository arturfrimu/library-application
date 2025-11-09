package com.arturfrimu.library.testcontainer.service;

import com.arturfrimu.library.dto.response.UserResponse;
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
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static lombok.AccessLevel.PRIVATE;

@Component
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserApiTestService extends BaseApiTestService {

    public UserApiTestService(Environment env, RestTemplate restTemplate) {
        super(env, restTemplate);
    }

    public Page<UserResponse> findUsers(int page, int size) {
        var uri = UriComponentsBuilder.fromUriString(baseUrl() + "/users")
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri();

        var response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<PageDto<UserResponse>>() {}
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