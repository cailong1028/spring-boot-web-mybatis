/******************************************************************************
	File User.java

	Version 1.0
	Date            Author          Changes
	Feb.10,2003     Lishengwang     Created

	Copyright (c), 2003
	all rights reserved
******************************************************************************/

package ess.base.bo;

/**
 * 为了将基础API的设计独立于平台中的上层功能模块，这里定义一个接口用来表示用户登陆后的身份标识。
 * 另外，用户身份标识的另外一个用途就是实现权限验证，所以本接口中只是简单定义最常用的接口，其它
 * 接口用户可以自行扩展。
 */

public interface User {
    
    /**
     * 基本信息：添加机构
     */
    public static final String AUTH_ADD_ORG               = "010101";
    
    /**
     * 基本信息：查看机构
     */
    public static final String AUTH_VIEW_ORG				= "010102";
    
    /**
     * 基本信息：修改机构
     */
    public static final String AUTH_UPDATE_ORG            = "010103";
    
    /**
     * 彻底删除机构
     */
	public static final String AUTH_DELETE_ORG          = "010106";
    /**
     * 基本信息：注销机构
     */
    public static final String AUTH_CANCEL_ORG          = "010104";
    
    
    /**
     * 基本信息：启用机构
     */
    public static final String AUTH_START_ORG           = "010105";
    
    /**
     * 基本信息：添加用户
     */
    public static final String AUTH_ADD_USER            = "010201";

    /**
     * 基本信息：查看用户
     */
    public static final String AUTH_VIEW_USER           = "010202";
    
    /**
     * 基本信息：修改用户
     */
    public static final String AUTH_UPDATE_USER         = "010203";
    
    /**
     * 基本信息：注销用户
     */
    public static final String AUTH_CANCEL_USER         = "010204";
    
    /**
     * 基本信息：启用用户
     */
    public static final String AUTH_START_USER          = "010205"; 

    
    /**
     * 基本信息：添加角色
     */
    public static final String AUTH_ADD_ROLE			  = "010301";

    /**
     * 基本信息：查看角色
     */
    public static final String AUTH_VIEW_ROLE           = "010302";
    
    /**
     * 基本信息：修改角色
     */
    public static final String AUTH_UPDATE_ROLE         = "010303";
    
    /**
     * 基本信息：删除角色
     */
    public static final String AUTH_DELETE_ROLE         = "010304";
    
    /**
     * 基本信息：用户修改密码
     */
    public static final String AUTH_CHANGE_PWD          = "010401";
    
    /**
     * 基本信息：前置机监控
     */
    public static final String AUTH_INSPECT_STORE       = "010501";
    
    /**
     * 基本信息：机构组管理
     */
    public static final String AUTH_GRP_MANAGE   = "010901";
    
	/**
	 * 基本信息：创建主密钥
	 */
	public static final String AUTH_MAIN_KEY   = "011001";
	
	/**
	 * 基本信息：查看商铺列表
	 */
	public static final String AUTH_VIEW_UNIT   = "011101";
	
	/**
	 * 基本信息：添加机构
	 */
	public static final String AUTH_ADD_UNIT               = "011102";

	/**
	 * 基本信息：修改机构
	 */
	public static final String AUTH_UPDATE_UNIT            = "011103";

	/**
	 * 基本信息：注销机构
	 */
	public static final String AUTH_CANCEL_UNIT         = "011104";


	/**
	 * 基本信息：启用机构
	 */
	public static final String AUTH_START_UNIT           = "011105";
	
	/**
	 * 基本信息：启用机构
	 */
	public static final String AUTH_DELETE_UNIT           = "011106"; 
	
	/**
	 * 基本信息：同步会员商户信息
	 */
	public static final String AUTH_UNIT_CONVERT          = "011107";
	
	/**
	 * 基本信息：查看POS列表
	 */
	public static final String AUTH_VIEW_POS   = "011201";
    
	
    //--------------------------------------------------------------
    
    /**
     * 业务报表：分配查询
     */
    public static final String AUTH_DISTR_QUERY         = "020101";
    
