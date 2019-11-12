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
import org.jeecg.modules.emergencySource.entity.Rescue;
import org.jeecg.modules.emergencySource.service.IRescueService;
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
 * @Description: 应急救援队伍信息
 * @Author: jeecg-boot
 * @Date:   2019-06-17
 * @Version: V1.0
 */
@Slf4j
@Api(tags="应急救援队伍信息")
@RestController
@RequestMapping("/EmergencySource/rescue")
public class RescueController {
	@Autowired
	private IRescueService rescueService;
	
	/**
	  * 分页列表查询
	 * @param rescue
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<Rescue>> queryPageList(Rescue rescue,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<Rescue>> result = new Result<IPage<Rescue>>();
		QueryWrapper<Rescue> queryWrapper = QueryGenerator.initQueryWrapper(rescue, req.getParameterMap());
		Page<Rescue> page = new Page<Rescue>(pageNo, pageSize);
		IPage<Rescue> pageList = rescueService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param rescue
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<Rescue> add(@RequestBody Rescue rescue) {
		Result<Rescue> result = new Result<Rescue>();
		try {
			rescueService.rescueSave(rescue);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param rescue
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<Rescue> edit(@RequestBody Rescue rescue) {
		Result<Rescue> result = new Result<Rescue>();
		Rescue rescueEntity = rescueService.getById(rescue.getId());
		if(rescueEntity==null) {
			result.error500("未找到对应实体");
		}else {
			 rescueService.rescueEdit(rescue);
		}
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@AutoLog(value = "应急救援队伍信息-通过id删除")
	@ApiOperation(value="应急救援队伍信息-通过id删除", notes="应急救援队伍信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<Rescue> delete(@RequestParam(name="id",required=true) String id) {
		Result<Rescue> result = new Result<Rescue>();
		Rescue rescue = rescueService.getById(id);
		if(rescue==null) {
			result.error500("未找到对应实体");
		}else {
			rescueService.rescueRemove(id);
		}
		
		return result;
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "应急救援队伍信息-批量删除")
	@ApiOperation(value="应急救援队伍信息-批量删除", notes="应急救援队伍信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<Rescue> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<Rescue> result = new Result<Rescue>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.rescueService.rescueRemoveBatch(ids);
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "应急救援队伍信息-通过id查询")
	@ApiOperation(value="应急救援队伍信息-通过id查询", notes="应急救援队伍信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<Rescue> queryById(@RequestParam(name="id",required=true) String id) {
		Result<Rescue> result = new Result<Rescue>();
		Rescue rescue = rescueService.getById(id);
		if(rescue==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(rescue);
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
      QueryWrapper<Rescue> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              Rescue rescue = JSON.parseObject(deString, Rescue.class);
              queryWrapper = QueryGenerator.initQueryWrapper(rescue, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<Rescue> pageList = rescueService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "应急救援队伍信息列表");
      mv.addObject(NormalExcelConstants.CLASS, Rescue.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("应急救援队伍信息列表数据", "导出人:Jeecg", "导出信息"));
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
              List<Rescue> listRescues = ExcelImportUtil.importExcel(file.getInputStream(), Rescue.class, params);
              for (Rescue rescueExcel : listRescues) {
                  rescueService.save(rescueExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listRescues.size());
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
