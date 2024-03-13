package org.jeecg.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.oss.entity.OssFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Description: OOS云存储service接口
 * @Author 余维华
 */
public interface IOssFileService extends IService<OssFile> {

    /**
     * oss文件上传
     * @param multipartFile
     * @throws IOException
     */
	void upload(MultipartFile multipartFile) throws Exception;

    /**
     * oss文件删除
     * @param ossFile OSSFile对象
     * @return
     */
	boolean delete(OssFile ossFile);

}
