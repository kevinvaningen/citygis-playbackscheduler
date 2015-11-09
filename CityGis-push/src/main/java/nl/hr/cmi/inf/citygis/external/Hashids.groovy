package nl.hr.cmi.inf.citygis.external
/**
 *
 * This is an implementation of http://hashids.org version 1.0.0.
 *
 * @author cmaron <cmaron@gmail.com>
 * @since 0.3.3
 */
class Hashids {
    public static final String DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    public static final int MIN_ALPHABET_LENGTH = 16
    public static final String VERSION = "0.3.3"

    private String salt = ""
    private String alphabet = ""
    private int alphabetLen = 0
    private String seps = "cfhistuCFHISTU"
    private int minHashLength = 0
    private double sepDiv = 3.5
    private int guardDiv = 12
    private String guards
    private int guardsLen = 0

    /**
     * Create an instance of Hashids with the given salt, no min hash length,
     * and the default alphabet (Hashids.DEFAULT_ALPHABET).
     *
     * @param salt the desired salt. Defaults to ""
     */
    public Hashids(salt = "") {
        this(salt, 0)
    }

    /**
     * Create an instance of Hashids with the given salt, min hash length,
     * and the default alphabet (Hashids.DEFAULT_ALPHABET).
     *
     * @param salt the desired salt
     * @param minHashLength the minimum hash length
     */
    public Hashids(String salt, int minHashLength) {
        this(salt, minHashLength, DEFAULT_ALPHABET)
    }

    /**
     * Create an instance of Hashids with the given salt, min hash length,
     * and alphabet.
     *
     * @param salt the desired salt
     * @param minHashLength the minimum hash length
     * @param alphabet the desired alphabet
     * @throws IllegalArgumentException thrown if the alphabet is too short or contains illegal characters.
     */
    public Hashids(String salt, int minHashLength, String alphabet) throws IllegalArgumentException {
        this.salt = salt
        this.minHashLength = (minHashLength < 0) ? 0 : minHashLength
        this.alphabet = alphabet.toList().unique().join('')

        if (this.alphabet.length() < MIN_ALPHABET_LENGTH) {
            throw new IllegalArgumentException("alphabet must contain at least " + MIN_ALPHABET_LENGTH + " unique characters")
        }

        if (this.alphabet.contains(" ")) {
            throw new IllegalArgumentException("alphabet cannot contains spaces")
        }

        // Ensure the separators are part of the given alphabet, and the alphabet does not contain the separators.
        this.seps = this.seps.replaceAll(/[^${this.alphabet}]/, '')
        this.alphabet = this.alphabet.replaceAll(/[${this.seps}]/, '')

        this.alphabetLen = this.alphabet.length()
        this.seps = this.consistentShuffle(this.seps, this.salt)

        if ((this.seps == "") || ((this.alphabetLen / this.seps.length()) > this.sepDiv)) {
            int seps_len = (int) Math.ceil(this.alphabetLen / this.sepDiv)

            if (seps_len == 1) {
                seps_len++
            }

            if (seps_len > this.seps.length()) {
                int diff = seps_len - this.seps.length()
                this.seps += this.alphabet.substring(0, diff)
                this.alphabet = this.alphabet.substring(diff)
            } else {
                this.seps = this.seps.substring(0, seps_len)
            }
        }

        this.alphabet = this.consistentShuffle(this.alphabet, this.salt)
        int guardCount = (int) Math.ceil(this.alphabetLen / this.guardDiv)

        if (this.alphabet.length() < 3) {
            this.guards = this.seps.substring(0, guardCount)
            this.guardsLen = this.guards.length()
            this.seps = this.seps.substring(guardCount)
        } else {
            this.guards = this.alphabet.substring(0, guardCount)
            this.guardsLen = this.guards.length()
            this.alphabet = this.alphabet.substring(guardCount)
        }
    }

