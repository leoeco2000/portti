CREATE TABLE `order_trade_record`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `customer_id` int(11)        DEFAULT NULL COMMENT '客户id',
    `order_id`    int(11)        DEFAULT NULL COMMENT '订单id',
    `price`       decimal(15, 2) DEFAULT NULL COMMENT '收款金额',
    `status`      int(11)        DEFAULT '0' COMMENT '状态（0=未支付，1=已支付）',
    `create_time` datetime       DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime       DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='订单交易记录';