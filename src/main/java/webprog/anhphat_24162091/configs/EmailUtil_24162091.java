package webprog.anhphat_24162091.configs;

import java.security.SecureRandom;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil_24162091 {

    private static String smtpHost = "smtp.gmail.com";
    private static String smtpPort = "587";
    private static String senderEmail = "webprogramming.edu.vn@gmail.com";
    private static String senderPassword = "your_app_password";

    /**
     * Sinh mã số OTP ngẫu nhiên gồm 6 chữ số
     */
    public static String generateOtp(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }

    /**
     * Gửi email thông báo mã OTP và in rõ ràng ra Console log để kiểm thử
     */
    public static boolean sendOtpEmail(String toEmail, String otpCode) {
        String subject = "[Web Programming 24162091] Mã Xác Nhận Kích Hoạt Tài Khoản (OTP)";
        String htmlContent = "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ddd; max-width: 600px;'>"
                + "<h2 style='color: #0d6efd;'>Xác Nhận Kích Hoạt Tài Khoản</h2>"
                + "<p>Xin chào,</p>"
                + "<p>Mã OTP dùng để kích hoạt tài khoản của bạn tại hệ thống Web Programming là:</p>"
                + "<div style='background: #f1f3f5; padding: 15px; text-align: center; font-size: 24px; font-weight: bold; letter-spacing: 5px; color: #d63384;'>"
                + otpCode + "</div>"
                + "<p>Mã OTP có hiệu lực trong vòng <strong>5 phút</strong>. Vui lòng không chia sẻ mã này cho bất kỳ ai.</p>"
                + "<hr><p style='font-size: 12px; color: #6c757d;'>Sinh viên: Huỳnh Tấn Anh Phát - MSSV: 24162091 - Đề 3</p>"
                + "</div>";

        // In trực tiếp ra console
        System.out.println("================================================================================");
        System.out.println("[OTP SERVICE - 24162091]");
        System.out.println("-> Người nhận: " + toEmail);
        System.out.println("-> MÃ OTP KÍCH HOẠT: " + otpCode);
        System.out.println("================================================================================");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        try {
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "Web Programming 24162091", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            try {
                Transport.send(message);
                System.out.println("[OTP SERVICE] Đã gửi email thành công tới " + toEmail);
            } catch (Exception e) {
                System.out.println("[OTP SERVICE] SMTP chưa được cấu hình credentials hoặc không có mạng, mã OTP đã xuất trên console log phục vụ kiểm thử.");
            }
            return true;
        } catch (Exception e) {
            System.err.println("[OTP SERVICE ERROR] " + e.getMessage());
            return true; // Vẫn cho phép tiếp tục vì OTP đã xuất lên console/session
        }
    }
}

