package com.geradorAleatorio.service;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.geradorAleatorio.model.RandomActivity;
import com.geradorAleatorio.repository.RandomActivityJpaRepository;
import com.geradorAleatorio.repository.RandomActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RandomActivityServiceImpl implements RandomActivityService {

    private final RandomActivityRepository randomActivityRepository;

    private final RandomActivityJpaRepository jpaRepository;

    public RandomActivityServiceImpl(RandomActivityRepository randomActivityRepository,
                           RandomActivityJpaRepository jpaRepository) {
        this.randomActivityRepository = randomActivityRepository;
        this.jpaRepository = jpaRepository;
    }

    @Transactional
    public RandomActivityDto getRandomActivity() {
        RandomActivityDto activityResponse = randomActivityRepository.getRandomActivity();
        if (activityResponse == null) {
            return null;
        }

        return saveActivity(activityResponse);
    }

    private RandomActivityDto saveActivity(RandomActivityDto activityResponse) {
        String activity = emptyToNull(activityResponse.getActivity());
        String type = emptyToNull(activityResponse.getType());
        String accessibility = emptyToNull(activityResponse.getAccessibility());
        String duration = emptyToNull(activityResponse.getDuration());
        String link = emptyToNull(activityResponse.getLink());
        String key = emptyToNull(activityResponse.getKey());

        if (key != null && !jpaRepository.existsByKey(key)) {
            RandomActivity entity = RandomActivity.builder()
                    .activity(activity)
                    .availability(activityResponse.getAvailability())
                    .type(type)
                    .participants(activityResponse.getParticipants())
                    .price(activityResponse.getPrice())
                    .accessibility(accessibility)
                    .duration(duration)
                    .kidFriendly(activityResponse.isKidFriendly())
                    .link(link)
                    .key(key)
                    .build();
            jpaRepository.save(entity);
        }

        return activityResponse;
    }

    private String emptyToNull(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }
}


