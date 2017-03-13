package com.pirnogion.piet.core;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created by 1 on 13.03.2017.
 */
public class PietStackTest {
    private static final int STACK_SIZE = 10;
    private static final int BIG_VALUE = Integer.MAX_VALUE;

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testConstructPietStack() throws Exception {
        /* Execution */
        PietStack stack_tries1 = new PietStack(-1);
        PietStack stack_tries2 = new PietStack(0);
        PietStack stack_tries3 = new PietStack(STACK_SIZE);
    }

    @Test
    public void testReplace() throws Exception {
        /* Data */
        PietStack stack = new PietStack(STACK_SIZE);

        /* Execution */
        for (int i = -1; i < STACK_SIZE+1; ++i)
        {
            stack.replace(i, BIG_VALUE);
        }
    }

    @Test
    public void testPush() throws Exception {
        /* Data */
        PietStack stack = new PietStack(STACK_SIZE);

        /* Execution */
        for (int i = 0; i < STACK_SIZE+1; ++i)
        {
            stack.push(BIG_VALUE);
        }

        /* Assertion */
        assertEquals( true,stack.getSize() <= stack.getMaxSize() );
    }

    @Test
    public void testPop() throws Exception {
        /* Data */
        PietStack stack = new PietStack(STACK_SIZE);

        /* Assertion */
        assertEquals( 0, stack.pop().intValue() );
    }

}