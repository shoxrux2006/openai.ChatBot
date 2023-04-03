package uz.gita.openaichatbot.utils

import android.util.Log
import uz.gita.openaichatbot.BuildConfig

fun l(string: String){
    if(BuildConfig.DEBUG){
        Log.d("TTT",string)
    }
}