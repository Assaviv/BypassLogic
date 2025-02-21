package assaviv.plugins.types;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class BoxedIdentityTest {
    private static final int EXPECTED_INT = 0;

    @Test
    @DisplayName("Test that int identity return default zero")
    void applyIntIdentity() {
        Integer actual = BoxedIdentity.apply(1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that short identity return default zero")
    void applyShortIdentity() {
        Short actual = BoxedIdentity.apply((short) 1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that byte identity return default zero")
    void applyByteIdentity() {
        Byte actual = BoxedIdentity.apply((byte) 1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that char identity return default zero")
    void applyCharIdentity() {
        Character actual = BoxedIdentity.apply((char) 1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that long identity return default zero")
    void applyLongIdentity() {
        Long actual = BoxedIdentity.apply((long) 1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that float identity return default zero")
    void applyFloatIdentity() {
        Float actual = BoxedIdentity.apply((float) 1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that double identity return default zero")
    void applyDoubleIdentity() {
        Double actual = BoxedIdentity.apply((double) 1);

        assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that boolean identity return default false")
    void applyBooleanIdentity() {
        Boolean actual = BoxedIdentity.apply(Boolean.TRUE);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Test that string identity return default blank")
    void applyStringIdentity() {
        String actual = BoxedIdentity.apply("Not Empty");

        assertThat(actual).isEmpty();
    }
}