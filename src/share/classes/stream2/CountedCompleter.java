/*
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
significant changes copyright lytles/nqzero 2016, released under the same terms
or the MIT license at your choice
*/


package stream2;

import kilim.Pausable;

/**
 * a fake fork/join task ... best attempt has been made to keep this working
 * for parallel streams, but it's mostly untested
 */
abstract class CountedCompleter<TT> {
    /** fork is fake, ie it's sequential which allows bypassing locks
     * this method is used as a marker to track places that are dependent on this assumption
     * in case fork/join is ever "fixed" and multiple threads are implemented
     */
    final static void forkIsFake() {}

    public final TT invoke() throws Pausable {
        compute();
        return getRawResult();
    }
    public final void fork() throws Pausable {
        Arrays2.kludge();
        if (status >= 0) this.compute(); // from ForkJoinPool.runTask
    }
    volatile int status; // accessed directly by pool and workers
    static private final int NORMAL      = 0xf0000000;  // must be negative
    private static final long serialVersionUID = 5232453752276485070L;

    /** This task's completer, or null if none */
    final CountedCompleter<?> completer;
    /** The number of pending tasks until completion */
    volatile int pending;


    /**
     * Creates a new CountedCompleter with the given completer
     * and an initial pending count of zero.
     *
     * @param completer this task's completer, or {@code null} if none
     */
    protected CountedCompleter(CountedCompleter<?> completer) {
        this.completer = completer;
    }

    /**
     * Creates a new CountedCompleter with no completer
     * and an initial pending count of zero.
     */
    protected CountedCompleter() {
        this.completer = null;
    }

    /**
     * The main computation performed by this task.
     */
    public abstract void compute() throws Pausable;

    /**
     * Performs an action when method {@link #tryComplete} is invoked
     * and the pending count is zero, or when the unconditional
     * method {@link #complete} is invoked.  By default, this method
     * does nothing. You can distinguish cases by checking the
     * identity of the given caller argument. If not equal to {@code
     * this}, then it is typically a subtask that may contain results
     * (and/or links to other results) to combine.
     *
     * @param caller the task invoking this method (which may
     * be this task itself)
     */
    public void onCompletion(CountedCompleter<?> caller) throws Pausable {
    }


    /**
     * Returns the completer established in this task's constructor,
     * or {@code null} if none.
     *
     * @return the completer
     */
    public final CountedCompleter<?> getCompleter() {
        return completer;
    }

    /**
     * Returns the current pending count.
     *
     * @return the current pending count
     */
    public final int getPendingCount() {
        return pending;
    }

    /**
     * Sets the pending count to the given value.
     *
     * @param count the count
     */
    public final void setPendingCount(int count) {
        pending = count;
    }

    /**
     * Adds (atomically) the given value to the pending count.
     *
     * @param delta the value to add
     */
    public final void addToPendingCount(int delta) {
        Arrays2.kludge();
        pending += delta;
    }




    /**
     * If the pending count is nonzero, decrements the count;
     * otherwise invokes {@link #onCompletion(CountedCompleter)}
     * and then similarly tries to complete this task's completer,
     * if one exists, else marks this task as complete.
     */
    public final void tryComplete() throws Pausable {
        CountedCompleter<?> a = this, s = a;
        for (;;) {
            if (a.pending==0) {
                a.onCompletion(s);
                if ((a = (s = a).completer) == null) {
                    s.quietlyComplete();
                    return;
                }
            }
            else {
                a.pending--;
                return;
            }
        }
    }

    /**
     * Equivalent to {@link #tryComplete} but does not invoke {@link
     * #onCompletion(CountedCompleter)} along the completion path:
     * If the pending count is nonzero, decrements the count;
     * otherwise, similarly tries to complete this task's completer, if
     * one exists, else marks this task as complete. This method may be
     * useful in cases where {@code onCompletion} should not, or need
     * not, be invoked for each completer in a computation.
     */
    public final void propagateCompletion() {
        CountedCompleter<?> a = this, s = a;
        for (;;) {
            if (a.pending==0) {
                if ((a = (s = a).completer) == null) {
                    s.quietlyComplete();
                    return;
                }
            }
            else {
                a.pending--;
                return;
            }
        }
    }








    /**
     * Returns the result of the computation. By default
     * returns {@code null}, which is appropriate for {@code Void}
     * actions, but in other cases should be overridden, almost
     * always to return a field or function of a field that
     * holds the result upon completion.
     *
     * @return the result of the computation
     */
    public TT getRawResult() { return null; }


    public final void quietlyComplete() {
        Arrays2.kludge();
        if (status==0) status |= NORMAL;
    }
}
