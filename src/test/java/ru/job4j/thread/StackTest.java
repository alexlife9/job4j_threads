package ru.job4j.thread;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * CAS - операции
 *
 * @author Alex_life
 * @version 1.0
 * @since 18.09.2022
 */
public class StackTest {

    @Test
    public void when3PushThen3Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertThat(stack.poll()).isEqualTo(3);
        assertThat(stack.poll()).isEqualTo(2);
        assertThat(stack.poll()).isEqualTo(1);
    }

    @Test
    public void when1PushThen1Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        assertThat(stack.poll()).isEqualTo(1);
    }

    @Test
    public void when2PushThen2Poll() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.poll()).isEqualTo(2);
        assertThat(stack.poll()).isEqualTo(1);
    }
}