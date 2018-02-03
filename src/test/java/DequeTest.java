/******************************************************************************
 *  Compilation:  javac DequeTest
 *  Execution: tbd
 *  Dependencies: Deque.java org.junit.Test org.junit.Assert
 *
 *  JUnit4 unit tests for Deque.java
 ******************************************************************************/

import org.junit.Test;

import java.util.*;
import com.google.common.truth.Truth;

import static org.junit.Assert.*;


public class DequeTest {
    @Test
    public void newDequeIsEmpty(){
        Deque<Integer> deque = new Deque<>();
        Truth.assertThat(deque).isEmpty();
    }
    @Test
    public void newDequeHasSizeZero(){
        Deque<Integer> deque = new Deque<>();
        assertEquals(0, deque.size());
    }

    @Test
    public void addFirstToEmptyDequeShouldIncreaseSize(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        assertEquals(1, deque.size());
    }

    @Test
    public void addFirstTwiceShouldIncreaseSize(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(2, deque.size());
    }

    @Test
    public void addFirstToEmptyDequeAndRemovingFirstShouldEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeFirst();
        assertTrue(deque.isEmpty());
        assertEquals(0, deque.size());
    }

    @Test
    public void addFirstToEmptyDequeAndRemovingLastShouldEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void addLastToEmptyDequeAndRemovingFirstShouldEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.removeFirst();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void addLastToEmptyDequeAndRemovingLastShouldEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void fillDeque_ThenEmptyDeque_ThenRefillDeck_ShouldEndWithNonEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.removeFirst();
        deque.removeFirst();
        deque.addFirst(3);
        deque.addFirst(4);
        assertEquals(2, deque.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addFirstWithNullItem_ShouldThrowIllegalArgumentException(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLastWithNullItem_ShouldThrowIllegalArgumentException(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(null);
    }

    @Test
    public void addFirst_ThenRemoveFirst_ShouldReturnFirstElement(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        assertEquals(1, deque.removeFirst().intValue());
    }

    @Test
    public void addFirstTwice_ThenRemoveFirst_ShouldReturnMostRecentElement(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        assertEquals(2, deque.removeFirst().intValue());
    }

    @Test
    public void addLast_ThenRemoveFirst_ShouldReturnFirstElement(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        assertEquals(1, deque.removeFirst().intValue());
    }

    @Test
    public void addLastTwice_ThenRemoveFirst_ShouldReturnFirstElement(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(1, deque.removeFirst().intValue());
    }

    @Test
    public void addLastTwice_ThenRemoveLast_shouldReturnLastElement(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addLast(2);
        assertEquals(2, deque.removeLast().intValue());
    }

    @Test
    public void addFirst_thenAddLast_shouldAddInCorrectOrder(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addLast(2);
        assertEquals(1, deque.removeFirst().intValue());
        assertEquals(2, deque.removeFirst().intValue());
    }

    @Test
    public void addLast_thenAddFirst_shouldAddInCorrectOrder(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(2);
        deque.addFirst(1);
        assertEquals(1, deque.removeFirst().intValue());
        assertEquals(2, deque.removeFirst().intValue());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFirstFromEmptyDeque_ShouldThrowNoSuchElementException(){
        Deque<Integer> deque = new Deque<>();
        deque.removeFirst();
    }

    @Test
    public void removeFirstFromDequeWithOneElement_shouldCreateEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeFirst();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void removeFirstFromDequeWithMultipleElements(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
        deque.addFirst(4);
        deque.addFirst(3);
        assertEquals(3, deque.removeFirst().intValue());
        assertEquals(4, deque.removeFirst().intValue());
        assertEquals(5, deque.removeFirst().intValue());
    }

    @Test
    public void removeFirst_thenRemoveLast(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        assertEquals(2, deque.removeFirst().intValue());
        assertEquals(4, deque.removeLast().intValue());
        assertEquals(1, deque.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastFromEmptyDeque_ShouldThrowNoSuchElementException(){
        Deque<Integer> deque = new Deque<>();
        deque.removeLast();
    }

    @Test
    public void removeLastFromDequeWithOneElement_shouldCreateEmptyDeque(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void removeLastFromDequeWithMultipleElements(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(5);
        deque.addFirst(4);
        deque.addFirst(3);
        assertEquals(5, deque.removeLast().intValue());
        assertEquals(4, deque.removeLast().intValue());
        assertEquals(3, deque.removeLast().intValue());
    }

    @Test
    public void removeLast_thenRemoveFirst(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        assertEquals(4, deque.removeLast().intValue());
        assertEquals(2, deque.removeFirst().intValue());
        assertEquals(1, deque.size());
    }

    @Test
    public void iteratorForEmptyDeque_hasNextShouldReturnFalse(){
        Deque<Integer> deque = new Deque<>();
        assertFalse(deque.iterator().hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorForEmptyDeque_nextShouldThrowNoSuchElementException(){
        Deque<Integer> deque = new Deque<>();
        deque.iterator().next();
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_keepCallingNext_shouldEventuallyThrowNoSuchElementException(){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(0);
        Iterator<Integer> iter = deque.iterator();
        iter.next();
        iter.next();
        iter.next();
    }

    @Test
    public void iteratorForDequeWithOneItem_hasNextShouldReturnTrue(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        assertTrue(deque.iterator().hasNext());
    }

    @Test
    public void iteratorForDequeWithOneItem_nextShouldReturnOnlyItem(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        assertEquals(1, deque.iterator().next().intValue());
    }

    @Test
    public void iterator_shouldIterateFromFrontToBack(){
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addLast(10);
        deque.addLast(100);
        deque.addLast(1000);
        deque.addLast(10000);

        Iterator<Integer> iter = deque.iterator();
        assertEquals(1, iter.next().intValue());
        assertEquals(10, iter.next().intValue());
        assertEquals(100, iter.next().intValue());
        assertEquals(1000, iter.next().intValue());
        assertEquals(10000, iter.next().intValue());
        assertFalse(iter.hasNext());
    }

    @Test
    public void iterator_shouldSupportForEachLoop(){
        int index = 0;
        String[] results = new String[3];
        Deque<String> deque = new Deque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        for(String item : deque) {
            results[index] = item;
            index++;
        }

        assertEquals("a", results[0]);
        assertEquals("b", results[1]);
        assertEquals("c", results[2]);
    }

    @Test
    public void iterator_shouldSupportMultipleIteratorsSimultaneously() {
        List<String> results = new ArrayList<>();
        Deque<String> deque = new Deque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");
        for(String item: deque){
            for(String innerItem: deque){
                results.add(item + innerItem);
            }
        }
        assertEquals(Arrays.asList("aa", "ab", "ac", "ba", "bb", "bc", "ca", "cb", "cc"), results);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iterator_removeShouldThrowNoSuchElementException(){
        Deque<Integer> deque = new Deque<>();
        deque.iterator().remove();
    }
}
