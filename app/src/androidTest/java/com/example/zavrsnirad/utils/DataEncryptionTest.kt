package com.example.zavrsnirad.utils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataEncryptionTest {

    @Test
    fun `enkripcija_vraca_neoriginalan_i_base64_string`() {
        val originalData = "test@example.com"
        val encryptedData = DataEncryption.encryptData(originalData)

        assertNotEquals(originalData, encryptedData)

        val sanitizedEncryptedData = encryptedData.replace("\n", "")
        assertTrue(sanitizedEncryptedData.matches("^[A-Za-z0-9+/=]+\$".toRegex()))
    }

    @Test
    fun dekripcija_vraca_originalne_podatke_nakon_enkripcije() {
        val originalData = "ivan.horvat@mail.hr"
        val encryptedData = DataEncryption.encryptData(originalData)
        val decryptedData = DataEncryption.decryptData(encryptedData)
        assertEquals(originalData, decryptedData)
    }

    @Test
    fun enkripcija_i_dekripcija_kompleksnog_stringa() {
        val complexData = "Adresa: Zagrebačka 5, Zagreb, HR; Telefon: +385912345678!"
        val encryptedData = DataEncryption.encryptData(complexData)
        val decryptedData = DataEncryption.decryptData(encryptedData)
        assertEquals(complexData, decryptedData)
    }

    @Test
    fun enkripcija_i_dekripcija_praznog_stringa() {
        val emptyData = ""
        val encryptedData = DataEncryption.encryptData(emptyData)
        val decryptedData = DataEncryption.decryptData(emptyData)
        assertEquals(emptyData, decryptedData)
    }
}
