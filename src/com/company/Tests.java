package com.company;

import com.company.CalcLib;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/*
Basic single: +, -, /, *
Basic single edges:
Advanced single: fact, pow, sqrt, mod
Advanced single edges:

Overflow single:
Operator precedence:
White characters impact:

More complex calculations
 */
public class Tests {

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void beforeEach() {
    }

    @AfterEach
    void afterEach() {
    }

    @AfterAll
    static void afterAll() {
    }

    @Nested
    @DisplayName("Basic operations, single operator")
    class BasicSingle {

        @BeforeEach
        void beforeEach() {
        }

        @AfterEach
        void afterEach() {
        }

        @Test
        @DisplayName("Addition")
        void Addition() {
            assertEquals(16, CalcLib.main("1+1"));
        }

        @Test
        @DisplayName("Subtraction")
        void Subtraction() {
            assertEquals(16, CalcLib.main("1+1"));
        }

    }

    @Nested
    @DisplayName("Basic operations, single operator")
    class AdvancedSingle {

        @BeforeEach
        void beforeEach() {
        }

        @AfterEach
        void afterEach() {
        }

        @Test
        @DisplayName("Multiplication")
        void Multiplication() {
            assertEquals(16, CalcLib.main("1*1"));
        }

        @Test
        @DisplayName("Division")
        void Division() {
            assertEquals(16, CalcLib.main("1*1"));
        }

    }
}