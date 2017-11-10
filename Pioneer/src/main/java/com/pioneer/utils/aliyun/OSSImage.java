package com.pioneer.utils.aliyun;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

public class OSSImage {
	private static String endpoint = "http://vpc100-oss-cn-shanghai.aliyuncs.com";
    private static String accessKeyId = "LTAIaB3qKoqmQCBe";
    private static String accessKeySecret = "11AVpPQgcQnGcEW4UtEVONuw7b9sLr";
    private static String bucketName = "pioneers";
    private static OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    
    public static InputStream downloadFile(String key) {        
        InputStream is = null;
        try {
            // 缩放
            GetObjectRequest request = new GetObjectRequest(bucketName, key);
            OSSObject ossObject=ossClient.getObject(request);
            is=ossObject.getObjectContent();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            return is;
        }
    }
    public static InputStream viewImage(String key) {        
        InputStream is = null;
        try {
            // 缩放
            String style = "image/resize,m_fixed,w_700,h_600";  
            GetObjectRequest request = new GetObjectRequest(bucketName, key);
            request.setProcess(style);
            OSSObject ossObject=ossClient.getObject(request);
            is=ossObject.getObjectContent();
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            return is;
        }
    }
    public static void uploadFile(MultipartFile file,String uuid) throws IOException{       
    	// 上传文件流
		InputStream inputStream;
		inputStream = file.getInputStream();
		ossClient.putObject(bucketName, uuid, inputStream);
    }
    public static void deleteFile(String uuid) throws IOException{       
        // 删除文件流
        ossClient.deleteObject(bucketName, uuid);
    }
    
}
