package assaviv.plugins.types;

import assaviv.annotations.BypassLogic;

public class BoxedIdentity {
    @BypassLogic
    public static Integer apply(Integer parameter) {
        return parameter;
    }

    @BypassLogic
    public static Short apply(Short parameter) {
        return parameter;
    }

    @BypassLogic
    public static Byte apply(Byte parameter) {
        return parameter;
    }

    @BypassLogic
    public static Character apply(Character parameter) {
        return parameter;
    }

    @BypassLogic
    public static Long apply(Long parameter) {
        return parameter;
    }

    @BypassLogic
    public static Float apply(Float parameter) {
        return parameter;
    }

    @BypassLogic
    public static Double apply(Double parameter) {
        return parameter;
    }

    @BypassLogic
    public static Boolean apply(Boolean parameter) {
        return parameter;
    }

    @BypassLogic
    public static String apply(String parameter) {
        return parameter;
    }
}
