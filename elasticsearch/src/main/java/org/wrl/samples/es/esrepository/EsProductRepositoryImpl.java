package org.wrl.samples.es.esrepository;

import com.bluejean.store.web.es.aggregation.AggregationResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.wrl.samples.es.common.DefaultAggregationMapper;
import org.wrl.samples.es.common.MySearchOption;
import org.wrl.samples.es.esentity.EsProductEntity;
import org.wrl.samples.es.form.EsSearchForm;
import org.wrl.samples.es.form.EsSearchResult;

import javax.annotation.Resource;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created with IntelliJ IDEA.
 * 自定义功能接口实现类
 * @author: wangrl
 * @Date: 2014-08-15 15:47
 */
public class EsProductRepositoryImpl implements EsProductRepositoryCustom {
    private static Logger logger = LoggerFactory.getLogger(EsProductRepositoryImpl.class);
    private static final String INDEX_NAME = "product_idx";
    private static final String TYPE_NAME = "product_idx_type";
    private static final String KEYWORD_CN_FIELD = "cnKeywords";
    private static final String KEYWORD_EN_FIELD = "enKeywords";
    private static final String PRODUCTID_FIELD = "productId";
    private static final String PRODUCTNUMBER_FIELD = "productNumber";
    private static final String ONLINETIME_FIELD = "onlineTime";
    private static final String ANALYZER_IK = "ik_smart_syno";
    private static final String NUMBER_PREFIX = "bji";
    /**Linux下空bool值串*/
    private static final String NULL_BOOL_VALUE_LINUX = "{\n  \"bool\" : { }\n}";
    private static final String NULL_BOOL_VALUE_WINDOWS = "{\r\n  \"bool\" : { }\r\n}";
    private static String[] letterArray = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static String[] numberArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    @Resource(name = "elasticsearchTemplate")
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public EsSearchResult queryByPage(PageRequest pageRequest, EsSearchForm queryForm) {
        /**未在关键词库中的搜索词**/
        List<String> nonexistenceWord = Lists.newArrayList();
        List<String> existsWord = Lists.newArrayList();
        List<String> chineseWord = Lists.newArrayList();
        SearchQuery searchQuery = buildSearchQuery(pageRequest, queryForm, nonexistenceWord, existsWord, chineseWord);


        long startTime = System.currentTimeMillis();
        Page<EsProductEntity> pages = elasticsearchTemplate.queryForPage(searchQuery, EsProductEntity.class);
        System.out.println("列表搜索引擎DAO查询时间:" + (System.currentTimeMillis() - startTime));

        return createResult(pages, nonexistenceWord, existsWord, chineseWord);
    }

    /**
     * 构建搜索查询
     * @param pageRequest
     * @param queryForm
     * @param nonexistenceWord
     * @param existsWord
     * @param chineseWord
     * @return
     */
    private SearchQuery buildSearchQuery(PageRequest pageRequest, EsSearchForm queryForm, List<String> nonexistenceWord, List<String> existsWord, List<String> chineseWord) {
        NativeSearchQueryBuilder searchQueryBuilder = getNativeSearchQueryBuilder();

        if (pageRequest != null) {
            //分页信息
            searchQueryBuilder.withPageable(pageRequest);
        }

        /*创建搜索条件*/
        QueryBuilder queryBuilder = createQueryBuilder(queryForm, nonexistenceWord, existsWord, chineseWord);

        //过滤查询
        BoolQueryBuilder filterBuilder = createFilterBuilder(queryForm.getFilterMap(), queryForm);

        searchQueryBuilder.withQuery(queryBuilder).withFilter(filterBuilder);

        //高亮
        //HighlightBuilder.Field highlight_cn = new HighlightBuilder.Field(KEYWORD_CN_FIELD);
        //HighlightBuilder.Field highlight_en = new HighlightBuilder.Field(KEYWORD_EN_FIELD);
        //searchQueryBuilder.withHighlightFields(highlight_cn, highlight_en);

        //添加聚合统计信息
        addAggreations(searchQueryBuilder, queryForm.getAggreations());

        //添加默认排序
        addDefaultSort(pageRequest, searchQueryBuilder);

        SearchQuery searchQuery = searchQueryBuilder.build();
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("query:" + searchQuery.getQuery().toString());
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        return searchQuery;
    }

