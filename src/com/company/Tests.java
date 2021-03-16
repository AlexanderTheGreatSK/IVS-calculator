package com.company;

import com.company.CalcLib;

import org.junit.jupiter.api.*;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

/*
No operation
Remove floating zeros
White characters impact
Floating point as , and .
Illegal number
    Two floating points
Illegal characters
    ; : ' "...
Periodic result

Basic single: +, -, /, *
Basic single edges
Advanced single: fact, pow, sqrt, mod
Advanced single edges

Overflow single?
Operator precedence

More complex calculations
 */
public class Tests {

    @Nested
    @DisplayName("No operation, only a constant")
    class NoOp {

        @Test
        @DisplayName("Just a single number")
        void SingleNumber() {
            assertEquals("123456789.123456789", CalcLib.main("123456789.123456789"));
        }

        @Test
        @DisplayName("9 floating digits")
        void LongFloat() {
            assertEquals("123456789.123456789", CalcLib.main("123456789.123456789"));
        }

        @Test
        @DisplayName("Remove the floating zeros")
        void FloatingZeros() {
            assertEquals("123456789.1234", CalcLib.main("123456789.12340000"));
        }
    }

    @Test
    @DisplayName("Acceptance of both ',' and '.' as floating point symbols")
    void FloatingSymbol() {
        assertEquals("0.1", CalcLib.main("0.1"));
        assertEquals("0.1", CalcLib.main("0,1"));
    }

    @Nested
    @DisplayName("Basic operations, single operator")
    class BasicSingle {

        @Nested
        @DisplayName("Simple addition tests")
        class Addition {

            @Test
            @DisplayName("Addition of natural numbers")
            void AddN() {
                assertEquals("246913578", CalcLib.main("123456789 + 123456789"));
            }

            @Test
            @DisplayName("Addition of real numbers")
            void AddR() {
                assertEquals("123456789.12345678", CalcLib.main("123456789.0 + 0.12345678"));
            }
        }

        @Nested
        @DisplayName("Simple subtraction tests")
        class Subtraction {

            @Test
            @DisplayName("Subtraction of natural numbers")
            void SubN() {
                assertEquals("0", CalcLib.main("123456789 - 123456789"));
            }

            @Test
            @DisplayName("Subtraction of real numbers")
            void SubR() {
                assertEquals("123456788,87654322", CalcLib.main("123456789.0 - 0.12345678"));
            }
        }

        @Nested
        @DisplayName("Simple multiplication tests")
        class Multiplication {

            @Test
            @DisplayName("Multiplication of natural numbers")
            void MulN() {
                assertEquals("838102050", CalcLib.main("12345 * 67890"));
            }

            @Test
            @DisplayName("Multiplication of real numbers, natural result")
            void MulRN() {
                assertEquals("3085", CalcLib.main("2.5 * 1234"));
            }

            @Test
            @DisplayName("Multiplication of real numbers, real result")
            void MulRR() {
                assertEquals("2.5 * 2222.11111111", CalcLib.main("2.5 * 2222.11111111"));
            }
        }

        @Nested
        @DisplayName("Simple division tests")
        class Division {

            @Test
            @DisplayName("Division of natural numbers, natural result")
            void DivNN() {
                assertEquals("6172839", CalcLib.main("12345678 / 2"));
            }

            @Test
            @DisplayName("Division of natural numbers, real result")
            void DivNR() {
                assertEquals("6172839", CalcLib.main("12345678 / 2"));
            }

            @Test
            @DisplayName("Division of real numbers, natural result")
            void DivRN() {
                assertEquals("6172839", CalcLib.main("123456789.5 / 0.25"));
            }

            @Test
            @DisplayName("Division of real numbers, real result")
            void DivRR() {
                assertEquals("0.10000549", CalcLib.main("0.12345678 / 1.2345"));
            }

            @Test
            @DisplayName("Division, periodic result in 16 floating digits")
            void DivPeriodic() {
                assertEquals("0.33333333", CalcLib.main("1 / 3"));
                assertEquals("0.66666667", CalcLib.main("2 / 3"));
            }
        }
    }

