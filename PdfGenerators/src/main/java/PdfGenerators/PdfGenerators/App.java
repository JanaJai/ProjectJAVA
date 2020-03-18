package PdfGenerators.PdfGenerators;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.itextpdf.html2pdf.HtmlConverter; 
import javax.mail.*; 
import javax.mail.internet.*; 
import javax.activation.*; 
import javax.mail.Session; 
import javax.mail.Transport; 

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        VelocityContext context = new VelocityContext();
        context.put("tableName","COMPANY TABLE");
        List<String> headerList = new ArrayList<String>();
        headerList.add("Company");
        headerList.add("Contact");
        headerList.add("Country");      
        context.put("headerList", headerList);
        Template t = ve.getTemplate( "./src/main/java/demoHtml.vm" );
        StringWriter writer = new StringWriter();

        t.merge( context, writer );
        String HTMLtoPDF=writer.toString();
      
        Template tdes = ve.getTemplate( "./src/main/java/description.vm" );
        StringWriter writerdes = new StringWriter();

        tdes.merge( context, writerdes );
        String des=writerdes.toString();
    
        String recipient = "janaranjanijayachandiran@gmail.com"; 
        String sender = "janaranjanijayachandiran@gmail.com"; 
      
        // using host as localhost 
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("janaranjanijayachandiran@gmail.com", "jana1995");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true); 
      
        try 
        { 
            // MimeMessage object. 
            MimeMessage message = new MimeMessage(session); 
      
            // Set From Field: adding senders email to from field. 
            message.setFrom(new InternetAddress(sender)); 
            // Set To Field: adding recipient's email to from field. 
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient)); 
      
            // Set Subject: subject of the email 
            message.setSubject("Velocity template POC"); 
            // creating first MimeBodyPart object 
            BodyPart messageBodyPart1 = new MimeBodyPart();  
            messageBodyPart1.setText(des); 
              
            // creating second MimeBodyPart object 
            BodyPart messageBodyPart2 = new MimeBodyPart();  
            String filename = "HTMLtoPDF.pdf";
    			HtmlConverter.convertToPdf(HTMLtoPDF, new FileOutputStream("E:/Perf/string-to-pdf.pdf"));
    			System.out.println( "PDF Created!" );
           
            DataSource source = new FileDataSource("E:/Perf/string-to-pdf.pdf");   
            messageBodyPart2.setDataHandler(new DataHandler(source));   
            messageBodyPart2.setFileName(filename);   
              
            // creating MultiPart object 
            Multipart multipartObject = new MimeMultipart();   
            multipartObject.addBodyPart(messageBodyPart1);   
            multipartObject.addBodyPart(messageBodyPart2); 
      
      
      
            // set body of the email. 
            message.setContent(multipartObject); 
      
            // Send email. 
//            Transport transport = session.getTransport("smtp");
//            transport.connect("janaranjanijayachandiran@gmail.com","jana1995");
            //transport.sendMessage(message, message.getAllRecipients());
           
            Transport.send(message); 
          //  transport.close();
            System.out.println("Mail successfully sent"); 
        } 
        catch (MessagingException mex)  
        { 
            mex.printStackTrace(); 
        }   catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }
}
