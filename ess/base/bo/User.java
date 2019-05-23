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
 * Ϊ�˽�����API����ƶ�����ƽ̨�е��ϲ㹦��ģ�飬���ﶨ��һ���ӿ�������ʾ�û���½�����ݱ�ʶ��
 * ���⣬�û���ݱ�ʶ������һ����;����ʵ��Ȩ����֤�����Ա��ӿ���ֻ�Ǽ򵥶�����õĽӿڣ�����
 * �ӿ��û�����������չ��
 */

public interface User {
    
    /**
     * ������Ϣ����ӻ���
     */
    public static final String AUTH_ADD_ORG               = "010101";
    
    /**
     * ������Ϣ���鿴����
     */
    public static final String AUTH_VIEW_ORG				= "010102";
    
    /**
     * ������Ϣ���޸Ļ���
     */
    public static final String AUTH_UPDATE_ORG            = "010103";
    
    /**
     * ����ɾ������
     */
	public static final String AUTH_DELETE_ORG          = "010106";
    /**
     * ������Ϣ��ע������
     */
    public static final String AUTH_CANCEL_ORG          = "010104";
    
    
    /**
     * ������Ϣ�����û���
     */
    public static final String AUTH_START_ORG           = "010105";
    
    /**
     * ������Ϣ������û�
     */
    public static final String AUTH_ADD_USER            = "010201";

    /**
     * ������Ϣ���鿴�û�
     */
    public static final String AUTH_VIEW_USER           = "010202";
    
    /**
     * ������Ϣ���޸��û�
     */
    public static final String AUTH_UPDATE_USER         = "010203";
    
    /**
     * ������Ϣ��ע���û�
     */
    public static final String AUTH_CANCEL_USER         = "010204";
    
    /**
     * ������Ϣ�������û�
     */
    public static final String AUTH_START_USER          = "010205"; 

    
    /**
     * ������Ϣ����ӽ�ɫ
     */
    public static final String AUTH_ADD_ROLE			  = "010301";

    /**
     * ������Ϣ���鿴��ɫ
     */
    public static final String AUTH_VIEW_ROLE           = "010302";
    
    /**
     * ������Ϣ���޸Ľ�ɫ
     */
    public static final String AUTH_UPDATE_ROLE         = "010303";
    
    /**
     * ������Ϣ��ɾ����ɫ
     */
    public static final String AUTH_DELETE_ROLE         = "010304";
    
    /**
     * ������Ϣ���û��޸�����
     */
    public static final String AUTH_CHANGE_PWD          = "010401";
    
    /**
     * ������Ϣ��ǰ�û����
     */
    public static final String AUTH_INSPECT_STORE       = "010501";
    
    /**
     * ������Ϣ�����������
     */
    public static final String AUTH_GRP_MANAGE   = "010901";
    
	/**
	 * ������Ϣ����������Կ
	 */
	public static final String AUTH_MAIN_KEY   = "011001";
	
	/**
	 * ������Ϣ���鿴�����б�
	 */
	public static final String AUTH_VIEW_UNIT   = "011101";
	
	/**
	 * ������Ϣ����ӻ���
	 */
	public static final String AUTH_ADD_UNIT               = "011102";

	/**
	 * ������Ϣ���޸Ļ���
	 */
	public static final String AUTH_UPDATE_UNIT            = "011103";

	/**
	 * ������Ϣ��ע������
	 */
	public static final String AUTH_CANCEL_UNIT         = "011104";


	/**
	 * ������Ϣ�����û���
	 */
	public static final String AUTH_START_UNIT           = "011105";
	
	/**
	 * ������Ϣ�����û���
	 */
	public static final String AUTH_DELETE_UNIT           = "011106"; 
	
	/**
	 * ������Ϣ��ͬ����Ա�̻���Ϣ
	 */
	public static final String AUTH_UNIT_CONVERT          = "011107";
	
	/**
	 * ������Ϣ���鿴POS�б�
	 */
	public static final String AUTH_VIEW_POS   = "011201";
    
	
    //--------------------------------------------------------------
    
    /**
     * ҵ�񱨱������ѯ
     */
    public static final String AUTH_DISTR_QUERY         = "020101";
    
    /**
     * ҵ�񱨱��ֲ����
     */
    public static final String AUTH_SALE_INFO           = "020201";
    
