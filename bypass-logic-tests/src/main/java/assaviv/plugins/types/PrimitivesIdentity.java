package assaviv.plugins.types;

import assaviv.annotations.BypassLogic;

@BypassLogic
public class PrimitivesIdentity {
    public static int apply(int parameter) {
        return parameter;
    }

    public static short apply(short parameter) {
        return parameter;
    }

    public static byte apply(byte parameter) {
        return parameter;
    }

    public static char apply(char parameter) {
        return parameter;
    }

    public static long apply(long parameter) {
        return parameter;
    }

    public static float apply(float parameter) {
        return parameter;
    }

    public static double apply(double parameter) {
        return parameter;
    }

    public static boolean apply(boolean parameter) {
        return parameter;
    }
}
