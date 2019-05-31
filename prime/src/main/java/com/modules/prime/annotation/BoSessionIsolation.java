package com.modules.prime.annotation;

import java.sql.Connection;

public class BoSessionIsolation {
    public static final int TRANSACTION_NONE = Connection.TRANSACTION_NONE;
    public static final int TRANSACTION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;
    public static final int TRANSACTION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
    public static final int TRANSACTION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;
    public static final int TRANSACTION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
}
