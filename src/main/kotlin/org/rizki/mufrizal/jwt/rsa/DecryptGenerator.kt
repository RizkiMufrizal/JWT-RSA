package org.rizki.mufrizal.jwt.rsa

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.crypto.RSADecrypter
import com.nimbusds.jwt.EncryptedJWT
import org.rizki.mufrizal.jwt.rsa.reader.PrivateKeyReader
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
                val jwt = EncryptedJWT.parse(encryptText)

                val decrypter = RSADecrypter(PrivateKeyReader.get(privateKey))

                jwt.decrypt(decrypter)

                return jwt.jwtClaimsSet.audience[0]
            } catch (ex: ParseException) {
                ex.printStackTrace()
            } catch (ex: JOSEException) {
                ex.printStackTrace()
            }

            return null
        }

    }
}