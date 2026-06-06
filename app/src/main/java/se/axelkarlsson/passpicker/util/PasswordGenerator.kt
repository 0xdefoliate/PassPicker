package se.axelkarlsson.passpicker.util

import java.security.SecureRandom

private val ALPHABET = listOf(
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z'
)

private val SPECIAL_CHARACTERS = listOf(
    '!', '@', '#', '$', '%', '&', '?', '*'
)

object SecureRNG {
    fun randInt(bound: Int): Int {
        val rng = SecureRandom.getInstanceStrong()
        return rng.nextInt(bound)
    }

    fun <T>choice(list: List<T>): T {
        val index = randInt(list.count())
        return list[index]
    }
}

sealed class PasswordGeneratorException(message: String) : RuntimeException(message) {
    class PasswordLengthUnmatched(expected: Int, actual: Int) :
        PasswordGeneratorException("Password length was expected to be: $expected, but was actually: $actual.")

    class PasswordLengthBelowMinimum(min: Int) :
        PasswordGeneratorException("Password length is below the minimum of $min characters!")
}


class PasswordGenerator {
    interface Option {
        fun random(): Char
    }

    object Alphabetic : Option {
        override fun random(): Char {
            return SecureRNG.choice(ALPHABET)
        }
    }

    object Numeric : Option {
        override fun random(): Char {
            return SecureRNG.randInt(10).digitToChar()
        }
    }

    object Special : Option {
        override fun random(): Char {
            return SecureRNG.choice(SPECIAL_CHARACTERS)
        }
    }

    fun generate(
        min: Int, length: Int, options: List<Option>
    ): String {
        var password: String = ""

        for (i in 1..length) {
            val choice = SecureRNG.choice(options)
            password += choice.random()
        }

        if (password.count() != length) {
            throw PasswordGeneratorException.PasswordLengthUnmatched(length, password.count())
        }

        if (length < min) {
            throw PasswordGeneratorException.PasswordLengthBelowMinimum(min)
        }

        return password
    }
}

