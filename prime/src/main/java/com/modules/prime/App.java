package com.modules.prime;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;

public class App implements Launcher {

    private static final transient Logger logger = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        LauncherHandler.handle(App.class);
    }

    @Override
    public void run() {
        logger.info("aaaaa");
    }
}
