package com.tiengine.scripting;

import com.tiengine.controls.*;
import com.tiengine.graphics.GGraphicFactory;
import com.tiengine.graphics.GTextArea;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.LuaError;

public class ControlScriptInterface extends LuaTable {
    ScriptHost __script_host;
    ControlScriptInterface (ScriptHost script_host) {
        System.out.println("control script interface constructor");
        __script_host = script_host;
        set("new_button", new NewButton());
        set("new_text", new NewTextArea());
    }

    private class NewButton extends VarArgFunction {
        @Override
        public LuaValue invoke(final Varargs arg) {
            if (arg.narg() != 5) throw new LuaError("Bad var arg for new button");
            System.out.format("new button: %d %d %d %d\n", arg.toint(1), arg.toint(2), arg.toint(3), arg.toint(4));
            GButton ctrl = GControlFactory.controlFactory().newButton();
            ctrl.setCallback(new GButton.GButtonCallback() {
                @Override
                public void call() {
                    __script_host.postMessage(new Runnable() {
                        LuaFunction cb = arg.checkfunction(5);
                        @Override
                        public void run() {
                            cb.call();
                        }
                    });
                }
            });
            __script_host.getControlHost().putControl(ctrl);
            ctrl.press();
            return LuaValue.userdataOf(ctrl);
        }
    }

    private class NewTextArea extends ThreeArgFunction {
        @Override
        public LuaValue call(LuaValue x, LuaValue y, LuaValue text) {
            System.out.format("text [%d:%d]: %s\n", x.toint(), y.toint(), text.toString());
            GTextArea area = GGraphicFactory.graphicFactory().newTextArea();
            area.setText(text.toString());
            area.setPosition(x.toint(), y.toint());
            return LuaValue.userdataOf(area);
        }
    }
}
