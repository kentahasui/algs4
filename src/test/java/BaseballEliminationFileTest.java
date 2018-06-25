import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.net.URL;

import static com.google.common.truth.Truth.assertThat;

public class BaseballEliminationFileTest {
    private BaseballElimination baseball;
    private ClassLoader loader = getClass().getClassLoader();
    private String fileName(String file){
        URL url = loader.getResource("baseball/" + file);
        if (url == null) throw new IllegalArgumentException(
                loader.getResource("") + "baseball/" + file + " not found"
        );
        return url.getFile();
    }

    ////////////////////////////////////////////////////////////////////////////
    // ctor
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void ctor_nullFile_throws(){
        baseball = new BaseballElimination(null);
    }

    ////////////////////////////////////////////////////////////////////////////
    // numberOfTeams
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void numberOfTeams_teams0(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(0);
    }

    @Test
    public void numberOfTeams_teams1(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(1);
    }

    @Test
    public void numberOfTeams_teams2(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(2);
    }

    @Test
    public void numberOfTeams_teams4(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(4);
    }

    @Test
    public void numberOfTeams_teams4a(){
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(4);
    }


    @Test
    public void numberOfTeams_teams4b(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(4);
    }

    @Test
    public void numberOfTeams_teams5(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(5);
    }

    @Test
    public void numberOfTeams_teams5a(){
        baseball = new BaseballElimination(fileName("teams5a.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(5);
    }

    @Test
    public void numberOfTeams_teams5b(){
        baseball = new BaseballElimination(fileName("teams5b.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(5);
    }

    @Test
    public void numberOfTeams_teams5c(){
        baseball = new BaseballElimination(fileName("teams5c.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(5);
    }

    @Test
    public void numberOfTeams_teams7(){
        baseball = new BaseballElimination(fileName("teams7.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(7);
    }

    @Test
    public void numberOfTeams_teams8(){
        baseball = new BaseballElimination(fileName("teams8.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(8);
    }

    @Test
    public void numberOfTeams_teams10(){
        baseball = new BaseballElimination(fileName("teams10.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(10);
    }

    @Test
    public void numberOfTeams_teams12(){
        baseball = new BaseballElimination(fileName("teams12.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(12);
    }

    @Test
    public void numberOfTeams_teams12_allgames(){
        baseball = new BaseballElimination(fileName("teams12-allgames.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(12);
    }

    @Test
    public void numberOfTeams_teams24(){
        baseball = new BaseballElimination(fileName("teams24.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(24);
    }

    @Test
    public void numberOfTeams_teams29(){
        baseball = new BaseballElimination(fileName("teams29.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(29);
    }

    @Test
    public void numberOfTeams_teams30(){
        baseball = new BaseballElimination(fileName("teams30.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(30);
    }

    @Test
    public void numberOfTeams_teams32(){
        baseball = new BaseballElimination(fileName("teams32.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(32);
    }

    @Test
    public void numberOfTeams_teams36(){
        baseball = new BaseballElimination(fileName("teams36.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(36);
    }

    @Test
    public void numberOfTeams_teams42(){
        baseball = new BaseballElimination(fileName("teams42.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(42);
    }

    @Test
    public void numberOfTeams_teams48(){
        baseball = new BaseballElimination(fileName("teams48.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(48);
    }

    @Test
    public void numberOfTeams_teams50(){
        baseball = new BaseballElimination(fileName("teams50.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(50);
    }

    @Test
    public void numberOfTeams_teams54(){
        baseball = new BaseballElimination(fileName("teams54.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(54);
    }

    @Test
    public void numberOfTeams_teams60(){
        baseball = new BaseballElimination(fileName("teams60.txt"));
        assertThat(baseball.numberOfTeams()).isEqualTo(60);
    }

    ////////////////////////////////////////////////////////////////////////////
    // teams
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void teams_teams0(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        assertThat(baseball.teams()).containsExactly();
    }

    @Test
    public void teams_teams1(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.teams()).containsExactly("Turing");
    }

    @Test
    public void teams_teams2(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.teams()).containsExactly("A", "B");
    }

    @Test
    public void teams_teams4(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.teams()).containsExactly(
                "Atlanta",
                "Philadelphia",
                "New_York",
                "Montreal"
        );
    }

    @Test
    public void teams_teams4a(){
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.teams()).containsExactly(
                "CIA",
                "Ghaddafi",
                "Bin_Ladin",
                "Obama"
        );
    }

    @Test
    public void teams_teams4b(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.teams()).containsExactly(
                "Hufflepuff", "Slytherin", "Ravenclaw", "Gryffindor"
        );
    }

    @Test
    public void teams_teams5(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.teams()).hasSize(5);
    }

    @Test
    public void teams_teams5a(){
        baseball = new BaseballElimination(fileName("teams5a.txt"));
        assertThat(baseball.teams()).hasSize(5);
    }

    @Test
    public void teams_teams5b(){
        baseball = new BaseballElimination(fileName("teams5b.txt"));
        assertThat(baseball.teams()).hasSize(5);
    }

    @Test
    public void teams_teams5c(){
        baseball = new BaseballElimination(fileName("teams5c.txt"));
        assertThat(baseball.teams()).hasSize(5);
    }

    @Test
    public void teams_teams7(){
        baseball = new BaseballElimination(fileName("teams7.txt"));
        assertThat(baseball.teams()).hasSize(7);
    }

    @Test
    public void teams_teams8(){
        baseball = new BaseballElimination(fileName("teams8.txt"));
        assertThat(baseball.teams()).hasSize(8);
    }

    @Test
    public void teams_teams10(){
        baseball = new BaseballElimination(fileName("teams10.txt"));
        assertThat(baseball.teams()).hasSize(10);
    }

    @Test
    public void teams_teams12(){
        baseball = new BaseballElimination(fileName("teams12.txt"));
        assertThat(baseball.teams()).hasSize(12);
    }

    @Test
    public void teams_teams12_allgames(){
        baseball = new BaseballElimination(fileName("teams12-allgames.txt"));
        assertThat(baseball.teams()).hasSize(12);
    }

    @Test
    public void teams_teams24(){
        baseball = new BaseballElimination(fileName("teams24.txt"));
        assertThat(baseball.teams()).hasSize(24);
    }

    @Test
    public void teams_teams29(){
        baseball = new BaseballElimination(fileName("teams29.txt"));
        assertThat(baseball.teams()).hasSize(29);
    }

    @Test
    public void teams_teams30(){
        baseball = new BaseballElimination(fileName("teams30.txt"));
        assertThat(baseball.teams()).hasSize(30);
    }

    @Test
    public void teams_teams32(){
        baseball = new BaseballElimination(fileName("teams32.txt"));
        assertThat(baseball.teams()).hasSize(32);
    }

    @Test
    public void teams_teams36(){
        baseball = new BaseballElimination(fileName("teams36.txt"));
        assertThat(baseball.teams()).hasSize(36);
    }

    @Test
    public void teams_teams42(){
        baseball = new BaseballElimination(fileName("teams42.txt"));
        assertThat(baseball.teams()).hasSize(42);
    }

    @Test
    public void teams_teams48(){
        baseball = new BaseballElimination(fileName("teams48.txt"));
        assertThat(baseball.teams()).hasSize(48);
    }

    @Test
    public void teams_teams50(){
        baseball = new BaseballElimination(fileName("teams50.txt"));
        assertThat(baseball.teams()).hasSize(50);
    }

    @Test
    public void teams_teams54(){
        baseball = new BaseballElimination(fileName("teams54.txt"));
        assertThat(baseball.teams()).hasSize(54);
    }

    @Test
    public void teams_teams60(){
        baseball = new BaseballElimination(fileName("teams60.txt"));
        assertThat(baseball.teams()).hasSize(60);
    }

    ////////////////////////////////////////////////////////////////////////////
    // wins
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void wins_teams0_throws(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        baseball.wins("Anything");
    }

    @Test(expected = IllegalArgumentException.class)
    public void wins_teams1_null_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.wins(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wins_teams1_invalidTeam_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.wins("someTeam");
    }

    @Test
    public void wins_teams1_Turing(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.wins("Turing")).isEqualTo(100);
    }

    @Test
    public void wins_teams2_A(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.wins("A")).isEqualTo(10);
    }

    @Test
    public void wins_teams2_B(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.wins("B")).isEqualTo(5);
    }

    @Test
    public void wins_teams4_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.wins("Atlanta")).isEqualTo(83);
    }

    @Test
    public void wins_teams4_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.wins("Philadelphia")).isEqualTo(80);
    }

    @Test
    public void wins_teams4_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.wins("New_York")).isEqualTo(78);
    }

    @Test
    public void wins_teams4_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.wins("Montreal")).isEqualTo(77);
    }

    ////////////////////////////////////////////////////////////////////////////
    // losses
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void losses_teams0_throws(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        baseball.losses("Anything");
    }

    @Test(expected = IllegalArgumentException.class)
    public void losses_teams1_null_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.losses(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void losses_teams1_invalidTeam_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.losses("someTeam");
    }

    @Test
    public void losses_teams1_Turing(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.losses("Turing")).isEqualTo(55);
    }

    @Test
    public void losses_teams2_A(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.losses("A")).isEqualTo(1);
    }

    @Test
    public void losses_teams2_B(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.losses("B")).isEqualTo(7);
    }

    @Test
    public void losses_teams4_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.losses("Atlanta")).isEqualTo(71);
    }

    @Test
    public void losses_teams4_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.losses("Philadelphia")).isEqualTo(79);
    }

    @Test
    public void losses_teams4_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.losses("New_York")).isEqualTo(78);
    }

    @Test
    public void losses_teams4_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.losses("Montreal")).isEqualTo(82);
    }

    ////////////////////////////////////////////////////////////////////////////
    // remaining
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void remaining_teams0_throws(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        baseball.remaining("Anything");
    }

    @Test(expected = IllegalArgumentException.class)
    public void remaining_teams1_null_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.remaining(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void remaining_teams1_invalidTeam_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.remaining("someTeam");
    }

    @Test
    public void remaining_teams1_Turing(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.remaining("Turing")).isEqualTo(0);
    }

    @Test
    public void remaining_teams2_A(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.remaining("A")).isEqualTo(3);
    }

    @Test
    public void remaining_teams2_B(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.remaining("B")).isEqualTo(2);
    }

    @Test
    public void remaining_teams4_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.remaining("Atlanta")).isEqualTo(8);
    }

    @Test
    public void remaining_teams4_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.remaining("Philadelphia")).isEqualTo(3);
    }

    @Test
    public void remaining_teams4_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.remaining("New_York")).isEqualTo(6);
    }

    @Test
    public void remaining_teams4_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.remaining("Montreal")).isEqualTo(3);
    }

    ////////////////////////////////////////////////////////////////////////////
    // against
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void against_teams0_throws(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        baseball.against("any", "any");
    }

    @Test(expected = IllegalArgumentException.class)
    public void against_teams1_bothNull_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.against(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void against_teams1_firstNull_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.against(null, "Turing");
    }

    @Test(expected = IllegalArgumentException.class)
    public void against_teams1_secondNull_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.against("Turing", null);
    }


    @Test(expected = IllegalArgumentException.class)
    public void against_teams1_firstTeamInvalid_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.against("someTeam", "Turing");
    }

    @Test(expected = IllegalArgumentException.class)
    public void against_teams1_secondTeamInvalid_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.against("Turing", "someTeam");
    }

    @Test
    public void against_teams1_Turing(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.against("Turing", "Turing")).isEqualTo(0);
    }

    @Test
    public void against_teams2_A_A(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.against("A", "A")).isEqualTo(0);
    }

    @Test
    public void against_teams2_A_B(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.against("A", "B")).isEqualTo(1);
    }

    @Test
    public void against_teams2_B_A(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.against("B", "A")).isEqualTo(1);
    }

    @Test
    public void against_teams2_B_B(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.against("B", "B")).isEqualTo(0);
    }


    @Test
    public void against_teams4_Atlanta_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Atlanta", "Philadelphia")).isEqualTo(1);
    }

    @Test
    public void against_teams4_Atlanta_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Atlanta", "New_York")).isEqualTo(6);
    }

    @Test
    public void against_teams4_Atlanta_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Atlanta", "Montreal")).isEqualTo(1);
    }

    @Test
    public void against_teams4_Philadelphia_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Philadelphia", "Atlanta")).isEqualTo(1);
    }

    @Test
    public void against_teams4_Philadelphia_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Philadelphia", "New_York")).isEqualTo(0);
    }

    @Test
    public void against_teams4_Philadelphia_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Philadelphia", "Montreal")).isEqualTo(2);
    }

    @Test
    public void against_teams4_New_York_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("New_York", "Atlanta")).isEqualTo(6);
    }

    @Test
    public void against_teams4_New_York_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("New_York", "Philadelphia")).isEqualTo(0);
    }


    @Test
    public void against_teams4_New_York_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("New_York", "Montreal")).isEqualTo(0);
    }

    @Test
    public void against_teams4_Montreal_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Montreal", "Atlanta")).isEqualTo(1);
    }

    @Test
    public void against_teams4_Montreal_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Montreal", "Philadelphia")).isEqualTo(2);
    }

    @Test
    public void against_teams4_Montreal_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.against("Montreal", "New_York")).isEqualTo(0);
    }

    ////////////////////////////////////////////////////////////////////////////
    // isEliminated()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void isEliminated_teams0_throws(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        baseball.isEliminated("any");

    }

    @Test(expected = IllegalArgumentException.class)
    public void isEliminated_teams1_null_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.isEliminated(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isEliminated_teams1_invalidTeam_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.isEliminated("someTeam");
    }

    @Test
    public void isEliminated_teams1_validTeam_alwaysFalse(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.isEliminated("Turing")).isFalse();
    }

    @Test
    public void isEliminated_teams2_A_false(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.isEliminated("A")).isFalse();
    }

    @Test
    public void isEliminated_teams2_B_true(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.isEliminated("B")).isTrue();
    }

    @Test
    public void isEliminated_teams2_not_eliminated_A_false(){
        baseball = new BaseballElimination(fileName("teams2-not-eliminated.txt"));
        assertThat(baseball.isEliminated("A")).isFalse();
    }

    @Test
    public void isEliminated_teams2_not_eliminated_B_false(){
        baseball = new BaseballElimination(fileName("teams2-not-eliminated.txt"));
        assertThat(baseball.isEliminated("B")).isFalse();
    }

    @Test
    public void isEliminated_teams3_2_noneliminated_A_false(){
        baseball = new BaseballElimination(fileName("teams3-2-noneliminated.txt"));
        assertThat(baseball.isEliminated("A")).isFalse();
    }

    @Test
    public void isEliminated_teams3_2_noneliminated_B_false(){
        baseball = new BaseballElimination(fileName("teams3-2-noneliminated.txt"));
        assertThat(baseball.isEliminated("B")).isFalse();
    }

    @Test
    public void isEliminated_teams3_2_nontrivial_B_true(){
        baseball = new BaseballElimination(fileName("teams3-2-nontrivial.txt"));
        assertThat(baseball.isEliminated("B")).isTrue();
    }

    @Test
    public void isEliminated_teams3_2_trivial_B_true(){
        baseball = new BaseballElimination(fileName("teams3-2-trivial.txt"));
        assertThat(baseball.isEliminated("B")).isTrue();
    }

    @Test
    public void isEliminated_teams3_3_noneliminated_C_false(){
        baseball = new BaseballElimination(fileName("teams3-3-noneliminated.txt"));
        assertThat(baseball.isEliminated("C")).isFalse();
    }

    @Test
    public void isEliminated_teams3_3_nontrivial_C_true(){
        baseball = new BaseballElimination(fileName("teams3-3-nontrivial.txt"));
        assertThat(baseball.isEliminated("C")).isTrue();
    }

    @Test
    public void isEliminated_teams3_3_trivial_C_true(){
        baseball = new BaseballElimination(fileName("teams3-3-trivial.txt"));
        assertThat(baseball.isEliminated("C")).isTrue();
    }

    @Test
    public void isEliminated_teams4_Atlanta_false(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.isEliminated("Atlanta")).isFalse();
    }

    @Test
    public void isEliminated_teams4_Philadelphia_true(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.isEliminated("Philadelphia")).isTrue();
    }

    @Test
    public void isEliminated_teams4_New_York_false(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.isEliminated("New_York")).isFalse();
    }

    @Test
    public void isEliminated_teams4_Montreal_true(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.isEliminated("Montreal")).isTrue();
    }

    @Test
    public void isEliminated_teams4a_CIA_false(){
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.isEliminated("CIA")).isFalse();
    }

    @Test
    public void isEliminated_teams4a_Ghaddafi_true(){
        // nontrivial elimination
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.isEliminated("Ghaddafi")).isTrue();
    }

    @Test
    public void isEliminated_teams4a_Bin_Laden_true(){
        // trivial elimination
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.isEliminated("Bin_Ladin")).isTrue();
    }

    @Test
    public void isEliminated_teams4a_Obama_false(){
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.isEliminated("Obama")).isFalse();
    }

    @Test
    public void isEliminated_teams4b_Gryffindor_false(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.isEliminated("Gryffindor")).isFalse();
    }

    @Test
    public void isEliminated_teams4b_Hufflepuff_true(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.isEliminated("Hufflepuff")).isTrue();
    }

    @Test
    public void isEliminated_teams4b_Ravenclaw_true(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.isEliminated("Ravenclaw")).isTrue();
    }


    @Test
    public void isEliminated_teams4b_Slytherin_true(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.isEliminated("Slytherin")).isTrue();
    }

    @Test
    public void isEliminated_teams5_New_York_false(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.isEliminated("New_York")).isFalse();
    }

    @Test
    public void isEliminated_teams5_Baltimore_false(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.isEliminated("Baltimore")).isFalse();
    }

    @Test
    public void isEliminated_teams5_Boston_false(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.isEliminated("Boston")).isFalse();
    }

    @Test
    public void isEliminated_teams5_Toronto_false(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.isEliminated("Toronto")).isFalse();
    }

    @Test
    public void isEliminated_teams5_Detroit_true(){
        // Nontrivial elimination
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.isEliminated("Detroit")).isTrue();
    }


    @Test
    public void isEliminated_teams5a(){
        baseball = new BaseballElimination(fileName("teams5a.txt"));
        assertThat(baseball.isEliminated("New_York")).isFalse();
        assertThat(baseball.isEliminated("Baltimore")).isFalse();
        assertThat(baseball.isEliminated("Boston")).isFalse();
        assertThat(baseball.isEliminated("Toronto")).isFalse();
        assertThat(baseball.isEliminated("Detroit")).isTrue();
    }

    @Test
    public void isEliminated_teams5b(){
        baseball = new BaseballElimination(fileName("teams5b.txt"));
        assertThat(baseball.isEliminated("New_York")).isFalse();
        assertThat(baseball.isEliminated("Baltimore")).isFalse();
        assertThat(baseball.isEliminated("Boston")).isFalse();
        assertThat(baseball.isEliminated("Toronto")).isFalse();
        assertThat(baseball.isEliminated("Detroit")).isTrue();
    }


    @Test
    public void isEliminated_teams5c(){
        baseball = new BaseballElimination(fileName("teams5c.txt"));
        assertThat(baseball.isEliminated("New_York")).isFalse();
        assertThat(baseball.isEliminated("Philadelphia")).isTrue();
        assertThat(baseball.isEliminated("Atlanta")).isFalse();
        assertThat(baseball.isEliminated("Florida")).isFalse();
        assertThat(baseball.isEliminated("Washington")).isTrue();
    }

    @Test
    public void isEliminated_teams7(){
        baseball = new BaseballElimination(fileName("teams7.txt"));
        assertThat(baseball.isEliminated("U.S.A.")).isFalse();
        assertThat(baseball.isEliminated("England")).isFalse();
        assertThat(baseball.isEliminated("France")).isFalse();
        assertThat(baseball.isEliminated("Germany")).isFalse();
        assertThat(baseball.isEliminated("Ireland")).isTrue();
        assertThat(baseball.isEliminated("Belgium")).isFalse();
        assertThat(baseball.isEliminated("China")).isTrue();
    }

    @Test
    public void isEliminated_teams8(){
        baseball = new BaseballElimination(fileName("teams8.txt"));
        assertThat(baseball.isEliminated("Brown")).isFalse();
        assertThat(baseball.isEliminated("Columbia")).isFalse();
        assertThat(baseball.isEliminated("Cornell")).isFalse();
        assertThat(baseball.isEliminated("Dartmouth")).isFalse();
        assertThat(baseball.isEliminated("Penn")).isFalse();
        assertThat(baseball.isEliminated("Harvard")).isTrue();
        assertThat(baseball.isEliminated("Yale")).isTrue();
        assertThat(baseball.isEliminated("Princeton")).isFalse();
    }

    @Test
    public void isEliminated_teams12(){
        baseball = new BaseballElimination(fileName("teams12.txt"));
        assertThat(baseball.isEliminated("Poland")).isFalse();
        assertThat(baseball.isEliminated("Russia")).isFalse();
        assertThat(baseball.isEliminated("Brazil")).isFalse();
        assertThat(baseball.isEliminated("Iran")).isFalse();
        assertThat(baseball.isEliminated("Italy")).isFalse();
        assertThat(baseball.isEliminated("Cuba")).isFalse();
        assertThat(baseball.isEliminated("Argentina")).isFalse();
        assertThat(baseball.isEliminated("USA")).isFalse();
        assertThat(baseball.isEliminated("Japan")).isTrue();
        assertThat(baseball.isEliminated("Serbia")).isTrue();
        assertThat(baseball.isEliminated("Egypt")).isTrue();
        assertThat(baseball.isEliminated("China")).isTrue();
    }

    @Test
    public void isEliminated_teams12_allgames(){
        baseball = new BaseballElimination(fileName("teams12-allgames.txt"));
        assertThat(baseball.isEliminated("Team0")).isTrue();
        assertThat(baseball.isEliminated("Team1")).isTrue();
        assertThat(baseball.isEliminated("Team2")).isTrue();
        assertThat(baseball.isEliminated("Team3")).isTrue();
        assertThat(baseball.isEliminated("Team4")).isFalse();
        assertThat(baseball.isEliminated("Team5")).isTrue();
        assertThat(baseball.isEliminated("Team6")).isTrue();
        assertThat(baseball.isEliminated("Team7")).isTrue();
        assertThat(baseball.isEliminated("Team8")).isTrue();
        assertThat(baseball.isEliminated("Team9")).isTrue();
        assertThat(baseball.isEliminated("Team10")).isTrue();
        assertThat(baseball.isEliminated("Team11")).isFalse();
    }

    @Test
    public void isEliminated_teams24() {
        baseball = new BaseballElimination(fileName("teams24.txt"));
        assertThat(baseball.isEliminated("Team13")).isTrue();
    }

    @Test
    public void isEliminated_teams32() {
        baseball = new BaseballElimination(fileName("teams32.txt"));
        assertThat(baseball.isEliminated("Team25")).isTrue();
        assertThat(baseball.isEliminated("Team29")).isTrue();
    }

    @Test
    public void isEliminated_teams36() {
        baseball = new BaseballElimination(fileName("teams36.txt"));
        assertThat(baseball.isEliminated("Team21")).isTrue();
    }

    @Test
    public void isEliminated_teams42() {
        baseball = new BaseballElimination(fileName("teams42.txt"));
        assertThat(baseball.isEliminated("Team6")).isTrue();
        assertThat(baseball.isEliminated("Team15")).isTrue();
        assertThat(baseball.isEliminated("Team25")).isTrue();
    }

    @Test
    public void isEliminated_teams48() {
        baseball = new BaseballElimination(fileName("teams48.txt"));
        assertThat(baseball.isEliminated("Team6")).isTrue();
        assertThat(baseball.isEliminated("Team23")).isTrue();
        assertThat(baseball.isEliminated("Team47")).isTrue();
    }

    @Test
    public void isEliminated_teams54() {
        baseball = new BaseballElimination(fileName("teams54.txt"));
        assertThat(baseball.isEliminated("Team3")).isTrue();
        assertThat(baseball.isEliminated("Team29")).isTrue();
        assertThat(baseball.isEliminated("Team37")).isTrue();
        assertThat(baseball.isEliminated("Team50")).isTrue();
    }

    ////////////////////////////////////////////////////////////////////////////
    // certificateOfElimination()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = IllegalArgumentException.class)
    public void certificateOfElimination_teams0_throws(){
        baseball = new BaseballElimination(fileName("teams0.txt"));
        baseball.certificateOfElimination("any");

    }

    @Test(expected = IllegalArgumentException.class)
    public void certificateOfElimination_teams1_null_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.certificateOfElimination(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void certificateOfElimination_teams1_invalidTeam_exception(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        baseball.certificateOfElimination("someTeam");
    }

    @Test
    public void certificateOfElimination_teams1_validTeam_alwaysFalse(){
        baseball = new BaseballElimination(fileName("teams1.txt"));
        assertThat(baseball.certificateOfElimination("Turing")).isNull();
    }

    @Test
    public void certificateOfElimination_teams2(){
        baseball = new BaseballElimination(fileName("teams2.txt"));
        assertThat(baseball.certificateOfElimination("A")).isNull();
        assertThat(baseball.certificateOfElimination("B")).containsExactly("A");
    }

    @Test
    public void certificateOfElimination_teams2_not_eliminated(){
        baseball = new BaseballElimination(fileName("teams2-not-eliminated.txt"));
        assertThat(baseball.certificateOfElimination("A")).isNull();
        assertThat(baseball.certificateOfElimination("B")).isNull();
    }

    @Test
    public void certificateOfElimination_teams3_2_noneliminated(){
        baseball = new BaseballElimination(fileName("teams3-2-noneliminated.txt"));
        assertThat(baseball.certificateOfElimination("A")).isNull();
        assertThat(baseball.certificateOfElimination("B")).isNull();
    }

    @Test
    public void certificateOfElimination_teams3_2_nontrivial(){
        baseball = new BaseballElimination(fileName("teams3-2-nontrivial.txt"));
        assertThat(baseball.certificateOfElimination("B")).containsExactly("A", "C");
    }

    @Test
    public void certificateOfElimination_teams3_2_trivial(){
        baseball = new BaseballElimination(fileName("teams3-2-trivial.txt"));
        assertThat(baseball.certificateOfElimination("B")).containsExactly("A");
    }

    @Test
    public void certificateOfElimination_teams3_3_noneliminated(){
        baseball = new BaseballElimination(fileName("teams3-3-noneliminated.txt"));
        assertThat(baseball.certificateOfElimination("C")).isNull();
    }

    @Test
    public void certificateOfElimination_teams3_3_nontrivial(){
        baseball = new BaseballElimination(fileName("teams3-3-nontrivial.txt"));
        assertThat(baseball.certificateOfElimination("C")).containsExactly("A", "B");
    }

    @Test
    public void certificateOfElimination_teams3_3_trivial(){
        baseball = new BaseballElimination(fileName("teams3-3-trivial.txt"));
        assertThat(baseball.certificateOfElimination("C")).containsExactly("A");
    }

    @Test
    public void certificateOfElimination_teams4_Atlanta(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.certificateOfElimination("Atlanta")).isNull();
    }

    @Test
    public void certificateOfElimination_teams4_Philadelphia(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.certificateOfElimination("Philadelphia")).containsExactly("Atlanta", "New_York");
    }

    @Test
    public void certificateOfElimination_teams4_New_York(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.certificateOfElimination("New_York")).isNull();
    }

    @Test
    public void certificateOfElimination_teams4_Montreal(){
        baseball = new BaseballElimination(fileName("teams4.txt"));
        assertThat(baseball.certificateOfElimination("Montreal")).containsExactly("Atlanta");
    }

    @Test
    public void certificateOfElimination_teams4a_CIA(){
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.certificateOfElimination("CIA")).isNull();
    }

    @Test
    public void certificateOfElimination_teams4a_Ghaddafi(){
        // nontrivial elimination
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.certificateOfElimination("Ghaddafi")).containsExactly("Obama", "CIA");
    }

    @Test
    public void certificateOfElimination_teams4a_Bin_Laden(){
        // trivial elimination
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.certificateOfElimination("Bin_Ladin")).containsExactly("Obama");
    }

    @Test
    public void certificateOfElimination_teams4a_Obama(){
        baseball = new BaseballElimination(fileName("teams4a.txt"));
        assertThat(baseball.certificateOfElimination("Obama")).isNull();
    }

    @Test
    public void certificateOfElimination_teams4b_Gryffindor(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.certificateOfElimination("Gryffindor")).isNull();
    }

    @Test
    public void certificateOfElimination_teams4b_Hufflepuff(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.certificateOfElimination("Hufflepuff")).containsExactly("Gryffindor");
    }

    @Test
    public void certificateOfElimination_teams4b_Ravenclaw(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.certificateOfElimination("Ravenclaw")).containsExactly("Gryffindor");
    }


    @Test
    public void certificateOfElimination_teams4b_Slytherin(){
        baseball = new BaseballElimination(fileName("teams4b.txt"));
        assertThat(baseball.certificateOfElimination("Slytherin")).containsExactly("Gryffindor");
    }

    @Test
    public void certificateOfElimination_teams5_New_York(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.certificateOfElimination("New_York")).isNull();
    }

    @Test
    public void certificateOfElimination_teams5_Baltimore(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.certificateOfElimination("Baltimore")).isNull();
    }

    @Test
    public void certificateOfElimination_teams5_Boston(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.certificateOfElimination("Boston")).isNull();
    }

    @Test
    public void certificateOfElimination_teams5_Toronto(){
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.certificateOfElimination("Toronto")).isNull();
    }

    @Test
    public void certificateOfElimination_teams5_Detroit(){
        // Nontrivial elimination
        baseball = new BaseballElimination(fileName("teams5.txt"));
        assertThat(baseball.certificateOfElimination("Detroit")).containsExactly(
                "Baltimore", "New_York", "Toronto", "Boston"
        );
    }

    @Test
    public void certificateOfElimination_teams5a(){
        baseball = new BaseballElimination(fileName("teams5a.txt"));
        assertThat(baseball.certificateOfElimination("New_York")).isNull();
        assertThat(baseball.certificateOfElimination("Baltimore")).isNull();
        assertThat(baseball.certificateOfElimination("Boston")).isNull();
        assertThat(baseball.certificateOfElimination("Toronto")).isNull();
        assertThat(baseball.certificateOfElimination("Detroit")).containsExactly(
                "Baltimore", "New_York", "Toronto", "Boston"
        );
    }

    @Test
    public void certificateOfElimination_teams5b(){
        baseball = new BaseballElimination(fileName("teams5b.txt"));
        assertThat(baseball.certificateOfElimination("New_York")).isNull();
        assertThat(baseball.certificateOfElimination("Baltimore")).isNull();
        assertThat(baseball.certificateOfElimination("Boston")).isNull();
        assertThat(baseball.certificateOfElimination("Toronto")).isNull();
        assertThat(baseball.certificateOfElimination("Detroit")).containsAnyIn(ImmutableList.of(
                "Baltimore", "New_York", "Toronto", "Boston"
        ));
        assertThat(baseball.certificateOfElimination("Detroit")).hasSize(1);
    }

    @Test
    public void certificateOfElimination_teams5c(){
        baseball = new BaseballElimination(fileName("teams5c.txt"));
        assertThat(baseball.certificateOfElimination("New_York")).isNull();
        assertThat(baseball.certificateOfElimination("Philadelphia")).containsExactly(
                "Atlanta", "Florida"
        );
        assertThat(baseball.certificateOfElimination("Atlanta")).isNull();
        assertThat(baseball.certificateOfElimination("Florida")).isNull();
        assertThat(baseball.certificateOfElimination("Washington")).containsAnyIn(ImmutableList.of(
                "New_York", "Philadelphia", "Atlanta", "Florida"
        ));
        assertThat(baseball.certificateOfElimination("Washington")).hasSize(1);
    }

    @Test
    public void certificateOfElimination_teams7(){
        baseball = new BaseballElimination(fileName("teams7.txt"));
        assertThat(baseball.certificateOfElimination("U.S.A.")).isNull();
        assertThat(baseball.certificateOfElimination("England")).isNull();
        assertThat(baseball.certificateOfElimination("France")).isNull();
        assertThat(baseball.certificateOfElimination("Germany")).isNull();
        assertThat(baseball.certificateOfElimination("Ireland")).containsExactly(
                "U.S.A.", "France", "Germany"
        );
        assertThat(baseball.certificateOfElimination("Belgium")).isNull();
        assertThat(baseball.certificateOfElimination("China")).containsExactly("France");
    }

    @Test
    public void certificateOfElimination_teams8(){
        baseball = new BaseballElimination(fileName("teams8.txt"));
        assertThat(baseball.certificateOfElimination("Brown")).isNull();
        assertThat(baseball.certificateOfElimination("Columbia")).isNull();
        assertThat(baseball.certificateOfElimination("Cornell")).isNull();
        assertThat(baseball.certificateOfElimination("Dartmouth")).isNull();
        assertThat(baseball.certificateOfElimination("Penn")).isNull();
        assertThat(baseball.certificateOfElimination("Harvard")).containsExactly(
                "Penn", "Brown", "Columbia", "Dartmouth", "Princeton", "Cornell"
        );
        assertThat(baseball.certificateOfElimination("Yale")).containsExactly(
                "Penn", "Brown", "Columbia", "Dartmouth", "Princeton", "Cornell"
        );
        assertThat(baseball.certificateOfElimination("Princeton")).isNull();
    }

    @Test
    public void certificateOfElimination_teams12(){
        baseball = new BaseballElimination(fileName("teams12.txt"));
        assertThat(baseball.certificateOfElimination("Poland")).isNull();
        assertThat(baseball.certificateOfElimination("Russia")).isNull();
        assertThat(baseball.certificateOfElimination("Brazil")).isNull();
        assertThat(baseball.certificateOfElimination("Iran")).isNull();
        assertThat(baseball.certificateOfElimination("Italy")).isNull();
        assertThat(baseball.certificateOfElimination("Cuba")).isNull();
        assertThat(baseball.certificateOfElimination("Argentina")).isNull();
        assertThat(baseball.certificateOfElimination("USA")).isNull();
        assertThat(baseball.certificateOfElimination("Japan")).containsExactly(
                "Iran", "Poland", "Brazil", "Russia"
        );
        assertThat(baseball.certificateOfElimination("Serbia")).containsExactly(
                "Iran", "Poland", "Brazil", "Russia"
        );
        assertThat(baseball.certificateOfElimination("Egypt")).containsExactly(
                "Poland"
        );
        assertThat(baseball.certificateOfElimination("China")).containsAnyIn(ImmutableList.of(
                "Iran", "Poland", "Brazil", "Russia"
        ));
        assertThat(baseball.certificateOfElimination("China")).hasSize(1);
    }
}
