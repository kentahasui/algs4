import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class CircularSuffixArrayTest {
    private CircularSuffixArray suffixes;

    ////////////////////////////////////////////////////////////////////////////
    // Ctor_Exceptions()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void ctor_sIsNull_throws(){
        suffixes = new CircularSuffixArray(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // length()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void length_sIsEmpty_0(){
        suffixes = new CircularSuffixArray("");
        assertThat(suffixes.length()).isEqualTo(0);
    }

    @Test
    public void length_1(){
        suffixes = new CircularSuffixArray("a");
        assertThat(suffixes.length()).isEqualTo(1);
    }

    @Test
    public void length_2(){
        suffixes = new CircularSuffixArray("za");
        assertThat(suffixes.length()).isEqualTo(2);
    }

    @Test
    public void length_3(){
        suffixes = new CircularSuffixArray("cab");
        assertThat(suffixes.length()).isEqualTo(3);
    }

    @Test
    public void length_13(){
        suffixes = new CircularSuffixArray("quadrilateral");
        assertThat(suffixes.length()).isEqualTo(13);
    }

    ////////////////////////////////////////////////////////////////////////////
    // index()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void index_sIsEmpty_throws(){
        suffixes = new CircularSuffixArray("");
        suffixes.index(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void index_negative_throws(){
        suffixes = new CircularSuffixArray("%^&*");
        suffixes.index(-7);
    }

    @Test(expected = IllegalArgumentException.class)
    public void index_tooLong_throws(){
        suffixes = new CircularSuffixArray("%^&*");
        suffixes.index(4);
    }

    @Test
    public void index_lengthOne_always0(){
        suffixes = new CircularSuffixArray("Z");
        assertThat(suffixes.index(0)).isEqualTo(0);
    }

    @Test
    public void index_length2_alreadySorted(){
        suffixes = new CircularSuffixArray("ab");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
    }

    @Test
    public void index_length2_notSorted(){
        suffixes = new CircularSuffixArray("ba");
        assertThat(suffixes.index(0)).isEqualTo(1);
        assertThat(suffixes.index(1)).isEqualTo(0);
    }

    @Test
    public void index_length3_sorted(){
        suffixes = new CircularSuffixArray("abc");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(2)).isEqualTo(2);
    }

    @Test
    public void index_length3_allRepeated(){
        suffixes = new CircularSuffixArray("zzz");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(2)).isEqualTo(2);
    }

    @Test
    public void index_length3_twoRepeated(){
        suffixes = new CircularSuffixArray("ZAZ");
        assertThat(suffixes.index(0)).isEqualTo(1);
        assertThat(suffixes.index(1)).isEqualTo(0);
        assertThat(suffixes.index(2)).isEqualTo(2);
    }

    @Test
    public void index_hello(){
        suffixes = new CircularSuffixArray("hello");
        assertThat(suffixes.index(0)).isEqualTo(1);
        assertThat(suffixes.index(1)).isEqualTo(0);
        assertThat(suffixes.index(2)).isEqualTo(2);
        assertThat(suffixes.index(3)).isEqualTo(3);
        assertThat(suffixes.index(4)).isEqualTo(4);
    }

    @Test
    public void index_abracadabra(){
        suffixes = new CircularSuffixArray("abracadabra!");
        assertThat(suffixes.index(0)).isEqualTo(11);
        assertThat(suffixes.index(3)).isEqualTo(0);
    }

    @Test
    public void index_length15_sorted(){
        suffixes = new CircularSuffixArray("ABCDEFGHIJKLMNO");
        assertThat(suffixes.length()).isEqualTo(15);
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(14)).isEqualTo(14);
    }

    @Test
    public void index_length15_antisorted(){
        suffixes = new CircularSuffixArray( "ONMLKJIHGFEDCBA");
//        suffixes = new CircularSuffixArray( "ABCDEFGHIJKLMNO");
        assertThat(suffixes.length()).isEqualTo(15);
        assertThat(suffixes.index(0)).isEqualTo(14);
        assertThat(suffixes.index(1)).isEqualTo(13);
        assertThat(suffixes.index(14)).isEqualTo(0);
    }

    @Test
    public void index_length16_sorted(){
        suffixes = new CircularSuffixArray("ABCDEFGHIJKLMNOP");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(14)).isEqualTo(14);
        assertThat(suffixes.index(15)).isEqualTo(15);
    }

    @Test
    public void index_length17_sorted(){
        suffixes = new CircularSuffixArray("ABCDEFGHIJKLMNOPQ");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(14)).isEqualTo(14);
        assertThat(suffixes.index(15)).isEqualTo(15);
    }

    @Test
    public void index_length17_someSame(){
        suffixes = new CircularSuffixArray("ABCDDDDHIJKLMNPPP");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(14)).isEqualTo(16);
        assertThat(suffixes.index(15)).isEqualTo(15);
        assertThat(suffixes.index(16)).isEqualTo(14);
    }

    @Test
    public void index_length20_allSame(){
        suffixes = new CircularSuffixArray("ZZZZZZZZZZZZZZZZZZZZ");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(19)).isEqualTo(19);
    }

    @Test
    public void index_length20_allVaried(){
        suffixes = new CircularSuffixArray("ZZZZZZZZZZZZZZZZZZZZ");
        assertThat(suffixes.index(0)).isEqualTo(0);
    }

    @Test
    public void index_cabacb(){
        suffixes = new CircularSuffixArray("cabacb");
        assertThat(suffixes.index(0)).isEqualTo(1);
        assertThat(suffixes.index(1)).isEqualTo(3);
        assertThat(suffixes.index(2)).isEqualTo(2);
        assertThat(suffixes.index(3)).isEqualTo(5);
        assertThat(suffixes.index(4)).isEqualTo(0);
        assertThat(suffixes.index(5)).isEqualTo(4);
    }

    @Test
    public void index_alphanum(){
        suffixes = new CircularSuffixArray("abcdefghijklmnopqrstuvwxyz0123456789");
        assertThat(suffixes.length()).isEqualTo(36);
        assertThat(suffixes.index(0)).isEqualTo(26);
        assertThat(suffixes.index(10)).isEqualTo(0);
        assertThat(suffixes.index(35)).isEqualTo(25);
    }

    @Test
    public void index_cadabra(){
        suffixes = new CircularSuffixArray("CADABRA!ABRA");
        assertThat(suffixes.index(0)).isEqualTo(7);
        assertThat(suffixes.index(8)).isEqualTo(0);
    }

    @Test
    public void index_couscous(){
        suffixes = new CircularSuffixArray("couscous");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(4);
        assertThat(suffixes.index(2)).isEqualTo(1);
        assertThat(suffixes.index(3)).isEqualTo(5);
        assertThat(suffixes.index(4)).isEqualTo(3);
        assertThat(suffixes.index(5)).isEqualTo(7);
        assertThat(suffixes.index(6)).isEqualTo(2);
        assertThat(suffixes.index(7)).isEqualTo(6);
    }

    @Test
    public void index_encodedSecretMessage(){
        suffixes = new CircularSuffixArray("\u0000\u0000\u0000\u000Fdead:  hnovwek )eeoga ");
    }

    @Test
    public void index_stars(){
        suffixes = new CircularSuffixArray("*************");
        assertThat(suffixes.index(0)).isEqualTo(0);
        assertThat(suffixes.index(1)).isEqualTo(1);
        assertThat(suffixes.index(12)).isEqualTo(12);
    }

    @Test
    public void index_zebra(){
        suffixes = new CircularSuffixArray("zebra");
        assertThat(suffixes.index(0)).isEqualTo(4);
        assertThat(suffixes.index(1)).isEqualTo(2);
        assertThat(suffixes.index(2)).isEqualTo(1);
        assertThat(suffixes.index(3)).isEqualTo(3);
        assertThat(suffixes.index(4)).isEqualTo(0);
    }


}