    /**
     * ҵ�񱨱��ۿ��ձ�
     */
    public static final String AUTH_TRANS_DAY_RPT       = "020301";
    
    /**
     * ҵ�񱨱��ۿ��±�
     */
    public static final String AUTH_TRANS_MONTH_RPT     = "020401";
    
    /**
     * ҵ�񱨱��˿���ѯ
     */
    public static final String AUTH_RETN_QUERY          = "020501";
    
    /**
     * ҵ�񱨱��˻������ձ�
     */
    public static final String AUTH_RETN_GOODS_DAY	  = "020701";
    
    /**
     * ҵ�񱨱��˻������±�
     */
    public static final String AUTH_RETN_GOODS_MONTH	  = "020801";
    
	/**
	 * ҵ�񱨱�����¡�����׿���ѯ
	 */
	public static final String AUTH_TRANS_CARD_QUERY    = "020901";

	/**
	 * ҵ�񱨱�����¡������ѯ
	 */
	public static final String AUTH_CARD_QUERY          = "021001";

    /**
     * ҵ�񱨱�����¡��������־
     */
    public static final String AUTH_SERVICE_LOG         = "021301";
	
	/**
	 * ҵ�񱨱�����¡�����׿�����ѯ
	 */
	public static final String AUTH_TRANS_CARD_BALANCEQUERY    = "021401";
    
    /**
     * ҵ�񱨱�����¡���ۿ���������ܲ�ѯ
     */
    public static final String AUTH_SALE_CARD_QUERY    = "021501";
    
    /**
     * ҵ�񱨱�������λ��ѯ
     */
    public static final String AUTH_SALE_CARD_CUSTNAME    = "021601";
        
    /**
     * �޸Ĺ�����λ��Ϣ 
     */
    public static final String AUTH_SALE_CARD_CUSTNAME_UPDATE = "021602";
    
    /**
     * �޸Ĺ�����λ��Ϣ -->�ƿ���
     */
    public static final String AUTH_SALE_CARD_CUSTNAME_UPDATE_MAKECHARGE = "021603";
    
	/**
	 * ҵ�񱨱�������λ��ѯ
	 */
	public static final String AUTH_CARD_VALIDDATE    = "021701";
    
	/**
	 * ҵ�񱨱������Ż������ѯ
	 */
	public static final String AUTH_CARD_USER_DISCOUNT    = "021801";
	
	/**
	 * ҵ�񱨱����ɹ����Ż���Ϣ
	 */
	public static final String AUTH_CARD_USER_DISCOUNT_CREATE    = "021802";
	
	/**
	 * ҵ�񱨱������Żݷ��������ѯ
	 */
	public static final String AUTH_CARD_USER_GIFT    = "021901";
	
	/**
	 * ҵ�񱨱��޸Ĺ����Żݷ������
	 */
	public static final String AUTH_CARD_USER_GIFT_EDIT    = "021902";
	
    //-----------------------------------------------------------------

    /**
     * ���ѱ�����������
     */
    public static final String AUTH_TODAY_CONSUME       = "030101";
    
    /**
     * ���ѱ��������ձ�
     */
    public static final String AUTH_DAY_CONSUME         = "030201";
    
    /**
     * ���ѱ����⻧��˾���׻��ܱ�
     */
    public static final String AUTH_CUST_TOTAL       = "030301";
    
    /**
     * ���ѱ������׻���
     */
    public static final String AUTH_LIST_TOTAL           = "030401"; 
    
    /**
     * ���ѱ���������ϸ
     */
    public static final String AUTH_DETAIL_TOTAL         = "030501";
    
	/**
	 * ���ѱ���������ˮ��ѯ
	 */
	public static final String AUTH_ALL_TOTAL           = "030601";    
    
	/**
	 * ���ѱ�������¡�����������ձ�
	 */
	public static final String AUTH_GRP_DAY_CONSUME           = "030901";    

	/**
	 * ���ѱ�������¡�����������±�
	 */
	public static final String AUTH_GRP_MONTH_CONSUME           = "031001";
	
	/**
	 * ���ѱ���: ������ˮ��ϸ
	 */
	public static final String AUTH_ITEM_DETAIL           = "031101";
	
	//��汨��-----------------------------------------------------------------

    /**
     * ��汨���ܲ����
     */
    public static final String AUTH_HQ_STOCK                = "040101";
    
