import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * WordNet unit testing with files
 * Reads files from out/production/classes/wordnet */
public class WordNetFileTest {
    private ClassLoader loader = getClass().getClassLoader();

    @Test(expected = IllegalArgumentException.class)
    public void wordNet_ctor_hasCycle_throws(){
        String synsets = fileName("synsets3.txt");
        String hypernyms = fileName("hypernyms3InvalidCycle.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wordNet_ctor_hasTwoRoots_throws(){
        String synsets = fileName("synsets3.txt");
        String hypernyms = fileName("hypernyms3InvalidTwoRoots.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);
    }

    @Test
    public void wordNet_fullFile(){
        String synsets = fileName("synsets.txt");
        String hypernyms = fileName("hypernyms.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);

        assertThat(wordNet.sap("worm", "bird")).isEqualTo("animal animate_being beast brute creature fauna");
        assertThat(wordNet.sap("bird", "worm")).isEqualTo("animal animate_being beast brute creature fauna");
        assertThat(wordNet.distance("worm", "bird")).isEqualTo(5);
        assertThat(wordNet.distance("bird", "worm")).isEqualTo(5);

        /*
        (distance = 23) white_marlin, mileage
        (distance = 33) Black_Plague, black_marlin
        (distance = 27) American_water_spaniel, histology
        (distance = 29) Brown_Swiss, barrel_roll
         */
        assertThat(wordNet.distance("white_marlin", "mileage")).isEqualTo(23);
        assertThat(wordNet.distance("Black_Plague", "black_marlin")).isEqualTo(33);
        assertThat(wordNet.distance("American_water_spaniel", "histology")).isEqualTo(27);
        assertThat(wordNet.distance("Brown_Swiss", "barrel_roll")).isEqualTo(29);

        assertThat(wordNet.sap("edible_fruit", "individual")).isEqualTo("physical_entity");
        assertThat(wordNet.distance("edible_fruit", "individual")).isEqualTo(7);

    }

    @Test
    public void outcast_fullFile(){
        String synsets = fileName("synsets.txt");
        String hypernyms = fileName("hypernyms.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);
        Outcast outcast = new Outcast(wordNet);

        String[] nouns;

        nouns = new String[]{"horse", "zebra", "cat", "bear", "table"};
        assertThat(outcast.outcast(nouns)).isEqualTo("table");

        nouns = new String[]{"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        assertThat(outcast.outcast(nouns)).isEqualTo("bed");


        nouns = new String[]{
                "apple",
                "pear",
                "peach",
                "banana",
                "lime",
                "lemon",
                "blueberry",
                "strawberry",
                "mango",
                "watermelon",
                "potato"};
        assertThat(outcast.outcast(nouns)).isEqualTo("potato");
    }

    @Test
    public void outcast_outcastFiles(){
        String synsets = fileName("synsets.txt");
        String hypernyms = fileName("hypernyms.txt");
        WordNet wordNet = new WordNet(synsets, hypernyms);
        Outcast outcast = new Outcast(wordNet);
        String[] nouns;

        nouns = nounsFromFile(fileName("outcast2.txt"));
        assertThat(outcast.outcast(nouns)).isAnyOf("von_Neumann", "Turing");

        nouns = nounsFromFile(fileName("outcast2.txt"));
        assertThat(outcast.outcast(nouns)).isAnyOf("von_Neumann", "Turing");

        nouns = nounsFromFile(fileName("outcast3.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("Mickey_Mouse");

        nouns = nounsFromFile(fileName("outcast4.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("probability");

        nouns = nounsFromFile(fileName("outcast5.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("table");

        nouns = nounsFromFile(fileName("outcast5a.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("heart");

        nouns = nounsFromFile(fileName("outcast8.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("bed");

        nouns = nounsFromFile(fileName("outcast8a.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("playboy");

        nouns = nounsFromFile(fileName("outcast8b.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("cabbage");

        nouns = nounsFromFile(fileName("outcast8c.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("tree");

        nouns = nounsFromFile(fileName("outcast9.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("tree");

        nouns = nounsFromFile(fileName("outcast9a.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("eyes");

        nouns = nounsFromFile(fileName("outcast10.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("albatross");

        nouns = nounsFromFile(fileName("outcast10a.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("serendipity");

        nouns = nounsFromFile(fileName("outcast11.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("potato");

        nouns = nounsFromFile(fileName("outcast12.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("Minneapolis");

        nouns = nounsFromFile(fileName("outcast12a.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("mongoose");

        nouns = nounsFromFile(fileName("outcast17.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("particularism");

        nouns = nounsFromFile(fileName("outcast20.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("particularism");

        nouns = nounsFromFile(fileName("outcast29.txt"));
        assertThat(outcast.outcast(nouns)).isEqualTo("acorn"); // mosque?
    }

    private String fileName(String fileName){
        return loader.getResource("wordnet/" + fileName).getFile();
    }

    private String[] nounsFromFile(String filePath){
        In in = new In(filePath);
        String[] nouns = in.readAllStrings();
        return nouns;
    }
}