    private NativeSearchQueryBuilder getNativeSearchQueryBuilder() {
        //获取实体类上注解的索引及type
        Document documentAnnotation = EsProductEntity.class.getAnnotation(Document.class);
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withIndices(documentAnnotation.indexName()).withTypes(documentAnnotation.type());

        //NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder().withIndices(INDEX_NAME).withTypes(TYPE_NAME);
        return searchQueryBuilder;
    }

    private EsSearchResult createResult(Page<EsProductEntity> pages, List<String> nonexistenceWord, List<String> existsWord, List<String> chineseWord) {
        EsSearchResult searchResult = new EsSearchResult();
        searchResult.setPages(pages);

        if (existsWord != null && existsWord.size() > 1) {
            //已匹配的单词，已经去重,仅反序即可
            Collections.reverse(existsWord);
            //对显示的英文首字母大写
            String temp;
            for (int i = 0; i < existsWord.size(); i++) {
                temp = existsWord.get(i);
                temp = StringUtils.capitalize(temp);
                existsWord.set(i, temp);
            }
        }
        if (existsWord != null) {
            existsWord.addAll(chineseWord);
        }
        searchResult.setKeyWord(existsWord);

        if (nonexistenceWord != null && nonexistenceWord.size() > 1) {
            //对于未匹配的单词，进行反序并去重
            List<String> temp = Lists.newArrayList();
            String word;
            for (int i = nonexistenceWord.size() - 1; i >= 0; i--) {
                word = nonexistenceWord.get(i);
                if (!temp.contains(word)) {
                    temp.add(word);
                }
            }
            nonexistenceWord.clear();
            nonexistenceWord.addAll(temp);
        }
        searchResult.setNonWord(nonexistenceWord);
        return searchResult;
    }

    /**
     * 添加默认排序
     * @param pageRequest
     * @param searchQueryBuilder
     */
    private void addDefaultSort(PageRequest pageRequest, NativeSearchQueryBuilder searchQueryBuilder) {
        SortBuilder sortBuilder;
        if (pageRequest != null && pageRequest.getSort() == null) {
            //直接使用elasticsearch排序，排序信息不会包含到Page信息中。与使用PageRequest排序结果相同,pageRequest中的排序信息会返回给结果Page中
//            sortBuilder = SortBuilders.fieldSort("brandSortId").order(SortOrder.ASC);
//            searchQueryBuilder.withSort(sortBuilder);
            sortBuilder = SortBuilders.fieldSort("sortId").order(SortOrder.ASC);
            searchQueryBuilder.withSort(sortBuilder);
            sortBuilder = SortBuilders.fieldSort("sortIdInBrand").order(SortOrder.ASC);
            searchQueryBuilder.withSort(sortBuilder);
            sortBuilder = SortBuilders.fieldSort("sortInBrandProductLevel").order(SortOrder.ASC);
            searchQueryBuilder.withSort(sortBuilder);
            sortBuilder = SortBuilders.fieldSort("sortInProductLevel").order(SortOrder.ASC);
            searchQueryBuilder.withSort(sortBuilder);
            sortBuilder = SortBuilders.fieldSort("_score").order(SortOrder.DESC);
            searchQueryBuilder.withSort(sortBuilder);
        }
    }

