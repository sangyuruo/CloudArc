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
 * 报警服务规则表
 * @author youhong
 */
@ApiModel(description = "报警服务规则表 @author youhong")
@Entity
@Table(name = "alarm_rule")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlarmRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规则名称
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "规则名称", required = true)
    @Column(name = "rule_name", length = 200, nullable = false)
    private String ruleName;

    /**
     * 规则编码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "规则编码", required = true)
    @Column(name = "rule_code", length = 64, nullable = false)
    private String ruleCode;

    /**
     * 规则类型
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "规则类型", required = true)
    @Column(name = "rule_type", length = 100, nullable = false)
    private String ruleType;

    /**
     * 紧急度
     */
    @NotNull
    @ApiModelProperty(value = "紧急度", required = true)
    @Column(name = "alarm_level", nullable = false)
    private Integer alarmLevel;

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

    @OneToMany(mappedBy = "alarmRule")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<RuleAttributes> ruleAttributes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public AlarmRule ruleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public AlarmRule ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleType() {
        return ruleType;
    }

    public AlarmRule ruleType(String ruleType) {
        this.ruleType = ruleType;
        return this;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getAlarmLevel() {
        return alarmLevel;
    }

    public AlarmRule alarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
        return this;
    }

    public void setAlarmLevel(Integer alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public Boolean isEnable() {
        return enable;
    }

    public AlarmRule enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public AlarmRule createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public AlarmRule createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public AlarmRule updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public AlarmRule updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public Set<RuleAttributes> getRuleAttributes() {
        return ruleAttributes;
    }

    public AlarmRule ruleAttributes(Set<RuleAttributes> ruleAttributes) {
        this.ruleAttributes = ruleAttributes;
        return this;
    }

    public AlarmRule addRuleAttributes(RuleAttributes ruleAttributes) {
        this.ruleAttributes.add(ruleAttributes);
        ruleAttributes.setAlarmRule(this);
        return this;
    }

    public AlarmRule removeRuleAttributes(RuleAttributes ruleAttributes) {
        this.ruleAttributes.remove(ruleAttributes);
        ruleAttributes.setAlarmRule(null);
        return this;
    }

    public void setRuleAttributes(Set<RuleAttributes> ruleAttributes) {
        this.ruleAttributes = ruleAttributes;
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
        AlarmRule alarmRule = (AlarmRule) o;
        if (alarmRule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), alarmRule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AlarmRule{" +
            "id=" + getId() +
            ", ruleName='" + getRuleName() + "'" +
            ", ruleCode='" + getRuleCode() + "'" +
            ", ruleType='" + getRuleType() + "'" +
            ", alarmLevel='" + getAlarmLevel() + "'" +
            ", enable='" + isEnable() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
