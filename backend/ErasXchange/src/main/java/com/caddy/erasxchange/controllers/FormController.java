package com.caddy.erasxchange.controllers;

import com.caddy.erasxchange.DTOs.FormItemSendDto;
import com.caddy.erasxchange.DTOs.PreApprovePostDto;
import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.forms.CourseTransferForm;
import com.caddy.erasxchange.models.forms.PreApprovalForm;
import com.caddy.erasxchange.models.university.ErasmusUniversity;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.form.PreApprovalFormRepository;
import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FormController {
    private FormService formService;
    private final StudentRepository studentRepository;
    private final PreApprovalFormRepository preApprovalFormRepository;

    public FormController(FormService formService,
                          StudentRepository studentRepository,
                          PreApprovalFormRepository preApprovalFormRepository) {
        this.formService = formService;
        this.studentRepository = studentRepository;
        this.preApprovalFormRepository = preApprovalFormRepository;
    }

    @GetMapping("api/pdf")
    public ResponseEntity<String> pdf(@RequestParam Integer bilkentId) throws Exception {
//        PreApprovalForm form = new PreApprovalForm();
//        form.setSender(new Student());
//        form.getSender().setFirstName("a");
//        form.getSender().setLastName("b");
//        form.getSender().setDepartment(Department.CS);
//        form.getSender().setBilkentId(22002200);
//        ((Student)form.getSender()).setErasmusApplication(new ErasmusApplication());
//        ((Student)form.getSender()).getErasmusApplication().setPlacedSchool(new ErasmusUniversity());
//        ((Student)form.getSender()).getErasmusApplication().getPlacedSchool().setName("A uni");
//        CourseTransferForm form1 = new CourseTransferForm();
//        form1.setStudent(new Student());
//        form1.getStudent().setFirstName("a");
//        form1.getStudent().setLastName("b");
//        form1.getStudent().setDepartment(Department.CS);
//        form1.getStudent().setBilkentId(22002200);
//        ((Student)form1.getStudent()).setErasmusApplication(new ErasmusApplication());
//        ((Student)form1.getStudent()).getErasmusApplication().setPlacedSchool(new ErasmusUniversity());
//        ((Student)form1.getStudent()).getErasmusApplication().getPlacedSchool().setName("A uni");
//        ((Student)form1.getStudent()).getErasmusApplication().setSemester1(Semester.SPRING);
//        ((Student)form1.getStudent()).getErasmusApplication().setSemester2(Semester.SPRING);
//        ((Student)form1.getStudent()).getErasmusApplication().setSemester3(Semester.SPRING);
//        ((Student)form1.getStudent()).getErasmusApplication().setSemester4(Semester.SPRING);
//        ((Student)form1.getStudent()).getErasmusApplication().setSemester5(Semester.SPRING);
        formService.generatePreAppPdf(preApprovalFormRepository.findBySender(studentRepository.findByBilkentId(bilkentId).get()));
//        formService.generateTransferPdf(form1);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/api/generate-form/{username}")
    @ResponseStatus(HttpStatus.OK)
    public void generateForm(@PathVariable String username) {
        formService.generatePreAppForm(username);
    }

    @PostMapping("/api/send-form-item")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<FormItemSendDto> sendFormItem(@RequestBody FormItemSendDto dto) {
        Student student = studentRepository.findByBilkentId(Integer.parseInt(dto.getUsername())).get();
        formService.newFormItem(student, dto);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

//    public ResponseEntity<String> decideForm(@RequestParam boolean decision)
}
