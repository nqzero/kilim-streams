/*
 * Copyright (c) 1997, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

/*
extensive changes and additons by lytles/nqzero copyright 2016, offered under the same terms
or the MIT license at your choice
*/


package stream2;

import kilim.Pausable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongConsumer;

/**
 * This class contains various methods for manipulating arrays (such as
 * sorting and searching). This class also contains a static factory
 * that allows arrays to be viewed as lists.
 *
 * <p>The methods in this class all throw a {@code NullPointerException},
 * if the specified array reference is null, except where noted.
 *
 * <p>The documentation for the methods contained in this class includes
 * briefs description of the <i>implementations</i>. Such descriptions should
 * be regarded as <i>implementation notes</i>, rather than parts of the
 * <i>specification</i>. Implementors should feel free to substitute other
 * algorithms, so long as the specification itself is adhered to. (For
 * example, the algorithm used by {@code sort(Object[])} does not have to be
 * a MergeSort, but it does have to be <i>stable</i>.)
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @author John Rose
 * @since  1.2
 */
public class Arrays2 {
    static boolean kludge = true;
    static void kludge() {}

    /**
     * Set all elements of the specified array, in parallel, using the
     * provided generator function to compute each element.
     *
     * <p>If the generator function throws an exception, an unchecked exception
     * is thrown from {@code parallelSetAll} and the array is left in an
     * indeterminate state.
     *
     * @param <T> type of elements of the array
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     *        value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static <T> void parallelSetAll(T[] array, IntFunction<? extends T> generator) throws Pausable {
        Objects.requireNonNull(generator);
        IntStream.range(0, array.length).parallel().forEach(i -> { array[i] = generator.apply(i); });
    }

    /**
     * Set all elements of the specified array, using the provided
     * generator function to compute each element.
     *
     * <p>If the generator function throws an exception, it is relayed to
     * the caller and the array is left in an indeterminate state.
     *
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     *        value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static void setAll(int[] array, IntUnaryOperator generator) {
        Objects.requireNonNull(generator);
        for (int i = 0; i < array.length; i++)
            array[i] = generator.applyAsInt(i);
    }

    /**
     * Set all elements of the specified array, in parallel, using the
     * provided generator function to compute each element.
     *
     * <p>If the generator function throws an exception, an unchecked exception
     * is thrown from {@code parallelSetAll} and the array is left in an
     * indeterminate state.
     *
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     * value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static void parallelSetAll(int[] array, IntUnaryOperator generator) throws Pausable {
        Objects.requireNonNull(generator);
        IntStream.range(0, array.length).parallel().forEach(i -> { array[i] = generator.applyAsInt(i); });
    }

    /**
     * Set all elements of the specified array, using the provided
     * generator function to compute each element.
     *
     * <p>If the generator function throws an exception, it is relayed to
     * the caller and the array is left in an indeterminate state.
     *
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     *        value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static void setAll(long[] array, IntToLongFunction generator) {
        Objects.requireNonNull(generator);
        for (int i = 0; i < array.length; i++)
            array[i] = generator.applyAsLong(i);
    }

    /**
     * Set all elements of the specified array, in parallel, using the
     * provided generator function to compute each element.
     *
     * <p>If the generator function throws an exception, an unchecked exception
     * is thrown from {@code parallelSetAll} and the array is left in an
     * indeterminate state.
     *
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     *        value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static void parallelSetAll(long[] array, IntToLongFunction generator) throws Pausable {
        Objects.requireNonNull(generator);
        IntStream.range(0, array.length).parallel().forEach(i -> { array[i] = generator.applyAsLong(i); });
    }

    /**
     * Set all elements of the specified array, using the provided
     * generator function to compute each element.
     *
     * <p>If the generator function throws an exception, it is relayed to
     * the caller and the array is left in an indeterminate state.
     *
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     *        value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static void setAll(double[] array, IntToDoubleFunction generator) {
        Objects.requireNonNull(generator);
        for (int i = 0; i < array.length; i++)
            array[i] = generator.applyAsDouble(i);
    }

    /**
     * Set all elements of the specified array, in parallel, using the
     * provided generator function to compute each element.
     *
     * <p>If the generator function throws an exception, an unchecked exception
     * is thrown from {@code parallelSetAll} and the array is left in an
     * indeterminate state.
     *
     * @param array array to be initialized
     * @param generator a function accepting an index and producing the desired
     *        value for that position
     * @throws NullPointerException if the generator is null
     * @since 1.8
     */
    public static void parallelSetAll(double[] array, IntToDoubleFunction generator) throws Pausable {
        Objects.requireNonNull(generator);
        IntStream.range(0, array.length).parallel().forEach(i -> { array[i] = generator.applyAsDouble(i); });
    }

