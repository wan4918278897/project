package org.jeecg.modules.tiffManage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.tiffManage.service.TiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author yzx
 */

@RestController
@RequestMapping("/tiffManage/tiff")
public class TiffController {

    @Autowired
    private TiffService tiffService;

    @GetMapping(value = "/list")
    public Result<IPage<FileManage>> queryPageList() {
        Result result = new Result();
        List<Map<String, Object>>  list=tiffService.selectAll();
        List<Map<String, Object>> arrayList=new ArrayList<>();
        for (Map<String, Object> map:list) {
            Map<String, Object> objectMap=new HashMap<>();
            objectMap.put("sender1",convertCharset(map.get("sender1").toString()));
            objectMap.put("title",convertCharset(map.get("title").toString()));
            Integer i= Integer.valueOf(map.get("recvtime").toString());
            Date date =new Date(i*1000L);
            objectMap.put("recvtime",DateUtils.formatDate(date,"yyyy-MM-dd HH:mm:ss"));
            objectMap.put("faxfile",map.get("faxfile"));
            arrayList.add(objectMap);
        }
        result.setSuccess(true);
        result.setResult(arrayList);
        return result;
    }


    /**
     * 将拉丁转成gb2312
     * @param s
     * @return
     */
    public String convertCharset(String s) {
        if (s != null) {
            try {
                int length = s.length();
                byte[] buffer = new byte[length];
                // 0x81 to Unicode 0x0081, 0x8d to 0x008d, 0x8f to 0x008f, 0x90
                // to 0x0090, and 0x9d to 0x009d.
                for (int i = 0; i < length; ++i) {
                    char c = s.charAt(i);
                    if (c == 0x0081) {
                        buffer[i] = (byte) 0x81;
                    } else if (c == 0x008d) {
                        buffer[i] = (byte) 0x8d;
                    } else if (c == 0x008f) {
                        buffer[i] = (byte) 0x8f;
                    } else if (c == 0x0090) {
                        buffer[i] = (byte) 0x90;
                    } else if (c == 0x009d) {
                        buffer[i] = (byte) 0x9d;
                    } else {
                        buffer[i] = Character.toString(c).getBytes("CP1252")[0];
                    }
                }
                String result = new String(buffer, "gb2312");
                return result;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