    /**
     * ��汨���ܲ����
     */
    public static final String AUTH_HQ_ENTER_STOCK      = "040201";
    
    /**
     * ��汨���ŵ���
     */
    public static final String AUTH_STORE_STOCK         = "040301";
    
    /**
     * �������ѯ
     */
    public static final String AUTH_RATION_STOCK_QUERY    = "040401";
    
    //---------------------  ������  ---------------------
    
    /**
     * ���������ʲ�ѯ
     */
    public static final String AUTH_ACCT_QUERY          = "050101";

    /**
     * �������ֹ�����
     */
    public static final String AUTH_ACCT_ADJUST          = "050201";

    /**
     * �������Ѷ��ʽ�����ϸ��
     */
    public static final String AUTH_ACCT_SETTLEMENT      = "050401";
    
    //��Ա��ҵ��--------------------------------------------------------
	/**
 	 * ��Ա��ҵ�񱨱������ѯ
 	 */
	public static final String AUTH_DISTR_QUERY_MEMBER     = "060101";
    
	/**
	 * ��Ա��ҵ�񱨱��ֲ����
	 */
	public static final String AUTH_SALE_INFO_MEMBER       = "060201";
    
	/**
	 * ��Ա��ҵ�񱨱��ۿ��ձ�
	 */
	public static final String AUTH_TRANS_DAY_RPT_MEMBER   = "060301";
	/**
	 * ��Ա��ҵ�񱨱��ۿ��±�
	 */
	public static final String AUTH_TRANS_MONTH_RPT_MEMBER = "060401";
    
	/**
	 * ��Ա��ҵ�񱨱��˿���ѯ
	 */
	public static final String AUTH_RETN_QUERY_MEMBER      = "060501";
	/**
	 * ��Ա��ҵ�񱨱��˻������ձ�
	 */
	public static final String AUTH_RETN_GOODS_DAY_MEMBER  = "060701";
	/**
	 * ��Ա��ҵ�񱨱��˻������±�
	 */
	public static final String AUTH_RETN_GOODS_MONTH_MEMBER= "060801";
	/**
	 * ��Ա��ҵ�񱨱���Ա�����׿���ѯ
	 */
	public static final String AUTH_TRANS_CARD_QUERY_MEMBER= "060901";

	/**
	 * ��Ա��ҵ�񱨱���Ա������ѯ
	 */
	public static final String AUTH_CARD_QUERY_MEMBER      = "061001";
    
    /**
     * ��Ա��ҵ�񱨱���Ա���˻���ֵ��ѯ
     */
    public static final String AUTH_RETN_FILL_QUERY_MEMBER = "061101";

	/**
	 * ��Ա��ҵ�񱨱���Ա��������־
	 */
	public static final String AUTH_SERVICE_LOG_MEMBER     = "061301";
	
	/**
	 * ��Ա��ҵ�񱨱���Ա�����׿�����ѯ
	 */
	public static final String AUTH_TRANS_CARD_BALANCEQUERY_MEMBER= "061401";

	/**
	 * ��Ա��ҵ�񱨱���Ա��Ʒ�·���־
	 */
	public static final String AUTH_MEMBGOODS_LOG_MEMBER     = "061501";
    
    /**
     * ��Ա��ҵ�񱨱���Ա����ֵ��������ܲ�ѯ
     */
    public static final String AUTH_CHARGE_QUERY_MEMBER      = "061601"; 

	//��Ա�����ѱ���--------------------------------------------------------

	/**
	 * ���ѱ�����Ա����������
	 */
	public static final String AUTH_TODAY_CONSUME_MEMBER  = "070101";
    
	/**
	 * ���ѱ�����Ա�������ձ�
	 */
	public static final String AUTH_DAY_CONSUME_MEMBER    = "070201";
    
	/**
	 * ���ѱ�����Ա�������±�
	 */
	public static final String AUTH_MONTH_CONSUME_MEMBER  = "070301";
    
	/**
	 * ���ѱ�����Ա�������ձ�
	 */
	public static final String AUTH_DAY_TOTAL_MEMBER      = "070401"; 
    
	/**
	 * ���ѱ�����Ա�������±�
	 */
	public static final String AUTH_MONTH_TOTAL_MEMBER    = "070501";
    
