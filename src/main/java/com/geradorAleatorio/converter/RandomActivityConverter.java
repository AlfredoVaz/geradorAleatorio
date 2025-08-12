package com.geradorAleatorio.converter;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.geradorAleatorio.model.RandomActivity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RandomActivityConverter {

    public RandomActivityDto toDto(RandomActivity entity) {
        if (entity == null) return null;
        return RandomActivityDto.builder()
                .activity(entity.getActivity())
                .availability(entity.getAvailability())
                .type(entity.getType())
                .participants(entity.getParticipants())
                .price(entity.getPrice())
                .accessibility(entity.getAccessibility())
                .duration(entity.getDuration())
                .kidFriendly(entity.isKidFriendly())
                .link(entity.getLink())
                .key(entity.getKey())
                .build();
    }

    public RandomActivity toEntity(RandomActivityDto dto) {
        if (dto == null) return null;
        return RandomActivity.builder()
                .activity(emptyToNull(dto.getActivity()))
                .availability(dto.getAvailability())
                .type(emptyToNull(dto.getType()))
                .participants(dto.getParticipants())
                .price(dto.getPrice())
                .accessibility(emptyToNull(dto.getAccessibility()))
                .duration(emptyToNull(dto.getDuration()))
                .kidFriendly(dto.isKidFriendly())
                .link(emptyToNull(dto.getLink()))
                .key(emptyToNull(dto.getKey()))
                .build();
    }

    private String emptyToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }
}