    /**
     * 业务报表：分布情况
     */
    public static final String AUTH_SALE_INFO           = "020201";
    
    /**
     * 业务报表：售卡日报
     */
    public static final String AUTH_TRANS_DAY_RPT       = "020301";
    
    /**
     * 业务报表：售卡月报
     */
    public static final String AUTH_TRANS_MONTH_RPT     = "020401";
    
    /**
     * 业务报表：退卡查询
     */
    public static final String AUTH_RETN_QUERY          = "020501";
    
    /**
     * 业务报表：退货开卡日报
     */
    public static final String AUTH_RETN_GOODS_DAY	  = "020701";
    
    /**
     * 业务报表：退货开卡月报
     */
    public static final String AUTH_RETN_GOODS_MONTH	  = "020801";
    
	/**
	 * 业务报表：京客隆卡交易卡查询
	 */
	public static final String AUTH_TRANS_CARD_QUERY    = "020901";

	/**
	 * 业务报表：京客隆卡卡查询
	 */
	public static final String AUTH_CARD_QUERY          = "021001";

    /**
     * 业务报表：京客隆卡服务日志
     */
    public static final String AUTH_SERVICE_LOG         = "021301";
	
	/**
	 * 业务报表：京客隆卡交易卡余额查询
	 */
	public static final String AUTH_TRANS_CARD_BALANCEQUERY    = "021401";
    
    /**
     * 业务报表：京客隆卡售卡机构组汇总查询
     */
    public static final String AUTH_SALE_CARD_QUERY    = "021501";
    
    /**
     * 业务报表：购卡单位查询
     */
    public static final String AUTH_SALE_CARD_CUSTNAME    = "021601";
        
    /**
     * 修改购卡单位信息 
     */
    public static final String AUTH_SALE_CARD_CUSTNAME_UPDATE = "021602";
    
    /**
     * 修改购卡单位信息 -->制卡费
     */
    public static final String AUTH_SALE_CARD_CUSTNAME_UPDATE_MAKECHARGE = "021603";
    
	/**
	 * 业务报表：购卡单位查询
	 */
	public static final String AUTH_CARD_VALIDDATE    = "021701";
    
	/**
	 * 业务报表：购卡优惠情况查询
	 */
	public static final String AUTH_CARD_USER_DISCOUNT    = "021801";
	
	/**
	 * 业务报表：生成购卡优惠信息
	 */
	public static final String AUTH_CARD_USER_DISCOUNT_CREATE    = "021802";
	
	/**
	 * 业务报表：购卡优惠返还情况查询
	 */
	public static final String AUTH_CARD_USER_GIFT    = "021901";
	
	/**
	 * 业务报表：修改购卡优惠返还情况
	 */
	public static final String AUTH_CARD_USER_GIFT_EDIT    = "021902";
	
    //-----------------------------------------------------------------

    /**
     * 消费报表：当日消费
     */
    public static final String AUTH_TODAY_CONSUME       = "030101";
    
    /**
     * 消费报表：消费日报
     */
    public static final String AUTH_DAY_CONSUME         = "030201";
    
    /**
     * 消费报表：租户公司交易汇总表
     */
    public static final String AUTH_CUST_TOTAL       = "030301";
    
    /**
     * 消费报表：交易汇总
     */
    public static final String AUTH_LIST_TOTAL           = "030401"; 
    
    /**
     * 消费报表：交易明细
     */
    public static final String AUTH_DETAIL_TOTAL         = "030501";
    
	/**
	 * 消费报表：交易流水查询
	 */
	public static final String AUTH_ALL_TOTAL           = "030601";    
    
	/**
	 * 消费报表：京客隆卡机构汇总日报
	 */
	public static final String AUTH_GRP_DAY_CONSUME           = "030901";    

	/**
	 * 消费报表：京客隆卡机构汇总月报
	 */
	public static final String AUTH_GRP_MONTH_CONSUME           = "031001";
	
	/**
	 * 消费报表: 交易流水明细
	 */
	public static final String AUTH_ITEM_DETAIL           = "031101";
	
	//库存报表-----------------------------------------------------------------

