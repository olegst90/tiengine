package com.tiengine.scripting;
import com.tiengine.controls.*;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.LuaError;

public class ControlScriptInterface extends LuaTable {
    ControlScriptInterface () {
        System.out.println("control script interface constructor");
        set("new_button", new NewButton());
    }

    private class NewButton extends VarArgFunction {
        public LuaValue invoke(Varargs arg) {
            if (arg.narg() != 5) throw new LuaError("Bad var arg for new button");
            System.out.format("new button: %d %d %d %d\n", arg.toint(1), arg.toint(2), arg.toint(3), arg.toint(4));
            GControl ctrl = new GControl();
            ctrl.setCallback(new GControl.GControlCallback() {
                LuaFunction cb = arg.checkfunction(5);
                @Override
                public void call() {
                    cb.call();
                }
            });
            return LuaValue.userdataOf(ctrl);
        }
    }
}
