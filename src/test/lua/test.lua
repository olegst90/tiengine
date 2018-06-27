function cb()
   print ("Button callback")
end
script_interface.ctrl.new_button(0,1,2,3, cb)
script_interface.load_script("src/test/lua/loadee.lua")