    /**
     * 库存报表：总部库存
     */
    public static final String AUTH_HQ_STOCK                = "040101";
    
    /**
     * 库存报表：总部入库
     */
    public static final String AUTH_HQ_ENTER_STOCK      = "040201";
    
    /**
     * 库存报表：门店库存
     */
    public static final String AUTH_STORE_STOCK         = "040301";
    
    /**
     * 定额卡库存查询
     */
    public static final String AUTH_RATION_STOCK_QUERY    = "040401";
    
    //---------------------  帐务处理  ---------------------
    
    /**
     * 帐务处理：对帐查询
     */
    public static final String AUTH_ACCT_QUERY          = "050101";

    /**
     * 帐务处理：手工调帐
     */
    public static final String AUTH_ACCT_ADJUST          = "050201";

    /**
     * 帐务处理：已对帐交易明细表
     */
    public static final String AUTH_ACCT_SETTLEMENT      = "050401";
    
    //会员卡业务--------------------------------------------------------
	/**
 	 * 会员卡业务报表：分配查询
 	 */
	public static final String AUTH_DISTR_QUERY_MEMBER     = "060101";
    
	/**
	 * 会员卡业务报表：分布情况
	 */
	public static final String AUTH_SALE_INFO_MEMBER       = "060201";
    
	/**
	 * 会员卡业务报表：售卡日报
	 */
	public static final String AUTH_TRANS_DAY_RPT_MEMBER   = "060301";
	/**
	 * 会员卡业务报表：售卡月报
	 */
	public static final String AUTH_TRANS_MONTH_RPT_MEMBER = "060401";
    
	/**
	 * 会员卡业务报表：退卡查询
	 */
	public static final String AUTH_RETN_QUERY_MEMBER      = "060501";
	/**
	 * 会员卡业务报表：退货开卡日报
	 */
	public static final String AUTH_RETN_GOODS_DAY_MEMBER  = "060701";
	/**
	 * 会员卡业务报表：退货开卡月报
	 */
	public static final String AUTH_RETN_GOODS_MONTH_MEMBER= "060801";
	/**
	 * 会员卡业务报表：会员卡交易卡查询
	 */
	public static final String AUTH_TRANS_CARD_QUERY_MEMBER= "060901";

	/**
	 * 会员卡业务报表：会员卡卡查询
	 */
	public static final String AUTH_CARD_QUERY_MEMBER      = "061001";
    
    /**
     * 会员卡业务报表：会员卡退货充值查询
     */
    public static final String AUTH_RETN_FILL_QUERY_MEMBER = "061101";

	/**
	 * 会员卡业务报表：会员卡服务日志
	 */
	public static final String AUTH_SERVICE_LOG_MEMBER     = "061301";
	
	/**
	 * 会员卡业务报表：会员卡交易卡余额查询
	 */
	public static final String AUTH_TRANS_CARD_BALANCEQUERY_MEMBER= "061401";

	/**
	 * 会员卡业务报表：会员商品下发日志
	 */
	public static final String AUTH_MEMBGOODS_LOG_MEMBER     = "061501";
    
    /**
     * 会员卡业务报表：会员卡充值机构组汇总查询
     */
    public static final String AUTH_CHARGE_QUERY_MEMBER      = "061601"; 

	//会员卡消费报表--------------------------------------------------------

	/**
	 * 消费报表：会员卡当日消费
	 */
	public static final String AUTH_TODAY_CONSUME_MEMBER  = "070101";
    
	/**
	 * 消费报表：会员卡消费日报
	 */
	public static final String AUTH_DAY_CONSUME_MEMBER    = "070201";
    
	/**
	 * 消费报表：会员卡消费月报
	 */
	public static final String AUTH_MONTH_CONSUME_MEMBER  = "070301";
    
	/**
	 * 消费报表：会员卡汇总日报
	 */
	public static final String AUTH_DAY_TOTAL_MEMBER      = "070401"; 
    
	/**
	 * 消费报表：会员卡汇总月报
	 */
	public static final String AUTH_MONTH_TOTAL_MEMBER    = "070501";
    
