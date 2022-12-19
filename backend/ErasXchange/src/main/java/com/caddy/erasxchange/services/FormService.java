package com.caddy.erasxchange.services;

import com.caddy.erasxchange.DTOs.FormItemSendDto;


import com.caddy.erasxchange.models.course.ApprovalStatus;
import com.caddy.erasxchange.models.course.EquivalenceItem;
import com.caddy.erasxchange.models.forms.*;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.course.BilkentCourseRepository;
import com.caddy.erasxchange.repositories.course.EquivalenceItemRepository;
import com.caddy.erasxchange.repositories.course.ExternalCourseRepository;
import com.caddy.erasxchange.repositories.form.CourseTransferFormRepository;
import com.caddy.erasxchange.repositories.form.PreApprovalFormRepository;
import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.repositories.user.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Instant;

@Service
public class FormService {
    private final PreApprovalFormRepository preApprovalFormRepository;
    private final EquivalenceItemRepository equivalenceItemRepository;
    private final StudentRepository studentRepository;
    private final ExternalCourseRepository externalCourseRepository;
    private final BilkentCourseRepository bilkentCourseRepository;

    public FormService(PreApprovalFormRepository preApprovalFormRepository,
                       EquivalenceItemRepository equivalenceItemRepository,
                       StudentRepository studentRepository,
                       ExternalCourseRepository externalCourseRepository,
                       BilkentCourseRepository bilkentCourseRepository) {
        this.preApprovalFormRepository = preApprovalFormRepository;
        this.equivalenceItemRepository = equivalenceItemRepository;
        this.studentRepository = studentRepository;
        this.externalCourseRepository = externalCourseRepository;
        this.bilkentCourseRepository = bilkentCourseRepository;
    }



    public void generatePreAppForm(String username) {
        Student student = studentRepository.findByBilkentId(Integer.parseInt(username)).get();
        PreApprovalForm form = preApprovalFormRepository.findBySender(student);


        form.getRows().forEach(item -> {

            EquivalenceItem equivalenceItem = equivalenceItemRepository.findByExternalCourseAndBilkentCourse(item.getExternalCourse(), item.getEquivalentCourse());

            if (equivalenceItem != null) {
                if (equivalenceItem.getApprovalStatus() == ApprovalStatus.ACCEPTED)
                    form.getRows().add(new FormItem(item.getExternalCourse(), item.getEquivalentCourse()));
                else if (equivalenceItem.getApprovalStatus() == ApprovalStatus.PENDING)
                    form.getRows().add(new FormItem(item.getExternalCourse(), item.getEquivalentCourse()));
                    //TODO: inform coordinator
                else
                    throw new EntityNotFoundException(item.getExternalCourse().getName() + " and " + item.getEquivalentCourse().getName() + " are not transferable");
            }
            else {
                equivalenceItem = new EquivalenceItem(item.getExternalCourse(), item.getEquivalentCourse(), ApprovalStatus.PENDING);
                equivalenceItemRepository.save(equivalenceItem);
                //TODO: inform coordinator
            }
        });

        form.setStatus(FormApprovalStatus.PENDING);
        form.setTimeSent(Instant.now());
        form.setReceiver(student.getErasmusApplication().getPlacedSchool().getCoordinator(form.getSender().getDepartment()));
        preApprovalFormRepository.save(form);

    }

