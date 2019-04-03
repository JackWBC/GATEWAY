package com.baicheng.gatewaydynamicdemo.pos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author baicheng
 * @description
 * @create 2019-04-02 11:01
 */
@TableName("gw_filter")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GwFilterPO implements Serializable {
    private static final long serialVersionUID = -3392075303053016503L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String routeId;

    private String filterName;

    private String filterText;

    @TableLogic
    private Byte isDeleted;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}