	/**
	 * 消费报表：会员卡当日积分查询
	 */
	public static final String AUTH_TODAY_SCORE_MEMBER      = "070601";    
	/**
	 * 消费报表：会员卡积分日报查询
	 */
	public static final String AUTH_DAY_SCORE_MEMBER      = "070701";    
	/**
	 * 消费报表：会员卡积分月报查询
	 */
	public static final String AUTH_MONTH_SCORE_MEMBER      = "070801";    
	/**
	 * 消费报表：会员卡汇总查询
	 */
	public static final String AUTH_ALL_TOTAL_MEMBER      = "070901";    
    
	/**
	 * 消费报表：会员卡机构汇总月报
	 */
	public static final String AUTH_GRP_DAY_CONSUME_MEMBER = "071001";    

	/**
	 * 消费报表：会员卡机构汇总月报
	 */
	public static final String AUTH_GRP_MONTH_CONSUME_MEMBER = "071101";    

	//会员卡库存报表-----------------------------------------------------------------

	/**
	 * 库存报表：会员卡总部库存
	 */
	public static final String AUTH_HQ_STOCK_MEMBER       = "080101";
    
	/**
	 * 库存报表：会员卡总部入库
	 */
	public static final String AUTH_HQ_ENTER_STOCK_MEMBER = "080201";
    
	/**
	 * 库存报表：会员卡门店库存
	 */
	public static final String AUTH_STORE_STOCK_MEMBER    = "080301";

	//会员卡客户服务---------------------------------------------------------------
    /**
     * 会员卡客户信息修改
     */
    public static final String AUTH_MODIFY_CUST_INFO_MEMBER = "090101";
    
    /**
     * 会员卡查询密码修改
     */
    public static final String AUTH_MODIFY_QPASSWORD_MEMBER = "090201";
    
    /**
     * 会员卡充值记录查询
     */
    public static final String AUTH_FILL_QUERY_MEMBER       = "090301";
    
    /**
     * 会员卡积分历史查询
     */
    public static final String AUTH_SCORE_HIST_MEMBER       = "090401"; 
    
    /**
     * 会员商品浏览
     */
    public static final String AUTH_VIEW_MEMGOODS_MEMBER    = "090601";
    
    /**
     * 会员卡积分策略定义
     */
    public static final String AUTH_SCORE_STRATEGY_DEFINE_MEMBER = "090701";
    
    /**
     * 会员换购策略管理
     */
    public static final String ATUH_STRATEGY_MANAGE_MEMBER       = "090801";
    
    /**
     * 会员卡积分换购
     */
    public static final String AUTH_SHOP_WITH_SCORE_MEMBER       = "090901";
    
    /**
     * 消费记录补登
     */ 
    public static final String AUTH_PATCHLOG_MEMBER              = "091001";
    
    /**
     * 会员卡客户信息更正
     */
    public static final String AUTH_MODIFY_CERT_INFO_MEMBER      = "091101"; 
    
    /**
     * 会员卡交易流水查询
     */
    public static final String AUTH_TRANSSEQ_MEMBER              = "091501";
    
    /**
     * 会员卡积分查询
     */
    public static final String AUTH_SCORE_MEMBER                 = "091601";
    
    /**
     * 会员商品销售查询
     */
    public static final String AUTH_GOODS_SALE_MEMBER            = "091701";
    
    /**
     * 会员卡信息查询
     */
    public static final String AUTH_CARD_INFO_MEMBER             = "091801";
    
    /**
     * 会员卡帐户查询
     */
    public static final String AUTH_ACCOUNT_MEMBER               = "091901";
    
    /**
     * 会员卡数量查询
     */
    public static final String AUTH_CARDNUM_MEMBER               = "092001";
    
    
    // 换购物料管理-----------------------------------------------------------------
    /**
     *  进货管理
     */
    public static final String AUTH_IMP_GOODS                    = "100101";
    
    /**
     *  总部配发
     */                                                           
    public static final String AUTH_DISTR_GOODS                  = "100201";
    
    /**
     *  总部和门店退货
     */                                                            
    public static final String AUTH_RET_GOODS                    = "100301";
    
