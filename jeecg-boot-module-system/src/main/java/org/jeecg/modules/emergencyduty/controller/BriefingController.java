package org.jeecg.modules.emergencyduty.controller;

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
import org.jeecg.modules.emergencyduty.entity.Briefing;
import org.jeecg.modules.emergencyduty.entity.EmergencyBriefing;
import org.jeecg.modules.emergencyduty.service.IBriefingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.emergencyduty.utils.Constant;
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
 * @Description: 简报
 * @Author: jeecg-boot
 * @Date:   2019-06-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="简报")
@RestController
@RequestMapping("/emergencyduty/briefing")
public class BriefingController {
	@Autowired
	private IBriefingService briefingService;

	 /**
	  * 分页列表查询
	  * @param briefing
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "典型案例-分页列表查询")
	 @ApiOperation(value="典型案例-分页列表查询", notes="典型案例-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<Briefing>> queryPageList(Briefing briefing,
														   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
														   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												           @RequestParam(name="title", defaultValue="") String title,
												           @RequestParam(name="reviewStatus", defaultValue="") String reviewStatus,
												           @RequestParam(name="befType", defaultValue="") String befType,
														   @RequestParam(name="pubStartDate", defaultValue="") String pubStartDate,
														   @RequestParam(name="pubEndDate", defaultValue="") String pubEndDate,
														   HttpServletRequest req) {
		 Result<IPage<Briefing>> result = new Result<IPage<Briefing>>();
		 Map<String, String[]> queryMap=new HashMap(req.getParameterMap());
		 String reporter="";
         String isReporter[]=queryMap.get("isReporter");
         //判断是否为上报模式查询list，若是，将登录人id插入查询条件
         if(isReporter!=null&&isReporter[0].equals("1"))
         {
             LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
             reporter+=sysUser.getId();
         }
		 Page<Briefing> page = new Page<Briefing>(pageNo, pageSize);
		 IPage<Briefing> pageList = briefingService.queryList(page,title,reviewStatus,befType,reporter,pubStartDate,pubEndDate);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }

	/**
	  *   添加
	 * @param briefing
	 * @return
	 */
	@ApiOperation(value="简报-添加", notes="简报-添加")
	@PostMapping(value = "/add")
	public Result<Briefing> add(@RequestBody Briefing briefing) {
		Result<Briefing> result = new Result<Briefing>();
		try {
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			briefing.setReporter(sysUser.getId());
			briefingService.save(briefing);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param briefing
	 * @return
	 */
	@ApiOperation(value="简报-编辑", notes="简报-编辑")
	@PutMapping(value = "/edit")
	public Result<Briefing> edit(@RequestBody Briefing briefing) {
		Result<Briefing> result = new Result<Briefing>();
		Briefing briefingEntity = briefingService.getById(briefing.getId());
		if(briefingEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = briefingService.updateById(briefing);
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
	@AutoLog(value = "简报-通过id删除")
	@ApiOperation(value="简报-通过id删除", notes="简报-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<Briefing> delete(@RequestParam(name="id",required=true) String id) {
		Result<Briefing> result = new Result<Briefing>();
		Briefing briefing = briefingService.getById(id);
		if(briefing==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = briefingService.removeById(id);
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
	@AutoLog(value = "简报-批量删除")
	@ApiOperation(value="简报-批量删除", notes="简报-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Briefing> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Briefing> result = new Result<Briefing>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.briefingService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "简报-通过id查询")
	@ApiOperation(value="简报-通过id查询", notes="简报-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Briefing> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Briefing> result = new Result<Briefing>();
		Briefing briefing = briefingService.getById(id);
		if(briefing==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(briefing);
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
      QueryWrapper<Briefing> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Briefing briefing = JSON.parseObject(deString, Briefing.class);
              queryWrapper = QueryGenerator.initQueryWrapper(briefing, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Briefing> pageList = briefingService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "简报列表");
      mv.addObject(NormalExcelConstants.CLASS, Briefing.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("简报列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Briefing> listBriefings = ExcelImportUtil.importExcel(file.getInputStream(), Briefing.class, params);
              for (Briefing briefingExcel : listBriefings) {
                  briefingService.save(briefingExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listBriefings.size());
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


	 /**
	  * 修改reviewstatus
	  * @return
	  */
	 @GetMapping(value = "/updateStatue")
	 public Result<Briefing> updateState(HttpServletRequest request,@RequestParam(name="status",required=true) String status) {
		 Result<Briefing> result = new Result<Briefing>();
		 String id=request.getParameter("id");
		 Briefing briefing = briefingService.getById(id);
		 if(briefing==null) {
			 result.error500("未找到对应实体");
		 }else {
		 	 briefing.setPublishTime(new Date());
		 	 briefing.setReviewStatus(Integer.parseInt(status));
			 briefingService.updateById(briefing);
			 result.setSuccess(true);
		 }
		 return result;
	 }

}
