import javafx.scene.shape.MoveTo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.google.common.truth.Truth.assertThat;

public class MoveToFrontTest {
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
    // encode()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void encode_from_BW_a(){
        System.setIn(new ByteArrayInputStream(new byte[]{
                0, 0, 0, 0, // 1st four bytes == number
                'a'         // next bytes == ascii value
        }));
        MoveToFront.encode();
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                0, 0, 0, 0, 97
        });
    }


    @Test
    public void encode_empty(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("".getBytes()));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toString()).isEmpty();
    }

    @Test
    public void encode_a(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("a".getBytes()));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                97
        });
    }

    @Test
    public void encode_aa(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("aa".getBytes()));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                97,
                0
        });
    }

    @Test
    public void encode_null(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{0}));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                0,
        });
    }

    @Test
    public void encode_null_null(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{0, 0}));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                0,
                0,
        });
    }

    @Test
    public void encode_abab(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("abab".getBytes()));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                97,
                98,
                1,
                1
        });
    }

    @Test
    public void encode_za(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("za@".getBytes()));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                122,
                98,
                66
        });
    }

    @Test
    public void encode_caaabcccaccf(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("caaabcccaccf".getBytes()));

        // Do work
        MoveToFront.encode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                99,
                98,
                0,
                0,
                99,
                2,
                0,
                0,
                2,
                1,
                0,
                102
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    // decode()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void decode_empty(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("".getBytes()));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toString()).isEmpty();
    }

    @Test
    public void decode_a(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                97
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toString()).isEqualTo("a");
    }

    @Test
    public void decode_aa(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                97,
                0
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toString()).isEqualTo("aa");
    }

    @Test
    public void decode_abab(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                97,
                98,
                1,
                1
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toString()).isEqualTo("abab");
    }

    @Test
    public void decode_caaabcccaccf(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                99,
                98,
                0,
                0,
                99,
                2,
                0,
                0,
                2,
                1,
                0,
                102
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toString()).isEqualTo("caaabcccaccf");
    }


    @Test
    public void decode_null(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                0
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{0});
    }

    @Test
    public void decode_null_null(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                0,
                0
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{0, 0});
    }

    @Test
    public void decode_za(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                122,
                98,
        }));

        // Do work
        MoveToFront.decode();

        // Assert
        assertThat(out.toString()).isEqualTo("za");
    }

    ////////////////////////////////////////////////////////////////////////////
    // main()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void main_encode_za(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream("za@".getBytes()));

        // Do work
        MoveToFront.main(new String[]{"-"});

        // Assert
        assertThat(out.toByteArray()).isEqualTo(new byte[]{
                122,
                98,
                66
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void main_illegalArg(){
        // Do work
        MoveToFront.main(new String[]{"PLUS"});
    }

    @Test
    public void main_decode_abab(){
        // Mock input from stdin
        System.setIn(new ByteArrayInputStream(new byte[]{
                97,
                98,
                1,
                1
        }));

        // Do work
        MoveToFront.main(new String[]{"+"});

        // Assert
        assertThat(out.toString()).isEqualTo("abab");
    }
}
