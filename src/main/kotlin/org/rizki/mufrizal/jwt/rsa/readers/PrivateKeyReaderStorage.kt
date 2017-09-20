package org.rizki.mufrizal.jwt.rsa.readers

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.interfaces.RSAPrivateKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 19 September 2017
 * @Time 10:47 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa.readers
 * @File PrivateKeyReaderStorage
 *
 */
class PrivateKeyReaderStorage {
    companion object {
        @JvmStatic
        fun get(fileName: String? = null): RSAPrivateKey? {
            try {
                val keyBytes = Files.readAllBytes(Paths.get(fileName))
                val privateKeyReplace = keyBytes.toString(Charset.forName("UTF-8")).replace("\\n".toRegex(), "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "")
                val kf = KeyFactory.getInstance("RSA")
                val keySpecPKCS8 = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyReplace))
                return kf.generatePrivate(keySpecPKCS8) as RSAPrivateKey
            } catch (ex: NoSuchAlgorithmException) {
                ex.printStackTrace()
            } catch (ex: InvalidKeySpecException) {
                ex.printStackTrace()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }
    }
}