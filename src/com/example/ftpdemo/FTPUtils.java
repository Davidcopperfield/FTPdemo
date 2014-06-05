package com.example.ftpdemo;
    import java.io.File;  
    import java.io.FileInputStream;  
    import java.io.FileOutputStream;  
    import java.io.IOException;  
    import java.io.OutputStream;  
    import java.net.SocketException;  
      
    import org.apache.commons.net.ftp.FTP;  
    import org.apache.commons.net.ftp.FTPClient;  
    import org.apache.commons.net.ftp.FTPFile;  
    import org.apache.commons.net.ftp.FTPReply;  
      
   // import android.R.bool;  
    
//

public class FTPUtils {
	private FTPClient ftpClient = null;  
    private static FTPUtils ftpUtilsInstance = null;  
    private String FTPUrl;  
    private int FTPPort;  
    private String UserName;  
    private String UserPassword;  
      
    private FTPUtils()  
    {  
        ftpClient = new FTPClient();  
    }  
    /* 
     * �õ������ʵ������Ϊֻ����һ������������������õ���ģʽ�� 
     */  
    public  static FTPUtils getInstance() {  
        if (ftpUtilsInstance == null)  
        {  
            ftpUtilsInstance = new FTPUtils();  
        }  
        return ftpUtilsInstance;  
    }  
      
    /** 
     * ����FTP������ 
     * @param FTPUrl   FTP������ip��ַ 
     * @param FTPPort   FTP�������˿ں� 
     * @param UserName    ��½FTP���������˺� 
     * @param UserPassword    ��½FTP������������ 
     * @return 
     */  
    public boolean initFTPSetting(String FTPUrl, int FTPPort, String UserName, String UserPassword)  
    {     
        this.FTPUrl = FTPUrl;  
        this.FTPPort = FTPPort;  
        this.UserName = UserName;  
        this.UserPassword = UserPassword;  
          
        int reply;  
          
        try {  
            //1.Ҫ���ӵ�FTP������Url,Port  
            ftpClient.connect(FTPUrl, FTPPort);  
              
            //2.��½FTP������  
            ftpClient.login(UserName, UserPassword);  
              
            //3.�����ص�ֵ�ǲ���230������ǣ���ʾ��½�ɹ�  
            reply = ftpClient.getReplyCode();  
          
            if (!FTPReply.isPositiveCompletion(reply))  
            {  
                //�Ͽ�  
                ftpClient.disconnect();  
                return false;  
            }  
              
            return true;  
              
        } catch (SocketException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * �ϴ��ļ� 
     * @param FilePath    Ҫ�ϴ��ļ�����SDCard��·�� 
     * @param FileName    Ҫ�ϴ����ļ����ļ���(�磺SimΨһ��ʶ��) 
     * @return    trueΪ�ɹ���falseΪʧ�� 
     */  
    public boolean uploadFile(String FilePath, String FileName) {  
          
        if (!ftpClient.isConnected())  
        {  
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))  
            {  
                return false;  
            }  
        }  
          
        try {  
              
            //���ô洢·��  
            ftpClient.makeDirectory("/data");  
            ftpClient.changeWorkingDirectory("/data");  
              
            //�����ϴ��ļ���Ҫ��һЩ������Ϣ  
            ftpClient.setBufferSize(1024);    
            ftpClient.setControlEncoding("UTF-8");   
            ftpClient.enterLocalPassiveMode();     
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);  
              
            //�ļ��ϴ��ɡ�  
            FileInputStream fileInputStream = new FileInputStream(FilePath);  
            ftpClient.storeFile(FileName, fileInputStream);  
              
            //�ر��ļ���  
            fileInputStream.close();  
              
            //�˳���½FTP���ر�ftpCLient������  
            ftpClient.logout();  
            ftpClient.disconnect();  
              
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    }  
      
    /** 
     * �����ļ� 
     * @param FilePath  Ҫ��ŵ��ļ���·�� 
     * @param FileName   Զ��FTP�������ϵ��Ǹ��ļ������� 
     * @return   trueΪ�ɹ���falseΪʧ�� 
     */  
    public boolean downLoadFile(String FilePath, String FileName) {  
          
        if (!ftpClient.isConnected())  
        {  
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))  
            {  
                return false;  
            }  
        }  
           
        try {  
            // ת��ָ������Ŀ¼  
            ftpClient.changeWorkingDirectory("/data");  
              
            // �г���Ŀ¼�������ļ�  
            FTPFile[] files = ftpClient.listFiles();  
              
            // ���������ļ����ҵ�ָ�����ļ�  
            for (FTPFile file : files) {  
                if (file.getName().equals(FileName)) {  
                    //���ݾ���·����ʼ���ļ�  
                    File localFile = new File(FilePath);  
                      
                    // �����  
                    OutputStream outputStream = new FileOutputStream(localFile);  
                      
                    // �����ļ�  
                    ftpClient.retrieveFile(file.getName(), outputStream);  
                      
                    //�ر���  
                    outputStream.close();  
                }  
            }  
              
            //�˳���½FTP���ر�ftpCLient������  
            ftpClient.logout();  
            ftpClient.disconnect();  
              
              
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
        return true;  
    }  

}
