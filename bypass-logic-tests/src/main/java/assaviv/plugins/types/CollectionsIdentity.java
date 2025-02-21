package assaviv.plugins.types;

import assaviv.annotations.BypassLogic;

import java.util.List;
import java.util.Map;
import java.util.Set;

@BypassLogic(reason = "Bypassed for collection")
public class CollectionsIdentity {
    public static List<Integer> apply(List<Integer> parameter) {
        return parameter;
    }

    public static Set<Integer> apply(Set<Integer> parameter) {
        return parameter;
    }

    public static Map<Integer, Integer> apply(Map<Integer, Integer> parameter) {
        return parameter;
    }
}
