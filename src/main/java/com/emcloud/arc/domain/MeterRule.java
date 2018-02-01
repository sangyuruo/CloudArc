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
 * 设备规则表
 * @author youhong
 */
@ApiModel(description = "设备规则表 @author youhong")
@Entity
@Table(name = "meter_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备编码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "设备编码", required = true)
    @Column(name = "meter_code", length = 64, nullable = false)
    private String meterCode;

    /**
     * 设备名称
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "设备名称", required = true)
    @Column(name = "meter_name", length = 200, nullable = false)
    private String meterName;

    /**
     * 规则编码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "规则编码", required = true)
    @Column(name = "rule_code", length = 64, nullable = false)
    private String ruleCode;

    /**
     * 规则名称
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "规则名称", required = true)
    @Column(name = "rule_name", length = 200, nullable = false)
    private String ruleName;

    /**
     * 分析器名
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "分析器名", required = true)
    @Column(name = "class_name", length = 64, nullable = false)
    private String className;


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

    /**
     * 设备分类id
     */
    @ApiModelProperty(value = "设备分类id")
    @Column(name = "meter_category", nullable = false)
    private Integer meterCategory;


    public Integer getMeterCategory() {
        return meterCategory;
    }

    public MeterRule meterCategory(Integer meterCategory) {
        this.meterCategory = meterCategory;
        return this;
    }

    public void setMeterCategory(Integer meterCategory) {
        this.meterCategory = meterCategory;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public MeterRule meterCode(String meterCode) {
        this.meterCode = meterCode;
        return this;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getMeterName() {
        return meterName;
    }

    public MeterRule meterName(String meterName) {
        this.meterName = meterName;
        return this;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public MeterRule ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public MeterRule ruleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getClassName() {
        return className;
    }

    public MeterRule className(String className) {
        this.className = className;
        return this;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean isEnable() {
        return enable;
    }

    public MeterRule enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MeterRule createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public MeterRule createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MeterRule updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public MeterRule updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
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
        MeterRule meterRule = (MeterRule) o;
        if (meterRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meterRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeterRule{" +
            "id=" + getId() +
            ", meterCode='" + getMeterCode() + "'" +
            ", meterName='" + getMeterName() + "'" +
            ", ruleCode='" + getRuleCode() + "'" +
            ", ruleName='" + getRuleName() + "'" +
            ", className='" + getClassName() + "'" +
            ", enable='" + isEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
