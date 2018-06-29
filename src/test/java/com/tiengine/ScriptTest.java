package com.tiengine;

import com.tiengine.controls.GControlHost;
import com.tiengine.graphics.GGraphicHost;
import com.tiengine.scripting.ScriptHost;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

/**
 * Created by olegst on 28.06.18.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScriptTest {
    ScriptHost se = null;
    static Logger logger = LoggerFactory.getLogger(ScriptTest.class);

    @Before
    public void setUp() {
        TestInit.init();
        se = new ScriptHost();
        se.setGraphicHost(new GGraphicHost());
        se.setControlHost(new GControlHost(se.getGraphicHost()));
    }

    @Test public void test_01_ScriptEngineRun() {
        try {
            se.loadScript("src/test/lua/test.lua");
            while (se.scriptLoop() == ScriptHost.CONTINUE) {
                logger.info("looping");
            }
            logger.info("Returned from load script\n");
        } catch (Exception e) {
            System.out.println(e.toString());
            assert(false);
        }
    }
}
