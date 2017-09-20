package org.rizki.mufrizal.jwt.rsa.readers

import java.security.spec.InvalidKeySpecException
import java.security.NoSuchAlgorithmException
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 14 September 2017
 * @Time 1:19 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa.readers
 * @File PublicKeyReader
 *
 */
class PublicKeyReader {
    companion object {
        @JvmStatic
        fun get(publicKey: String? = null): RSAPublicKey? {
            var publicKeyContent = publicKey
            try {
                publicKeyContent = publicKeyContent?.replace("\\n".toRegex(), "")?.replace("-----BEGIN PUBLIC KEY-----", "")?.replace("-----END PUBLIC KEY-----", "")
                val kf = KeyFactory.getInstance("RSA")
                val keySpecX509 = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent))
                return kf.generatePublic(keySpecX509) as RSAPublicKey
            } catch (ex: NoSuchAlgorithmException) {
                ex.printStackTrace()
            } catch (ex: InvalidKeySpecException) {
                ex.printStackTrace()
            }
            return null
        }
    }
}