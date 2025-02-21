package assaviv.plugins.types;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimitivesIdentityTest {
    private static final int EXPECTED_INT = 0;

    @Test
    @DisplayName("Test that int identity return default zero")
    void applyIntIdentity() {
        int actual = PrimitivesIdentity.apply(1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that short identity return default zero")
    void applyShortIdentity() {
        short actual = PrimitivesIdentity.apply((short) 1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that byte identity return default zero")
    void applyByteIdentity() {
        byte actual = PrimitivesIdentity.apply((byte) 1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that char identity return default zero")
    void applyCharIdentity() {
        char actual = PrimitivesIdentity.apply((char) 1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that long identity return default zero")
    void applyLongIdentity() {
        long actual = PrimitivesIdentity.apply((long) 1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that float identity return default zero")
    void applyFloatIdentity() {
        float actual = PrimitivesIdentity.apply((float) 1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }

    @Test
    @DisplayName("Test that double identity return default zero")
    void applyDoubleIdentity() {
        double actual = PrimitivesIdentity.apply((double) 1);

        Truth.assertThat(actual).isEqualTo(EXPECTED_INT);
    }


    @Test
    @DisplayName("Test that boolean identity return default false")
    void applyBooleanIdentity() {
        boolean actual = PrimitivesIdentity.apply(true);

        Truth.assertThat(actual).isFalse();
    }
}