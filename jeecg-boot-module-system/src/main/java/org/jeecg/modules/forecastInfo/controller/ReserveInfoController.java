package org.jeecg.modules.forecastInfo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.forecastInfo.entity.ReserveInfo;
import org.jeecg.modules.forecastInfo.entity.ReserveSetting;
import org.jeecg.modules.forecastInfo.service.IReserveInfoService;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.forecastInfo.service.IReserveSettingService;
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
 * @Description: 预案信息
 * @Author: jeecg-boot
 * @Date: 2019-07-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "预案信息")
@RestController
@RequestMapping("/forecastInfo/reserveInfo")
public class ReserveInfoController {
    @Autowired
    private IReserveInfoService reserveInfoService;

    @Autowired
    private IReserveSettingService reserveSettingService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param
     * @return
     */
    @AutoLog(value = "预案信息-分页列表查询")
    @ApiOperation(value = "预案信息-分页列表查询", notes = "预案信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<ReserveInfo>> queryPageList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    String name, String type, String publishOrg, String reserveStatues, String beginDate, String endDate) {
        Result<IPage<ReserveInfo>> result = new Result<IPage<ReserveInfo>>();
        Page<ReserveInfo> page = new Page<ReserveInfo>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        IPage<ReserveInfo> pageList = reserveInfoService.reserveInfoList(page, "list",sysUser.getId(), name, type, publishOrg, reserveStatues, beginDate, endDate);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "预案待办事项-分页列表查询")
    @ApiOperation(value = "预案待办事项-分页列表查询", notes = "预案待办事项-分页列表查询")
    @GetMapping(value = "/backLogList")
    public Result<IPage<ReserveInfo>> backLogList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                    String name, String type, String publishOrg,  String beginDate, String endDate) {
        Result<IPage<ReserveInfo>> result = new Result<IPage<ReserveInfo>>();
        Page<ReserveInfo> page = new Page<ReserveInfo>(pageNo, pageSize);
        IPage<ReserveInfo> pageList = reserveInfoService.reserveInfoList(page, "backLogList","", name, type, publishOrg, "0", beginDate, endDate);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "预案待办事项-分页列表查询")
    @ApiOperation(value = "预案待办事项-分页列表查询", notes = "预案待办事项-分页列表查询")
    @GetMapping(value = "/publishList")
    public Result<IPage<ReserveInfo>> publishList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                  String name, String type, String publishOrg, String reserveStatues, String beginDate, String endDate) {
        Result<IPage<ReserveInfo>> result = new Result<IPage<ReserveInfo>>();
        Page<ReserveInfo> page = new Page<ReserveInfo>(pageNo, pageSize);
        IPage<ReserveInfo> pageList = reserveInfoService.reserveInfoList(page, "publishList","", name, type, publishOrg, reserveStatues, beginDate, endDate);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
    /**
     * 添加
     *
     * @param reserveInfo
     * @return
     */
    @PostMapping(value = "/add")
    public Result<ReserveInfo> add(@RequestBody ReserveInfo reserveInfo,HttpServletRequest request) {
        Result<ReserveInfo> result = new Result<ReserveInfo>();
        try {
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            reserveInfo.setUserId(sysUser.getId());
            String relateId = request.getParameter("relateId");
            reserveInfoService.reserveInfoSave(reserveInfo,relateId);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param reserveInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<ReserveInfo> edit(@RequestBody ReserveInfo reserveInfo) {
        Result<ReserveInfo> result = new Result<ReserveInfo>();
        ReserveInfo reserveInfoEntity = reserveInfoService.getById(reserveInfo.getId());
        if (reserveInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            reserveInfo.setReserveStatues(0);
            boolean ok = reserveInfoService.updateById(reserveInfo);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "预案信息-通过id删除")
    @ApiOperation(value = "预案信息-通过id删除", notes = "预案信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<ReserveInfo> delete(@RequestParam(name = "id", required = true) String id) {
        Result<ReserveInfo> result = new Result<ReserveInfo>();
        ReserveInfo reserveInfo = reserveInfoService.getById(id);
        if (reserveInfo == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = reserveInfoService.removeById(id);
            if (ok) {
                result.success("删除成功!");
            }
        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "预案信息-批量删除")
    @ApiOperation(value = "预案信息-批量删除", notes = "预案信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<ReserveInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<ReserveInfo> result = new Result<ReserveInfo>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.reserveInfoService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "预案信息-通过id查询")
    @ApiOperation(value = "预案信息-通过id查询", notes = "预案信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<ReserveInfo> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<ReserveInfo> result = new Result<ReserveInfo>();
        ReserveInfo reserveInfo = reserveInfoService.getById(id);
        if (reserveInfo == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(reserveInfo);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<ReserveInfo> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                ReserveInfo reserveInfo = JSON.parseObject(deString, ReserveInfo.class);
                queryWrapper = QueryGenerator.initQueryWrapper(reserveInfo, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<ReserveInfo> pageList = reserveInfoService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "预案信息列表");
        mv.addObject(NormalExcelConstants.CLASS, ReserveInfo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("预案信息列表数据", "导出人:Jeecg", "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<ReserveInfo> listReserveInfos = ExcelImportUtil.importExcel(file.getInputStream(), ReserveInfo.class, params);
                for (ReserveInfo reserveInfoExcel : listReserveInfos) {
                    reserveInfoService.save(reserveInfoExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listReserveInfos.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.ok("文件导入失败！");
    }


    @GetMapping(value = "/getReserveSetting")
    public List<ReserveSetting> getReserveSetting() {
        QueryWrapper<ReserveSetting> queryWrapper = new QueryWrapper<ReserveSetting>();
        queryWrapper.orderByAsc("sort");
        List<ReserveSetting> list = reserveSettingService.list(queryWrapper);
        return list;
    }

    @PostMapping(value = "/approved")
    public Result<ReserveInfo> approved(@RequestBody Map<String,String> map){
        Result<ReserveInfo> result = new Result<ReserveInfo>();
        String id=map.get("id");
        String statues=map.get("result");
        ReserveInfo reserveInfo=reserveInfoService.getById(id);
        reserveInfo.setReserveStatues(Integer.valueOf(statues));
        reserveInfoService.updateById(reserveInfo);
        return result;
    }
}