    /**
     *  换购策略管理
     */                                                      
    public static final String AUTH_STRATEGY_MNG                 = "100401";
    
    /**
     *  积分换购
     */                                                        
    public static final String AUTH_SCORE_EXCHGE                 = "100501";
    
    /**
     *  库存盘点
     */                                                        
    public static final String AUTH_STOCK_CHK                    = "100601";
    
    /**
     * 年终储值返还
     */
    public static final String AUTH_RET_MONEY                    = "100701";
    
    /**
     * 总部库存查询
     */
    public static final String AUTH_HQSTOCK_QUERY                = "100801";
    
    /**
     * 门店库存查询
     */
    public static final String AUTH_SSTOCK_QUERY                 = "100901"; 
    
    /**
     * 商品基础信息管理
     */
    public static final String AUTH_GOODSINFO_MNG                = "101001";
    
    /**
     * 进货单据查询
     */
    public static final String AUTH_IMPBILLS_QRY                 = "101101";
    
    /**
     * 配发单据查询
     */
    public static final String AUTH_DISTRBILLS_QRY               = "101201";
    
    /**
     * 退货单据查询
     */
    public static final String AUTH_RETBILLS_QRY                 = "101301";
    
    /**
     * 库存盘点差异单据查询
     */
    public static final String AUTH_STOCKCHK_BILLS_QRY           = "101401";
    
    /**
     * 交易信息表备份
     */
    public static final String AUTH_TRANSCARD_BAKUP              = "101501";
    /**
     * 换购记录查询
     */
    public static final String AUTH_QRY_EXCHNG_LOG               = "101601";
    

    //-----------------------------------------------------------------
          
    /**
     * 用户类型：业务员
     */
    public static final String USER_TYPE_OPER   = "1";
    
    /**
     * 用户类型：客户（持卡人）
     */
    public static final String USER_TYPE_CUST   = "2";

	/**
	 * 机构名称。
	 * 创建日期：(2003-3-7 16:46:56)
	 * @return java.lang.String
	 */
	public String getOrgName();

	/**
	 * 用户名称。
	 * 创建日期：(2003-3-7 16:44:38)
	 * @return java.lang.String
	 */
	public String getUserName();

	/**
	 * 是否有此权限。
	 * 创建日期：(2003-3-7 17:14:21)
	 * @return boolean
	 * @param strAuthID java.lang.String
	 */
	public boolean isAllowed(String strAuthID);

	/**
	 * 机构代码。
	 * 创建日期：(2003-3-7 16:46:37)
	 * @return java.lang.String
	 */
	public String getOrgID();

	/**
	 * 机构类型。
	 * 创建日期：(2003-3-7 16:47:19)
	 * @return java.lang.String
	 */
	public String getOrgType();
    
	/**
	 * 用户登录代码。
	 * 创建日期：(2003-3-7 16:46:04)
	 * @return java.lang.String
	 */
	public String getLoginID();

    /**
     * 用户登录密码。
     * 创建日期：(2003-3-7 16:46:04)
     * @return java.lang.String
     */
	public String getPasswd();
    
	/**
	 * 用户是否有效。
	 * 创建日期：(2003-3-7 16:45:17)
	 * @return boolean
	 */
	public String getStatus();
    
	/**
	 * 用户代码。
	 * 创建日期：(2003-3-7 16:43:51)
	 * @return int
	 */
	public String getUserID();

    /**
     * 用户所属岗位。
     * 创建日期：(2003-3-7 16:43:51)
     * @return int[][]
     */
    public String[] getUserRole();

    /**
     * 用户拥有的功能。
     * 创建日期：(2003-3-7 16:43:51)
     * @return String[][]
     */
    public String[][] getUserFuncs();
    
    /**
     * Sets the 上下文参数.
     * @param context The context to set
     */
    public void setContext(String context);
    
    /**
     * 还原上下文参数
     */
    public void restoreContext();
    
    /**
     * 获得用户的类型，用于积分卡
     */
    public String getUserType();
    
    /**
     * 获得积分卡卡号
     */
    public String getScoreCardID();
    
}