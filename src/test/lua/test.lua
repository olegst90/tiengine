print ("This is lua script 1")
function cb()
   print ("Button callback")
end

if (i == nil) then
  i = 0
end

print("------->>>>>>>>>>>", i)
ctrl.new_button(0,1,2,3, cb)
load_script("src/test/lua/loadee.lua", false)

