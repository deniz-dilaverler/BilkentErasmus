package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.ApplicationDto;
import com.caddy.erasxchange.DTOs.ApplicationPostDto;
import com.caddy.erasxchange.models.application.Application;
import lombok.AllArgsConstructor;

import java.util.List;


public interface ApplicationServiceStrategy<App extends Application > {

    public void addApplication(ApplicationPostDto applicationPostDto);
    public  ApplicationDto getById(Long id);
    public void updateApp(ApplicationDto applicationDto);
    public List<ApplicationDto> getAll();
}
