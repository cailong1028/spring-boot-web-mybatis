/******************************************************************************
    File:   ESSContextNotExistedException.java

    Version 1.0
    Date                Author              Changes
    Jan.1,2001          LiShan              Created

    Copyright (c) 2001, eChinaStores.com
    all rights reserved
*******************************************************************************/

package ess.base.exp;

import ess.base.exp.ESSException;

/**
 * �������־�û�����ͼ����ĳ��Taskʱ����ϵͳ������û�з����û��ĵ�¼��Ϣ��
 */
public class ESSContextNotExistedException extends ESSException {
    public ESSContextNotExistedException(){
        super( "�û���Ч�������µ�¼֮������");
    }
}
