package org.jeecg.modules.fileManage.controller;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.fileManage.entity.FileManage;
import org.jeecg.modules.fileManage.service.IFileManageService;
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
 * @Description: 附件管理
 * @Author: jeecg-boot
 * @Date: 2019-05-27
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "附件管理")
@RestController
@RequestMapping("/fileManage/fileManage")
public class FileManageController {
    @Autowired
    private IFileManageService fileManageService;

    /**
     * 分页列表查询
     *
     * @param fileManage
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "附件管理-分页列表查询")
    @ApiOperation(value = "附件管理-分页列表查询", notes = "附件管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<FileManage>> queryPageList(FileManage fileManage,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        Result<IPage<FileManage>> result = new Result<IPage<FileManage>>();
        QueryWrapper<FileManage> queryWrapper = QueryGenerator.initQueryWrapper(fileManage, req.getParameterMap());
        Page<FileManage> page = new Page<FileManage>(pageNo, pageSize);
        IPage<FileManage> pageList = fileManageService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param fileManage
     * @return
     */
    @AutoLog(value = "附件管理-添加")
    @ApiOperation(value = "附件管理-添加", notes = "附件管理-添加")
    @PostMapping(value = "/add")
    public Result<FileManage> add(@RequestBody FileManage fileManage) {
        Result<FileManage> result = new Result<FileManage>();
        try {
            fileManageService.save(fileManage);
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
     * @param fileManage
     * @return
     */
    @AutoLog(value = "附件管理-编辑")
    @ApiOperation(value = "附件管理-编辑", notes = "附件管理-编辑")
    @PutMapping(value = "/edit")
    public Result<FileManage> edit(@RequestBody FileManage fileManage) {
        Result<FileManage> result = new Result<FileManage>();
        FileManage fileManageEntity = fileManageService.getById(fileManage.getId());
        if (fileManageEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = fileManageService.updateById(fileManage);
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
    @AutoLog(value = "附件管理-通过id删除")
    @ApiOperation(value = "附件管理-通过id删除", notes = "附件管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<FileManage> delete(@RequestParam(name = "id", required = true) String id) {
        Result<FileManage> result = new Result<FileManage>();
        FileManage fileManage = fileManageService.getById(id);
        if (fileManage == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = fileManageService.removeById(id);
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
    @AutoLog(value = "附件管理-批量删除")
    @ApiOperation(value = "附件管理-批量删除", notes = "附件管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<FileManage> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<FileManage> result = new Result<FileManage>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.fileManageService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 依据关联的类型和Id删除无用附件
     *
     * @param relateId   关联文件Id
     * @param relateType 文件类型
     * @return
     */
    @AutoLog(value = "附件管理-通过id删除")
    @ApiOperation(value = "附件管理-通过id删除", notes = "附件管理-通过id删除")
    @DeleteMapping(value = "/deleteByType")
    public Result<FileManage> deleteByType(@RequestParam(name = "relateId", required = true) String relateId,
                                           @RequestParam(name = "relateType", required = true) String relateType) {
        Result<FileManage> result = new Result<FileManage>();
        QueryWrapper<FileManage> queryWrapper = new QueryWrapper<FileManage>();
        queryWrapper.eq("relate_id", relateId).eq("relate_type", relateType);
        boolean b = fileManageService.remove(queryWrapper);
        if (b){
            result.setSuccess(true);
        }else {
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "附件管理-通过id查询")
    @ApiOperation(value = "附件管理-通过id查询", notes = "附件管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<FileManage> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<FileManage> result = new Result<FileManage>();
        FileManage fileManage = fileManageService.getById(id);
        if (fileManage == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(fileManage);
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
        QueryWrapper<FileManage> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                FileManage fileManage = JSON.parseObject(deString, FileManage.class);
                queryWrapper = QueryGenerator.initQueryWrapper(fileManage, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<FileManage> pageList = fileManageService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "附件管理列表");
        mv.addObject(NormalExcelConstants.CLASS, FileManage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("附件管理列表数据", "导出人:Jeecg", "导出信息"));
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
                List<FileManage> listFileManages = ExcelImportUtil.importExcel(file.getInputStream(), FileManage.class, params);
                for (FileManage fileManageExcel : listFileManages) {
                    fileManageService.save(fileManageExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listFileManages.size());
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
