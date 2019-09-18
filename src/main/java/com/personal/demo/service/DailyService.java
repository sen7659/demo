package com.personal.demo.service;

import com.baomidou.mybatisplus.service.IService;
import com.personal.demo.bean.Daily;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface DailyService extends IService<Daily> {
    public Map query(Daily daily) ;

    public Map upload(MultipartFile file, HttpServletRequest request,Daily daily);

    public Map uploadImg(MultipartFile file,Daily daily);

    public Map updata(MultipartFile file, HttpServletRequest request,Daily daily);
}
