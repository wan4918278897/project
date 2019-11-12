package org.jeecg.modules.scheduling.controller;

import java.util.*;
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
import org.jeecg.modules.scheduling.entity.ScheduleJournal;
import org.jeecg.modules.scheduling.service.IScheduleJournalService;
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
 * @Description: 值班日志
 * @Author: jeecg-boot
 * @Date:   2019-07-23
 * @Version: V1.0
 */
@Slf4j
@Api(tags="值班日志")
@RestController
@RequestMapping("/scheduling/scheduleJournal")
public class ScheduleJournalController {
	@Autowired
	private IScheduleJournalService scheduleJournalService;
	
	/**
	  * 分页列表查询
	 * @param scheduleJournal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "值班日志-分页列表查询")
	@ApiOperation(value="值班日志-分页列表查询", notes="值班日志-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ScheduleJournal>> queryPageList(ScheduleJournal scheduleJournal,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
										@RequestParam(name="checkDate", defaultValue="") String checkDate,
									  HttpServletRequest req) {
		Result<IPage<ScheduleJournal>> result = new Result<IPage<ScheduleJournal>>();
		QueryWrapper<ScheduleJournal> queryWrapper = QueryGenerator.initQueryWrapper(scheduleJournal, req.getParameterMap());
		Page<ScheduleJournal> page = new Page<ScheduleJournal>(pageNo, pageSize);
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		IPage<ScheduleJournal> pageList = scheduleJournalService.queryList(page,sysUser.getId(),checkDate);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param scheduleJournal
	 * @return
	 */
	@ApiOperation(value="值班日志-添加", notes="值班日志-添加")
	@PostMapping(value = "/add")
	public Result<ScheduleJournal> add(@RequestBody ScheduleJournal scheduleJournal) {
		Result<ScheduleJournal> result = new Result<ScheduleJournal>();
		try {
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			scheduleJournal.setUserId(sysUser.getId());
			scheduleJournalService.save(scheduleJournal);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param scheduleJournal
	 * @return
	 */
	@ApiOperation(value="值班日志-编辑", notes="值班日志-编辑")
	@PutMapping(value = "/edit")
	public Result<ScheduleJournal> edit(@RequestBody ScheduleJournal scheduleJournal) {
		Result<ScheduleJournal> result = new Result<ScheduleJournal>();
		ScheduleJournal scheduleJournalEntity = scheduleJournalService.getById(scheduleJournal.getId());
		if(scheduleJournalEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = scheduleJournalService.updateById(scheduleJournal);
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
	@AutoLog(value = "值班日志-通过id删除")
	@ApiOperation(value="值班日志-通过id删除", notes="值班日志-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<ScheduleJournal> delete(@RequestParam(name="id",required=true) String id) {
		Result<ScheduleJournal> result = new Result<ScheduleJournal>();
		ScheduleJournal scheduleJournal = scheduleJournalService.getById(id);
		if(scheduleJournal==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = scheduleJournalService.removeById(id);
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
	@AutoLog(value = "值班日志-批量删除")
	@ApiOperation(value="值班日志-批量删除", notes="值班日志-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<ScheduleJournal> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<ScheduleJournal> result = new Result<ScheduleJournal>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.scheduleJournalService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "值班日志-通过id查询")
	@ApiOperation(value="值班日志-通过id查询", notes="值班日志-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ScheduleJournal> queryById(@RequestParam(name="id",required=true) String id) {
		Result<ScheduleJournal> result = new Result<ScheduleJournal>();
		ScheduleJournal scheduleJournal = scheduleJournalService.getById(id);
		if(scheduleJournal==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(scheduleJournal);
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
      QueryWrapper<ScheduleJournal> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              ScheduleJournal scheduleJournal = JSON.parseObject(deString, ScheduleJournal.class);
              queryWrapper = QueryGenerator.initQueryWrapper(scheduleJournal, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<ScheduleJournal> pageList = scheduleJournalService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "值班日志列表");
      mv.addObject(NormalExcelConstants.CLASS, ScheduleJournal.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("值班日志列表数据", "导出人:Jeecg", "导出信息"));
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
              List<ScheduleJournal> listScheduleJournals = ExcelImportUtil.importExcel(file.getInputStream(), ScheduleJournal.class, params);
              for (ScheduleJournal scheduleJournalExcel : listScheduleJournals) {
                  scheduleJournalService.save(scheduleJournalExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listScheduleJournals.size());
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

	 @GetMapping(value = "/findSchedulePerson")
	 public Result<List<Object>> findSchedulePerson(HttpServletRequest request) {
		 String id = request.getParameter("id");
		 List list = scheduleJournalService.findSchedulePerson(id);
		 Result<List<Object>> result = new Result<>();
		 if (list.size() > 0) {
			 result.setResult(list);
		 }
		 result.setSuccess(true);
		 return result;
	 }

}
