package ca.bcit.comp1510.lab09;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for Complex.
 *
 * @author Xing Jiarui
 * @version 2024.3.29
 */
public class ComplexTest {

    /**
     * The delta used for equality comparisons.
     */
    private final double DELTA = 1E-10;

    /**
     * Creates an array of Instance of Complex.
     *
     * @return an array of Instance of Complex
     */
    private Complex[] Instances() {
        return new Complex[]{
                new Complex(1, 2),
                new Complex(1, -2),
                new Complex(-1, 2),
                new Complex(-1, -2),
                new Complex(5, Math.PI / 4),
                new Complex(5, Math.PI / 3),
                new Complex(5, Math.PI / 2),
                new Complex(5, Math.PI),
                new Complex(0, 0),
                new Complex(0, 1),
                new Complex(1, 0)
        };
    }

    /**
     * To test whether the two Complex are equal within an error margin. I was thinking of passing a
     * method as an argument, just like in JS.
     */
    @FunctionalInterface
    private interface ComplexDefinition {
        Complex define(Complex complex);
    }

    @FunctionalInterface
    private interface ComplexDefinitionThrow {
        Complex define(Complex complex) throws Exception;

    }

    /**
     * To test whether the two Complex are equal within an error margin.
     *
     * @param definition the definition of the operation
     */
    private void isEqual(ComplexDefinition definition) {
        for (Complex orig : Instances()) {
            Complex res = definition.define(orig);
            assertEquals(orig.re, res.re, DELTA);
            assertEquals(orig.im, res.im, DELTA);
        }
    }

    /**
     * To test whether the two Complex are equal within an error margin.This overload method is used
     * when the exception is thrown.
     *
     * @param definition the definition of the operation
     * @param message    the message to be thrown
     */
    private void isEqual(ComplexDefinitionThrow definition, String message) {
        for (Complex orig : Instances()) {
            try {
                Complex res = definition.define(orig);
                assertEquals(orig.re, res.re, DELTA);
                assertEquals(orig.im, res.im, DELTA);
            } catch (Exception e) {
                assertEquals(message, e.getMessage());
            }
        }
    }

    /**
     * Tests the polarComplex function of a complex number.
     */
    @Test
    public void testPolarComplex() {
        /*
         * Definition: polarComplex(z) = r * e^(iθ) = 复数的极坐标
         * r = abs(z) = 极径
         * θ = arg(z) = tan^(-1)(y/x) = arctan(y/x) = 幅角
         * polarComplex(z) = r * cos(θ) , i * sin(θ) = (abs(z), arg(z))
         *  */
        isEqual(orig -> orig.polarComplex(orig.abs(), orig.arg()));
    }

    /**
     * Tests the abs function of a complex number.
     */
    @Test
    public void testAbs() {
        /*
         * Definition:
         * abs(z) = |z| = sqrt(z.real^2 + z.imag^2)
         * */
        for (Complex orig : Instances()) {
            assertEquals(orig.abs(), Math.sqrt(Math.pow(orig.re, 2) + Math.pow(orig.im, 2)), DELTA);
        }
    }

    /**
     * Tests the arg function of a complex number.
     */
    @Test
    public void testArg() {
        /*
         * Definition:
         *  arg(z) = tan^(-1)(y/x) = arctan(y/x)
         * */
        for (Complex orig : Instances()) {
            assertEquals(orig.arg(), Math.atan2(orig.im, orig.re), DELTA);
        }
    }

    /**
     * Tests the conjugate function of a complex number.
     */
    @Test
    public void testConjugate() {
        /* Definition: conjugate(z) = (z.real, -z.imag)`= z的共轭复数 */
        isEqual(orig -> new Complex(orig.re, -orig.im).conjugate());
    }

    /**
     * Tests the add and subtract functions of a complex number.
     */
    @Test
    public void testAddSubtract() {
        /*
         * Definition:
         * add(z1, z2) = (z1.real + z2.real, z1.imag + z2.imag);
         * subtract(z1, z2) = (z1.real - z2.real, z1.imag - z2.imag);
         * z + z - z = z.
         * */
        isEqual(orig -> orig.add(orig).subtract(orig));
    }

    /**
     * Tests the multiply and divide functions of a complex number.
     */
    @Test
    public void testMultiplyDivide() {
        /*
         * Definition:
         * multiply(z1, z2) = (z1.real * z2.real - z1.imag * z2.imag, z1.real * z2.imag + z1.imag
         *     z2.real);
         * divide(z1, z2) = (z1.real * z2.real + z1.imag * z2.imag, z1.imag * z2.real - z1.real
         *     z2.imag) / (z2.real^2 + z2.imag^2);
         * z * z / z = z.
         * */
        isEqual(orig -> orig.multiply(orig).divide(orig), "Tried to divide by zero");
    }

    /**
     * Tests the reciprocal function of a complex number.
     */
    @Test
    public void testReciprocal() {
        /* Definition: reciprocal(z) = 1 / z = conjugate(z) / z * conjugate(z) */
        isEqual(orig -> Complex.ONE.divide(orig).reciprocal(),
                "Tried to divide by zero");
        isEqual(orig -> orig.conjugate().divide(orig.multiply(orig.conjugate())).reciprocal(),
                "Tried to divide by zero");
    }

    /**
     * Tests the exp and log functions of a complex number.
     */
    @Test
    public void testExpLog() {
        /*Definition:
         * exp(z) = z^e = (r * e^(i * θ))^e = r^e * e^(i * e * θ)
         * log(z) = log(re^(iθ)) = log(r)
         * log(e^z) = z
         * */
        isEqual(orig -> orig.exp().log());
    }

    /**
     * Tests the equals function of a complex number.
     */
    @Test
    public void testEquals() {
        isEqual(orig -> new Complex(orig.re, orig.im));
    }
}