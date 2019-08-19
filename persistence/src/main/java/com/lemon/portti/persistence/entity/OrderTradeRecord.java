package com.lemon.portti.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单交易记录
 * </p>
 *
 * @author lemon
 * @since 2019-08-18
 */
@Data
@Accessors(chain = true)
@ToString
public class OrderTradeRecord implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 收款金额
     */
    private BigDecimal price;

    /**
     * 状态（0=未支付，1=已支付）
     */
    private Integer status = 0;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
