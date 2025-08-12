package com.geradorAleatorio.service;

import com.geradorAleatorio.dto.RandomActivityDto;
import com.geradorAleatorio.model.RandomActivity;
import com.geradorAleatorio.repository.RandomActivityJpaRepository;
import com.geradorAleatorio.repository.RandomActivityRepository;
import com.geradorAleatorio.converter.RandomActivityConverter;
import com.geradorAleatorio.exception.ResourceNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RandomActivityServiceImpl implements RandomActivityService {

    private final RandomActivityRepository randomActivityRepository;
    private final RandomActivityJpaRepository jpaRepository;
    private final RandomActivityConverter converter;

    public RandomActivityServiceImpl(RandomActivityRepository randomActivityRepository,
                           RandomActivityJpaRepository jpaRepository,
                           RandomActivityConverter converter) {
        this.randomActivityRepository = randomActivityRepository;
        this.jpaRepository = jpaRepository;
        this.converter = converter;
    }

    @Transactional
    public RandomActivityDto getRandomActivity() {
        return saveActivity(randomActivityRepository.getRandomActivity());
    }

    private RandomActivityDto saveActivity(RandomActivityDto activityResponse) {

        RandomActivity entity = converter.toEntity(activityResponse);
        String activityKey = entity.getKey();
        if (StringUtils.hasText(activityKey) && !jpaRepository.existsByKey(activityKey)) {
            jpaRepository.save(entity);
        }

        return activityResponse;
    }
    
    public List<RandomActivityDto> listActivities() {
        return jpaRepository.findAll().stream()
                .map(converter::toDto)
                .toList();
    }

    public RandomActivityDto findActivityByKey(String key) {
        RandomActivity entity = jpaRepository.findByKey(key);
        if (entity == null) {
            throw new ResourceNotFoundException("Activity not found for key: " + key);
        }
        return converter.toDto(entity);
    }
}


