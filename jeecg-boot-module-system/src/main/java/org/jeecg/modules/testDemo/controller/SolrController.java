//package org.jeecg.modules.testDemo.controller;
//
//import org.apache.solr.client.solrj.SolrClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.CloudSolrClient;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.client.solrj.response.UpdateResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.apache.solr.common.params.MapSolrParams;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
///**
// * @author yzx
// */
//@RestController
//@RequestMapping("/solr/solr")
//public class SolrController {
//
//    @Autowired
//    private SolrClient client;
//
//    /**
//     * 新增/修改 索引
//     * 当 id 存在的时候, 此方法是修改(当然, 我这里用的 uuid, 不会存在的), 如果 id 不存在, 则是新增
//     *
//     * @return
//     */
//    @RequestMapping("/add")
//    public String add() {
//        try {
//            SolrInputDocument doc = new SolrInputDocument();
//            doc.setField("id", "zczczcsa");
//
//            /* 如果spring.data.solr.host 里面配置到 core了, 那么这里就不需要传 collection1 这个参数
//             * 下面都是一样的
//             */
//
//            client.add("yzx1", doc);
//            //client.commit();
//            client.commit("yzx1");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    @RequestMapping("/search")
//    public Map<String, Map<String, List<String>>> search(){
//
//        SolrQuery query = new SolrQuery();
//        String s="学院";
//        query.setQuery("name:"+s+" OR oldName:"+s+" OR aliasName:"+s);
//        query.setFields("id,name");
//        query.set("defType","edismax");
//        query.set("mm",2);
//
////        Map<String, String> queryParamMap = new HashMap<String, String>();
////        //查询id为1或者为2的
////        String s="南楼";
////        String str="";
////        queryParamMap.put("q","name:"+s+" OR "+"oldName:"+s+" OR "+"aliasName:"+s );
//        // "*:*"表示查询所有索引
////        //按照id进行排序
////        queryParamMap.put("sort","id asc");
////        //显示前10条数据
////        queryParamMap.put("start","0");
////        queryParamMap.put("rows","10");
////        //需要查询显示的字段，
////        queryParamMap.put("fl","name");
////        MapSolrParams mapSolrParams = new MapSolrParams(queryParamMap);
//        QueryResponse response1 = null;
//        try {
//            response1 = client.query(query);
//        } catch (SolrServerException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SolrDocumentList documentList1 = response1.getResults();
//        for (SolrDocument solrDocument : documentList1) {
//            System.err.println("第一种方式：" +solrDocument);
//        }
//
//
//        System.out.println("");
//
//        return null;
//
//    }
//
//
//
//
//
//
//
//
//}