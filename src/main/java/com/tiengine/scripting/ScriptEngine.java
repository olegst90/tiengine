package com.tiengine.scripting;

import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.compiler.LuaC;


public class ScriptEngine {
    Globals __globals = null;

    public void loadScript(String path) throws LuaError {
        __globals = new Globals();
        __globals.load(new JseBaseLib());
        LoadState.install(__globals);
        LuaC.install(__globals);

        RootScriptInterface rs = new RootScriptInterface();
        rs.setLoadHandler(new RootScriptInterface.LoadHandler() {
            @Override
            public void load(String script) {
                loadScript(script);
            }
        });
        rs.setExitHandler(__exitHandler);
        __globals.load(rs);
        LuaValue chunk = __globals.loadfile(path);
        chunk.call();
    }


    private RootScriptInterface.ExitHandler __exitHandler = null;
    public void setExitHandler(RootScriptInterface.ExitHandler handler) {
        __exitHandler = handler;
    }
}