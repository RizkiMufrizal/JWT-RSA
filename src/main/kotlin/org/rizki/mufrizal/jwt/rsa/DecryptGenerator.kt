package org.rizki.mufrizal.jwt.rsa

import com.nimbusds.jose.JOSEException
import java.security.spec.InvalidKeySpecException
import java.security.NoSuchAlgorithmException
import org.rizki.mufrizal.jwt.rsa.readers.PrivateKeyReader
import com.nimbusds.jose.crypto.RSADecrypter
import com.nimbusds.jose.JWEObject
import org.rizki.mufrizal.jwt.rsa.helpers.isEndSeparator
import org.rizki.mufrizal.jwt.rsa.readers.PrivateKeyReaderStorage
import java.io.File
import java.text.ParseException

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 14 September 2017
 * @Time 1:19 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa
 * @File DecryptGenerator
 *
 */
class DecryptGenerator {
    companion object {
        @JvmStatic
        fun generateDecrypt(encryptText: String? = null, privateKey: String? = null): String? {
            try {
                val jweObject = JWEObject.parse(encryptText)
                val privateKeyReader = PrivateKeyReader.get(privateKey)
                val decrypted = RSADecrypter(privateKeyReader)
                jweObject.decrypt(decrypted)
                return jweObject.payload.toString()
            } catch (ex: ParseException) {
                ex.printStackTrace()
            } catch (ex: NoSuchAlgorithmException) {
                ex.printStackTrace()
            } catch (ex: InvalidKeySpecException) {
                ex.printStackTrace()
            } catch (ex: JOSEException) {
                ex.printStackTrace()
            }
            return null
        }

        @JvmStatic
        fun generateDecrypt(encryptText: String? = null, path: String? = null, fileName: String? = null): String? {
            try {
                val jweObject = JWEObject.parse(encryptText)
                val pathSeparator = if (path?.isEndSeparator() == false) "$path${File.separator}$fileName" else "$path$fileName"
                val privateKeyReader = PrivateKeyReaderStorage.get(pathSeparator)
                val decrypted = RSADecrypter(privateKeyReader)
                jweObject.decrypt(decrypted)
                return jweObject.payload.toString()
            } catch (ex: ParseException) {
                ex.printStackTrace()
            } catch (ex: NoSuchAlgorithmException) {
                ex.printStackTrace()
            } catch (ex: InvalidKeySpecException) {
                ex.printStackTrace()
            } catch (ex: JOSEException) {
                ex.printStackTrace()
            }
            return null
        }
    }
}