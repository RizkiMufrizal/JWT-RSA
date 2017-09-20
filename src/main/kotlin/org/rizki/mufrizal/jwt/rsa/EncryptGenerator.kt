package org.rizki.mufrizal.jwt.rsa

import com.nimbusds.jose.EncryptionMethod
import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWEAlgorithm
import com.nimbusds.jose.JWEHeader
import com.nimbusds.jose.JWEObject
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.RSAEncrypter
import org.rizki.mufrizal.jwt.rsa.helpers.isEndSeparator
import org.rizki.mufrizal.jwt.rsa.readers.PublicKeyReader
import org.rizki.mufrizal.jwt.rsa.readers.PublicKeyReaderStorage
import java.io.File
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 14 September 2017
 * @Time 1:18 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa
 * @File EncryptGenerator
 *
 */
class EncryptGenerator {
    companion object {
        @JvmStatic
        fun generateEncrypt(plainText: String? = null, publicKey: String? = null): String? {
            try {
                val header = JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256CBC_HS512)
                val payload = Payload(plainText)
                val jweObject = JWEObject(header, payload)
                val publicKeyReader = PublicKeyReader.get(publicKey)
                val encrypted = RSAEncrypter(publicKeyReader)
                jweObject.encrypt(encrypted)
                return jweObject.serialize()
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
        fun generateEncrypt(plainText: String? = null, path: String? = null, fileName: String? = null): String? {
            try {
                val header = JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256CBC_HS512)
                val payload = Payload(plainText)
                val jweObject = JWEObject(header, payload)
                val pathSeparator = if (path?.isEndSeparator() == false) "$path${File.separator}$fileName" else "$path$fileName"
                val publicKeyReader = PublicKeyReaderStorage.get(pathSeparator)
                val encrypted = RSAEncrypter(publicKeyReader)
                jweObject.encrypt(encrypted)
                return jweObject.serialize()
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