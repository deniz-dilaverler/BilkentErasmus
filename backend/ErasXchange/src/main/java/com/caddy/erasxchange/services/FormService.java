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
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        return preApprovalFormRepository.findBySender(userRepository.findByBilkentId(Integer.parseInt(username)));
    }

    public List<CourseTransferForm> getCourseTransferFormsBySender(String username) {
        return courseTransferFormRepository.findBySender(userRepository.findByBilkentId(Integer.parseInt(username)));
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
        personalInfoTable.addCell("Name");
        personalInfoTable.addCell(form.getSender().getFirstName());
        personalInfoTable.addCell("ID Number");
        personalInfoTable.addCell(form.getSender().getBilkentId().toString());
        personalInfoTable.addCell("Surname");
        personalInfoTable.addCell(form.getSender().getLastName());
        personalInfoTable.addCell("Department");
        personalInfoTable.addCell(form.getSender().getDepartment().name());
        document.add(personalInfoTable);

        PdfPTable hostUniTable = new PdfPTable(2);
        hostUniTable.addCell("Name of the host institution");
        hostUniTable.addCell(((Student)form.getSender()).getErasmusApplication().getPlacedSchool().getName());
        hostUniTable.setSpacingBefore(10);
        hostUniTable.setSpacingAfter(10);
        document.add(hostUniTable);

        document.close();
    }

    public void decideOnForm(Form form, boolean decision) {
        if (decision)
            form.setStatus(FormApprovalStatus.APPROVED);
        else
            form.setStatus(FormApprovalStatus.REJECTED);
    }
}
