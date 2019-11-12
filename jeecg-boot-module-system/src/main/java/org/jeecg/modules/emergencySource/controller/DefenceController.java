package org.jeecg.modules.emergencySource.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.emergencySource.entity.Defence;
import org.jeecg.modules.emergencySource.service.IDefenceService;
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
 * @Description: 防护目标
 * @Author: jeecg-boot
 * @Date: 2019-07-04
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "防护目标")
@RestController
@RequestMapping("/EmergencySource/defence")
public class DefenceController {
    @Autowired
    private IDefenceService defenceService;

    /**
     * 分页列表查询
     *
     * @param defence
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<Defence>> queryPageList(Defence defence,
                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                HttpServletRequest req) {
        Result<IPage<Defence>> result = new Result<IPage<Defence>>();
        QueryWrapper<Defence> queryWrapper = QueryGenerator.initQueryWrapper(defence, req.getParameterMap());
        Page<Defence> page = new Page<Defence>(pageNo, pageSize);
        IPage<Defence> pageList = defenceService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param defence
     * @return
     */
    //@AutoLog(value = "防护目标-添加")
    @PostMapping(value = "/add")
    public Result<Defence> add(@RequestBody Defence defence) {
        Result<Defence> result = new Result<Defence>();
        try {
            defenceService.defenceSave(defence);
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
     * @param defence
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<Defence> edit(@RequestBody Defence defence) {
        Result<Defence> result = new Result<Defence>();
        Defence defenceEntity = defenceService.getById(defence.getId());
        if (defenceEntity == null) {
            result.error500("未找到对应实体");
        } else {
            defenceService.defenceEdit(defence);
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "防护目标-通过id删除")
    @ApiOperation(value = "防护目标-通过id删除", notes = "防护目标-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<Defence> delete(@RequestParam(name = "id", required = true) String id) {
        Result<Defence> result = new Result<Defence>();
        Defence defence = defenceService.getById(id);
        if (defence == null) {
            result.error500("未找到对应实体");
        } else {
            defenceService.defenceRemove(id);

        }

        return result;
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "防护目标-批量删除")
    @ApiOperation(value = "防护目标-批量删除", notes = "防护目标-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<Defence> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<Defence> result = new Result<Defence>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.defenceService.defenceRemoveBatch(ids);
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
    @AutoLog(value = "防护目标-通过id查询")
    @ApiOperation(value = "防护目标-通过id查询", notes = "防护目标-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Defence> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<Defence> result = new Result<Defence>();
        Defence defence = defenceService.getById(id);
        if (defence == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(defence);
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
        QueryWrapper<Defence> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                Defence defence = JSON.parseObject(deString, Defence.class);
                queryWrapper = QueryGenerator.initQueryWrapper(defence, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<Defence> pageList = defenceService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "防护目标列表");
        mv.addObject(NormalExcelConstants.CLASS, Defence.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("防护目标列表数据", "导出人:Jeecg", "导出信息"));
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
                List<Defence> listDefences = ExcelImportUtil.importExcel(file.getInputStream(), Defence.class, params);
                for (Defence defenceExcel : listDefences) {
                    defenceService.save(defenceExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listDefences.size());
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

}
