package com.pirnogion.piet.core;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by 1 on 13.03.2017.
 */
public class PietColorsetTest {
    private final int BIG_VALUE = Integer.MAX_VALUE;
    private final int SMALL_VALUE = Integer.MIN_VALUE;

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetColorFrom_IndexOutOfBound() throws Exception {
        /* Execution */
        PietColorset.getColorFrom(SMALL_VALUE); // ExpectedException: IndexOutOfBoundException
        PietColorset.getColorFrom(BIG_VALUE);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetIndexFrom_NullPointer() throws Exception {
        /* Execution */
        PietColorset.getIndexFrom(SMALL_VALUE);
        PietColorset.getIndexFrom(BIG_VALUE);
    }

    @Test
    public void testGetColorIndexes() throws Exception {

    }

    @Test
    public void testGetCommandSignature() throws Exception {

    }

}