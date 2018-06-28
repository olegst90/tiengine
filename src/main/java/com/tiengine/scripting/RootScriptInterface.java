package com.tiengine.scripting;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by olegst on 6/27/18.
 */

public class RootScriptInterface extends TwoArgFunction {
    ScriptEngine __parent_engine;

    public RootScriptInterface(ScriptEngine parent_engine) {
        __parent_engine = parent_engine;
    }

    Logger logger = LoggerFactory.getLogger(RootScriptInterface.class);
    //LuaValue __library = tableOf();

    public LuaValue call(LuaValue modname, LuaValue env) {
        //general script routines
        env.set("load_script", new LoadScript());
        env.set("terminate", new ExitScript());

        //_2d engine
        env.set("_2d", new ScriptInterface2D());
        env.set("ctrl",new ControlScriptInterface(__parent_engine));

        //env.set("si", __library);
        return env;
    }

    /* script load handler */
    public interface LoadHandler {
        void load(String script, boolean reset_cache);
    }

    LoadHandler __loadHandler = null;

    public void setLoadHandler(LoadHandler handler) {
        __loadHandler = handler;
    }

    private class LoadScript extends TwoArgFunction {
        public LuaValue call(LuaValue script, LuaValue reset_cache)  {
            __loadHandler.load(script.toString(),
                               reset_cache != null ? reset_cache.toboolean() : false);
            return null;
        }
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
            System.out.println("exiting");
            __exitHandler.exit();
            System.out.println("exited");
            return LuaValue.valueOf(true);
        }
    }
}