    /**
     * Encode numbers to string
     *
     * @param numbers the numbers to encrypt
     * @return the encoded string
     */
    public String encode(long ... numbers) {
        if (numbers.length == 0) {
            return ""
        }

        int numberHashInt = 0
        numbers.length.times() {
            numberHashInt += (numbers[it] % (it + 100))
        }

        String alphabet = this.alphabet
        char ret = alphabet[numberHashInt % alphabet.length()]
        char lottery = ret
        long num
        int guardIndex
        String buffer, last, ret_str = ret.toString() + ""
        char guard

        numbers.length.times() {
            num = numbers[it]
            buffer = lottery.toString() + this.salt + alphabet
            alphabet = this.consistentShuffle(alphabet, buffer.substring(0, alphabet.length()))
            last = this.hash(num, alphabet)

            ret_str += last

            if (it + 1 < numbers.length) {
                num %= (last[0].toCharacter() + it)
                ret_str += this.seps[(num % this.seps.length()).toInteger()]
            }
        }

        if (ret_str.length() < this.minHashLength) {
            guardIndex = (numberHashInt + (int) (ret_str[0])) % this.guardsLen
            guard = this.guards[guardIndex]

            ret_str = guard.toString() + ret_str

            if (ret_str.length() < this.minHashLength) {
                guardIndex = (numberHashInt + (int) (ret_str[2])) % this.guardsLen
                guard = this.guards[guardIndex]

                ret_str += guard
            }
        }

        int halfLen = (int) (alphabet.length() / 2)
        while (ret_str.length() < this.minHashLength) {
            alphabet = this.consistentShuffle(alphabet, alphabet)
            ret_str = alphabet.substring(halfLen) + ret_str + alphabet.substring(0, halfLen)
            int excess = ret_str.length() - this.minHashLength
            if (excess > 0) {
                int start_pos = excess / 2
                ret_str = ret_str.substring(start_pos, start_pos + this.minHashLength)
            }
        }

        return ret_str
    }

    /**
     * Decode string to numbers
     *
     * @param hash the encoded string
     *
     * @return the decoded numbers
     */
    public List<Long> decode(String hash) {
        def alphabet = this.alphabet
        List<Long> ret = []

        List<String> hashList = hash.split(/[${this.guards}]/)

        int i = (hashList.size() == 3 || hashList.size() == 2) ? 1 : 0

        String hashBreakdown = hashList[i]

        char lottery = hashBreakdown[0]
        hashBreakdown = hashBreakdown.substring(1)
        hashList = hashBreakdown.split(/[${this.seps}]/)

        int alphabetLen = alphabet.length()
        String buffer
        hashList.each() {
            buffer = lottery.toString() + this.salt + alphabet
            alphabet = this.consistentShuffle(alphabet, buffer.substring(0, alphabetLen))
            ret.add(this.unhash(it, alphabet))
        }

        return ret
    }

    /* Private methods */

    /**
     *
     * @param alphabet
     * @param salt
     * @return
     */
    private String consistentShuffle(String alphabet, String salt) {
        if (salt.length() <= 0)
            return alphabet

        int asc_val, j, v = 0, p = 0
        char tmp
        for (i in (alphabet.length() - 1)..1) {
            v %= salt.length()
            asc_val = (int) salt[v]
            p += asc_val
            j = (asc_val + v + p) % i
            v++

            tmp = alphabet[j]
            alphabet = alphabet.substring(0, j) + alphabet[i] + alphabet.substring(j + 1)
            alphabet = alphabet.substring(0, i) + tmp + alphabet.substring(i + 1)
        }

        return alphabet
    }

    /**
     *
     * @param input
     * @param alphabet
     * @return
     */
    private String hash(long input, String alphabet) {
        String hash = ""
        int alphabetLen = alphabet.length()

        while (input > 0) {
            hash = alphabet[(input % alphabetLen).toInteger()] + hash
            input = (input / alphabetLen)
        }

        return hash
    }

    /**
     *
     * @param input
     * @param alphabet
     * @return
     */
    private Long unhash(String input, String alphabet) {
        long number = 0, pos
        int alphabetLen = alphabet.length()

        input.length().times {
            pos = alphabet.indexOf(input[it])
            number += pos * Math.pow(alphabetLen, input.length() - it - 1)
        }

        return number
    }

}
