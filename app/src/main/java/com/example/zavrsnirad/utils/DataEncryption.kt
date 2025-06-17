package com.example.zavrsnirad.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object DataEncryption {

    private const val KEY_ALIAS = "moj_kljuc"

    // Inicijalizacija Keystore-a i generiranje kljuca AES-a ako ga ne postoji
    private fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }

        // Provjera postoji li kljuc, ako ne kreiramo ga
        val key = keyStore.getKey(KEY_ALIAS, null) as SecretKey?
        if (key != null) {
            return key
        }

        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(keyGenParameterSpec)
        return keyGenerator.generateKey()
    }

    // Enkripcija podataka koristenjem AES enkripcije
    fun encryptData(data: String): String {
        val secretKey = getKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptionResult = cipher.doFinal(data.toByteArray())

        //Kombinacija IV-a i enkriptiranih podataka, zatim ga enkodiramo na Base64
        val combined = ByteArray(iv.size + encryptionResult.size)
        System.arraycopy(iv, 0, combined, 0, iv.size)
        System.arraycopy(encryptionResult, 0, combined, iv.size, encryptionResult.size)
        return Base64.encodeToString(combined, Base64.DEFAULT)
    }

    // Dekriptiranje podataka koristenjem AES-a
    fun decryptData(encryptedData: String): String {
        val secretKey = getKey()
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")

        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)

        if (decodedData.size < 12) {
            return "" // Vrati prazan string ako podaci ne sadrze IV
        }

        val iv = decodedData.copyOfRange(0, 12) // Prvih 12 bajtova sadrze IV
        val encryptedBytes = decodedData.copyOfRange(12, decodedData.size)

        val gcmParameterSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParameterSpec)

        val decryptedData = cipher.doFinal(encryptedBytes)
        return String(decryptedData)
    }
}
