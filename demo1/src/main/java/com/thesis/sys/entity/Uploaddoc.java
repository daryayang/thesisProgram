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
public class Uploaddoc implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userid;

    @TableId(value = "fileid", type = IdType.AUTO)
    private Integer fileid;

    private String fileName;

    private String filePath;

    private LocalDateTime uploadTime;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "Uploaddoc{" +
            "userid = " + userid +
            ", fileid = " + fileid +
            ", fileName = " + fileName +
            ", filePath = " + filePath +
            ", uploadTime = " + uploadTime +
        "}";
    }
}
