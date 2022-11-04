package com.android.architecture.utils;

import java.util.Objects;

public class TupleUtil {

    private TupleUtil() {
        throw new AssertionError();
    }

    public static <Param1, Param2> Tuple<Param1, Param2> tuple(Param1 param1, Param2 param2) {
        return new Tuple<Param1, Param2>(param1, param2);
    }

    public static <Param1, Param2, Param3> Tuple3<Param1, Param2, Param3> tuple(Param1 param1, Param2 param2, Param3 param3) {
        return new Tuple3<Param1, Param2, Param3>(param1, param2, param3);
    }

    public static <Param1, Param2, Param3, Param4> Tuple4<Param1, Param2, Param3, Param4> tuple(Param1 param1, Param2 param2, Param3 param3, Param4 param4) {
        return new Tuple4<Param1, Param2, Param3, Param4>(param1, param2, param3, param4);
    }

    public static class Tuple<Param1, Param2> {
        public final Param1 param1;
        public final Param2 param2;

        public Tuple(Param1 param1, Param2 param2) {
            this.param1 = param1;
            this.param2 = param2;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple)) {
                return false;
            }
            Tuple<?, ?> t = (Tuple<?, ?>) o;
            return equalsEx(t.param1, param1) && equalsEx(t.param2, param2);
        }

        @Override
        public int hashCode() {
            return hashCodeEx(param1) ^ hashCodeEx(param2);
        }

        boolean equalsEx(Object a, Object b) {
            return Objects.equals(a, b);
        }

        int hashCodeEx(Object o) {
            return o == null ? 0 : o.hashCode();
        }
    }

    public static class Tuple3<Param1, Param2, Param3> extends Tuple<Param1, Param2> {
        public final Param3 param3;

        public Tuple3(Param1 param1, Param2 param2, Param3 param3) {
            super(param1, param2);
            this.param3 = param3;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple3)) {
                return false;
            }
            Tuple3<?, ?, ?> t = (Tuple3<?, ?, ?>) o;
            return equalsEx(t.param1, param1) && equalsEx(t.param2, param2) && equalsEx(t.param3, param3);
        }

        @Override
        public int hashCode() {
            return hashCodeEx(param1) ^ hashCodeEx(param2) ^ hashCodeEx(param3);
        }
    }

    public static class Tuple4<Param1, Param2, Param3, Param4> extends Tuple3<Param1, Param2, Param3> {
        public final Param4 param4;

        public Tuple4(Param1 param1, Param2 param2, Param3 param3, Param4 param4) {
            super(param1, param2, param3);
            this.param4 = param4;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Tuple4)) {
                return false;
            }
            Tuple4<?, ?, ?, ?> t = (Tuple4<?, ?, ?, ?>) o;
            return equalsEx(t.param1, param1) && equalsEx(t.param2, param2) && equalsEx(t.param3, param3) && equalsEx(t.param4, param4);
        }

        @Override
        public int hashCode() {
            return hashCodeEx(param1) ^ hashCodeEx(param2) ^ hashCodeEx(param3) ^ hashCodeEx(param4);
        }
    }
}
