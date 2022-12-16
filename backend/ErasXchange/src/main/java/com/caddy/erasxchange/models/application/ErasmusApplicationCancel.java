package com.caddy.erasxchange.models.application;

import com.caddy.erasxchange.models.BaseEntity;
import com.caddy.erasxchange.models.university.Program;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
public class ErasmusApplicationCancel extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "canceled_application_id")
    private ErasmusApplication canceledApplication;
    @OneToOne
    @JoinColumn(name = "proposed_application_id")
    private ErasmusApplication proposedApplication;


    @OneToOne
    @JoinColumn(name = "proposed_program_id")
    private Program proposedProgram;


}
