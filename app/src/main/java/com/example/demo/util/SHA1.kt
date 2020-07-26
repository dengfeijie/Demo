package com.example.demo.util

import android.content.Context
import android.content.pm.PackageManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * @author dengfeijie
 * @description:  获取高德的SHA1码
 * @date : 2020/7/13 21:54
 */
object SHA1 {
    fun sHA1(context: Context): String? {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName, PackageManager.GET_SIGNATURES)
            val cert = info.signatures[0].toByteArray()
            val md = MessageDigest.getInstance("SHA1")
            val publicKey = md.digest(cert)
            val hexString = StringBuffer()
            for (i in publicKey.indices) {
                val appendString = Integer.toHexString(0xFF and publicKey[i]
                    .toInt())
                    .toUpperCase(Locale.US)
                if (appendString.length == 1) {
                    hexString.append("0")
                }
                if (i % 2 == 0) {
                    hexString.append(":$appendString")
                } else {
                    hexString.append(appendString)
                }
            }
            return hexString.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}