package com.bethen.bethen.services;

import com.bethen.bethen.models.PlanNotificationModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailingService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String notificationOfPlan(PlanNotificationModel planNotificationModel) {

        try {
            String htmContent = notificationHtmlObject(planNotificationModel);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(planNotificationModel.getEmail());
            helper.setSubject("Plan Activation Success");
            helper.setText(htmContent, true);
            helper.setFrom("NoReplyBethen");

            javaMailSender.send(message);
            return "mail sent";
        }catch (Exception e){
           e.printStackTrace();
           return "not sent";
        }


    }

    public String notificationOfPayout(String email, String amount, String actNumber, String bank) {

        try {
            String htmContent = notificationBuilderForPayout( email, amount, actNumber, bank);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("alanemehenry6@gmail.com");
            helper.setSubject("Payout Request");
            helper.setText(htmContent, true);
            helper.setFrom("henryoluwatobi6@gmail.com");

            javaMailSender.send(message);
            return "mail sent";
        }catch (Exception e){
            e.printStackTrace();
            return "not sent";
        }


    }

    public String notificationOfROIPayment( String name, String amount, String month,String email) {

        try {
            String htmContent = notificationOfROI(name,amount,month);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("ROI Payment Notification");
            helper.setText(htmContent, true);
            helper.setFrom("henryoluwatobi6@gmail.com");

            javaMailSender.send(message);
            return "mail sent";
        }catch (Exception e){
            e.printStackTrace();
            return "not sent";
        }


    }

    //This is just to re-trigger build


    public  String notificationHtmlObject (PlanNotificationModel planNotificationModel){

        String html = String.format( """
                <!DOCTYPE html>
                
                <body>
                    Hi <b>%s</b>, <br />
                
                    <div style="margin-top: 20px; margin-bottom: 20px;">Thanks for fixing the sum of <b>NGN%s</b> with us your monthly interest will be <b>5%% of NGN%s</b> for the next 3months.</div>
                
                    <div style="margin-bottom: 20px;">Please feel free to reach out to us on <b>bethen718@gmail.com</b> if you require further assistance or clarification </div>
                
                    Warm regards <br />
                
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXTDz9qt0niP5vOzghA2zlOEw7f-TINtPehQ&s" width="30%%" height="10%%"  />
                </body>
                
                </html>
                
                """, planNotificationModel.getFirstName(),planNotificationModel.getAmount(),planNotificationModel.getAmount());
        return html;
    }

    public  String notificationOfROI (String name, String amount, String month){

        String html = String.format( """
                <!DOCTYPE html>
                
                <body>
                    Hi <b>%s</b>, <br />
                
                    <div style="margin-top: 20px; margin-bottom: 20px;">This is to notify you that the sum of <b>NGN%s</b> was credited to your account on %s </div>
                
                    <div style="margin-bottom: 20px;">Please feel free to reach out to us on <b>bethen718@gmail.com</b> if you require further assistance or clarification </div>
                
                    Warm regards <br />
                
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXTDz9qt0niP5vOzghA2zlOEw7f-TINtPehQ&s" width="30%%" height="10%%"  />
                </body>
                
                </html>
                
                """, name,amount,month);
        return html;
    }

    public  String notificationBuilderForPayout (String email, String amount, String actNumber, String bank){

        String html = String.format( """
                <!DOCTYPE html>
                
                <body>
                    Hi <b>Henry</b>, <br />
                
                    <div style="margin-top: 20px; margin-bottom: 20px;">This is to notify you that the user <b>%s</b> requested for a payout of <b>NGN%s</b></div>
                    
                    <div>
                    Account Number: <b>%s</b> <br />
                    Bank: <b>%s</b>
                    <div>
                
                    <div style="margin-bottom: 20px;">Please feel free to reach out to us on <b>bethen718@gmail.com</b> if you require further assistance or clarification </div>
                
                    Warm regards <br />
                
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXTDz9qt0niP5vOzghA2zlOEw7f-TINtPehQ&s" width="30%%" height="10%%"  />
                </body>
                
                </html>
                
                """, email,amount,actNumber,bank);
        return html;
    }
}
