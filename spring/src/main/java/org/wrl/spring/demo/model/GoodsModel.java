package org.wrl.spring.demo.model;

/**
 * Created by wrl on 2015/12/10.
 */

import javax.persistence.*;

/**
 * 商品表
 * @author Zhang Kaitao
 *
 */
@Entity
@Table(name = "tb_goods")
public class GoodsModel implements java.io.Serializable {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10)
    private int id;

    /** 商品名称 */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /** 商品简介 */
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    /** 原需积分 */
    @Column(name = "original_point", nullable = false, length = 10)
    private int originalPoint;

    /** 现需积分 */
    @Column(name = "now_point", nullable = false, length = 10)
    private int nowPoint;


    /** 是否发布，只有发布的在前台显示 */
    @Column(name = "published", nullable = false)
    private boolean published;

    /** 是否删除，商品不会被物理删除的 */
    @Column(name = "is_delete", nullable = false)
    private boolean deleted;

    /** 版本 */
    @Version
    @Column(name = "version", nullable = false, length = 10)
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOriginalPoint() {
        return originalPoint;
    }

    public void setOriginalPoint(int originalPoint) {
        this.originalPoint = originalPoint;
    }

    public int getNowPoint() {
        return nowPoint;
    }

    public void setNowPoint(int nowPoint) {
        this.nowPoint = nowPoint;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GoodsModel other = (GoodsModel) obj;
        if (id != other.id)
            return false;
        return true;
    }

}

