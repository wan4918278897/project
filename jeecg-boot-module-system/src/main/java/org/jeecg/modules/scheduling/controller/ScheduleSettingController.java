package org.jeecg.modules.scheduling.controller;

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
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.scheduling.entity.ScheduleSetting;
import org.jeecg.modules.scheduling.service.IScheduleSettingService;
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
 * @Description: 1
 * @Author: jeecg-boot
 * @Date:   2019-06-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags="1")
@RestController
@RequestMapping("/scheduling/scheduleSetting")
public class ScheduleSettingController {
	@Autowired
	private IScheduleSettingService scheduleSettingService;
	
	/**
	  * 分页列表查询
	 * @param scheduleSetting
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "1-分页列表查询")
	@ApiOperation(value="1-分页列表查询", notes="1-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ScheduleSetting>> queryPageList(ScheduleSetting scheduleSetting,
                                                        @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                        @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                        HttpServletRequest req) {
		Result<IPage<ScheduleSetting>> result = new Result<IPage<ScheduleSetting>>();
		Page<ScheduleSetting> page = new Page<ScheduleSetting>(pageNo, pageSize);
		IPage<ScheduleSetting> pageList = scheduleSettingService.scheduleSettingList(page);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param scheduleSetting
	 * @return
	 */
	@AutoLog(value = "1-添加")
	@ApiOperation(value="1-添加", notes="1-添加")
	@PostMapping(value = "/add")
	public Result<ScheduleSetting> add(@RequestBody ScheduleSetting scheduleSetting) {
		Result<ScheduleSetting> result = new Result<ScheduleSetting>();
		try {
			scheduleSettingService.settingSave(scheduleSetting);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param scheduleSetting
	 * @return
	 */
	@AutoLog(value = "1-编辑")
	@ApiOperation(value="1-编辑", notes="1-编辑")
	@PutMapping(value = "/edit")
	public Result<ScheduleSetting> edit(@RequestBody ScheduleSetting scheduleSetting) {
		Result<ScheduleSetting> result = new Result<ScheduleSetting>();
		ScheduleSetting scheduleSettingEntity = scheduleSettingService.getById(scheduleSetting.getId());
		if(scheduleSettingEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = scheduleSettingService.updateById(scheduleSetting);
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
	@AutoLog(value = "1-通过id删除")
	@ApiOperation(value="1-通过id删除", notes="1-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<ScheduleSetting> delete(@RequestParam(name="id",required=true) String id) {
		Result<ScheduleSetting> result = new Result<ScheduleSetting>();
		ScheduleSetting scheduleSetting = scheduleSettingService.getById(id);
		if(scheduleSetting==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = scheduleSettingService.removeById(id);
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
	@AutoLog(value = "1-批量删除")
	@ApiOperation(value="1-批量删除", notes="1-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<ScheduleSetting> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<ScheduleSetting> result = new Result<ScheduleSetting>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.scheduleSettingService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}

}
