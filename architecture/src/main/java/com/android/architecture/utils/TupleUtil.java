package com.android.architecture.utils;

import java.util.Objects;

public class TupleUtil {

    private TupleUtil() {
        throw new AssertionError();
    }

    public static <A, B> Tuple<A, B> tuple(A a, B b) {
        return new Tuple<A, B>(a, b);
    }

    public static <A, B, C> Tuple3<A, B, C> tuple(A a, B b, C c) {
        return new Tuple3<A, B, C>(a, b, c);
    }

    public static class Tuple<A, B> {
        public final A a;
        public final B b;

        public Tuple(A a, B b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple)) {
                return false;
            }
            Tuple<?, ?> t = (Tuple<?, ?>) o;
            return equalsEx(t.a, a) && equalsEx(t.b, b);
        }

        @Override
        public int hashCode() {
            return hashCodeEx(a) ^ hashCodeEx(b);
        }

        boolean equalsEx(Object a, Object b) {
            return Objects.equals(a, b);
        }

        int hashCodeEx(Object o) {
            return o == null ? 0 : o.hashCode();
        }
    }

    public static class Tuple3<A, B, C> extends Tuple<A, B> {
        public final C c;

        public Tuple3(A a, B b, C c) {
            super(a, b);
            this.c = c;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple3)) {
                return false;
            }
            Tuple3<?, ?, ?> t = (Tuple3<?, ?, ?>) o;
            return equalsEx(t.a, a) && equalsEx(t.b, b) && equalsEx(t.c, c);
        }

        @Override
        public int hashCode() {
            return hashCodeEx(a) ^ hashCodeEx(b) ^ hashCodeEx(c);
        }
    }
}
