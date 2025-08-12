package com.geradorAleatorio.service;

import com.geradorAleatorio.dto.RandomActivityDto;
import java.util.List;

public interface RandomActivityService {

    public RandomActivityDto getRandomActivity();
    public List<RandomActivityDto> listActivities();
    public RandomActivityDto findActivityByKey(String key);

}


