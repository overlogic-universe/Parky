package com.lucky7.parky.core.util.common;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    private final String senderEmail  = "overlogicuniverse@gmail.com";
    private final String senderPass = "vcntpnqwsjdxulhh";
    String receiverEmail;
    String receiverPass;

    public EmailSender(String receiverEmail, String receiverPass){
        this.receiverEmail = receiverEmail;
        this.receiverPass = receiverPass;
    }

    public void sendEmail(){
        Properties properties =  System.getProperties();
        String host = "smtp.gmail.com";
        String port = "465";

        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        javax.mail.Session session  = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPass);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        try {
            mimeMessage.addRecipients(MimeMessage.RecipientType.TO, new InternetAddress[]{new InternetAddress(receiverEmail)});
            mimeMessage.setSubject("Akses Akun Parkir Mahasiswa UMS: Informasi Penting");

            String emailContent = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body {font-family: Arial, sans-serif; line-height: 1.6; margin: 0; padding: 0;}"
                    + "p {margin: 0 0 10px; text-align: justify;}"
                    + "ul, ol {margin: 0 0 10px 20px; padding-left: 20px; text-align: justify;}"
                    + "ul li, ol li {margin: 0 0 5px;}"
                    + ".header {background-color: #f2f2f2; padding: 10px; text-align: center; border-bottom: 1px solid #ccc;}"
                    + ".content {padding: 20px;}"
                    + ".footer {background-color: #f2f2f2; padding: 10px; text-align: center; border-top: 1px solid #ccc; margin-top: 20px;}"
                    + ".highlight {color: #2d9cdb; font-weight: bold;}"
                    + "a {color: #2d9cdb; text-decoration: none;}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='header'>"
                    + "<h2>Universitas Muhammadiyah Surakarta</h2>"
                    + "</div>"
                    + "<div class='content'>"
                    + "<p>Yth. Mahasiswa Universitas Muhammadiyah Surakarta,</p>"
                    + "<p>Kami mengucapkan <span class='highlight'>selamat datang</span> dan selamat bergabung sebagai bagian dari keluarga besar Universitas Muhammadiyah Surakarta (UMS). "
                    + "Sebagai langkah awal, berikut kami informasikan detail akun dan password yang akan Anda gunakan untuk mengakses aplikasi sistem parkir bernama Parky, "
                    + "serta berbagai layanan dan sistem akademik di UMS.</p>"
                    + "<p><span class='highlight'>Informasi Akun Parky:</span></p>"
                    + "<ul>"
                    + "<li><b>Email:</b> " + receiverEmail + "</li>"
                    + "<li><b>Password:</b> " + receiverPass + "</li>"
                    + "</ul>"
                    + "<p><span class='highlight'>Petunjuk Penggunaan Parky:</span></p>"
                    + "<ol>"
                    + "<li><b>Login ke Aplikasi Parky:</b></li>"
                    + "<ul>"
                    + "<li>Unduh dan instal aplikasi Parky dari <a href='https://drive.google.com/drive/folders/1Uywot9T_bV-c_xyT1o1b8xl2OpxDkWjA?usp=drive_link'>link ini</a>.</li>"
                    + "<li>Buka aplikasi Parky di perangkat Anda.</li>"
                    + "<li>Masukkan email dan password yang telah diberikan di atas.</li>"
                    + "<li>Klik tombol <b>\"Login\"</b> untuk masuk ke dalam sistem.</li>"
                    + "</ul>"
                    + "<li><b>Ganti Password:</b></li>"
                    + "<ul>"
                    + "<li>Setelah berhasil login, Anda disarankan untuk segera mengganti password demi keamanan akun Anda.</li>"
                    + "<li>Anda dapat langsung mengganti password pada halaman beranda aplikasi Parky.</li>"
                    + "</ul>"
                    + "<li><b>Akses Layanan Parky:</b></li>"
                    + "<ul>"
                    + "<li>Anda dapat mengakses berbagai layanan parkir, seperti pengecekan status parkir Anda, melihat aktivitas terakhir parkir Anda"
                    + "dan informasi lainnya melalui aplikasi ini.</li>"
                    + "</ul>"
                    + "</ol>"
                    + "<p>Apabila Anda mengalami kesulitan dalam mengakses akun atau memerlukan bantuan lebih lanjut, silakan menghubungi layanan bantuan teknis kami di "
                    + "<a href='mailto:overlogicuniverse@gmail.com'>overlogicuniverse@gmail.com</a>.</p>"
                    + "<p>Kami berharap informasi ini membantu Anda untuk memulai perjalanan akademik di UMS dengan lancar serta memanfaatkan layanan Parky dengan efektif. "
                    + "Terima kasih atas perhatian dan kerjasamanya.</p>"
                    + "<p>Salam hangat,</p><br><br>"
                    + "<p>Universitas Muhammadiyah Surakarta</p>"
                    + "</div>"
                    + "<div class='footer'>"
                    + "<p>&copy; " + year + " Overlogic Universe. All rights reserved.</p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            mimeMessage.setContent(emailContent, "text/html");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            thread.start();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
