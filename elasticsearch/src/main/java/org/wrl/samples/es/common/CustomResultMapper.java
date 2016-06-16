package org.wrl.samples.es.common;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.ElasticsearchException;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.ScriptedField;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentProperty;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.context.MappingContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * 扩展spring-data-elasticsearch的DefaultResultMapper类，
 * 因为目前DefaultResultMapper类的结果集处理时没有提供新的聚合类信息Aggregations，所以此处进行了扩展
 * @author: wangrl
 * @Date: 2014-09-04 10:33
 */
public class CustomResultMapper extends DefaultResultMapper {
    private MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext;

    public CustomResultMapper() {
        super();
    }

    public CustomResultMapper(MappingContext<? extends ElasticsearchPersistentEntity<?>, ElasticsearchPersistentProperty> mappingContext) {
        super(mappingContext);
        this.mappingContext = mappingContext;
    }

    @Override
    public <T> Page<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        /************** DefaultResultMapper中源码，此处直接使用 2014/9/4 By:wangrl start ****************/
        long totalHits = response.getHits().totalHits();
        List<T> results = new ArrayList<>();
        for (SearchHit hit : response.getHits()) {
            if (hit != null) {
                T result = null;
                if (StringUtils.isNotBlank(hit.sourceAsString())) {
                    result = mapEntity(hit.sourceAsString(), clazz);
                } else {
                    result = mapEntity(hit.getFields().values(), clazz);
                }
                setPersistentEntityId(result, hit.getId(), clazz);
                populateScriptFields(result, hit);
                results.add(result);
            }
        }
        //return new PageImpl<>(results, pageable, totalHits);
        /************** DefaultResultMapper中源码，此处直接使用 2014/9/4 By:wangrl end    **************/

        /************** 此处扩展结果集中的统计聚合信息 2014/9/4 By:wangrl start ****************/
        Aggregations aggregations = response.getAggregations();
        List<Terms> aggregationTerms = Lists.newArrayList();
        if (aggregations != null && aggregations.asList() != null && aggregations.asList().size() > 0) {
            Terms terms = null;
            for (Aggregation aggregation : aggregations.asList()) {
                if (aggregation != null) {
                    terms = aggregations.get(aggregation.getName());
                    if (terms != null) {
                        aggregationTerms.add(terms);
                    }
                }
            }
        }
        /************** 此处扩展结果集中的统计聚合信息 2014/9/4 By:wangrl end ****************/
        //return new PageImpl<>(results, pageable, totalHits);
        return new AggregatedPageImpl<>(results, pageable, totalHits, aggregationTerms);
    }

    /**
     * 抽取高亮字
     * 弯腰,会议室,<em>商务</em>,商务人士
     * @param hit
     * @param highlightText
     */
    private void extractHighlight(SearchHit hit, Set<String> highlightText) {
        Map<String, HighlightField> highlightFieldMap = hit.getHighlightFields();
        if (highlightFieldMap != null) {
            Iterator<String> iterator = highlightFieldMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                HighlightField highlightField = highlightFieldMap.get(key);
                if (highlightField != null && highlightField.fragments() != null && highlightField.fragments().length > 0) {
                    Text[] texts = highlightField.fragments();
                    Text text = texts[0];
                    String s = text.string();

                }
            }
        }
    }

    /**
     * @see DefaultResultMapper#mapEntity(Collection, Class)
     * @param values
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T mapEntity(Collection<SearchHitField> values, Class<T> clazz) {
        return mapEntity(buildJSONFromFields(values), clazz);
    }

    /**
     * @see DefaultResultMapper#buildJSONFromFields(Collection)
     * @param values
     * @return
     */
    private String buildJSONFromFields(Collection<SearchHitField> values) {
        JsonFactory nodeFactory = new JsonFactory();
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            JsonGenerator generator = nodeFactory.createGenerator(stream, JsonEncoding.UTF8);
            generator.writeStartObject();
            for (SearchHitField value : values) {
                if (value.getValues().size() > 1) {
                    generator.writeArrayFieldStart(value.getName());
                    for (Object val : value.getValues()) {
                        generator.writeObject(val);
                    }
                    generator.writeEndArray();
                } else {
                    generator.writeObjectField(value.getName(), value.getValue());
                }
            }
            generator.writeEndObject();
            generator.flush();
            return new String(stream.toByteArray(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            return null;
        }
    }

    private <T> void populateScriptFields(T result, SearchHit hit) {
        if (hit.getFields() != null && !hit.getFields().isEmpty() && result != null) {
            for (java.lang.reflect.Field field : result.getClass().getDeclaredFields()) {
                ScriptedField scriptedField = field.getAnnotation(ScriptedField.class);
                if (scriptedField != null) {
                    String name = scriptedField.name().isEmpty() ? field.getName() : scriptedField.name();
                    SearchHitField searchHitField = hit.getFields().get(name);
                    if (searchHitField != null) {
                        field.setAccessible(true);
                        try {
                            field.set(result, searchHitField.getValue());
                        } catch (IllegalArgumentException e) {
                            throw new ElasticsearchException("failed to set scripted field: " + name + " with value: "
                                    + searchHitField.getValue(), e);
                        } catch (IllegalAccessException e) {
                            throw new ElasticsearchException("failed to access scripted field: " + name, e);
                        }
                    }
                }
            }
        }
    }

    private <T> void setPersistentEntityId(T result, String id, Class<T> clazz) {
        if (mappingContext != null && clazz.isAnnotationPresent(Document.class)) {
            PersistentProperty<ElasticsearchPersistentProperty> idProperty = mappingContext.getPersistentEntity(clazz).getIdProperty();
            // Only deal with String because ES generated Ids are strings !
            if (idProperty != null && idProperty.getType().isAssignableFrom(String.class)) {
                Method setter = idProperty.getSetter();
                if (setter != null) {
                    try {
                        setter.invoke(result, id);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }
}
