package org.rizki.mufrizal.jwt.rsa.helpers

import java.io.File

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 20 September 2017
 * @Time 7:57 PM
 * @Project JWT-RSA
 * @Package org.rizki.mufrizal.jwt.rsa.helpers
 * @File Lang
 *
 */

fun String.isEndSeparator(): Boolean? {
    return this.reversed().startsWith(File.separator)
}