package org.jeecg.modules.emergencyduty.controller;

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
import org.jeecg.modules.emergencyduty.entity.Contacts;
import org.jeecg.modules.emergencyduty.service.IContactsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 通讯录管理
 * @Author: jeecg-boot
 * @Date: 2019-05-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "通讯录管理")
@RestController
@RequestMapping("/emergencyduty/contacts")
public class ContactsController {
    @Autowired
    private IContactsService contactsService;
    @Autowired
    private ISysDepartService sysDepartService;

    /**
     * 分页列表查询
     *
     * @param contacts
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "通讯录管理-分页列表查询")
    @ApiOperation(value = "通讯录管理-分页列表查询", notes = "通讯录管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<Contacts>> queryPageList(Contacts contacts,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 @RequestParam(name = "name", defaultValue = "") String  name,
                                                 @RequestParam(name = "mobile", defaultValue = "") String mobile,
                                                 @RequestParam(name = "officePhone", defaultValue = "") String officePhone,
                                                 HttpServletRequest req) {

        Result<IPage<Contacts>> result = new Result<IPage<Contacts>>();
        Page page = new Page(pageNo, pageSize);
        IPage pageList = null;
        pageList = contactsService.getUserByOrgIdOrDepId(page, contacts.getDepartId(),name,mobile,officePhone);
/*        if (StringUtils.isEmpty(contacts.getOrgId())) {

        } else {
            QueryWrapper<Contacts> queryWrapper = QueryGenerator.initQueryWrapper(contacts, req.getParameterMap());
            pageList = contactsService.page(page, queryWrapper);
        }*/

        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param Contacts
     * @return
     */
//	@AutoLog(value = "通讯录管理-添加")
//	@ApiOperation(value="通讯录管理-添加", notes="通讯录管理-添加")
    @PostMapping(value = "/add")
    public Result<Contacts> add(@RequestBody Contacts Contacts) {
        Result<Contacts> result = new Result<Contacts>();
        try {
            if (StringUtils.isEmpty(Contacts.getOrgId())) {
                SysDepart sysDepart = sysDepartService.getById(Contacts.getDepartId());
                Contacts.setOrgId(Contacts.getDepartId());
                Contacts.setDepartId(null);
            }else {
                SysDepart sysDepart = sysDepartService.getById(Contacts.getOrgId());
            }
            contactsService.save(Contacts);
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
     * @param Contacts
     * @return
     */
//	@AutoLog(value = "通讯录管理-编辑")
//	@ApiOperation(value="通讯录管理-编辑", notes="通讯录管理-编辑")
    @PutMapping(value = "/edit")
    public Result<Contacts> edit(@RequestBody Contacts Contacts) {
        Result<Contacts> result = new Result<Contacts>();
        Contacts contactsEntity = contactsService.getById(Contacts.getId());
        if (contactsEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = contactsService.updateById(Contacts);
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
    @AutoLog(value = "通讯录管理-通过id删除")
    @ApiOperation(value = "通讯录管理-通过id删除", notes = "通讯录管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<Contacts> delete(@RequestParam(name = "userId", required = true) String id) {
        Result<Contacts> result = new Result<Contacts>();
        Contacts Contacts = contactsService.getById(id);
        if (Contacts == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = contactsService.removeById(id);
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
    @AutoLog(value = "通讯录管理-批量删除")
    @ApiOperation(value = "通讯录管理-批量删除", notes = "通讯录管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<Contacts> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<Contacts> result = new Result<Contacts>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.contactsService.removeByIds(Arrays.asList(ids.split(",")));
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
    @AutoLog(value = "通讯录管理-通过id查询")
    @ApiOperation(value = "通讯录管理-通过id查询", notes = "通讯录管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<Contacts> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<Contacts> result = new Result<Contacts>();
        Contacts Contacts = contactsService.getById(id);
        if (Contacts == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(Contacts);
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
        QueryWrapper<Contacts> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                Contacts Contacts = JSON.parseObject(deString, Contacts.class);
                queryWrapper = QueryGenerator.initQueryWrapper(Contacts, request.getParameterMap());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<Contacts> pageList = contactsService.list(queryWrapper);
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "通讯录管理列表");
        mv.addObject(NormalExcelConstants.CLASS, Contacts.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("通讯录管理列表数据", "导出人:Jeecg", "导出信息"));
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
                List<Contacts> listContacts = ExcelImportUtil.importExcel(file.getInputStream(), Contacts.class, params);
                for (Contacts contactsExcel : listContacts) {
                    contactsService.save(contactsExcel);
                }
                return Result.ok("文件导入成功！数据行数:" + listContacts.size());
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
