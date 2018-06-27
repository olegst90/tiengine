package com.tiengine.scripting;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by olegst on 6/27/18.
 */

public class RootScriptInterface extends TwoArgFunction {
    Logger logger = LoggerFactory.getLogger(RootScriptInterface.class);
    LuaValue __library = tableOf();

    public LuaValue call(LuaValue modname, LuaValue env) {
        //general script routines
        __library.set("load_script", new LoadScript());
        __library.set("terminate_script", new ExitScript());

        //_2d engine
        __library.set("_2d", new ScriptInterface2D());
        __library.set("ctrl",new ControlScriptInterface());

        env.set("script_interface", __library);
        return __library;
    }

    private class LoadScript extends OneArgFunction {
        public LuaValue call(LuaValue arg) {
            __loadHandler.load(arg.toString());
            return LuaValue.valueOf(true);
        }
    }

    /* script load handler */
    public interface LoadHandler {
        void load(String script);
    }

    LoadHandler __loadHandler = null;

    public void setLoadHandler(LoadHandler handler) {
        __loadHandler = handler;
    }

    /* app termination handler */
    public interface ExitHandler {
        void exit();
    }

    private ExitHandler __exitHandler = null;

    public void setExitHandler(ExitHandler handler) {
        __exitHandler = handler;
    }

    private class ExitScript extends ZeroArgFunction {
        public LuaValue call() {
            __exitHandler.exit();
            return null; //we shouldn't reach this statement
        }
    }
}
