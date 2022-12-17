package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.university.University;
import com.caddy.erasxchange.models.users.Iso;
import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.repositories.application.ErasmusApplicationRepository;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.services.GenericService;
import com.caddy.erasxchange.services.StorageService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class IsoService  {

    private StudentRepository studentRepository;
    private ErasmusApplicationRepository applicationRepository;
    private ErasmusUniversityRepository universityRepository;
    private StorageService storageService;

    public IsoService(StudentRepository studentRepository, ErasmusApplicationRepository applicationRepository, ErasmusUniversityRepository universityRepository, StorageService storageService) {
        this.studentRepository = studentRepository;
        this.applicationRepository = applicationRepository;
        this.universityRepository = universityRepository;
        this.storageService = storageService;
    }

    public List<Student> readExcel(String fileName) throws Exception {
        InputStream file = storageService.loadFileAsResource(fileName).getInputStream();
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row == null)
                break;
            if (row.getRowNum() == 0)
                continue;

            Student student = new Student();
            ErasmusApplication application = new ErasmusApplication();
            student.setRole(Role.ROLE_STUDENT);

            for (Cell cell : row) {
                if (cell == null)
                    break;

                Cell colName = sheet.getRow(0).getCell(cell.getColumnIndex());
                switch (colName.getStringCellValue()) {
                    case ("First Name") -> {
                        student.setFirstName(readCell(cell));
                    }
                    case ("Lastname") -> {
                        student.setLastName(readCell(cell));
                    }
                    case ("Student ID Number") -> {
                        student.setBilkentId((int) Double.parseDouble(readCell(cell)));
                    }
                    case ("Department") -> {
                        student.setDepartment(Department.valueOf(readCell(cell)));
                    }
                    case ("Email") -> student.setEmail(readCell(cell));
                    case ("Total Points") -> {
                        student.setExchangeScore(Double.parseDouble(readCell(cell)));
                    }
                    case ("Transcript Grade(4/4)") -> {
                        student.setGpa(Double.parseDouble(readCell(cell)));
                    }
                    case ("Duration Preferred") -> application.setSemester(Semester.valueOf(cell.getStringCellValue()));
                    case ("Preferred University #1") -> application.setChoice1(universityRepository.findByName(readCell(cell)));
                    case ("Preferred University #2") -> application.setChoice2(universityRepository.findByName(readCell(cell)));
                    case ("Preferred University #3") -> application.setChoice3(universityRepository.findByName(readCell(cell)));
                    case ("Preferred University #4") -> application.setChoice4(universityRepository.findByName(readCell(cell)));
                    case ("Preferred University #5") -> application.setChoice5(universityRepository.findByName(readCell(cell)));
                    default -> {
                    }
                    
                }

            }
            application.setStatus(AppStatus.PENDING);
            application.setStudent(student);
            student.setErasmusApplication(application);
//            student.setErasmusApplication(applicationRepository.findByStudent(student));
            studentRepository.save(student);

        }
        return studentRepository.findAll();
    }

    private String readCell(Cell cell) {
        switch (cell.getCellType()) {
            case NUMERIC -> {
                return String.valueOf(cell.getNumericCellValue());
            }
            case STRING -> {
                return cell.getStringCellValue();
            }
            default -> {
                return null;
            }
        }
    }
}
