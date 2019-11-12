package org.jeecg.modules.emergencySource.controller;

import java.util.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.HttpClientUtils;
import org.jeecg.modules.emergencySource.entity.EmergencySource;
import org.jeecg.modules.emergencySource.service.IEmergencySourceService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 应急资源
 * @Author: jeecg-boot
 * @Date: 2019-07-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "应急资源")
@RestController
@RequestMapping("/emergencySource/emergencySource")
public class EmergencySourceController {
    @Autowired
    private IEmergencySourceService emergencySourceService;

    /**
     * 分页列表查询
     *
     * @param emergencySource
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "应急资源-分页列表查询")
    @ApiOperation(value = "应急资源-分页列表查询", notes = "应急资源-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EmergencySource>> queryPageList(EmergencySource emergencySource,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        Result<IPage<EmergencySource>> result = new Result<IPage<EmergencySource>>();
        QueryWrapper<EmergencySource> queryWrapper = QueryGenerator.initQueryWrapper(emergencySource, req.getParameterMap());
        Page<EmergencySource> page = new Page<EmergencySource>(pageNo, pageSize);
        IPage<EmergencySource> pageList = emergencySourceService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @PostMapping(value = "/remark")
    public Result remark(@RequestBody Map<String, String> map) {
        Result result = new Result();
        String relatedId = map.get("relateId");
        String type = map.get("type");
        String lat = map.get("lat");
        String lng = map.get("lng");
        QueryWrapper<EmergencySource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("related_id", relatedId).eq("type", type);
        EmergencySource emergencySource = emergencySourceService.getOne(queryWrapper);
        if (emergencySource == null) {
            result.error500("未找到对应实体");
        } else {
            emergencySource.setLatitude(Float.valueOf(lat));
            emergencySource.setLongitude(Float.valueOf(lng));
            emergencySourceService.updateById(emergencySource);
            result.setSuccess(true);
            result.success("标记成功");
        }
        return result;
    }

    @GetMapping(value = "/remarkList")
    public List<EmergencySource> remarkList(HttpServletRequest req) {
        String type=req.getParameter("type");
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.isNotNull("latitude");
        queryWrapper.isNotNull("longitude");
        if (StringUtils.isNotBlank(type)){
            //地图最下边点击图标赛选
            queryWrapper.eq("type",type);
        }

        List<EmergencySource> list = emergencySourceService.list(queryWrapper);
        return list;
    }

    @AutoLog(value = "应急资源-通过id查询")
    @ApiOperation(value="应急资源-通过id查询", notes="应急资源-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<EmergencySource> queryById(@RequestParam(name="id",required=true) String id) {
        Result<EmergencySource> result = new Result<EmergencySource>();
        EmergencySource emergencySource = emergencySourceService.getById(id);
        if(emergencySource==null) {
            result.error500("未找到对应实体");
        }else {
            result.setResult(emergencySource);
            result.setSuccess(true);
        }
        return result;
    }

    @GetMapping(value = "/getDataSource")
    public String getDataSource(HttpServletRequest request) {
        Map map = new HashMap();
        String key = request.getParameter("key");
        String address = request.getParameter("address");
        String url = request.getParameter("url");
        map.put("key", key);
        map.put("address", address);
        String s = "";
        try {
            s = HttpClientUtils.httpGet(url, map, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

}
