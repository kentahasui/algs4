import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class BoggleTrieTest {
    private BoggleTrie trie = new BoggleTrie();

    ////////////////////////////////////////////////////////////////////////////
    // Ctor()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void ctor(){
        trie = new BoggleTrie();
    }

    ////////////////////////////////////////////////////////////////////////////
    // add()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void add_null_throws(){
        trie.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_a_throws(){
        trie.add("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_ab_throws(){
        trie.add("ab");
    }


    @Test(expected = IllegalArgumentException.class)
    public void add_z_throws(){
        trie.add("z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_at_throws(){
        trie.add("@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_leftBracket_throws(){
        trie.add("[");
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_rightBracket_throws(){
        trie.add("]");
    }

    @Test
    public void add_empty(){
        trie.add("");
        assertThat(trie.getWords()).isEmpty();
    }

    @Test
    public void add_A(){
        trie.add("A");
        assertThat(trie.getWords()).containsExactly("A");
    }

    @Test
    public void add_AA(){
        trie.add("AA");
        assertThat(trie.getWords()).containsExactly("AA");
    }

    @Test
    public void add_Z(){
        trie.add("Z");
        assertThat(trie.getWords()).containsExactly("Z");
    }

    @Test
    public void add_A_AA_AZ_ZZ_Z(){
        trie.add("A");
        trie.add("AA");
        trie.add("AZ");
        trie.add("ZZ");
        trie.add("Z");
        assertThat(trie.getWords()).containsExactly(
                "A", "AA", "AZ", "ZZ", "Z");
    }

    @Test
    public void add_dedupe(){
        trie.add("DUPLICATE");
        trie.add("DUPLICATE");
        assertThat(trie.getWords()).containsExactly("DUPLICATE");
    }

    @Test
    public void add_firstWordIsPrefixOfSecondWord(){
        trie.add("APP");
        trie.add("APPLE");
        assertThat(trie.getWords()).containsExactly("APP", "APPLE");
    }

    @Test
    public void add_secondWordIsPrefixOfFirstWord(){
        trie.add("APPLE");
        trie.add("APP");
        assertThat(trie.getWords()).containsExactly("APP", "APPLE");
    }

    @Test
    public void add_wordsDifferByEndLetter(){
        trie.add("TON");
        trie.add("TOP");
        assertThat(trie.getWords()).containsExactly("TON", "TOP");
    }

    @Test
    public void add_wordsDifferByFirstLetter(){
        trie.add("FUN");
        trie.add("RUN");
        assertThat(trie.getWords()).containsExactly("FUN", "RUN");
    }

    @Test
    public void add_wordsDifferByMiddleLetter(){
        trie.add("QUEUE");
        trie.add("QUAUE");
        assertThat(trie.getWords()).containsExactly("QUEUE", "QUAUE");
    }

    ////////////////////////////////////////////////////////////////////////////
    // contains()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void contains_null_throws(){
        trie.contains(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_a_throws(){
        trie.contains("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_ab_throws(){
        trie.contains("ab");
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_z_throws(){
        trie.contains("z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_at_throws(){
        trie.contains("@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_leftBracket_throws(){
        trie.contains("[");
    }

    @Test(expected = IllegalArgumentException.class)
    public void contains_rightBracket_throws(){
        trie.contains("]");
    }

    @Test
    public void contains_emptyString_false(){
        assertThat(trie.contains("")).isFalse();
    }

    @Test
    public void contains_A_false(){
        trie.contains("A");
        assertThat(trie.contains("A")).isFalse();
    }

    @Test
    public void contains_A_true(){
        trie.add("A");
        assertThat(trie.contains("A")).isTrue();
    }

    @Test
    public void contains_AA_true(){
        trie.add("A");
        trie.add("AA");
        trie.add("AAA");
        trie.contains("AA");
        assertThat(trie.contains("AA")).isTrue();
    }

    @Test
    public void contains_AA_false(){
        trie.add("A");
        trie.add("AAA");
        assertThat(trie.contains("AA")).isFalse();
    }

    @Test
    public void contains_Z_true(){
        trie.add("Z");
        assertThat(trie.contains("Z")).isTrue();
    }

    @Test
    public void contains_A_AA_AZ_ZZ_Z(){
        trie.add("A");
        trie.add("AA");
        trie.add("AZ");
        trie.add("ZZ");
        trie.add("Z");
        assertThat(trie.contains("ZZ")).isTrue();
    }

    @Test
    public void contains_dedupe(){
        trie.add("DUPLICATE");
        trie.add("DUPLICATE");
        assertThat(trie.contains("DUPLICATE")).isTrue();
    }

    @Test
    public void contains_firstWordIsPrefixOfSecondWord(){
        trie.add("APP");
        trie.add("APPLE");
        assertThat(trie.contains("APP")).isTrue();
        assertThat(trie.contains("APPLE")).isTrue();
        assertThat(trie.contains("APPS")).isFalse();
    }

    @Test
    public void contains_wordsDifferByEndLetter(){
        trie.add("TON");
        trie.add("TOP");
        assertThat(trie.contains("TON")).isTrue();
    }

    @Test
    public void contains_wordsDifferByMiddleLetter(){
        trie.add("QUEUE");
        trie.add("QAEUE");
        assertThat(trie.contains("QAEUE")).isTrue();
        assertThat(trie.contains("QUEUE")).isTrue();
    }

    ////////////////////////////////////////////////////////////////////////////
    // isPrefix()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_null_throws(){
        trie.isPrefix(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_a_throws(){
        trie.isPrefix("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_ab_throws(){
        trie.isPrefix("ab");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_z_throws(){
        trie.isPrefix("z");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_at_throws(){
        trie.isPrefix("@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_leftBracket_throws(){
        trie.isPrefix("[");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isPrefix_rightBracket_throws(){
        trie.isPrefix("]");
    }

    @Test
    public void isPrefix_emptyDict_emptyString_false(){
        assertThat(trie.isPrefix("")).isFalse();
    }

    @Test
    public void isPrefix_nonEmptyDict_emptyString_true(){
        trie.add("HELLO");
        assertThat(trie.isPrefix("")).isTrue();
    }

    @Test
    public void isPrefix_wordIsWord_wordIsPrefix_true(){
        trie.add("LAW");
        trie.add("LAWYER");
        assertThat(trie.isPrefix("LAW")).isTrue();
    }

    @Test
    public void isPrefix_wordIsWord_wordIsNotPrefix_false(){
        trie.add("LAW");
        trie.add("LAMP");
        assertThat(trie.isPrefix("LAW")).isFalse();
    }

    @Test
    public void isPrefix_wordIsNotWord_wordIsPrefix_true(){
        trie.add("LAWYER");
        assertThat(trie.isPrefix("LAW")).isTrue();
    }

    @Test
    public void isPrefix_wordIsNotWord_wordIsNotPrefix_false(){
        trie.add("LATTER");
        trie.add("LAZY");
        assertThat(trie.isPrefix("LAW")).isFalse();
    }

    @Test
    public void isPrefix_isSuffix_false(){
        trie.add("SPLATTER");
        assertThat(trie.isPrefix("PLATTER")).isFalse();
    }

    @Test
    public void isPrefix_isNotPrefix_false(){
        trie.add("MATT");
        assertThat(trie.isPrefix("MATTER")).isFalse();
    }
}
