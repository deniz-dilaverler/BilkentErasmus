package com.caddy.erasxchange.services.application.applicationplacer;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.university.Program;
import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;
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

    @Autowired
    public ErasmusApplicationPlacer(ErasmusApplicationRepository repository, ErasmusUniversityService uniService) {
        this.repository = repository;
        this.uniService = uniService;
        quotas = new HashMap<University,Integer>();
    }



    @Override
    public void startPlacements(List<ErasmusApplication> applicationsToPlace, Department department) {
        applications = applicationsToPlace;
        initializeQuotas(department);
        sortApplications();

        for(ErasmusApplication application : applications) {
            ErasmusUniversity choice = null;
            if(application.getStatus() == AppStatus.PLACED) continue;

            choice = application.getChoice1();
            if(choice == null ) {
                application.setStatus(AppStatus.WAITING_BIN);
                continue;
            }
            if(quotas.get(application.getChoice1()) > 0) {
                application.setStatus(AppStatus.PLACED);
                application.setPlacedSchool(application.getChoice1());
                //decreemt quota
                quotas.put(choice, quotas.get(choice) - 1);
                continue;
            }

            choice = application.getChoice2();
            if(choice == null ) {
                application.setStatus(AppStatus.WAITING_BIN);
                continue;
            }
            if(quotas.get(application.getChoice1()) > 0) {
                application.setStatus(AppStatus.PLACED);
                application.setPlacedSchool(application.getChoice1());
                //decreemt quota
                quotas.put(choice, quotas.get(choice) - 1);
                continue;
            }

            choice = application.getChoice3();
            if(choice == null ) {
                application.setStatus(AppStatus.WAITING_BIN);
                continue;
            }
            if(quotas.get(application.getChoice1()) > 0) {
                application.setStatus(AppStatus.PLACED);
                application.setPlacedSchool(application.getChoice1());
                //decreemt quota
                quotas.put(choice, quotas.get(choice) - 1);
                continue;
            }

            choice = application.getChoice4();
            if(choice == null ) {
                application.setStatus(AppStatus.WAITING_BIN);
                continue;
            }
            if(quotas.get(application.getChoice1()) > 0) {
                application.setStatus(AppStatus.PLACED);
                application.setPlacedSchool(application.getChoice1());
                //decreemt quota
                quotas.put(choice, quotas.get(choice) - 1);
                continue;
            }

            choice = application.getChoice5();
            if(choice == null ) {
                application.setStatus(AppStatus.WAITING_BIN);
                continue;
            }
            if(quotas.get(application.getChoice1()) > 0) {
                application.setStatus(AppStatus.PLACED);
                application.setPlacedSchool(application.getChoice1());
                //decreemt quota
                quotas.put(choice, quotas.get(choice) - 1);
                continue;
            }

            application.setStatus(AppStatus.WAITING_BIN);

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
