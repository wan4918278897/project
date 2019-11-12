package org.jeecg.modules.system.controller;

import java.text.SimpleDateFormat;
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
import org.jeecg.modules.emergencyduty.entity.Briefing;
import org.jeecg.modules.system.entity.SysJournal;
import org.jeecg.modules.system.service.ISysJournalService;
import java.util.Date;
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
 * @Description: 个人日志
 * @Author: jeecg-boot
 * @Date:   2019-07-22
 * @Version: V1.0
 */
@Slf4j
@Api(tags="个人日志")
@RestController
@RequestMapping("/system/sysJournal")
public class SysJournalController {
	@Autowired
	private ISysJournalService sysJournalService;
	
	/**
	  * 分页列表查询
	 * @param sysJournal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "个人日志-分页列表查询")
	@ApiOperation(value="个人日志-分页列表查询", notes="个人日志-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SysJournal>> queryPageList(SysJournal sysJournal,
										 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										 @RequestParam(name="isReporter", defaultValue="") String isReporter,
												   @RequestParam(name="title", defaultValue="") String title,
												   @RequestParam(name="checkDate", defaultValue="") String checkDate,
									  HttpServletRequest req) {
		Result<IPage<SysJournal>> result = new Result<IPage<SysJournal>>();
		//判断是否为上报模式查询list，若是，将登录人id插入查询条件
		String reporter="";
		if(isReporter.equals("1"))
		{
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			reporter+=sysUser.getId();
		}
		Page<SysJournal> page = new Page<SysJournal>(pageNo, pageSize);
		IPage<SysJournal> pageList = sysJournalService.queryList(page,title,reporter,checkDate);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param sysJournal
	 * @return
	 */
	@ApiOperation(value="个人日志-添加", notes="个人日志-添加")
	@PostMapping(value = "/add")
	public Result<SysJournal> add(@RequestBody SysJournal sysJournal) {
		Result<SysJournal> result = new Result<SysJournal>();
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		sysJournal.setUserId(sysUser.getId());

		//查询是否已有当日日志
/*
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date=bartDateFormat.format(sysJournal.getWriteTime().getTime());
*/

		//插入新日志
		try {
			//若已有当日日志，拒绝插入
/*			if(sysJournalService.selectByDate(date)!=0)
			{
				result.error500("已有当日日志");
				return result;
			}*/
			sysJournalService.save(sysJournal);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param sysJournal
	 * @return
	 */
	@ApiOperation(value="个人日志-编辑", notes="个人日志-编辑")
	@PutMapping(value = "/edit")
	public Result<SysJournal> edit(@RequestBody SysJournal sysJournal) {
		Result<SysJournal> result = new Result<SysJournal>();
		SysJournal sysJournalEntity = sysJournalService.getById(sysJournal.getId());
		if(sysJournalEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysJournalService.updateById(sysJournal);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "个人日志-通过id删除")
	@ApiOperation(value="个人日志-通过id删除", notes="个人日志-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<SysJournal> delete(@RequestParam(name="id",required=true) String id) {
		Result<SysJournal> result = new Result<SysJournal>();
		SysJournal sysJournal = sysJournalService.getById(id);
		if(sysJournal==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysJournalService.removeById(id);
			if(ok) {
				result.success("删除成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "个人日志-批量删除")
	@ApiOperation(value="个人日志-批量删除", notes="个人日志-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<SysJournal> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysJournal> result = new Result<SysJournal>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.sysJournalService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "个人日志-通过id查询")
	@ApiOperation(value="个人日志-通过id查询", notes="个人日志-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SysJournal> queryById(@RequestParam(name="id",required=true) String id) {
		Result<SysJournal> result = new Result<SysJournal>();
		SysJournal sysJournal = sysJournalService.getById(id);
		if(sysJournal==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysJournal);
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
      QueryWrapper<SysJournal> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              SysJournal sysJournal = JSON.parseObject(deString, SysJournal.class);
              queryWrapper = QueryGenerator.initQueryWrapper(sysJournal, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<SysJournal> pageList = sysJournalService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "个人日志列表");
      mv.addObject(NormalExcelConstants.CLASS, SysJournal.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("个人日志列表数据", "导出人:Jeecg", "导出信息"));
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
              List<SysJournal> listSysJournals = ExcelImportUtil.importExcel(file.getInputStream(), SysJournal.class, params);
              for (SysJournal sysJournalExcel : listSysJournals) {
                  sysJournalService.save(sysJournalExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listSysJournals.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
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