	/**
	 * ���ѱ�����Ա�����ջ��ֲ�ѯ
	 */
	public static final String AUTH_TODAY_SCORE_MEMBER      = "070601";    
	/**
	 * ���ѱ�����Ա�������ձ���ѯ
	 */
	public static final String AUTH_DAY_SCORE_MEMBER      = "070701";    
	/**
	 * ���ѱ�����Ա�������±���ѯ
	 */
	public static final String AUTH_MONTH_SCORE_MEMBER      = "070801";    
	/**
	 * ���ѱ�����Ա�����ܲ�ѯ
	 */
	public static final String AUTH_ALL_TOTAL_MEMBER      = "070901";    
    
	/**
	 * ���ѱ�����Ա�����������±�
	 */
	public static final String AUTH_GRP_DAY_CONSUME_MEMBER = "071001";    

	/**
	 * ���ѱ�����Ա�����������±�
	 */
	public static final String AUTH_GRP_MONTH_CONSUME_MEMBER = "071101";    

	//��Ա����汨��-----------------------------------------------------------------

	/**
	 * ��汨����Ա���ܲ����
	 */
	public static final String AUTH_HQ_STOCK_MEMBER       = "080101";
    
	/**
	 * ��汨����Ա���ܲ����
	 */
	public static final String AUTH_HQ_ENTER_STOCK_MEMBER = "080201";
    
	/**
	 * ��汨����Ա���ŵ���
	 */
	public static final String AUTH_STORE_STOCK_MEMBER    = "080301";

	//��Ա���ͻ�����---------------------------------------------------------------
    /**
     * ��Ա���ͻ���Ϣ�޸�
     */
    public static final String AUTH_MODIFY_CUST_INFO_MEMBER = "090101";
    
    /**
     * ��Ա����ѯ�����޸�
     */
    public static final String AUTH_MODIFY_QPASSWORD_MEMBER = "090201";
    
    /**
     * ��Ա����ֵ��¼��ѯ
     */
    public static final String AUTH_FILL_QUERY_MEMBER       = "090301";
    
    /**
     * ��Ա��������ʷ��ѯ
     */
    public static final String AUTH_SCORE_HIST_MEMBER       = "090401"; 
    
    /**
     * ��Ա��Ʒ���
     */
    public static final String AUTH_VIEW_MEMGOODS_MEMBER    = "090601";
    
    /**
     * ��Ա�����ֲ��Զ���
     */
    public static final String AUTH_SCORE_STRATEGY_DEFINE_MEMBER = "090701";
    
    /**
     * ��Ա�������Թ���
     */
    public static final String ATUH_STRATEGY_MANAGE_MEMBER       = "090801";
    
    /**
     * ��Ա�����ֻ���
     */
    public static final String AUTH_SHOP_WITH_SCORE_MEMBER       = "090901";
    
    /**
     * ���Ѽ�¼����
     */ 
    public static final String AUTH_PATCHLOG_MEMBER              = "091001";
    
    /**
     * ��Ա���ͻ���Ϣ����
     */
    public static final String AUTH_MODIFY_CERT_INFO_MEMBER      = "091101"; 
    
    /**
     * ��Ա��������ˮ��ѯ
     */
    public static final String AUTH_TRANSSEQ_MEMBER              = "091501";
    
    /**
     * ��Ա�����ֲ�ѯ
     */
    public static final String AUTH_SCORE_MEMBER                 = "091601";
    
    /**
     * ��Ա��Ʒ���۲�ѯ
     */
    public static final String AUTH_GOODS_SALE_MEMBER            = "091701";
    
    /**
     * ��Ա����Ϣ��ѯ
     */
    public static final String AUTH_CARD_INFO_MEMBER             = "091801";
    
    /**
     * ��Ա���ʻ���ѯ
     */
    public static final String AUTH_ACCOUNT_MEMBER               = "091901";
    
    /**
     * ��Ա��������ѯ
     */
    public static final String AUTH_CARDNUM_MEMBER               = "092001";
    
    
    // �������Ϲ���-----------------------------------------------------------------
    /**
     *  ��������
     */
    public static final String AUTH_IMP_GOODS                    = "100101";
    
    /**
     *  �ܲ��䷢
     */                                                           
    public static final String AUTH_DISTR_GOODS                  = "100201";
    
    /**
     *  �ܲ����ŵ��˻�
     */                                                            
    public static final String AUTH_RET_GOODS                    = "100301";
    
