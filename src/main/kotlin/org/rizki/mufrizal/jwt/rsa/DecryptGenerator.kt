package org.rizki.mufrizal.jwt.rsa

import com.nimbusds.jose.JOSEException
import java.security.spec.InvalidKeySpecException
import java.security.NoSuchAlgorithmException
import org.rizki.mufrizal.jwt.rsa.reader.PrivateKeyReader
import com.nimbusds.jose.crypto.RSADecrypter
import com.nimbusds.jose.JWEObject
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
        fun generateDecrypt(encryptText: String, privateKey: String): String? {
            try {
                val jWEObject = JWEObject.parse(encryptText)
                val decrypter = RSADecrypter(PrivateKeyReader.get(privateKey)!!)
                jWEObject.decrypt(decrypter)
                return jWEObject.payload.toString()
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