package org.jeecg.modules.even.controller;

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
import org.jeecg.modules.even.entity.EvenCallPhone;
import org.jeecg.modules.even.service.IEvenCallPhoneService;
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
 * @Description: 上报电话
 * @Author: jeecg-boot
 * @Date:   2019-06-26
 * @Version: V1.0
 */
@Slf4j
@Api(tags="上报电话")
@RestController
@RequestMapping("/even/evenCallPhone")
public class EvenCallPhoneController {
	@Autowired
	private IEvenCallPhoneService evenCallPhoneService;
	
	/**
	  * 分页列表查询
	 * @param evenCallPhone
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "上报电话-分页列表查询")
	@ApiOperation(value="上报电话-分页列表查询", notes="上报电话-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EvenCallPhone>> queryPageList(EvenCallPhone evenCallPhone,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<EvenCallPhone>> result = new Result<IPage<EvenCallPhone>>();
		QueryWrapper<EvenCallPhone> queryWrapper = QueryGenerator.initQueryWrapper(evenCallPhone, req.getParameterMap());
		Page<EvenCallPhone> page = new Page<EvenCallPhone>(pageNo, pageSize);
		IPage<EvenCallPhone> pageList = evenCallPhoneService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param evenCallPhone
	 * @return
	 */
	@AutoLog(value = "上报电话-添加")
	@ApiOperation(value="上报电话-添加", notes="上报电话-添加")
	@PostMapping(value = "/add")
	public Result<EvenCallPhone> add(@RequestBody EvenCallPhone evenCallPhone) {
		Result<EvenCallPhone> result = new Result<EvenCallPhone>();
		try {
			evenCallPhoneService.save(evenCallPhone);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param evenCallPhone
	 * @return
	 */
	@AutoLog(value = "上报电话-编辑")
	@ApiOperation(value="上报电话-编辑", notes="上报电话-编辑")
	@PutMapping(value = "/edit")
	public Result<EvenCallPhone> edit(@RequestBody EvenCallPhone evenCallPhone) {
		Result<EvenCallPhone> result = new Result<EvenCallPhone>();
		EvenCallPhone evenCallPhoneEntity = evenCallPhoneService.getById(evenCallPhone.getId());
		if(evenCallPhoneEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = evenCallPhoneService.updateById(evenCallPhone);
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
	@AutoLog(value = "上报电话-通过id删除")
	@ApiOperation(value="上报电话-通过id删除", notes="上报电话-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<EvenCallPhone> delete(@RequestParam(name="id",required=true) String id) {
		Result<EvenCallPhone> result = new Result<EvenCallPhone>();
		EvenCallPhone evenCallPhone = evenCallPhoneService.getById(id);
		if(evenCallPhone==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = evenCallPhoneService.removeById(id);
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
	@AutoLog(value = "上报电话-批量删除")
	@ApiOperation(value="上报电话-批量删除", notes="上报电话-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<EvenCallPhone> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<EvenCallPhone> result = new Result<EvenCallPhone>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.evenCallPhoneService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@AutoLog(value = "上报电话-通过id查询")
	@ApiOperation(value="上报电话-通过id查询", notes="上报电话-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<EvenCallPhone> queryById(@RequestParam(name="id",required=true) String id) {
		Result<EvenCallPhone> result = new Result<EvenCallPhone>();
		EvenCallPhone evenCallPhone = evenCallPhoneService.getById(id);
		if(evenCallPhone==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(evenCallPhone);
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
      QueryWrapper<EvenCallPhone> queryWrapper = null;
      try {
          String paramsStr = request.getParameter("paramsStr");
          if (oConvertUtils.isNotEmpty(paramsStr)) {
              String deString = URLDecoder.decode(paramsStr, "UTF-8");
              EvenCallPhone evenCallPhone = JSON.parseObject(deString, EvenCallPhone.class);
              queryWrapper = QueryGenerator.initQueryWrapper(evenCallPhone, request.getParameterMap());
          }
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      }

      //Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      List<EvenCallPhone> pageList = evenCallPhoneService.list(queryWrapper);
      //导出文件名称
      mv.addObject(NormalExcelConstants.FILE_NAME, "上报电话列表");
      mv.addObject(NormalExcelConstants.CLASS, EvenCallPhone.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("上报电话列表数据", "导出人:Jeecg", "导出信息"));
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
              List<EvenCallPhone> listEvenCallPhones = ExcelImportUtil.importExcel(file.getInputStream(), EvenCallPhone.class, params);
              for (EvenCallPhone evenCallPhoneExcel : listEvenCallPhones) {
                  evenCallPhoneService.save(evenCallPhoneExcel);
              }
              return Result.ok("文件导入成功！数据行数:" + listEvenCallPhones.size());
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
