package com.walmart.fraudengine.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @author jfli 
 * mail tool for sending the mail
 */

public class MailHelper {

    private String displayName;
    private String to;
    private String ccto;
    private String from;
    private String smtpServer;
    private String username;
    private String password;
    private String subject;
    private String content;
    private boolean ifAuth = false; // the flag for us ,if need to authorize the user
    private String filename = "";
    private Vector<String> file = new Vector<String>(); // the attachments
    private Properties configp = new Properties();
    private String bcc;
    
    private static class SmtpAuth extends javax.mail.Authenticator {

        private String username, password;

        public SmtpAuth(String username, String password) {
            this.username = username;
            this.password = password;
        }

        protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
            return new javax.mail.PasswordAuthentication(username, password);
        }
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setIfAuth(boolean ifAuth) {
        this.ifAuth = ifAuth;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCcto() {
        return ccto;
    }

    public void setCcto(String ccto) {
        this.ccto = ccto;
    }

    public void addAttachfile(String fname) {
        file.addElement(fname);
    }
    
    /**
	 * Get the value of bcc
	 * @return The bcc
	 */
	public String getBcc() {
		return bcc;
	}

	/**
	 * @param bcc The bcc to set
	 */
	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	/**
     * load the remedy config info
     * 
     * server,username,password,port
     */
    public void loadProperty() throws Exception{
    	InputStream config = null;
        try {
        	config = this.getClass().getResourceAsStream("/fraudEngine.properties");
            configp.clear();
            configp.load(config);
           
                from = configp.getProperty("mail.exception.from");
                to = configp.getProperty("mail.exception.to");
                ccto = configp.getProperty("mail.exception.ccto");
                subject = configp.getProperty("mail.exception.Report.subject");
                content = configp.getProperty("mail.daily.content");
                bcc = configp.getProperty("mail.exception.bcc");
        } catch (IOException e) {
            LogSupport.error("read the config file error" + e);
            throw e;
        }finally{
        	if(config!=null)
        		config.close();
        }
    }
    
    public MailHelper() throws Exception{
        try {
			loadProperty();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
    }
    
    
    public MailHelper(Exception e) throws Exception {
        loadProperty();
//        StringBuffer buf=new StringBuffer();
        String exceptionStr="";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        /**
         StackTraceElement[] stracks= e.getStackTrace();
        for(StackTraceElement strack:stracks){
           
            buf.append("/n    " 
                    + strack.getClassName()
                    + "." 
                    + strack.getMethodName() 
                    + "(" 
                    + strack.getFileName() 
                    + ":" 
                    + strack.getLineNumber()
                    + ")");
            }
            this.setContent(buf.toString());
            **/  
                    try {
                            e.printStackTrace(pw);
                            exceptionStr = sw.toString();
                    }
                    finally {
                        try {
                                sw.close();
                                pw.close();
                        } catch (IOException e1) {
                              LogSupport.error(e1.getMessage(),e1);
                              throw e;
                        }
                    }  
             this.setContent(exceptionStr);
            
                       
    }
    
    /**
     * @param smtpServer
     * @param from
     * @param displayName
     * @param username
     * @param password
     * @param to
     * @param subject
     * @param content
     * @param bcc 
     */
    public MailHelper(String smtpServer, String from, String displayName, String username, String password, String to,
            String subject, String content,String bcc) {
        this.smtpServer = smtpServer;
        this.from = from;
        this.displayName = displayName;
        this.ifAuth = true;
        this.username = username;
        this.password = password;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.bcc = bcc;
    }

    public MailHelper(String smtpServer, String from, String displayName, String to, String subject, String content) {
        this.smtpServer = smtpServer;
        this.from = from;
        this.displayName = displayName;
        this.ifAuth = false;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public HashMap<String, String> send() throws UnsupportedEncodingException {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("state", "success");
        String message = "success";
        Session session = null;
        Properties props = System.getProperties();
        LogSupport.info("get into the Mail send()");
        if (ifAuth) {// need to authorize
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.auth", "true");
            SmtpAuth smtpAuth = new SmtpAuth(username, password);
            session = Session.getDefaultInstance(props, smtpAuth);
        } else { // needn't to Authorize
            props.put("mail.smtp.auth", "false");
            session = Session.getDefaultInstance(props, null);
        }
        session.setDebug(true);
        Transport trans = null;
        try {
            Message msg = new MimeMessage(session);
            try {
                Address from_address = new InternetAddress(from, displayName);
                msg.setFrom(from_address);
                LogSupport.info("read the from address");
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
                LogSupport.error("read the from address error" + e);
                throw e;
            }
            String[] names = to.split(";");
            InternetAddress[] address = new InternetAddress[names.length];
            
            for (int i = 0; i < address.length; i++) {
            	LogSupport.debug(i+":"+names[i]);
            	if (names[i].contains("@")) {
            		address[i] = new InternetAddress(names[i]);
            		LogSupport.info("set the to _address" + names[i]);
            	}
            }
            msg.setRecipients(Message.RecipientType.TO, address);

            names = null;
            names = ccto.split(";");
            InternetAddress[] ccaddress = new InternetAddress[names.length];
            if (names.length != 0) {
                //
                for (int i = 0; i < ccaddress.length; i++) {
                	LogSupport.debug(i+":"+names[i]);
                	if (names[i].contains("@")) {
                		ccaddress[i] = new InternetAddress(names[i]);
                		 LogSupport.info("set the cc _address" + names[i]);
                	}
                }
                // InternetAddress[] ccaddress = { new InternetAddress(ccto) };
            }
            
            
            names = null;
            names = bcc.split(";");
            InternetAddress[] bccaddress = new InternetAddress[names.length];
            if (names.length != 0) {
                //
                for (int i = 0; i < bccaddress.length; i++) {
                	LogSupport.debug(i+":"+names[i]);
                	if (names[i].contains("@")) {
                		bccaddress[i] = new InternetAddress(names[i]);
                		LogSupport.info("set the bcc _address" + names[i]);
                	}
                }
            }
            msg.setRecipients(Message.RecipientType.CC, ccaddress);
            msg.setRecipients(Message.RecipientType.BCC, bccaddress);
            msg.setSubject(subject);
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(content.toString(), "text/html;charset=gbk");
            mp.addBodyPart(mbp);
            if (!file.isEmpty()) {
                Enumeration<String> efile = file.elements();
                while (efile.hasMoreElements()) {
                    mbp = new MimeBodyPart();
                    filename = efile.nextElement().toString();
                    FileDataSource fds = new FileDataSource(filename);
                    mbp.setDataHandler(new DataHandler(fds));
                    mbp.setFileName(fds.getName());
                    mp.addBodyPart(mbp);
                }
                file.removeAllElements();
            }
            msg.setContent(mp);
            msg.setSentDate(new Date());

            msg.saveChanges();
            trans = session.getTransport("smtp");
            trans.connect(smtpServer, username, password);
            trans.sendMessage(msg, msg.getAllRecipients());
            trans.close();

        } catch (AuthenticationFailedException e) {

            map.put("state", "failed");
            message = "fail to send email ! username and passsword invaild";
            LogSupport.error(message + e);
            e.printStackTrace();
            
            
        } catch (MessagingException e) {
            message = "fail to send email：\n" + e.getMessage();
            map.put("state", "failed");
            LogSupport.error(message + e);
            e.printStackTrace();
            Exception ex = null;
            if ((ex = e.getNextException()) != null) {
                LogSupport.debug(ex.toString());
                ex.printStackTrace();
            }
        }
        map.put("message", message);
        return map;
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        try {
//            throw new Exception("this is a test exception");
//        
//        }catch(Exception e1){
//            Mail mail=new Mail(e1);
//            mail.send();
//        }
//    }
}