    public void generatePreAppPdf(PreApprovalForm form) throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4.rotate(),20,20,50,50);
        PdfWriter.getInstance(document, new FileOutputStream(form.getSender().getFirstName() + "_"
                                                            + form.getSender().getLastName()
                                                            + "_PreApproval.pdf"));
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Chunk title = new Chunk("Course Exemption Pre-Approval Form for Outgoing Exchange Students", titleFont);
        title.setTextRise(10);
        document.add(title);

        PdfPTable personalInfoTable = new PdfPTable(4);
        personalInfoTable.setWidths(new int[] {3,10,5,10});
        personalInfoTable.addCell("Name");
        personalInfoTable.addCell(form.getSender().getFirstName());
        personalInfoTable.addCell("ID Number");
        personalInfoTable.addCell(form.getSender().getBilkentId().toString());
        personalInfoTable.addCell("Surname");
        personalInfoTable.addCell(form.getSender().getLastName());
        personalInfoTable.addCell("Department");
        personalInfoTable.addCell(form.getSender().getDepartment().name());
        personalInfoTable.setWidthPercentage(98);
        document.add(personalInfoTable);

        PdfPTable hostUniTable = new PdfPTable(2);
        hostUniTable.setWidths(new int[] {2,5});
        hostUniTable.addCell("Name of the host institution");
        hostUniTable.addCell(((Student)form.getSender()).getErasmusApplication().getPlacedSchool().getName());
        hostUniTable.setSpacingBefore(10);
        hostUniTable.setSpacingAfter(10);
        hostUniTable.setWidthPercentage(98);
        document.add(hostUniTable);

        PdfPTable courseTable = new PdfPTable(7);
        courseTable.setWidths(new int[] {1,4,15,4,15,4,15});
        PdfPCell cell = new PdfPCell(new Phrase("Host institution courses to be transferred upon approval"));
        cell.setColspan(4);
        courseTable.addCell(cell);
        cell = new PdfPCell(new Phrase("Course or requirement to be exempted if transferred course is completed with a passing grade"));
        cell.setColspan(3);
        courseTable.addCell(cell);
        courseTable.addCell("");
        courseTable.addCell("Course Code");
        courseTable.addCell("Course Name");
        courseTable.addCell("Credits");
        courseTable.addCell("Course Code and Name for a Required Course,\n" +
                "Elective Group Name for an Elective Requirement");
        courseTable.addCell("Credits");
        courseTable.addCell("Elective Requirement Exemptions\n" +
                "only: Course code(s) of directly\n" +
                "equivalent course(s), if any");
        for (int i = 0; i< form.getRows().size(); i++){
            courseTable.addCell(String.valueOf(i+1));
            courseTable.addCell(form.getRows().get(i).getExternalCourse().getCourseCode());
            courseTable.addCell(form.getRows().get(i).getExternalCourse().getName());
            courseTable.addCell(form.getRows().get(i).getExternalCourse().getEcts().toString());
            courseTable.addCell(form.getRows().get(i).getEquivalentCourse().getCourseCode()
                                + " " + form.getRows().get(i).getEquivalentCourse().getName());
            courseTable.addCell(form.getRows().get(i).getEquivalentCourse().getEcts().toString());
            courseTable.addCell("");
        }
        courseTable.setSpacingBefore(10);
        courseTable.setSpacingAfter(10);
        courseTable.setWidthPercentage(98);
        document.add(courseTable);

        PdfPTable coordinatorTable = new PdfPTable(4);
        coordinatorTable.addCell("Approved By");
        coordinatorTable.addCell("Name");
        coordinatorTable.addCell("Signature");
        coordinatorTable.addCell("Date");
        coordinatorTable.addCell("Exchange Coordinator");
        coordinatorTable.addCell("");
        coordinatorTable.addCell("");
        coordinatorTable.addCell("");
        coordinatorTable.setSpacingBefore(10);
        coordinatorTable.setSpacingAfter(10);
        coordinatorTable.setWidthPercentage(98);
        document.add(coordinatorTable);


        document.close();
    }

    public void generateTransferPdf(CourseTransferForm form) throws Exception {
        Document document = new Document(PageSize.A4.rotate(),20,20,50,50);
        PdfWriter.getInstance(document, new FileOutputStream(form.getStudent().getFirstName() + "_"
                + form.getStudent().getLastName()
                + "_Transfer.pdf"));
        document.open();

        BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Chunk title = new Chunk("Course Transfer and Exemption Form for Undergraduate Students", titleFont);
        title.setTextRise(10);
        document.add(title);

        PdfPTable personalInfoTable = new PdfPTable(6);
        personalInfoTable.setWidths(new int[] {3,10,3,10,5,10});
        personalInfoTable.addCell("Name");
        personalInfoTable.addCell(form.getStudent().getFirstName());
        personalInfoTable.addCell("ID Number");
        personalInfoTable.addCell(form.getStudent().getBilkentId().toString());
        personalInfoTable.addCell("Academic Year");
        personalInfoTable.addCell("");
        personalInfoTable.addCell("Surname");
        personalInfoTable.addCell(form.getStudent().getLastName());
        personalInfoTable.addCell("Department");
        personalInfoTable.addCell(form.getStudent().getDepartment().name());
        personalInfoTable.addCell("Semester");
        personalInfoTable.addCell(form.getStudent().getErasmusApplication().getPlacedSchool().getSemester().name());
        personalInfoTable.setWidthPercentage(98);
        document.add(personalInfoTable);

        PdfPTable detailTable = new PdfPTable(4);
        Phrase content = new Phrase("□ External Transfer Student", new Font(bf, 12));
        content.add(Chunk.NEWLINE);
        content.add("\u25A0 Outgoing Exchange Student");
        PdfPCell cell = new PdfPCell();
        cell.addElement(content);
        detailTable.addCell(cell);
        content = new Phrase("□ Internal Transfer Student" + Chunk.NEWLINE + "□ Transfer via DGS" + Chunk.NEWLINE + "□ Re-registered Student", new Font(bf, 12));
        detailTable.addCell("Name of the institution from which courses are transferred:" + Chunk.NEWLINE + form.getStudent().getErasmusApplication().getPlacedSchool().getName());
        detailTable.addCell(content);
        detailTable.addCell("Name of previous department:");
        detailTable.setWidthPercentage(98);
        document.add(detailTable);

        PdfPTable courseTable = new PdfPTable(8);
        courseTable.setWidths(new int[] {1,4,15,4,4,15,4,15});
        cell = new PdfPCell(new Phrase("Transferred Courses"));
        cell.setColspan(5);
        courseTable.addCell(cell);
        cell = new PdfPCell(new Phrase("Course or requirement to be exempted if transferred course is completed with a passing grade"));
        cell.setColspan(3);
        courseTable.addCell(cell);
        courseTable.addCell("");
        courseTable.addCell("Course Code");
        courseTable.addCell("Course Name");
        courseTable.addCell("Credits");
        courseTable.addCell("Grade");
        courseTable.addCell("Course Code and Name for a Required Course,\n" +
                "Elective Group Name for an Elective Requirement");
        courseTable.addCell("Credits");
        courseTable.addCell("Elective Requirement Exemptions\n" +
                "only: Course code(s) of directly\n" +
                "equivalent course(s), if any");
        courseTable.addCell("1");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.addCell("");
        courseTable.setSpacingBefore(10);
        courseTable.setSpacingAfter(10);
        courseTable.setWidthPercentage(98);
        document.add(courseTable);

        PdfPTable signTable = new PdfPTable(4);


        document.close();
    }
    public void decideOnForm(Form form, boolean decision) {
        if (decision)
            form.setStatus(FormApprovalStatus.APPROVED);
        else
            form.setStatus(FormApprovalStatus.REJECTED);
    }

    public void newFormItem(Student student, FormItemSendDto dto) {
        PreApprovalForm form;
        if(preApprovalFormRepository.findBySender(student) != null) {
            form = preApprovalFormRepository.findBySender(student);
        }
        else {
            form = new PreApprovalForm();
            form.setSender(student);
        }
        FormItem formItem = new FormItem();
        formItem.setExternalCourse(externalCourseRepository.findCourseByCourseCode(dto.getCourseCode()).get());
        formItem.setEquivalentCourse(bilkentCourseRepository.findCourseByCourseCode(dto.getEqivalentCourseCode()).get());
        form.getRows().add(formItem);
        preApprovalFormRepository.save(form);

    }
}
