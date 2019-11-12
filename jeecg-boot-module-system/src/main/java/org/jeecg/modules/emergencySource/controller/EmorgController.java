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
import org.jeecg.modules.emergencySource.entity.Emorg;
import org.jeecg.modules.emergencySource.service.IEmorgService;
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
 * @Description: 应急管理机构
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Slf4j
@Api(tags="应急管理机构")
@RestController
@RequestMapping("/EmergencySource/emorg")
public class EmorgController {
	@Autowired
	private IEmorgService emorgService;
	
	/**
	  * 分页列表查询
	 * @param emorg
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<Emorg>> queryPageList(Emorg emorg,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Emorg>> result = new Result<IPage<Emorg>>();
		QueryWrapper<Emorg> queryWrapper = QueryGenerator.initQueryWrapper(emorg, req.getParameterMap());
		Page<Emorg> page = new Page<Emorg>(pageNo, pageSize);
		IPage<Emorg> pageList = emorgService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param emorg
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Emorg> add(@RequestBody Emorg emorg) {
		Result<Emorg> result = new Result<Emorg>();
		try {
			emorgService.emorgSave(emorg);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param emorg
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Emorg> edit(@RequestBody Emorg emorg) {
		Result<Emorg> result = new Result<Emorg>();
		Emorg emorgEntity = emorgService.getById(emorg.getId());
		if(emorgEntity==null) {
			result.error500("未找到对应实体");
		}else {
			 emorgService.emorgEdit(emorg);
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "应急管理机构-通过id删除")
	@ApiOperation(value="应急管理机构-通过id删除", notes="应急管理机构-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<Emorg> delete(@RequestParam(name="id",required=true) String id) {
		Result<Emorg> result = new Result<Emorg>();
		Emorg emorg = emorgService.getById(id);
		if(emorg==null) {
			result.error500("未找到对应实体");
		}else {
			emorgService.emorgRemove(id);
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "应急管理机构-批量删除")
	@ApiOperation(value="应急管理机构-批量删除", notes="应急管理机构-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Emorg> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Emorg> result = new Result<Emorg>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.emorgService.emorgRemoveBatch(ids);
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "应急管理机构-通过id查询")
	@ApiOperation(value="应急管理机构-通过id查询", notes="应急管理机构-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Emorg> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Emorg> result = new Result<Emorg>();
		Emorg emorg = emorgService.getById(id);
		if(emorg==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(emorg);
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
      QueryWrapper<Emorg> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Emorg emorg = JSON.parseObject(deString, Emorg.class);
              queryWrapper = QueryGenerator.initQueryWrapper(emorg, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Emorg> pageList = emorgService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "应急管理机构列表");
      mv.addObject(NormalExcelConstants.CLASS, Emorg.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("应急管理机构列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Emorg> listEmorgs = ExcelImportUtil.importExcel(file.getInputStream(), Emorg.class, params);
              for (Emorg emorgExcel : listEmorgs) {
                  emorgService.save(emorgExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listEmorgs.size());
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
