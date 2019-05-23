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
 * 本例外标志用户在试图调用某个Task时，在系统环境中没有发现用户的登录信息。
 */
public class ESSContextNotExistedException extends ESSException {
    public ESSContextNotExistedException(){
        super( "用户无效，请重新登录之后再试");
    }
}
