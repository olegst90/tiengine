package com.tiengine.scripting;


import com.tiengine.controls.GControlHost;
import com.tiengine.graphics.GGraphicHost;
import com.tiengine.utils.GResourceFactory;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.OrphanedThread;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.compiler.LuaC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;


public class ScriptHost {
    static Logger logger = LoggerFactory.getLogger(ScriptHost.class);

    class EngineState {
        LuaThread thread;
        String script;
        GGraphicHost.State graphicHostState;
        GControlHost.State controlHostState;
    }
    Map<String, EngineState> __state_cache = new HashMap<String, EngineState>();
    Stack<EngineState> __state_stack = new Stack<EngineState>();
    RootScriptInterface __root_script;
    Queue<Runnable> __messages = new LinkedBlockingQueue<Runnable>();

    public void postMessage(Runnable msg) {
        synchronized (__messages) {
            __messages.add(msg);
        }
    }

    GGraphicHost __graphicHost;
    public void setGraphicHost(GGraphicHost host) {
        __graphicHost = host;
    }
    public GGraphicHost getGraphicHost() {
        return __graphicHost;
    }

    GControlHost __controlHost;
    public void setControlHost(GControlHost host) {
        __controlHost = host;
    }
    public GControlHost getControlHost() {
        return __controlHost;
    }


    public ScriptHost() {
        __root_script = new RootScriptInterface(this);
        __root_script.setLoadHandler(new RootScriptInterface.LoadHandler() {
            @Override
            public void load(String script, boolean reset_cache) {
                logger.debug("load script handler {}\n", script);
                //this call won't return
                cb_LoadScript(script, reset_cache);
            }
        });

        __root_script.setExitHandler(new RootScriptInterface.ExitHandler() {
            @Override
            public void exit() {
                cb_TerminateScript();
            }
        });

        logger.info("base globals initialized\n");
    }

    Globals newGlobals() {
        Globals globals = new Globals();
        globals.load(new JseBaseLib());
        globals.load(__root_script);
        LoadState.install(globals);
        LuaC.install(globals);
        return globals;
    }

   private class ScriptZigote extends OneArgFunction {
        Globals __globals;
        ScriptZigote(Globals globals) {
            __globals = globals;
        }
        public LuaValue call(LuaValue script) {
            logger.info("Thread started. Loading script {}", script.toString());
            try {
                __globals.load(new InputStreamReader(GResourceFactory.resourceFactory().loadScript(script.toString())),
                        script.toString()).call();
            } catch (FileNotFoundException e) {
                logger.error("Could not load script {}", script.toString());
                LuaValue arg[] = {
                        LuaValue.valueOf(TERMINATE),
                };
                __state_stack.peek().thread.state.lua_yield(LuaValue.varargsOf(arg));
            }
            logger.info("Script {} is executed, going async", script.toString());
            do {
                synchronized (__messages) {
                    while (!__messages.isEmpty()) {
                        __messages.remove().run();
                    }
                }
                LuaValue arg[] = {
                        LuaValue.valueOf(CONTINUE),
                };
                __state_stack.peek().thread.state.lua_yield(LuaValue.varargsOf(arg));
            } while(true);
        }
    }

    public final static int TERMINATE  = 1;
    public final static int LOAD       = 3;
    public final static int PUSH       = 5;
    public final static int POP        = 6;
    public final static int CONTINUE   = 7;

    void uninit() {
        __state_cache.clear();
        __state_stack.clear();
    }

    void initialLoad(String script) {
        EngineState state = new EngineState();
        state.script = script;

        state.graphicHostState = new GGraphicHost.State();
        __graphicHost.setState(state.graphicHostState);

        state.controlHostState = new GControlHost.State();
        __controlHost.setState(state.controlHostState);

        Globals globals = newGlobals();
        state.thread = new LuaThread(globals, new ScriptZigote(globals));
        __state_cache.put(script, state);
        __state_stack.push(state);
    }

    public void loadScript(String script) {
        uninit();
        initialLoad(script);
    }

    public int scriptLoop() throws Exception {
        if (__state_stack.empty()) {
            logger.debug("No more threads to run");
            uninit();
            return TERMINATE;
        }
        LuaValue arg[] = {LuaValue.valueOf(__state_stack.peek().script)};
        Varargs r = __state_stack.peek().thread.resume(LuaValue.varargsOf(arg));
        if (!r.toboolean(1)) {
            logger.error("Could not resume");
            uninit();
            return TERMINATE;
        }
        logger.debug("Control returned back: cmd {}", r.toint(2));
        if (r.toint(2) == CONTINUE) {
            return CONTINUE;
        } else if (r.toint(2) == TERMINATE) {
            logger.debug("Exit requested");
            __state_stack.peek().thread.resume(null);
            uninit();
            return TERMINATE;
        } else if (r.toint(2) == LOAD) {
            String script = r.tojstring(3);
            boolean reset_cache = r.toboolean(4);
            logger.debug("Script {} was requested", script);

            // let caller terminate
            __state_stack.pop().thread.resume(null);

            if (reset_cache) {
                logger.debug("Invalidating cache");
                uninit();
                initialLoad(r.tojstring(3));
            } else {
                EngineState new_state = __state_cache.get(script);
                if (new_state == null) {
                    logger.debug("Creating new state");
                    new_state = new EngineState();
                    new_state.graphicHostState = new GGraphicHost.State();
                    __graphicHost.setState(new_state.graphicHostState);

                    new_state.controlHostState = new GControlHost.State();
                    __controlHost.setState(new_state.controlHostState);
                    new_state.script = script.toString();
                    Globals globals = newGlobals();
                    new_state.thread = new LuaThread(globals, new ScriptZigote(globals));
                    __state_cache.put(script, new_state);
                } else {
                    logger.debug("State was restored from cache\n");
                    __graphicHost.setState(new_state.graphicHostState);
                    __controlHost.setState(new_state.controlHostState);
                }
                __state_stack.push(new_state);
            }
            return CONTINUE;
        } else {
            throw new Exception("Bad thread status");
        }
    }

    public void cb_LoadScript(String script, boolean reset_cache) throws LuaError {
        logger.debug("Loading new script {}", script);
        LuaValue arg [] = {
                LuaValue.valueOf(LOAD),
                LuaValue.valueOf(script),
                LuaValue.valueOf(reset_cache)
        };
        __state_stack.peek().thread.state.lua_yield(LuaValue.varargsOf(arg));
        throw new OrphanedThread();
    }

    public void cb_TerminateScript() throws LuaError {
        LuaValue arg [] = {
                LuaValue.valueOf(TERMINATE),
        };
        __state_stack.peek().thread.state.lua_yield(LuaValue.varargsOf(arg));
        throw new OrphanedThread();
    }
}