    /**
     * 添加聚合统计信息
     * @param searchQueryBuilder
     * @param aggreations
     */
    private void addAggreations(NativeSearchQueryBuilder searchQueryBuilder, Map<String, Set<String>> aggreations) {
        if (aggreations != null && aggreations.size() > 0) {
            for (String field : aggreations.keySet()) {

                TermsBuilder termsBuilder1 = AggregationBuilders.terms(field).showTermDocCountError(true);
                termsBuilder1.field(field);
                String[] values = aggreations.get(field).toArray(new String[0]);
                termsBuilder1.include(values);
                searchQueryBuilder.addAggregation(termsBuilder1);

                //if (StringUtils.isNotBlank(field) && aggreations.get(field) != null) {
                //    for (String value : aggreations.get(field)) {
                //        TermsBuilder termsBuilder1 = AggregationBuilders.terms(field).showTermDocCountError(true);
                //        termsBuilder1.field(field);
                //        termsBuilder1.include(value);
                //
                //        searchQueryBuilder.addAggregation(termsBuilder1);
                //    }
                //}
            }
        }

        //searchQueryBuilder.addAggregation(AggregationBuilders.terms("cnKeywords").showTermDocCountError(true).field("cnKeywords"));

        TermsBuilder termsBuilder = AggregationBuilders.terms("productLevel").showTermDocCountError(true);
        termsBuilder.field("productLevel");
        searchQueryBuilder.addAggregation(termsBuilder);
    }

    /**
     * 自定义评分查询
     * @param queryBuilder
     * @return
     */
    private FunctionScoreQueryBuilder customScoreQuery(QueryBuilder queryBuilder) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = new FunctionScoreQueryBuilder(queryBuilder);
        Map<String, Object> custScore = Maps.newHashMap();
        custScore.put("custBoot", 1.5);
//        String custScript = "_score = _score * doc['status'].value";
        String lang = "groovy";

