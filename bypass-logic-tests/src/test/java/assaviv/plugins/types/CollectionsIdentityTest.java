package assaviv.plugins.types;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollectionsIdentityTest {
    @Test
    @DisplayName("Test that integers list identity return default empty")
    void applyIntegerListIdentity() {
        List<Integer> expectedList = List.of(1, 2, 3);

        List<Integer> actual = CollectionsIdentity.apply(expectedList);

        Truth.assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test that integers set identity return default empty")
    void applyIntegerSetIdentity() {
        Set<Integer> expectedSet = Set.of(1, 2, 3);

        Set<Integer> actual = CollectionsIdentity.apply(expectedSet);

        Truth.assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test that integers map identity return default empty")
    void applyIntegersMapIdentity() {
        Map<Integer, Integer> expectedMap = Map.of(1, 2, 3, 4);

        Map<Integer, Integer> actual = CollectionsIdentity.apply(expectedMap);

        Truth.assertThat(actual).isEmpty();
    }
}
