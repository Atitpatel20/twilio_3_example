package com.twilio.example3.controller;

import com.twilio.example3.payload.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
public class SmsController {

    // Twilio credentials
    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

        @PostMapping("/send-sms")
        public ResponseEntity<String> sendSms (@RequestBody SmsRequest smsRequest){
            try {
                Twilio.init(accountSid, authToken);
                // Send SMS using Twilio API
                Message message = Message.creator(
                                new PhoneNumber(smsRequest.getTo()),
                                new PhoneNumber(twilioPhoneNumber),
                                smsRequest.getMessage())
                        .create();
                return new ResponseEntity<>("SMS sent successfully. SID: " + message.getSid(), HttpStatus.OK);
            } catch (Exception ex) {
                return new ResponseEntity<>("Failed to send SMS: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
