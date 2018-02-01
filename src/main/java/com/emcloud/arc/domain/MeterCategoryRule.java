package com.emcloud.arc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 设备分类规则表
 * @author youhong
 */
@ApiModel(description = "设备分类规则表 @author youhong")
@Entity
@Table(name = "meter_category_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterCategoryRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备分类编码
     */
    @NotNull
    @ApiModelProperty(value = "设备分类编码", required = true)
    @Column(name = "meter_category_code", nullable = false)
    private Integer meterCategoryCode;

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
     * 创建人
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @NotNull
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @NotNull
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    @OneToMany(mappedBy = "meterCategoryRule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AlarmRule> alarmRules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMeterCategoryCode() {
        return meterCategoryCode;
    }

    public MeterCategoryRule meterCategoryCode(Integer meterCategoryCode) {
        this.meterCategoryCode = meterCategoryCode;
        return this;
    }

    public void setMeterCategoryCode(Integer meterCategoryCode) {
        this.meterCategoryCode = meterCategoryCode;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public MeterCategoryRule ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public MeterCategoryRule ruleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getClassName() {
        return className;
    }

    public MeterCategoryRule className(String className) {
        this.className = className;
        return this;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MeterCategoryRule createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public MeterCategoryRule createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MeterCategoryRule updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public MeterCategoryRule updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Set<AlarmRule> getAlarmRules() {
        return alarmRules;
    }

    public MeterCategoryRule alarmRules(Set<AlarmRule> alarmRules) {
        this.alarmRules = alarmRules;
        return this;
    }

    public MeterCategoryRule addAlarmRule(AlarmRule alarmRule) {
        this.alarmRules.add(alarmRule);
        alarmRule.setMeterCategoryRule(this);
        return this;
    }

    public MeterCategoryRule removeAlarmRule(AlarmRule alarmRule) {
        this.alarmRules.remove(alarmRule);
        alarmRule.setMeterCategoryRule(null);
        return this;
    }

    public void setAlarmRules(Set<AlarmRule> alarmRules) {
        this.alarmRules = alarmRules;
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
        MeterCategoryRule meterCategoryRule = (MeterCategoryRule) o;
        if (meterCategoryRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meterCategoryRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeterCategoryRule{" +
            "id=" + getId() +
            ", meterCategoryCode='" + getMeterCategoryCode() + "'" +
            ", ruleCode='" + getRuleCode() + "'" +
            ", ruleName='" + getRuleName() + "'" +
            ", className='" + getClassName() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
