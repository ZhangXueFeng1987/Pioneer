package com.pioneer.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pioneer.mapper.MapImagesGeoLocationMapper;
import com.pioneer.mapper.po.MapImagesGeoLocationPO;
import com.pioneer.utils.aliyun.OSSImage;

@Controller
@RequestMapping("/map")
public class MapController {
    @Autowired
    private MapImagesGeoLocationMapper mapImagesGeoLocationMapper;
    
    //地图首页
    @RequestMapping("/index")
    public String index(Model model) {
    	model.addAttribute("editIndicator", "Y");
        return "module/map/index";
    }
    
    //加载地图可视域内的所有数据点
    @ResponseBody
    @RequestMapping("/loadImageGeolocationAroundCenterPoint")
    public ArrayList<MapImagesGeoLocationPO> loadImageGeolocationAroundCenterPoint(@RequestParam("southWestPointlng") String southWestPointlng,@RequestParam("southWestPointlat") String southWestPointlat,
                                                                                   @RequestParam("northEastPointlng") String northEastPointlng,@RequestParam("northEastPointlat") String northEastPointlat) {
    	String polygo="POLYGON(("+southWestPointlng+" "+southWestPointlat+","
                                    + northEastPointlng+" "+southWestPointlat+","
                                    + northEastPointlng+" "+northEastPointlat+","
                                    + southWestPointlng+" "+northEastPointlat+","
                                    + southWestPointlng+" "+southWestPointlat+"))";
    	System.out.println(polygo);
        ArrayList<MapImagesGeoLocationPO> mapImagesGeoLocationPOList=mapImagesGeoLocationMapper.selectGeolocationForUploadFile(polygo);
        return mapImagesGeoLocationPOList;
    }
    
    
    //上传图片
    @ResponseBody
    @RequestMapping(value="/uploadImage")
    public String uploadImage(
            @RequestParam("files") MultipartFile[] uploadfiles,@RequestParam("lng") String lng,@RequestParam("lat") String lat) {
    	for(MultipartFile file:uploadfiles){
    		try {
    			String uuid=UUID.randomUUID().toString().replace("-", "");
    			uuid=uuid+file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.'));
				OSSImage.uploadFile(file,uuid);
				String point="POINT("+lng+ " "+ lat+")";
				mapImagesGeoLocationMapper.insertGeolocationForUploadFile(point,uuid);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
        return "Y";
    }
    
    //加载上传图片的窗口
    @RequestMapping("/loadUploadFileWindowTemplate")
    public String loadUploadFileTemplate(ModelMap map) {
        return "module/map/contextMenuTemplate/uploadInfoWindow";
    }
    
    //加载单个图片
    @RequestMapping("/loadImage")
    public void loadImage(@RequestParam("imageUUID") String imageUUID,HttpServletResponse response) throws IOException {
        InputStream in=OSSImage.viewImage(imageUUID);
        response.setContentType("image/"+imageUUID.substring(imageUUID.indexOf('.')+1));
        OutputStream stream = response.getOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {   
            stream.write(ch);   
        }
        stream.flush();
        stream.close();
        in.close();
    }
    //加载查看图片的窗口
    @RequestMapping("/loadViewFileWindowTemplate")
    public String loadViewFileWindowTemplate(Model model,@RequestParam("uuid") String uuid) {
        model.addAttribute("uuid", uuid);
        return "module/map/contextMenuTemplate/viewInfoWindow";
    }
    //删除图片
    @ResponseBody
    @RequestMapping("/deleteImage")
    public String deleteImage(Model model,@RequestParam("imageUUID") String imageUUID) {
        try {
            OSSImage.deleteFile(imageUUID);
            mapImagesGeoLocationMapper.deleteGeolocationForUploadFile(imageUUID);
        } catch (IOException e) {
            e.printStackTrace();
            return "N";
        }
        return "Y";
    }
    //下载原始图片
    @RequestMapping("/downloadImage")
    public void downloadImage(Model model,@RequestParam("imageUUID") String imageUUID,HttpServletResponse response) throws IOException {
    	InputStream in=OSSImage.downloadFile(imageUUID);
        response.setContentType("application/octet-stream");  
        response.setHeader("Content-Disposition", "attachment;fileName="+System.currentTimeMillis()+imageUUID.substring(imageUUID.indexOf('.')));   
        OutputStream stream = response.getOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {   
            stream.write(ch);   
        }
        stream.flush();
        stream.close();
        in.close();
    }
}