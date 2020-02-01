package toc.hec.helloworld2;

import org.junit.Test;
import static org.junit.Assert.*;



public class AdditionUnitTest {

    addition myaddition = new addition();

    @Test
    public void addition_isCorrect() {
        assertEquals(5, myaddition.add(2,3));
    }
}





