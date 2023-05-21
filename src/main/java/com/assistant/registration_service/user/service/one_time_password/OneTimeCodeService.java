package com.assistant.registration_service.user.service.one_time_password;

import com.assistant.registration_service.auth.exceptions.EntityNotFoundException;
import com.assistant.registration_service.user.model_data.enums.EmailMessage;
import com.assistant.registration_service.user.model_data.enums.SmsHtmlMessage;
import com.assistant.registration_service.user.model_data.model.User;
import com.assistant.registration_service.user.repository.EntityRepository;
import com.assistant.registration_service.user.repository.UserEntityRepository;
import com.assistant.registration_service.user.service.sent_to_email.SmsSendTwilio;
import com.assistant.registration_service.user.service.user.UserService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Receive logs program
 * @author Oleksandra Morunova
 *
 */
@Service
public class OneTimeCodeService extends AbstractOneTimeCodeService<User, String> {
    private final SmsSendTwilio sendService;

    private final UserService userService;

    private final JavaMailSender javaMailSender;

    public OneTimeCodeService(EntityRepository<User, String> repository, UserEntityRepository userEntityRepository, SmsSendTwilio sendService, UserService userService, JavaMailSender javaMailSender) {
        super(repository);
        this.userEntityRepository = userEntityRepository;
        this.sendService = sendService;
        this.userService = userService;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public User sentToPhoneNumberAndDataBase(User entity){
        String code = createCode();
        sendService.sendSms(entity.getPhone(), code);
        Optional<User> u = Optional.ofNullable(userService.findUserByPhone(entity.getPhone()));
        if(u.isPresent()){
            User new_u = u.get();
            new_u.setCode(code);
            new_u.setCodeData(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return repository.save(new_u);
        } else throw new EntityNotFoundException(User.class, "id", entity.getEmail());
    }

    @Override
    public void deleteCode(String code){
        Optional<User> u = Optional.ofNullable(userEntityRepository.findUserByCode(code));
        if(u.isPresent() && validateCode(code)){
            User new_u = u.get();
            if(LocalDateTime.now().isBefore(LocalDateTime.parse(new_u.getCodeData()).plusMinutes(5))){
                new_u.setCode(null);
                new_u.setCodeData(null);
                repository.save(new_u);
            } else throw new EntityNotFoundException(User.class, "Час активності коду минув : ", u.get().getCode());
        } else throw new EntityNotFoundException(String.class, "id", code);
    }

    private boolean validateCode(String code){
        return code.chars().allMatch(Character::isDigit);
    }

    @Override
    public User sentToEmailAndDataBase(User user) {
        String code = createCode();
        MimeMessage mimeMessage;
        try {
            mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.setSubject(EmailMessage.EMAIL_CODE.getFirst_message(), "UTF-8");
            mimeMessage.setFrom(new InternetAddress("oleksandramorunova@gmail.com", "Emmo Techie"));
            mimeMessage.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(user.getEmail())});
            mimeMessage.setContent(SmsHtmlMessage.sms(code), "text/html; charset=UTF-8");
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Optional<User> u = Optional.ofNullable(checkByValue(user));
        if(u.isPresent()){
            u.get().setCode(code);
            u.get().setCodeData(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            return repository.save(u.get());
        } else throw new EntityNotFoundException(User.class, "id", user.getEmail());
    }

    private User checkByValue(User user){
        if (user.getEmail() != null){
            return userService.findUserByEmail(user.getEmail());
        } else return userService.findUserByPhone(user.getPhone());
    }

    private String createCode(){
        return String.valueOf((int) (Math.random() * (99999 - 10000 + 1) + 10000));
    }
}
