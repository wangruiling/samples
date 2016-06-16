package org.wrl.samples.es.esservice.impl;

import com.bluejean.store.web.es.aggregation.AggregationResult;
import org.junit.Test;
import org.wrl.samples.es.BaseServiceTest;
import org.wrl.samples.es.esservice.IEsSearchService;
import org.wrl.samples.es.form.EsSearchForm;
import org.wrl.samples.es.utils.JsonMapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class EsSearchServiceImplTest extends BaseServiceTest{
    @Resource(name = "esSearchService")
    private IEsSearchService esSearchService;

    @Test
    public void testFindByProductIds() throws Exception {

    }

    @Test
    public void testFindLatestOnlineNumber() throws Exception {

    }

    @Test
    public void testBaseSearch() throws Exception {
        EsSearchForm queryForm = new EsSearchForm();
        queryForm.setPageSize(5);
//        queryForm.setMltpid(794);
//        queryForm.setMltpn("bji70803601");
//        queryForm.setQ1("");
        queryForm.setKeywords("商务");
//        queryForm.setAttributeSetId("2,1");
//        queryForm.setProductLevel(2);
//        queryForm.setHolderId("39");
//        queryForm.setPhotographer("Lane Oatey");
//        queryForm.setBrandName("blue jean images");
//        queryForm.setBrandId("39");
//        queryForm.setPn("bji04280005 bji04280049");
//        queryForm.setSaleFormat("RF,RM");
//        queryForm.setLatestOnline(1);
//        queryForm.setOnlineTimeFrom(LocalDateTime.of(2015, 4, 12, 22, 22, 22));
//        queryForm.setOnlineTimeTo(LocalDateTime.of(2015, 4, 22, 22, 22, 22));
//        EsSearchResult searchResult = esSearchService.baseSearch(queryForm);
        List<AggregationResult> searchResult = esSearchService.aggregatesForSearch(queryForm);
        //for (Terms terms : searchResult) {
        //    String name = terms.getName();
        //    Map<String, Object> metaData = terms.getMetaData();
        //    long docCountError = terms.getDocCountError();
        //    long otherDocCounts = terms.getSumOfOtherDocCounts();
        //    Terms.Bucket bucket = terms.getBucketByKey(name);
        //    System.out.println(JsonMapper.nonEmptyMapper().toJson(terms));
        //}
        System.out.println("结果:");
        System.out.println(JsonMapper.nonEmptyMapper().toJson(searchResult));
    }

    @Test
    public void testFindProductIds() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {

    }

    @Test
    public void testFindByProductNumber() throws Exception {

    }

    @Test
    public void testFindByAutocomplete() throws Exception {

    }
}