package com.appsflyer.load

import android.annotation.SuppressLint
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
@SuppressLint("NewApi")
object AfEncryUtils {
    private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"
    private const val KEY_SIZE = 256

    fun generateSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(128)
        return keyGenerator.generateKey()
    }

    fun keyToString(secretKey: SecretKey): String {
        return Base64.getEncoder().encodeToString(secretKey.encoded)
    }

    fun stringToKey(stringKey: String): SecretKey {
        val keyBytes = Base64.getDecoder().decode(stringKey)
        return SecretKeySpec(keyBytes, "AES")
    }

    fun encryptAES(data: String, stringKey: String): String {
        val secretKey = stringToKey(stringKey)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decryptAES(encryptedData: String, stringKey: String): String {
        val secretKey = stringToKey(stringKey)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, secretKey)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes)
    }
}