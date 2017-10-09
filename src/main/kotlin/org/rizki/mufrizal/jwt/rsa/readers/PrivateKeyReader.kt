package org.rizki.mufrizal.jwt.rsa.readers

import java.security.spec.InvalidKeySpecException
import java.security.NoSuchAlgorithmException
import java.security.interfaces.RSAPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.KeyFactory
import java.util.Base64

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 14 September 2017
 * @Time 1:19 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa.readers
 * @File PrivateKeyReader
 *
 */
class PrivateKeyReader {
    companion object {
        @JvmStatic
        fun get(privateKey: String? = null): RSAPrivateKey? {
            var privateKeyContent = privateKey
            try {
                privateKeyContent = privateKeyContent?.replace("\\n".toRegex(), "")?.replace("-----BEGIN PRIVATE KEY-----", "")?.replace("-----END PRIVATE KEY-----", "")
                privateKeyContent = privateKeyContent?.replace("\\n".toRegex(), "")?.replace("-----BEGIN RSA PRIVATE KEY-----", "")?.replace("-----END RSA PRIVATE KEY-----", "")
                val kf = KeyFactory.getInstance("RSA")
                val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent))
                return kf.generatePrivate(keySpecPKCS8) as RSAPrivateKey
            } catch (ex: NoSuchAlgorithmException) {
                ex.printStackTrace()
            } catch (ex: InvalidKeySpecException) {
                ex.printStackTrace()
            }
            return null
        }
    }
}