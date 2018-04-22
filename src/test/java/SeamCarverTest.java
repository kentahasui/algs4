import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import java.awt.*;

import static com.google.common.truth.Truth.assertThat;

public class SeamCarverTest {
    private SeamCarver seamCarver;
    private Picture picture;

    ////////////////////////////////////////////////////////////////////////////
    // Exception Testing
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////
    // Constructor
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void ctor_pictureIsNull_throws(){
        seamCarver = new SeamCarver(null);
    }

    ////////////////////////////
    // Energy
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void energy_xTooSmall(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.energy(-1, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void energy_xTooLarge(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.energy(3, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void energy_yTooSmall(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.energy(0, -2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void energy_yTooLarge(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.energy(0, 4);
    }

    ////////////////////////////
    // RemoveVerticalSeam
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_seamIsNull_throws(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_widthIsOne_throws(){
        picture = new Picture(1, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {1, 2};
        seamCarver.removeVerticalSeam(seam);
    }

    @Test
    public void removeVerticalSeam_heightIsOne_doesNotThrow(){
        picture = new Picture(5, 1);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {0};
        seamCarver.removeVerticalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_seamLengthZero_throws(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(new int[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_seamNotEqualToHeight_throws(){
        picture = new Picture(3, 10);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {0, 1, 2};
        seamCarver.removeVerticalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_seamEntriesDifferByMoreThanOne_throws(){
        picture = new Picture(4, 4);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {0, 1, 2, 9};
        seamCarver.removeVerticalSeam(seam);
    }

    ////////////////////////////
    // RemoveHorizontalSeam
    ////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_seamIsNull_throws(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_heightIsOne_throws(){
        picture = new Picture(10, 1);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {0, 1, 0};
        seamCarver.removeHorizontalSeam(seam);
    }

    @Test
    public void removeHorizontalSeam_widthIsOne_doesNotThrow(){
        picture = new Picture(1, 10);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {2};
        seamCarver.removeHorizontalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_seamLengthZero_throws(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[0]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_seamNotEqualToWidth_throws(){
        picture = new Picture(10, 3);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {0, 1, 2};
        seamCarver.removeHorizontalSeam(seam);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_seamEntriesDifferByMoreThanOne_throws(){
        picture = new Picture(4, 4);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[] {0, 1, 2, 9};
        seamCarver.removeHorizontalSeam(seam);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Immutability testing
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void immutable_modifyOriginalPicture_seamCarverRemainsUnchanged(){
        picture = new Picture(3, 3);
        // Top middle
        picture.set(1, 0, new Color(0, 0, 0));

        // Left middle
        picture.set(0, 1, new Color(0, 0, 0));

        // Right middle
        picture.set(2, 1, new Color(0, 0, 0));

        // Bottom middle
        picture.set(1, 2, new Color(0, 0, 0));

        seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.energy(1, 1)).isEqualTo(0.0);

        picture.set(1, 0, new Color(255, 255, 255));
        assertThat(seamCarver.energy(1, 1)).isEqualTo(0.0);
    }

    @Test
    public void immutable_modifyReturnValueOfPicture_seamCarverRemainsUnchanged(){
        picture = new Picture(3, 3);
        // Top middle
        picture.set(1, 0, new Color(0, 0, 0));

        // Left middle
        picture.set(0, 1, new Color(0, 0, 0));

        // Right middle
        picture.set(2, 1, new Color(0, 0, 0));

        // Bottom middle
        picture.set(1, 2, new Color(0, 0, 0));

        seamCarver = new SeamCarver(picture);

        // Get energy at middle
        assertThat(seamCarver.energy(1, 1)).isEqualTo(0.0);

        // Mutate return value
        Picture retVal = seamCarver.picture();
        retVal.set(1, 0, new Color(255, 255, 255));

        // Original picture should remain unchanged
        assertThat(seamCarver.energy(1, 1)).isEqualTo(0.0);
    }

    ////////////////////////////////////////////////////////////////////////////
    // Basic testing: width, height, picture
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void width(){
        picture = new Picture(1, 3);
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.width()).isEqualTo(1);
    }

    @Test
    public void height(){
        picture = new Picture(1, 3);
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.height()).isEqualTo(3);
    }

    @Test
    public void picture(){
        picture = new Picture(3, 3);
        picture.set(2,2, new Color(0, 1, 2));
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.picture().get(2, 2)).isEqualTo(
                new Color(0, 1, 2));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Energy()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void energy_oneByOne_returns1000(){
        picture = new Picture(1, 1);
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
    }

    @Test
    public void energy_twoByTwo_returns1000(){
        picture = new Picture(2, 2);
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(1, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(1, 1)).isEqualTo(1000.0);
    }

    @Test
    public void energy_threeByThree(){
        picture = new Picture(3, 3);
        picture.set(1, 0, new Color(0, 0, 0)); // top
        picture.set(1, 2, new Color(1, 1, 1)); // bottom
        picture.set(0, 1, new Color(0, 0, 0)); // left
        picture.set(2, 1, new Color(2, 2, 2)); // right
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 2)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(1, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(1, 2)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(2, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(2, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(2, 2)).isEqualTo(1000.0);

        // xGradSq = 1 + 1 + 1 = 3
        // yGradSq = 4 + 4 + 4 = 12
        // energy = root(15)
        assertThat(seamCarver.energy(1, 1)).isEqualTo(Math.sqrt(15));
    }

    @Test
    public void energy_noEnergyChange_returnsZero(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 2)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(1, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(1, 2)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(2, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(2, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(2, 2)).isEqualTo(1000.0);
        // All blanks = all 0s
        assertThat(seamCarver.energy(1, 1)).isEqualTo(0.0);
    }

    ////////////////////////////////////////////////////////////////////////////
    // findVerticalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void findVerticalSeam_1x1_zero(){
        picture = new Picture(1, 1);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_1x2_zeros(){
        picture = new Picture(1, 2);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_2x1_zero(){
        picture = new Picture(2, 1);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_2x2_any(){
        picture = new Picture(2, 2);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_2x3_any(){
        picture = new Picture(2, 3);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_3x2_any(){
        picture = new Picture(3, 2);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_3x3_passesThroughMiddle(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 1, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_3x4_passesThroughMiddle(){
        picture = new Picture(3, 4);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 1, 1, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_4x3_choosesShortestPath(){
        picture = new Picture(4, 3);
        // (1,1) will have high energy gradient
        // (2,1) will have low energy gradient
        picture.set(0, 1, new Color(255, 255, 255));
        picture.set(1, 2, new Color(255, 255, 255));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{1, 2, 1};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    ////////////////////////////////////////////////////////////////////////////
    // findHorizontalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void findHorizontalSeam_1x1_zero(){
        picture = new Picture(1, 1);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_1x2_zero(){
        picture = new Picture(1, 2);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_2x1_zeroes(){
        picture = new Picture(2, 1);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_2x2_any(){
        picture = new Picture(2, 2);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_2x3_any(){
        picture = new Picture(2, 3);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_3x2_any(){
        picture = new Picture(3, 2);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 0, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_3x3_passesThroughMiddle(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 1, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_3x4_choosesShortestPath(){
        picture = new Picture(3, 4);

        // (1,1) will have high energy gradient
        // (1,2) will have low energy gradient
        picture.set(1, 0, new Color(255, 255, 255));
        picture.set(2, 1, new Color(255, 255, 255));

        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{1, 2, 1};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_4x3_passesThroughMiddle(){
        picture = new Picture(4, 3);
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 1, 1, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    ////////////////////////////////////////////////////////////////////////////
    // mixFindSeams()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void findVerticalSeam_findVerticalSeam(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 1, 1, 1, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_findHorizontalSeam(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] vSeam = new int[]{0, 1, 1, 1, 0};
        int[] hSeam = new int[]{0, 1, 1, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(vSeam);
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(hSeam);
    }

    @Test
    public void findHorizontalSeam_findVerticalSeam(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] vSeam = new int[]{0, 1, 1, 1, 0};
        int[] hSeam = new int[]{0, 1, 1, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(hSeam);
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(vSeam);
    }



    ////////////////////////////////////////////////////////////////////////////
    // removeVerticalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_1x2_throws(){
        picture = new Picture(1, 2);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0};
        seamCarver.removeVerticalSeam(seam);
    }

    @Test
    public void removeVerticalSeam_2x1_doesntThrow(){
        picture = new Picture(2, 1);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0};
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.picture()).isEqualTo(new Picture(1, 1));
    }

    @Test
    public void removeVerticalSeam_2x2_remove_0_1(){
        picture = new Picture(2, 2);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        // Row 2
        picture.set(0, 1, new Color(2));
        picture.set(1, 1, new Color(3));

        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 1};
        seamCarver.removeVerticalSeam(seam);

        assertThat(seamCarver.width()).isEqualTo(1);
        assertThat(seamCarver.height()).isEqualTo(2);
        assertThat(seamCarver.picture().get(0, 0)).isEqualTo(new Color(1));
        assertThat(seamCarver.picture().get(0, 1)).isEqualTo(new Color(2));
    }

    @Test
    public void removeVerticalSeam_2x2_remove_1_0(){
        picture = new Picture(2, 2);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{1, 0};
        seamCarver.removeVerticalSeam(seam);

        Picture expected = new Picture(1, 2);
        assertThat(seamCarver.picture()).isEqualTo(expected);
    }

    @Test
    public void removeVerticalSeam_2x2_remove_0_0(){
        picture = new Picture(2, 2);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        // Row 2
        picture.set(0, 1, new Color(2));
        picture.set(1, 1, new Color(3));

        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 0};
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.picture().get(0, 0)).isEqualTo(new Color(1));
        assertThat(seamCarver.picture().get(0, 1)).isEqualTo(new Color(3));
    }

    @Test
    public void removeVerticalSeam_2x2_remove_1_1(){
        picture = new Picture(2, 2);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        // Row 2
        picture.set(0, 1, new Color(2));
        picture.set(1, 1, new Color(3));

        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{1, 1};
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.picture().get(0, 0)).isEqualTo(new Color(0));
        assertThat(seamCarver.picture().get(0, 1)).isEqualTo(new Color(2));
    }

    @Test
    public void removeVerticalSeam_3x3(){
        picture = new Picture(3, 3);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        picture.set(2, 0, new Color(3));
        // Row 2
        picture.set(0, 1, new Color(4));
        picture.set(1, 1, new Color(5));
        picture.set(2, 1, new Color(6));
        // Row 3
        picture.set(0, 2, new Color(7));
        picture.set(1, 2, new Color(8));
        picture.set(2, 2, new Color(9));

        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 1, 2};
        seamCarver.removeVerticalSeam(seam);

        assertThat(seamCarver.width()).isEqualTo(2);
    }

    @Test
    public void removeVerticalSeam_4x5_leftMost(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 0, 0, 0, 0};
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.width()).isEqualTo(3);
    }

    @Test
    public void removeVerticalSeam_4x5_rightMost(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{3, 3, 3, 3, 3};
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.width()).isEqualTo(3);
    }

    @Test
    public void removeVerticalSeam_4x5_mixed(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{1, 2, 3, 2, 2};
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.width()).isEqualTo(3);
    }

    ////////////////////////////////////////////////////////////////////////////
    // removeHorizontalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void removeHorizontalSeam_1x2(){
        picture = new Picture(1, 2);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0});
        assertThat(seamCarver.height()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_2x1_throws(){
        picture = new Picture(2, 1);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0, 0});
    }

    @Test
    public void removeHorizontalSeam_2x2_remove_0_0(){
        picture = new Picture(2, 2);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        // Row 2
        picture.set(0, 1, new Color(2));
        picture.set(1, 1, new Color(3));

        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 0};
        seamCarver.removeHorizontalSeam(seam);
        assertThat(seamCarver.picture().get(0, 0)).isEqualTo(new Color(2));
        assertThat(seamCarver.picture().get(1, 0)).isEqualTo(new Color(3));
    }

    @Test
    public void removeHorizontalSeam_2x2_remove_0_1(){
        picture = new Picture(2, 2);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        // Row 2
        picture.set(0, 1, new Color(2));
        picture.set(1, 1, new Color(3));

        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 1};
        seamCarver.removeHorizontalSeam(seam);

        assertThat(seamCarver.picture().get(0, 0)).isEqualTo(new Color(2));
        assertThat(seamCarver.picture().get(1, 0)).isEqualTo(new Color(1));
    }

    @Test
    public void removeHorizontalSeam_2x2_remove_1_0(){
        picture = new Picture(2, 2);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{1, 0};
        seamCarver.removeHorizontalSeam(seam);

        Picture expected = new Picture(2, 1);
        assertThat(seamCarver.picture()).isEqualTo(expected);
    }

    @Test
    public void removeHorizontalSeam_2x2_remove_1_1(){
        picture = new Picture(2, 2);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        // Row 2
        picture.set(0, 1, new Color(2));
        picture.set(1, 1, new Color(3));

        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{1, 1});
        assertThat(seamCarver.picture().get(0, 0)).isEqualTo(new Color(0));
        assertThat(seamCarver.picture().get(1, 0)).isEqualTo(new Color(1));
    }

    @Test
    public void removeHorizontalSeam_3x3(){
        picture = new Picture(3, 3);
        // Row 1
        picture.set(0, 0, new Color(0));
        picture.set(1, 0, new Color(1));
        picture.set(2, 0, new Color(3));
        // Row 2
        picture.set(0, 1, new Color(4));
        picture.set(1, 1, new Color(5));
        picture.set(2, 1, new Color(6));
        // Row 3
        picture.set(0, 2, new Color(7));
        picture.set(1, 2, new Color(8));
        picture.set(2, 2, new Color(9));

        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0, 1, 2});
        assertThat(seamCarver.height()).isEqualTo(2);
    }

    @Test
    public void removeHorizontalSeam_4x5_top(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 0, 0, 0};
        seamCarver.removeHorizontalSeam(seam);
        assertThat(seamCarver.height()).isEqualTo(4);
    }

    @Test
    public void removeHorizontalSeam_4x5_bottom(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{4, 4, 4, 4};
        seamCarver.removeHorizontalSeam(seam);
        assertThat(seamCarver.height()).isEqualTo(4);
    }

    @Test
    public void removeHorizontalSeam_4x5_mixed(){
        picture = new Picture(4, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{2, 3, 4, 4};
        seamCarver.removeHorizontalSeam(seam);
        assertThat(seamCarver.height()).isEqualTo(4);
    }

    ////////////////////////////////////////////////////////////////////////////
    // mixRemoveSeams()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void removeVerticalSeam_removeVerticalSeam_removeVerticalSeam(){
        picture = new Picture(4, 1);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0};
        seamCarver.removeVerticalSeam(seam);
        seamCarver.removeVerticalSeam(seam);
        seamCarver.removeVerticalSeam(seam);
        assertThat(seamCarver.width()).isEqualTo(1);
        assertThat(seamCarver.height()).isEqualTo(1);
    }
    @Test
    public void removeHorizontalSeam_removeHorizontalSeam_removeHorizontalSeam(){
        picture = new Picture(1, 5);
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0};
        seamCarver.removeHorizontalSeam(seam);
        seamCarver.removeHorizontalSeam(seam);
        seamCarver.removeHorizontalSeam(seam);
        assertThat(seamCarver.width()).isEqualTo(1);
        assertThat(seamCarver.height()).isEqualTo(2);
    }

    @Test
    public void removeVSeam_removeHSeam_removeVSeam_removeHSeam(){
        picture = new Picture(3, 3);
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(new int[]{0, 1, 2});
        seamCarver.removeHorizontalSeam(new int[]{0, 1});
        seamCarver.removeVerticalSeam(new int[]{0, 0});
        seamCarver.removeHorizontalSeam(new int[]{1});
    }
}
