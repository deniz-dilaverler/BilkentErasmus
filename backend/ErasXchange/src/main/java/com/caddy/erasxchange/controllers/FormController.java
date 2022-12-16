package com.caddy.erasxchange.controllers;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.forms.PreApprovalForm;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.services.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FormController {
    private FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @GetMapping("api/pdf")
    public ResponseEntity<String> pdf() throws Exception {
        PreApprovalForm form = new PreApprovalForm();
        form.setSender(new Student());
        form.getSender().setFirstName("a");
        form.getSender().setLastName("b");
        form.getSender().setDepartment(Department.CS);
        form.getSender().setBilkentId(22002200);
        ((Student)form.getSender()).setErasmusApplication(new ErasmusApplication());
        ((Student)form.getSender()).getErasmusApplication().setPlacedSchool(new ErasmusUniversity());
        ((Student)form.getSender()).getErasmusApplication().getPlacedSchool().setName("A uni");
        formService.generatePreAppPdf(form);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    public ResponseEntity<String> decideForm(@RequestParam boolean decision)
}
