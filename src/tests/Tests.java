package tests;

import com.company.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class Tests {

    @Nested
    @DisplayName("No operation, only a constant")
    class NoOp {

        @Test
        @DisplayName("Empty string")
        void EmptyString() {
            assertEquals("",
                    CalcLib.main(""));
        }

        @Test
        @DisplayName("Just a single number")
        void SingleNumber() {
            assertEquals("123456789",
                    CalcLib.main("123456789"));
        }

        @Test
        @DisplayName("9 floating digits")
        void LongFloat() {
            assertEquals("123456789.12345679",
                    CalcLib.main("123456789.123456789"));
        }

        @Test
        @DisplayName("Remove the floating zeros")
        void FloatingZeros() {
            assertEquals("123456789.1234",
                    CalcLib.main("123456789.12340000"));
            assertEquals("1",
                    CalcLib.main("0.1 + 0.9"));
        }
    }

    @Test
    @DisplayName("Acceptance of both ',' and '.' as floating point symbols")
    void FloatingSymbol() {
        assertEquals("0.1",
                CalcLib.main("0.1"));
        assertEquals("0.1",
                CalcLib.main("0,1"));
        assertEquals("0.2",
                CalcLib.main("0,1 + 0.1"));
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
                assertEquals("246913578",
                        CalcLib.main("123456789 + 123456789"));
            }

            @Test
            @DisplayName("Addition of real numbers")
            void AddR() {
                assertEquals("123456789.12345678",
                        CalcLib.main("123456789.0 + 0.12345678"));
            }
        }

        @Nested
        @DisplayName("Simple subtraction tests")
        class Subtraction {

            @Test
            @DisplayName("Subtraction of natural numbers")
            void SubN() {
                assertEquals("0",
                        CalcLib.main("123456789 - 123456789"));
            }

            @Test
            @DisplayName("Subtraction of real numbers")
            void SubR() {
                assertEquals("123456788.87654322",
                        CalcLib.main("123456789.0 - 0.12345678"));
            }
        }

        @Nested
        @DisplayName("Simple multiplication tests")
        class Multiplication {

            @Test
            @DisplayName("Multiplication of natural numbers")
            void MulN() {
                assertEquals("838102050",
                        CalcLib.main("12345 * 67890"));
            }

            @Test
            @DisplayName("Multiplication of real numbers, natural result")
            void MulRN() {
                assertEquals("3085",
                        CalcLib.main("2.5 * 1234"));
            }

            @Test
            @DisplayName("Multiplication of real numbers, real result")
            void MulRR() {
                assertEquals("5555.27777778",
                        CalcLib.main("2.5 * 2222.111111111"));
            }
        }

        @Nested
        @DisplayName("Simple division tests")
        class Division {

            @Test
            @DisplayName("Division of natural numbers, natural result")
            void DivNN() {
                assertEquals("6172839",
                        CalcLib.main("12345678 / 2"));
            }

            @Test
            @DisplayName("Division of natural numbers, real result")
            void DivNR() {
                assertEquals("6172839",
                        CalcLib.main("12345678 / 2"));
            }

            @Test
            @DisplayName("Division of real numbers, natural result")
            void DivRN() {
                assertEquals("493827158",
                        CalcLib.main("123456789.5 / 0.25"));
            }

            @Test
            @DisplayName("Division of real numbers, real result")
            void DivRR() {
                assertEquals("0.10000549",
                        CalcLib.main("0.12345678 / 1.2345"));
            }

            @Test
            @DisplayName("Division, periodic result in 8 floating digits")
            void DivPeriodic() {
                assertEquals("0.33333333",
                        CalcLib.main("1 / 3"));
                assertEquals("0.66666667",
                        CalcLib.main("2 / 3"));
            }
        }
    }

    @Nested
    @DisplayName("Advanced operations, single operator")
    class AdvancedSingle {

        @Test
        @DisplayName("Factorial")
        void Fact() {
            assertEquals("362880",
                    CalcLib.main("9!"));
        }

        @Test
        @DisplayName("Power (2) of a natural number")
        void PowN() {
            assertEquals("4",
                    CalcLib.main("pow(2, 2)"));
            assertEquals("4",
                    CalcLib.main("2^2"));
        }

        @Test
        @DisplayName("Power (2) of a real number")
        void PowR() {
            assertEquals("0.25",
                    CalcLib.main("pow(0.5, 2)"));
            assertEquals("0.25",
                    CalcLib.main("0.5^2"));
        }

        @Test
        @DisplayName("Power (32) of a real number")
        void PowTo4R() {
            assertEquals("4294967296",
                    CalcLib.main("pow(2, 32)"));
            assertEquals("4294967296",
                    CalcLib.main("2^32"));
        }

        @Test
        @DisplayName("Square root with a natural number")
        void sqrtN() {
            assertEquals("2",
                    CalcLib.main("root(4, 2)"));
        }

        @Test
        @DisplayName("Square root with a real number")
        void sqrtR() {
            assertEquals("1.73205081",
                    CalcLib.main("root(3, 2)"));
        }

        @Test
        @DisplayName("Cube root with a natural number")
        void cbrtN() {
            assertEquals("3",
                    CalcLib.main("root(27, 3)"));
        }

        @Test
        @DisplayName("Cube root with a real number")
        void cbrtR() {
            assertEquals("4.64158883",
                    CalcLib.main("root(100, 3)"));
        }

        @Test
        @DisplayName("Bigger root with a natural number")
        void root() {
            assertEquals("2",
                    CalcLib.main("root(128, 7)"));
        }

        @Test
        @DisplayName("Modulo with a natural number")
        void modN() {
            assertEquals("2",
                    CalcLib.main("123456 % 17"));
            assertEquals("2",
                    CalcLib.main("123456 mod 17"));
        }
    }

    @Nested
    @DisplayName("White characters influence")
    class WhiteChars {

        @Test
        @DisplayName("A space at the start or/and the end of the input")
        void StartEndSpace() {
            assertEquals("1",
                    CalcLib.main("  1  "));
        }

        @Test
        @DisplayName("A space around a basic operation symbol")
        void BasicSpace() {
            assertEquals("2",
                    CalcLib.main("1 + 1"));
            assertEquals("0",
                    CalcLib.main("1 - 1"));
            assertEquals("1",
                    CalcLib.main("1 * 1"));
            assertEquals("1",
                    CalcLib.main("1  /  1"));
        }

        @Nested
        @DisplayName("A space around/in an advanced operation symbol")
        class AdvancedSpace {

            @Test
            @DisplayName("A space in a factorial")
            void SpaceInFact() {
                assertEquals("362880",
                        CalcLib.main(" 9 ! "));
                assertEquals("362880",
                        CalcLib.main("9!"));
            }

            @Test
            @DisplayName("Weird spaces in a power")
            void SpaceInPow() {
                assertEquals("4",
                        CalcLib.main(" pow( 2 ,2 ) "));
                assertEquals("4",
                        CalcLib.main(" 2 ^ 2 "));
                assertEquals("4",
                        CalcLib.main("pow(2,2)"));
                assertEquals("4",
                        CalcLib.main("2^2"));
            }

            @Test
            @DisplayName("Weird spaces in a root")
            void SpaceInRoot() {
                assertEquals("2",
                        CalcLib.main(" root( 4 ,2 ) "));
                assertEquals("2",
                        CalcLib.main("root(4,2)"));
            }

            @Test
            @DisplayName("Weird spaces in a modulo")
            void SpaceInMod() {
                assertEquals("2",
                        CalcLib.main(" 123456%17 "));
                assertEquals("2",
                        CalcLib.main(" 123456 % 17 "));
                assertEquals("2",
                        CalcLib.main(" 123456mod17 "));
                assertEquals("2",
                        CalcLib.main(" 123456 mod 17 "));
            }
        }
    }

    @Nested
    @DisplayName("Illegal input")
    class IllegalInput {

        @Test
        @Disabled
        @DisplayName("Too huge numbers")
        void HugeNums() {
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("12345678901234567890123456789012345678901234567890 + 1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("9876543210 * 9876543210 * 9876543210"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("12345678901234567890123456789012345678901234567890.1 + 0.9"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("9876543210.1 * 9876543210.1 * 9876543210.1"));
        }

        @Test
        @DisplayName("Illegal format of input")
        void IllegalFormat() {
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + !1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + sqrt(4, 2"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + sqrt 4, 2)"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("sqrt(4, 2 + 2"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + ,1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + .1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 +"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 *"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("root()"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("root(2, )"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("root(2 2)"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("pow()"));
        }

        @Test
        @DisplayName("Illegal characters")
        void IllegalChars() {
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + {1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + 1}"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + ;1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + &1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + $1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + #1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + @1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + [1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + ]1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + \\1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + '1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + \"1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + :1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + >1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + <1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + _1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + `1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + a1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + A1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + z1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + Z1"));
        }

        @Test
        @DisplayName("Illegal numbers")
        void IllegalNums() {
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1.01.02"));
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1.01,02"));
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1,01,02"));
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1,01.02"));
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1,.02"));
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1..02"));
            assertThrows(NumberFormatException.class,
                    () -> CalcLib.main("1.,02"));
        }

        @Test
        @DisplayName("Illegal operation/operand")
        void IllegalOp() {
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("root(2, 0)"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("root(2, -1)"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("root(2, 1.2)"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("pow(2, 1.2)"));

            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1.1 mod 1.1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1.1 % 1.1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 mod 1.1"));
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 % 1.1"));

            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("2.5!"));
        }

        @Test
        @DisplayName("Empty input")
        void EmptyInput() {
            assertEquals("",
                    CalcLib.main(""));
        }
    }

    @Test
    @DisplayName("Operation precedence")
    void OpPrec() {
        assertEquals("2",
                CalcLib.main("1 + 1 * 2 - 1"));
        assertEquals("5",
                CalcLib.main("1 + 10 / 2 - 1"));
        assertEquals("123",
                CalcLib.main("123 + 1 % 2 - 1"));
        assertEquals("123",
                CalcLib.main("123 + 1 mod 2 - 1"));
        assertEquals("150",
                CalcLib.main("100 + 5 * root(100, 2)"));
        assertEquals("300",
                CalcLib.main("100 + 2 * pow(10, 2)"));
        assertEquals("112",
                CalcLib.main("100 + 2 * 3!"));
    }

    @Test
    @DisplayName("More complex calculations")
    void ComplexCalculations() {
        assertEquals("366782.25063265",
                CalcLib.main("1234 * root(10, 2) + 17mod2 * 9!"));
        assertEquals("-3627200",
                CalcLib.main("pow(4, 2) * root(10000, 2) - 10! * 120%17"));
        assertEquals("-359",
                CalcLib.main("100*100/250+100-50*10+1"));
        assertEquals("18101.93359838",
                CalcLib.main("100%21*400/root(2,2)*pow(2,2)"));
        assertEquals("0",
                CalcLib.main("pow(2, 2) - 2*5 + 6"));
        assertEquals("-32749.25531915",
                CalcLib.main("root(123456, 4) - pow(2, 15)"));
    }


    @Nested
    @DisplayName("Advanced tests for unnecessary implementation")
    class Advanced {

        @Disabled
        @Test
        @DisplayName("Calculations with parentheses")
        void Parentheses() {
            assertEquals("1.86384262",
                    CalcLib.main("1234 * (root(10, 2) + (17mod2)) * 9!"));
            assertEquals("-3627100",
                    CalcLib.main("(pow(4, 2) + 1) * root(10000, 2) - (10! * (120%17))"));
            assertEquals("-571.42857142",
                    CalcLib.main("(100*100)/(250+100)-50*(10+(1*2))"));
            assertEquals("1131.37084989",
                    CalcLib.main("(((100%21)*400)/(root(2,2)*((pow(2,2)))))"));
            assertEquals("-18",
                    CalcLib.main("pow(2, 2) - (2*(5 + (6)))"));
        }

        @Disabled
        @Test
        @DisplayName("Basic operations within pow and sqrt")
        void OpWithinOp() {
            assertEquals("16",
                    CalcLib.main("pow(2+2, 2)"));
            assertEquals("16",
                    CalcLib.main("pow(2*2, 2)"));
            assertEquals("10",
                    CalcLib.main("root(50+50, 2)"));
            assertEquals("10",
                    CalcLib.main("root(50*2, 2)"));
        }

        @Disabled
        @Test
        @DisplayName("Uneven number of parentheses")
        void NoPairPar() {
            assertThrows(ArithmeticException.class,
                    () -> CalcLib.main("1 + (1 + (1 + 1)"));
        }
    }

}