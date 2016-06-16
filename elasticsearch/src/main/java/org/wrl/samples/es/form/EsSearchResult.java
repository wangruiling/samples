package org.wrl.samples.es.form;

import org.springframework.data.domain.Page;
import org.wrl.samples.es.esentity.EsProductEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: wangrl
 * @Date: 2015-01-23 14:42
 */
public class EsSearchResult implements Serializable {
    private Page<EsProductEntity> pages;
    private List<String> nonWord;
    private List<String> keyWord;
    private EsProductEntity fixedItem;

    public Page<EsProductEntity> getPages() {
        return pages;
    }

    public void setPages(Page<EsProductEntity> pages) {
        this.pages = pages;
    }

    public List<String> getNonWord() {
        return nonWord;
    }

    public void setNonWord(List<String> nonWord) {
        this.nonWord = nonWord;
    }

    public List<String> getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(List<String> keyWord) {
        this.keyWord = keyWord;
    }

    public EsProductEntity getFixedItem() {
        return fixedItem;
    }

    public void setFixedItem(EsProductEntity fixedItem) {
        this.fixedItem = fixedItem;
    }
}
