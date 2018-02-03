/******************************************************************************
 *  Compilation:  javac RandomizedQueueTest.java
 *  Execution:    java RandomizedQueueTest
 *  Dependencies: RandomizedQueue.java org.junit.*
 *
 *  Unit tests for RandomizedQueue class.
 *
 ******************************************************************************/

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class RandomizedQueueTest {
    private RandomizedQueue<String> queue;

    @Before
    public void setUp(){ queue = new RandomizedQueue<>(); }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for isEmpty()
    ////////////////////////////////////////////////////////////////////////////

    @Test
    public void newQueue_shouldBeEmpty(){
        assertTrue(queue.isEmpty());
    }

    @Test
    public void enqueueItem_shouldNotBeEmpty(){
        queue.enqueue("a");
        assertFalse(queue.isEmpty());
    }

    @Test
    public void enqueueItem_thenSample_shouldNotBeEmpty(){
        queue.enqueue("a");
        assertFalse(queue.isEmpty());
    }

    @Test
    public void enqueueItem_thenDequeueItem_shouldBeEmpty(){
        queue.enqueue("a");
        queue.dequeue();
        assertTrue(queue.isEmpty());
    }

    @Test
    public void enqueueItem_thenDequeueItem_thenEnqueueItem_shouldNotBeEmpty(){
        queue.enqueue("a");
        queue.dequeue();
        queue.enqueue("b");
        assertFalse(queue.isEmpty());
    }

    @Test
    public void enqueueTwoItems_thenDequeueItem_shouldNotBeEmpty(){
        queue.enqueue("a");
        queue.enqueue("b");
        queue.dequeue();
        assertFalse(queue.isEmpty());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for size()
    ////////////////////////////////////////////////////////////////////////////
    @Test
    public void newQueue_shouldHaveSizeZero(){
        assertEquals(0, queue.size());
    }

    @Test
    public void enqueueItem_shouldHaveSizeOne(){
        queue.enqueue("a");
        assertEquals(1, queue.size());
    }

    @Test
    public void enqueueItem_thenSample_shouldHaveSizeOne(){
        queue.enqueue("a");
        queue.sample();
        assertEquals(1, queue.size());
    }

    @Test
    public void enqueueItem_thenDequeue_shouldHaveSizeZero(){
        queue.enqueue("a");
        queue.dequeue();
        assertEquals(0,  queue.size());
    }

    @Test
    public void enqueueItem_thenDequeue_thenEnqueue_shouldHaveSizeOne(){
        queue.enqueue("a");
        queue.dequeue();
        queue.enqueue("b");
        assertEquals(1,  queue.size());
    }

    @Test
    public void enqueueTwoItems_thenDequeue_shouldHaveSizeOne(){
        queue.enqueue("a");
        queue.enqueue("b");
        queue.dequeue();
        assertEquals(1,  queue.size());
    }

    @Test
    public void enqueueFiveItems_shouldHaveSizeFive(){
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        assertEquals(5,  queue.size());
    }

    @Test
    public void enqueueFiveItems_thenDequeueFiveItems_shouldHaveSizeZero(){
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        assertEquals(0,  queue.size());
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for enqueue() and dequeue()
    ////////////////////////////////////////////////////////////////////////////

    @Test(expected = IllegalArgumentException.class)
    public void enqueueNull_shouldThrowIllegalArgumentException(){
        queue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueFromEmptyQueue_shouldThrowNoSuchElementException(){
        queue.dequeue();
    }

    @Test
    public void enqueueItem_thenDequeueItem_shouldReturnOriginalItem(){
        queue.enqueue("a");
        assertEquals("a", queue.dequeue());
    }

    @Test
    public void enqueueTwoItems_thenDequeueTwoItems_shouldReturnBothItems(){
        Set<String> results = new LinkedHashSet<>();
        queue.enqueue("a");
        queue.enqueue("b");
        results.add(queue.dequeue());
        results.add(queue.dequeue());
        assertTrue(results.contains("a") && results.contains("b"));
    }


    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for sample()
    ////////////////////////////////////////////////////////////////////////////
    @Test(expected = NoSuchElementException.class)
    public void sampleFromEmptyQueue_throwsNoSuchElementException(){
        queue.dequeue();
    }

    @Test
    public void enqueueItem_thenSample_shouldReturnOnlyItem(){
        queue.enqueue("a");
        assertEquals("a", queue.sample());
    }

    @Test
    public void enqueueItem_thenSampleMultipleTimes_shouldReturnSameItem(){
        queue.enqueue("a");
        assertEquals("a", queue.sample());
        assertEquals("a", queue.sample());
        assertEquals("a", queue.sample());
    }

    @Test
    public void enqueueMultipleItems_thenSample_shouldReturnSomeItemInQueue(){
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");

        Set<String> expected = new LinkedHashSet<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");

        assertTrue(expected.contains(queue.sample()));
    }

    ////////////////////////////////////////////////////////////////////////////
    // Unit tests for iterator()
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////
    // Unit tests for remove()
    ///////////////////////////////////
    @Test(expected = UnsupportedOperationException.class)
    public void removeOnIterator_shouldThrowUnsupportedOperationException(){
        queue.iterator().remove();
    }

    ///////////////////////////////////
    // Unit tests for hasNext()
    ///////////////////////////////////
    @Test
    public void emptyQueue_iteratorShouldNotHaveNext(){
        assertFalse(queue.iterator().hasNext());
    }

    @Test
    public void enqueueItem_iteratorShouldHaveNext(){
        queue.enqueue("eee");
        assertTrue(queue.iterator().hasNext());
    }

    ///////////////////////////////////
    // Unit tests for next()
    ///////////////////////////////////
    @Test(expected = NoSuchElementException.class)
    public void emptyQueue_nextOnIterator_shouldThrowNoSuchElementException(){
        queue.iterator().next();
    }

    @Test
    public void enqueueItem_nextOnIteratorShouldReturnItem(){
        queue.enqueue("eee");
        assertEquals("eee", queue.iterator().next());
    }

    @Test
    public void enqueueItem_iteratorNext_iteratorShouldNoLongerHaveNext(){
        queue.enqueue("eee");
        Iterator<String> iter = queue.iterator();
        iter.next();
        assertFalse(iter.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void enqueueItem_iteratorNext_iteratorNext_ShouldThrowNoSuchElementException(){
        queue.enqueue("eee");
        Iterator<String> iter = queue.iterator();
        iter.next();
        iter.next();
    }


    @Test
    public void multipleIterators_shouldHaveDifferentIterationOrders(){
        /*
         * Flaky test alert!!
         * It's difficult to test random iteration orders, so we
         * make test that is statistically likely to fail.
         *
         * The odds that, for a random ordering of 26 elements, all are equal
         * is quite low.
         */

        List<String> iterationResults1 = new ArrayList<>();
        List<String> iterationResults2 = new ArrayList<>();
        List<String> iterationResults3 = new ArrayList<>();

        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");
        queue.enqueue("e");
        queue.enqueue("f");
        queue.enqueue("g");
        queue.enqueue("h");
        queue.enqueue("i");
        queue.enqueue("j");
        queue.enqueue("k");
        queue.enqueue("l");
        queue.enqueue("m");
        queue.enqueue("n");
        queue.enqueue("o");
        queue.enqueue("p");
        queue.enqueue("q");
        queue.enqueue("r");
        queue.enqueue("s");
        queue.enqueue("t");
        queue.enqueue("u");
        queue.enqueue("v");
        queue.enqueue("w");
        queue.enqueue("x");
        queue.enqueue("y");
        queue.enqueue("z");


        for(String item : queue) { iterationResults1.add(item); }
        for(String item : queue) { iterationResults2.add(item); }
        for(String item : queue) { iterationResults3.add(item); }

        assertFalse(
                iterationResults1.equals(iterationResults2) &&
                        iterationResults2.equals(iterationResults3));
    }

    @Test
    public void nestedIteration_shouldIterateOverItemsExhaustivelyAndIndependently(){
        Set<String> results = new HashSet<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");

        for(String outer : queue) {
            for (String inner : queue){
                results.add(outer + inner);
            }
        }

        Set<String> expected = new HashSet<>();
        expected.add("aa");
        expected.add("ab");
        expected.add("ac");
        expected.add("ba");
        expected.add("bb");
        expected.add("bc");
        expected.add("ca");
        expected.add("cb");
        expected.add("cc");
        assertEquals(expected, results);
    }

    ///////////////////////////////////
    // Unit tests for ConcurrentModificationException()
    ///////////////////////////////////

}
