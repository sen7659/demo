package com.personal.demo.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.personal.demo.Utils.Ognl;
import com.personal.demo.bean.Daily;
import com.personal.demo.dao.DailyDao;
import com.personal.demo.service.DailyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class DailyServiceImpl extends ServiceImpl<DailyDao, Daily> implements DailyService {
    @Resource
    DailyDao dailyDao;

    @Override
    public Map query(Daily daily) {
        Map map = new LinkedHashMap();

        int start = (daily.getPage() - 1) * daily.getLimit();
        daily.setPage(start);

        if (Ognl.isNotEmpty(daily.getSelectDate())) {
            String format = daily.getSelectDate();
            String startDate = "";
            String endDate = "";
            for (int i = 0; i < format.length() - 13; i++) {
                startDate += format.charAt(i);
            }
            for (int i = 13; i < format.length(); i++) {
                endDate += format.charAt(i);
            }
            daily.setStartDate(startDate);
            daily.setEndDate(endDate);
        }

        map.put("code", 0);
        map.put("data", dailyDao.query(daily));
        map.put("count", dailyDao.queryCount(daily));
        return map;
    }

    @Override
    public Map upload(MultipartFile file, HttpServletRequest request, Daily daily) {
        Map map = new HashMap();
        try {
            if (file != null) {
                File dir = new File(ResourceUtils.getURL("classpath:").getPath());

                File upload = new File(dir.getAbsolutePath(), "/static/successImage/content/");

                if (!upload.exists()) {
                    upload.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + substring;
                String uploadPath = upload + "/";
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File(uploadPath + newFileName));

                String url = "/static/successImage/content/" + newFileName;
                daily.setPictureUrl(url);

                if (Ognl.isEmpty(daily.getTitle()) || Ognl.isEmpty(daily.getDate()) || Ognl.isEmpty(daily.getContent())) {
                    map.put("code", 0);
                    map.put("msg", "填写完整");
                } else {
                    dailyDao.insert(daily);
                    map.put("code", 1);
                    map.put("msg", "上传成功");
                }
            }
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", "上传失败");
        }
        return map;
    }

    @Override
    public Map uploadImg(MultipartFile file, Daily daily) {
        Map map = new HashMap();
        try {
            if (file != null) {
                File dir = new File(ResourceUtils.getURL("classpath:").getPath());

                File upload = new File(dir.getAbsolutePath(), "/static/successImage/content/img/");

                if (!upload.exists()) {
                    upload.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
                String newFileName = UUID.randomUUID().toString() + substring;
                String uploadPath = upload + "/";
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File(uploadPath + newFileName));
                String url = "/static/successImage/content/img/" + newFileName;

                Map dataMap=new HashMap();
                dataMap.put("src",url);

                map.put("code", 0);
                map.put("msg", "上传成功");
                map.put("data",dataMap);

            }
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "上传失败");
        }
        return map;
    }

    @Override
    public Map updata(MultipartFile file, HttpServletRequest request, Daily daily) {
        Map map = new HashMap();
        try {
            if (file !=null){

                File dir = new File(ResourceUtils.getURL("classpath:").getPath());

                File upload = new File(dir.getAbsolutePath());

                if (!upload.exists()) {
                    upload.mkdirs();
                }
                String newFileName = daily.getPictureUrl();

                String uploadPath = upload + "/";
                FileUtils.copyInputStreamToFile(file.getInputStream(),new File(uploadPath + newFileName));

                String url =  newFileName;

                daily.setPictureUrl(url);
            }
            if (Ognl.isEmpty(daily.getTitle()) || Ognl.isEmpty(daily.getDate()) || Ognl.isEmpty(daily.getContent())) {
                map.put("code", 0);
                map.put("msg", "填写完整");
            } else {
                dailyDao.updateById(daily);
                map.put("code", 1);
                map.put("msg", "修改成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", "修改失败");
        }
        return map;
    }
}