    @Nested
    @DisplayName("Advanced operations, single operator")
    class AdvancedSingle {

        @Test
        @DisplayName("Factorial")
        void Fact() {
            assertEquals("362880", CalcLib.main("9!"));
        /* TODO
            assertThrows(CalcLib.main("2.5!"));
         */
        }

        @Test
        @DisplayName("Power (2) of a natural number")
        void PowN() {
            assertEquals("4", CalcLib.main("pow(2, 2)"));
            assertEquals("4", CalcLib.main("2^2"));
        }

        @Test
        @DisplayName("Power (2) of a real number")
        void PowR() {
            assertEquals("0.25", CalcLib.main("pow(0.5, 2)"));
            assertEquals("0.25", CalcLib.main("0.5^2"));
        }

        @Test
        @DisplayName("Power (4) of a real number")
        void PowTo4R() {
            assertEquals("4294967296", CalcLib.main("pow(2, 32)"));
            assertEquals("4294967296", CalcLib.main("2^32"));
        }

        @Test
        @DisplayName("Square root with a natural number")
        void sqrtN() {
            assertEquals("2", CalcLib.main("root(4, 2)"));
        }

        @Test
        @DisplayName("Square root with a real number")
        void sqrtR() {
            assertEquals("1,73205080", CalcLib.main("root(3, 2)"));
        }

        @Test
        @DisplayName("Cube root with a natural number")
        void cbrtN() {
            assertEquals("3", CalcLib.main("root(27, 3)"));
        }

        @Test
        @DisplayName("Cube root with a real number")
        void cbrtR() {
            assertEquals("4.64158883", CalcLib.main("root(100, 3)"));
        }

        @Test
        @DisplayName("Bigger root with a natural number")
        void root() {
            assertEquals("2", CalcLib.main("root(255, 7)"));
        }

        @Test
        @DisplayName("Modulo with a natural number")
        void modN() {
            assertEquals("2", CalcLib.main("123456 % 17"));
        }

        /* TODO
        @Test
        @DisplayName("Modulo with a real number")
        void modR() {
            assertThrows("2", CalcLib.main("123456 % 17"));
        }
         */
    }
    @Nested
    @DisplayName("White characters influence")
    class WhiteChars {

        @Test
        @DisplayName("A space at the start or/and the end of the input")
        void StartEndSpace() {
            assertEquals("1", CalcLib.main("  1  "));
        }

        @Test
        @DisplayName("A space around a basic operation symbol")
        void BasicSpace() {
            assertEquals("2", CalcLib.main("1 + 1"));
            assertEquals("0", CalcLib.main("1 - 1"));
            assertEquals("1", CalcLib.main("1 * 1"));
            assertEquals("1", CalcLib.main("1  /  1"));
        }

        @Nested
        @DisplayName("A space around/in an advanced operation symbol")
        class AdvancedSpace {

            @Test
            @DisplayName("A space in a factorial")
            void SpaceInFact() {
                assertEquals("362880", CalcLib.main(" 9 ! "));
                assertEquals("362880", CalcLib.main("9!"));
            }

            @Test
            @DisplayName("Weird spaces in a power")
            void SpaceInPow() {
                assertEquals("4", CalcLib.main(" pow( 2 ,2 ) "));
                assertEquals("4", CalcLib.main(" 2 ^ 2 "));
                assertEquals("4", CalcLib.main("pow(2,2)"));
                assertEquals("4", CalcLib.main("2^2"));
            }

            @Test
            @DisplayName("Weird spaces in a root")
            void SpaceInRoot() {
                assertEquals("2", CalcLib.main(" root( 4 ,2 ) "));
                assertEquals("2", CalcLib.main("root(4,2)"));
            }

            @Test
            @DisplayName("Weird spaces in a modulo")
            void SpaceInMod() {
                assertEquals("2", CalcLib.main(" 123456%17 "));
                assertEquals("2", CalcLib.main(" 123456 % 17 "));
            }
        }
    }

}