        //functionScoreQueryBuilder.add(ScoreFunctionBuilders.scriptFunction("calculate-score", lang, custScore));
        return functionScoreQueryBuilder;
    }

    /**
     * 创建过滤条件
     * @param filterMap
     * @param queryForm
     * @return
     */
    private BoolQueryBuilder createFilterBuilder(Map<String, Set<Object>> filterMap, EsSearchForm queryForm) {
        BoolQueryBuilder filterQueryBuilder = QueryBuilders.boolQuery();

        //正式过滤字段
        if (filterMap != null && filterMap.size() > 0) {
            for (String key : filterMap.keySet()) {
                Set<Object> filterValues = filterMap.get(key);
                if (StringUtils.isNotBlank(key) && filterValues != null && filterValues.size() > 0) {
                    TermQueryBuilder termQuery = QueryBuilders.termQuery(key, filterValues);
                    filterQueryBuilder.filter(termQuery);
                    //filterBuilder.must(FilterBuilders.termsFilter(key, filterValues).cache(true));
                }
            }
        }

        //最近上线过滤
        if (1 == queryForm.getLatestOnline()) {
            createLatestOnlineFilter(filterQueryBuilder, queryForm);
        }

        //关键词字段过滤
        /**Q1热词过滤*/
        createKeywordFilter(filterQueryBuilder, queryForm.getQ1());

        return filterQueryBuilder;
    }

    private void createKeywordFilter(BoolQueryBuilder filterBuilder, String keywords) {
        if (StringUtils.isNotBlank(keywords)) {
            for (String keyword : StringUtils.split(keywords, ",")) {
                if (StringUtils.isNotBlank(keyword)) {
                    //filterBuilder.must(FilterBuilders.termFilter(KEYWORD_CN_FIELD, keyword));
                    filterBuilder.filter(QueryBuilders.termQuery(KEYWORD_CN_FIELD, keyword));
                }
            }
        }
    }

    /**
     * 最新上线过滤器
     * @param filterBuilder
     * @param queryForm
     */
    private void createLatestOnlineFilter(BoolQueryBuilder filterBuilder, EsSearchForm queryForm) {
        //最近上线过滤
        //RangeFilterBuilder rangeFilterBuilder = FilterBuilders.rangeFilter(ONLINETIME_FIELD).cache(true)
        //        .from(queryForm.getOnlineTimeFrom())
        //        .to(queryForm.getOnlineTimeTo());
        //filterBuilder.must(rangeFilterBuilder);

        QueryBuilder rangeFilterBuilder = QueryBuilders.rangeQuery(ONLINETIME_FIELD).from(queryForm.getOnlineTimeFrom()).to(queryForm.getOnlineTimeTo());
        filterBuilder.filter(rangeFilterBuilder);
    }


    /**
     * 创建关键词查找条件
     * @param queryForm
     * @param nonexistenceWord
     * @param existsWord
     * @param chineseWord
     * @return
     */
    private QueryBuilder createQueryBuilder(EsSearchForm queryForm, List<String> nonexistenceWord, List<String> existsWord, List<String> chineseWord) {
        BoolQueryBuilder topQueryBuilder = QueryBuilders.boolQuery();

        /**高级搜索-多编号*/
        createProductNoQuery(topQueryBuilder, queryForm.getPn());

        /**摄影师名字搜索*/
        createPhotographerQuery(topQueryBuilder, queryForm.getPhotographer());

        /**品牌名称搜索*/
        createBrandNameQuery(topQueryBuilder, queryForm.getBrandName());

        /**关键词域查询*/
        createKeywordQuery(topQueryBuilder, queryForm.getKeywords(), MySearchOption.SearchLogic.must, nonexistenceWord, existsWord, chineseWord);

        /**排除关键词*/
        if (StringUtils.isNotBlank(queryForm.getEx_q())) {
            //排除关键词暂不做返回处理
            List<String> tempList = Lists.newArrayList();
//            createKeywordQuery(topQueryBuilder, queryForm.getEx_q(), MySearchOption.SearchLogic.mustNot, nonexistenceWord, existsWord, chineseWord);
            createKeywordQuery(topQueryBuilder, queryForm.getEx_q(), MySearchOption.SearchLogic.mustNot, tempList, tempList, tempList);
        }

        if (StringUtils.isBlank(queryForm.getKeywords()) && isBlankBoolQuery(topQueryBuilder)) {
            //如果关键词为空，而且没有其他查询语句时，显示所有
            return QueryBuilders.matchAllQuery();
        } else {
            return topQueryBuilder;
        }
    }

    /**
     * 高级搜索-编号搜索
     * @param topQueryBuilder
     * @param pn
     */
    private void createProductNoQuery(BoolQueryBuilder topQueryBuilder, String pn) {
        if (StringUtils.isNotBlank(pn)) {
            String[] productNoArry = StringUtils.split(pn.trim(), " ,;");

            for (String productNo : productNoArry) {
                if (StringUtils.isNotBlank(productNo)) {
                    if (StringUtils.startsWithIgnoreCase(productNo, NUMBER_PREFIX)) {
                        if (StringUtils.length(productNo) >= 11) {
                            //编号查询
                            TermQueryBuilder termQueryBuilder = termQuery(PRODUCTNUMBER_FIELD, StringUtils.lowerCase(productNo).trim());
                            topQueryBuilder.should(termQueryBuilder);
                        } else {
                            //批次查询
                            PrefixQueryBuilder prefixQuery = QueryBuilders.prefixQuery(PRODUCTNUMBER_FIELD, StringUtils.lowerCase(productNo).trim());
                            topQueryBuilder.must(prefixQuery);
                        }
                    } else {
                        //原始编号搜索
                        topQueryBuilder.should(QueryBuilders.termsQuery("giImageNumber", StringUtils.lowerCase(productNo).trim()));
                        topQueryBuilder.should(QueryBuilders.termsQuery("gicImageNumber", StringUtils.lowerCase(productNo).trim()));
                        topQueryBuilder.should(QueryBuilders.termsQuery("corbisImageNumber", StringUtils.lowerCase(productNo).trim()));
                        topQueryBuilder.should(QueryBuilders.termsQuery("originalNo", StringUtils.lowerCase(productNo).trim()));
                    }
                }
            }
        }
    }

    /**
     * 品牌名字搜索
     * @param topQueryBuilder
     * @param brandName
     */
    private void createBrandNameQuery(BoolQueryBuilder topQueryBuilder, String brandName) {
        if (StringUtils.isNotBlank(brandName)) {
            String[] brandNameArr = StringUtils.split(brandName, ",;");
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

            for (String brand : brandNameArr) {
                if (StringUtils.isNotBlank(brand)) {
                    BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                    TermQueryBuilder termQueryBuilder = termQuery("brandNameEn", StringUtils.lowerCase(brand.trim()));
                    TermQueryBuilder termQueryBuilder2 = termQuery("brandNameCn", StringUtils.lowerCase(brand.trim()));

                    boolQueryBuilder.should(termQueryBuilder);
                    boolQueryBuilder.should(termQueryBuilder2);

                    queryBuilder.should(boolQueryBuilder);
                }
            }

            topQueryBuilder.must(queryBuilder);
        }
    }

    /**
     * 摄像师名字搜索
     * @param topQueryBuilder
     * @param photographer
     */
    private void createPhotographerQuery(BoolQueryBuilder topQueryBuilder, String photographer) {
        if (StringUtils.isNotBlank(photographer)) {
            String[] photographerArr = StringUtils.split(photographer, ",;");
            BoolQueryBuilder queryBuilder = boolQuery();

            for (String pn : photographerArr) {
                if (StringUtils.isNotBlank(pn)) {
                    BoolQueryBuilder boolQueryBuilder = boolQuery();
                    TermQueryBuilder termQueryBuilder = termQuery("photographer", StringUtils.lowerCase(pn.trim()));
                    TermQueryBuilder termQueryBuilder2 = termQuery("realName", StringUtils.lowerCase(pn.trim()));

                    boolQueryBuilder.should(termQueryBuilder);
                    boolQueryBuilder.should(termQueryBuilder2);

                    queryBuilder.should(boolQueryBuilder);
                }
            }

            topQueryBuilder.must(queryBuilder);
        }
    }

    /**
     * 判断是否空的查询
     * @param topQueryBuilder
     * @return
     */
    private boolean isBlankBoolQuery(BoolQueryBuilder topQueryBuilder) {
        String queryStr = topQueryBuilder.toString();
        return StringUtils.equals(NULL_BOOL_VALUE_LINUX, queryStr) || StringUtils.equals(NULL_BOOL_VALUE_WINDOWS, queryStr);
    }

    /**
     * 创建关键词域的查询
     * 对关键词进行分析，找出中英文的关键词，分别进行处理
     * @param topQueryBuilder
     * @param keywords
     * @param searchLogic
     * @param nonexistenceWord
     * @param existsWord
     * @param chineseWord
     */
    private void createKeywordQuery(BoolQueryBuilder topQueryBuilder, String keywords, MySearchOption.SearchLogic searchLogic, List<String> nonexistenceWord,
                                    List<String> existsWord, List<String> chineseWord) {

        /**输入框关键词查找*/
        if (StringUtils.isNotBlank(keywords)) {
            for (String keyword : StringUtils.split(keywords.trim(), ",;，")) {

                if (StringUtils.isNotBlank(keyword)) {
                    keyword = StringUtils.trim(keyword).toLowerCase();

                    boolean isCnKeyword = isCnKeyword(keyword);
                    if (isCnKeyword) {
                        //用中文关键词搜索
                        createCnKeywordQuery(keyword, topQueryBuilder, searchLogic, chineseWord);
                    } else {
                        //英文关键词
                        createEnKeywordQuery(keyword, topQueryBuilder, searchLogic, nonexistenceWord, existsWord);
                    }

                }
            }
        }
    }

    /**
     * 是否包含中文的关键词
     * @param keyword
     * @return 包含中文字
     */
    private boolean isCnKeyword(String keyword) {
        boolean isCn = false;
        char[] chars = keyword.toCharArray();
        for (char c : chars) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
            if(ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                    || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A){
                //目前已知的中文字符UTF-8集合
                isCn = true;
                break;
            }
        }
        return isCn;
    }

    /**
     * 创建中文关键词查询语句
     * @param keyword
     * @param topQueryBuilder
     * @param searchLogic
     * @param chineseWord
     */
    private void createCnKeywordQuery(String keyword, BoolQueryBuilder topQueryBuilder, MySearchOption.SearchLogic searchLogic, List<String> chineseWord) {
        for (String key : StringUtils.split(keyword, " ")) {
            if (StringUtils.isNotBlank(key)) {
                chineseWord.add(key);
                MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery(KEYWORD_CN_FIELD, key).analyzer(ANALYZER_IK).operator(MatchQueryBuilder
                        .Operator.AND);
//                QueryBuilder queryBuilder = QueryBuilders.queryString(key).defaultOperator(QueryStringQueryBuilder.Operator.AND).field(KEYWORD_CN_FIELD).analyzer
//                        (ANALYZER_IK);

                if (searchLogic == MySearchOption.SearchLogic.must) {
                    topQueryBuilder.must(queryBuilder);
                } else if (searchLogic == MySearchOption.SearchLogic.mustNot) {
                    topQueryBuilder.mustNot(queryBuilder);
                }
            }
        }
    }

    /**
     * 创建英文关键词查询语句
     * @param keyword
     * @param topQueryBuilder
     * @param searchLogic
     * @param nonexistenceWord
     * @param existsWord
     */
    private boolean createEnKeywordQuery(String keyword, BoolQueryBuilder topQueryBuilder, MySearchOption.SearchLogic searchLogic, List<String> nonexistenceWord, List<String> existsWord) {
        Set<String> smartKeywords = Sets.newHashSet();
        querySmartKeyword(keyword, smartKeywords, nonexistenceWord, existsWord);

        if (smartKeywords.size() < 1) {
            return false;
        }

        Iterator<String> smartIterator = smartKeywords.iterator();
        String mainKeyword;
        while (smartIterator.hasNext()) {
            mainKeyword = smartIterator.next();

            if (StringUtils.isNotBlank(mainKeyword)) {
                QueryBuilder queryBuilder = termQuery(KEYWORD_EN_FIELD, mainKeyword);

                if (searchLogic == MySearchOption.SearchLogic.must) {
                    topQueryBuilder.must(queryBuilder);
                } else if (searchLogic == MySearchOption.SearchLogic.mustNot) {
                    topQueryBuilder.mustNot(queryBuilder);
                }
            }
        }
        return true;
    }


    /**
     * 找到最大匹配的英文关键词
     * @param keyword
     * @param existsWord
     * @return
     */
    private void querySmartKeyword(String keyword, Set<String> smartKeywords, List<String> nonexistenceWord, List<String> existsWord) {
        while (StringUtils.isNotBlank(keyword)) {
            String throwWord = recursiveKeyword(keyword, smartKeywords, nonexistenceWord, existsWord);
            keyword = StringUtils.removeEnd(keyword, throwWord);
            keyword = StringUtils.trim(keyword);
        }
    }

    private String recursiveKeyword(String keyword, Set<String> smartKeywords, List<String> nonexistenceWord, List<String> existsWord) {
        String beThrowWord = keyword;

        return beThrowWord;
    }

    private void addToExistsWord(String keyword, List<String> wordList) {
        if (!wordList.contains(keyword)) {
            wordList.add(keyword);
        }
    }

    /**
     * 判断是否数字开头
     * @param keyword
     * @return true 以数字开头
     */
    private boolean isNumberStart(String keyword) {
        if (StringUtils.startsWithAny(keyword, numberArray)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否符字母开头
     * @param keyword
     * @return true 包含英文字符,
     */
    private static boolean isLetterStart(String keyword) {
        if (StringUtils.startsWithAny(keyword, letterArray)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<EsProductEntity> queryMoreLikeByPage(PageRequest pageRequest, EsProductEntity productEntity) {
        NativeSearchQueryBuilder searchQueryBuilder = getNativeSearchQueryBuilder().withPageable(pageRequest);

        MoreLikeThisQueryBuilder moreLikeThisQuery = QueryBuilders.moreLikeThisQuery(KEYWORD_CN_FIELD);
        moreLikeThisQuery.ids(String.valueOf(productEntity.getProductId()));
//        moreLikeThisQuery.likeText(productEntity.getCnKeywords());
//        moreLikeThisQuery.percentTermsToMatch(0.3f);
        moreLikeThisQuery.boostTerms(0.3f);
        //默认的minTermFreq最小为2，即一个term最少应该出现2次.
        moreLikeThisQuery.minTermFreq(1);
        moreLikeThisQuery.maxQueryTerms(200);

        moreLikeThisQuery.include(false);
//        MoreLikeThisQueryBuilder.Item item = new MoreLikeThisQueryBuilder.Item(documentAnnotation.indexName(), documentAnnotation.type(), String.valueOf
//                (productEntity.getProductId()));
//        moreLikeThisQuery.addItem(item);
        moreLikeThisQuery.analyzer(ANALYZER_IK);

        searchQueryBuilder.withQuery(moreLikeThisQuery);
        SearchQuery searchQuery = searchQueryBuilder.build();
        System.out.println(searchQuery.getQuery().toString());
        Page<EsProductEntity> result = elasticsearchTemplate.queryForPage(searchQuery, EsProductEntity.class);

        return result;
    }


    @Override
    public EsSearchResult findByOriginalNo(PageRequest pageRequest, EsSearchForm queryForm, EsSearchResult searchResult) {
        NativeSearchQueryBuilder searchQueryBuilder = getNativeSearchQueryBuilder().withPageable(pageRequest);

        /*创建搜索条件*/
        QueryBuilder queryBuilder = createOriginalNoQueryBuilder(queryForm.getKeywords());

        searchQueryBuilder.withQuery(queryBuilder);



        return searchResult;
    }

    private QueryBuilder createOriginalNoQueryBuilder(String keywords) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (StringUtils.isNotBlank(keywords)) {
            String[] keywordArr = StringUtils.split(keywords, ", ");
            boolQueryBuilder.should(QueryBuilders.termsQuery("giImageNumber", keywordArr));
            boolQueryBuilder.should(QueryBuilders.termsQuery("gicImageNumber", keywordArr));
            boolQueryBuilder.should(QueryBuilders.termsQuery("corbisImageNumber", keywordArr));
            boolQueryBuilder.should(QueryBuilders.termsQuery("originalNo", keywordArr));
        }
        return boolQueryBuilder;
    }

    /**
     * 获取图片数量
     * @param queryForm
     * @return
     */
    @Override
    public long countByQueryForm(EsSearchForm queryForm) {
        NativeSearchQueryBuilder searchQueryBuilder = getNativeSearchQueryBuilder();

        return elasticsearchTemplate.count(searchQueryBuilder.build());
    }

    @Override
    public List<AggregationResult> aggregateForSearch(EsSearchForm queryForm) {
        /**未在关键词库中的搜索词**/
        List<String> nonexistenceWord = Lists.newArrayList();
        List<String> existsWord = Lists.newArrayList();
        List<String> chineseWord = Lists.newArrayList();
        SearchQuery searchQuery = buildSearchQuery(null, queryForm, nonexistenceWord, existsWord, chineseWord);

        Aggregations aggregations = elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        List<AggregationResult> termsList = Lists.newArrayList();


        aggregations.asList().stream().filter(aggregation -> aggregation != null).forEach(aggregation -> {
            termsList.add(DefaultAggregationMapper.parse(aggregation));
        });

        return termsList;
    }
}
