package com.tiengine.scripting;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.LuaError;
/**
 * Created by olegst on 6/27/18.
 */

public class ScriptInterface2D extends LuaTable {
    ScriptInterface2D () {
        System.out.println("2D interface constructor");
        set("version", LuaValue.valueOf("0.0.1"));
    }
}
