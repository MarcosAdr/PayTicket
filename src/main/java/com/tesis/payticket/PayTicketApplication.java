package com.tesis.payticket;

import com.tesis.payticket.models.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PayTicketApplication implements CommandLineRunner  {

    @Autowired
    IUploadFileService uploadFileService;
    public static void main(String[] args) {
        SpringApplication.run(PayTicketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        uploadFileService.deleteAll();
        uploadFileService.init();
 
    }
}
