package com.caddy.erasxchange.services;

import com.caddy.erasxchange.models.forms.CourseTransferForm;
import com.caddy.erasxchange.models.forms.Form;
import com.caddy.erasxchange.models.forms.FormApprovalStatus;
import com.caddy.erasxchange.models.forms.PreApprovalForm;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.form.CourseTransferFormRepository;
import com.caddy.erasxchange.repositories.form.FormRepository;
import com.caddy.erasxchange.repositories.form.PreApprovalFormRepository;
import com.caddy.erasxchange.repositories.user.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FormService {
    private PreApprovalFormRepository preApprovalFormRepository;
    private CourseTransferFormRepository courseTransferFormRepository;
    private UserRepository<User> userRepository;

    public FormService(PreApprovalFormRepository preApprovalFormRepository, CourseTransferFormRepository courseTransferFormRepository, UserRepository<User> userRepository) {
        this.preApprovalFormRepository = preApprovalFormRepository;
        this.courseTransferFormRepository = courseTransferFormRepository;
        this.userRepository = userRepository;
    }

    public List<PreApprovalForm> getPreApprovalFormsBySender(String username) {
        Optional<User> optional =  userRepository.findByBilkentId(Integer.parseInt(username));
        if(optional.isEmpty()) throw new EntityNotFoundException("User with bilkent id :" + username + " doesn't exist");
        User user = optional.get();

        return preApprovalFormRepository.findBySender(user);
    }

    public List<CourseTransferForm> getCourseTransferFormsBySender(String username) {
        Optional<User> optional =  userRepository.findByBilkentId(Integer.parseInt(username));
        if(optional.isEmpty()) throw new EntityNotFoundException("User with bilkent id :" + username + " doesn't exist");
        User user = optional.get();
        return courseTransferFormRepository.findBySender(user);
    }

    public void generatePreAppPdf(PreApprovalForm form) throws Exception {
        Document document = new Document(PageSize.A4.rotate(),20,20,50,50);
//        Path path = Paths.get(ClassLoader.getSystemResource("bilkent-logo.png").toURI());
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
        courseTable.addCell("1");
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
        personalInfoTable.addCell(form.getStudent().getErasmusApplication().getSemester().name());
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

        document.close();
    }
    public void decideOnForm(Form form, boolean decision) {
        if (decision)
            form.setStatus(FormApprovalStatus.APPROVED);
        else
            form.setStatus(FormApprovalStatus.REJECTED);
    }
}
