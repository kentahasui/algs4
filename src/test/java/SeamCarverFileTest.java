import edu.princeton.cs.algs4.Picture;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class SeamCarverFileTest {
    private static final double TOLERANCE = 0.005; // For rounding up

    private Picture picture;
    private SeamCarver seamCarver;
    private ClassLoader loader = getClass().getClassLoader();
    private String fileName(String fileName){
        return loader.getResource("seam/" + fileName).getFile();
    }

    ////////////////////////////////////////////////////////////////////////////
    // energy()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void energy_1x1(){
        picture = new Picture(fileName("1x1.png"));
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
    }

    @Test
    public void energy_1x8(){
        picture = new Picture(fileName("1x8.png"));
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 2)).isEqualTo(1000.0);
    }

    @Test
    public void energy_3x4(){
        picture = new Picture(fileName("3x4.png"));
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 1)).isEqualTo(1000.0);
        assertThat(seamCarver.energy(0, 2)).isEqualTo(1000.0);

        assertThat(seamCarver.energy(1, 1)).isWithin(0.005).of(228.53);
        assertThat(seamCarver.energy(1, 2)).isWithin(0.005).of(228.09);
    }

    @Test
    public void energy_3x7() {
        picture = new Picture(fileName("3x7.png"));
        seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.energy(1, 1)).isWithin(TOLERANCE).of(294.32);
        assertThat(seamCarver.energy(1, 2)).isWithin(TOLERANCE).of(236.17);
        assertThat(seamCarver.energy(1, 3)).isWithin(TOLERANCE).of(325.15);
        assertThat(seamCarver.energy(1, 4)).isWithin(TOLERANCE).of(251.36);
        assertThat(seamCarver.energy(1, 5)).isWithin(TOLERANCE).of(279.64);
    }

    @Test
    public void energy_4x6() {
        picture = new Picture(fileName("4x6.png"));
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(1, 1)).isWithin(TOLERANCE).of(275.66);
        assertThat(seamCarver.energy(1, 2)).isWithin(TOLERANCE).of(173.21);
        assertThat(seamCarver.energy(1, 3)).isWithin(TOLERANCE).of(171.80);
        assertThat(seamCarver.energy(1, 4)).isWithin(TOLERANCE).of(270.93);

        assertThat(seamCarver.energy(2, 1)).isWithin(TOLERANCE).of(173.21);
        assertThat(seamCarver.energy(2, 2)).isWithin(TOLERANCE).of(321.01);
        assertThat(seamCarver.energy(2, 3)).isWithin(TOLERANCE).of(195.63);
        assertThat(seamCarver.energy(2, 4)).isWithin(TOLERANCE).of(188.15);

    }

    @Test
    public void energy_5x6() {
        picture = new Picture(fileName("5x6.png"));
        seamCarver = new SeamCarver(picture);
        assertThat(seamCarver.energy(1, 1)).isWithin(TOLERANCE).of(300.07);
        assertThat(seamCarver.energy(1, 2)).isWithin(TOLERANCE).of(311.94);
        assertThat(seamCarver.energy(1, 3)).isWithin(TOLERANCE).of(295.49);
        assertThat(seamCarver.energy(1, 4)).isWithin(TOLERANCE).of(264.36);

        assertThat(seamCarver.energy(2, 1)).isWithin(TOLERANCE).of(265.33);
        assertThat(seamCarver.energy(2, 2)).isWithin(TOLERANCE).of(94.36);
        assertThat(seamCarver.energy(2, 3)).isWithin(TOLERANCE).of(312.36);
        assertThat(seamCarver.energy(2, 4)).isWithin(TOLERANCE).of(216.49);


        assertThat(seamCarver.energy(3, 1)).isWithin(TOLERANCE).of(289.67);
        assertThat(seamCarver.energy(3, 2)).isWithin(TOLERANCE).of(309.61);
        assertThat(seamCarver.energy(3, 3)).isWithin(TOLERANCE).of(193.36);
        assertThat(seamCarver.energy(3, 4)).isWithin(TOLERANCE).of(299.43);
    }

    @Test
    public void energy_6x5() {
        picture = new Picture(fileName("6x5.png"));
        seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.energy(1, 1)).isWithin(TOLERANCE).of(237.35);
        assertThat(seamCarver.energy(1, 2)).isWithin(TOLERANCE).of(138.69);
        assertThat(seamCarver.energy(1, 3)).isWithin(TOLERANCE).of(153.88);

        assertThat(seamCarver.energy(2, 1)).isWithin(TOLERANCE).of(151.02);
        assertThat(seamCarver.energy(2, 2)).isWithin(TOLERANCE).of(228.10);
        assertThat(seamCarver.energy(2, 3)).isWithin(TOLERANCE).of(174.01);

        assertThat(seamCarver.energy(3, 1)).isWithin(TOLERANCE).of(234.09);
        assertThat(seamCarver.energy(3, 2)).isWithin(TOLERANCE).of(133.07);
        assertThat(seamCarver.energy(3, 3)).isWithin(TOLERANCE).of(284.01);

        assertThat(seamCarver.energy(4, 1)).isWithin(TOLERANCE).of(107.89);
        assertThat(seamCarver.energy(4, 2)).isWithin(TOLERANCE).of(211.51);
        assertThat(seamCarver.energy(4, 3)).isWithin(TOLERANCE).of(194.50);
    }

    @Test
    public void energy_7x3() {
        picture = new Picture(fileName("7x3.png"));
        seamCarver = new SeamCarver(picture);

        assertThat(seamCarver.energy(1, 1)).isWithin(TOLERANCE).of(237.12);
        assertThat(seamCarver.energy(2, 1)).isWithin(TOLERANCE).of(268.77);
        assertThat(seamCarver.energy(3, 1)).isWithin(TOLERANCE).of(218.95);
        assertThat(seamCarver.energy(4, 1)).isWithin(TOLERANCE).of(265.27);
        assertThat(seamCarver.energy(5, 1)).isWithin(TOLERANCE).of(292.37);
    }

    @Test
    public void energy_diagonals() {
        picture = new Picture(fileName("diagonals.png"));
        seamCarver = new SeamCarver(picture);
        for (int row = 1; row < picture.width() -1; row++){
            for (int col = 1; col < picture.height() -1; col++){
                assertThat(seamCarver.energy(row, col)).isWithin(TOLERANCE).of(624.62);
            }
        }
    }

    @Test
    public void energy_stripes() {
        picture = new Picture(fileName("stripes.png"));
        seamCarver = new SeamCarver(picture);
        for (int row = 1; row < picture.width() -1; row++){
            for (int col = 1; col < picture.height() -1; col++){
                assertThat(seamCarver.energy(row, col)).isWithin(TOLERANCE).of(441.67);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // findVerticalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void findVerticalSeam_1x1(){
        picture = new Picture(fileName("1x1.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_1x8(){
        picture = new Picture(fileName("1x8.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_3x4(){
        picture = new Picture(fileName("3x4.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 1, 1, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_3x7() {
        picture = new Picture(fileName("3x7.png"));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 1, 1, 1, 1, 1, 0};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_4x6() {
        picture = new Picture(fileName("4x6.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{1, 2, 1, 1, 2, 1 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);

    }

    @Test
    public void findVerticalSeam_5x6() {
        picture = new Picture(fileName("5x6.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{1, 2, 2, 3, 2, 1};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_6x5() {
        picture = new Picture(fileName("6x5.png"));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{ 3, 4, 3, 2, 1 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_7x3() {
        picture = new Picture(fileName("7x3.png"));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{2, 3, 2};
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_7x10() {
        picture = new Picture(fileName("7x10.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{2, 3, 4, 3, 4, 3, 3, 2, 2, 1 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_10x10() {
        picture = new Picture(fileName("10x10.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{6, 7, 7, 7, 7, 7, 8, 8, 7, 6 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_10x12() {
        picture = new Picture(fileName("10x12.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{5, 6, 7, 8, 7, 7, 6, 7, 6, 5, 6, 5 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_12x10() {
        picture = new Picture(fileName("12x10.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{6, 7, 7, 6, 6, 7, 7, 7, 8, 7 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_diagonals() {
        picture = new Picture(fileName("diagonals.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    @Test
    public void findVerticalSeam_stripes() {
        picture = new Picture(fileName("stripes.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 };
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(expected);
    }

    ////////////////////////////////////////////////////////////////////////////
    // findHorizontalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void findHorizontalSeam_1x1(){
        picture = new Picture(fileName("1x1.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_1x8(){
        picture = new Picture(fileName("1x8.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_3x4(){
        picture = new Picture(fileName("3x4.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{1, 2, 1};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_3x7() {
        picture = new Picture(fileName("3x7.png"));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{1, 2, 1};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_4x6() {
        picture = new Picture(fileName("4x6.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{1, 2, 1, 0 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);

    }

    @Test
    public void findHorizontalSeam_5x6() {
        picture = new Picture(fileName("5x6.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{2, 3, 2, 3, 2};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_6x5() {
        picture = new Picture(fileName("6x5.png"));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{ 1, 2, 1, 2, 1, 0 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_7x3() {
        picture = new Picture(fileName("7x3.png"));
        seamCarver = new SeamCarver(picture);

        int[] expected = new int[]{0, 1, 1, 1, 1, 1, 0};
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_7x10() {
        picture = new Picture(fileName("7x10.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{6, 7, 7, 7, 8, 8, 7 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_10x10() {
        picture = new Picture(fileName("10x10.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 1, 2, 3, 3, 3, 3, 2, 1, 0 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_10x12() {
        picture = new Picture(fileName("10x12.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{8, 9, 10, 10, 10, 9, 10, 10, 9, 8 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_12x10() {
        picture = new Picture(fileName("12x10.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{7, 8, 7, 8, 7, 6, 5, 6, 6, 5, 4, 3 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_diagonals() {
        picture = new Picture(fileName("diagonals.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{0, 1, 1, 1, 1, 1, 1, 1, 0 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    @Test
    public void findHorizontalSeam_stripes() {
        picture = new Picture(fileName("stripes.png"));
        seamCarver = new SeamCarver(picture);
        int[] expected = new int[]{ 0, 1, 1, 1, 1, 1, 1, 1, 0 };
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(expected);
    }

    ////////////////////////////////////////////////////////////////////////////
    // removeVerticalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_1x1(){
        picture = new Picture(fileName("1x1.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(new int[]{0});
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeVerticalSeam_1x8(){
        picture = new Picture(fileName("1x8.png"));
        seamCarver = new SeamCarver(picture);
        int[] seam = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        seamCarver.removeVerticalSeam(seam);
    }

    @Test
    public void removeVerticalSeam_3x4(){
        picture = new Picture(fileName("3x4.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(1);
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(new int[]{0, 0, 0, 0});
    }

    @Test
    public void removeVerticalSeam_3x7() {
        picture = new Picture(fileName("3x7.png"));
        seamCarver = new SeamCarver(picture);

        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(1);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.00);
        assertThat(seamCarver.energy(0, 6)).isEqualTo(1000.00);
    }

    @Test
    public void removeVerticalSeam_4x6() {
        picture = new Picture(fileName("4x6.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(
                new int[]{0, 1, 1, 1, 1, 0}
        );
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(
                new int[]{0, 0, 0, 0, 0, 0}
        );
    }

    @Test
    public void removeVerticalSeam_5x6() {
        picture = new Picture(fileName("5x6.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(
                new int[]{1, 2, 2, 2, 1, 0}
        );
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.findVerticalSeam()).isEqualTo(
                new int[]{0, 1, 1, 1, 1, 0}
        );
    }

    @Test
    public void removeVerticalSeam_6x5() {
        picture = new Picture(fileName("6x5.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(new int[]{0, 0, 0, 0, 0});
        assertThat(seamCarver.width()).isEqualTo(5);
    }

    @Test
    public void removeVerticalSeam_7x3() {
        picture = new Picture(fileName("7x3.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(new int[]{0, 0, 0});
        assertThat(seamCarver.width()).isEqualTo(6);
        seamCarver.removeVerticalSeam(new int[]{5, 5, 5});
        assertThat(seamCarver.width()).isEqualTo(5);
    }

    @Test
    public void removeVerticalSeam_7x10() {
        picture = new Picture(fileName("7x10.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(6);
    }

    @Test
    public void removeVerticalSeam_10x10() {
        picture = new Picture(fileName("10x10.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(1);
    }

    @Test
    public void removeVerticalSeam_10x12() {
        picture = new Picture(fileName("10x12.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(1);
    }

    @Test
    public void removeVerticalSeam_12x10() {
        picture = new Picture(fileName("12x10.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(1);
    }

    @Test
    public void removeVerticalSeam_diagonals() {
        picture = new Picture(fileName("diagonals.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(8);
        assertThat(seamCarver.findHorizontalSeam()).hasLength(8);
    }

    @Test
    public void removeVerticalSeam_stripes() {
        picture = new Picture(fileName("stripes.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeVerticalSeam(seamCarver.findVerticalSeam());
        assertThat(seamCarver.width()).isEqualTo(8);
        assertThat(seamCarver.findHorizontalSeam()).hasLength(8);
    }

    ////////////////////////////////////////////////////////////////////////////
    // removeVerticalSeam()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void removeHorizontalSeam_1x1(){
        picture = new Picture(fileName("1x1.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0});
    }

    @Test
    public void removeHorizontalSeam_1x8(){
        picture = new Picture(fileName("1x8.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0});
        assertThat(seamCarver.height()).isEqualTo(7);
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(new int[]{0});
    }

    @Test
    public void removeHorizontalSeam_3x4(){
        picture = new Picture(fileName("3x4.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(1);
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(new int[]{0, 0, 0});
    }

    @Test
    public void removeHorizontalSeam_3x7() {
        picture = new Picture(fileName("3x7.png"));
        seamCarver = new SeamCarver(picture);

        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(1);
        assertThat(seamCarver.energy(0, 0)).isEqualTo(1000.00);
        assertThat(seamCarver.energy(2, 0)).isEqualTo(1000.00);
    }

    @Test
    public void removeHorizontalSeam_4x6() {
        picture = new Picture(fileName("4x6.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(
                new int[]{0, 1, 1, 0}
        );
    }

    @Test
    public void removeHorizontalSeam_5x6() {
        picture = new Picture(fileName("5x6.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.findHorizontalSeam()).isEqualTo(
                new int[]{0, 1, 1, 1, 0}
        );
    }

    @Test
    public void removeHorizontalSeam_6x5() {
        picture = new Picture(fileName("6x5.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0, 0, 0, 0, 0, 0});
        assertThat(seamCarver.height()).isEqualTo(4);
    }

    @Test
    public void removeHorizontalSeam_7x3() {
        picture = new Picture(fileName("7x3.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(new int[]{0, 0, 0, 0, 0, 0, 0});
        assertThat(seamCarver.height()).isEqualTo(2);
        seamCarver.removeHorizontalSeam(new int[]{1, 1, 1, 1, 1, 1, 1});
        assertThat(seamCarver.height()).isEqualTo(1);
    }

    @Test
    public void removeHorizontalSeam_7x10() {
        picture = new Picture(fileName("7x10.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(9);
    }

    @Test
    public void removeHorizontalSeam_10x10() {
        picture = new Picture(fileName("10x10.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(1);
    }

    @Test
    public void removeHorizontalSeam_10x12() {
        picture = new Picture(fileName("10x12.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(1);
    }

    @Test
    public void removeHorizontalSeam_12x10() {
        picture = new Picture(fileName("12x10.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(1);
    }

    @Test
    public void removeHorizontalSeam_diagonals() {
        picture = new Picture(fileName("diagonals.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(11);
        assertThat(seamCarver.findVerticalSeam()).hasLength(11);
    }

    @Test
    public void removeHorizontalSeam_stripes() {
        picture = new Picture(fileName("stripes.png"));
        seamCarver = new SeamCarver(picture);
        seamCarver.removeHorizontalSeam(seamCarver.findHorizontalSeam());
        assertThat(seamCarver.height()).isEqualTo(11);
        assertThat(seamCarver.findVerticalSeam()).hasLength(11);
    }
}

