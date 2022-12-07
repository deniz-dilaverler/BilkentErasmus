package com.caddy.erasxchange.services.application;

import com.caddy.erasxchange.DTOs.PostApplicationDTO;
import com.caddy.erasxchange.models.application.Application;
import com.caddy.erasxchange.repositories.application.ApplicationRepository;

public abstract class ApplicationService<App extends Application, Repo extends ApplicationRepository<?>> {

    Repo applicationRepository;

    public void addApplication(PostApplicationDTO postApplicationDTO) {
        //TODO: Add to database
    }
}
