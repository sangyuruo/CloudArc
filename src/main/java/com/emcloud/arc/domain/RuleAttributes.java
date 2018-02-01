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
 * 规则属性表
 * @author youhong
 */
@ApiModel(description = "规则属性表 @author youhong")
@Entity
@Table(name = "rule_attributes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RuleAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规则编码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "规则编码", required = true)
    @Column(name = "rule_code", length = 64, nullable = false)
    private String ruleCode;

    /**
     * 紧急度
     */
    @NotNull
    @ApiModelProperty(value = "紧急度", required = true)
    @Column(name = "alarm_level", nullable = false)
    private Integer alarmLevel;

    /**
     * 起始值
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "起始值", required = true)
    @Column(name = "start_value", nullable = false)
    private Double startValue;

    /**
     * 结束值
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "结束值", required = true)
    @Column(name = "end_value", nullable = false)
    private Double endValue;

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
    private AlarmRule alarmRule;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public RuleAttributes ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public RuleAttributes alarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
        return this;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Double getStartValue() {
        return startValue;
    }

    public RuleAttributes startValue(Double startValue) {
        this.startValue = startValue;
        return this;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public RuleAttributes endValue(Double endValue) {
        this.endValue = endValue;
        return this;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public RuleAttributes createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public RuleAttributes createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public RuleAttributes updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public RuleAttributes updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public AlarmRule getAlarmRule() {
        return alarmRule;
    }

    public RuleAttributes alarmRule(AlarmRule alarmRule) {
        this.alarmRule = alarmRule;
        return this;
    }

    public void setAlarmRule(AlarmRule alarmRule) {
        this.alarmRule = alarmRule;
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
        RuleAttributes ruleAttributes = (RuleAttributes) o;
        if (ruleAttributes.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ruleAttributes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RuleAttributes{" +
            "id=" + getId() +
            ", ruleCode='" + getRuleCode() + "'" +
            ", alarmLevel='" + getAlarmLevel() + "'" +
            ", startValue='" + getStartValue() + "'" +
            ", endValue='" + getEndValue() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
