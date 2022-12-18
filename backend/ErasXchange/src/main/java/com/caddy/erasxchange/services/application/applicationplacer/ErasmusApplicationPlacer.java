package com.caddy.erasxchange.services.application.applicationplacer;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;
import com.caddy.erasxchange.services.EventService;
import com.caddy.erasxchange.services.university.ErasmusUniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ErasmusApplicationPlacer implements ApplicationPlacementStrategy<ErasmusApplication> {
    private final ErasmusApplicationRepository repository;
    private final ErasmusUniversityService uniService;
    private List<ErasmusApplication> applications;
    private Map<University, Integer> quotas;
    private final EventService eventService;

    @Autowired
    public ErasmusApplicationPlacer(ErasmusApplicationRepository repository, ErasmusUniversityService uniService, EventService eventService) {
        this.repository = repository;
        this.uniService = uniService;
        this.eventService = eventService;
        quotas = new HashMap<University,Integer>();
    }



    @Override
    public void startPlacements(List<ErasmusApplication> applicationsToPlace, Department department) {
        applications = applicationsToPlace;
        initializeQuotas(department);
        sortApplications();

        for(ErasmusApplication application : applications) {

            if(application.getStatus() == AppStatus.PLACED) continue;
            ErasmusUniversity placedUni = null;

            if (application.getChoice1()!= null && quotas.get(application.getChoice1()) > 0) {
                placedUni = application.getChoice1();
            } else if (application.getChoice2()!= null && quotas.get(application.getChoice2()) > 0) {
                placedUni = application.getChoice2();
            } else if (application.getChoice3()!= null && quotas.get(application.getChoice3()) > 0) {
                placedUni = application.getChoice3();
            }else if (application.getChoice4()!= null && quotas.get(application.getChoice4()) > 0) {
                placedUni = application.getChoice4();
            }else if (application.getChoice5()!= null && quotas.get(application.getChoice5()) > 0) {
                placedUni = application.getChoice5();
            }

            if(placedUni == null)
                application.setStatus(AppStatus.WAITING_BIN);
            else{
                application.setPlacedSchool(placedUni);
                application.setStatus(AppStatus.PLACED);
                //Decrement quota
                quotas.put(placedUni, quotas.get(placedUni) - 1);
            }

            eventService.sendEvent(application.getStudent(), "Update on your Erasmus application", "Your application is now " + application.getStatus().name().toLowerCase(Locale.US));

        }

        repository.saveAllAndFlush(applications);
        //TODO: notify repsonsible coordinator
        //TODO: notify students
        //TODO: update application state

        //reset placer
        applications = null;
        quotas.clear();
    }


    private void sortApplications() {
        Collections.sort(applications);
        Collections.reverse(applications);
    }

    private void initializeQuotas(Department department) {
        List<ErasmusUniversity> universities = uniService.findAll();

        //setting the quotas of all universities in the system for the relevant department to map
        for(ErasmusUniversity university : universities) {
            if(university.getPrograms() == null) continue;

            Set<Program> programs = university.getPrograms();
            for(Program program : programs) {
                if(program.getDepartment() == department)
                    quotas.put(university, program.getQuota());
            }
        }
    }


    @Override
    public void startPlacements(List<ErasmusApplication> applications ) {
        throw new UnsupportedOperationException("ErasmusApplicationPlacer doesn't implement this method");
    }
}
