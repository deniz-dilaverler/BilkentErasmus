package com.caddy.erasxchange.security;

import com.caddy.erasxchange.DTOs.LoginDto;
import com.caddy.erasxchange.models.users.Role;
import com.caddy.erasxchange.models.users.SecurityUser;
import com.caddy.erasxchange.models.users.User;
import com.caddy.erasxchange.repositories.SecurityUserRepository;
import com.caddy.erasxchange.repositories.user.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository<User> userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JavaMailSender mailSender;

    public AuthService(UserRepository<User> userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.mailSender = mailSender;
    }

    public Optional<String> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityUser user = securityUserRepository.findByUsername(loginDto.getUsername());
        List<Role> roles  = new ArrayList<>();
        roles.add(user.getRole());
        Optional<String> token = Optional.of(jwtProvider.createToken(user.getUsername(), roles));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    public void resetPassword(String code, String pwd) {
        User user = userRepository.findByVerificationCode(code);
        if (!user.getVerificationCode().equals("registered"))
            user.setPassword(passwordEncoder.encode(pwd));
        else
            throw new IllegalArgumentException("User already registered.");
        user.setVerificationCode("registered");
        userRepository.save(user);
    }

    public void sendRegisterMail(User user)
            throws MessagingException, UnsupportedEncodingException {

        user.setVerificationCode(RandomString.make(64));

        String toAddress = user.getEmail();
        String fromAddress = "emir.guzey@ug.bilkent.edu.tr";
        String senderName = "ErasXchange";
        String subject = "Please reset your password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">REGISTER</a></h3>"
                + "Thank you,<br>"
                + "ErasXchange.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL =  "http://localhost:3000/register?code=" + user.getVerificationCode();
        userRepository.save(user);

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

}
