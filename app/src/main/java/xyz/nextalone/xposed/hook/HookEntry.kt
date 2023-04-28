package xyz.nextalone.xposed.hook

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.factory.configs
import com.highcapable.yukihookapi.hook.factory.encase
import com.highcapable.yukihookapi.hook.log.YukiHookLogger
import com.highcapable.yukihookapi.hook.log.loggerD
import com.highcapable.yukihookapi.hook.type.android.BundleClass
import com.highcapable.yukihookapi.hook.type.android.ViewClass
import com.highcapable.yukihookapi.hook.type.java.IntClass
import com.highcapable.yukihookapi.hook.type.java.UnitType
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit

@InjectYukiHookWithXposed
class HookEntry : IYukiHookXposedInit {

    override fun onInit() = configs {
        debugLog {
            isEnable = true
            tag = "NextAlone"
        }
        isDebug = true

    }


    override fun onHook() = encase {
        loadSystem {
            "com.android.internal.app.MiuiChooserActivity".hook {
                injectMember {
                    method {
                        name = "onCreate"
                        param(BundleClass)
                        returnType = UnitType
                    }
                    beforeHook {
                        loggerD(msg = "before onCreate")
                        Log.d(YukiHookLogger.Configs.tag, "onHook: before onCreate")
                    }
                    afterHook {
                        loggerD(msg = "after onCreate")
                        Log.d(YukiHookLogger.Configs.tag, "onHook: after onCreate")
                        AlertDialog.Builder(instance())
                            .setTitle("Hooked")
                            .setMessage("Hooked")
                            .setPositiveButton("OK") { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                }

                injectMember {
                    method {
                        name = "handleLayoutChange"
                        returnType = UnitType
                    }
                    beforeHook {
                        loggerD(msg = "before handleLayoutChange")
                    }
                    afterHook {
                        loggerD(msg = "after handleLayoutChange")
                    }
                }
            }
        }
    }
}