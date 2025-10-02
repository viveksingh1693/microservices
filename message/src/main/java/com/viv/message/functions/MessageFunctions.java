package com.viv.message.functions;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.viv.message.dto.AccountMsgDto;
import com.viv.message.dto.EmailRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MessageFunctions {

     private final JavaMailSender mailSender;

    @Bean
    public Function<EmailRequest, String> sendMail() {
        return request -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(request.getTo());
                message.setSubject(request.getSubject());
                message.setText(request.getBody());
                mailSender.send(message);
                return "Email sent successfully to " + request.getTo();
            } catch (Exception e) {
                return "Failed to send email: " + e.getMessage();
            }
        };
    }

    @Bean
    public Function<AccountMsgDto, AccountMsgDto> email() {
        return accountMsgDto -> {
            log.info("Emailing: {}", accountMsgDto);
            sendMail().apply(new EmailRequest() {{
                log.info("Sending email to: {}", accountMsgDto.email());
                setTo(accountMsgDto.email());
                setSubject("Account Notification");
                setBody("Your account number is: " + accountMsgDto.accountNumber());
            }});
            return accountMsgDto;
        };
    }

    @Bean
    public Function<AccountMsgDto, Long> sms() {
        return accountMsgDto -> {
            log.info("Send Mesaging: {}", accountMsgDto);
            return accountMsgDto.accountNumber();
        };
    }

}
