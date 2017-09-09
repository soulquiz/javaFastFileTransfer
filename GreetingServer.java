/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greetingserver;

/**
 *
 * @author soulq
 */
// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();  // listenning on port
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());  // create data input steam 

//            //open file to write data form client 1-byte
//            File file = new File("D:/Downloads/write.txt");
//            try (FileOutputStream fop = new FileOutputStream(file)) {
//                    // if file doesn't exists, then create it
//                    if (!file.exists()) {
//                            file.createNewFile();
//                    }
//                    
//                    int content; 
//                    byte lowByte = 0;
//                    MessageDigest md = MessageDigest.getInstance("MD5");  
//                    while ((content = in.read()) != -1) {
//                        lowByte = (byte)(content & 0xFF);  // change int to byte (lower byte)
//                        md.update(lowByte);
//                        fop.write(content);  //  write int to file 
//                     }
//                    fop.flush();
//                    fop.close();
//                    byte [] digest = md.digest();
//                    String myHash = DatatypeConverter
//                        .printHexBinary(digest).toUpperCase();
//                    System.out.println("Done");
//                    System.out.println("MD5 is " + myHash);
//            } catch (IOException e) {
//                    e.printStackTrace();
//            } catch (NoSuchAlgorithmException ex) {
//                 Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
//             }
//            System.out.println("Receive file complete");
            
            
            //open file to write data form client 1460 buffer size
           File file = new File("D:/Downloads/game.zip");
           int bufferSize = 1460;
           try (FileOutputStream fop = new FileOutputStream(file)) {        
                // if file doesn't exists, then create it
                    if (!file.exists()) {
                            file.createNewFile();
                    }
                // crete buffer 
                byte [] buf;
                int remain;  // remain of data input 
                MessageDigest md = MessageDigest.getInstance("MD5");  // create MD5 Instance
                while(true){
                    if((remain = in.available()) != 0){  // if socket not empty           
                        if(remain < bufferSize){  // last 1024 byte 
                            buf = new byte[remain];
                            in.read(buf);
                            md.update(buf);  // update the digest using the specified array of bytes.
                            fop.write(buf);  // last write 
                            break;  // write file finished 
                        }else{
                            buf = new byte[bufferSize];  // buffer size 1024 
                        }
                        in.read(buf);  // read data in steam keep in buffer
                        md.update(buf);  // update the digest using the specified array of bytes.
                        fop.write(buf);  // write buffer to file
                    }
                }
                fop.flush();
                fop.close();
                byte [] digest = md.digest();  // Completes the hash computation
                String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();  // Converts an array of bytes into a string.
                System.out.println("Done");
                System.out.println("MD5 is " + myHash);  // Display MD5 Checksum
           } catch (IOException e) {
                e.printStackTrace();
           } catch (NoSuchAlgorithmException ex) {
                 Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
             }
           System.out.println("Receive file complete");
           // end 1460 buffer size
           
           
//            //open file to write data form client array byte buffer
//            File file = new File("D:/Downloads/write.pdf");
//            try (FileOutputStream fop = new FileOutputStream(file)) {
//                    // if file doesn't exists, then create it
//                    if (!file.exists()) {
//                            file.createNewFile();
//                    }
//                    //get the content in bytes
//                    byte [] contentInBytes = new byte[in.available()];
//                    in.read(contentInBytes);
//                    fop.write(contentInBytes);
//                    fop.flush();
//                    fop.close();
//                    System.out.println("Done");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("Receive file complete");
                    
                    
            
            // MD5 
//            FileInputStream fis_md5 = new FileInputStream(file);
//            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis_md5);
//            fis_md5.close();
//            System.out.println("MD5 is " + md5);
            // end MD5
        
            //read file
//            System.out.println(in.readUTF());
//            DataOutputStream out = new DataOutputStream(server.getOutputStream());
//            out.writeUTF("Thank you for connecting to สวัสดีครับ " + server.getLocalSocketAddress()
//               + "\nลาก่อน!");
            
            server.close();
            
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args){
      int port = Integer.parseInt(args[0]);  // port number 
      try {
         Thread t = new GreetingServer(port);  // fork new steam 
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}