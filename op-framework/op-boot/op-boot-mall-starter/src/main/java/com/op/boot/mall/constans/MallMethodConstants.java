package com.op.boot.mall.constans;

/**
 * 电商请求方法常量类
 *
 * @author cdrcool
 */
public class MallMethodConstants {
    /**
     * 订单提交
     */
    public static final String ORDER_SUBMIT = "order_submit";

    /**
     * 订单取消
     */
    public static final String ORDER_CANCEL = "order_cancel";

    /**
     * 确认预占订单
     */
    public static final String ORDER_CONFIRM = "order_confirm";
    /**
     * 查询订单详情
     */
    public static final String ORDER_QUERY_DETAIL = "order_query_detail";

    /**
     * 查询订单配送信息
     */
    public static final String ORDER_QUERY_DELIVERY = "order_query_track";

    /**
     * 更新订单的po单号
     */
    public static final String ORDER_UPDATE_PO_NO = "order_update_po_no";

    /**
     * 查询商品运费
     */
    public static final String ORDER_QUERY_FREIGHT = "order_query_freight";

    /**
     * 查询预计送达时间
     */
    public static final String ORDER_QUERY_PREDICT_PROMISE = "order_query_predict_promise";

    /**
     * 确认订单收货
     */
    public static final String ORDER_CONFIRM_RECEIVE = "order_confirm_receive";

    /**
     * 根据订单状态分页查询订单失败
     */
    public static final String ORDER_QUERY_PAGE_BY_STATE = "order_query_page_by_state";

    /**
     * 根据订单状态分页查询订单失败
     */
    public static final String ORDER_QUERY_STATES = "order_query_states";



    /**
     * 发票提交申请
     */
    public static final String INVOICE_SUBMIT_APPLY = "invoice_submit_apply";

    /**
     * 发票取消申请
     */
    public static final String INVOICE_CANCEL_APPLY = "invoice_cancel_apply";

    /**
     * 发票查询详情
     */
    public static final String INVOICE_QUERY_DETAIL = "invoice_query_detail";

    /**
     * 发票查询详情
     */
    public static final String INVOICE_QUERY_DELIVERY = "invoice_query_delivery";



    /**
     * 地址查询下级地址
     */
    public static final String AREA_QUERY_AREAS = "area_query_areas";



    /**
     * 查询商品详情
     */
    public static final String PRODUCT_QUERY_DETAIL = "product_query_detail";

    /**
     * 查询商品详情和分类信息
     */
    public static final String PRODUCT_QUERY_DETAIL_AND_CATEGORY = "product_query_detail_and_category";

    /**
     * 查询商品价格
     */
    public static final String PRODUCT_QUERY_PRICE = "product_query_price";

    /**
     * 查询商品分类
     */
    public static final String PRODUCT_QUERY_CATEGORY = "product_query_category";

    /**
     * 查询商品池商品id
     */
    public static final String PRODUCT_QUERY_ID = "product_query_id";

    /**
     * 查询商品图片
     */
    public static final String PRODUCT_QUERY_IMAGE = "product_query_image";

    /**
     * 查询商品状态
     */
    public static final String PRODUCT_QUERY_STATE = "product_query_state";

    /**
     * 查询商品可售性
     */
    public static final String PRODUCT_QUERY_SALEABILITY = "product_query_saleability";

    /**
     * 查询商品区域可售性
     */
    public static final String PRODUCT_CHECK_AREA_LIMIT = "product_check_area_limit";

    /**
     * 查询商品库存
     */
    public static final String PRODUCT_QUERY_STOCK = "product_query_stock";

    /**
     * 查询商品池信息
     */
    public static final String PRODUCT_QUERY_POOL_INFO = "product_query_pool_info";



    /**
     * 售后单创建
     */
    public static final String AFS_CREATE_APPLY = "afs_create_apply";

    /**
     * 售后单确认
     */
    public static final String AFS_CONFIRM = "afs_confirm";

    /**
     * 取消售后单
     */
    public static final String AFS_CANCEL = "afs_cancel";

    /**
     * 填写售后运单信息
     */
    public static final String AFS_UPDATE_WAYBILL = "afs_update_waybill";

    /**
     * 查询售后单物流信息
     */
    public static final String AFS_QUERY_WAYBILL = "afs_query_waybill";

    /**
     * 查询售后详情
     */
    public static final String AFS_QUERY_DETAIL = "afs_query_detail";

    /**
     * 查询售后单概要
     */
    public static final String AFS_QUERY_OUTLINE = "afs_query_outline";

    /**
     * 查询订单下商品售后权益
     */
    public static final String AFS_QUERY_ATTRIBUTES = "afs_query_attributes";


    /**
     * 查询消息详情
     */
    public static final String MESSAGE_QUERY = "message_query";

    /**
     * 删除消息
     */
    public static final String MESSAGE_DELETE = "message_delete";


    /**
     * 账单拉取对账单
     */
    public static final String BILL_PULL_ACCOUNTS = "bill_pull_accounts";
}
