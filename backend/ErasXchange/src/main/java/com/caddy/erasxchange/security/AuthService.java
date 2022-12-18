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
    private final SecurityUserRepository securityUserRepository;
    private final JavaMailSender mailSender;

    public AuthService(UserRepository<User> userRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, SecurityUserRepository securityUserRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.securityUserRepository = securityUserRepository;
        this.mailSender = mailSender;
    }

    public Optional<String> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        User user = userRepository.findByBilkentId(Integer.parseInt(loginDto.getUsername())).get();
        List<Role> roles  = new ArrayList<>();
        roles.add(user.getRole());
        Optional<String> token = Optional.of(jwtProvider.createToken(user.getBilkentId().toString(), roles));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return token;
    }

    public void register(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        //TODO
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//
//        String randomCode = RandomString.make(64);
//        user.setVerificationCode(randomCode);
//        user.setEnabled(false);
//
//        repo.save(user);
//
//        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        //TODO
//        String toAddress = user.getEmail();
//        String fromAddress = "aliemirguzey@gmail.com";
//        String senderName = "ErasXchange";
//        String subject = "Please verify your registration";
//        String content = "Dear [[name]],<br>"
//                + "Please click the link below to verify your registration:<br>"
//                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
//                + "Thank you,<br>"
//                + "Your company name.";
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom(fromAddress, senderName);
//        helper.setTo(toAddress);
//        helper.setSubject(subject);
//
//        content = content.replace("[[name]]", user.getFullName());
//        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();
//
//        content = content.replace("[[URL]]", verifyURL);
//
//        helper.setText(content, true);
//
//        mailSender.send(message);

    }

    public boolean verify(String verificationCode) {
        //TODO
//        User user = repo.findByVerificationCode(verificationCode);
//
//        if (user == null || user.isEnabled()) {
//            return false;
//        } else {
//            user.setVerificationCode(null);
//            user.setEnabled(true);
//            repo.save(user);
//
//            return true;
//        }
        return false;
    }
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