    /**
     * Returns a {@link Spliterator} covering all of the specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param <T> type of elements
     * @param array the array, assumed to be unmodified during use
     * @return a spliterator for the array elements
     * @since 1.8
     */
    public static <T> Spliterator<T> spliterator(T[] array) {
        return Spliterators.spliterator(array,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator} covering the specified range of the
     * specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param <T> type of elements
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a spliterator for the array elements
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static <T> Spliterator<T> spliterator(T[] array, int startInclusive, int endExclusive) {
        return Spliterators.spliterator(array, startInclusive, endExclusive,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator.OfInt} covering all of the specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param array the array, assumed to be unmodified during use
     * @return a spliterator for the array elements
     * @since 1.8
     */
    public static Spliterator.OfInt spliterator(int[] array) {
        return Spliterators.spliterator(array,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator.OfInt} covering the specified range of the
     * specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a spliterator for the array elements
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static Spliterator.OfInt spliterator(int[] array, int startInclusive, int endExclusive) {
        return Spliterators.spliterator(array, startInclusive, endExclusive,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator.OfLong} covering all of the specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param array the array, assumed to be unmodified during use
     * @return the spliterator for the array elements
     * @since 1.8
     */
    public static Spliterator.OfLong spliterator(long[] array) {
        return Spliterators.spliterator(array,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator.OfLong} covering the specified range of the
     * specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a spliterator for the array elements
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static Spliterator.OfLong spliterator(long[] array, int startInclusive, int endExclusive) {
        return Spliterators.spliterator(array, startInclusive, endExclusive,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator.OfDouble} covering all of the specified
     * array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param array the array, assumed to be unmodified during use
     * @return a spliterator for the array elements
     * @since 1.8
     */
    public static Spliterator.OfDouble spliterator(double[] array) {
        return Spliterators.spliterator(array,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a {@link Spliterator.OfDouble} covering the specified range of
     * the specified array.
     *
     * <p>The spliterator reports {@link Spliterator#SIZED},
     * {@link Spliterator#SUBSIZED}, {@link Spliterator#ORDERED}, and
     * {@link Spliterator#IMMUTABLE}.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a spliterator for the array elements
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static Spliterator.OfDouble spliterator(double[] array, int startInclusive, int endExclusive) {
        return Spliterators.spliterator(array, startInclusive, endExclusive,
                                        Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }

    /**
     * Returns a sequential {@link Stream} with the specified array as its
     * source.
     *
     * @param <T> The type of the array elements
     * @param array The array, assumed to be unmodified during use
     * @return a {@code Stream} for the array
     * @since 1.8
     */
    public static <T> Stream<T> stream(T[] array) {
        return stream(array, 0, array.length);
    }

    /**
     * Returns a sequential {@link Stream} with the specified range of the
     * specified array as its source.
     *
     * @param <T> the type of the array elements
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a {@code Stream} for the array range
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive) {
        return StreamSupport.stream(spliterator(array, startInclusive, endExclusive), false);
    }

    /**
     * Returns a sequential {@link IntStream} with the specified array as its
     * source.
     *
     * @param array the array, assumed to be unmodified during use
     * @return an {@code IntStream} for the array
     * @since 1.8
     */
    public static IntStream stream(int[] array) {
        return stream(array, 0, array.length);
    }

    /**
     * Returns a sequential {@link IntStream} with the specified range of the
     * specified array as its source.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return an {@code IntStream} for the array range
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static IntStream stream(int[] array, int startInclusive, int endExclusive) {
        return StreamSupport.intStream(spliterator(array, startInclusive, endExclusive), false);
    }

    /**
     * Returns a sequential {@link LongStream} with the specified array as its
     * source.
     *
     * @param array the array, assumed to be unmodified during use
     * @return a {@code LongStream} for the array
     * @since 1.8
     */
    public static LongStream stream(long[] array) {
        return stream(array, 0, array.length);
    }

    /**
     * Returns a sequential {@link LongStream} with the specified range of the
     * specified array as its source.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a {@code LongStream} for the array range
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static LongStream stream(long[] array, int startInclusive, int endExclusive) {
        return StreamSupport.longStream(spliterator(array, startInclusive, endExclusive), false);
    }

    /**
     * Returns a sequential {@link DoubleStream} with the specified array as its
     * source.
     *
     * @param array the array, assumed to be unmodified during use
     * @return a {@code DoubleStream} for the array
     * @since 1.8
     */
    public static DoubleStream stream(double[] array) {
        return stream(array, 0, array.length);
    }

    /**
     * Returns a sequential {@link DoubleStream} with the specified range of the
     * specified array as its source.
     *
     * @param array the array, assumed to be unmodified during use
     * @param startInclusive the first index to cover, inclusive
     * @param endExclusive index immediately past the last index to cover
     * @return a {@code DoubleStream} for the array range
     * @throws ArrayIndexOutOfBoundsException if {@code startInclusive} is
     *         negative, {@code endExclusive} is less than
     *         {@code startInclusive}, or {@code endExclusive} is greater than
     *         the array size
     * @since 1.8
     */
    public static DoubleStream stream(double[] array, int startInclusive, int endExclusive) {
        return StreamSupport.doubleStream(spliterator(array, startInclusive, endExclusive), false);
    }

    

    public static class Proxy <T> implements Spliterator<T>, Iterable<T> {
        java.util.Spliterator<T> host;

        public Proxy(java.util.Spliterator<T> host) { this.host = host; }

        protected Proxy<T> alloc() { return new Proxy(null); }
       
        public Spliterator<T> trySplit() throws Pausable { 
            java.util.Spliterator<T> s2 = host.trySplit();
            if (s2==null) return null;
            Proxy<T> p2 = alloc();
            p2.host = s2;
            return p2;
        }
        public boolean tryAdvance(Consumer<? super T> action) throws Pausable { return host.tryAdvance( action ); }
        public void forEachRemaining(Consumer<? super T> action) throws Pausable { host.forEachRemaining( action ); }
        public long estimateSize() { return host.estimateSize(); }
        public long getExactSizeIfKnown() { return host.getExactSizeIfKnown(); }
        public int characteristics() { return host.characteristics(); }
        public boolean hasCharacteristics(int characteristics) { return host.hasCharacteristics( characteristics ); }
        public Comparator<? super T> getComparator() { return host.getComparator(); }

        public Stream<T> stream() { return StreamSupport.stream(this, false); }
        public Stream<T> parallelStream() { return StreamSupport.stream(this, true); }

        public Iterator<T> iterator() {
            return stream().iterator();
        }
    }    
    public static class P2
        <T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
        extends Proxy<T> implements Spliterator.OfPrimitive<T,T_CONS,T_SPLITR>
    {
        java.util.Spliterator.OfPrimitive<T,T_CONS,?> host2;
    
        public P2(java.util.Spliterator.OfPrimitive<T, T_CONS, ?> host) { super(host); }
        
        public T_SPLITR trySplit() throws Pausable {
            return (T_SPLITR) super.trySplit();
        }
        public boolean tryAdvance(T_CONS action) throws Pausable { return host2.tryAdvance( action ); }
        public static class OfDouble
                extends P2<Double, DoubleConsumer, Spliterator.OfDouble>
                implements Spliterator.OfDouble {
            OfDouble(java.util.Spliterator.OfDouble supplier) { super(supplier); }
            protected P2.OfDouble alloc() {
                return new P2.OfDouble(null);
            }
        }
    } 
    
    public abstract static class OfPrimitive2
        <T, T_CONS, T_SPLITR extends Spliterator.OfPrimitive<T, T_CONS, T_SPLITR>>
        implements Spliterator.OfPrimitive<T,T_CONS,T_SPLITR>
    {
        java.util.Spliterator.OfPrimitive<T,T_CONS,?> host;

        public OfPrimitive2(java.util.Spliterator.OfPrimitive<T, T_CONS, ?> host) {
            this.host = host;
        }

        protected abstract T_SPLITR alloc();
        
        public T_SPLITR trySplit() throws Pausable {
            OfPrimitive2 sp = (OfPrimitive2) alloc();
            sp.host = host.trySplit();
            return (T_SPLITR) sp;
        }
        public boolean tryAdvance(T_CONS action) throws Pausable { return host.tryAdvance( action ); }
        public void forEachRemaining(T_CONS action) throws Pausable { host.forEachRemaining( action ); }
        public boolean tryAdvance(Consumer<? super T> action) throws Pausable { return host.tryAdvance( action ); }
        public void forEachRemaining(Consumer<? super T> action) throws Pausable { host.forEachRemaining( action ); }
        public long estimateSize() { return host.estimateSize(); }
        public long getExactSizeIfKnown() { return host.getExactSizeIfKnown(); }
        public int characteristics() { return host.characteristics(); }
        public boolean hasCharacteristics(int characteristics) { return host.hasCharacteristics( characteristics ); }
        public Comparator<? super T> getComparator() { return host.getComparator(); }

        public static class OfInt
                extends OfPrimitive2<Integer, IntConsumer, Spliterator.OfInt>
                implements Spliterator.OfInt {
            OfInt(java.util.Spliterator.OfInt supplier) { super(supplier); }
            protected OfPrimitive2.OfInt alloc() {
                return new OfPrimitive2.OfInt(null);
            }
        }
        public static class OfLong
                extends OfPrimitive2<Long, LongConsumer, Spliterator.OfLong>
                implements Spliterator.OfLong {
            OfLong(java.util.Spliterator.OfLong supplier) { super(supplier); }
            protected OfPrimitive2.OfLong alloc() {
                return new OfPrimitive2.OfLong(null);
            }
        }
        public static class OfDouble
                extends OfPrimitive2<Double, DoubleConsumer, Spliterator.OfDouble>
                implements Spliterator.OfDouble {
            OfDouble(java.util.Spliterator.OfDouble supplier) { super(supplier); }
            protected OfPrimitive2.OfDouble alloc() {
                return new OfPrimitive2.OfDouble(null);
            }
        }
    }

    // fixme - add Collection, etc specializations
    // fixme - pull proxy usage up to top-level (where type info is available)
    
    public static OfPrimitive2.OfInt proxy(java.util.Spliterator.OfInt host) {
        return new OfPrimitive2.OfInt(host);
    }
    public static OfPrimitive2.OfLong proxy(java.util.Spliterator.OfLong host) {
        return new OfPrimitive2.OfLong(host);
    }
    public static OfPrimitive2.OfDouble proxy(java.util.Spliterator.OfDouble host) {
        return new OfPrimitive2.OfDouble(host);
    }
    public static <TT> Stream<TT> proxy(java.util.stream.Stream<TT> host) {
        Spliterator<TT> spliter = proxy(host.spliterator());
        return StreamSupport.stream(spliter,false);
    }
    public static <TT> IteratorProxy<TT> proxy(java.util.Iterator<TT> host) {
        return new IteratorProxy(host);
    }
    public static <TT> Proxy<TT> proxy(java.util.Collection<TT> host) {
        return new Proxy(host.spliterator());
    }
    public static <TT> IterableProxy<TT> proxy(java.lang.Iterable<TT> host) {
        return new IterableProxy(host);
    }
    public static <TT> SpliteratorProxy<TT> proxy(java.util.Spliterator<TT> host) {
        return new SpliteratorProxy(host);
    }

    /**
     *  proxy java.util.Iterator to allow collections to work as expected
     *  note: collections are inherently non-Pausable, so this isn't really a meaningful use case
     *        but it's here for completeness and to keep the APIs parallel
     */
    public static class IteratorProxy<TT> implements Iterator<TT> {
        public IteratorProxy(java.util.Iterator<TT> host) {
            this.host = host;
        }
        java.util.Iterator<TT> host;
        public boolean hasNext() throws Pausable { return host.hasNext(); }
        public TT next() throws Pausable { return host.next(); }
    }
    public static class IterableProxy<TT> implements Iterable<TT> {
        public IterableProxy(java.lang.Iterable<TT> host) {
            this.host = host;
        }
        java.lang.Iterable<TT> host;
        public Iterator<TT> iterator() {
            return proxy(host.iterator());
        }
        public Stream<TT> stream() { return StreamSupport.stream(spliterator(),false); }
        public Stream<TT> parallelStream() { return StreamSupport.stream(spliterator(),true); }
        }

    public static class SpliteratorProxy<TT> implements Spliterator<TT> {
        java.util.Spliterator<TT> host;
        public SpliteratorProxy(java.util.Spliterator<TT> host) {
            this.host = host;
        }

        public boolean tryAdvance(Consumer<? super TT> action) throws Pausable {
            return host.tryAdvance( action );
        }

        public void forEachRemaining(Consumer<? super TT> action) throws Pausable {
            host.forEachRemaining( action );
        }

        public Spliterator<TT> trySplit() throws Pausable {
            java.util.Spliterator<TT> h2 = host.trySplit();
            if (h2==null) return null;
            else return new SpliteratorProxy(h2);
        }

        public long estimateSize() {
            return host.estimateSize();
        }

        public long getExactSizeIfKnown() {
            return host.getExactSizeIfKnown();
        }

        public int characteristics() {
            return host.characteristics();
        }

        public boolean hasCharacteristics(int characteristics) {
            return host.hasCharacteristics( characteristics );
        }

        public Comparator<? super TT> getComparator() {
            return host.getComparator();
        }
        
    }

    public static class NoFeature extends UnsupportedOperationException {}
}
