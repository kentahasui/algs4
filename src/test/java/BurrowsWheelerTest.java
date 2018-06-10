import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests for BurrowsWheeler.
 * You MUST run each test individually.
 * This is because BinaryStdIn and BinaryStdOut use static variables and
 * static methods, which we cannot mock without using powermockito, which is
 * quite slow.
 */
public class BurrowsWheelerTest {
    private ByteArrayOutputStream out;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    ////////////////////////////////////////////////////////////////////////////
    // transform()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void transform_isEmpty() {
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toString()).isEmpty();
    }

    @Test
    public void transform_a() {
        System.setIn(new ByteArrayInputStream("a".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                0, 0, 0, 0, // 1st four bytes == number
                'a'         // next bytes == ascii value
        });
    }


    @Test
    public void transform_aa() {
        System.setIn(new ByteArrayInputStream("aa".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'a', 'a'
        });
    }

    @Test
    public void transform_ab() {
        System.setIn(new ByteArrayInputStream("ab".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'b', 'a'
        });
    }

    @Test
    public void transform_ba() {
        System.setIn(new ByteArrayInputStream("ba".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 1,
                // Rest of bytes == chars
                'b', 'a'
        });
    }

    @Test
    public void transform_cab() {
        System.setIn(new ByteArrayInputStream("cab".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 2,
                // Rest of bytes == chars
                'c', 'a', 'b'
        });
    }

    @Test
    public void transform_cabacb() {
        System.setIn(new ByteArrayInputStream("cabacb".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 4,
                // Rest of bytes == chars
                'c', 'b', 'a', 'c', 'b', 'a'
        });
    }

    @Test
    public void transform_allRepeated() {
        System.setIn(new ByteArrayInputStream("zzz".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'z', 'z', 'z'
        });
    }

    @Test
    public void transform_hello() {
        System.setIn(new ByteArrayInputStream("hello".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 1,
                // Rest of bytes == chars
                'h', 'o', 'e', 'l', 'l'
        });
    }

    @Test
    public void transform_abracadabra() {
        System.setIn(new ByteArrayInputStream("abracadabra!".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 3,
                // Rest of bytes == chars
                'a', 'r', 'd', '!', 'r', 'c', 'a', 'a', 'a', 'a', 'b' ,'b'
        });
    }

    @Test
    public void transform_stars() {
        System.setIn(new ByteArrayInputStream("*****".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                '*', '*', '*', '*', '*'
        });
    }

    @Test
    public void transform_couscous(){
        System.setIn(new ByteArrayInputStream("couscous".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                's', 's', 'c', 'c', 'u', 'u', 'o', 'o'
        });
    }

    @Test
    public void transform_zebra(){
        System.setIn(new ByteArrayInputStream("zebra".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 4,
                // Rest of bytes == chars
                'r', 'e', 'z', 'b', 'a'
        });
    }

    @Test
    public void transform_20_Zs(){
        System.setIn(new ByteArrayInputStream("ZZZZZZZZZZZZZZZZZZZZ".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'Z','Z','Z','Z','Z','Z','Z','Z','Z','Z',
                'Z','Z','Z','Z','Z','Z','Z','Z','Z','Z',
        });
    }

    @Test
    public void transform_17s(){
        System.setIn(new ByteArrayInputStream("ABCDEFGHIJKLMNOPQ".getBytes()));

        // Do work
        BurrowsWheeler.transform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                81, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    // inverseTransform()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void inverseTransform_a() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'a'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("a".getBytes());
    }

    @Test
    public void inverseTransform_aa() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'a', 'a'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("aa".getBytes());
    }

    @Test
    public void inverseTransform_ab() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'b', 'a'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("ab".getBytes());
    }


    @Test
    public void inverseTransform_ba() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 1,
                // Rest of bytes == chars
                'b', 'a'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("ba".getBytes());
    }

    @Test
    public void inverseTransform_cab() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 2,
                // Rest of bytes == chars
                'c', 'a', 'b'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("cab".getBytes());
    }

    @Test
    public void inverseTransform_cabacb() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 4,
                // Rest of bytes == chars
                'c', 'b', 'a', 'c', 'b', 'a'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("cabacb".getBytes());
    }

    @Test
    public void inverseTransform_abracadabra() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 3,
                // Rest of bytes == chars
                'a', 'r', 'd', '!', 'r', 'c', 'a', 'a' ,'a', 'a', 'b', 'b'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("abracadabra!".getBytes());
    }

    @Test
    public void inverseTransform_allRepeated() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 1,
                // Rest of bytes == chars
                'z', 'z', 'z'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("zzz".getBytes());
    }


    @Test
    public void inverseTransform_couscous() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                's', 's', 'c', 'c', 'u', 'u', 'o', 'o'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("couscous".getBytes());
    }

    @Test
    public void inverseTransform_hello() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 1,
                // Rest of bytes == chars
                'h', 'o', 'e', 'l', 'l'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("hello".getBytes());
    }

    @Test
    public void inverseTransform_zebra() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 4,
                // Rest of bytes == chars
                'r', 'e', 'z', 'b', 'a'
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("zebra".getBytes());
    }

    @Test
    public void inverseTransform_20_Zs() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'Z','Z','Z','Z','Z','Z','Z','Z','Z','Z',
                'Z','Z','Z','Z','Z','Z','Z','Z','Z','Z',
        }));

        // Do work
        BurrowsWheeler.inverseTransform();

        // Assert
        assertThat(out.toByteArray()).isEqualTo("ZZZZZZZZZZZZZZZZZZZZ".getBytes());
    }


    @Test
    public void main_ab_transform() {
        System.setIn(new ByteArrayInputStream("0ba".getBytes()));

        // Do work
        BurrowsWheeler.main(new String[]{"+"});

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'b', 'a'
        });
    }

    @Test
    public void main_ab_inverseTransform() {
        System.setIn(new ByteArrayInputStream(new byte[]{
                // 1st four bytes == index
                0, 0, 0, 0,
                // Rest of bytes == chars
                'b', 'a'
        }));

        // Do work
        BurrowsWheeler.main(new String[]{"+"});

        // Assert
        assertThat(out.toByteArray()).isEqualTo("ab".getBytes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void main_ab_invalidArg() {
        // Do work
        BurrowsWheeler.main(new String[]{"+++"});

    }
}
