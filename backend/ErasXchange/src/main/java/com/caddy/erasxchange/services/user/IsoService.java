package com.caddy.erasxchange.services.user;

import com.caddy.erasxchange.models.Department;
import com.caddy.erasxchange.models.Semester;
import com.caddy.erasxchange.models.application.AppStatus;
import com.caddy.erasxchange.models.application.ErasmusApplication;
import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.Student;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.university.ErasmusUniversityRepository;
import com.caddy.erasxchange.repositories.user.CoordinatorRepository;
import com.caddy.erasxchange.repositories.user.StudentRepository;
import com.caddy.erasxchange.repositories.user.UserRepository;
import com.caddy.erasxchange.security.AuthService;
import com.caddy.erasxchange.services.ApplicationStateService;
import com.caddy.erasxchange.services.EventService;
import com.caddy.erasxchange.services.PlacementStatus;
import com.caddy.erasxchange.services.StorageService;
import net.bytebuddy.utility.RandomString;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class IsoService  {

    private StudentRepository studentRepository;
    private ErasmusUniversityRepository universityRepository;
    private StorageService storageService;
    private  final ApplicationStateService applicationStateService;
    private final PasswordEncoder passwordEncoder;
    private final EventService eventService;
    private final CoordinatorRepository coordinatorRepository;
    private final UserRepository<User> userRepository;
    private final AuthService authService;

    public IsoService(StudentRepository studentRepository, ErasmusUniversityRepository universityRepository, StorageService storageService, ApplicationStateService applicationStateService, PasswordEncoder passwordEncoder, EventService eventService,
                      CoordinatorRepository coordinatorRepository,
                      UserRepository<User> userRepository, AuthService authService) {
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
        this.storageService = storageService;
        this.applicationStateService = applicationStateService;
        this.passwordEncoder = passwordEncoder;
        this.eventService = eventService;
        this.coordinatorRepository = coordinatorRepository;
        this.userRepository = userRepository;
        this.authService = authService;
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
            student.setPassword(passwordEncoder.encode(RandomString.make(32)));

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
                    case ("Duration Preferred") -> {
                        application.setSemester1(Semester.valueOf(cell.getStringCellValue()));
                        application.setSemester2(Semester.valueOf(cell.getStringCellValue()));
                        application.setSemester3(Semester.valueOf(cell.getStringCellValue()));
                        application.setSemester4(Semester.valueOf(cell.getStringCellValue()));
                        application.setSemester5(Semester.valueOf(cell.getStringCellValue()));
                    }
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
            if (student.getLastName().equals("Güzey")) {
                student.setEmail("aliemirguzey@gmail.com");
                authService.sendRegisterMail(student);
            }

            eventService.sendEvent(student, "Update on your Erasmus application", "Your application is now pending");
//            student.setErasmusApplication(applicationRepository.findByStudent(student));
            studentRepository.save(student);

        }

        applicationStateService.setErasmusAppState(Department.CS,PlacementStatus.APPS_CREATED);
        for (User user : userRepository.findByDepartmentAndRole(Department.CS, Role.ROLE_COORDINATOR)){
            eventService.sendEvent(user, "Erasmus Applications", "Your department's application list is uploaded by ISO");
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
