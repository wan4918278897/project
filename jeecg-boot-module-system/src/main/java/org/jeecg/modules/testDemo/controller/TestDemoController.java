//package org.jeecg.modules.testDemo.controller;
//
//import java.util.*;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.common.system.query.QueryGenerator;
//import org.jeecg.common.aspect.annotation.AutoLog;
//import org.jeecg.common.util.oConvertUtils;
//import org.jeecg.modules.testDemo.entity.StandardData;
//import org.jeecg.modules.testDemo.entity.TestDemo;
//import org.jeecg.modules.testDemo.service.IStandardDataService;
//import org.jeecg.modules.testDemo.service.ITestDemoService;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import lombok.extern.slf4j.Slf4j;
//
//import org.jeecgframework.poi.excel.ExcelImportUtil;
//import org.jeecgframework.poi.excel.def.NormalExcelConstants;
//import org.jeecgframework.poi.excel.entity.ExportParams;
//import org.jeecgframework.poi.excel.entity.ImportParams;
//import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//import com.alibaba.fastjson.JSON;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
///**
// * @Description: 22
// * @Author: jeecg-boot
// * @Date: 2019-08-02
// * @Version: V1.0
// */
//@Slf4j
//@Api(tags = "数据配置")
//@RestController
//@RequestMapping("/testDemo/testDemo")
//public class TestDemoController {
//    @Autowired
//    private ITestDemoService testDemoService;
//
//    @Autowired
//    private IStandardDataService standardDataService;
//
//    @Autowired
//    private SolrClient client;
//
//    /**
//     * 分页列表查询
//     *
//     * @param testDemo
//     * @param pageNo
//     * @param pageSize
//     * @param req
//     * @return
//     */
//    @AutoLog(value = "22-分页列表查询")
//    @ApiOperation(value = "22-分页列表查询", notes = "22-分页列表查询")
//    @GetMapping(value = "/list")
//    public Result<IPage<TestDemo>> queryPageList(TestDemo testDemo,
//                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
//                                                 HttpServletRequest req) {
//        Result<IPage<TestDemo>> result = new Result<IPage<TestDemo>>();
//        QueryWrapper<TestDemo> queryWrapper = QueryGenerator.initQueryWrapper(testDemo, req.getParameterMap());
//        Page<TestDemo> page = new Page<TestDemo>(pageNo, pageSize);
//        IPage<TestDemo> pageList = testDemoService.page(page, queryWrapper);
//        result.setSuccess(true);
//        result.setResult(pageList);
//        return result;
//    }
//
//    /**
//     * 添加
//     *
//     * @param testDemo
//     * @return
//     */
//    @AutoLog(value = "22-添加")
//    @ApiOperation(value = "22-添加", notes = "22-添加")
//    @PostMapping(value = "/add")
//    public Result<TestDemo> add(@RequestBody TestDemo testDemo) {
//        Result<TestDemo> result = new Result<TestDemo>();
//        try {
//            testDemoService.save(testDemo);
//            result.success("添加成功！");
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            result.error500("操作失败");
//        }
//        return result;
//    }
//
//    /**
//     * 编辑
//     *
//     * @param testDemo
//     * @return
//     */
//    @AutoLog(value = "22-编辑")
//    @ApiOperation(value = "22-编辑", notes = "22-编辑")
//    @PutMapping(value = "/edit")
//    public Result<TestDemo> edit(@RequestBody TestDemo testDemo) {
//        Result<TestDemo> result = new Result<TestDemo>();
//        TestDemo testDemoEntity = testDemoService.getById(testDemo.getId());
//        if (testDemoEntity == null) {
//            result.error500("未找到对应实体");
//        } else {
//            boolean ok = testDemoService.updateById(testDemo);
//            //TODO 返回false说明什么？
//            if (ok) {
//                result.success("修改成功!");
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * 通过id删除
//     *
//     * @param id
//     * @return
//     */
//    @DeleteMapping(value = "/delete")
//    public Result<TestDemo> delete(@RequestParam(name = "id", required = true) String id) {
//        Result<TestDemo> result = new Result<TestDemo>();
//        TestDemo testDemo = testDemoService.getById(id);
//        if (testDemo == null) {
//            result.error500("未找到对应实体");
//        } else {
//            boolean ok = testDemoService.removeById(id);
//            if (ok) {
//                result.success("删除成功!");
//            }
//        }
//
//        return result;
//    }
//
//    /**
//     * 批量删除
//     *
//     * @param ids
//     * @return
//     */
//    @DeleteMapping(value = "/deleteBatch")
//    public Result<TestDemo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
//        Result<TestDemo> result = new Result<TestDemo>();
//        if (ids == null || "".equals(ids.trim())) {
//            result.error500("参数不识别！");
//        } else {
//            this.testDemoService.removeByIds(Arrays.asList(ids.split(",")));
//            result.success("删除成功!");
//        }
//        return result;
//    }
//
//    /**
//     * 通过id查询
//     *
//     * @param id
//     * @return
//     */
//    @AutoLog(value = "22-通过id查询")
//    @ApiOperation(value = "22-通过id查询", notes = "22-通过id查询")
//    @GetMapping(value = "/queryById")
//    public Result<TestDemo> queryById(@RequestParam(name = "id", required = true) String id) {
//        Result<TestDemo> result = new Result<TestDemo>();
//        TestDemo testDemo = testDemoService.getById(id);
//        if (testDemo == null) {
//            result.error500("未找到对应实体");
//        } else {
//            result.setResult(testDemo);
//            result.setSuccess(true);
//        }
//        return result;
//    }
//
//    /**
//     * 导出excel
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/exportXls")
//    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
//        // Step.1 组装查询条件
//        QueryWrapper<TestDemo> queryWrapper = null;
//        try {
//            String paramsStr = request.getParameter("paramsStr");
//            if (oConvertUtils.isNotEmpty(paramsStr)) {
//                String deString = URLDecoder.decode(paramsStr, "UTF-8");
//                TestDemo testDemo = JSON.parseObject(deString, TestDemo.class);
//                queryWrapper = QueryGenerator.initQueryWrapper(testDemo, request.getParameterMap());
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        //Step.2 AutoPoi 导出Excel
//        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
//        List<TestDemo> pageList = testDemoService.list(queryWrapper);
//        //导出文件名称
//        mv.addObject(NormalExcelConstants.FILE_NAME, "22列表");
//        mv.addObject(NormalExcelConstants.CLASS, TestDemo.class);
//        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("22列表数据", "导出人:Jeecg", "导出信息"));
//        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
//        return mv;
//    }
//
//    /**
//     * 通过excel导入数据
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
//        String url = "http://localhost:8983/solr/yzx1";
//        List<StandardData> standardData = standardDataService.list();
//        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//            MultipartFile file = entity.getValue();// 获取上传文件对象
//            ImportParams params = new ImportParams();
//            params.setTitleRows(2);
//            params.setHeadRows(1);
//            params.setNeedSave(true);
//            try {
//                List<TestDemo> listTestDemos = ExcelImportUtil.importExcel(file.getInputStream(), TestDemo.class, params);
//                for (TestDemo testDemoExcel : listTestDemos) {
//                    System.out.println("2222222");
//                    SolrQuery query = new SolrQuery();
//                    String s = testDemoExcel.getAddress();
//                    query.setQuery( "oldName:" + s + " OR aliasName:" + s);
//                    query.setFields("id,name");
////                    query.set("defType", "edismax");
////                    query.set("mm", "85%");
//                    QueryResponse qr = null;
//                    try {
//                        qr = client.query(query);
//                    } catch (SolrServerException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    SolrDocumentList documentList1 = qr.getResults();
//                    if (documentList1.size() > 1) {
//                        String str = "";
//                        for (SolrDocument solrDocument : documentList1) {
//                            str = str + solrDocument.get("name").toString() + ",";
//                            testDemoExcel.setRemark("疑似项为：" + str);
//                        }
//                        System.err.println("第一种方式：" + str);
//
//                    } else if (documentList1.size() == 1) {
//                        for (SolrDocument solrDocument : documentList1) {
//                            testDemoExcel.setRootId(solrDocument.get("id").toString());
//                            testDemoExcel.setRemark("匹配成功，对应地址为：" + solrDocument.get("name").toString());
//                        }
//                    } else if (documentList1.size() == 0) {
//                        testDemoExcel.setRemark("匹配失败，无相似项");
//                    }
//                    testDemoService.save(testDemoExcel);
//                }
//                return Result.ok("文件导入成功！数据行数:" + listTestDemos.size());
//            } catch (Exception e) {
//                log.error(e.getMessage(), e);
//                return Result.error("文件导入失败:" + e.getMessage());
//            } finally {
//                try {
//                    file.getInputStream().close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return Result.ok("文件导入失败！");
//    }
//
//
//    @PostMapping(value = "/remark")
//    public Result remark(@RequestBody Map<String, String> map) {
//        Result result = new Result();
//        String id = map.get("id");
//        String lat = map.get("lat");
//        String lng = map.get("lng");
//        TestDemo testDemo = testDemoService.getById(id);
//        if (testDemo == null) {
//            result.error500("未找到对应实体");
//        } else {
//            testDemo.setLatitude(Float.valueOf(lat));
//            testDemo.setLongitude(Float.valueOf(lng));
//            testDemoService.updateById(testDemo);
//            result.setSuccess(true);
//            result.success("标记成功");
//        }
//        return result;
//    }
//
//    @GetMapping(value = "/remarkList")
//    public List<TestDemo> remarkList(HttpServletRequest req) {
//        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.isNotNull("latitude");
//        queryWrapper.isNotNull("longitude");
//        List<TestDemo> list = testDemoService.list(queryWrapper);
//        return list;
//    }
//
//    @GetMapping(value = "/indexNameSource")
//    public List<TestDemo> indexNameSource(HttpServletRequest request) {
//        String name = request.getParameter("name");
//        List list = new ArrayList();
//        if (StringUtils.isNotBlank(name)) {
//            list = testDemoService.indexNameSource(name);
//        }
//        return list;
//    }
//
//    @GetMapping(value = "/indexDepartSource")
//    public List<TestDemo> indexDepartSource(HttpServletRequest request) {
//        String departName = request.getParameter("departName");
//        List list = new ArrayList();
//        if (StringUtils.isNotBlank(departName)) {
//            list = testDemoService.indexDepartSource(departName);
//        }
//        return list;
//    }
//
//    @GetMapping(value = "/indexAddressSource")
//    public Result indexAddressSource(HttpServletRequest request) {
//        Result result = new Result();
//        String s = request.getParameter("s");
////        String address ="南六楼";
//        SolrQuery query = new SolrQuery();
//        query.setQuery("name:" + s + "OR oldName:" + s + "OR aliasName:" + s);
////        query.setQuery( "oldName:" + s + " OR aliasName:" + s);
//
////        query.setQuery("name:" + s);
//
//        query.setFields("id,name");
////        query.set("defType", "edismax");
////        query.set("mm", -1);
////        query.set("mm", "2< 40%");
//        QueryResponse qr = null;
//        try {
//            qr = client.query(query);
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//            result.error500("");
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//            result.error500("");
//            return result;
//        }
//        result.setResult(qr.getResults());
//        result.setSuccess(true);
//        return result;
//    }
//}
