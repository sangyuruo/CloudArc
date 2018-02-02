package com.emcloud.arc.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 分析引擎表
 * @author youhong
 */
@ApiModel(description = "分析引擎表 @author youhong")
@Entity
@Table(name = "analysis_engine")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AnalysisEngine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 名称
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "名称", required = true)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    /**
     * 分析器
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "分析器", required = true)
    @Column(name = "analysis", length = 64, nullable = false)
    private String analysis;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "jhi_enable", nullable = false)
    private Boolean enable;

    /**
     * 创建人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @ManyToOne
    private MeterCategoryRule meterCategoryRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public AnalysisEngine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnalysis() {
        return analysis;
    }

    public AnalysisEngine analysis(String analysis) {
        this.analysis = analysis;
        return this;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Boolean isEnable() {
        return enable;
    }

    public AnalysisEngine enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AnalysisEngine createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public AnalysisEngine createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AnalysisEngine updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public AnalysisEngine updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public MeterCategoryRule getMeterCategoryRule() {
        return meterCategoryRule;
    }

    public AnalysisEngine meterCategoryRule(MeterCategoryRule meterCategoryRule) {
        this.meterCategoryRule = meterCategoryRule;
        return this;
    }

    public void setMeterCategoryRule(MeterCategoryRule meterCategoryRule) {
        this.meterCategoryRule = meterCategoryRule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnalysisEngine analysisEngine = (AnalysisEngine) o;
        if (analysisEngine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), analysisEngine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnalysisEngine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", analysis='" + getAnalysis() + "'" +
            ", enable='" + isEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
