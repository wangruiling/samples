package com.bluejean.modules.dozer;

import org.dozer.DozerBeanMapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-12-10 17:53
 */
public class DozerTest {

    /**
     * 假设由request得到了页面参数map，想将其转为领域对象–Product
     */
    @Test
    public void testMapToBean() throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("id", 10000L);
        map.put("name", "么么哒");
        map.put("description", "金色外壳");
        map.put("no", "tt");

        DozerBeanMapper mapper = new DozerBeanMapper();

        Product product = mapper.map(map, Product.class);
        assertThat(product.getId()).isEqualTo(10000L);
        assertThat(product.getName()).isEqualTo("么么哒");
        assertThat(product.getDescription()).isEqualTo("金色外壳");
    }

    @Test
    public void testBeanToDTO() throws Exception {
        Product product = new Product();
        product.setId(10001L);
        product.setName("Hero");
        product.setDescription("黑色外壳");

        DozerBeanMapper mapper = new DozerBeanMapper();
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);

        assertThat(productDTO.getProductId()).isEqualTo(10001L);
        assertThat(productDTO.getProductName()).isEqualTo("Hero");
        assertThat(productDTO.getDesc()).isEqualTo("黑色外壳");
    }
}