    /**
     *  �������Թ���
     */                                                      
    public static final String AUTH_STRATEGY_MNG                 = "100401";
    
    /**
     *  ���ֻ���
     */                                                        
    public static final String AUTH_SCORE_EXCHGE                 = "100501";
    
    /**
     *  ����̵�
     */                                                        
    public static final String AUTH_STOCK_CHK                    = "100601";
    
    /**
     * ���մ�ֵ����
     */
    public static final String AUTH_RET_MONEY                    = "100701";
    
    /**
     * �ܲ�����ѯ
     */
    public static final String AUTH_HQSTOCK_QUERY                = "100801";
    
    /**
     * �ŵ����ѯ
     */
    public static final String AUTH_SSTOCK_QUERY                 = "100901"; 
    
    /**
     * ��Ʒ������Ϣ����
     */
    public static final String AUTH_GOODSINFO_MNG                = "101001";
    
    /**
     * �������ݲ�ѯ
     */
    public static final String AUTH_IMPBILLS_QRY                 = "101101";
    
    /**
     * �䷢���ݲ�ѯ
     */
    public static final String AUTH_DISTRBILLS_QRY               = "101201";
    
    /**
     * �˻����ݲ�ѯ
     */
    public static final String AUTH_RETBILLS_QRY                 = "101301";
    
    /**
     * ����̵���쵥�ݲ�ѯ
     */
    public static final String AUTH_STOCKCHK_BILLS_QRY           = "101401";
    
    /**
     * ������Ϣ����
     */
    public static final String AUTH_TRANSCARD_BAKUP              = "101501";
    /**
     * ������¼��ѯ
     */
    public static final String AUTH_QRY_EXCHNG_LOG               = "101601";
    

    //-----------------------------------------------------------------
          
    /**
     * �û����ͣ�ҵ��Ա
     */
    public static final String USER_TYPE_OPER   = "1";
    
    /**
     * �û����ͣ��ͻ����ֿ��ˣ�
     */
    public static final String USER_TYPE_CUST   = "2";

	/**
	 * �������ơ�
	 * �������ڣ�(2003-3-7 16:46:56)
	 * @return java.lang.String
	 */
	public String getOrgName();

	/**
	 * �û����ơ�
	 * �������ڣ�(2003-3-7 16:44:38)
	 * @return java.lang.String
	 */
	public String getUserName();

	/**
	 * �Ƿ��д�Ȩ�ޡ�
	 * �������ڣ�(2003-3-7 17:14:21)
	 * @return boolean
	 * @param strAuthID java.lang.String
	 */
	public boolean isAllowed(String strAuthID);

	/**
	 * �������롣
	 * �������ڣ�(2003-3-7 16:46:37)
	 * @return java.lang.String
	 */
	public String getOrgID();

	/**
	 * �������͡�
	 * �������ڣ�(2003-3-7 16:47:19)
	 * @return java.lang.String
	 */
	public String getOrgType();
    
	/**
	 * �û���¼���롣
	 * �������ڣ�(2003-3-7 16:46:04)
	 * @return java.lang.String
	 */
	public String getLoginID();

    /**
     * �û���¼���롣
     * �������ڣ�(2003-3-7 16:46:04)
     * @return java.lang.String
     */
	public String getPasswd();
    
	/**
	 * �û��Ƿ���Ч��
	 * �������ڣ�(2003-3-7 16:45:17)
	 * @return boolean
	 */
	public String getStatus();
    
	/**
	 * �û����롣
	 * �������ڣ�(2003-3-7 16:43:51)
	 * @return int
	 */
	public String getUserID();

    /**
     * �û�������λ��
     * �������ڣ�(2003-3-7 16:43:51)
     * @return int[][]
     */
    public String[] getUserRole();

    /**
     * �û�ӵ�еĹ��ܡ�
     * �������ڣ�(2003-3-7 16:43:51)
     * @return String[][]
     */
    public String[][] getUserFuncs();
    
    /**
     * Sets the �����Ĳ���.
     * @param context The context to set
     */
    public void setContext(String context);
    
    /**
     * ��ԭ�����Ĳ���
     */
    public void restoreContext();
    
    /**
     * ����û������ͣ����ڻ��ֿ�
     */
    public String getUserType();
    
    /**
     * ��û��ֿ�����
     */
    public String getScoreCardID();
    
}