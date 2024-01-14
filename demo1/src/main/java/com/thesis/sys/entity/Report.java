package com.thesis.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangyang
 * @since 2024-01-14
 */
public class Report implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "reportid", type = IdType.AUTO)
    private Integer reportid;

    private Integer userid;

    private Integer fileid;

    private String reportTitle;

    private String reportPath;

    private LocalDateTime generateTime;

    public Integer getReportid() {
        return reportid;
    }

    public void setReportid(Integer reportid) {
        this.reportid = reportid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getFileid() {
        return fileid;
    }

    public void setFileid(Integer fileid) {
        this.fileid = fileid;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getReportPath() {
        return reportPath;
    }

    public void setReportPath(String reportPath) {
        this.reportPath = reportPath;
    }

    public LocalDateTime getGenerateTime() {
        return generateTime;
    }

    public void setGenerateTime(LocalDateTime generateTime) {
        this.generateTime = generateTime;
    }

    @Override
    public String toString() {
        return "Report{" +
            "reportid = " + reportid +
            ", userid = " + userid +
            ", fileid = " + fileid +
            ", reportTitle = " + reportTitle +
            ", reportPath = " + reportPath +
            ", generateTime = " + generateTime +
        "}";
    }
}
