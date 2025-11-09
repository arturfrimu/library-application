package com.arturfrimu.library.testcontainer.data;

import com.arturfrimu.library.dto.request.CreateBookRequest;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class DtoCreator {

    public CreateBookRequest buildCreateBookRequest(UUID authorId) {
        return new CreateBookRequest(
                "Test Book",
                authorId,
                "9780123456789",
                2024
        );
    }

    public CreateBookRequest buildCreateBookRequest() {
        var authorId = UUID.fromString("22222222-2222-2222-2222-222222222222");
        return buildCreateBookRequest(authorId);
    }
}