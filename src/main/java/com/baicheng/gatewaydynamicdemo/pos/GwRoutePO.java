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
 * @create 2019-04-02 10:50
 */
@TableName(value = "gw_route")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GwRoutePO implements Serializable {
    private static final long serialVersionUID = -1854959630761245891L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String routeId;

    private String routeUri;

    private Integer routeOrder;

    @TableLogic
    private Byte isDeleted;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;
}
