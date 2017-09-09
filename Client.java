/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author soulq
 */
import java.net.*;
import java.io.*;
import java.security.*;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.digest.DigestUtils;

public class Client {

   public static void main(String [] args) throws NoSuchAlgorithmException{
       // send file to server 
      String serverName = args[0];  // ip number
      int port = Integer.parseInt(args[1]);  // port number 
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);  // connect on socket
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();  // create output steam 
         DataOutputStream out = new DataOutputStream(outToServer);  // create data output
         
//        // open & read file to send to server 1-byte
//        File file = new File("D:/Downloads/hello.txt");
//        try (FileInputStream fis = new FileInputStream(file)) {
//            int content;  
//            byte lowByte = 0;
//            MessageDigest md = MessageDigest.getInstance("MD5");  
//            while ((content = fis.read()) != -1) {
//               lowByte = (byte)(content & 0xFF);  // change int to byte (lower byte)
//               md.update(lowByte);
//               out.write(content);  // send int to server     
//            }
//                 
//            byte [] digest = md.digest();
//            String myHash = DatatypeConverter
//                    .printHexBinary(digest).toUpperCase();
////            MessageDigest md = MessageDigest.getInstance("MD5");
//            System.out.println("Send file complete");
//            System.out.println("MD5 is " + myHash);
//            
//            //System.out.println("MD5 is " + md5);
//
//        } catch (IOException e) {
//                e.printStackTrace();
//        }

           // open & read file to send to server 1460 buffer size 
           File file = new File("D:/Downloads/Setup_Yulgang13.77.zip");
           int bufferSize = 1460;
           try (FileInputStream fis = new FileInputStream(file)) {
               byte [] buf;
               MessageDigest md = MessageDigest.getInstance("MD5");  // create MD5 Instance
               while ((fis.available()) != 0){  // if file is not empty
                int length = fis.available();  // file size
                if(length < bufferSize){  
                    buf = new byte[length];  // create file size buffer
                }else{
                    buf = new byte[bufferSize];  // buffer size 1024 
                }
                fis.read(buf);  // read keep data in buffer 
                md.update(buf);  // update the digest using the specified array of bytes.
                out.write(buf);  // send data to server 
               }
                byte [] digest = md.digest();  // Completes the hash computation
                String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();  // Converts an array of bytes into a string.
               System.out.println("Send file complete");
               System.out.println("MD5 is " + myHash);  // Display MD5 Checksum
           } catch (IOException e) {
                e.printStackTrace();
           }
           // end 1460 buffer size
           
//        // open & read file to send to server array bytes
//        File file = new File("D:/Downloads/sale.pdf");
//        try (FileInputStream fis = new FileInputStream(file)) {
//            byte[] buf = new byte[fis.available()];
//            fis.read(buf);
//            out.write(buf);
//            System.out.println("Send file complete");
//                
//        } catch (IOException e) {
//                e.printStackTrace();
//        }
        
        // MD5 
//        FileInputStream fis_md5 = new FileInputStream(file);
//        String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis_md5);
//        fis_md5.close();
//        System.out.println("MD5 is " + md5);
        // end MD5
        
//        // MD5
//            MessageDigest md = MessageDigest.getInstance("MD5");
//        // end MD5
        
         //out.writeUTF("สวัสดี Hello from " + client.getLocalSocketAddress());
         
//         InputStream inFromServer = client.getInputStream();
//         DataInputStream in = new DataInputStream(inFromServer);
//         
//         System.out.println("Server says " + in.readUTF());

         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}